package oolala;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.StringConverter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static oolala.Command.CmdName.TELL;


public class CanvasScreen {

    //TODO: ArrayList of turtles
    private Canvas canvas;
    private Rectangle borderRectangle;
    private VBox vBox;
    private Group shapes;
    private HBox hBox;
    private HBox stylingBox;
    private HBox settingsBox;
    private GraphicsContext gc;
    private HashMap<Integer, Turtle> turtles;
    private ArrayList<Integer> currTurtleIdxs;

    private ComboBox<String> languagesComboBox;
    private ComboBox<ColorChoice> colorsComboBox;
    private ResourceBundle myResources;
    private Color COLOR = Color.BLACK;
    private Double THICKNESS = 3.0;
    private double buttonSpacing;
    private ArrayList<String> labels = new ArrayList<String>(Arrays.asList("ClearCanvasButton", "ResetTurtleButton", "SaveButton"));
    private ArrayList<String> langs = new ArrayList<String>(Arrays.asList("English", "简体中文", "繁體中文", "日本語"));
    private ArrayList<ColorChoice> colors = new ArrayList<ColorChoice>(Arrays.asList(new ColorChoice("Black", Color.BLACK), new ColorChoice("Red", Color.RED), new ColorChoice("Blue", Color.BLUE)));

    public CanvasScreen(ResourceBundle myResources) {
        this.myResources = myResources;
        shapes = new Group();
        vBox = new VBox();
//        vBox.setPrefHeight(600);
//        vBox.setPrefWidth(500);
        vBox.setMinSize(400, 500);

//        colorsComboBox = makeComboBoxColor(colors);

//        EventHandler<ActionEvent> colorCommand = event -> {
//            Color clr = colorsComboBox.getValue().getColor();
//            changeColor(clr);
//        };
//        colorsComboBox.setOnAction(colorCommand);

//        hBox = new HBox(clearButton, resetButton, saveButton, languagesComboBox);
//        hBox.setAlignment(Pos.TOP_RIGHT);

        borderRectangle = new Rectangle(300, 50, 500, 540);
        shapes.getChildren().add(borderRectangle);
        borderRectangle.setFill(Color.AZURE);

        buttonSpacing = borderRectangle.getWidth();

        createHBox();

        //Area indicator
//        vBox.getChildren().add(Buttons);
//        borderRectangle = new Rectangle();
//        vBox.getChildren().add(borderRectangle);
//        borderRectangle.widthProperty().bind(vBox.widthProperty().subtract(60));
//        borderRectangle.heightProperty().bind(vBox.heightProperty().subtract(60));
//        borderRectangle.translateXProperty().bind((vBox.widthProperty().divide(2)).subtract((borderRectangle.widthProperty().divide(2))));
//        borderRectangle.translateYProperty().bind((vBox.heightProperty().divide(2)).subtract((borderRectangle.heightProperty().divide(2))));

        vBox.getChildren().add(hBox);


        turtles = new HashMap<>();
        currTurtleIdxs = new ArrayList<>();
        turtles.put(1, new Turtle(1, 0, 0, borderRectangle));
        currTurtleIdxs.add(1);
        shapes.getChildren().add(turtles.get(1).getIcon());
    }

    public void setCommands(ArrayList<Command> commands, OolalaView display) {
        Iterator<Command> itCmd = commands.iterator();
        while (itCmd.hasNext()) {
            Command instruction = itCmd.next();
            // Handle tell command
            if(instruction.prefix == TELL){
                currTurtleIdxs.clear();
                currTurtleIdxs.addAll(instruction.params);
                for (Integer param : instruction.params){
                    if (!turtles.containsKey(param)){
                        System.out.println("Creating new turtle");
                        turtles.put(param, new Turtle(param, 0, 0, borderRectangle));
                        shapes.getChildren().add(turtles.get(param).getIcon());
                    }
                }
            }
            for(Integer idx : currTurtleIdxs){
                turtles.get(idx).readInstruction(instruction, display);
            }
            itCmd.remove();
        }
    }

    private void setThickness(String value) {
        Alert alert = new Alert(Alert.AlertType.ERROR,
                myResources.getString("NumberFormatException"));
        try {
            double doubleValue = Double.parseDouble(value);
            if (doubleValue > 0)
                THICKNESS = doubleValue;
            else
                alert.showAndWait();
        } catch (NumberFormatException e) {
            alert.showAndWait();
        }
    }

    private void changeColor(Color clr) {
        COLOR = clr;
    }

