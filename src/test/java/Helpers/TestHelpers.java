package Helpers;

import Data.ToDoJson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;

import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestHelpers {

    public static Long generateRandomId() {
        long generatedId = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
        System.out.println("Generated ID is: " + generatedId);
        return generatedId;
    }

    /**
     * Getting credentials
     */
    public static String getCredentials() { //Created in a separate class to enable testing of API methods with negative test cases to get 401
        String username = "admin";
        String password = "admin";
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        return basicAuth;
    }

    /**
     * Create ToDos body
     */
    public static String createTodoJson(long id, String text, boolean completed) {
        try {
            ToDoJson todoRequest = new ToDoJson(id, text, completed);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(todoRequest);
        } catch (Exception e) {
            throw new RuntimeException("Error during generating JSON body", e);
        }
    }

    /**
     * Deserialize ToDos response body
     */

    public static List<ToDoJson> deserializeResponseBody(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, new TypeReference<List<ToDoJson>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing JSON response to TodoRequest list", e);
        }
    }

    public boolean checkMessageForIdAndText(long expectedId, String expectedText, String messageJson) {
        System.out.println("[WebSocketMessageChecker] checkMessageForIdAndText called with expectedId=" + expectedId + ", expectedText=" + expectedText);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(messageJson);

            JsonNode idNode = root.get("id");
            JsonNode textNode = root.get("text");

            if (idNode != null && idNode.isLong() && textNode != null && textNode.isTextual()) {
                long messageId = idNode.asLong();
                String msgText = textNode.asText();
                boolean result = (messageId == expectedId && msgText.equals(expectedText));
                System.out.println("[WebSocketMessageChecker] checkMessageForIdAndText found messageId=" + messageId + ", msgText=" + msgText + ", result=" + result);
                return result;
            } else {
                System.out.println("[WebSocketMessageChecker] checkMessageForIdAndText 'id' or 'text' not found or invalid.");
            }
        } catch (Exception e) {
            System.out.println("[WebSocketMessageChecker] checkMessageForIdAndText error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Getting 'id' from wsMessage
     */
    public long getIdFromWsMessage(String wsMsg) {
        System.out.println("[WebSockets] checking ws message started");

        // If wsMsg is null, it means no message was received or not set
        Assertions.assertNotNull(wsMsg, "[WebSockets] wsMessage is null, no message received");

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(wsMsg);
            JsonNode dataNode = root.get("data");
            JsonNode idNode = (dataNode != null) ? dataNode.get("id") : null; //if dataNode null then "id" null
            Assertions.assertNotNull(idNode, "[idNode parser] 'id' field is missing in the message");
            return idNode.asLong();

        } catch (Exception e) {
            System.out.println("[WebSockets] checkMessageIdInWsMessage error: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalStateException("[WebSockets] checkMessageIdInWsMessage: Failed to parse message JSON");
        }
    }
}
