package oolala;

import javafx.animation.SequentialTransition;
import javafx.scene.shape.Rectangle;
import oolala.Command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class AppModel {
    public AppView myDisplay;
    public HashMap<Integer, TurtleView> turtles;
    public ArrayList<Integer> currTurtleIdxs;
    public SequentialTransition animation;
    public ResourceBundle myResources;
    public Parser parser;
    public double homeX;
    public double homeY;
    public boolean running = false;

    //TODO: Can we create polymorphism for parser?

    public AppModel(AppView display, ResourceBundle resources) {
        myResources = resources;
        myDisplay = display;
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        homeX = 0;
        homeY = 0;
        turtles.put(1, new TurtleView(homeX, homeY, display.getCanvasScreen(), this));
        currTurtleIdxs.add(1);
        animation = new SequentialTransition();
        animation.setOnFinished(event -> {
            running = false;
        });
    }
    public void displayTurtles(){
        for (Integer i: currTurtleIdxs){
            myDisplay.getCanvasScreen().getShapes().getChildren().add(turtles.get(i).getIcon());
        }
    }
    public void runApp(ArrayList<Command> commands) {

    }

    public ArrayList<Integer> getCurrTurtleIdxs() {
        return currTurtleIdxs;
    }

    public HashMap<Integer, TurtleView> getTurtles() {
        return turtles;
    }

    public void reset(boolean resetHome) {
        if (resetHome){
            homeX = 0;
            homeY = 0;
        }
        turtles.clear(); // TODO: Check if this is correct functionality
        myDisplay.getCanvasScreen().getShapes().getChildren().removeIf(i -> !(i instanceof Rectangle));
        currTurtleIdxs.clear();
        turtles.put(1, new TurtleView(homeX, homeY, myDisplay.getCanvasScreen(), this));
        myDisplay.getCanvasScreen().getShapes().getChildren().add(turtles.get(1).getIcon()); // TODO: We should probably refactor this for scalability
        currTurtleIdxs.add(1);
        animation.stop();
        running = false;
        animation.getChildren().removeAll(animation.getChildren());
    }
    public boolean isRunning(){
        return running;
    }
    public void setHome(double x, double y){
        homeX = x;
        homeY = y;
    }
    public Parser getParser() {
        return parser;
    }
    public AppView getMyDisplay(){
        return myDisplay;
    }
}
