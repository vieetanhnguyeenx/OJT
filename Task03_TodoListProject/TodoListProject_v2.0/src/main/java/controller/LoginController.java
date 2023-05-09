package controller;

import Model.User;
import context.UserDAO;
import helper.HTTPServerHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class LoginController extends BaseAuthentication implements HttpBaseController {


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
        Boolean flag = authentication(HTTPServerHelper.getParameter(payload.toString(), "username"),
                HTTPServerHelper.getParameter(payload.toString(), "password"));
        if (flag) {
            HTTPServerHelper.sendRedirect("http://localhost:8888/todolist", outputStream);
        } else {
            HTTPServerHelper.sendRedirect("http://localhost:8888/login", outputStream);
        }
    }

    private Boolean authentication(String userName, String password) {
        User user = UserDAO.getUser(userName, password);
        if (user != null) {
            setUserToken(user);
            return true;
        } else {
            setUserToken(null);
            return false;
        }
    }

    @Override
    public void ProcessGet(PrintWriter writer, OutputStream outputStream, String header) {

    }

    @Override
    public void ProcessPost(PrintWriter writer, OutputStream outputStream, String payload) {
    }
}
