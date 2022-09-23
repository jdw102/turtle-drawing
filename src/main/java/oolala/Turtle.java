package oolala;

import javafx.scene.paint.Color;

/**
 * A turtle/cursor to draw things with.
 *
 * @author Aditya Paul
 */
public class Turtle {

  public static final int DEFAULT_ID = 0; //TODO: Will we init this in the canvas?
  public static final int HOME_X = 400; //TODO: Will we init this in the canvas?
  public static final int HOME_Y = 400; //TODO: Will we init this in the canvas?
  public static final int DEFAULT_ANGLE = 0;
  public static final Color DEFAULT_COLOR = Color.BLACK;

  private int id; //TODO: Is this variable needed?
  private int posX;
  private int posY;
  private int angle;
  private Color color;

  public Turtle() {
    this.id = DEFAULT_ID;
    this.posX = HOME_X;
    this.posY = HOME_Y;
    this.angle = DEFAULT_ANGLE;
    this.color = Color.BLACK;
  }

}
