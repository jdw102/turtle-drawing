package oolala;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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
    private final int SIZE_WIDTH = 840;
    private final int SIZE_HEIGHT = 650;
    private final int START_WIDTH = 400;
    private final int START_HEIGHT = 500;
    private StartingView startingView;
    private final String TITLE = "Oolala";
    private final String STYLESHEET = "default.css";
    private final String DARK_MODE_STYLESHEET = "darkmode.css";
    private final String DEFAULT_RESOURCE_FOLDER = "/Properties/";

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
        EventHandler<ActionEvent> startApp = event -> {
            stage.close();
            String language = startingView.getLanguage();

            TabPane tabPane = new TabPane();
            Scene scene = new Scene(tabPane, SIZE_WIDTH, SIZE_HEIGHT);
            AppView view1 = new LogoAppView(stage, language, DEFAULT_RESOURCE_FOLDER, STYLESHEET, DARK_MODE_STYLESHEET);
            AppView view2 = new LSystemAppView(stage, language, DEFAULT_RESOURCE_FOLDER, STYLESHEET, DARK_MODE_STYLESHEET);
            Tab tab1 = new Tab("Logo", view1.setUpRootBorderPane());
            Tab tab2 = new Tab("L-System", view2.setUpRootBorderPane());
            tabPane.getTabs().add(tab1);
            tabPane.getTabs().add(tab2);

            scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(TITLE);
            stage.setResizable(false);
            stage.show();
        };

        startingView = new StartingView();
        Scene startScene = startingView.setUpScene(START_WIDTH, START_HEIGHT, startApp);
        startScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        stage.setTitle(TITLE);
        stage.setScene(startScene);
        stage.setResizable(false);
        stage.show();
    }
}
