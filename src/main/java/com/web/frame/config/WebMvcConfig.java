package com.web.frame.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.web.frame.entity.table.system.User;
import com.web.frame.entity.table.web.App;
import com.web.frame.entity.table.web.Wallet;
import com.web.frame.exception.BussinessException;
import com.web.frame.util.AccessTokenUtil;
import com.web.frame.util.Out;
import com.web.frame.util.SessionContext;
import com.web.frame.util.redis.RedisUtil;

/**
 * 拦截器
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{

	@Autowired
	private Out out;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private AccessTokenUtil accessTokenUtil;
	@Value("${url}")
    private String url;
	
    /**
     * 自定义拦截器
     *
     * @return HandlerInterceptor
     */
    @Bean
    public HandlerInterceptor getAccessInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BussinessException {
            	
            	String uri = request.getRequestURI();
            	if(uri.equals("/")||uri.contains("error")) {
            		try {
						response.sendRedirect(url+"/doc.html");
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            	if(!uri.contains("api")) {//后台操作或找回密码
            		/*User user = SessionContext.get("user");
            		if(user == null) {
            			try {
							response.sendRedirect("/login");
						} catch (IOException e) {
							e.printStackTrace();
						}
            			return false;
            		}*/
            		return true;
            	}else if(uri.contains("api") //api接口
            			&& !uri.contains("/api/app")){//非app接口
            		
            		String token = request.getHeader("accessToken");
            		
            		if(StringUtils.isEmpty(token)) {
            			throw new BussinessException(40001, "header参数未传入accessToken");
            		}
            		
            		String appId = accessTokenUtil.getAppId(token);
            		if(redisUtil.get(appId)==null) {
            			throw new BussinessException(40006,"appId不存在");
            		}
            		App app = (App) redisUtil.get(appId);
            		if(!"1".equals(app.getVerify())) {
            			throw new BussinessException(40006,"APP尚未认证");
            		}
            		
            		if(!uri.contains("/api/wallet")) {//非钱包接口
            			String walletId = request.getHeader("walletId");
            			if(StringUtils.isEmpty(walletId)) {
            				throw new BussinessException(40001, "header参数未传入walletId");
            			}
            			if(redisUtil.get(walletId)==null)
            				throw new BussinessException(40006, "该钱包不存在");
            			Wallet wallet = (Wallet) redisUtil.get(walletId);
            			if(!wallet.getAppId().equals(app.getAppId())) 
            				throw new BussinessException(40006, "该钱包不属于当前app");
            			if(!"1".equals(wallet.getVerify()))
            				throw new BussinessException(40006,"钱包尚未认证");
            		}else {
            			if(uri.contains("symbol")||uri.contains("balance")) {//用户代币接口
            				String walletId = request.getHeader("walletId");
                			if(StringUtils.isEmpty(walletId)) {
                				throw new BussinessException(40001, "header参数未传入walletId");
                			}
                			if(redisUtil.get(walletId)==null)
                				throw new BussinessException(40006, "该钱包不存在");
                			Wallet wallet = (Wallet) redisUtil.get(walletId);
                 			if(!wallet.getAppId().equals(app.getAppId())) 
                				throw new BussinessException(40006, "该钱包不属于当前app");
                			if(!"1".equals(wallet.getVerify()))
                				throw new BussinessException(40006,"钱包尚未认证");
            			}
            		}
            		
            		return true;
            	}else {
            		return true;
            	}
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

            }

            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns 用于添加拦截规则
        //excludePathPatterns 用于排除拦截
        registry.addInterceptor(getAccessInterceptor()).addPathPatterns("/**")
         
		/*放行swagger*/
        .excludePathPatterns("/register").excludePathPatterns("/login")
        .excludePathPatterns("/main").excludePathPatterns("/exit")
        .excludePathPatterns("/static/**")
        .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/doc.html/**");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*放行静态资源*/
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");
        /*放行swagger*/
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        /*swagger-ui*/
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        /*swagger-boostrap-ui*/
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }
    
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    	super.configureMessageConverters(converters);
    	converters.add(responseBodyConverter());
    }
    
    @Bean
    public HttpMessageConverter responseBodyConverter() {
    	
	     StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
	     return converter;
   }
}
