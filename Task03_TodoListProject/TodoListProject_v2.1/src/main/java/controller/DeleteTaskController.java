package controller;

import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import context.TodoDAO;
import context.UserDAO;
import helper.HTTPServerHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class DeleteTaskController implements HttpBaseController, BaseAuthentication{
    @Override
    public void processGet(PrintWriter writer, OutputStream outputStream, String header, User user) {

    }

    @Override
    public void processPost(PrintWriter writer, OutputStream outputStream, String payload, User user) {
        int taskId = Integer.parseInt(HTTPServerHelper.getParameter(payload, "id"));
        TodoDAO.deleteTask(user.getId(), taskId);
        HTTPServerHelper.responeString(writer, outputStream, "Delete Successful");
    }

    @Override
    public void doGet(PrintWriter writer, OutputStream outputStream, String header) {
        String paramString = HTTPServerHelper.getParameterString(header);
        String token = HTTPServerHelper.getParameter(paramString, "token");
        if (token == null) {
            try {
                HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                User user = UserDAO.authenticationToken(token);
                if (user == null) {
                    HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
                } else {
                    processGet(writer, outputStream, header, user);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
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
                throw new RuntimeException(e);
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
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
