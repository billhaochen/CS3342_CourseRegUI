//package CourseRegisterUI.controllers;
//
//import CourseRegisterUI.AppContext;
//import CourseRegisterUI.ComponentLoader;
//import CourseRegisterUI.ContextAware;
//import CourseRegisterUI.models.Course;
//import CourseRegisterUI.models.Root;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.effect.BoxBlur;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.Window;
//
//public class WindowController {
//    private static final String CSS_PATH = "/CourseRegisterUI/css/style.css";
//
//    public static <T> T showModal(
//            Window owner,
//            String fxmlPath,
//            String title,
//            AppContext context
//    ) {
//        try {
//            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource(fxmlPath));
//            Parent root = loader.load();
//            T controller = loader.getController();
//
//            if (controller instanceof ContextAware aware) {
//                aware.setAppContext(context);
//            }
//
//            Stage dialog = new Stage();
//            dialog.initOwner(owner);
//            dialog.initModality(Modality.APPLICATION_MODAL);
//            dialog.setTitle(title);
//
//            Scene scene = new Scene(root);
//            scene.getStylesheets().add(WindowController.class.getResource("/CourseRegisterUI/css/style.css").toExternalForm());
//            dialog.setScene(scene);
//
//            BoxBlur blur = new BoxBlur(8, 8, 3);
//            owner.getScene().getRoot().setEffect(blur);
//            dialog.setOnHidden(e -> owner.getScene().getRoot().setEffect(null));
//
//            dialog.show();
//            return controller;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void switchToMainView(Stage stage, AppContext context) {
//        try {
//            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource("/CourseRegisterUI/Main.fxml"));
//            Parent root = loader.load();
//            CourseController mainController = loader.getController();
//            mainController.setAppContext(context);
//            if (stage.getScene() != null) {
//                stage.getScene().setRoot(root);
//            } else {
//                Scene scene = new Scene(root, 800, 600);
//                stage.setScene(scene);
//            }
//            SignInController controller =  WindowController.showModal(stage, "/CourseRegisterUI/SignInDialog.fxml", "Sign In" , context);
//            controller.setMainController(mainController);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public static void showCourseInfoPopup(Window owner, Course selectedCourse) {
//        try {
//            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource("/CourseRegisterUI/CourseInfo.fxml"));
//            Parent root = loader.load();
//
//            CourseInfoController controller = loader.getController();
//            controller.setCourseInfo(selectedCourse);
//
//            Stage popupStage = new Stage();
//            popupStage.initOwner(owner);
//            popupStage.initModality(Modality.NONE);
//            popupStage.initStyle(javafx.stage.StageStyle.TRANSPARENT);
//
//            Scene scene = new Scene(root);
//            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
//            popupStage.setScene(scene);
//
//            popupStage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
//                if (!isNowFocused) {
//                    popupStage.close();
//                }
//            });
//            scene.getStylesheets().add(WindowController.class.getResource("/CourseRegisterUI/css/style.css").toExternalForm());
//            popupStage.show();
//            BoxBlur blur = new BoxBlur(8, 8, 3);
//            owner.getScene().getRoot().setEffect(blur);
//            popupStage.setOnHidden(e -> owner.getScene().getRoot().setEffect(null));
//            popupStage.setX(owner.getX() + (owner.getWidth() - popupStage.getWidth()) / 2);
//            popupStage.setY(owner.getY() + (owner.getHeight() - popupStage.getHeight()) / 2);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void showCreateAccountPopup(Window owner, Stage stage, AppContext context) {
//        WindowController.showModal(stage, "/CourseRegisterUI/CreateAccount.fxml", "Sign In" , context);
//    }
//
//    public static void requestCourseInfo(Window owner,Course course) {
//        showCourseInfoPopup(owner,course);
//    }
//}

package CourseRegisterUI.controllers;

import CourseRegisterUI.AppContext;
import CourseRegisterUI.ContextAware;
import CourseRegisterUI.models.Course;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

public class WindowController {
    private static final String CSS_PATH = "/CourseRegisterUI/css/style.css";

    private record LoadedDialog<T>(Stage stage, T controller) {}

    private static <T> LoadedDialog<T> createDialog(
            Window owner,
            String fxmlPath,
            String title,
            AppContext context,
            Modality modality,
            StageStyle stageStyle,
            boolean blurOwner,
            boolean transparentScene,
            Consumer<T> controllerInitializer
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource(fxmlPath));
            Parent root = loader.load();
            T controller = loader.getController();

