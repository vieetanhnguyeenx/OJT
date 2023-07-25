package helper;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HTTPServerHelper {
    public static void sendRedirect(String url, OutputStream outputStream) {
        String response = "HTTP/1.1 302 Found\r\n"
                + "Location: " + url + "\r\n"
                + "Connection: close\r\n\r\n";
        try {
            outputStream.write(response.getBytes()); // gửi phản hồi đến client
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getHtml(String path) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), StandardCharsets.UTF_8));
        StringBuilder buf = new StringBuilder();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            buf.append(line);
        }
        String response = buf.toString();
        return response;
    }

    public static String getParameter(String s, String paramName) {
        if (s == null) {
            return null;
        }
        if (!s.contains(paramName)) {
            return null;
        }
        int start = s.indexOf(paramName + "=") + paramName.length() + 1;
        int end = -1;
        if (!s.substring(start).contains("&")) {
            end = s.length();
        } else {
            end = s.indexOf("&", start);
        }
        if (end == -1) {
            return null;
        }
        return s.substring(start, end);
    }

    public static String getUrl(String s) {
        if (s == null) {
            return null;
        }
        int start = s.indexOf(" /") + 1;
        int end = -1;
        if (s.contains("?")) {
            end = s.indexOf("?", start);
        } else {
            end = s.indexOf(" ", start);
        }
        if (end == -1) {
            return null;
        }
        return s.substring(start, end);
    }

    public static String getMethod(String s) {
        if (s == null) {
            return null;
        }
        if (s.toLowerCase().contains("get")) {
            return "get";
        } else if (s.toLowerCase().contains("post")) {
            return "post";
        }
        return null;
    }

    public static String getParameterString(String line) {
        if (!line.contains("?")) {
            return null;
        }
        int start = line.indexOf("?") + 1;
        int end = line.indexOf(" ", start);
        return line.substring(start, end);
    }


    public static void responeString(PrintWriter writer, OutputStream outputStream, String respString) {
        try {
            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: text;charset=utf-8");
            writer.println("Content-Length: " + respString.getBytes(StandardCharsets.UTF_8).length);
            writer.println();
            outputStream.write(respString.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void forwardHtml(PrintWriter writer, OutputStream outputStream, String path) throws IOException {
        String response = HTTPServerHelper.getHtml(path);
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: text/html;charset=utf-8");
        writer.println("Content-Length: " + response.getBytes(StandardCharsets.UTF_8).length);
        writer.println();
        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
    }

    //    public static String getParamPost(String paramName, String s) {
//        int start = s.indexOf(paramName + "=") + paramName.length() + 1;
//        int end = s.
//    }

    public static void forwardJson(PrintWriter writer, OutputStream outputStream, String jsonString) throws IOException {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: application/json; charset=utf-8");
        writer.println("Content-Length: " + jsonString.getBytes(StandardCharsets.UTF_8).length);
        writer.println();
        outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        String line = "GET /changeStatus?id=3 HTTP/1.1";
        System.out.println(HTTPServerHelper.getParameterString(line));
        System.out.println();
    }
}
