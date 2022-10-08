package oolala.Views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import oolala.Views.AppView;
import oolala.Views.LSystemAppView;
import oolala.Views.LogoAppView;
import oolala.Views.StartingView;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.DukeApplicationTest;

import static oolala.Main.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StartViewTest extends DukeApplicationTest {
    private final int SIZE_WIDTH = 840;
    private final int SIZE_HEIGHT = 650;
    private final int START_WIDTH = 400;
    private final int START_HEIGHT = 500;
    private StartingView startingView;
    private final String TITLE = "Oolala";

    @Override
    public void start (Stage stage) {
        EventHandler<ActionEvent> startApp = event -> {
            stage.close();
            String language = startingView.getLanguage();

            TabPane tabPane = new TabPane();
            Scene scene = new Scene(tabPane, SIZE_WIDTH, SIZE_HEIGHT);
            AppView view1 = new LogoAppView(stage, language);
            AppView view2 = new LSystemAppView(stage, language);
            Tab tab1 = new Tab("Logo", view1.setUpRootBorderPane());
            Tab tab2 = new Tab("L-System", view2.setUpRootBorderPane());
            tabPane.getTabs().add(tab1);
            tabPane.getTabs().add(tab2);

            scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
            stage.setScene(scene);
            stage.setTitle(TITLE);
            stage.setResizable(false);
            stage.show();

        };

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
            "繁體中文"
    })
    void testLanguageSelector (String language) {

        ComboBox<String> languages = lookup("#LanguageSelector").query();
        String expected = language;
        select(languages, expected);
        assertEquals(expected, languages.getValue());
    }

}
