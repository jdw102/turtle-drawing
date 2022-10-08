package oolala.Command;

import javafx.animation.SequentialTransition;
import javafx.stage.Stage;
import oolala.Models.AppModel;
import oolala.Models.LogoModel;
import oolala.Models.RunningStatus;
import oolala.Models.TurtleModel;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;


public class CommandTest extends DukeApplicationTest {
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
    void testBackward() {
        command = new CommandBackward();
        command.setParam(50);
        command.runCommand(turtleView);
        assertEquals(0, turtleModel.getRelPos().posX);
        assertEquals(-50, turtleModel.getRelPos().posY);
    }

    @Test
    void testLeftTurn() {
        command = new CommandLeft();
        command.setParam(60);
        command.runCommand(turtleView);
        assertEquals(60, turtleModel.getAngle());
    }

    @Test
    void testRightTurn() {
        command = new CommandRight();
        command.setParam(60);
        command.runCommand(turtleView);
        assertEquals(-60, turtleModel.getAngle());
    }

    @Test
    void testPenDown() {
        command = new CommandPenDown();
        command.runCommand(turtleView);
        assertTrue(turtleModel.isPenDown());
    }

    @Test
    void testPenUp() {
        command = new CommandPenUp();
        command.runCommand(turtleView);
        assertFalse(turtleModel.isPenDown());
    }

    @Test
    void testShowTurtle() {
        command = new CommandShowTurtle();
        command.runCommand(turtleView);
        assertTrue(turtleModel.isShown());
    }

    @Test
    void testHideTurtle() {
        command = new CommandHideTurtle();
        command.runCommand(turtleView);
        assertFalse(turtleModel.isShown());
    }

    @Test
    void testHome() {
        turtleModel.setRelativePosition(500, 500);
        //assertEquals(0, turtleModel.getRelPos().posX);
        //assertEquals(0, turtleModel.getRelPos().posY);
        command = new CommandHome();
        command.runCommand(turtleView);
        assertEquals(0, turtleModel.getRelPos().posX);
        assertEquals(0, turtleModel.getRelPos().posY);
    }

    @Test
    void testTell (){
        command = new CommandTell();
        command.getParams().add(1);
        command.getParams().add(2);
        List<Command> commands = new ArrayList<>();
        commands.add(command);
        logoModel.runApp(commands);
        assertEquals(2, logoModel.getTurtles().size());
    }

    @Test
    void testToString() {
        command = new CommandForward();
        command.setParam(50);
        assertEquals("fd 50", command.toString());
        command = new CommandBackward();
        command.setParam(50);
        assertEquals("bk 50", command.toString());
        command = new CommandLeft();
        command.setParam(50);
        assertEquals("lt 50", command.toString());
        command = new CommandRight();
        command.setParam(60);
        assertEquals("rt 60", command.toString());
        command = new CommandPenDown();
        assertEquals("pd", command.toString());
        command = new CommandHideTurtle();
        assertEquals("ht", command.toString());
        command = new CommandShowTurtle();
        assertEquals("st", command.toString());
        command = new CommandHome();
        assertEquals("home", command.toString());
        command = new CommandStamp();
        assertEquals("stamp", command.toString());
    }

    @Test
    void testTellToString(){
        command = new CommandTell();
        command.getParams().add(1);
        command.getParams().add(2);
        assertEquals("tell 1 2", command.toString());
    }
}