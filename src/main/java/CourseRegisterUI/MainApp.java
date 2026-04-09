package CourseRegisterUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LandingPage.fxml"));
        Parent root = loader.load();

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
