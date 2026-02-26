package CourseRegisterUI;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class CourseController {

    @FXML private ScrollPane dayScroll;
    @FXML private ScrollPane timeScroll;
    @FXML private ScrollPane contentScroll;

    @FXML
    public void initialize() {
        timeScroll.vvalueProperty().bind(contentScroll.vvalueProperty());

        dayScroll.hvalueProperty().bind(contentScroll.hvalueProperty());
    }
}