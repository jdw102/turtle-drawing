# OOLALA Design Report
### TEAM


## Team Roles and Responsibilities

 * Team Member #1 
Aditya Paul: parser, functionality of multiple turtles.

 * Team Member #2 
Luyao Wang: canvas, the model, different languages.
 * Team Member #3
Jerry Worthy: textbox, turtle class, styling, the model.


## Design goals

#### What Features are Easy to Add

Buttons to change brush and background color, text field for thickness are easy to add because they are only related their own classes.
The multi-language feature is also easy to add because everything is bundled up in `.properties` files.

## High-level Design

#### Core Classes

`OolalaGame`

`Parse`, `CanvasView`, and `TextBox` objected are created in this class. `TextBox` and `CanvasView` classes are class for display, while
`OolalaGame` is the model setting up the specific UI, and the `OolalaGame` also deals with the connection between the other 3 classes. For example, when the "Run" button is clicked, the text in 
the `TextArea` will be passed to `Parser`, and the interpreted instructions will be passed to a list of `Turtle` objects, which call the `drawLine` method in `canvasScreen` class.

## Assumptions that Affect the Design

We assume that the window is not resizable, which is an easier way to design this program. Nodes extending regions can be resized in an elegant way, but the area for drawing (we indicate this area by a rectangle) cannot be easily resized.
Therefore, when the window size is fixed, layout is easier, and we can focus on the design of the core elements of this program.

#### Features Affected by Assumptions

We assume that languages can be dynamically changed, so the way languages are changed in this program is to create a `setLanguage` method, which changes the text of all the occurrences where languages need to be changed.

## Significant differences from Original Plan

In our original plan, we made the `OolalaGame` class a simpler class whose responsibility is mainly to set up the scene, and all the specific UI components are in `CanvasScreen` and `TextBox` class. 
However, we found that in this way, the program is not very extensible, so we make `CanvasScreen` and `TextBox` classes only for view, which mainly contains `makeXXX` methods which do not specify a certain components, and we pass magical value to this method to create different components as an abstraction.

## New Features HowTo

#### Easy to Add Features

# set the Home location to a specific xy-location (default is the center of the display)

# set the Home location to a specific xy-location (default is the center of the display)

These two features are easy to add since we assume that the size of window do not change.

#### Other Features not yet Done

Animation. This feature is important, without which our program seems to be lack of animation and vivacity. This feature should be done since we have biological entities in the next part.