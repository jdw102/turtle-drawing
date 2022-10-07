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
    private double posX;
    private double posY;
    private int angle;
    private boolean penDown;
    private double xMax;
    private double xMin;
    private double yMax;
    private double yMin;
    private double relX;
    private double relY;
    private boolean inBounds;
    private boolean isShown;

    public TurtleModel(double posX, double posY, Rectangle border, double iconSize) {
        this.posX = posX + border.getX() + border.getWidth() / 2;
        this.posY = posY + border.getY() + border.getHeight() / 2;
        this.angle = DEFAULT_ANGLE;
        this.isShown = true;
        this.penDown = true;
        this.inBounds = true;
        calcBounds(border, iconSize);
    }
    /**
     * A method to calculate the maximum and minimum x and y coordinates that the turtle view is allowed to move to.
     *
     * @param r - The rectangle that defines the boundaries.
     * @param iconSize - The size of the image view icon.
     * @author Jerry Worthy
     */
    public void calcBounds(Rectangle r, double iconSize) {
        xMin = r.getX() + iconSize / 2;
        xMax = r.getX() + r.getWidth() - iconSize / 2;
        yMin = r.getY() + iconSize / 2;
        yMax = r.getY() + r.getHeight() - iconSize / 2;
    }
    /**
     * A method to calculate the new coordinates after traveling a distance in the direction
     * of the model's current angle value. It adjusts the new x and y positions accordingly if
     * either of them are outside the bound. If they fall out of the bounds than inBounds is set
     * to false.
     *
     * @param dist - The distance to travel.
     * @return Returns a position object that contains the new x and y coordinates of the icon and model.
     * @author Luyao Wang
     */
    public Position calculateMove(double dist) {
        double x = this.posX + dist * Math.cos(Math.toRadians(this.angle + 90));
        double y = this.posY - dist * Math.sin(Math.toRadians(this.angle + 90));
        inBounds = !(x > xMax || x < xMin || y > yMax || y < yMin);
        if (x > xMax){
            double distX = xMax - this.posX;
            x = xMax;
            y = this.posY - distX * Math.tan(Math.toRadians(this.angle + 90));
        }
        if (x < xMin){
            double distX = xMin - this.posX;
            x = xMin;
            y = this.posY - distX * Math.tan(Math.toRadians(this.angle + 90));
        }
        if (y > yMax){
            double distY = this.posY - yMax;
            y = yMax;
            x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
        }
        if (y < yMin){
            double distY = this.posY - yMin;
            y = yMin;
            x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
        }
        setPosition(x, y);
        updateRelativePosition();
        return new Position(x, y);
    }

    public boolean inBounds(){
        return inBounds;
    }

    public void setPosition(double x, double y) {
        posX = x;
        posY = y;
        updateRelativePosition();
    }

    public void rotate(double newAngle) {
        angle -= newAngle;
    }

    public void putPenDown() {
        penDown = true;
    }

    public void putPenUp() {
        penDown = false;
    }

    public void setShown(boolean shown){
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
    public void updateRelativePosition(){
        double width = xMax - xMin;
        double height = yMax - yMin;
        relX = posX- xMin - width / 2;
        relY = - posY + yMin + height / 2;
    }
    public boolean isPenDown() {
        return penDown;
    }

    public Position getPos() {
        return new Position(posX, posY);
    }
    public double getAngle() {
        return angle;
    }

    public double getXMin() {
        return xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMin() {
        return yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public Position getRelPos(){
        return new Position(relX, relY);
    }
}
