package controller;

import Model.User;
import context.TodoDAO;
import helper.HTTPServerHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class DeleteItemController extends BaseAuthentication implements HttpBaseController {

    @Override
    public void ProcessGet(PrintWriter writer, OutputStream outputStream, String header) {
        String paramString = HTTPServerHelper.getParameterString(header);
        int id = Integer.parseInt(HTTPServerHelper.getParameter(paramString, "id"));
        TodoDAO.deleteItem(id);
    }

    @Override
    public void ProcessPost(PrintWriter writer, OutputStream outputStream, String payload) {

    }

    @Override
    public void doGet(PrintWriter writer, OutputStream outputStream, String header) {
        System.out.println(getUserToken());
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
