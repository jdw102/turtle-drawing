package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Models.TurtleModel;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

import java.util.ArrayList;

/**
 * A single class containing the information of each command.
 * Might refactor later into separate classes for each command.
 *
 * @author Aditya Paul
 */
public class Command {
    public CommandName prefix;

    public void setParam(int param) {
        this.param = param;
    }

    public int param;
    protected ArrayList<Integer> params;

    public int getParam() {
        return param;
    }

    public ArrayList<Integer> getParams() {
        return params;
    }

    public void runCommand(TurtleView turtle) {

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        switch (this.prefix) {
            case FORWARD -> s = new StringBuilder("fd");
            case BACK -> s = new StringBuilder("bk");
            case LEFT -> s = new StringBuilder("lt");
            case RIGHT -> s = new StringBuilder("rt");
            case PENDOWN -> s = new StringBuilder("pd");
            case PENUP -> s = new StringBuilder("pu");
            case SHOWT -> s = new StringBuilder("st");
            case HIDET -> s = new StringBuilder("ht");
            case HOME -> s = new StringBuilder("home");
            case STAMP -> s = new StringBuilder("stamp");
            case TELL -> s = new StringBuilder("tell");
        }
        if (this.params != null) {
            for (Integer i : this.params) {
                s.append(" ");
                s.append(Integer.toString(i));
            }
        } else if (this.param != 0) {
            s.append(" ");
            s.append(Integer.toString(this.param));
        }
        return s.toString();
    }
}
