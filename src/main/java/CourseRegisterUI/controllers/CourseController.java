package CourseRegisterUI.controllers;

import CourseRegisterUI.ComponentLoader;
import CourseRegisterUI.models.Root;
import CourseRegisterUI.util.MasterJSONBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CourseController {
    @FXML private Button exportButton;
    @FXML private VBox courseListPane;
    @FXML private BorderPane schedulePane;
    @FXML private MenuBar menuBar;
    @FXML private Circle profilePicture;
    @FXML private Label userNameAndId;
    private Root root;

    public CourseController(Root initialData) {
        this.root = initialData;
    }
    public CourseController() {
    }
    @FXML
    public void initialize() {
        courseListPane.getChildren().setAll(ComponentLoader.loadSidePanel());
        schedulePane.setCenter(ComponentLoader.loadWeeklyCalendar());
        menuBar.getMenus().addAll(ComponentLoader.loadMenuBar().getMenus());
        userNameAndId.setText("Not Signed In");
    }

    @FXML
    public void updateUserInfo(String full_name, String id) {
        userNameAndId.setText(full_name + " | " + id);
    }

    @FXML
    public void handleExportButton() {
        try {

            // Create directory with current year/month for organization
            LocalDateTime now = LocalDateTime.now();
            File jsonDir = new File("src/main/resources/json/");

            // Create directory if it doesn't exist
            if (!jsonDir.exists()) {
                jsonDir.mkdirs();
            }

            // Generate detailed timestamp filename
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-SSS");
            String timestamp = now.format(formatter);
            String filename = "master_export_" + timestamp + ".json";

            File outputFile = new File(jsonDir, filename);

            // Show a progress indicator (optional)
            // You could show a loading dialog here if export takes time

            MasterJSONBuilder.writeMasterToFile(outputFile);

            // Success message with file info
            String successMessage = String.format(
                    "File exported successfully!\n\n" +
                            "Filename: %s\n" +
                            "Location: %s\n" +
                            "Size: %d bytes\n" +
                            "Date: %s",
                    filename,
                    outputFile.getAbsolutePath(),
                    outputFile.length(),
                    now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );

            showSuccessAlert(successMessage);

        } catch (Exception e) {
            e.printStackTrace();

            String errorMessage = "Failed to export JSON file:\n" + e.getMessage();
            showErrorAlert(errorMessage);
        }
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export Successful");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Export Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}