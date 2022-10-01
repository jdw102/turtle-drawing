package oolala;

public class CommandRight extends Command {

    public CommandRight(){
        prefix =CmdName.RIGHT;
    }
    public void runCommand(Turtle turtle, AppView display) {
        turtle.rightTurn(param);
    }
}
