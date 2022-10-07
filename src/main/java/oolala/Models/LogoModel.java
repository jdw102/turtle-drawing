package oolala.Models;

import javafx.animation.Animation;
import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Command.Command;
import oolala.Parsers.LogoParser;
import oolala.Views.AppView;
import oolala.Views.TurtleView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static oolala.Command.Command.CmdName.TELL;

public class LogoModel extends AppModel {

    public LogoModel(CanvasScreen canvas, ResourceBundle myResources, String iconUrl, SequentialTransition animation) {
        super(canvas, myResources, animation);
        turtleStamp = myResources.getString(iconUrl);
        turtleIcon = myResources.getString(iconUrl);
        parser = new LogoParser(myResources);
        turtles.put(1, addNewTurtle());
        currTurtleIdxs.add(1);
        turtles.get(1).getIcon().setId("Turtle" + Integer.toString(1));
        myCanvas.getShapes().getChildren().add(turtles.get(1).getIcon());
    }

    @Override
    public void runApp(List<Command> commands) {
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
                        myCanvas.getShapes().getChildren().add(turtles.get(param).getIcon());
                    }
                }
            }
            for (Integer idx : currTurtleIdxs) {
                instruction.runCommand(turtles.get(idx));
                if (!turtles.get(idx).getModel().inBounds()){
                    turtlesInBound = false;
                    break;
                }
            }
            itCmd.remove();
        }
    }

    @Override
    public void changeImage(String url) {
        turtleIcon = url;
        turtleStamp = url;
        for (Integer i: currTurtleIdxs){
            turtles.get(i).changeIcon(url, this);
            turtles.get(i).changeStamp(url);
        }
    }
}
