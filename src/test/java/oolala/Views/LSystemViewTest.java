package oolala.Views;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import oolala.Views.LSystemAppView;
import oolala.Views.LogoAppView;
import oolala.Views.StartingView;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import static oolala.Main.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LSystemViewTest extends DukeApplicationTest {
    private final int SIZE_WIDTH = 840;
    private final int SIZE_HEIGHT = 650;
    private final String TITLE = "Oolala";
    private final String STYLESHEET = "default.css";
    private final String DEFAULT_RESOURCE_FOLDER = "/Properties/";

    private LSystemAppView lSystemView;
    // keep GUI components used in multiple tests
    private Button runButton;
    private Button clearTerminalButton;
    private Button clearCanvasButton;
    private Button resetTurtleButton;
    private ColorPicker canvasColorPicker;
    private ColorPicker brushColorPicker;
    private TextField thicknessTextField;
    private TextArea terminalText;
    private Group canvasShapes;
    private Slider lengthSlider;
    private Slider angleSlider;
    private Slider levelSlider;


    @Override
    public void start (Stage stage) {
        lSystemView = new LSystemAppView(stage, "English");
        Scene startScene = new Scene(lSystemView.setUpRootBorderPane(), SIZE_WIDTH, SIZE_HEIGHT);
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
        canvasColorPicker = lookup("#CanvasColorPicker").query();
        brushColorPicker = lookup("#BrushColorPicker").query();
        thicknessTextField = lookup("#ThicknessTextField").query();
        canvasShapes = lookup("#CanvasShapes").query();
        lengthSlider = lookup("#LengthSlider").query();
        levelSlider = lookup("#LevelSlider").query();
        angleSlider = lookup("#AngleSlider").query();
    }


    @Test
    void testLevelSlider() {
        levelSlider = lookup("#LevelSlider").query();
        setValue(levelSlider, 0);
        clickOn(terminalText).write("\n" + "start \tF\n" + "rule \tF F-F++F-F");
        clickOn(runButton);
        sleep(2000);
        int expectedNumShapes = 3;
        assertEquals(expectedNumShapes, canvasShapes.getChildren().size());
    }
    @Test
    void testLengthSlider() {
        setValue(levelSlider, 0);
        setValue(lengthSlider, 100);
        clickOn(terminalText).write("\n" + "start \tF\n" + "rule \tF F-F++F-F");
        ImageView turtle = lookup("#Turtle1").query();
        double expected = turtle.getY() - 100;
        clickOn(runButton);
        sleep(2000);
        assertEquals(expected, turtle.getY());
    }
    @Test
    void testAngleSlider() {
        setValue(levelSlider, 1);
        setValue(lengthSlider, 100);
        setValue(angleSlider, 135);
        clickOn(terminalText).write("\n" + "start \tF\n" + "rule \tF ++F");
        ImageView turtle = lookup("#Turtle1").query();
        double expected = turtle.getX() - 100;
        clickOn(runButton);
        sleep(2000);
        assertEquals(expected, turtle.getX());
    }

}
