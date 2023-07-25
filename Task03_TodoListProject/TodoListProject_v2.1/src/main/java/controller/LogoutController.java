package controller;

import Model.User;

import java.io.OutputStream;
import java.io.PrintWriter;

public class LogoutController implements HttpBaseController, BaseAuthentication {
    @Override
    public void processGet(PrintWriter writer, OutputStream outputStream, String header, User user) {

    }

    @Override
    public void processPost(PrintWriter writer, OutputStream outputStream, String payload, User user) {

    }

    @Override
    public void doGet(PrintWriter writer, OutputStream outputStream, String header) {

    }

    @Override
    public void doPost(PrintWriter writer, OutputStream outputStream, String payload) {

    }
}
