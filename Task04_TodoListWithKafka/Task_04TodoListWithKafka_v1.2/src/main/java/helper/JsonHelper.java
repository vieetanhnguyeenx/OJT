package helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import model.Response;

public class JsonHelper {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.registerModule(new JavaTimeModule());
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        defaultObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return defaultObjectMapper;
    }


    public static JsonNode parse(String src) throws JsonProcessingException {
        return objectMapper.readTree(src);
    }

    public static <A> A fromJson(JsonNode node, Class<A> aClass) throws JsonProcessingException {
        return objectMapper.treeToValue(node, aClass);
    }

    public static JsonNode toJson(Object a) {
        return objectMapper.valueToTree(a);
    }

    public static String stringgify(JsonNode jnode) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        return objectWriter.writeValueAsString(jnode);
    }

    public static String prettyString(JsonNode jnode) throws JsonProcessingException {
        ObjectWriter objectWriter = objectMapper.writer();
        objectWriter = objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        return objectWriter.writeValueAsString(jnode);
    }

    public static void main(String[] args) {
        Gson gson = new Gson();
        String json = "{\"url\":\"/login\",\"method\":\"post\",\"statusCode\":\"302 Found\",\"newUrl\":\"http://localhost:8888/todolist?token=eyJpZCI6MSwibmFtZSI6ImFuaG52IiwicGFzc3dvcmQiOm51bGx9\",\"htmlResponse\":null,\"jsonData\":null,\"token\":null,\"socketId\":4}";
        Response response = gson.fromJson(json, Response.class);
        System.out.println(response);
    }
}
