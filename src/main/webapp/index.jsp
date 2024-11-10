<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>

<%
    String usuarioLogado = (String) session.getAttribute("usuarioLogado");
    if (usuarioLogado == null) {
        // Redireciona para login.jsp se o usuário não estiver logado
        response.sendRedirect("login");
        return;

    }
    else {
        response.sendRedirect("dashboard");
    }
%>




