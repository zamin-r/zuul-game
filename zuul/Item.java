
/**
 * This class handles the Item objects that can be found in the "World of Zuul"
 * application. These items have a description and a weight.
 *
 * @author Zamin Rizvi  ID: 101203136
 * @version Februrary 18, 2023
 */
public class Item
{
    private String description;
    
    private double weight;
    
    private String name;

    /**
     * Constructor for objects of class Item. 
     * 
     * @param descrption describes what the item is
     * @param weight is the weight of the item
     */
    public Item(String description, double weight, String name)
    {
        this.description = description;
        this.weight = weight;
        this.name = name;
    }
    
    /**
     * This returns the description of the item and its weight.
     * 
     * @return a String that describes the item
     */
    public String getItemDescription()
    {
        return name + ": " + description + " that weighs " + weight + "kg. \n";
    }
    
    public String getName()
    {
        return name;
    }
}
