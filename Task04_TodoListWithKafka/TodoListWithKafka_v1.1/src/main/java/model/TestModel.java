package model;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TestModel {
    private InputStream inputStream;
    private OutputStream outputStream;
    private PrintWriter printWriter;

    public TestModel(InputStream inputStream, OutputStream outputStream, PrintWriter printWriter) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.printWriter = printWriter;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }

    public TestModel(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
