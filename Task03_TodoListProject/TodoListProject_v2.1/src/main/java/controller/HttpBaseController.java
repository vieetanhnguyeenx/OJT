package controller;

import java.io.OutputStream;
import java.io.PrintWriter;

public interface HttpBaseController {
    public void doGet(PrintWriter writer, OutputStream outputStream, String header);

    public void doPost(PrintWriter writer, OutputStream outputStream, String payload);

}
