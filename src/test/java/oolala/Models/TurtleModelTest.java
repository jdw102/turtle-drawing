package oolala.Models;

import javafx.animation.SequentialTransition;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import oolala.Command.Command;
import oolala.Command.CommandForward;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class TurtleModelTest extends DukeApplicationTest {
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private static final String DEFAULT_LANGUAGE = "English";
    TurtleModel turtleModel;
    TurtleView turtleView;
    CanvasScreen canvasScreen;
    Command command;
    ResourceBundle myResources;
    String turtleIcon;
    String turtleStamp;
    RunningStatus runningStatus;
    SequentialTransition animation;
    public HashMap<Integer, TurtleView> turtles;
    public ArrayList<Integer> currTurtleIdxs;
    private AppModel logoModel;

    @Override
    public void start(Stage primaryStage) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
        turtleIcon = myResources.getString("CursorIcon");
        turtleStamp = myResources.getString("SimpleLeafStamp");
        canvasScreen = new CanvasScreen(myResources);
        animation = new SequentialTransition();
        animation.setRate(3);
        logoModel = new LogoModel(canvasScreen, myResources, "CursorIcon", animation);
        turtleView = new TurtleView(0, 0, canvasScreen, turtleStamp, turtleIcon, logoModel.getRunningStatus(), animation, logoModel);
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();


        runningStatus = new RunningStatus();
        turtleModel = turtleView.getModel();
    }


    @Test
    void testForward() {
        command = new CommandForward();
        command.setParam(50);
        command.runCommand(turtleView);
        assertEquals(0, turtleModel.getRelPos().posX);
        assertEquals(50, turtleModel.getRelPos().posY);
    }

    @Test
    void testcalcBounds() {
        turtleModel.calcBounds(new Rectangle(100, 100, 50, 60), 10);
        assertEquals(turtleModel.getXMin(), 105);
        assertEquals(turtleModel.getXMax(), 145);
        assertEquals(turtleModel.getYMin(), 105);
        assertEquals(turtleModel.getYMax(), 155);
    }

    @Test
    void testCalculateMove() {
        turtleModel.calculateMove(1000);
        assertEquals((turtleModel.getXMin() + turtleModel.getXMax()) / 2, turtleModel.getAbsPos().posX);
        assertEquals(turtleModel.getYMin(), turtleModel.getAbsPos().posY);
    }

    @Test
    void testRelativePosition() {
        turtleModel.setRelativePosition(650,500);
        assertEquals(80, turtleModel.getRelPos().posX);
        assertEquals(-167, turtleModel.getRelPos().posY);
    }

    @Test
    void testRotate()  {
        turtleModel.rotate(30);
        assertEquals(-30, turtleModel.getAngle());
    }

    @Test
    void testPenDown() {
        turtleModel.putPenDown();
        assertTrue(turtleModel.isPenDown());
    }

    @Test
    void testPenUp() {
        turtleModel.putPenUp();
        assertFalse(turtleModel.isPenDown());
    }
}