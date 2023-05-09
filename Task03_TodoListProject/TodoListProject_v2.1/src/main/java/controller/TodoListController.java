package controller;

import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import context.UserDAO;
import helper.HTTPServerHelper;
import helper.TokenHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TodoListController implements HttpBaseController, BaseAuthentication{


    @Override
    public void doGet(PrintWriter writer, OutputStream outputStream, String header) {
        String paramString = HTTPServerHelper.getParameterString(header);
        String token = HTTPServerHelper.getParameter(paramString, "token");
        if (token == null) {
            try {
                HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
            } catch (IOException e) {
                HTTPServerHelper.sendRedirect("http://localhost:8888/404NotFound", outputStream);
            }
        } else {
            try {
                User user = UserDAO.authenticationToken(token);
                if (user == null) {
                    HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
                } else {
                    processGet(writer, outputStream, header, user);
                }
            } catch (Exception e) {
                HTTPServerHelper.sendRedirect("http://localhost:8888/404NotFound", outputStream);
            }
        }
    }

    @Override
    public void doPost(PrintWriter writer, OutputStream outputStream, String payload) {
        String token = HTTPServerHelper.getParameter(payload, "token");
        if (token == null) {
            try {
                HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
            } catch (IOException e) {
                HTTPServerHelper.sendRedirect("http://localhost:8888/404NotFound", outputStream);
            }
        } else {
            try {
                User user = UserDAO.authenticationToken(token);
                if (user == null) {
                    HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
                } else {
                    processPost(writer, outputStream, payload, user);
                }
            } catch (JsonProcessingException e) {
                HTTPServerHelper.sendRedirect("http://localhost:8888/404NotFound", outputStream);
            } catch (IOException e) {
                HTTPServerHelper.sendRedirect("http://localhost:8888/404NotFound", outputStream);
            }
        }
    }

    @Override
    public void processGet(PrintWriter writer, OutputStream outputStream, String header, User user) {
        try {
            HTTPServerHelper.forwardHtml(writer, outputStream, "todoList.html");
        } catch (IOException e) {
            HTTPServerHelper.sendRedirect("http://localhost:8888/404NotFound", outputStream);
        }
    }

    @Override
    public void processPost(PrintWriter writer, OutputStream outputStream, String payload, User user) {

    }
}
