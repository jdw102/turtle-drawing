package oolala.Views;

import javafx.stage.Stage;
import oolala.Models.LogoModel;
import oolala.Views.ViewComponents.LogoTerminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogoAppView extends AppView {
    public final List<String> iconLabels = new ArrayList<>(Arrays.asList("TurtleIcon", "SimpleTurtleIcon", "TriangleArrowIcon"));

    public LogoAppView(Stage stage, String language) {
        super(stage, language);
        currentAppModel = new LogoModel(canvasScreen, myResources, "TurtleIcon", animation);
        terminal = new LogoTerminal(myResources);
        imageSelector = makeAppViewImageSelector("IconChange", iconLabels);
    }

}
