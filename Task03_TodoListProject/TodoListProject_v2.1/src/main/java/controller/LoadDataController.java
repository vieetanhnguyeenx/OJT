package controller;

import Model.Todo;
import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import context.DBContext;
import context.TodoDAO;
import context.UserDAO;
import helper.HTTPServerHelper;
import helper.JsonHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class LoadDataController implements HttpBaseController, BaseAuthentication {
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

    @Override
    public void processGet(PrintWriter writer, OutputStream outputStream, String header, User user) {

    }

    @Override
    public void processPost(PrintWriter writer, OutputStream outputStream, String payload, User user) {
        List<Todo> todoList = TodoDAO.getTodoList(user.getId());
        JsonNode jsonNode = JsonHelper.toJson(todoList);
        try {
            String todoJson = JsonHelper.stringgify(jsonNode);
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: application/json; charset=utf-8");
            writer.println("Content-Length: " + todoJson.length());
            writer.println();
            writer.println(todoJson);
            writer.flush();
        } catch (JsonProcessingException e) {
            try {
                HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


    }
}
