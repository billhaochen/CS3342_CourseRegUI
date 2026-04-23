package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.models.RootUserType;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LandingPageController {
    @FXML
    private VBox contentBox;
    private AppContext context;

    public void setAppContext(AppContext appContext) {
        this.context = appContext;
    }

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
            context.setRootUserType(RootUserType.STUDENT);
            Stage stage = (Stage) contentBox.getScene().getWindow();
            WindowController.switchToStudentMainView(stage, context);
        });
    }

    @FXML
    private void handleTeacherPage(){
        slideOutAndNavigate(() -> {
            context.setRootUserType(RootUserType.ADMIN);
            Stage stage = (Stage) contentBox.getScene().getWindow();
            WindowController.switchToAdminMainView(stage, context);
        });
    }
    private void slideOutAndNavigate(Runnable nextAction) {

        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.5), contentBox);
        slideOut.setByX(-800); 

        slideOut.setOnFinished(e -> nextAction.run());
        slideOut.play();
    }
}
