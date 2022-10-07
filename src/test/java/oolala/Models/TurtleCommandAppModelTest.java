package oolala.Models;

import javafx.animation.SequentialTransition;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurtleCommandAppModelTest extends DukeApplicationTest {
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
    public double homeX = 0;
    public double homeY = 0;

    @Override
    public void start(Stage primaryStage) {
        runningStatus = new RunningStatus();
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE);
        animation = new SequentialTransition();
        animation.setRate(3);

        turtleIcon = myResources.getString("CursorIcon");
        turtleStamp = myResources.getString("SimpleLeafStamp");
        canvasScreen = new CanvasScreen(myResources);
        runningStatus = new RunningStatus();
        turtleModel = turtleView.getModel();
    }


    @Test
    void testForward() {
        command = new CommandForward();
        command.setParam(50);
        command.runCommand(turtleView);
        assertEquals(0, turtleModel.getRelPos().getX());
        assertEquals(50, turtleModel.getRelPos().getY());
    }

    @Test
    void enableInputs() {
    }

    @Test
    void disableInputs() {
    }

    @Test
    void runApp() {
    }

    @Test
    void getCurrTurtleIdxs() {
    }

    @Test
    void getTurtles() {
    }

    @Test
    void reset() {
    }

    @Test
    void setHome() {
    }

    @Test
    void getParser() {
    }

    @Test
    void getTurtleIconUrl() {
    }

    @Test
    void getStampIconUrl() {
    }

    @Test
    void changeImage() {
    }

    @Test
    void testRunApp() {
    }

    @Test
    void testChangeImage() {
    }
}