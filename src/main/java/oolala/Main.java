package oolala;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * @Author Luyao Wang
 * Feel free to completely change this code or delete it entirely.
 */
public class Main extends Application{
    private static final int SIZE_WIDTH = 800;
    private static final int SIZE_HEIGHT = 600;
    private static final Paint BACKGROUND = Color.BLACK;
    private Stage stage;
    private OolalaGame view;
    private Scene scene;
    String TITLE = "Oolala";
    public static final String STYLESHEET = "default.css";
    public static final String DEFAULT_RESOURCE_FOLDER = "/Properties/";

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
        view = new OolalaGame();
        scene = view.setUpScene(SIZE_WIDTH, SIZE_HEIGHT,  stage);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
