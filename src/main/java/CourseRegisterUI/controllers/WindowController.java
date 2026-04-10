package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.models.Root;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WindowController {
    private static final String CSS_PATH = "/CourseRegisterUI/css/style.css";
    public static void openModal(Window owner, Parent root, String title) {
        //setup window
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(title);
        //setup scene
        Scene scene = new Scene(root);
        String css = WindowController.class.getResource(CSS_PATH).toExternalForm();
        scene.getStylesheets().add(css);
        dialog.setScene(scene);
        //blur
        BoxBlur blur = new BoxBlur(8, 8, 3);
        owner.getScene().getRoot().setEffect(blur);
        dialog.setOnHidden(e -> owner.getScene().getRoot().setEffect(null));

        dialog.show();
    }
    public static void showSignIn(Window owner, AppContext context, CourseController mainController) {
        Parent root = ComponentLoader.loadSignInDialog(context, mainController);
        openModal(owner, root, "Sign In");
    }
    public static void showAddCourse(Window owner, AppContext context) {
        Parent root = ComponentLoader.loadAddCourseDialog(context);
        openModal(owner, root, "Add New Course");
    }
    public static void switchToMainView(Stage stage, AppContext context) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource("/CourseRegisterUI/Main.fxml"));
            Parent root = loader.load();
            CourseController mainController = loader.getController();
            if (stage.getScene() != null) {
                stage.getScene().setRoot(root);
            } else {
                Scene scene = new Scene(root, 800, 600);
                stage.setScene(scene);
            }
            WindowController.showSignIn(stage, context, mainController);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
