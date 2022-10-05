package oolala.Views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oolala.Models.LogoModel;
import oolala.Views.ViewComponents.LogoRunInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogoAppView extends AppView {
    public final List<String> iconLabels = new ArrayList<>(Arrays.asList("TurtleIcon", "SimpleTurtleIcon", "TriangleArrowIcon"));

    public LogoAppView(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new LogoModel(canvasScreen, myResources, "TurtleIcon", this);
    }

    public BorderPane setUpScene() {
        rightToolBarHBox = makeRightToolbarHBox();
        imageSelector = makeImageSelector("IconChange", iconLabels);
        rightToolBarHBox.getChildren().add(0, imageSelector);
        runInterface = new LogoRunInterface(textBoxWidth, textBoxHeight, myResources, currentApp, this, viewUtils);
        root.setLeft(runInterface.getBox());
        root.setCenter(rightToolBarHBox);
        root.getChildren().add(canvasScreen.getShapes());
        root.setPadding(new Insets(10, 10, 10, 10));
        return root;
    }
}
