package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonHelper {
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.registerModule(new JavaTimeModule());
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        defaultObjectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return  defaultObjectMapper;
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
//        String json = "{\"name\" : \"cuocdoi.net\"}";
//        try {
//            JsonNode node= JsonHelper.parse(json);
//            System.out.println(node.get("name").asText());
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        String jsonUSer = "{\"id\": 1, \"name\" : \"anhnv\", \"password\" : \"123\"}";
//        try {
//            JsonNode jn = JsonHelper.parse(jsonUSer);
//            User user = JsonHelper.fromJson(jn, User.class);
//            System.out.println(user.toString());
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        User user = new User();
//        user.setId(1);
//        user.setName("doibuon");
//        user.setPassword("123");
//
//        JsonNode j = JsonHelper.toJson(user);
//        try {
//
//            System.out.println(JsonHelper.stringgify(j));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        Todo todo = new Todo();
//        todo.setId(1);
//        todo.setTitle("doiBuonViChangConE");
//        todo.setStatus(true);
//        todo.setCreatedDate(LocalDate.now());
//
//        JsonNode node = JsonHelper.toJson(todo);
//        try {
//            System.out.println(JsonHelper.stringgify(node));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        String jsontodo = "{\"id\":1,\"title\":\"doiBuonViChangConE\",\"status\":true,\"createdDate\":[2023,4,26]}";
//        try {
//            JsonNode jtodo = JsonHelper.parse(jsontodo);
//            Todo todo1 = JsonHelper.fromJson(jtodo, Todo.class);
//            System.out.println(todo1.toString());
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        Base64.Encoder encoder = Base64.getEncoder();
//        //String s = "{\"id\" : 1, \"name\" : \"anhnv\"}";
//        String s = "{\"id\" : 1}";
//        String sEncode = encoder.encodeToString(s.getBytes());
//        System.out.println(sEncode);
//        Base64.Decoder decoder = Base64.getDecoder();
//        String sDecode = new String(decoder.decode(sEncode));
//        System.out.println(sDecode);
//
//        User user1 = new User();
//        user1.setId(1);
//        user1.setName("Anhnv");
//        JsonNode juser = JsonHelper.toJson(user1);
//        try {
//            String jStringUser = JsonHelper.stringgify(juser);
//            System.out.println(jStringUser);
//            String uEcode = encoder.encodeToString(jStringUser.getBytes());
//            System.out.println(uEcode);
//            String uDecode = new String(decoder.decode(uEcode));
//            System.out.println(uDecode);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

//        List<User> userList = new ArrayList<>();
//        User user = new User();
//        user.setId(1);
//        user.setName("nguyen van A");
//        user.setPassword("123");
//        User user1 = new User();
//        user1.setId(2);
//        user1.setName("nguyen van B");
//        user1.setPassword("1231");
//        userList.add(user);
//        userList.add(user1);
//        JsonNode jsonNode = JsonHelper.toJson(userList);
//        try {
//            String s = JsonHelper.stringgify(jsonNode);
//            System.out.println(s);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        String j = "{\n" +
                "  \"url\": \"cuoc doi\",\n" +
                "  \"method\": \"get\",\n" +
                "  \"requestHeader\": \"abcxyz\",\n" +
                "  \"payload\": null\n" +
                "}";
        try {
            JsonNode jn =  JsonHelper.parse(j);
            System.out.println(JsonHelper.parse(jn.toString()).findValue("method").asText());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
