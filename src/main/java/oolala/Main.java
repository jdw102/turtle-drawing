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
    private static final int SIZE_WIDTH = 700;
    private static final int SIZE_HEIGHT = 500;
    private static final Paint BACKGROUND = Color.BLACK;
    private Stage stage;
    private OolalaView view;
    private Scene scene;
    String TITLE = "Oolala";

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
        view = new OolalaView();
        scene = view.setUpScene(SIZE_WIDTH, SIZE_HEIGHT, "English");
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }
}
