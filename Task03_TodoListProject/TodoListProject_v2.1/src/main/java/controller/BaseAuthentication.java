package controller;

import Model.User;

import java.io.OutputStream;
import java.io.PrintWriter;

public interface BaseAuthentication {
    public void processGet(PrintWriter writer, OutputStream outputStream, String header, User user);

    public void processPost(PrintWriter writer, OutputStream outputStream, String payload, User user);
}
