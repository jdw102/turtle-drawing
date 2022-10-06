package oolala.Models;

import javafx.animation.SequentialTransition;
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
    public boolean running = false;
    public String turtleIcon;
    public String turtleStamp;
    public boolean turtlesInBound;

    //TODO: Can we create polymorphism for parser?

    public AppModel(CanvasScreen canvas, ResourceBundle resources, String imageUrl, AppView display, SequentialTransition animation) {
        this.animation = animation;
        myCanvas = canvas;
        myResources = resources;
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        homeX = 0;
        homeY = 0;
        animation.setOnFinished(event ->{
            display.enableInputs();
        });
        turtlesInBound = true;
    }

    public void runApp(List<Command> commands, AppView display) {
        running = true;
        display.disableInputs();
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
        turtles.put(1, new TurtleView(homeX, homeY, myCanvas, this));
        myCanvas.getShapes().getChildren().add(turtles.get(1).getIcon()); // TODO: We should probably refactor this for scalability
        currTurtleIdxs.add(1);
        animation.stop();
        running = false;
        animation.getChildren().removeAll(animation.getChildren());
        turtlesInBound = true;
    }

    public boolean isRunning() {
        return running;
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
