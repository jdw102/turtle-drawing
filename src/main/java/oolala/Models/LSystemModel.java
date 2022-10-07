package oolala.Models;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Command.Command;
import oolala.Parsers.LSystemParser;
import oolala.Views.AppView;
import oolala.Views.TurtleView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static oolala.Command.Command.CmdName.TELL;

public class LSystemModel extends AppModel {
    public LSystemModel(CanvasScreen canvas, ResourceBundle myResources, String stampUrl, SequentialTransition animation) {
        super(canvas, myResources, animation);
        turtleIcon = myResources.getString("CursorIcon");
        turtleStamp = myResources.getString(stampUrl);
        parser = new LSystemParser(myResources);
        turtles.put(1, addNewTurtle());
        currTurtleIdxs.add(1);
        turtles.get(1).getIcon().setId("Turtle" + Integer.toString(1));
        myCanvas.getShapes().getChildren().add(turtles.get(1).getIcon());
    }

    @Override
    public void runApp(List<Command> commands) {
        turtles.get(1).hideTurtle();
        super.runApp(commands);
        Iterator<Command> itCmd = commands.iterator();
        while (itCmd.hasNext() && turtlesInBound) {
            Command instruction = itCmd.next();
            //TODO: Handle tell command
            if (instruction.prefix == TELL) {
                currTurtleIdxs.clear();
                currTurtleIdxs.addAll(instruction.params);
                for (Integer param : instruction.params) {
                    if (!turtles.containsKey(param)) {
                        System.out.println("Creating new turtle");
                        turtles.put(param, addNewTurtle());
                        turtles.get(param).getIcon().setId("Turtle" + Integer.toString(param));
                    }
                }
            }
            for (Integer idx : currTurtleIdxs) {
                instruction.runCommand(turtles.get(idx));
                if (!turtles.get(idx).getModel().inBounds()) {
                    turtlesInBound = false;
                    break;
                }
            }
            itCmd.remove();
        }
        animation.play();
        if (animation.getChildren().size() == 0) runningStatus.setRunningStatus(false);
        animation.getChildren().removeAll(animation.getChildren());
    }

    @Override
    public void changeImage(String url) {
        turtleStamp = url;
    }
}
