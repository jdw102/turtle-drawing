package oolala;

import javafx.animation.SequentialTransition;
import javafx.scene.shape.Path;

import java.util.ArrayList;
import java.util.Iterator;

import static oolala.Command.CmdName.TELL;

public class TurtleDrawingModel extends AppModel {
    public TurtleDrawingModel(AppView display) {
        super(display);
    }

    @Override
    public void runApp(ArrayList<Command> commands) {
        Iterator<Command> itCmd = commands.iterator();
        int iter = 0;
        while (itCmd.hasNext()) {
            Command instruction = itCmd.next();
            //TODO: Handle tell command
            if (instruction.prefix == TELL) {
                currTurtleIdxs.clear();
                currTurtleIdxs.addAll(instruction.params);
                for (Integer param : instruction.params) {
                    if (!turtles.containsKey(param)) {
                        System.out.println("Creating new turtle");
                        turtles.put(param, new Turtle(param, 0, 0, myDisplay.getCanvasScreen()));
                        myDisplay.getCanvasScreen().getShapes().getChildren().add(turtles.get(param).getIcon());
                    }
                }
            }
            for (Integer idx : currTurtleIdxs) {
                turtles.get(idx).readInstruction(instruction, myDisplay.getCanvasScreen(), animation);
            }
            itCmd.remove();
            iter++;
        }
        animation.play();
        animation.getChildren().removeAll(animation.getChildren());
    }
}
