package com.web.frame.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author yaoyao
 *
 */
@Scope("prototype")
@Component
public class Out {
	
	public void out(HttpServletResponse response, String result) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write(result);
		out.flush();
		out.close();
	}
	
	public void outJson(HttpServletResponse response, String result) {
		
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write(result);
		out.flush();
		out.close();
	}
	
	/**
	 * return string true
	 */
	public void outTrue(HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("true");
		out.flush();
		out.close();
	}
	
	public void outTrueJson(HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("true");
		out.flush();
		out.close();
	}

	/**
	 * return string false
	 */
	public void outFalse(HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("false");
		out.flush();
		out.close();
	}
	
	public void outFalseJson(HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("false");
		out.flush();
		out.close();
	}
	
	/**
	 * return ok true
	 */
	public void outOkTrue(HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("{\"ok\":\"true\"}");
		out.flush();
		out.close();
	}
	
	public void outOkTrueJson(HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("{\"ok\":\"true\"}");
		out.flush();
		out.close();
	}
	
	public void outOkTrueMsgJson(HttpServletResponse response,String msg) {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("{\"ok\":true,\"msg\":\""+msg+"\"}");
		out.flush();
		out.close();
	}
	
	/**
	 * return ok false
	 */
	public void outOkFalse(HttpServletResponse response) {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("{\"ok\":\"false\"}");
		out.flush();
		out.close();
	}
	
	public void outOkFalseJson(HttpServletResponse response) {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("{\"ok\":false}");
		out.flush();
		out.close();
	}
	
	
	public void outOkFalseMsgJson(HttpServletResponse response,String msg) {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("{\"ok\":false,\"msg\":\""+msg+"\"}");
		out.flush();
		out.close();
	}
	
	
	
	
	public void outOkTrueDataJson(HttpServletResponse response,String data) {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("{\"ok\":true,\"data\":"+data+"}");
		out.flush();
		out.close();
	}
	
	
	public void outOkFalseDataJson(HttpServletResponse response,String data) {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write("{\"ok\":false,\"data\":"+data+"}");
		out.flush();
		out.close();
	}
	
	
	
	
	public void outToResponse(HttpServletResponse response, String result) {
        response.setCharacterEncoding("GBK");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.write(result);
        out.flush();
        out.close();
    }

}
