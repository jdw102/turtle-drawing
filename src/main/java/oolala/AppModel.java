package oolala;

import javafx.animation.SequentialTransition;
import javafx.scene.shape.Rectangle;
import oolala.Command.Command;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AppModel {
    public AppView myDisplay;
    public HashMap<Integer, TurtleView> turtles;
    public ArrayList<Integer> currTurtleIdxs;
    public SequentialTransition animation;

    public AppModel(AppView display){
        myDisplay = display;
        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        turtles.put(1, new TurtleView(0, 0, display.getCanvasScreen()));
        currTurtleIdxs.add(1);
        display.getCanvasScreen().getShapes().getChildren().add(turtles.get(1).getIcon());
        animation = new SequentialTransition();
    }
    public void runApp(ArrayList<Command> commands){

    }
    public ArrayList<Integer> getCurrTurtleIdxs(){
        return currTurtleIdxs;
    }
    public HashMap<Integer, TurtleView> getTurtles(){
        return turtles;
    }
    public void reset() {
        turtles.clear(); // TODO: Check if this is correct functionality
        myDisplay.getCanvasScreen().getShapes().getChildren().removeIf(i -> !(i instanceof Rectangle));
        currTurtleIdxs.clear();

        turtles.put(1, new TurtleView(0, 0, myDisplay.getCanvasScreen()));
        myDisplay.getCanvasScreen().getShapes().getChildren().add(turtles.get(1).getIcon()); // TODO: We should probably refactor this for scalability
        currTurtleIdxs.add(1);
        animation.stop();
        animation.getChildren().removeAll(animation.getChildren());
    }
}
