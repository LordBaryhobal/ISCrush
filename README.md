# ISCrush

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


| Name               | Effect                                                   |
|:-------------------|:---------------------------------------------------------|
| **ISCrush**        | allow us to launch the game (contains the main object)   |
| **Candy**          | Class which creates all the teachers                     |
| **GridManager**    | generates the grid and all the logic                     |
| **GridRenderer**   | render the grid                                          |
| **ConsoleManager** | allow us to play in the console                          |
| **MouseManager**   | allow us to use the mouse to play the game               |
| **InputHandler**   | allow us to use the mouse or the keyboard                |
| **Input**          | allow us to use the keyboard                             |
| **Pos**            | creates tuples to configure the positions of the candies |
| **Score**          | has the score of the game                                |
| **Audio**          | allow us to play audio                                   |