package org.example;

import Model.Todo;
import context.DBContext;
import context.TodoDAO;
import helper.HTTPServerHelper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            List<String> header;
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connected");


                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter writer = new PrintWriter(outputStream, true);
                header = new ArrayList<>();
                String hederLine = null;
                while ((hederLine = bufferedReader.readLine()).length() != 0) {
                    System.out.println(hederLine);
                    header.add(hederLine);
                }

                StringBuilder payload = new StringBuilder();
                while (bufferedReader.ready()) {
                    payload.append((char) bufferedReader.read());
                }
                // Do GET
                if (HTTPServerHelper.getMethod(header.get(0)).equals("get")) {
                    String url = HTTPServerHelper.getUrl(header.get(0));

                    // /login and /
                    if (url.toLowerCase().equals("/login") || url.toLowerCase().equals("/")) {

                        HTTPServerHelper.forwardHtml(writer, outputStream, "login.html");

                        //    /todolist
                    } else if (url.toLowerCase().equals("/todolist")) {

                        HTTPServerHelper.forwardHtml(writer, outputStream, "todolist.html");

                        //  /loaddata
                    } else if (url.toLowerCase().equals("/loaddata")) {
                        loadData(writer, outputStream);


                    } else if (url.toLowerCase().equals("/changestatus")) {
                        String paramString = HTTPServerHelper.getParameterString(header.get(0));
                        int id = Integer.parseInt(HTTPServerHelper.getParameter(paramString, "id"));
                        changeStatus(id);

                    } else {
                        HTTPServerHelper.forwardHtml(writer, outputStream, "404NotFound.html");
                    }
                } else if (HTTPServerHelper.getMethod(header.get(0)).equals("post")) {

                    String url = HTTPServerHelper.getUrl(header.get(0));
                    if (url.toLowerCase().equals("/login")) {
                        Boolean flag = authentication(HTTPServerHelper.getParameter(payload.toString(), "username"),
                                HTTPServerHelper.getParameter(payload.toString(), "password"));
                        if (flag) {
                            HTTPServerHelper.sendRedirect("http://localhost:8888/todolist", outputStream);
                        } else {
                            HTTPServerHelper.sendRedirect("http://localhost:8888/login", outputStream);
                        }
                    }
                }

                inputStream.close();
                outputStream.close();
                socket.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Boolean authentication(String userName, String password) {
        if (!userName.trim().toLowerCase().equals("anhnv")) {
            return false;
        }
        if (!password.trim().toLowerCase().equals("123")) {
            return false;
        }
        return true;
    }

    private static void changeStatus(int id) {
        TodoDAO.changeStatus(id);
    }

    private static void loadData(PrintWriter writer, OutputStream outputStream) {
        List<Todo> todoList = TodoDAO.getTodoList();
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
                        "                            <button class=\"btn btn-outline-primary py-0\">Edit</button>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"col-md-2\">\n" +
                        "                            <button class=\"btn btn-outline-danger py-0\">Delete</button>\n" +
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
                        "                            <button class=\"btn btn-outline-primary py-0\">Edit</button>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"col-md-2\">\n" +
                        "                            <button class=\"btn btn-outline-danger py-0\">Delete</button>\n" +
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