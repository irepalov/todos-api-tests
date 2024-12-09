package testcases;

import Data.Stash;
import Helpers.TestHelpers;
import org.junit.jupiter.api.*;
import stepdefs.StepDefs;

@DisplayName("GET ToDos API tests")
class GetToDos {
    StepDefs stepDefs = new StepDefs();

    @BeforeEach
    void setUp() {
        System.out.println("[BeforeEach] Creating a ToDo");
        Stash.expectedId = TestHelpers.generateRandomId();
        stepDefs.createToDo(Stash.expectedId, "ToDo name for GET requests", false);
        System.out.println("[BeforeEach] ToDo with ID " + Stash.expectedId + " created");
    }

    @AfterEach
    void cleanUp() {
        System.out.println("[AfterEach] deleting created ToDo items");
        if (Stash.expectedId != 0) {
            stepDefs.deleteToDo(Stash.expectedId);
            System.out.println("[AfterEach] ToDo with ID " + Stash.expectedId + " deleted");
        } else {
            System.out.println("[AfterEach] ToDo ID is null");
        }
    }

    /* GET todos/  */
    @Test
    @Tag("Smoke")
    @DisplayName("Get ToDos list")
    void getToDosList() {
        stepDefs.getToDoList();
        stepDefs.assertResponseStatusCodeEqualExpected(200);
        stepDefs.assertResponseBodyIsNotEmpty();
    }

    @Test
    @Tag("Smoke")
    @DisplayName("Verify that the offset query is functioning correctly") // e.g. /todos?offset=8
    void getToDosListCheckOffsetWorksCorrectly() {}

    @Test
    @Tag("Smoke")
    @DisplayName("Verify that the limit query is functioning correctly") // e.g. /todos?limit=5
    void getToDosListCheckLimitWorksCorrectly() {}

    @Test
    @Tag("Smoke")
    @DisplayName("Verify that the limit and offset queries is functioning correctly")// e.g. /todos?limit=5&offset=10
    void getToDosListCheckLimitAndOffsetWorksCorrectly() {}

    @Test
    @Tag("Smoke")
    @DisplayName("Verify that the offset query is functioning correctly when only one ToDos in the list")
    void getToDosListCheckOffsetWhenOneToDoExist() {}

    @Test
    @Tag("Regress")
    @DisplayName("Send GET ToDos request with offset value 0")
    void getToDosListOffsetZero() {}

    @Test
    @Tag("Regress")
    @DisplayName("Send GET ToDos request with limit value 0")
    void getToDosListLimitZero() {}

    @Test
    @Tag("Regress")
    @DisplayName("Get ToDos list without authentication")// it is working now without authorization. What the expected result?
    void getToDosListWithoutAuthentication() {}

    @Test
    @DisplayName("Send GET ToDos request to invalid route")
    void sendGetToDosRequestToInvalidRoute() {}

}
