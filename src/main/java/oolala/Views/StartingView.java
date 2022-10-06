package oolala.Views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;



public class StartingView {
    private BorderPane root;
    private ComboBox<String> languageSelector;
    public static ResourceBundle myResources;
    private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private static final String STARTING_LANGUAGE = "English";
    private Button startButton;
    private ObservableList<String> languageOptions = FXCollections.observableArrayList("English", "日本語", "简体中文", "繁體中文");

    public Scene setUpScene(int sizeWidth, int sizeHeight, EventHandler<ActionEvent> startApp){
        root = new BorderPane();
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STARTING_LANGUAGE);
        languageSelector = makeLanguageSelector();
        languageSelector.getStyleClass().add("start-combo-box");
        languageSelector.setId("LanguageSelector");
        Label languageLabel = new Label("Select Language");
        languageLabel.getStyleClass().add("start-label");
        startButton = new Button(myResources.getString("StartButton"));
        startButton.setOnAction(startApp);
        startButton.getStyleClass().add("start-button");
        VBox box = new VBox(languageLabel, languageSelector, startButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        root.setCenter(box);
        Scene scene = new Scene(root, sizeWidth, sizeHeight);
        return scene;
    }
    private ComboBox<String> makeLanguageSelector(){
        ComboBox<String> c = new ComboBox<>(languageOptions);
        c.setValue(languageOptions.get(0));
        return c;
    }
    public String getLanguage(){
        return languageSelector.getValue();
    }

}
