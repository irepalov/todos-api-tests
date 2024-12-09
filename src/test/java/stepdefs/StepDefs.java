package stepdefs;

import Data.ToDoJson;
import Helpers.TestHelpers;
import api.Api;
import api.WebSockets;
import io.restassured.response.Response;
import todos_autotests.Props;
import Data.Stash;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static Helpers.TestHelpers.deserializeResponseBody;

public class StepDefs {
    WebSockets websockets = new WebSockets();
    private static final Logger logger = LoggerFactory.getLogger(StepDefs.class);
    private static Props props = ConfigFactory.create(Props.class);
    private Response response;

    /**
     * GET /todos
     */
    public void getToDoList() {
           response = Api.sendRequest(
                    "GET",
                   props.toDosEndpoint(),
                    null);
        System.out.println("[API] Response status code: " + response.statusCode());
        Stash.responseStatusCode = response.statusCode();
        String responseBody = response.getBody().asString();
        System.out.println("GET /todos response body: " + responseBody);
        Stash.toDosListFromResponse = deserializeResponseBody(responseBody);
    }

    /**
     * check ID is exist in the list.
     */
    public void assertIdExist(long expectedId) {
        boolean idExist = Stash.toDosListFromResponse.stream().anyMatch(t -> t.getId() == Stash.expectedId);
        Assertions.assertTrue(idExist, "[API] Expected ID " + expectedId + " is not found in the list of ToDos");
        logger.info("[API] Check expectedId exist {}", expectedId);
    }

    /**
     * check ID is exist in the list.
     */
    public void assertTextFieldValueEqualExpected(String expectedTextValue, long toDoId) {
        //find ToDo_object by ID
        ToDoJson matchedToDo = Stash.toDosListFromResponse.stream().filter(t -> t.getId() == Stash.expectedId).findFirst().orElse(null);
        Assertions.assertEquals(
                expectedTextValue,
                matchedToDo.getText(),
                "[API] ID " + Stash.expectedId + " doesn't contain expected text value; instead, it contains:  " + matchedToDo.getText()
        );
        logger.info("[API] Check actualTextValue is equal {}", expectedTextValue);
    }

    /**
     * check ID is NOT exist in the list.
     */
    public void assertIdNotExist(long expectedId) {
        boolean idExist = Stash.toDosListFromResponse.stream().anyMatch(t -> t.getId() == Stash.expectedId);
        Assertions.assertFalse(idExist, "[API] Expected ID " + expectedId + " was found in the list of ToDos");
        logger.info("[API] Check expectedId NOT exist {}", expectedId);
    }

    /**
     * check that ToDos list is not empty
     */
    public void assertResponseBodyIsNotEmpty() {
        Assertions.assertFalse(Stash.toDosListFromResponse.isEmpty(), "[API] The ToDos list is empty");
    }

    public void assertResponseStatusCodeEqualExpected(int expectedStatusCode) {
        Assertions.assertEquals(expectedStatusCode, Stash.responseStatusCode, "[API] The status code not equal expected. Expected " + expectedStatusCode + "but returned " + Stash.responseStatusCode);
    }

    /**
     * POST /todos
     */
    public void createToDo(long id, String text, boolean completed) {
        try {
            String body = TestHelpers.createTodoJson(id, text, completed);
            response = Api.sendRequest(
                    "POST",
                    props.toDosEndpoint(),
                    body);
            System.out.println("[API] Response status code: " + response.statusCode());
            if (response.statusCode() != 201) {
                throw new RuntimeException("[API] Step failed: Expected status code 201 but received " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("[API] An error occurred during the request: " + e.getMessage());
            throw e; // link to exception object
        }
    }

    /**
     * PUT /todos/
     */
    public void updateToDo(long id, String text, boolean completed) {
        try {
            String body = TestHelpers.createTodoJson(id, text, completed);
            response = Api.sendRequest(
                    "PUT",
                    props.toDosEndpoint() + "/" + id,
                    body);
            System.out.println("[API] Response status code: " + response.statusCode());
            if (response.statusCode() != 200) {
                throw new RuntimeException("[API] Step failed: Expected status code 201 but received " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("[API] An error occurred during the request: " + e.getMessage());
            throw e; // link to exception object
        }
    }
    /**
     * DELETE /todos
     */
    public void deleteToDo(long id) {
        try {
            response = Api.sendRequest(
                    "DELETE",
                    props.toDosEndpoint() + "/" + id,
                    null);
            System.out.println("[API] Response status code: " + response.statusCode());
            if (response.statusCode() != 204) {
                throw new RuntimeException("[API] Step failed: Expected status code 204 but received " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("[API] An error occurred during the request: " + e.getMessage());
            throw e; // link to exception object
        }
    }
    /**
     * Websockets connect
     */
    public void wsConnect() throws Exception {
        websockets.connect();
    }
    /**
     * Websockets disconnect
     */
    public void wsDisconnect() throws Exception {
        websockets.disconnect();
    }
    /**
     * check that ToDos id from WS message is equal to expectedId
     */
    public void assertToDoIdInWsEqualExpectedId(long expId, String wsMessage) {
       TestHelpers testHelpers = new TestHelpers();
       long idFromWsMessage = testHelpers.getIdFromWsMessage(wsMessage);
       Assertions.assertEquals(expId, idFromWsMessage, "[Assertions] 'id' field from Ws does not match expectedId");
       System.out.println("[Assertions] 'id' field from Ws matches expectedId= " + expId);
    }
}
