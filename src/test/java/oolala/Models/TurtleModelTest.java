package oolala.Models;

import javafx.scene.shape.Rectangle;
import oolala.Command.Command;
import oolala.Command.CommandForward;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TurtleModelTest {
    TurtleModel turtleModel;
    TurtleView turtleView;
    CanvasScreen canvasScreen;
    Command command;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private static final String DEFAULT_LANGUAGE = "English";
    ResourceBundle myResources;
    String turtleIcon;
    String turtleStamp;
    RunningStatus runningStatus;


    @BeforeEach
    void setUp() {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
        turtleIcon = myResources.getString("CursorIcon");
        turtleStamp = myResources.getString("SimpleLeafStamp");
        canvasScreen = new CanvasScreen(myResources);
        runningStatus = new RunningStatus();

        turtleView = new TurtleView(0, 0, canvasScreen, turtleIcon, turtleStamp, runningStatus);
    }

    @Test
    void createTurtlePath() {
    }

    @Test
    void setPosition() {
        command = new CommandForward();
        command.runCommand(turtleView, canvasScreen, );
    }

    @Test
    void rotate() {
    }

    @Test
    void putPenDown() {
    }

    @Test
    void putPenUp() {
    }

    @Test
    void updateRelativePosition() {
    }

    @Test
    void isPenDown() {
    }

    @Test
    void getPosX() {
    }

    @Test
    void getPosY() {
    }

    @Test
    void geAngle() {
    }

    @Test
    void getXMin() {
    }

    @Test
    void getXMax() {
    }

    @Test
    void getYMin() {
    }

    @Test
    void getYMax() {
    }

    @Test
    void getRelX() {
    }

    @Test
    void getRelY() {
    }
}