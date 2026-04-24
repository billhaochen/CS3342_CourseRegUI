package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.models.SignedOut;
import CourseRegisterUI.models.Student;
import CourseRegisterUI.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SignInControllerTest {

    private SignInController controller;

    @Mock
    private AppContext context;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new SignInController();
        setField(controller, "context", context);
    }

    @Test
    void setMainController_assignsMainControllerReference() throws Exception {
        CourseController mainController = mock(CourseController.class);

        controller.setMainController(mainController);

        Object stored = getField(controller, "mainController");
        assertSame(mainController, stored);
    }

    @Test
    void setAppContext_assignsContextReference() throws Exception {
        AppContext newContext = mock(AppContext.class);

        controller.setAppContext(newContext);

        Object stored = getField(controller, "context");
        assertSame(newContext, stored);
    }

    @Test
    void studentFactoryCreate_buildsRoleValuesUsedBySignInLogic() {
        Student student = Student.factoryCreate(
                "Jane Doe",
                "Doe",
                "Jane",
                "12345678",
                "jd123",
                "secret"
        );
        User user = new User(student.student_id(), student.name(), student);

        assertEquals("12345678", user.getID());
        assertEquals("secret", user.getPassword());
        assertEquals("jd123", user.getEID());
    }

    @Test
    void signedOutUser_exposesEmptyCredentialValues() {
        User signedOut = new User("", "Guest", new SignedOut());

        assertEquals("", signedOut.getID());
        assertEquals("", signedOut.getPassword());
        assertEquals("", signedOut.getEID());
    }

    private boolean invokeBoolean(String methodName, Class<?>[] parameterTypes, Object... args) throws Exception {
        Method method = SignInController.class.getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return (boolean) method.invoke(controller, args);
    }

    private void invokeVoid(String methodName) throws Exception {
        Method method = SignInController.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        method.invoke(controller);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private Object getField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }
}
