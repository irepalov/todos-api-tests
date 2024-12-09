package testcases;

import Data.Stash;
import Helpers.TestHelpers;
import org.junit.jupiter.api.*;
import stepdefs.StepDefs;

@DisplayName("PUT ToDos API tests")
class PutToDos {
    StepDefs stepDefs = new StepDefs();

    @BeforeEach
    void setUp() {
        System.out.println("[BeforeEach] Creating a ToDo");
        Stash.expectedId = TestHelpers.generateRandomId();
        stepDefs.createToDo(Stash.expectedId, "ToDo name for PUT requests", false);
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


    /* PUT todos/  */
    @Test
    @Tag("Smoke")
    @DisplayName("Update ToDo text field value")
    void updateToDoTextFieldValue() {
        String newTextValue = "Updated to new To Do name. smoke test";
        stepDefs.updateToDo(Stash.expectedId, newTextValue, false);
        stepDefs.getToDoList();
        stepDefs.assertTextFieldValueEqualExpected(newTextValue, Stash.expectedId);
    }


}
