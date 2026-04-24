package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertSame;

class LandingPageControllerTest {

    private LandingPageController controller;

    @Mock
    private AppContext context;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        controller = new LandingPageController();
        setField("contentBox", new VBox());
    }

    @Test
    void setAppContext_storesProvidedContext() throws Exception {
        controller.setAppContext(context);

        Field field = LandingPageController.class.getDeclaredField("appContext");
        field.setAccessible(true);
        assertSame(context, field.get(controller));
    }

    private void setField(String fieldName, Object value) throws Exception {
        Field field = LandingPageController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }
}
