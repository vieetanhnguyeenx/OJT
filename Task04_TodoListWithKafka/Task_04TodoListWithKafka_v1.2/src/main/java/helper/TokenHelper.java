package helper;

import com.fasterxml.jackson.databind.JsonNode;
import model.User;

import java.util.Base64;

public class TokenHelper {
    private static Base64.Decoder decoder = Base64.getDecoder();
    private static Base64.Encoder encoder = Base64.getEncoder();

    public static User tokenToUser(String token) {
        JsonNode jsonNode = null;
        try {
            String tokenDecoded = new String(decoder.decode(token));
            jsonNode = JsonHelper.parse(tokenDecoded);
            return JsonHelper.fromJson(jsonNode, User.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static String userToToken(User u) {
        try {
            JsonNode jsonNode = JsonHelper.toJson(u);
            String jsonString = JsonHelper.stringgify(jsonNode);
            return encoder.encodeToString(jsonString.getBytes());
        } catch (Exception e) {
            return null;
        }

    }
}
