package oolala;


import javafx.scene.paint.Color;

public class ColorChoice {

    private String colorName;
    private Color color;

    public ColorChoice(String colorName, Color color) {
        this.colorName = colorName;
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return this.getColorName();
    }
}