
/**
 * Represents a standard 52 card card deck- Aces are considered high cards in this type of game.  
 * 
 * @author (Kakabu) 
 * @version (2015.08.13)
 */

public class Deck
{
    
    private int[] cards;
    private int numberOfRemainingCards;
    private static String[] cardStrings = new String[]{"2","3","4","5","6","7","8","9","10","Jack","Queen","King","Ace"};
    private double pHigher; //we need to include decimals so this can't be an int type; probabilities are real numbers
    private double pLower;
    private double pSame;
    private double pNotLose;

    /**
     * Constructor for objects of class Deck
     */
    public Deck()
    {
        cards = new int[]{4,4,4,4,4,4,4,4,4,4,4,4,4};
        // there are 13 types of cards, 2-10, the three face cards, and the Ace
        // we initialize each element to 4 because there are initially 4 of each type due to the 4 suits (Hearts, Spades, Clubs, Diamonds)
        // the 'Cards' array, at all times, represents the state of the deck
        pHigher = 0;
        pLower = 0;
        pSame = 0;
        pNotLose = 0;
        numberOfRemainingCards = 52;
        // in this version of Hi-Lo if the dealer draws the same card you don't win the pot but you don't lose no matter how you bet
        // you just keep playing, which is what pNotLose means

    }

    /**
     * Printing out the probabilities: higher, lower, the same, and the probability of not losing if you follow the program's advice 
     */

    public void printProbabilities() {
        String msg = "To stay in the game I advise you to bet ";
        System.out.println("There are " + Integer.toString(numberOfRemainingCards) + " cards remaining in the deck.");
        System.out.println("pHigher = " + Double.toString(pHigher));
        System.out.println("pLower = " + Double.toString(pLower));
        System.out.println("pSame = " + Double.toString(pSame));
        System.out.println("pNotLose = " + Double.toString(pNotLose));
        System.out.println("");
        if (pHigher > pLower) {
            System.out.println(msg + "higher.");
        }
        else if (pHigher == pLower) {
            System.out.println(msg + "either way.");
        }
        else {
            System.out.println(msg + "lower.");
        }
    }    

    /**
     * Calculates the probabilities of the dealer getting a card higher, lower, or the same as yours based on your card and the state of the deck.
     * 
     * @param cardNum:int - the player's card number
     */

    public void calculateProbabilities(int cardNum) { //I admit, I had to have a tutor talk me through the math on this one!!  
        int nHigher = 0;
        int nLower = 0;
        int nSame = cards[cardNum-2];

        for (int i = cardNum + 1; i <= 14; i++) {
            nHigher = nHigher + cards[i-2];

        }

        for (int i = 2; i <= cardNum - 1; i++) {
            nLower = nLower + cards[i-2];

        }

        numberOfRemainingCards = nHigher + nLower + nSame;  //when you try to assign less than 1 to an integer it transforms into zeros without this!
        
        pHigher = (double)nHigher/(double)numberOfRemainingCards; // (double) converts integers to doubles 
        pLower = (double)nLower/(double)numberOfRemainingCards;  //forces the integers to become doubles...called type casting??
        pSame = (double)nSame/(double)numberOfRemainingCards;  //totally frustrating!

        if (pHigher > pLower) {
            pNotLose = pHigher + pSame;  //ties don't count as losses in this version of high low so we include them with the greater of the two 
        }
        else {
            pNotLose = pLower + pSame;  //probabilities, higher or lower
        }

    }

    /**
     * Draw a card from the deck
     * 
     * @param   cardNum: which card (by number) is drawn
     * @return  boolean: whether the operation succeeds or not
     */

    public boolean drawCard(int cardNum) {
        int cardIndex = cardNum - 2;
        if (cardNum >= 2 && cardNum <= 14 && cards[cardIndex] > 0) {
            cards[cardIndex]--;
            return true;
        }

        return false;
    }

    /**
     * Put a card back in the deck
     * 
     * @param   cardNum: which card (by number) is drawn
     * @return  boolean: whether the operation succeeds or not
     */

    public boolean returnCard(int cardNum) {
        int cardIndex = cardNum - 2;
        if (cardNum >= 2 && cardNum <= 14 && cards[cardIndex] < 4) {
            cards[cardIndex]++;
            return true;
        }

        return false;
    }

    /**
     * Returns a string representation of a given card number.
     * 
     * @return String
     * @param cardNum: which card (by number) is drawn
     * 
     * Ex: If this method is called with an 11 (Jack), this will print Jack vs 11...totally more human friendly!
     */
    public String getCardString(int cardNum) {
        int cardIndex = cardNum - 2;
        return cardStrings[cardIndex];
    }

    /**
     * Returns a string representation of the deck
     * 
     * @return String
     */
    public String toString() {
        String deckString = "";
        int cardNum;
        for (int cardIndex = 0; cardIndex < 9; cardIndex++) {
            cardNum = cardIndex + 2;
            deckString += Integer.toString(cardNum) + ":" + Integer.toString(cards[cardIndex]) + ",";
        }
        for (int cardIndex = 9; cardIndex < 13; cardIndex++) {
            cardNum = cardIndex + 2;
            deckString += getCardString(cardNum) + ":" + Integer.toString(cards[cardIndex]) + ",";
        }
        return deckString;
    }

}
