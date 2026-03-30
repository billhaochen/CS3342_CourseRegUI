package CourseRegisterUI;

import CourseRegisterUI.models.Root;
import CourseRegisterUI.util.ExampleJSONBuilder;
import CourseRegisterUI.util.JSONDeserializer;
import CourseRegisterUI.util.MasterJSONBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

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

//        Root root = JSONDeserializer.JSONToRoot("src/main/resources/json/master_export_2026-03-30_13-24-32-630.json");
//
//        System.out.println(JSONDeserializer.getStudentByName(root, "John Doe"));

        // Only uncomment the block below to register example files
//        MasterJSONBuilder.generateExamplesAndMaster();
    }

    private void showSignInPopup(Stage owner) {
        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        javafx.scene.effect.BoxBlur blur = new javafx.scene.effect.BoxBlur(8, 8, 3);
        owner.getScene().getRoot().setEffect(blur);

        Parent signInRoot = ComponentLoader.loadSignInDialog();
        Scene dialogScene = new Scene(signInRoot);
        String cssPath = getClass().getResource("css/style.css").toExternalForm();
        dialogScene.getStylesheets().add(cssPath);

        dialog.setScene(dialogScene);
        dialog.setOnHidden(e -> owner.getScene().getRoot().setEffect(null));
        dialog.show();

    }
}
