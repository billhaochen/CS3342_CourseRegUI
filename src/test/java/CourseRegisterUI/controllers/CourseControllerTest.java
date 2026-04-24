package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.models.SignedOut;
import CourseRegisterUI.models.Student;
import CourseRegisterUI.models.User;
import javafx.scene.control.Hyperlink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CourseControllerTest {

    private CourseController controller;

    @Mock
    private AppContext context;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CourseController();
    }

    private void setField(String fieldName, Object value) throws Exception {
        Field field = CourseController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    private Hyperlink hyperlink() throws Exception {
        Field field = CourseController.class.getDeclaredField("userNameAndId");
        field.setAccessible(true);
        return (Hyperlink) field.get(controller);
    }
}
