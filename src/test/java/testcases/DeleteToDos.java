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

    /* DELETE todos/  */
    @Test
    @Tag("Smoke")
    @DisplayName("Delete ToDo")
    void updateToDoTextFieldValue() {
        stepDefs.deleteToDo(Stash.expectedId);
        stepDefs.getToDoList();
        stepDefs.assertIdNotExist(Stash.expectedId);
    }

    @Test
    @Tag("Regress")
    @DisplayName("Delete ToDo by previously deleted id")
    void deleteToDoByDeletedId() {}

    @Test
    @Tag("Regress")
    @DisplayName("Delete ToDo with non-existing id")
    void deleteToDoWithNonExistingId() {}

    @Test
    @Tag("Regress")
    @DisplayName("Delete ToDo without id value in query")
    void deleteToDoWithoutId() {}

    @Test
    @Tag("Regress")
    @DisplayName("Sen request to delete ToDo with invalid query parameter") //e.g. string
    void deleteToDoWithInvalidQueryParameter() {}

    @Test
    @Tag("Regress")
    @DisplayName("Delete ToDo without authentication")
    void deleteToDoWithoutAuth() {}

    @Test
    @Tag("Regress")
    @DisplayName("Send request to invalid route")
    void sendDeleteRequestToInvalidRoute() {}

}
