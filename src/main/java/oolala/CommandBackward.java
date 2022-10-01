package oolala;

public class CommandBackward extends Command {

    public CommandBackward(){
        prefix =CmdName.BACK;
    }

    public void runCommand(Turtle turtle, AppView display) {
        turtle.moveBack(param, display);
    }
}
