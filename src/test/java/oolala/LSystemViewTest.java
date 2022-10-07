package oolala;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import oolala.Views.LogoAppView;
import util.DukeApplicationTest;

import static oolala.Main.*;

public class LSystemViewTest extends DukeApplicationTest {
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
}
