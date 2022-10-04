package oolala;

import oolala.Command.Command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import static oolala.Command.Command.CmdName.TELL;

public class LSystemModel extends AppModel {
    public LSystemModel(CanvasScreen canvas, ResourceBundle myResources, String stampUrl, AppView display) {
        super(canvas, myResources, stampUrl, display);
        changeIcon(myResources.getString("TriangleArrowIcon"));
        parser = new LSystemParser(myResources);
    }

    @Override
    public void runApp(ArrayList<Command> commands, AppView display) {
        turtles.get(1).hideTurtle(animation);
        super.runApp(commands, display);
        Iterator<Command> itCmd = commands.iterator();
        while (itCmd.hasNext()) {
            Command instruction = itCmd.next();
            //TODO: Handle tell command
            if (instruction.prefix == TELL) {
                currTurtleIdxs.clear();
                currTurtleIdxs.addAll(instruction.params);
                for (Integer param : instruction.params) {
                    if (!turtles.containsKey(param)) {
                        System.out.println("Creating new turtle");
                        turtles.put(param, new TurtleView(homeX, homeY, myCanvas, this));
                    }
                }
            }
            for (Integer idx : currTurtleIdxs) {
                instruction.runCommand(turtles.get(idx), myCanvas, animation);
            }
            itCmd.remove();
        }
        animation.play();
        animation.getChildren().removeAll(animation.getChildren());
    }
}
