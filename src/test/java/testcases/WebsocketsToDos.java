package testcases;

import Data.Stash;
import Helpers.TestHelpers;
import org.junit.jupiter.api.*;
import stepdefs.StepDefs;

@DisplayName("To Do websockets testing")
class WebsocketsToDos {
    private static StepDefs stepDefs = new StepDefs();

    @BeforeAll
    static void setUpWs() throws Exception {
        System.out.println("[BeforeEach] Setting up test environment and connecting to WebSocket");
        stepDefs.wsConnect();
        Stash.expectedId = 123456789L; // Example ID
        stepDefs.createToDo(Stash.expectedId, "Setup ToDo", false);
        System.out.println("[BeforeEach] ToDo with ID " + Stash.expectedId + " created and WebSocket connected");
    }

    @AfterAll
    static void disconnectWs() throws Exception {
        System.out.println("[AfterEach] Disconnecting from WebSocket");
        stepDefs.wsDisconnect();
        System.out.println("[AfterEach] WebSocket disconnected");
    }


    /* WS todos/  */
    @Test
    @Tag("Smoke")
    @DisplayName("Check created ToDo is returned in ws")
    void createdToDoReturnedInWs() throws Exception {
        Stash.expectedId = TestHelpers.generateRandomId();
        stepDefs.createToDo(Stash.expectedId, "Smoke autotests text", false);
        stepDefs.assertToDoIdInWsEqualExpectedId(Stash.expectedId, Stash.wsResponseMessage);
    }


}
