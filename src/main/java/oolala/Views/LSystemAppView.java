package oolala.Views;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oolala.Models.LSystemModel;
import oolala.Views.ViewComponents.LSystemTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LSystemAppView extends AppView {
    public final List<String> stampLabels = new ArrayList<>(Arrays.asList("SimpleLeafStamp", "OakLeafStamp", "MapleLeafStamp", "FireworkStamp"));

    public LSystemAppView(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new LSystemModel(canvasScreen, myResources, "SimpleLeafStamp", this);
    }

    public BorderPane setUpRootBorderPane() {
        rightToolBarHBox = makeRightToolbarHBox();
        imageSelector = makeImageSelector("StampChange", stampLabels);
        rightToolBarHBox.getChildren().add(1, imageSelector);
        terminal = new LSystemTerminal(textBoxWidth, textBoxHeight, myResources, currentApp, this, viewUtils);
        root.setLeft(terminal.getBox());
        root.setCenter(rightToolBarHBox);
        root.getChildren().add(canvasScreen.getShapes());
        root.setPadding(new Insets(10, 10, 10, 10));
        return root;
    }
}
