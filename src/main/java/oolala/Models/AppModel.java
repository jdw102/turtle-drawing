package oolala.Models;

import javafx.animation.SequentialTransition;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Command.Command;
import oolala.Parsers.Parser;
import oolala.Views.AppView;
import oolala.Views.TurtleView;

import java.util.*;

public abstract class AppModel {
    public CanvasScreen myCanvas;
    public Map<Integer, TurtleView> turtles;
    public List<Integer> currTurtleIdxs;
    public SequentialTransition animation;
    public ResourceBundle myResources;
    public Parser parser;
    public double homeX;
    public double homeY;
    public String turtleIcon;
    public String turtleStamp;
    protected RunningStatus runningStatus;
    public boolean turtlesInBound;

    //TODO: Can we create polymorphism for parser?

    public AppModel(CanvasScreen canvas, ResourceBundle resources, SequentialTransition animation) {
        runningStatus = new RunningStatus();
        this.animation = animation;
        myCanvas = canvas;
        myResources = resources;
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        homeX = 0;
        homeY = 0;
        turtlesInBound = true;
    }

    public TurtleView addNewTurtle(){
        TurtleView turtleView =  new TurtleView(homeX, homeY, myCanvas, turtleStamp, turtleIcon, runningStatus, animation);
        return turtleView;
    }

    public void runApp(List<Command> commands) {
        runningStatus.setRunningStatus(true);
    }

    public void setRunning(boolean isRunning){
        runningStatus.setRunningStatus(isRunning);
    }


    public List<Integer> getCurrTurtleIdxs() {
        return currTurtleIdxs;
    }

    public Map<Integer, TurtleView> getTurtles() {
        return turtles;
    }

    public void reset(boolean resetHome) {
        if (resetHome) {
            homeX = 0;
            homeY = 0;
        }
        turtles.clear(); // TODO: Check if this is correct functionality
        myCanvas.clear();
        currTurtleIdxs.clear();
        turtles.put(1, addNewTurtle());
        myCanvas.getShapes().getChildren().add(turtles.get(1).getIcon()); // TODO: We should probably refactor this for scalability
        currTurtleIdxs.add(1);
        animation.stop();
        runningStatus.setRunningStatus(false);
        animation.getChildren().removeAll(animation.getChildren());
    }

    public void setHome(double x, double y) {
        homeX = x;
        homeY = y;
    }

    public Parser getParser() {
        return parser;
    }

    public String getTurtleIconUrl() {
        return turtleIcon;
    }

    public String getStampIconUrl() {
        return turtleStamp;
    }

    public CanvasScreen getMyCanvas() {
        return myCanvas;
    }

    public void changeImage(String url) {

    }
}
