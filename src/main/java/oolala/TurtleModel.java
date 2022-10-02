package oolala;

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;

public class TurtleModel {
    private double homeX; //TODO: Will we init this in the canvas?
    private double homeY; //TODO: Will we init this in the canvas?
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

    public TurtleModel(int posX, int posY, Rectangle border, double iconSize){
        this.homeX = posX + border.getX() + border.getWidth()/2;
        this.homeY = posY + border.getY() + border.getHeight()/2;
        this.posX = homeX;
        this.posY = homeY;
        this.angle = DEFAULT_ANGLE;
        this.penDown = true;
        calcBounds(border, iconSize);
    }
    private void calcBounds(Rectangle r, double iconSize){
        xMin = r.getX() + iconSize / 2;
        xMax = r.getX() + r.getWidth() - iconSize / 2;
        yMin = r.getY() + iconSize / 2;
        yMax = r.getY() + r.getHeight() - iconSize / 2;
    }
    public Line createTurtlePath(double dist, CanvasScreen canvas){
        double x = this.posX + dist * Math.cos(Math.toRadians(this.angle + 90));
        double y = this.posY - dist * Math.sin(Math.toRadians(this.angle + 90));
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
        if (y < yMin) {
            double distY = this.posY - yMin;
            y = yMin;
            x = this.posX + distY / Math.tan(Math.toRadians(this.angle + 90));
        }
        Line l = createLine(posX, posY, x, y, canvas);
        posX = x;
        posY = y;
        return l;
    }
    public void moveHome(){
        posX = homeX;
        posY = homeY;
    }
    public void rotate(double newAngle){
        angle -= newAngle;
    }
    public void putPenDown(){
        penDown = true;
    }
    public void putPenUp(){
        penDown = false;
    }
    public void updateRelativePosition(ImageView i, Tooltip tooltip){
        relX = i.getX() - xMin;
        relY = yMax - i.getY();
        tooltip.setText("x: " + Double.toString(Math.round(relX)) + " y: " + Double.toString(Math.round(relY)));
    }
    private Line createLine(double xStart, double yStart, double xEnd, double yEnd, CanvasScreen canvas) {
        Line line = new Line();
        line.setStartX(xStart);
        line.setStartY(yStart);
        line.setEndX(xEnd);
        line.setEndY(yEnd);
        line.setStrokeWidth(canvas.getThickness());
        line.setStroke(canvas.getBrushColor());
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        return line;
    }
    public boolean isPenDown(){
        return penDown;
    }
    public double getHomeX(){
        return homeX;
    }
    public double getHomeY(){
        return homeY;
    }
    public double getPosX(){
        return posX;
    }
    public double getPosY(){
        return posY;
    }
    public double geAngle(){
        return angle;
    }
}
