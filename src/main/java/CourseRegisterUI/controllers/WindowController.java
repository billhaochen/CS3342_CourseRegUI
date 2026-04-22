package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.Root;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WindowController {
    public static <T> T showModal(
            Window owner,
            String fxmlPath,
            String title,
            AppContext context
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource(fxmlPath));
            Parent root = loader.load();
            T controller = loader.getController();

            if (controller instanceof ContextAware aware) {
                aware.setAppContext(context);
            }

            Stage dialog = new Stage();
            dialog.initOwner(owner);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle(title);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(WindowController.class.getResource("/CourseRegisterUI/css/style.css").toExternalForm());
            dialog.setScene(scene);

            BoxBlur blur = new BoxBlur(8, 8, 3);
            owner.getScene().getRoot().setEffect(blur);
            dialog.setOnHidden(e -> owner.getScene().getRoot().setEffect(null));

            dialog.show();
            return controller;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void switchToMainView(Stage stage, AppContext context) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource("/CourseRegisterUI/Main.fxml"));
            Parent root = loader.load();
            CourseController mainController = loader.getController();
            mainController.setAppContext(context);
            if (stage.getScene() != null) {
                stage.getScene().setRoot(root);
            } else {
                Scene scene = new Scene(root, 800, 600);
                stage.setScene(scene);
            }
            SignInController controller =  WindowController.showModal(stage, "/CourseRegisterUI/SignInDialog.fxml", "Sign In" , context);
            controller.setMainController(mainController);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void showCourseInfoPopup(Window owner, Course selectedCourse) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource("/CourseRegisterUI/CourseInfo.fxml"));
            Parent root = loader.load();

            CourseInfoController controller = loader.getController();
            controller.setCourseInfo(selectedCourse);

            Stage popupStage = new Stage();
            popupStage.initOwner(owner);
            popupStage.initModality(Modality.NONE);
            popupStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            popupStage.setScene(scene);

            popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (!isNowFocused) {
                    popupStage.close();
                }
            });
            scene.getStylesheets().add(WindowController.class.getResource("/CourseRegisterUI/css/style.css").toExternalForm());
            popupStage.show();
            BoxBlur blur = new BoxBlur(8, 8, 3);
            owner.getScene().getRoot().setEffect(blur);
            popupStage.setOnHidden(e -> owner.getScene().getRoot().setEffect(null));
            popupStage.setX(owner.getX() + (owner.getWidth() - popupStage.getWidth()) / 2);
            popupStage.setY(owner.getY() + (owner.getHeight() - popupStage.getHeight()) / 2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCreateAccountPopup(Window owner, AppContext context) {
        WindowController.showModal(owner, "/CourseRegisterUI/CreateAccount.fxml", "Sign In" , context);
    }
    public static void showStudentInfoDialog(Window owner, AppContext context) {
        WindowController.showModal(
                owner,
                "/CourseRegisterUI/StudentInfo.fxml",
                "Student Information",
                context
        );
    }

    public static void requestCourseInfo(Window owner,Course course) {
        showCourseInfoPopup(owner, course);
    }
}