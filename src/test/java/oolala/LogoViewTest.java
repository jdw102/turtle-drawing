package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import oolala.Views.AppView;
import oolala.Views.LSystemAppView;
import oolala.Views.LogoAppView;
import oolala.Views.StartingView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.DukeApplicationTest;

import javax.swing.text.View;

import static oolala.Main.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogoViewTest extends DukeApplicationTest {
    private LogoAppView logoView;
    // keep GUI components used in multiple tests
    private Button runButton;
    private Button clearTerminalButton;
    private Button clearCanvasButton;
    private Button resetTurtleButton;
    private ColorPicker canvasColorPicker;
    private ColorPicker brushColorPicker;
    private TextField thicknessTextField;
    private ComboBox<ImageView> iconChangeBox;
    private TextArea terminalText;
    private Group canvasShapes;


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
        canvasColorPicker = lookup("#CanvasColorPicker").query();
        brushColorPicker = lookup("#BrushColorPicker").query();
        thicknessTextField = lookup("#ThicknessTextField").query();
        iconChangeBox = lookup("#IconChange").query();
        canvasShapes = lookup("#CanvasShapes").query();

    }

    @Test
    void testClearTerminalText(){
        clickOn(terminalText).write("fd 100").write(KeyCode.ENTER.getChar());
        clickOn(clearTerminalButton);
        String expected = "";
        assertEquals(expected, terminalText.getText());
    }
    @Test
    void testCanvasClear() {
        int expected = 2;
        clickOn(terminalText).write("fd 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        clickOn(clearCanvasButton);
        assertEquals(expected, canvasShapes.getChildren().size());
    }
    @Test
    void changeCanvasColor() {
        Color expected = Color.RED;
        setValue(canvasColorPicker, Color.RED);
        Rectangle borderRectangle = lookup("#BorderRectangle").query();
        assertEquals(expected, borderRectangle.getFill());
    }
    @Test
    void changeBrushColor(){
        Color expected = Color.RED;
        setValue(brushColorPicker, Color.RED);
        clickOn(terminalText).write("fd 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        Line line = lookup("#Line1").query();
        assertEquals(expected, line.getStroke());
    }
    @Test
    void changeLineThickness(){
        double expected = 7;
        clickOn(thicknessTextField).press(KeyCode.BACK_SPACE).write('7');
        press(KeyCode.ENTER);
        clickOn(terminalText).write("fd 100").write(KeyCode.ENTER.getChar());
        clickOn(runButton);
        sleep(1000);
        Line line = lookup("#Line1").query();
        assertEquals(expected, line.getStrokeWidth());
    }
}
