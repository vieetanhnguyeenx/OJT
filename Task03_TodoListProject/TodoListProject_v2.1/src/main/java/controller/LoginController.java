package controller;

import Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import context.UserDAO;
import helper.HTTPServerHelper;
import helper.JsonHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Base64;

public class LoginController implements HttpBaseController{
    @Override
    public void doGet(PrintWriter writer, OutputStream outputStream, String header) {
        try {
            HTTPServerHelper.forwardHtml(writer, outputStream, "login.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(PrintWriter writer, OutputStream outputStream, String payload) {
        try {
            String token = getToken(HTTPServerHelper.getParameter(payload, "username"),
                    HTTPServerHelper.getParameter(payload, "password"));
            if(token != null) {
                HTTPServerHelper.sendRe                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               direct("http://localhost:8888/todolist?token=" + token, outputStream);
            } else {
                HTTPServerHelper.sendRedirect("http://localhost:8888/login", outputStream);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getToken(String username, String password) throws JsonProcessingException {
       User user = UserDAO.getUser(username, password);
       if(user != null) {
           JsonNode jsonNode = JsonHelper.toJson(user);
           String jString = JsonHelper.stringgify(jsonNode);
           Base64.Encoder encoder = Base64.getEncoder();
           String token = encoder.encodeToString(jString.getBytes());
           return token;
       }
       return null;
    }
}
