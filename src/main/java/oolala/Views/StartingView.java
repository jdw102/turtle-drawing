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
import oolala.Views.ViewComponents.ViewUtils;

import java.util.ResourceBundle;

import static oolala.Views.ViewComponents.ViewUtils.makeButton;

/**
 * A view class to select the language and start the apps.
 *
 * @author Jerry Worthy
 */
public class StartingView {
    private ViewUtils viewUtils;
    private BorderPane root;
    private ComboBox<String> languageSelector;
    public static ResourceBundle myResources;
    private final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
    private final String STARTING_LANGUAGE = "English";
    private final ObservableList<String> languageOptions = FXCollections.observableArrayList("English", "日本語", "简体中文", "繁體中文");

    public Scene setUpScene(int sizeWidth, int sizeHeight, EventHandler<ActionEvent> startApp) {
        root = new BorderPane();
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STARTING_LANGUAGE);
        viewUtils = new ViewUtils(myResources);
        languageSelector = makeLanguageSelector("LanguageSelector");
        Label languageLabel = new Label("Select Language");
        languageLabel.getStyleClass().add("start-label");
        Button startButton = makeButton("StartButton", startApp);
        startButton.getStyleClass().add("start-button");
        VBox box = new VBox(languageLabel, languageSelector, startButton);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(30);
        root.setCenter(box);
        Scene scene = new Scene(root, sizeWidth, sizeHeight);
        return scene;
    }

    /**
     * A method to create the language selection combobox using the language options list.
     * It makes the id the String name.
     *
     * @param name - The id of the combo box.
     * @return The combo box of language strings.
     * @author Jerry Worthy
     */
    private ComboBox<String> makeLanguageSelector(String name) {
        ComboBox<String> c = new ComboBox<>(languageOptions);
        c.setValue(languageOptions.get(0));
        c.getStyleClass().add("start-combo-box");
        c.setId(name);
        return c;
    }

    public String getLanguage() {
        return languageSelector.getValue();
    }

}
