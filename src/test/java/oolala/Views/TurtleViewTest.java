package oolala.Views;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import oolala.Views.LogoAppView;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static oolala.Main.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurtleViewTest extends DukeApplicationTest {
    private final int SIZE_WIDTH = 840;
    private final int SIZE_HEIGHT = 650;
    private final String TITLE = "Oolala";
    private final String STYLESHEET = "default.css";
    private final String DARK_MODE_STYLESHEET = "darkmode.css";
    private final String DEFAULT_RESOURCE_FOLDER = "/Properties/";
    private LogoAppView logoView;
    // keep GUI components used in multiple tests
    private Button runButton;
    private Button clearTerminalButton;
    private Button clearCanvasButton;
    private Button resetTurtleButton;
    private ComboBox<ImageView> iconChangeBox;
    private TextArea terminalText;
    private Group canvasShapes;


    @Override
    public void start (Stage stage) {
        logoView = new LogoAppView(stage, "English", DEFAULT_RESOURCE_FOLDER, STYLESHEET, DARK_MODE_STYLESHEET);;
        Scene startScene = new Scene(logoView.setUpRootBorderPane(), SIZE_WIDTH, SIZE_HEIGHT);
        startScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        stage.setTitle(TITLE);
        stage.setScene(startScene);
        stage.setResizable(false);
        stage.show();
        runButton = lookup("#RunButton").query();
        clearTerminalButton = lookup("#ClearTextButton").query();
        terminalText = lookup("#TerminalText").query();
        clearCanvasButton = lookup("#ClearCanvasButton").query();
        resetTurtleButton = lookup("#ResetTurtleButton").query();
        iconChangeBox = lookup("#IconChange").query();
        canvasShapes = lookup("#CanvasShapes").query();

    }
    @Test
    void testTurtleForward()  {
        ImageView turtleIcon = lookup("#Turtle1").query();
        double expectedPositon = turtleIcon.getY() - 100;
        clickOn(terminalText).write("fd 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        assertEquals(expectedPositon, turtleIcon.getY());
    }
    @Test
    void testTurtleBack() {
        ImageView turtleIcon = lookup("#Turtle1").query();
        double expectedPositon = turtleIcon.getY() + 100;
        clickOn(terminalText).write("bk 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        assertEquals(expectedPositon, turtleIcon.getY());
    }
    @Test
    void testTurtleHome() {
        ImageView turtleIcon = lookup("#Turtle1").query();
        double expectedPositionY = turtleIcon.getY();
        double expectedPositionX = turtleIcon.getX();
        clickOn(terminalText).write("fd 100 home").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        assertEquals(expectedPositionX, turtleIcon.getX());
        assertEquals(expectedPositionY, turtleIcon.getY());
    }
    @Test
    void testTurtleRotate() {
        ImageView turtleIcon = lookup("#Turtle1").query();
        double expectedPositionX = turtleIcon.getX() + 100;
        clickOn(terminalText).write("rt 90 fd 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        assertEquals(expectedPositionX, turtleIcon.getX());
    }
    @Test
    void testTurtlePenUp(){
        int expectedNumShapes = 2;
        clickOn(terminalText).write("pu fd 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        assertEquals(expectedNumShapes, canvasShapes.getChildren().size());
    }
}
