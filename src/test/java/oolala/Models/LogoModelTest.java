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


public class LogoModelTest extends DukeApplicationTest {
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
    List<Command> commands;

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
    void testRunApp() {
        command = new CommandTell();
        command.getParams().add(1);
        command.getParams().add(2);
        commands = new ArrayList<>();
        commands.add(command);
        logoModel.runApp(commands);
        assertEquals(2, logoModel.getTurtles().size());
    }

    @Test
    void testRunningStatus(){
        assertFalse(logoModel.getRunningStatus().isRunning());
    }

    @Test
    void testRunningStatusDuringRuntime(){
        command = new CommandTell();
        command.getParams().add(1);
        command.getParams().add(2);
        commands = new ArrayList<>();
        commands.add(command);
        logoModel.runApp(commands);
        assertTrue(logoModel.getRunningStatus().isRunning());
    }

    @Test
    void testResetPositionOfTurtle(){
        command = new CommandForward();
        command.setParam(50);
        commands = new ArrayList<>();
        commands.add(command);
        logoModel.runApp(commands);
        logoModel.reset(false);
        assertEquals(0, logoModel.getTurtles().get(1).getModel().getRelPos().posX);
        assertEquals(0, logoModel.getTurtles().get(1).getModel().getRelPos().posY);
    }

    @Test
    void testResetNumberOfTurtles(){
        command = new CommandTell();
        command.getParams().add(1);
        command.getParams().add(2);
        commands = new ArrayList<>();
        commands.add(command);
        logoModel.runApp(commands);
        assertEquals(2, logoModel.getTurtles().size());
        logoModel.reset(false);
        assertEquals(1, logoModel.getTurtles().size());
    }

    @Test
    void testResetHome(){
        logoModel.setHome(100, 150);
        command = new CommandForward();
        command.setParam(50);
        commands = new ArrayList<>();
        commands.add(command);
        logoModel.runApp(commands);
        logoModel.reset(false);
        assertEquals(100, logoModel.getHomeX());
        assertEquals(150, logoModel.getHomeY());
        logoModel.reset(true);
        assertEquals(0, logoModel.getHomeX());
        assertEquals(0, logoModel.getHomeY());
    }

    @Test
    void testChangeImage(){
        String stampUrl = myResources.getString("TriangleArrowIcon");
        String iconUrl = myResources.getString("TriangleArrowIcon");
        logoModel.changeImage(stampUrl);
        assertEquals(stampUrl, logoModel.getTurtles().get(1).getStampUrl());
        assertEquals(stampUrl, logoModel.getTurtles().get(1).getIconUrl());
    }
}