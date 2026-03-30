package CourseRegisterUI;
import CourseRegisterUI.controllers.CourseController;
import CourseRegisterUI.controllers.SignInController;
import CourseRegisterUI.models.Root;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;

import java.net.URL;

public class ComponentLoader {
    public static Parent loadSidePanel() {
        URL resource = ComponentLoader.class.getResource("/CourseRegisterUI/SidePanel.fxml");
        System.out.println("SidePanel URL: " + resource);  // ← ADD THIS
        if (resource == null) {
            System.err.println("❌ SidePanel.fxml NOT FOUND at /CourseRegisterUI/SidePanel.fxml");
            throw new RuntimeException("File missing");
        }
        try {
            return FXMLLoader.load(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MenuBar loadMenuBar() {
        URL resource = ComponentLoader.class.getResource("/CourseRegisterUI/Menu.fxml");
        System.out.println("MenuBar URL: " + resource);  // ← ADD THIS
        if (resource == null) {
            System.err.println("❌ Menubar.fxml NOT FOUND at /CourseRegisterUI/Menu.fxml");
            throw new RuntimeException("File missing");
        }
        try {
            return FXMLLoader.load(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Parent loadWeeklyCalendar() {
        URL resource = ComponentLoader.class.getResource("/CourseRegisterUI/WeeklyCalendar.fxml");
        System.out.println("WeeklyCalendar URL: " + resource);  // ← ADD THIS
        if (resource == null) {
            System.err.println("❌ WeeklyCalendar.fxml NOT FOUND at /CourseRegisterUI/WeeklyCalendar.fxml");
            throw new RuntimeException("File missing");
        }
        try {
            return FXMLLoader.load(resource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Parent loadSignInDialog(Root initData, CourseController mainController){
        URL resource = ComponentLoader.class.getResource("/CourseRegisterUI/SignInDialog.fxml");
        System.out.println("SignInDialog URL: " + resource);  // ← ADD THIS
        if (resource == null) {
            throw new RuntimeException("SignInDialog.fxml NOT FOUND");
        }
        try {
            FXMLLoader loader = new FXMLLoader(ComponentLoader.class.getResource("SignInDialog.fxml"));
            SignInController controller = new SignInController(initData);
            loader.setControllerFactory(type -> {
                if (type == SignInController.class) {
                    return controller;
                }
                try {
                    return type.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            controller.setMainController(mainController);
            return loader.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
