package CourseRegisterUI.controllers;

import CourseRegisterUI.util.MasterJSONBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExportButtonController {
    public Button exportBtn;
    public HBox exportHBox;

//
@FXML
private void handleExportButton() {
    try {
        System.out.println("Export button clicked");

        // Create directory with current year/month for organization
        LocalDateTime now = LocalDateTime.now();
        String yearMonth = now.format(DateTimeFormatter.ofPattern("yyyy-MM"));
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

        System.out.println(successMessage);
        showSuccessAlert("Export Successful", successMessage);

    } catch (Exception e) {
        e.printStackTrace();

        String errorMessage = "Failed to export JSON file:\n" + e.getMessage();
        showErrorAlert("Export Failed", errorMessage);
    }
}

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
