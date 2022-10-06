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

public class LSystemAppView extends AppView {
    public final List<String> stampLabels = new ArrayList<>(Arrays.asList("SimpleLeafStamp", "OakLeafStamp", "MapleLeafStamp", "FireworkStamp"));

    public LSystemAppView(Stage stage, String language, String defaultResourceFolder, String styleSheet, String darkModeStyleSheet) {
        super(stage, language, defaultResourceFolder, styleSheet, darkModeStyleSheet);
        currentAppModel = new LSystemModel(canvasScreen, myResources, "SimpleLeafStamp", this, animation);
        terminal = new LSystemTerminal(myResources, (LSystemParser) currentAppModel.getParser());
        imageSelector = makeImageSelector("StampChange", stampLabels);
    }
}
