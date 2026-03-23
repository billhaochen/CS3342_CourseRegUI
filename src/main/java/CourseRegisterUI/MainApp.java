package CourseRegisterUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("Course Register System");
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
        showSignInPopup(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }

    private void showSignInPopup(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        javafx.scene.effect.BoxBlur blur = new javafx.scene.effect.BoxBlur(8, 8, 3);
        owner.getScene().getRoot().setEffect(blur);
        Parent signInRoot = ComponentLoader.loadSignInDialog();
        dialog.setScene(new Scene(signInRoot));

        dialog.setOnHidden(e -> owner.getScene().getRoot().setEffect(null));
        dialog.show();

    }
}
