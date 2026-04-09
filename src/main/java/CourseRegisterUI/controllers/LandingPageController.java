package CourseRegisterUI.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LandingPageController {
    @FXML
    private VBox contentBox;

    @FXML
    public void initialize() {
        contentBox.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.2), contentBox);
        fadeIn.setToValue(1);
        fadeIn.play();
    }
    @FXML
    private void handleStudentPage(){
        slideOutAndNavigate(() -> {
            Stage stage = (Stage) contentBox.getScene().getWindow();
            WindowController.switchToMainView(stage);
        });
    }

    @FXML
    private void handleTeacherPage(){

    }
    private void slideOutAndNavigate(Runnable nextAction) {

        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.5), contentBox);
        slideOut.setByX(-800); 

        slideOut.setOnFinished(e -> nextAction.run());
        slideOut.play();
    }
}
