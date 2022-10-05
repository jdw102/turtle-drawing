package oolala;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import oolala.Views.AppView;
import oolala.Views.LSystemAppView;
import oolala.Views.LogoAppView;
import oolala.Views.StartingView;


/**
 * @Author Luyao Wang
 * <p>
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application {
    private static final int SIZE_WIDTH = 800;

    private static final int SIZE_HEIGHT = 600;
    private static final int START_WIDTH = 400;
    private static final int START_HEIGHT = 500;
    private Stage stage;
    private AppView view;
    private StartingView startingView;
    private Scene scene;
    String TITLE = "Oolala";
    public static final String STYLESHEET = "default.css";
    public static final String DARKMODE_STYLESHEET = "darkmode.css";
    public static final String DEFAULT_RESOURCE_FOLDER = "/Properties/";
    private String currentAppName;
    private String currentLanguage;

    /**
     * A method to test (and a joke :).
     */
    public double getVersion() {
        return 0.001;
    }

    /**
     * Start of the program.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        EventHandler<ActionEvent> startApp = event -> {
            stage.close();
            currentLanguage = startingView.getLanguage();
            currentAppName = startingView.getAppName();
            switch (currentAppName) {
                case "Logo" -> view = new LogoAppView(SIZE_WIDTH, SIZE_HEIGHT, stage, currentLanguage);
                case "LSystem" -> view = new LSystemAppView(SIZE_WIDTH, SIZE_HEIGHT, stage, currentLanguage);
            }
            scene = view.setUpScene();
            scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        };

        startingView = new StartingView();
        scene = startingView.setUpScene(START_WIDTH, START_HEIGHT, startApp);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
