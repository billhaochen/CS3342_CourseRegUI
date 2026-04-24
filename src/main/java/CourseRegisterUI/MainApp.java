package CourseRegisterUI;

import CourseRegisterUI.controllers.LandingPageController;
import CourseRegisterUI.models.Course;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.models.User;
import CourseRegisterUI.util.ExampleJSONBuilder;
import CourseRegisterUI.util.JSONDeserializer;
import CourseRegisterUI.util.MasterJSONBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppContext context = new AppContext();
        context.loadInitialData();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
        Parent root = loader.load();

        LandingPageController controller = loader.getController();
        controller.setAppContext(context);
        Scene scene = new Scene(root);

        primaryStage.setTitle("Course Register System");
        scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }

}
