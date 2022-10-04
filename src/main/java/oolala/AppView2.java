package oolala;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;

public class AppView2 extends AppView {

    public AppView2(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new LSystemModel(canvasScreen, myResources, "SimpleLeafStamp", this);
        textBox = new TextBox(textBoxWidth, textBoxHeight, myResources, currentApp, this, viewUtils);
        rightToolBarHBox = makeRightToolbarHBox();
        imageSelector = makeImageSelector("StampChange");
        rightToolBarHBox.getChildren().add(0, imageSelector);
        root.setLeft(textBox.getBox());
        root.setCenter(rightToolBarHBox);
        root.getChildren().add(canvasScreen.getShapes());
        root.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(root, sizeWidth, sizeHeight);

    }

}
