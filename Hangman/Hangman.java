import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Hangman {

    private static List<String> words = new ArrayList<>();
   // Instance Variables   
	private String secretWord;       // the chosen secret word
	private List<Character> correctLetters;   // correct guesses
	private List<Character> incorrectLetters; // incorrect guesses

	private Scanner stdin = new Scanner(System.in); // for user input

	public Hangman() {
        loadWordsFromFile("dictionary.txt");
        Random rand = new Random();
        int randomIndex = rand.nextInt(words.size());
        this.secretWord = words.get(randomIndex);

        this.correctLetters = new ArrayList<>();
            for(int i = 0; i < secretWord.length(); i++) {
                this.correctLetters.add('_');
            }
                this.incorrectLetters = new ArrayList<>();
    }

    private void loadWordsFromFile(String filename) {
        try {
			String absolutePath = "C:\\Users\\laure\\Downloads\\GitHub\\Hangman\\dictionary.txt";
            Scanner fileScanner = new Scanner(new File(absolutePath)); // Use Scanner to read the file
            while (fileScanner.hasNextLine()) {
                words.add(fileScanner.nextLine().trim()); // Add each word to the list
            }
            // No need to close the scanner as you requested
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.exit(1); // Exit the program if the file cannot be loaded
        }
    }
   /** 
   * PlayGame
   *
   * Is a constructor that prints out the Hangman picture and letters that are entered into the console. For
   *     when the letter is correct, it will print out the guesses in the secret word. For when it is an incorrect
   *     guess, the letter(s) will print into the wrong letter section.
   *
   * @while the game is not over, it will continue to print out the letters that are being guessed.
   * If the game has been one, a message congradulating the user will be printed. Else, if the user lost, it will
   *     print a message notifying that the user lost.
   */
	public void playGame() {
		
		while (!gameOver()) {

			printHangman();

			for (int i = 0; i < this.correctLetters.size(); i++)
				System.out.print(this.correctLetters.get(i) + " ");
	
			System.out.print("\nWrong letters: ");
			for (int i = 0; i < this.incorrectLetters.size(); i++)
				System.out.print(this.incorrectLetters.get(i) + " ");

			System.out.print("\nEnter a lower-case letter as your guess: ");
			String guess = stdin.nextLine();
			
			handleGuess(guess.charAt(0));
		}
		
		System.out.println("The secret word was: " + secretWord);
		if (gameWon()) {
			System.out.println("Congratulations, you won!");
		} else {
			System.out.println("Sorry, you lost.");
			printHangman();
		}
	}

	/**
	 * handleGuess
	 *
	 * If the guessed letter (parameter ch) is in the secret word
	 * then add it to the array list of correct guesses and tell the user
	 *      that the guess was correct;
	 * otherwise, add the guessed letter to the array list of incorrect
	 *      guesses and tell the user that the guess was wrong.
	 *
	 * @param ch the guessed letter
	 */
	public void handleGuess(char ch) {
        if(!Character.isLetter(ch)) {
            System.out.println("Please enter a letter.");
        }
        ch = Character.toLowerCase(ch);
        if(secretWord.contains(String.valueOf(ch))) {
            for(int i = 0; i < secretWord.length(); i++) {
                if(secretWord.charAt(i) == ch) {
                    correctLetters.set(i, ch);
                }
            }
            System.out.println("Correct guess! :)");
         }
         else {
            incorrectLetters.add(ch);
            System.out.println("Wrong guess! :(");
         }
	}

	/**
	 * gameWon
	 *
	 * Return true if the user has won the game 
	 * otherwise, return false.
	 *
	 *@return true if the user has won, false otherwise
	 */
	public boolean gameWon() {
		return !correctLetters.contains('_');
	}
	
	/**
	 * gameLost
	 *
	 * Return true if the user has lost the game(has made 7 wrong guesses)
	 * otherwise, return false.
	 *
	 * @return true if the user has lost, false otherwise
	 */
	public boolean gameLost() {
		return incorrectLetters.size() >= 7;
	}
	
    /**
     * gameOver
     *
     * Return true if the user has either won or lost the game;
     * otherwise, return false.
     *
     * @return true if the user has won or lost, false otherwise
     */
    public boolean gameOver() {
		if(gameWon() || gameLost()) {
            return true;
        }
        else {
            return false;
        }
      
    }
	
	/**
	 * printHangman
	 *
	 * Print the Hangman that corresponds to the given number of
	 * wrong guesses so far.
	 *
	 * @param numWrong number of wrong guesses so far
	 */
	public void printHangman() {
		int poleLines = 6; 
		System.out.println("  ____");
		System.out.println("  |  |");
		
		int badGuesses = this.incorrectLetters.size();
		if (badGuesses == 7) {
			System.out.println("  |  |");
			System.out.println("  |  |");
		}
		
		if (badGuesses > 0) {	    	   
			System.out.println("  |  O");
			poleLines = 5;
		}
		if (badGuesses > 1) {
			poleLines = 4;
			if (badGuesses == 2) {
				System.out.println("  |  |");
			} else if (badGuesses == 3) {
				System.out.println("  | \\|");
			} else if (badGuesses >= 4) {
				System.out.println("  | \\|/");
			}
		}
		if (badGuesses > 4) {
			poleLines = 3;
			if (badGuesses == 5) {
				System.out.println("  | /");
			} else if (badGuesses >= 6) {
				System.out.println("  | / \\");
			}
		}
		if (badGuesses == 7) {
			poleLines = 1;
		}

		for (int k = 0; k < poleLines; k++) {
			System.out.println("  |");
		}
		System.out.println("__|__");
		System.out.println();
	}
	
	//////////////////////////////////////////////////////////////////////
	// 6. FOR TESTING PURPOSE ONLY
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * toString
	 * 
	 * Returns a string representation of the Hangman object.
	 * Note that this method is for testing purposes only!
	 * @return a string representation of the Hangman object
	 */
	public String toString() {
		String s = "secret word: " + this.secretWord;
		
		s += "\ncorrect letters: ";
		for (int i = 0; i < this.correctLetters.size(); i++)
			s += this.correctLetters.get(i) + " ";
		
		s += "\nused letters: ";
		for (int i = 0; i < this.incorrectLetters.size(); i++)
			s += this.incorrectLetters.get(i) + " ";
		
		s += "\n# bad letters: " + this.incorrectLetters.size();
		
		return s;
	}
	
    public void setCurrentWord(String newWord) {
        this.secretWord = newWord;
    }
    
    public void setCorrectLetters(ArrayList<Character> newGuesses) {
        this.correctLetters = newGuesses;
    }
    
    public void setIncorrectLetters(ArrayList<Character> newUsedLetters) {
        this.incorrectLetters = newUsedLetters;
    }
	
    public void setBadGuesses(int newBadGuesses) {
    	this.incorrectLetters.clear();
    	    for (int i = 0; i < newBadGuesses; i++) {
    		    this.incorrectLetters.add('x');
    	    }
    }
    public static void main(String[] args) {
        
        System.out.println("Current directory: " + System.getProperty("user.dir"));
        Hangman game = new Hangman();
        
        game.playGame();
     }
	
	}