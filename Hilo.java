
/**
 * Basically this program will help you cheat at the card game High-Low by card counting!
 * 
 * High-Low is a deceptively simple card game where a card is drawn by the "House" and you decide whether your card will be higher or lower than the House's card.  In the case of a tie, you advance to the next round, but you don't win anything.  
 * 
 * When finished, this will eventually show you when you lose, the percentage chance of the card being higher or lower than the House, and more instructions on how to play.  
 * 
 * @author (Kakabu)
 * @version (2015.08.22)
 */

import java.util.Scanner;

public class Hilo
{
    private static Deck cardDeck;
    private static Histogram gameResultHistogram;

    public static char askToContinue(Scanner sc) {
        System.out.println("Type 'L' to indicate that you lost this game, 'X' to print the histogram and end the program.");
        System.out.println("Type anything else to continue.");
        System.out.println("");
        String cardPattern = "[xlXLsS]"; //regex (regular expression) had to look this one up!
        if(sc.hasNext(cardPattern) ){
            String cardStr = sc.next(cardPattern).toLowerCase(); //had to look up how to make a scanner, and this gets the first character of what is put in like typing King yeilds K
            char continueChar = cardStr.charAt(0); //pulls the single character out
            return continueChar; //returns the character

        }
        else {
            sc.next();  //seems to be necessary to prevent this from getting stuck here after an error code
        }
        return '\n'; // code for new line
    }

    /**
     * Gets a single card input from the user for either the player or dealer.
     * Checks for errors in what people type.
     */
    public static int getCard(int roundNum ,String msg, Scanner sc) {
        do {
            System.out.print(msg + " round " + Integer.toString(roundNum) + "> "); //I fixed this so the carrot icon actually appears
            if (sc.hasNextInt()) {
                int cardNum = sc.nextInt(); //trying to distinguish between integers (the card numbers) vs. the suits
                if (cardNum >= 2 && cardNum <= 10) {
                    return cardNum;
                }
                else {
                    System.out.println("Alas good chap, the card number must be between 2 and 10!");
                }
            }
            else {
                String cardPattern = "[jqkaJQKA]"; //regex (regular expression) had to look this one up!
                if(sc.hasNext(cardPattern) ){
                    String cardStr = sc.next(cardPattern).toLowerCase();
                    char cardChar = cardStr.charAt(0);
                    switch(cardChar) {  //scanning :-) and translating the character into a number value
                        case 'j':
                        return 11;                        
                        case 'q':
                        return 12;
                        case 'k':
                        return 13;
                        case 'a':
                        return 14;                        
                    }

                }
                else {
                    System.out.println("Alas good chap, high cards must be entered as J,Q,K, or A...and nothing else will do!");
                    sc.next();  //seems to be necessary to prevent us from getting stuck here after an error code
                }
            }

        } while (true);
        // return 0;
    }

    /** 
     * Main loop of the game! Takes the player's input in the form of cards drawn by player
     * and dealer (the House). Uses Deck and Histogram objects to track the state of the deck
     * and maintain a histogram of the number of rounds at the point at which each
     * game of Hi-Lo is lost. This calculates and prints out probabilites
     * of the next round's dealer card being higher or lower than the player's
     * based on counting the cards remaining in the deck...which will be great for the cheating process!  ;)
     */

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in); 
        boolean donePlaying = false;
        boolean lostRound = false;

        int playerCardNum = 0;
        int dealerCardNum = 0;
        int roundNum;
        gameResultHistogram = new Histogram();  //here's where that Histogram comes in!
        do {
            System.out.println("New game and fresh, sparkling new deck too!");
            cardDeck = new Deck();
            roundNum = 1;
            do {
                System.out.println("State of the deck (aka how many cards are left):");
                System.out.println(cardDeck.toString());
                System.out.println("");

                playerCardNum = getCard(roundNum, "Player's Card for", sc);
                System.out.println("");

                if (cardDeck.drawCard(playerCardNum)) {  //firstly, the program tries to draw a player's card, it returns a boolean cuz' you can run out of cards
                    System.out.println("This is what the current probabilities look like in this devious game:");
                    cardDeck.calculateProbabilities(playerCardNum);
                    cardDeck.printProbabilities();

                    dealerCardNum = getCard(roundNum, "Dealer's Card for", sc); //if we succeed in drawing a player card, we draw a dealer card
                    if (cardDeck.drawCard(dealerCardNum)) {
                        roundNum++;
                        // both drawing the dealer AND player cards must succeed for the round number to increase
                        char continueChar = askToContinue(sc);
                        switch (continueChar) {  //keeps the loop rolling along!  Also gives you a way out of the infinite loop
                            case 'x':
                            lostRound = true;
                            donePlaying = true;
                            break;
                            case 'l':
                            lostRound = true;
                            break;
                            case 's':
                            cardDeck = new Deck();
                            System.out.println("The bastards have shuffled the deck on you!");
                            break;
                        }

                    }
                    else {
                        System.out.println("Error: you're out of " + cardDeck.getCardString(dealerCardNum) + "s");
                        System.out.println("");
                        cardDeck.returnCard(playerCardNum);
                        // We succeeded in drawing the player card but failed to draw the dealer card (sadness!)
                        // We now put the player card back in and redo the round. We don't need to return the dealer card bacause the draw card method failed for that one
                    }
                }
                else {
                    System.out.println("Error: you're out of " + cardDeck.getCardString(playerCardNum) + "s");  //starts failure branch of the tree here, the attempt to draw the player card failed
                    System.out.println("");
                }

            } while (!lostRound);
            System.out.println("The House Won Again! You lost on round " + Integer.toString(roundNum-1) + "!"); //happens when a game ends
            gameResultHistogram.addLoss(roundNum-1); //tallys the result in the histogram!
            lostRound = false; //resets the lost round boolean so that the next game will continue until the player ends it
        } while (!donePlaying);
        System.out.println("Here are your stats as a histogram of your losses by round number."); //the end of the game! 
        System.out.println(gameResultHistogram.toString());  //prints the histogram
    }
}
