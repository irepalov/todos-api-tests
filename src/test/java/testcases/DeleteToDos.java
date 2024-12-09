package testcases;

import Data.Stash;
import Helpers.TestHelpers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import stepdefs.StepDefs;

@DisplayName("DELETE ToDos API tests")
class DeleteToDos {
    StepDefs stepDefs = new StepDefs();

    @BeforeEach
    void setUp() {
        System.out.println("[BeforeEach] Creating a ToDo");
        Stash.expectedId = TestHelpers.generateRandomId();
        stepDefs.createToDo(Stash.expectedId, "ToDo name for DELETE requests", false);
        System.out.println("[BeforeEach] ToDo with ID " + Stash.expectedId + " created");
    }

    /* PUT todos/  */
    @Test
    @Tag("Smoke")
    @DisplayName("Delete ToDo")
    void updateToDoTextFieldValue() {
        stepDefs.deleteToDo(Stash.expectedId);
        stepDefs.getToDoList();
        stepDefs.assertIdNotExist(Stash.expectedId);

    }


}
