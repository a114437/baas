<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.baidu.ueditor.ActionEnter"%>
<%@page import="java.io.PrintWriter"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	
	out.write(new ActionEnter( request, rootPath ).exec());
%>