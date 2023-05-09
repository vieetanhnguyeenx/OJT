package controller;

import Model.Todo;
import Model.User;
import context.TodoDAO;
import helper.HTTPServerHelper;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class LoadDataController extends BaseAuthentication implements HttpBaseController{


    @Override
    public void ProcessGet(PrintWriter writer, OutputStream outputStream, String header) {
        loadData(writer, outputStream);
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

    private static void loadData(PrintWriter writer, OutputStream outputStream) {
        List<Todo> todoList = TodoDAO.getTodoList(getUserToken().getId());
        StringBuilder builder = new StringBuilder();
        for (Todo t : todoList) {
            if (t.isStatus()) {
                builder.append("<li class=\"list-group-item list-group-item-action list-group-item-success\">\n" +
                        "                    <div class=\"row\">\n" +
                        "                        <div class=\"col-md-1\">\n" +
                        "                            <input onclick=\"changeStatus(" + t.getId() + ")\"  class=\"form-check-input me-1\" type=\"checkbox\" checked value=\"\" aria-label=\"...\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"col-md-8\">\n" +
                        "                            <p class=\"mb-0\">" + t.getTitle() + "</p>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"col-md-1\">\n" +
                        "                            <button onclick=\"update("+t.getId()+")\" class=\"btn btn-outline-primary py-0\">Edit</button>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"col-md-2\">\n" +
                        "                            <button onclick=\"deleteItem("+ t.getId() +")\" class=\"btn btn-outline-danger py-0\">Delete</button>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </li>\n");
            } else {
                builder.append("<li class=\"list-group-item list-group-item-action list-group-item-success\">\n" +
                        "                    <div class=\"row\">\n" +
                        "                        <div class=\"col-md-1\">\n" +
                        "                            <input onclick=\"changeStatus(" + t.getId() + ")\" on class=\"form-check-input me-1\" type=\"checkbox\" value=\"\" aria-label=\"...\">\n" +
                        "                        </div>\n" +
                        "                        <div class=\"col-md-8\">\n" +
                        "                            <p class=\"mb-0\">" + t.getTitle() + "</p>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"col-md-1\">\n" +
                        "                            <button onclick=\"update("+t.getId()+")\" class=\"btn btn-outline-primary py-0\">Edit</button>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"col-md-2\">\n" +
                        "                            <button onclick=\"deleteItem("+ t.getId() +")\" class=\"btn btn-outline-danger py-0\">Delete</button>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </li>\n");
            }
        }
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html;charset=utf-8");
        writer.println("Content-Length: " + builder.toString().length());
        writer.println();
        writer.println(builder.toString());
        writer.flush();

    }
}
