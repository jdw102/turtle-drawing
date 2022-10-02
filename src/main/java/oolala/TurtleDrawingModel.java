package oolala;

import oolala.Command.Command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import static oolala.Command.Command.CmdName.TELL;

public class TurtleDrawingModel extends AppModel {

    public TurtleDrawingModel(AppView display, ResourceBundle myResources) {
        super(display, myResources);
        parser = new TurtleParser(myResources);
    }

    @Override
    public void runApp(ArrayList<Command> commands) {
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
                        turtles.put(param, new TurtleView(0, 0, myDisplay.getCanvasScreen()));
                        myDisplay.getCanvasScreen().getShapes().getChildren().add(turtles.get(param).getIcon());
                    }
                }
            }
            for (Integer idx : currTurtleIdxs) {
                instruction.runCommand(turtles.get(idx), myDisplay.getCanvasScreen(), animation);
            }
            itCmd.remove();
        }
        animation.play();
        animation.getChildren().removeAll(animation.getChildren());
    }
}
