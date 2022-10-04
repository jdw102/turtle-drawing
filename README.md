OOLALA
====

This project implements a variety of applications that take advantage of drawing using a turtle.

Names:

Aditya Paul, Luyao Wang, Jerry Worthy


### Timeline

Start Date: 9/21/2022

Finish Date: 9/26/2022

Hours Spent: 36

### Primary Roles

Aditya Paul: parsers of LOGO and L-Syetem, functionality of multiple turtles.

Luyao Wang: the model, GUI, different languages.

Jerry Worthy: turtle class, GUI, styling, the model, splashscreen.

### Resources Used

https://horstmann.com/corejava/corejava_11ed-bonuschapter13-javafx.pdf

https://stackoverflow.com/questions/52124603/how-to-fix-the-window-size-in-scene-builder

https://coursework.cs.duke.edu/compsci307_2022fall/example_browser

Collaborators:

Attributions for others' work:

### Running the Program

Main class: Main.java

How to use the program: 

When the program is run, we can choose which app (LOGO and L-System) and languages (English, Japanese, Simplified Chinese, Traditional Chinese) in the splashscreen.

In the App of LOGO, the program takes in a set of instructions to control the current turtle(s). On the left side, one can type in one line or multiple lines of instructions and click on the "Run" button or Alt + Enter to produce drawing.
You can import a txt file with valid instructions, or save your instructions to a local directory to preserve your masterpiece. You can also click on the "Empty" button to empty the commands (it effect won't change) if you think the commands are too messy.
Previous commands are visible below, and you can click on it so that the command is added to your text box, and you do not need to type again.

Canvas is on the right side, which displays your drawing. You can use the color selector to choose a color. The first color selector is background color selector, and the second one is the brush color. You can also hover over the button or the color you select to view the detail.
To the right is a text field in which you can type a positive number as the width of the brush. When you click on "reset", the lines you draw will not change, and a turtle will be put at origin while all the other turtles are removed. When you click on "clear", everything on the screen is removed, leaving a turtle at origin.
You can save the image you draw by clicking on "Save" button. The icon of the brush (turtle) can be changed by choosing one icon from the combo box.

In the App of L-System, the program takes in a starting string (e.g., a simple string like F), a set of production rules (e.g., F-F++F-F). The self-similar shapes are generated based on level, the times of iteration to expand the expression.

Aside from the features in the previous app, you can also adjust level, angle, and line Length using sliders.

Data files needed: 

Resources\Images\firework.png, mapleleaf.png, oakleaf.png, painbrush.png, plant.png, simpleleaf.png, simpleturtle.png, trianglearrow.png, turtleicon.png.

resources\Properties\default.css, English.properties, 日本語.properties (Japanese), 简体中文.properties (Simplified Chinese), 繁體中文.properties (Traditional Chinese). 

Reminder, set file encoding to UTF-8 when running this program since these asia languages are poorly supported in some encodings.

Features implemented:

* enter and run programs interactively
* display the results of executing the commands visually
* see errors that may result from entered commands in a user friendly way (i.e., not just printed to the console or crashing the program)
* load a program from a file
* save the current program to a file
* reset the environment back to its starting state
* see commands previously entered and reuse them again easily (perhaps by clicking on them rather than having to copy/paste them by hand)
* view any turtle's state (i.e., xy-location and heading)
* ~~heading not implemented~~
* set a color and thickness to use for the turtles' pen
* set an image to use for the current turtles
* set a background color for the display area
* ~~Not implemented: set the Home location to a specific xy-location (default is the center of the display)~~
* ~~Not implemented: set SET symbol "Logo Commands" might not work well~~

### Notes/Assumptions

Assumptions or Simplifications:

The window size is fixed by `stage.setResizable(false)`. In this way, we do not have to worry about resizing, especially the area for drawing (it might be implemented later).

Interesting data files:

`circle_tell.txt` which would be more interesting if animation is enabled.

Known Bugs:

Used instructions are not removed, and can be reused.

The order in which previous commands are placed in command history might not be expected.

When shapes get beyond the canvas in the L-System app, the brush still move around the border.

Extra features:

### Impressions

App1:

In this part one of the Oolala project, we implemented some basic functions of a logo IDE. In a team-based way, we come to realize that
the code one writes should not only work well separately, but also work well with others' code. Besides, we discussed much so that we can have a consensus on the design idea, which is the key in the model part.
We think it is a good practice to communicate with teammates often to update teammates on your progress, which makes a active team atmosphere.

App2:

In the part two of the L-System, we create polymorphism for some classes. For example, we created multiple subclasses of the command class specifying which the kind of command (such as forward, right turn). Besides, we make the model of the two apps subclasses of an abstract AppModel class.
These way of polymorphism can help us write clean code, since we do not need to write lengthy switch syntax or writing duplicate code.