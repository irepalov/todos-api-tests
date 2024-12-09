package testcases;

import Data.Stash;
import Helpers.TestHelpers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
    @DisplayName("Get ToDos list")
    void getToDosList() {
        stepDefs.getToDoList();
        stepDefs.assertResponseStatusCodeEqualExpected(200);
        stepDefs.assertResponseBodyIsNotEmpty();
    }


}
