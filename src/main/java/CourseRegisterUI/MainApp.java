package CourseRegisterUI;

import CourseRegisterUI.controllers.CourseController;
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

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Root initialData = JSONDeserializer.JSONToRoot("src/main/resources/json/master_export_2026-03-30_13-24-32-630.json");
        CourseController mainController = new CourseController(initialData);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == CourseController.class) {
                return mainController;
            }
            try {
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 600);

        primaryStage.setTitle("Course Register System");
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();
        WindowController.showSignIn(primaryStage,  initialData, mainController);

    }
    public static void main(String[] args) {
        launch(args);

//        Root root = JSONDeserializer.JSONToRoot("src/main/resources/json/master_export_2026-03-30_13-24-32-630.json");
//
//        System.out.println(JSONDeserializer.getStudentByName(root, "John Doe"));

        // Only uncomment the block below to register example files
//        MasterJSONBuilder.generateExamplesAndMaster();
    }
}
