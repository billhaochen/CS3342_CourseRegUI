package CourseRegisterUI.controllers;

import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.models.Root;
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
    public static void showSignIn(Window owner, Root initialData, CourseController mainController) {
        Parent root = ComponentLoader.loadSignInDialog(initialData, mainController);
        openModal(owner, root, "Sign In");
    }
    public static void showAddCourse(Window owner) {
        Parent root = ComponentLoader.loadAddCourseDialog();
        openModal(owner, root, "Add New Course");
    }
}
