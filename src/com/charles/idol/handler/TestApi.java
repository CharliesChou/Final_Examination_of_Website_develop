package com.charles.idol.handler;
import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class TestApi extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	response.setCharacterEncoding("utf-8");
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	System.out.println(request.getParameter("data"));
	String jsonData="["+"{\"name\":\"charles\"}"+",{\"name\":\"jack\"}"+"]";
	response.getWriter().write("{\"name\":\"charles\"}");
	}
}
