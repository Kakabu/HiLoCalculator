import java.util.ArrayList;

/**
 * Will print the number of losses after each number of rounds.  You'll understand how far you've gotten in general before the House wins.    
 * 
 * @author (Kakabu) 
 * @version (2015.08.13)
 */
public class Histogram
{

    private ArrayList<Integer> histogramArrayList;
    // Holds the number of times the game was lost at various numbers of rounds

    /**
     * Constructor for objects of class Histogram
     */
    public Histogram()
    {
        histogramArrayList = new ArrayList<Integer>();

    }

    /**
     * Adds a loss to the histogram at the specified number of rounds
     * 
     * @param roundNum: int. The number of the round where we lost a game
     */

    public void addLoss(int roundNum) {
        int currentSize = histogramArrayList.size(); //makes sure there's space in the array list for the value we want to add
        int zerosToAdd = roundNum - currentSize; //adds a bunch of zeros to fill in empty regions in the histogram (that's what a website said you had to do!)
        for (int i=0;i<zerosToAdd;i++) {
            histogramArrayList.add(0);
        }
        histogramArrayList.ensureCapacity(roundNum);  //works around an error, apparently this is necessary
        int lossCount = histogramArrayList.get(roundNum-1); //gets the previous value that was at the relevant index 
        histogramArrayList.set(roundNum-1,lossCount+1); //sets the value to one more than it previously was
    }

    
    /**
     * Returns a string form of the histogram
     */
    
    public String toString() {
        String s = "";
        int histogramArrayListSize = histogramArrayList.size();
        for (int i = 0; i < histogramArrayListSize; i++) {
            s = s + Integer.toString(i+1) + ":";  
            int value = histogramArrayList.get(i);
            for (int j = 0; j < value; j++) {
                s=s+"*"; //prints ** that represents the histogram in text
            }
            s=s+"\n";
        }

        // print out the numeric values too
        s=s+"\n";
        for (int i = 0; i < histogramArrayListSize; i++) {
            s = s + Integer.toString(i+1) + ":" + Integer.toString(histogramArrayList.get(i));  
            s=s+"\n";
        }
        
        return s;
    }
    

}
