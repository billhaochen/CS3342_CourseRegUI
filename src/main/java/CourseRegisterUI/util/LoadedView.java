package CourseRegisterUI.util;

import javafx.scene.Parent;

public class LoadedView<T> {
    private final Parent view;
    private final T controller;

    public LoadedView(Parent view, T controller) {
        this.view = view;
        this.controller = controller;
    }

    public Parent view() {
        return view;
    }

    public T controller() {
        return controller;
    }
}