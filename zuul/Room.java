import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
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

public class Room 
{
    private String description;
    
    private ArrayList<Item> items;
    
    private HashMap<String, Room> exits;        // stores exits of this room.
    
    private static final ArrayList<Room> Rooms = new ArrayList<Room>();

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new ArrayList<Item>();
        Rooms.add(this);
    }

    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit
     * @param neighbour The room to which the exit leads
     */
    public void setExit(String direction, Room neighbour) 
    {
        exits.put(direction, neighbour);
    }

    /**
     * Returns a short description of the room, i.e. the one that
     * was defined in the constructor
     * 
     * @return The short description of the room
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a long description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     *     Items:
     *     
     * @return A long description of this room including the items present
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\nItems:" + getItems()
        + "\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction
     * @return The room in the given direction
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Adds an item to the room.
     * 
     * @param item The item that will be added
     */
    public void addItem(Item item)
    {
        items.add(item);
    }
    
    /**
     * Retrieves the items in the current room.
     * 
     * @return a String of one or more items
     */
    public String getItems()
    {
        String itemDesc = ("\n  ");
        for (Item i : items)
        {
            itemDesc = itemDesc + i.getItemDescription();
        }
        return itemDesc;
    }
    
    public Item grabItem(String item)
    {
        for (Item i: items)
        {
            if (item.equals(i.getName()))
            {
                items.remove(i);
                return i;
            }
        }
        return null;
    }
}

