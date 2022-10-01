package oolala;

public class CommandStamp extends Command {
    public CommandStamp(){
        prefix =CmdName.STAMP;
    }

    public void runCommand(Turtle turtle, AppView display) {
        turtle.stamp(display);
    }
}