    private void screenShot() {
        WritableImage snapshot = shapes.snapshot(null, null);
        File file = new File("snapshot.png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    public Turtle[] getTurtles() {
        Turtle[] currTurtles = new Turtle[currTurtleIdxs.size()];
        for(int i = 0; i < currTurtleIdxs.size(); i++)
            currTurtles[i] = turtles.get(currTurtleIdxs.get(i));
        return currTurtles;
    }


    /**
     * A method to draw a new line on the canvas.
     *
     * @param xStart    x coordinate of the start point
     * @param yStart
     * @param xEnd
     * @param yEnd
     * @author Luyao Wang
     */
    public void drawLine(double xStart, double yStart, double xEnd, double yEnd) {
        System.out.println(xStart);
        System.out.println(yStart);
        System.out.println(xEnd);
        System.out.println(yEnd);
        Line line = new Line();
//        line.setStartX(SIZE_WIDTH / 2.0 + xStart);
//        line.setStartY(SIZE_HEIGHT / 2.0 - yStart);
        line.setStartX(xStart);
        line.setStartY(yStart);
//        line.setEndX(SIZE_WIDTH / 2.0 + x + length * Math.cos(Math.toRadians(direction)));
//        line.setEndY(SIZE_HEIGHT / 2.0 - y - length * Math.sin(Math.toRadians(direction)));
        line.setEndX(xEnd);
        line.setEndY(yEnd);
        line.setStrokeWidth(THICKNESS);
        //line.setFill(color);
        line.setStroke(COLOR);
        //TODO: is color an attribute of a turtle?
        shapes.getChildren().add(line);
    }

    public void reset() {
        turtles.clear(); // TODO: Check if this is correct functionality
        currTurtleIdxs.clear();

        turtles.put(1, new Turtle(1, 0, 0, borderRectangle));
        shapes.getChildren().add(turtles.get(1).getIcon()); // TODO: We should probably refactor this for scalability
        currTurtleIdxs.add(1);
    }

    public void clear() {
        //same way of implementing
//        for (Node i : shapes.getChildren()) {
//            if (i instanceof Line)
//                shapes.getChildren().remove(i);
//        }
        shapes.getChildren().removeIf(i -> i instanceof Line);
        shapes.getChildren().removeIf(i -> i instanceof ImageView);
        reset();
    }

    public VBox getVBox() {
        return vBox;
    }

    public Group getShapes() {
        return shapes;
    }

    public ComboBox<String> getLanguagesComboBox() {
        return languagesComboBox;
    }
    private TextField makeTextField() {
        TextField textField = new TextField("Thickness");
        textField.setPrefWidth(40);

        return textField;
    }

    private ComboBox<String> makeComboBoxArrayList(ArrayList<String> items) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setValue(items.get(0));//Default language

        return comboBox;
    }

    private ComboBox<ColorChoice> makeComboBoxColor(ArrayList<ColorChoice> items) {
        ComboBox<ColorChoice> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(items);
        comboBox.setValue(items.get(0));//Default color

        return comboBox;
    }


    private Button makeButtons(String property, EventHandler<ActionEvent> handler) {
        Button result = new Button();
        String label = myResources.getString(property);
        result.setText(label);
        result.setOnAction(handler);
        return result;
    }

    public void setLanguage(ResourceBundle resources) {
        myResources = resources;
        int i = 0;
        for (Node n : hBox.getChildren()) {
            if (n instanceof Button) {
                updateButtonLanguage((Button) n, labels.get(i));
                i++;
            }
        }
    }

    public void updateButtonLanguage(Button button, String property) {
        String label = myResources.getString(property);
        button.setText(label);
    }
    public void createHBox(){
        EventHandler<ActionEvent> clearCommand = event -> clear();
        EventHandler<ActionEvent> resetCommand = event -> reset();
        EventHandler<ActionEvent> saveCommand = event -> screenShot();
        Button clearButton = makeButtons(labels.get(0), clearCommand);
        Button resetButton = makeButtons(labels.get(1), resetCommand);
        Button saveButton = makeButtons(labels.get(2), saveCommand);

        languagesComboBox = makeComboBoxArrayList(langs);

        TextField thicknessTextField = makeTextField();
        thicknessTextField.setText("1");
        EventHandler<ActionEvent> thicknessCommand = event -> setThickness(thicknessTextField.getText());
        thicknessTextField.setOnAction(thicknessCommand);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        EventHandler<ActionEvent> setColor = event -> {
            COLOR = colorPicker.getValue();
        };
        colorPicker.setOnAction(setColor);

        hBox = new HBox(colorPicker, thicknessTextField, clearButton, resetButton, saveButton);
        hBox.setAlignment(Pos.TOP_RIGHT);
    }
}
