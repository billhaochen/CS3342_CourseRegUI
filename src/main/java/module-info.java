module CourseRegisterUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens CourseRegisterUI to javafx.fxml;
    opens CourseRegisterUI.controllers to javafx.fxml;

    opens CourseRegisterUI.models to com.fasterxml.jackson.databind;


    exports CourseRegisterUI;
}