package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.*;
import CourseRegisterUI.util.LoadedView;
import CourseRegisterUI.util.MasterJSONBuilder;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import static CourseRegisterUI.ComponentLoader.showErrorAlert;
import static CourseRegisterUI.ComponentLoader.showSuccessAlert;

public class CourseController implements ContextAware, MainController {
    @FXML
    private Button exportButton;
    @FXML
    private ScrollPane courseListPane;
    @FXML
    private BorderPane schedulePane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Circle profilePicture;
    @FXML
    private Hyperlink userNameAndId;
    @FXML
    private Button adminBackButton;
    @FXML
    private VBox contentBox;
    private AppContext context;

    private LoadedView<SidePanelController> sidePanelView;
    private LoadedView<WeeklyCalendarController> weeklyCalendarView;

    @FXML
    public void initialize() {
        sidePanelView = ComponentLoader.loadSidePanel();
        courseListPane.setContent(sidePanelView.view());
        weeklyCalendarView = ComponentLoader.loadWeeklyCalendar();
        schedulePane.setCenter(weeklyCalendarView.view());
        menuBar.getMenus().addAll(ComponentLoader.loadMenuBar().getMenus());
        userNameAndId.getStyleClass().add("link-ghost");
        adminBackButton.setVisible(false);
    }

    public void setAppContext(AppContext appContext) {
        this.context = appContext;

        if (sidePanelView != null) {
            sidePanelView.controller().setAppContext(context);
        }
        if (weeklyCalendarView != null) {
            weeklyCalendarView.controller().setAppContext(context);
            weeklyCalendarView.controller().renderCourses();
        }

        context.currentUserProperty().addListener((obs, oldUser, newUser) -> {
            if (newUser == null) {
                userNameAndId.setText("Not Signed In");
            } else {
                userNameAndId.setText(newUser.name() + " | " + newUser.getID());
            }
        });

        updateUserInfo();

        if (context.getRootUserType().equals(RootUserType.ADMIN)) {
            adminBackButton.setVisible(true);
        }
    }

    @FXML
    public void updateUserInfo() {
        User curr_user = context.getCurrentUser();
        userNameAndId.setText(curr_user.name() + " | " + curr_user.getID());
    }

    @FXML
    public void handleExportButton() {
        if (context.getCurrentUser().role() instanceof SignedOut) {
            String errorMessage = "Failed to export JSON file: Must be Signed In";
            showErrorAlert(errorMessage);
        } else {
            try {
//                MasterJSONBuilder.writeLocalToMaster(MasterJSONBuilder.buildSampleMaster());
//                MasterJSONBuilder.generateExamplesAndMaster();
                String result = MasterJSONBuilder.writeLocalToMaster(context.exportContext());
                showSuccessAlert(result);

            } catch (Exception e) {
                e.printStackTrace();

                String errorMessage = "Failed to export JSON file:\n" + e.getMessage();
                showErrorAlert(errorMessage);
            }
        }
    }

    @FXML
    public void handleUserInfo() {
        Stage stage = (Stage) userNameAndId.getScene().getWindow();
        if (context.getCurrentUser().role() instanceof SignedOut) {
            WindowController.showModal(stage, "/CourseRegisterUI/SignInDialog.fxml", "Sign In", context);
        } else if (context.getCurrentUser().role() instanceof Student) {
            WindowController.showStudentInfoDialog(stage, this.context);
        } else {
            WindowController.showAdminInfoDialog(stage, this.context);
        }
    }

    private void slideOutAndNavigate(Runnable nextAction) {

        TranslateTransition slideOut = new TranslateTransition(Duration.seconds(0.5), contentBox);
        slideOut.setByX(-800);

        slideOut.setOnFinished(e -> nextAction.run());
        slideOut.play();
    }

    @FXML
    public void handleBackButton() {
        slideOutAndNavigate(() -> {
            Stage stage = (Stage) contentBox.getScene().getWindow();
            WindowController.switchToAdminMainView(stage, context);
        });
    }
}