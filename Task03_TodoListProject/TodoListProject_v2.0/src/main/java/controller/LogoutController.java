package controller;

import helper.HTTPServerHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class LogoutController extends BaseAuthentication implements HttpBaseController{
    @Override
    public void ProcessGet(PrintWriter writer, OutputStream outputStream, String header) {
        setUserToken(null);
        HTTPServerHelper.sendRedirect("http://localhost:8888/login", outputStream);
    }

    @Override
    public void ProcessPost(PrintWriter writer, OutputStream outputStream, String payload) {

    }

    @Override
    public void doGet(PrintWriter writer, OutputStream outputStream, String header) {
        if (getUserToken() != null) {
            ProcessGet(writer, outputStream, header);
        } else {
            try {
                HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doPost(PrintWriter writer, OutputStream outputStream, String payload) {
        if (getUserToken() != null) {
            ProcessPost(writer, outputStream, payload);
        } else {
            try {
                HTTPServerHelper.forwardHtml(writer, outputStream, "accessDenied.html");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
