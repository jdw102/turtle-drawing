package oolala.Models;

import javafx.animation.SequentialTransition;
import javafx.stage.Stage;
import oolala.Command.Command;
import oolala.Command.CommandForward;
import oolala.Command.CommandTell;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;


public class LSystemModelTest extends DukeApplicationTest {
    public static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    public static final String DEFAULT_LANGUAGE = "English";
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
    private AppModel lSystemModel;
    List<Command> commands;

    @Override
    public void start(Stage primaryStage) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
        turtleIcon = myResources.getString("CursorIcon");
        turtleStamp = myResources.getString("SimpleLeafStamp");
        canvasScreen = new CanvasScreen(myResources);
        animation = new SequentialTransition();
        animation.setRate(3);
        lSystemModel = new LSystemModel(canvasScreen, myResources, "SimpleLeafStamp", animation);
        turtleView = new TurtleView(0, 0, canvasScreen, turtleStamp, turtleIcon, lSystemModel.getRunningStatus(), animation, lSystemModel);
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();


        runningStatus = new RunningStatus();
        turtleModel = turtleView.getModel();
    }

    @Test
    void testRunApp() {
        command = new CommandTell();
        command.getParams().add(1);
        command.getParams().add(2);
        commands = new ArrayList<>();
        commands.add(command);
        lSystemModel.runApp(commands);
        assertEquals(2, lSystemModel.getTurtles().size());
    }

    @Test
    void testRunningStatus(){
        assertFalse(lSystemModel.getRunningStatus().isRunning());
    }

    @Test
    void testRunningStatusDuringRuntime(){
        command = new CommandTell();
        command.getParams().add(1);
        command.getParams().add(2);
        commands = new ArrayList<>();
        commands.add(command);
        lSystemModel.runApp(commands);
        assertTrue(lSystemModel.getRunningStatus().isRunning());
    }

    @Test
    void testTurtleHidden(){
        command = new CommandForward();
        command.setParam(50);
        commands = new ArrayList<>();
        commands.add(command);
        lSystemModel.runApp(commands);
        assertFalse(lSystemModel.getTurtles().get(1).getModel().isShown());
    }

    @Test
    void testResetPositionOfTurtle(){
        command = new CommandForward();
        command.setParam(50);
        commands = new ArrayList<>();
        commands.add(command);
        lSystemModel.runApp(commands);
        lSystemModel.reset(false);
        assertEquals(0, lSystemModel.getTurtles().get(1).getModel().getRelPos().posX);
        assertEquals(0, lSystemModel.getTurtles().get(1).getModel().getRelPos().posY);
    }

    @Test
    void testResetNumberOfTurtles(){
        command = new CommandTell();
        command.getParams().add(1);
        command.getParams().add(2);
        commands = new ArrayList<>();
        commands.add(command);
        lSystemModel.runApp(commands);
        assertEquals(2, lSystemModel.getTurtles().size());
        lSystemModel.reset(false);
        assertEquals(1, lSystemModel.getTurtles().size());
    }

    @Test
    void testResetHome(){
        lSystemModel.setHome(100, 150);
        command = new CommandForward();
        command.setParam(50);
        commands = new ArrayList<>();
        commands.add(command);
        lSystemModel.runApp(commands);
        lSystemModel.reset(false);
        assertEquals(100, lSystemModel.getHomePos().posX);
        assertEquals(150, lSystemModel.getHomePos().posY);
        lSystemModel.reset(true);
        assertEquals(0, lSystemModel.getHomePos().posX);
        assertEquals(0, lSystemModel.getHomePos().posY);
    }

    @Test
    void testChangeImage(){
        String stampPath = myResources.getString("OakLeafStamp");
        lSystemModel.changeImage(stampPath);
        assertEquals(stampPath, lSystemModel.getTurtles().get(1).getStampPath());
    }
}