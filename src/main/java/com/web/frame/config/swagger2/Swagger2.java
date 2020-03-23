package com.web.frame.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.method.HandlerMethod;

import com.google.common.base.Predicate;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.WebMvcRequestHandler;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2MapperImpl;

/**
 * http://localhost:8080/swagger-ui.html
 * @author Administrator
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	
	@Bean
	public ServiceModelToSwagger2Mapper mapper(){
		
		return new ServiceModelToSwagger2MapperImplEx();
//		return new ServiceModelToSwagger2MapperImpl();
	}

	@Bean
    public Docket createRestApi() {
		
		Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
            	
            	
                if (input.isAnnotatedWith(ApiOperation.class)) {//只有添加了ApiOperation注解的method才在API中显示
                	return true;
                	/*if (input instanceof WebMvcRequestHandler) {
                        WebMvcRequestHandler handler = (WebMvcRequestHandler)input;
                        HandlerMethod handlerMethod = handler.getHandlerMethod();
                        // 已经拿到Handler和HandlerMethod了，尽情根据实际情况过滤吧。
                        if(!handlerMethod.getBean().toString().toLowerCase().equals("accountcontroller")) {
                       	 	return true;
                        }else {
                       	 	return false;
                        }
                    }else {
                    	return true;
                    }*/
                }else {
                	return false;
                }
            }
        };
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("v1.0")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .select()
                .apis(predicate)
                .paths(PathSelectors.any())//过滤的接口
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("Baas接口文档")
                        .description("支持以太坊、墨客（公链及子链）的baas接口")
                        .version("1.0")
                        .contact(new Contact("望天-李江", "", "1243817476@qq.com"))
                        .build());
    }
	
	/*@Bean
    public Docket createRestApi2() {
		
		Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
            	
            	 if (input instanceof WebMvcRequestHandler) {
                     WebMvcRequestHandler handler = (WebMvcRequestHandler)input;
                     HandlerMethod handlerMethod = handler.getHandlerMethod();
                     // 已经拿到Handler和HandlerMethod了，尽情根据实际情况过滤吧。
                     if(handlerMethod.getBean().toString().toLowerCase().equals("accountcontroller")) {
                    	 return true;
                     }else {
                    	 return false;
                     }
                 }
            	
                if (input.isAnnotatedWith(ApiOperation.class))//只有添加了ApiOperation注解的method才在API中显示
                    return true;
                return false;
            }
        };
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .groupName("v2.0")
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .select()
                .apis(predicate)
//                .paths(PathSelectors.regex("/account/.*"))//过滤的接口
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("望天baas接口文档")
                        .description("接口目前支持以太坊、墨客、井通，若有疑问，请联系邮箱：1243817476@qq.com")
                        .version("2.0")
//                        .contact(new Contact("江，邮箱：1243817476@qq.com", "", ""))
                        .build());
    }*/
    
}