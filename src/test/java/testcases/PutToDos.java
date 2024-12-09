package testcases;

import Data.Stash;
import Helpers.TestHelpers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import stepdefs.StepDefs;

@DisplayName("PUT ToDos API tests")
class PutToDos {
    StepDefs stepDefs = new StepDefs();

    @BeforeEach
    void setUp() {
        System.out.println("[BeforeEach] Generating expectedId");
        Stash.expectedId = TestHelpers.generateRandomId();
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
        stepDefs.createToDo(Stash.expectedId, "ToDo name for PUT requests", false);
        stepDefs.updateToDo(Stash.expectedId, newTextValue, false);
        stepDefs.getToDoList();
        stepDefs.assertTextFieldValueEqualExpected(newTextValue, Stash.expectedId);
    }

    @Tag("Smoke")
    @Tag("Regress")
    @DisplayName("Update ToDo completed value")
    @ParameterizedTest(name = "Launch test with completed value={0} and value={1}")
    @MethodSource("Data.TestParameters#completedValues")
    void updateToDoCompletedValue(boolean existedCompletedValue, boolean newCompletedValue) {
        String textValue = "ToDo name for PUT requests";
        stepDefs.createToDo(Stash.expectedId, textValue, existedCompletedValue);
        stepDefs.updateToDo(Stash.expectedId, textValue, newCompletedValue);
        stepDefs.getToDoList();
        stepDefs.assertCompletedValueEqualExpected(newCompletedValue);
    }

    @Test
    @Tag("Regress")
    @DisplayName("Update ToDo id field with valid id value")
    void updateToDoIdValues() {}

    @Test
    @Tag("Regress")
    @DisplayName("Update ToDo text field with valid text values")
    @ParameterizedTest(name = "Launch test with valid text value={0}")
    @MethodSource("Data.TestParameters#validTextValues")
    void updateToDoWithValidTextValues() {}


    @Test
    @Tag("Regress")
    @DisplayName("Update ToDo text field with invalid text values")
    void updateToDoWithInvalidTextValues() {}

    @Test
    @Tag("Regress")
    @DisplayName("Update ToDo text field with valid text length")
    void updateToDoWithValidTextLength() {}

    @Test
    @Tag("Regress")
    @Tag("Negative")
    @DisplayName("Update ToDo text field with invalid text length")
    void updateToDoWithInvalidTextLength() {}

    @Test
    @Tag("Regress")
    @Tag("Negative")
    @DisplayName("Send request to update ToDo without text field")
    void updateToDoWithoutTextField() {}

    @Test
    @Tag("Regress")
    @Tag("Negative")
    @DisplayName("Send request to update ToDo without id value")
    void updateToDoWithoutIdValue() {}

    @Test
    @Tag("Regress")
    @Tag("Negative")
    @DisplayName("Send request to update ToDo without completed value")
    void updateToDoWithoutCompletedValue() {}

    @Test
    @Tag("Regress")
    @Tag("Negative")
    @DisplayName("Send request to update ToDo with completed value in incorrect type")
    void updateToDoWithCompletedValueInIncorrectType() {}

    @Test
    @Tag("Regress")
    @Tag("Negative")
    @DisplayName("Send request to update ToDo with incorrect id type") //byte, short, int, float, double, string
    void updateToDoWithIncorrectIdType() {}

    @Test
    @Tag("Regress")
    @DisplayName("Update ToDo text field with text value that already exists in the list of ToDos")
    void updateToDoWithTextValueThatAlreadyExists() {}

    @Test
    @Tag("Regress")
    @Tag("Negative")
    @DisplayName("Send request to update ToDo without body")
    void updateToDoWithoutBody() {}

    @Test
    @Tag("Regress")
    @DisplayName("Send request to update ToDo with empty body")
    void updateToDoWithEmptyBody() {}

    @Test
    @Tag("Regress")
    @Tag("Negative")
    @DisplayName("Update ToDo with text value in incorrect type")//int, long, boolean, float
    void updateToDoWithTextValueInIncorrectType() {}

    @Test
    @Tag("Regress")
    @DisplayName("Send request to update ToDo with previously deleted id in query")
    void updateToDoWithDeletedIdInQuery() {}

    @Test
    @Tag("Regress")
    @DisplayName("Send request to update ToDo with previously deleted id in body")
    void updateToDoWithDeletedIdInBody() {}

    @Test
    @Tag("Regress")
    @DisplayName("PUT request to incorrect route")
    void postRequestToIncorrectRouteForUpdate() {}

    @Test
    @Tag("Regress")
    @DisplayName("Send request to update ToDo without id in query")
    void updateToDoRequestWithoutQuery() {}

    @Test
    @Tag("Regress")
    @DisplayName("Send request to update ToDo with id in query that doesn't match id value in body")
    void updateToDoRequestWithQueryNotMatchWithId() {}

}
