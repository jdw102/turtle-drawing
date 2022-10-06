package oolala;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import oolala.Views.LogoAppView;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static oolala.Main.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurtleViewTest extends DukeApplicationTest {
    private LogoAppView logoView;
    // keep GUI components used in multiple tests
    private Button runButton;
    private Button clearTerminalButton;
    private Button clearCanvasButton;
    private Button resetTurtleButton;
    private ComboBox<ImageView> iconChangeBox;
    private TextArea terminalText;


    @Override
    public void start (Stage stage) {
        logoView = new LogoAppView(stage, "English");;
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
    }
    @Test
    void testTurtleForward()  {
        ImageView turtleIcon = lookup("#Turtle1").query();
        Double expectedPositon = turtleIcon.getY() - 100;
        clickOn(terminalText).write("fd 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        assertEquals(expectedPositon, turtleIcon.getY());
    }
    @Test
    void testTurtleBack() {
        ImageView turtleIcon = lookup("#Turtle1").query();
        Double expectedPositon = turtleIcon.getY() + 100;
        clickOn(terminalText).write("bk 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        assertEquals(expectedPositon, turtleIcon.getY());
    }
}
