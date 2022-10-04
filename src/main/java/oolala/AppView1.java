package oolala;

import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppView1 extends AppView {

    private HBox logoRightHBox;

    public AppView1(int sizeWidth, int sizeHeight, Stage stage, String language) {
        super(sizeWidth, sizeHeight, stage, language);
        currentApp = new TurtleDrawingModel(canvasScreen, myResources, "TurtleIcon", this);
        textBox = new TextBox(textBoxWidth, textBoxHeight, myResources, currentApp, this, viewUtils);
        logoRightHBox = getRightToolBarHBox();
        imageSelector = makeImageSelector("IconChange");
        logoRightHBox.getChildren().add(0, imageSelector);
    }
}
