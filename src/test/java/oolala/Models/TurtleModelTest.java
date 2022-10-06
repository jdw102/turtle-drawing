package oolala.Models;

import javafx.scene.shape.Rectangle;
import oolala.Command.Command;
import oolala.Command.CommandForward;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurtleModelTest {
    TurtleModel turtleModel;
    Rectangle borderRectangle;
    Command command;


    @BeforeEach
    void setUp() {
        borderRectangle = new Rectangle(0, 0, 500, 500);
        turtleModel = new TurtleModel(0, 0, borderRectangle, 30);
    }

    @Test
    void createTurtlePath() {
    }

    @Test
    void setPosition() {
        command = new CommandForward();
        command.runCommand(turtleModel, );
    }

    @Test
    void rotate() {
    }

    @Test
    void putPenDown() {
    }

    @Test
    void putPenUp() {
    }

    @Test
    void updateRelativePosition() {
    }

    @Test
    void isPenDown() {
    }

    @Test
    void getPosX() {
    }

    @Test
    void getPosY() {
    }

    @Test
    void geAngle() {
    }

    @Test
    void getXMin() {
    }

    @Test
    void getXMax() {
    }

    @Test
    void getYMin() {
    }

    @Test
    void getYMax() {
    }

    @Test
    void getRelX() {
    }

    @Test
    void getRelY() {
    }
}