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
    public LSystemModel(CanvasScreen canvas, ResourceBundle myResources, String stampUrl, AppView display) {
        super(canvas, myResources, stampUrl, display);
        turtleIcon = myResources.getString("CursorIcon");
        turtleStamp = myResources.getString(stampUrl);
        parser = new LSystemParser(myResources);
        turtles.put(1, new TurtleView(homeX, homeY, myCanvas, this));
        currTurtleIdxs.add(1);
        turtles.get(1).getIcon().setId("Turtle" + Integer.toString(1));
        myCanvas.getShapes().getChildren().add(turtles.get(1).getIcon());
    }

    @Override
    public void runApp(List<Command> commands, AppView display, SequentialTransition animation) {
        running = true;
        turtles.get(1).hideTurtle(animation);
        super.runApp(commands, display, animation);
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
                        turtles.put(param, new TurtleView(homeX, homeY, myCanvas, this));
                        turtles.get(param).getIcon().setId("Turtle" + Integer.toString(param));
                    }
                }
            }
            for (Integer idx : currTurtleIdxs) {
                instruction.runCommand(turtles.get(idx), myCanvas, animation);
                if (!turtles.get(idx).getModel().inBounds()){
                    turtlesInBound = false;
                    break;
                }
            }
            itCmd.remove();
        }
    }
    @Override
    public void changeImage(String url){
        turtleStamp = url;
        for (Integer i: currTurtleIdxs)
            turtles.get(i).changeStamp(turtleStamp);
    }
}
