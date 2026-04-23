package CourseRegisterUI.util;

import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadedViewTest {

    @Test
    void constructor_storesViewAndController() {
        VBox view = new VBox();
        String controller = "controller";

        LoadedView<String> loadedView = new LoadedView<>(view, controller);

        assertSame(view, loadedView.view());
        assertSame(controller, loadedView.controller());
    }
}
