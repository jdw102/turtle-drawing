package oolala;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ResourceBundle;

public class StartingView {
    private BorderPane root;
    private ComboBox<String> appSelector;
    private ComboBox<String> languageSelector;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private static final String STARTING_LANGUAGE = "English";
    private Button startButton;
    private ObservableList<String> applicationLabels = FXCollections.observableArrayList("Logo", "LSystem");
    private ObservableList<String> languageOptions = FXCollections.observableArrayList("English", "日本語", "简体中文", "繁體中文");

    public Scene setUpScene(int sizeWidth, int sizeHeight, EventHandler<ActionEvent> startApp){
        root = new BorderPane();
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STARTING_LANGUAGE);
        appSelector = makeAppSelector();
        languageSelector = makeLanguageSelector();
        languageSelector.getStyleClass().add("start-combo-box");
        appSelector.getStyleClass().add("start-combo-box");
        Label appLabel = new Label("Select App");
        Label languageLabel = new Label("Select Language");
        appLabel.getStyleClass().add("start-label");
        languageLabel.getStyleClass().add("start-label");
        startButton = new Button("START");
        startButton.setOnAction(startApp);
        startButton.setMinWidth(100);
        VBox box = new VBox(appLabel, appSelector, languageLabel, languageSelector);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        root.setCenter(box);
        root.setBottom(startButton);
        BorderPane.setAlignment(startButton, Pos.CENTER);
        Scene scene = new Scene(root, sizeWidth, sizeHeight);
        return scene;
    }

    private ComboBox<String> makeAppSelector(){
        ComboBox<String> c = new ComboBox<>(applicationLabels);
        c.setButtonCell(new ListCell<String>(){
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                setGraphic(null);
                setText(null);
                if(item!=null){
                    ImageView imageView = new ImageView(new Image(myResources.getString(item)));
                    imageView.setFitWidth(20);
                    imageView.setFitHeight(20);
                    setGraphic(imageView);
                    setText(item);
                }
            }
        });
        c.setValue(applicationLabels.get(0));
        return c;
    }
    private ComboBox<String> makeLanguageSelector(){
        ComboBox<String> c = new ComboBox<>(languageOptions);
        c.setValue(languageOptions.get(0));
        return c;
    }
    public String getAppName(){
        return appSelector.getValue();
    }
    public String getLanguage(){
        return languageSelector.getValue();
    }
    public void setStartButtonAction(EventHandler<ActionEvent> handler){
        startButton.setOnAction(handler);
    }
}
