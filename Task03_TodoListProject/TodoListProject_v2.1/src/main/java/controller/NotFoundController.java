package controller;

import helper.HTTPServerHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class NotFoundController implements HttpBaseController{
    @Override
    public void doGet(PrintWriter writer, OutputStream outputStream, String header) {
        try {
            HTTPServerHelper.forwardHtml(writer, outputStream, "404NotFound.html");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(PrintWriter writer, OutputStream outputStream, String payload) {

    }
}
