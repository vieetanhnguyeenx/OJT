package controller;

import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import context.TodoDAO;
import context.UserDAO;
import helper.HTTPServerHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class EditTaskController implements HttpBaseController, BaseAuthentication {

    @Override
    public void processGet(PrintWriter writer, OutputStream outputStream, String header, User user) {

    }

    @Override
    public void processPost(PrintWriter writer, OutputStream outputStream, String payload, User user) {
        int taskId = Integer.parseInt(HTTPServerHelper.getParameter(payload, "id"));
        String title = HTTPServerHelper.getParameter(payload, "title");
        TodoDAO.updateTitle(user.getId(), taskId, title);
        try {
            String response = "Success";
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text;charset=utf-8");
            writer.println("Content-Length: " + response.getBytes(StandardCharsets.UTF_8).length);
            writer.println();
            outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
