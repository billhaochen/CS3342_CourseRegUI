package CourseRegisterUI;
import CourseRegisterUI.controllers.SidePanelController;
import CourseRegisterUI.controllers.WeeklyCalendarController;
import CourseRegisterUI.util.LoadedView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;

import java.net.URL;

public class ComponentLoader {
    public static LoadedView<SidePanelController> loadSidePanel() {
        URL resource = ComponentLoader.class.getResource("/CourseRegisterUI/SidePanel.fxml");
        System.out.println("SidePanel URL: " + resource);  // ← ADD THIS
        if (resource == null) {
            System.err.println("❌ SidePanel.fxml NOT FOUND at /CourseRegisterUI/SidePanel.fxml");
            throw new RuntimeException("File missing");
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    ComponentLoader.class.getResource("/CourseRegisterUI/SidePanel.fxml")
            );

            Parent root = loader.load();
            SidePanelController controller = loader.getController();

            return new LoadedView<>(root, controller);
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

    public static LoadedView<WeeklyCalendarController> loadWeeklyCalendar() {
        URL resource = ComponentLoader.class.getResource("/CourseRegisterUI/WeeklyCalendar.fxml");
        System.out.println("WeeklyCalendar URL: " + resource);  // ← ADD THIS
        if (resource == null) {
            System.err.println("❌ WeeklyCalendar.fxml NOT FOUND at /CourseRegisterUI/WeeklyCalendar.fxml");
            throw new RuntimeException("File missing");
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    ComponentLoader.class.getResource("/CourseRegisterUI/WeeklyCalendar.fxml")
            );

            Parent root = loader.load();
            WeeklyCalendarController controller = loader.getController();

            return new LoadedView<>(root, controller);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static FXMLLoader getWeeklyCalendarLoader() {
        URL resource = ComponentLoader.class.getResource("/CourseRegisterUI/WeeklyCalendar.fxml");
        System.out.println("WeeklyCalendar Loader URL: " + resource);
        if (resource == null) {
            System.err.println("❌ WeeklyCalendar.fxml NOT FOUND");
            throw new RuntimeException("File missing");
        }
        return new FXMLLoader(resource);
    }
}
