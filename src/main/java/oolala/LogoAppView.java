package oolala;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LogoAppView extends AppView {

    private HBox logoRightHBox;
    private ComboBox<ImageView> logoImageSelector;

    public LogoAppView(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new TurtleDrawingModel(canvasScreen, myResources, "TurtleIcon", this);
    }

    public Scene setUpScene() {
        logoRightHBox = makeRightToolbarHBox();
        imageSelector = makeBrushImageSelector();
        logoRightHBox.getChildren().add(0, imageSelector);
        textBox = new TextBox(textBoxWidth, textBoxHeight, myResources, currentApp, this, viewUtils);
        root.setLeft(textBox.getBox());
        root.setCenter(logoRightHBox);
        root.getChildren().add(canvasShapes);

        root.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(root, sizeWidth, sizeHeight);
        return scene;
    }

    private ComboBox<ImageView> makeBrushImageSelector() {
        ComboBox<ImageView> imageSelector = viewUtils.makeImageSelector(iconLabels, "IconChange");
        imageSelector.setOnAction(event -> {
            currentApp.changeIcon(imageSelector.getValue().getImage().getUrl());
        });
        return imageSelector;
    }
}
