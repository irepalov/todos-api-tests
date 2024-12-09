package testcases;

import Helpers.TestHelpers;
import Data.Stash;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import stepdefs.StepDefs;

@DisplayName("POST ToDos API tests")
class PostToDos {
    StepDefs stepDefs = new StepDefs();

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

    /* POST todos/  */
    @Test
    @Tag("Smoke")
    @DisplayName("Create ToDos with valid values")
    void createToDosWithValidValues() {
        Stash.expectedId = TestHelpers.generateRandomId();
        stepDefs.createToDo(Stash.expectedId, "Smoke autotests text", false);
        stepDefs.getToDoList();
        stepDefs.assertIdExist(Stash.expectedId);
    }

    @Tag("Smoke")
    @DisplayName("Create ToDos with valid values")
    @ParameterizedTest(name = "Launch test with valid text value={0}")
    @MethodSource("Data.TestParameters#validTextValues")
    void createToDosWithValidTextValues(String text) {
        Stash.expectedId = TestHelpers.generateRandomId();
        stepDefs.createToDo(Stash.expectedId, text, false);
        stepDefs.getToDoList();
        stepDefs.assertIdExist(Stash.expectedId);
    }

    @Test
    @Tag("Smoke")
    @Tag("Regress")
    @DisplayName("Create ToDos with different completed: values")
    @ParameterizedTest(name = "Launch test with completed value={0}")
    @MethodSource("Data.TestParameters#completedValues")
    void createToDosWithDifferentCompletedValues() {}

}
