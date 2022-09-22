package oolala;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class SceneController {

    private static final int SIZE_WIDTH = 480;
    private static final int SIZE_HEIGHT = 600;
    private static final Paint BACKGROUND = Color.WHITE;
    private Stage stage;
    private OolalaView view;
    private Scene scene;
    String TITLE = "Oolala";

    public SceneController(Stage stage) {
        this.stage = stage;
        view = new OolalaView();
        scene = view.setUpScene(SIZE_WIDTH, SIZE_HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }


}
