package oolala.Views;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oolala.Models.LSystemModel;
import oolala.Parsers.LSystemParser;
import oolala.Views.ViewComponents.LSystemTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * A view class for the LSystem app extends the AppView class. It contains an LSystem terminal
 * with sliders for input changes, the LSystem app model, and an image selector that
 * changes the stamps of the turtles.
 *
 * @author Jerry Worthy
 */
public class LSystemAppView extends AppView {
    public final List<String> stampLabels = new ArrayList<>(Arrays.asList("SimpleLeafStamp", "OakLeafStamp", "MapleLeafStamp", "FireworkStamp"));

    public LSystemAppView(Stage stage, String language) {
        super(stage, language);
        currentAppModel = new LSystemModel(canvasScreen, myResources, "SimpleLeafStamp", animation);
        terminal = new LSystemTerminal(myResources, (LSystemParser) currentAppModel.getParser());
        imageSelector = makeAppViewImageSelector("StampChange", stampLabels);
    }
}
