package oolala;

import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import oolala.Command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class AppModel {
    public CanvasScreen myCanvas;
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

    public AppModel(CanvasScreen canvas, ResourceBundle resources, String imageUrl, AppView display) {
        myCanvas = canvas;
        myResources = resources;
        turtleStamp = myResources.getString(imageUrl);
        turtleIcon = myResources.getString(imageUrl);
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        homeX = 0;
        homeY = 0;
        turtles.put(1, new TurtleView(homeX, homeY, myCanvas, this));
        currTurtleIdxs.add(1);
        myCanvas.getShapes().getChildren().add(turtles.get(1).getIcon());
        animation = new SequentialTransition();
        animation.setRate(3);
        animation.setOnFinished(event -> {
            running = false;
            display.enableImageSelectors();
        });
    }
    public void runApp(ArrayList<Command> commands, AppView display) {
        display.disableImageSelectors();
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
        myCanvas.clear();
        currTurtleIdxs.clear();
        turtles.put(1, new TurtleView(homeX, homeY, myCanvas, this));
        myCanvas.getShapes().getChildren().add(turtles.get(1).getIcon()); // TODO: We should probably refactor this for scalability
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
    public String getTurtleIconUrl(){
        return turtleIcon;
    }
    public String getStampIconUrl(){
        return turtleStamp;
    }
    public void changeStamp(String s){
        turtleStamp = s;
    }
    public CanvasScreen getMyCanvas(){
        return myCanvas;
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
