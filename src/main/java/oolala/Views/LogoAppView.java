package oolala.Views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
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

    public Scene setUpScene() {
        rightToolBarHBox = makeRightToolbarHBox();
        imageSelector = makeImageSelector("IconChange", iconLabels);
        rightToolBarHBox.getChildren().add(1, imageSelector);
        runInterface = new LogoRunInterface(textBoxWidth, textBoxHeight, myResources, currentApp, this, viewUtils);
        root.setLeft(runInterface.getBox());
        root.setCenter(rightToolBarHBox);
        root.getChildren().add(canvasScreen.getShapes());
        root.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(root, sizeWidth, sizeHeight);
        return scene;
    }
}
