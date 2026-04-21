module CourseRegisterUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.controlsfx.controls;


    opens CourseRegisterUI to javafx.fxml;
    opens CourseRegisterUI.controllers to javafx.fxml;

    opens CourseRegisterUI.models to com.fasterxml.jackson.databind;


    exports CourseRegisterUI;
    exports CourseRegisterUI.models;
    exports CourseRegisterUI.controllers;
    exports CourseRegisterUI.util;
}