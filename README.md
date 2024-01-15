# ISCrush

## Table of contents
<details>
<summary>Show</summary>

<!-- TOC -->
* [ISCrush](#iscrush)
  * [Table of contents](#table-of-contents)
  * [Introduction](#introduction)
  * [Configuration of the game](#configuration-of-the-game)
  * [Rules](#rules)
    * [Bonus](#bonus)
  * [Controls](#controls)
    * [Mouse](#mouse)
    * [Console](#console)
  * [Structure of the game](#structure-of-the-game)
  * [Logic flow of the game](#logic-flow-of-the-game)
    * [Initialization](#initialization-)
    * [Mainloop](#mainloop)
    * [Input Handling](#input-handling)
    * [Processing Combos](#processing-combos)
<!-- TOC -->
</details>

## Introduction
Welcome to the best game of the world ! In this game you will be able 
to move the head of your favorite teachers to win points. You will discover a
unique game design and a wonderful gameplay.

![screenshot_1.png](images/screenshot_1.png)


## Configuration of the game
When you launch the game you will have to choose the size of the grid in the console. Then choose the number 
of teacher that you really like. Finally, choose if you want to play in the console or in 
a well-designed window.

## Rules
Move the teachers to make combinations of at least three identical teachers. If you do it, you win some points. 
You can also win more points by doing combinations of more than three heads.

Be cautious, you can only move a teacher with its direct neighbours. If the move doesn't cause a combo, they will 
switch back to their original place.

When you reach the score of 100 you win the game. Congrats !

### Bonus
When you do a combo of more than three teachers, a new kind of teacher appears : bonuses

| Name       | Effect                                                        | How to obtain it             |
|:-----------|:--------------------------------------------------------------|:-----------------------------|
| **Row**    | all teachers in the row are destroyed                         | four of a kind horizontally  |
| **Column** | all teachers in the column are destroyed                      | four a kind vertically       |
| **Bomb**   | all teachers around the selected teacher (3x3) are destroyed  | five of a kind               |

![screenshot_1.png](images/screenshot_2.png)

## Controls
### Mouse
Click on the teacher (left click) and drag it in the direction you want to swap it (let go of the mouse button).
It will move if a combo exists.

### Console
Find the teacher that you want to move. Write the number of the line and column of this incredible teacher. Then
choose the direction of the teacher you want to switch it with. 
- 0 : switch to the right
- 1 : switch to the bottom
- 2 : switch to the left
- 3 : switch to the top

## Structure of the game
To launch the game you will have to use the ISCrush object.

| Class / Object     | Purpose                                                                              |
|:-------------------|:-------------------------------------------------------------------------------------|
| **ISCrush**        | Main object which coordinates all the elements (contains the main function)          |
| **Candy**          | Class which creates all the teachers                                                 |
| **GridManager**    | Generates the grid and handles all the logic (moving candies, combos, bonuses, etc.) |
| **GridRenderer**   | Renders the grid                                                                     |
| **InputHandler**   | Allows us to use the mouse or the keyboard                                           |
| **ConsoleManager** | Allows us to play in the console                                                     |
| **MouseManager**   | Allows us to use the mouse to play the game                                          |
| **Input**          | Allows us to use the keyboard                                                        |
| **Pos**            | Stores a 2D position (for candies)                                                   |
| **Score**          | Stores the score of the game                                                         |
| **Audio**          | Allow us to play audio                                                               |
| **AudioManager**   | Manages audio resources                                                              |

## Logic flow of the game

### Initialization 
When `ISCrush` is launched, after all the settings are entered, the different managers are created, namely:
 - A `GridManager` to manage the grid (what a surprise)
 - A `GridRenderer` to render the grid
 - An `InputHandler` to manage inputs (would that be from the console or with the mouse)
 - A `Score` instance to keep track of the player's progress

When the `GridManager` is created, a 2D array is built and filled with random `Candy` instances.
Before starting the game, the manager makes sure that there is no combo in the grid by repeatedly simplifying available combos and refilling the grid (see [Processing Combos](#processing-combos))

### Mainloop
The main game loop is pretty straightforward. The following steps are executed on every frame (as long as the game is running):
 1. If there is an input available, and it is not already being processed
    1. A new thread is started to handle it
 2. The grid is rendered

### Input Handling
To process an input, the selected candies are first swapped.
The `GridManager` then checks for combos and simplifies them (see [Processing Combos](#processing-combos))
If no combo was found (i.e. the grid hasn't changed), the candies are swapped back in place, thus cancelling the move.

### Processing Combos
 1. To process combos, a copy of the grid is created. We thus have the main `grid` and a copy `tmpGrid`.
    The manager checks the candies in `grid` but modifies `tmpGrid`.
    At the end of the process, `tmpGrid` is copied back into `grid`.

 2. When a combo is found (3 or more consecutive candies of the same type), each cell of the combo is emptied.
 3. After checking the whole grid, the manager will fill the holes by repeatedly moving down holey columns, until the grid is full again.
 4. If a combo was found, this process is repeated, until no more combo can be found.
