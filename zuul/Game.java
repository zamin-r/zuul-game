import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns. The items are also created
 *  within this class.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 * 
 * @author Lynn Marshall
 * @version October 21, 2012
 * 
 * @author Zamin Rizvi  ID: 101203136
 * @version February 18, 2023
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;
    private Item item;
    private Item heldItem;
    private Stack<Room> stackBack;
    private int pickupEnergy;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        previousRoom = null;
        stackBack = new Stack<Room>();
        heldItem = null;
        pickupEnergy = 0;
        
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;
        Item box, chair, donut, painting, table, tree, cookie;
        
        box = new Item("a box", 1, "box");
        chair = new Item("a wooden chair", 3, "chair");
        donut = new Item("a jelly donut", 0.1, "donut");
        painting = new Item("a painting sits on the table", 1, "painting");
        table = new Item("a wooden table", 10, "table");
        tree = new Item("an oak tree", 450, "tree");
        cookie = new Item("a consumable cookie", 0.1, "cookie");
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // items put in rooms
        
        theatre.addItem(box);
        theatre.addItem(cookie);
        office.addItem(table);
        office.addItem(painting);
        office.addItem(cookie);
        lab.addItem(donut);
        lab.addItem(cookie);
        pub.addItem(chair);
        pub.addItem(cookie);
        outside.addItem(tree);
        outside.addItem(cookie);
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed
     * @return true If the command ends the game, false otherwise
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look(command);
        }
        else if (commandWord.equals("eat")) {
            eat(command);
        }
        else if (commandWord.equals("back")) {
            back(command);
        }
        else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
        else if (commandWord.equals("stackBack")) {
            stackBack(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print a cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     * 
     * @param command The command to be processed
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = currentRoom;
            stackBack.push(currentRoom);
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @param command The command to be processed
     * @return true, if this command quits the game, false otherwise
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * "Look" was entered. Outputs the detailed description of the current room.
     * 
     * @param The command to be processed
     */
    
    private void look(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("Look what?");
        }
        else
        {
        System.out.println(currentRoom.getLongDescription());
        }
    }
    
    /**
     * "Eat" was entered. The person will eat which will result in them being
     * no longer hungry.
     * 
     * @param The command to be processed
     */
    
    private void eat(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("Eat what?");
        }
        if(pickupEnergy == 5)
        {
            System.out.println("You are energized and are not hungry.");
        }
        if(heldItem.getName().equals("cookie"))
        {
            pickupEnergy = 5;
            heldItem = null;
            System.out.println("The cookie has been consumed and you are energized.");
        }
        else
        {
            System.out.println("You are not holding any cookies.");
        }
    }
    
    /**
     * "Back" was entered. The person will travel back to the previous room.
     * 
     * @param The command to be processed
     */
    private void back(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("Back what?");
        }
        else if(previousRoom == null)
        {
            System.out.println("There is no previous room.");
        }
        else
        {
            Room previousBack = currentRoom; 
            currentRoom = previousRoom;
            previousRoom = previousBack;
            stackBack.push(previousBack); //Ensures that back() and standBack() commands are compatible if called one after the other.
            //The previousRoom is put onto the stack so if stackBack() is called, the person will go to the correct previous room.
            System.out.println(currentRoom.getLongDescription());
            
        }
    }
    
    /**
     * "stackBack" was entered. The person can travel back through the previous
     * rooms as far back as they want.
     * 
     * @param The command to be entered
     */
    private void stackBack(Command command)
    {
        if(command.hasSecondWord())
        {
            System.out.println("stackBack what?");
        }
        else if(stackBack.isEmpty()) 
        {
            System.out.println("There is no room to stack back to.");
        }
        else
        {
            previousRoom = currentRoom;
            currentRoom = stackBack.pop();
            System.out.println(currentRoom.getLongDescription());            
        }
    }
    
    private void take(Command command)
    {
        String chosenItem = command.getSecondWord();

        if (pickupEnergy == 0) 
        {
            if(!chosenItem.equals("cookie"))
            {
                System.out.println("You cannot pick up any items as you are too hungry. Please find a cookie.");
                return;
            }
        }
        if(heldItem == null)
        {
            Item selectedItem = currentRoom.grabItem(chosenItem);
            if (selectedItem == null)
            {
                System.out.println("This item is not present in the room.");
            }
            else
            {
                heldItem = selectedItem;
                System.out.println("The item " + chosenItem + " has been picked up.");
            }
        }
        else 
        {
            System.out.println("You already have an item.");
        }
    }
    
    private void drop(Command command)
    {
        if(heldItem == null)
        {
            System.out.println("There is nothing to drop.");
        }
        else
        {
            currentRoom.addItem(heldItem);
            System.out.println(heldItem.getName() + " has been dropped.");
            heldItem = null;
        }
    }
}
