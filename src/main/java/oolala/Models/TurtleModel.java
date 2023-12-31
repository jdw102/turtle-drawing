package oolala.Models;

import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * A model class to hold information about the turtles position, bounds, and bearing and
 * communicate with the turtle view and adjust according to commands.
 *
 * @author Jerry Worthy
 */
public class TurtleModel {
    public static final int DEFAULT_ANGLE = 0;
    private double absPosX;
    private double absPosY;
    private int angle;
    private boolean penDown;
    private double xMaxAbs;
    private double xMinAbs;
    private double yMaxABs;
    private double yMinAbs;
    private double xBoundRel;
    private double yBoundRel;
    private double relX;
    private double relY;
    private double homeX;
    private double homeY;
    private double originAbsX;
    private double originAbsY;
    private boolean inBounds;
    private boolean isShown;


    public TurtleModel(double relPosX, double relPosY, Rectangle border, double iconSize) {
        calcBounds(border, iconSize);
        setRelPos(relPosX, relPosY);
        this.angle = DEFAULT_ANGLE;
        this.isShown = true;
        this.penDown = true;
        this.inBounds = true;
    }

    /**
     * A method to calculate the maximum and minimum x and y coordinates that the turtle view is allowed to move to.
     *
     * @param r        - The rectangle that defines the boundaries.
     * @param iconSize - The size of the image view icon.
     * @author Jerry Worthy
     */
    public void calcBounds(Rectangle r, double iconSize) {
        xMinAbs = r.getX();
        xMaxAbs = r.getX() + r.getWidth();
        yMinAbs = r.getY();
        yMaxABs = r.getY() + r.getHeight();
        double width = xMaxAbs - xMinAbs;
        double height = yMaxABs - yMinAbs;
        xBoundRel = width / 2 - iconSize / 2;
        yBoundRel = height / 2 - iconSize / 2;
        originAbsX = r.getX() + width / 2;
        originAbsY = r.getY() + height / 2;

    }

    /**
     * A method to calculate the new coordinates after traveling a distance in the direction
     * of the model's current angle value. It adjusts the new x and y positions accordingly if
     * either of them are outside the bound. If they fall out of the bounds than inBounds is set
     * to false.
     *
     * @param dist - The distance to travel.
     * @return Returns a position object that contains the new relative x and y coordinates of the icon and model.
     * @author Luyao Wang
     */
    public Position calculateMove(double dist) {
        double x = relX + dist * Math.cos(Math.toRadians(this.angle + 90));
        double y = relY + dist * Math.sin(Math.toRadians(this.angle + 90));

        inBounds = !(x > xBoundRel || x < -xBoundRel || y > yBoundRel|| y < -yBoundRel);
        if (x > xBoundRel) {
            x = xBoundRel;
            y = relY;
        }
        if (x < -xBoundRel) {
            x = -xBoundRel;
            y = relY;
        }
        if (y > yBoundRel) {
            y = yBoundRel;
            x = relX;
        }
        if (y < -yBoundRel) {
            y = -yBoundRel;
            x = relX;
        }
        return new Position(x, y);
    }

    public boolean inBounds() {
        return inBounds;
    }

    public void rotate(double newAngle) {
        angle -= newAngle;
    }
    public void putPenDown(){
        penDown = true;
    }

    public void putPenUp() {
        penDown = false;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public boolean isShown() {
        return isShown;
    }

    /**
     * A method to update the tooltip text to reflect the new relative positions and bearing. It is called by the view class.
     *
     * @author Jerry Worthy
     */
    public void setTooltipRelativePosition(ImageView i, Tooltip tooltip) {
        tooltip.setText("x: " + Double.toString(Math.round(relX)) + " y: " + Double.toString(Math.round(relY)) + ", " + Integer.toString(-angle % 360) + "°");
    }

    /**
     * A method to update the position of the model relative to the center of the border rectangle. It is called whenever the actual
     * position is changed. These relative x and y coordinates are to be displayed by the icon tooltip.
     *
     * @author Jerry Worthy
     */
    public void setRelPos(double relXArg, double relYArg) {
        relX = relXArg;
        relY = relYArg;
        Position absPos = relToAbs(relX, relY);
        setAbsPos(absPos.posX, absPos.posY);
    }

    public void setAbsPos(double x, double y) {
        absPosX = x;
        absPosY = y;
    }

    /**
     * Convert from relative position in frame to absolute position in frame.
     *
     * @param relXArg - relative x-position to center of frame
     * @param relYArg - relative y-position to center of frame
     * @return Luyao Wang
     */
    public Position relToAbs(double relXArg, double relYArg) {
        return new Position(relXArg + originAbsX, - relYArg + originAbsY);
    }

    /**
     * Convert from absolute position in frame to position relative to frame center.
     *
     * @param absXArg - absolute x-position in frame
     * @param absYArg - absolute y-position in frame
     */
    public Position absToRel(double absXArg, double absYArg) {
        return new Position(absXArg - originAbsX, - absYArg + originAbsY);
    }


    public Position getHomePos() {
        return new Position(homeX, homeY);
    }

    public void setHomePos(double relX, double relY) {
        homeX = relX;
        homeY = relY;
    }

    public boolean isPenDown() {
        return penDown;
    }

    public Position getAbsPos() {
        return new Position(absPosX, absPosY);
    }

    public double getAngle() {
        return angle;
    }

    public double getXMin() {
        return xMinAbs;
    }

    public double getXMax() {
        return xMaxAbs;
    }

    public double getYMin() {
        return yMinAbs;
    }

    public double getYMax() {
        return yMaxABs;
    }

    public Position getRelPos() {
        return new Position(relX, relY);
    }
}
