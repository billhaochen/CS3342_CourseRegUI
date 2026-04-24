package CourseRegisterUI.controllers;

import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateAccountControllerTest {

    private CreateAccountController controller;

    @BeforeEach
    void setUp() {
        controller = new CreateAccountController();
    }

    @Test
    void isBlank_returnsTrue_forNullAndWhitespace() throws Exception {
        assertTrue(invokeBoolean("isBlank", new Class[]{String.class}, (Object) null));
        assertTrue(invokeBoolean("isBlank", new Class[]{String.class}, "   "));
        assertFalse(invokeBoolean("isBlank", new Class[]{String.class}, "abc"));
    }

    @Test
    void allFilled_returnsTrue_onlyWhenAllObjectsAreNonNull() throws Exception {
        assertTrue(invokeBoolean("allFilled", new Class[]{Object[].class}, (Object) new Object[]{"a", 1, List.of()}));
        assertFalse(invokeBoolean("allFilled", new Class[]{Object[].class}, (Object) new Object[]{"a", null, 1}));
    }

    @Test
    void allNonBlank_returnsTrue_onlyWhenEveryValueHasText() throws Exception {
        assertTrue(invokeBoolean("allNonBlank", new Class[]{String[].class}, (Object) new String[]{"a", "b", "c"}));
        assertFalse(invokeBoolean("allNonBlank", new Class[]{String[].class}, (Object) new String[]{"a", " ", "c"}));
        assertFalse(invokeBoolean("allNonBlank", new Class[]{String[].class}, (Object) new String[]{"a", null, "c"}));
    }

    @Test
    void allNonNull_returnsTrue_onlyWhenEveryObjectIsNonNull() throws Exception {
        assertTrue(invokeBoolean("allNonNull", new Class[]{Object[].class}, (Object) new Object[]{"a", 1, Boolean.TRUE}));
        assertFalse(invokeBoolean("allNonNull", new Class[]{Object[].class}, (Object) new Object[]{"a", null}));
    }

    @Test
    void isNonBlank_returnsExpectedValue() throws Exception {
        assertTrue(invokeBoolean("isNonBlank", new Class[]{String.class}, "hello"));
        assertFalse(invokeBoolean("isNonBlank", new Class[]{String.class}, ""));
        assertFalse(invokeBoolean("isNonBlank", new Class[]{String.class}, "   "));
        assertFalse(invokeBoolean("isNonBlank", new Class[]{String.class}, (Object) null));
    }

    @Test
    void isNonNull_returnsExpectedValue() throws Exception {
        assertTrue(invokeBoolean("isNonNull", new Class[]{Object.class}, "value"));
        assertFalse(invokeBoolean("isNonNull", new Class[]{Object.class}, (Object) null));
    }

//    @Test
//    void isMinimumStudentFormValid_returnsTrue_whenRequiredFieldsFilled() throws Exception {
//        textField("nameField").setText("Jane Doe");
//        textField("passwordField").setText("secret");
//        textField("studentIdField").setText("12345678");
//
//        boolean result = invokeBoolean("isMinimumStudentFormValid", new Class[]{});
//
//        assertTrue(result);
//    }
//
//    @Test
//    void isMinimumStudentFormValid_returnsFalse_whenAnyRequiredFieldMissing() throws Exception {
//        textField("nameField").setText("Jane Doe");
//        textField("passwordField").setText("");
//        textField("studentIdField").setText("12345678");
//
//        boolean result = invokeBoolean("isMinimumStudentFormValid", new Class[]{});
//
//        assertFalse(result);
//    }
//
//    @Test
//    void validateForm_enablesSubmit_whenRequiredFieldsPresent() throws Exception {
//        textField("nameField").setText("Jane Doe");
//        textField("passwordField").setText("secret");
//        textField("studentIdField").setText("12345678");
//
//        invokeVoid("validateForm");
//
//        assertFalse(button("submitBtn").isDisable());
//    }
//
//    @Test
//    void validateForm_disablesSubmit_whenRequiredFieldBlank() throws Exception {
//        textField("nameField").setText("Jane Doe");
//        textField("passwordField").setText("secret");
//        textField("studentIdField").setText(" ");
//
//        invokeVoid("validateForm");
//
//        assertTrue(button("submitBtn").isDisable());
//    }

    private boolean invokeBoolean(String methodName, Class<?>[] parameterTypes, Object... args) throws Exception {
        Method method = CreateAccountController.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (boolean) method.invoke(controller, args);
    }

    private void invokeVoid(String methodName) throws Exception {
        Method method = CreateAccountController.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(controller);
    }

    private void setField(String fieldName, Object value) throws Exception {
        Field field = CreateAccountController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    private TextField textField(String fieldName) throws Exception {
        Field field = CreateAccountController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (TextField) field.get(controller);
    }

    private javafx.scene.control.Button button(String fieldName) throws Exception {
        Field field = CreateAccountController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return (javafx.scene.control.Button) field.get(controller);
    }
}
