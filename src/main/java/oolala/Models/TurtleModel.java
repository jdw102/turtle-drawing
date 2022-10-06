package oolala.Models;

import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import oolala.Views.ViewComponents.CanvasScreen;

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

    public TurtleModel(double posX, double posY, Rectangle border, double iconSize) {
        this.posX = posX + border.getX() + border.getWidth() / 2;
        this.posY = posY + border.getY() + border.getHeight() / 2;
        this.angle = DEFAULT_ANGLE;
        this.penDown = true;
        calcBounds(border, iconSize);
    }

    private void calcBounds(Rectangle r, double iconSize) {
        xMin = r.getX() + iconSize / 2;
        xMax = r.getX() + r.getWidth() - iconSize / 2;
        yMin = r.getY() + iconSize / 2;
        yMax = r.getY() + r.getHeight() - iconSize / 2;
    }

    public Position calculateMove(double dist) {
        double x = this.posX + dist * Math.cos(Math.toRadians(this.angle + 90));
        double y = this.posY - dist * Math.sin(Math.toRadians(this.angle + 90));
        if (x > xMax) {
            double distX = xMax - this.posX;
            x = xMax;
            y = this.posY - distX * Math.tan(Math.toRadians(this.angle + 90));
        }
        if (x < xMin) {
            double distX = xMin - this.posX;
            x = xMin;
            y = this.posY - distX * Math.tan(Math.toRadians(this.angle + 90));
        }
        if (y > yMax) {
            double distY = this.posY - yMax;
            y = yMax;
            x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
        }
        if (y < yMin) {
            double distY = this.posY - yMin;
            y = yMin;
            x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
        }
        setPosition(x, y);
        return new Position(x, y);
    }

    public void setPosition(double x, double y) {
        posX = x;
        posY = y;
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

    public void updateRelativePosition(ImageView i, Tooltip tooltip) {
        double width = xMax - xMin;
        double height = yMax - yMin;
        relX = i.getX() - xMin - width / 2 + i.getFitWidth() / 2;
        relY = -(i.getY() - yMin - height / 2 + i.getFitWidth() / 2);
        tooltip.setText("x: " + Double.toString(Math.round(relX)) + " y: " + Double.toString(Math.round(relY)));
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

    public double getRelX() {
        return relX;
    }

    public double getRelY() {
        return relY;
    }
}
