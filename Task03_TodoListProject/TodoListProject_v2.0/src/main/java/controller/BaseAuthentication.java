package controller;

import Model.User;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

public abstract class BaseAuthentication {
    protected static User userToken;

    public static User getUserToken() {
        return userToken;
    }

    public static void setUserToken(User userToken) {
        BaseAuthentication.userToken = userToken;
    }

    public abstract void ProcessGet(PrintWriter writer, OutputStream outputStream, String header);

    public abstract void ProcessPost(PrintWriter writer, OutputStream outputStream, String payload);
}
