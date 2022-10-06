package oolala;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import oolala.Views.AppView;
import oolala.Views.LSystemAppView;
import oolala.Views.LogoAppView;
import oolala.Views.StartingView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.DukeApplicationTest;

import javax.swing.text.View;

import static oolala.Main.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppViewTest extends DukeApplicationTest {
    private StartingView startingView;
    // keep GUI components used in multiple tests



    // this method is run BEFORE EACH test to set up application in a fresh state
    @Override
    public void start (Stage stage) {
        EventHandler<ActionEvent> startApp = event -> {
            stage.close();
            String language = startingView.getLanguage();

            TabPane tabPane = new TabPane();
            AppView view1 = new LogoAppView(SIZE_WIDTH, SIZE_HEIGHT, stage, language);
            AppView view2 = new LSystemAppView(SIZE_WIDTH, SIZE_HEIGHT, stage, language);
            Tab tab1 = new Tab("Logo", view1.setUpRootBorderPane());
            Tab tab2 = new Tab("L-System", view2.setUpRootBorderPane());
            tabPane.getTabs().add(tab1);
            tabPane.getTabs().add(tab2);

            Scene scene = new Scene(tabPane, SIZE_WIDTH, SIZE_HEIGHT);
            scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(TITLE);
            stage.setResizable(false);
            stage.show();
        };
        // create application and add scene for testing to given stage
        startingView = new StartingView();
        Scene startScene = startingView.setUpScene(START_WIDTH, START_HEIGHT, startApp);
        startScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
        stage.setTitle(TITLE);
        stage.setScene(startScene);
        stage.setResizable(false);
        stage.show();
    }
    @ParameterizedTest
    @CsvSource({
            "English",
            "日本語",
            "简体中文",
            "繁體中文",
            "WHAT"
    })
    void testLanguageSelector (String language) {

        ComboBox<String> languages = lookup("#LanguageSelector").query();
        //ChoiceBox<String> options = lookup("#Options").query();
        //ListView<String> options = lookup("#Options").query();
        String expected = language;
        // GIVEN, app first starts up
        // WHEN, choice is made using a combo box
        select(languages, expected);
        assertEquals(expected, languages.getValue());
        // THEN, check label text has been updated to match input
    }
    @Test
    void testStartButton(){
        Button startButton = lookup("StartButton").query();
        clickOn(startButton);

    }



}
