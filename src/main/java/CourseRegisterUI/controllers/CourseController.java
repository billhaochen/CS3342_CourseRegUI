package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.SignedOut;
import CourseRegisterUI.models.Student;
import CourseRegisterUI.models.User;
import CourseRegisterUI.util.LoadedView;
import CourseRegisterUI.util.MasterJSONBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static CourseRegisterUI.ComponentLoader.showErrorAlert;
import static CourseRegisterUI.ComponentLoader.showSuccessAlert;

public class CourseController {
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
    private AppContext context;

    private LoadedView<SidePanelController> sidePanelView;
    private LoadedView<WeeklyCalendarController> weeklyCalendarView;
//    private LoadedView<MenuBarController> menuBarView;

    @FXML
    public void initialize() {
        sidePanelView = ComponentLoader.loadSidePanel();
        courseListPane.setContent(sidePanelView.view());
        weeklyCalendarView = ComponentLoader.loadWeeklyCalendar();
        schedulePane.setCenter(weeklyCalendarView.view());
        menuBar.getMenus().addAll(ComponentLoader.loadMenuBar().getMenus());
        userNameAndId.getStyleClass().add("link-ghost");
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
//        if (menuBarView != null) {
//            menuBarView.controller().setAppContext(context);
//        }
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

    public void handleUserInfo() {
        Stage stage = (Stage) userNameAndId.getScene().getWindow();
        if (context.getCurrentUser().role() instanceof SignedOut) {
            WindowController.showModal(stage, "/CourseRegisterUI/SignInDialog.fxml", "Sign In", context);
        } else if (context.getCurrentUser().role() instanceof Student) {
            WindowController.showStudentInfoDialog(stage, this.context);
        } else {
            System.out.println("not implemented yet");
        }
    }
}