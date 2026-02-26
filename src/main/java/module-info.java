module CourseRegisterUI {
    requires javafx.controls;
    requires javafx.fxml;


    opens CourseRegisterUI to javafx.fxml;
    exports CourseRegisterUI;
}