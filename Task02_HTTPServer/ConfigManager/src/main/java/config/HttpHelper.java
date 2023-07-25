package config;

import java.io.*;
import java.net.Socket;

public class HttpHelper {

    public static String getParameter(Socket socket, String paramName, boolean isPayload) {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            if (!isPayload) {
                String line = bufferedReader.readLine();
                String stringParameter = getParameterString(line);
                if (stringParameter != null) {
                    return getParameter(stringParameter, paramName);
                }
                return null;
            } else {
                String headerLine = null;
                while ((headerLine = bufferedReader.readLine()).length() != 0) {
                    System.out.println(headerLine);
                }

                StringBuilder payload = new StringBuilder();
                while (bufferedReader.ready()) {
                    payload.append((char) bufferedReader.read());
                }
                if (!payload.isEmpty()) {
                    return getParameter(payload.toString(), paramName);
                }
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String getParameterString(String line) {
        if (!line.contains("?")) {
            return null;
        }
        int start = line.indexOf("?") + 1;
        int end = line.indexOf(" ", start);
        return line.substring(start, end);
    }

    public static String getParameter(String s, String paramName) {
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

    public static void main(String[] args) {
        String line = getParameterString("Request: GET /?name=anhnv&password=cuocdoi&desc=helloworldvahoicham HTTP/1.1");
        System.out.println(line);
        System.out.println("Name: " + getParameter(line, "name"));
        System.out.println("Pass: " + getParameter(line, "password"));
        System.out.println("Pass: " + getParameter(line, "desc"));
    }
}
