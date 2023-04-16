# zuul-game

This is an assignment that I completed using Java for my SYSC 2004 course at Carleton University. We were provided a very bare bones "skeleton" of the game that our instructor had produced which we then took and improved upon by adding our own methods, classes, and functionalities.

The game is a basic text-based adventure game where the player can travel through different locations at Carleton. When travelling through a room, player's can pick up items unique to the room. In order to pick up an item, the player has to ensure that they are not hungry. The player must find a cookie and eat it before they can collect the different items scatted across all of the rooms.

This assignment helped me understand concepts such as:
- Proper Javadoc documentation conventions and how to write efficently so that someone looking at my project can understand the functionalities of every class and method.
- How to utilize ArrayList collections. I used these for my Item class objects so that I had an easy way to add new items into the game. Using this collection also helped with the interaction of the player with an item that they found, specifically if they chose to add or drop an item in a room.
- How to use a HashMap to help me code the functionality of a player moving through different rooms
- A Stack that I used to add the funtionality of travelling back to the previous room that the player was in triggered by a command, similar to an "undo".
- Understanding of when to use public vs private methods. I also employed my understanding of Java concepts such as encapsulation and inheritance.
