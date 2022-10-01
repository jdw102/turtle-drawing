package oolala;

public class CommandHome extends Command {
    public CommandHome(){
        prefix =CmdName.HOME;
    }

    public void runCommand(Turtle turtle, AppView display) {
        turtle.home();
    }
}
