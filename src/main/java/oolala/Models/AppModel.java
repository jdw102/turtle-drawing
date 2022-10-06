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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class AppModel {
    public CanvasScreen myCanvas;
    private AppView myDisplay;
    public HashMap<Integer, TurtleView> turtles;
    public ArrayList<Integer> currTurtleIdxs;
    public SequentialTransition animation;
    public ResourceBundle myResources;
    public Parser parser;
    public double homeX;
    public double homeY;
    public boolean running = false;
    public String turtleIcon;
    public String turtleStamp;

    //TODO: Can we create polymorphism for parser?

    public AppModel(CanvasScreen canvas, ResourceBundle resources, String imageUrl, AppView display, SequentialTransition animation) {
        myDisplay = display;
        this.animation = animation;
        myCanvas = canvas;
        myResources = resources;
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        homeX = 0;
        homeY = 0;
    }

    public void enableInputs(ComboBox<ImageView> imageSelector, Button runButton) {
        imageSelector.setDisable(false);
        runButton.setDisable(false);
    }

    public void disableInputs(ComboBox<ImageView> imageSelector, Button runButton) {
        imageSelector.setDisable(true);
        runButton.setDisable(true);
    }

    public void runApp(ArrayList<Command> commands) {
        running = true;
    }

    public ArrayList<Integer> getCurrTurtleIdxs() {
        return currTurtleIdxs;
    }

    public HashMap<Integer, TurtleView> getTurtles() {
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
