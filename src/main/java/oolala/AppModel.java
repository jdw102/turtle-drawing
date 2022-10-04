package oolala;

import javafx.animation.SequentialTransition;
import javafx.scene.image.ImageView;
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
    private String turtleIcon;
    private String turtleStamp;

    //TODO: Can we create polymorphism for parser?

    public AppModel(AppView display, ResourceBundle resources, String imageUrl) {
        myResources = resources;
        turtleStamp = myResources.getString(imageUrl);
        turtleIcon = myResources.getString(imageUrl);
        myDisplay = display;
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        homeX = 0;
        homeY = 0;
        turtles.put(1, new TurtleView(homeX, homeY, display.getCanvasScreen(), this));
        currTurtleIdxs.add(1);
        myDisplay.getCanvasScreen().getShapes().getChildren().add(turtles.get(1).getIcon());
        animation = new SequentialTransition();
        animation.setRate(3);
        animation.setOnFinished(event -> {
            running = false;
            myDisplay.enableImageSelectors();
        });
    }
    public void runApp(ArrayList<Command> commands) {
        myDisplay.disableImageSelectors();
        running = true;
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
    public void removeTurtles(){
        for (Integer i: currTurtleIdxs){
            myDisplay.getCanvasScreen().getShapes().getChildren().remove(turtles.get(i).getIcon());
        }
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
    public String getTurtleIconUrl(){
        return turtleIcon;
    }
    public String getStampIconUrl(){
        return turtleStamp;
    }
    public void changeStamp(String s){
        turtleStamp = s;
    }
    public void changeIcon(String s){
        turtleIcon = s;
        turtleStamp = s;
        for (Integer i: currTurtleIdxs){
            turtles.get(i).changeIcon(s, this);
            turtles.get(i).changeStamp(s);
        }
    }
}
