package oolala.Views;

import javafx.stage.Stage;
import oolala.Models.LogoModel;
import oolala.Views.ViewComponents.LogoTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * A view class for the Logo app extends the AppView class. It contains a Logo terminal
 * that has all the basic terminal inputs, the Logo app model, and an image selector that
 * changes the icons of the turtles.
 *
 * @author Jerry Worthy
 */
public class LogoAppView extends AppView {
    public final List<String> iconLabels = new ArrayList<>(Arrays.asList("TurtleIcon", "SimpleTurtleIcon", "TriangleArrowIcon"));

    public LogoAppView(Stage stage, String language) {
        super(stage, language);
        currentAppModel = new LogoModel(canvasScreen, myResources, "TurtleIcon", animation);
        terminal = new LogoTerminal(myResources);
        imageSelector = makeAppViewImageSelector("IconChange", iconLabels);
    }

}
