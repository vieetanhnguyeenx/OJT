package core;

import model.Request;
import model.Response;
import service.todo.list.TodoData;

import java.util.HashMap;
import java.util.Map;

public class SharedData {
    public static Map<Integer, Response> data = new HashMap<>();
}
