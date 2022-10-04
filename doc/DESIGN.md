# OOLALA Design Report
### TEAM


## Team Roles and Responsibilities

 * Team Member #1
   Aditya Paul: parsers of LOGO and L-Syetem, functionality of multiple turtles.
 * Team Member #2
   Luyao Wang: the model, GUI, different languages.
 * Team Member #3
   Jerry Worthy: turtle class, GUI, styling, the model, splashscreen.


## Design goals

#### What Features are Easy to Add

Buttons to change brush and background color, text field for thickness are easy to add because they are only related their own classes.
The multi-language feature is also easy to add because everything is bundled up in `.properties` files.

## High-level Design

#### Core Classes

`AppModel`

`AppModel` has two subclasses `LogoModel` and `LSystemModel`

`AppView`

`Parser`, `CanvasScreen`, `TextBox`, and `ToolBar` objected are created in `AppView`. `AppModel` are also created in this class.

## Assumptions that Affect the Design

We assume that the window is not resizable, which is an easier way to design this program. Nodes extending regions can be resized in an elegant way, but the area for drawing (we indicate this area by a rectangle) cannot be easily resized.
Therefore, when the window size is fixed, layout is easier, and we can focus on the design of the core elements of this program.

#### Features Affected by Assumptions


## Significant differences from Original Plan

In our original plan, we did not separate model from view.
However, we found that in this way, the program is not extensible, so we make two subclasses inheriting the `AppModel` class.

## New Features HowTo

#### Easy to Add Features

set the Home location to a specific xy-location (default is the center of the display)

#### Other Features not yet Done

Added: Animation. This feature is important, without which our program seems to be lack of animation and vivacity. This feature should be done since we have biological entities in the next part.

Tests for GUI

Option for enable animation or not