            if (controller instanceof ContextAware aware) {
                aware.setAppContext(context);
            }
            if (controllerInitializer != null) {
                controllerInitializer.accept(controller);
            }

            Stage stage = new Stage();
            if (owner != null) {
                stage.initOwner(owner);
            }
            stage.initModality(modality);
            stage.initStyle(stageStyle);
            stage.setTitle(title);

            Scene scene = new Scene(root);
            if (transparentScene) {
                scene.setFill(Color.TRANSPARENT);
            }

            scene.getStylesheets().add(
                    Objects.requireNonNull(
                            WindowController.class.getResource(CSS_PATH)
                    ).toExternalForm()
            );
            stage.setScene(scene);

            if (blurOwner && owner != null && owner.getScene() != null) {
                BoxBlur blur = new BoxBlur(8, 8, 3);
                owner.getScene().getRoot().setEffect(blur);
                stage.setOnHidden(e -> owner.getScene().getRoot().setEffect(null));
            }

            return new LoadedDialog<>(stage, controller);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dialog: " + fxmlPath, e);
        }
    }

    public static <T> T showModal(
            Window owner,
            String fxmlPath,
            String title,
            AppContext context
    ) {
        LoadedDialog<T> dialog = createDialog(
                owner,
                fxmlPath,
                title,
                context,
                Modality.WINDOW_MODAL,
                StageStyle.DECORATED,
                true,
                false,
                null
        );
        dialog.stage().showAndWait();
        return dialog.controller();
    }

    public static <T> T showModal(
            Window owner,
            String fxmlPath,
            String title,
            AppContext context,
            Consumer<T> controllerInitializer
    ) {
        LoadedDialog<T> dialog = createDialog(
                owner,
                fxmlPath,
                title,
                context,
                Modality.WINDOW_MODAL,
                StageStyle.DECORATED,
                true,
                false,
                controllerInitializer
        );
        dialog.stage().showAndWait();
        return dialog.controller();
    }

    public static <T> T showPopup(
            Window owner,
            String fxmlPath,
            String title,
            AppContext context,
            Consumer<T> controllerInitializer
    ) {
        LoadedDialog<T> dialog = createDialog(
                owner,
                fxmlPath,
                title,
                context,
                Modality.NONE,
                StageStyle.TRANSPARENT,
                true,
                true,
                controllerInitializer
        );

        Stage stage = dialog.stage();
        stage.focusedProperty().addListener((obs, oldValue, focused) -> {
            if (!focused) {
                stage.close();
            }
        });

        stage.show();
        stage.setX(owner.getX() + (owner.getWidth() - stage.getWidth()) / 2);
        stage.setY(owner.getY() + (owner.getHeight() - stage.getHeight()) / 2);
        return dialog.controller();
    }

    public static void switchToMainView(Stage stage, AppContext context) {
        try {
            FXMLLoader loader = new FXMLLoader(WindowController.class.getResource("/CourseRegisterUI/Main.fxml"));
            Parent root = loader.load();
            CourseController mainController = loader.getController();
            mainController.setAppContext(context);

            if (stage.getScene() != null) {
                stage.getScene().setRoot(root);
            } else {
                Scene scene = new Scene(root, 800, 600);
                stage.setScene(scene);
            }

            Platform.runLater(() -> WindowController.showModal(
                    stage,
                    "/CourseRegisterUI/SignInDialog.fxml",
                    "Sign In",
                    context,
                    (SignInController c) -> c.setMainController(mainController)
            ));
        } catch (Exception e) {
            throw new RuntimeException("Failed to switch to main view", e);
        }
    }

    public static void showCourseInfoPopup(Window owner, Course selectedCourse) {
        WindowController.showPopup(
                owner,
                "/CourseRegisterUI/CourseInfo.fxml",
                "Course Info",
                null,
                (CourseInfoController controller) -> controller.setCourseInfo(selectedCourse)
        );
    }

    public static void showCreateAccountPopup(Window owner, AppContext context) {
        WindowController.showModal(
                owner,
                "/CourseRegisterUI/CreateAccount.fxml",
                "Create Account",
                context
        );
    }

    public static void requestCourseInfo(Window owner, Course course) {
        showCourseInfoPopup(owner, course);
    }
}