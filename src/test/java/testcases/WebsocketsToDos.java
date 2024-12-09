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
    void createdToDoReturnedInWs() {
        Stash.expectedId = TestHelpers.generateRandomId();
        stepDefs.createToDo(Stash.expectedId, "Smoke autotests text", false);
        stepDefs.assertToDoIdInWsEqualExpectedId(Stash.expectedId, Stash.wsResponseMessage);
    }

    @Test
    @Tag("Smoke")
    @DisplayName("Check deleted ToDo is disappeared in ws")
    void deletedToDoIsDisappearedInWs() {}

    @Test
    @Tag("Smoke")
    @DisplayName("Check updated ToDo text value is changed in ws") // now it's not changed
    void updatedToDoTextValueChangedInWs() {}

    @Test
    @Tag("Regress")
    @DisplayName("Check updated ToDo completed value is changed in ws") // now it's not changed
    void updatedToDoCompletedValueChangedInWs() {}

    @Test
    @Tag("Regress")
    @DisplayName("Check updated ToDo id value is changed in ws") // now it's not changed
    void updatedToDoIdValueChangedInWs() {}

    @Tag("Regress")
    @DisplayName("Check message about created ToDo appears within 1 sec") // depending on requirements
    void checkCreatedToDoAppearsWithinExactTime() {}

    @Tag("Regress")
    @DisplayName("Check diferent ToDO types appears depending on API requests sent") // check that ToDos type changed from new_todo to changed_todo (seems like it should work like this)
    void checkToDoTypesInWs() {}

}
