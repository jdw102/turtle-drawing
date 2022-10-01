package oolala;

public class CommandLeft extends Command {

    public CommandLeft(){
        prefix =CmdName.LEFT;
    }
    public void runCommand(Turtle turtle, AppView display) {
        turtle.leftTurn(param);
    }
}
