package assignment2_Mastermind;
import java.util.Scanner;

/*
 public class GameConfiguration {
	public static final int guessNumber = 12;
	public static final String[] colors = {"B","G","O","P","R","Y"};
	public static final int pegNumber = 4;
}
 */
public class Driver {
	
	private static String secret_code = SecretCodeGenerator.getInstance().getNewSecretCode();
	private static boolean program_flag = true; //if false, terminate program
	private static boolean run_flag = true; //true means game running, false means game over
	private static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		boolean temp_reveal_flag = false; //hide the code
		
		if(args.length != 0) {
			if(args[0].equals("1")) {
				temp_reveal_flag = true; //reveal the code
			}
		}
		
		print_welcome();
		if(!program_flag) { //terminates program if not ready to play
			scanner.close();
			return;
		}
	
		while(true) {
			Game b1 = new Game(GameConfiguration.guessNumber, GameConfiguration.pegNumber, temp_reveal_flag);
			
			if(b1.reveal_flag)
				System.out.println(secret_code);
			
			while(run_flag) { //
				System.out.println("\nYou have " + (GameConfiguration.guessNumber-b1.index_of_guess+1) + " guesses left."
					+ "\nWhat is your next guess?" + "\nType in the characters for your guess and press enter." + "\nEnter guess:" );
				
				String guess = scanner.next();
				guess = check_guess(guess, b1.hist_guess, b1.hist_peg, b1.index_of_guess); //check if guess valid, if not updated until valid
				
				int peg[] = b1.get_peg(guess, secret_code); //get the pegs
				run_flag = b1.check_win(peg[0]); //run_flag false, if wins
				print_guess_result(guess, peg);
				b1.incr_index_of_guess(); //increase the index_of_guess
				if (b1.index_of_guess >= 13) { //run out of moves, game over
					run_flag = false;
					System.out.println("\nSorry, you are out of guesses. You lose, boo-hoo.");
				}
			}
			
			System.out.println("\nAre you ready for another game (Y/N):");
			print_gen_code();
			if(!program_flag) { //if program_flag is false, terminates
				scanner.close();
				return;
			}
			
			run_flag = true; //if reaches here, start a new game
			secret_code = SecretCodeGenerator.getInstance().getNewSecretCode(); //get new code for the new game
		}
	}
	
	/* print program starts welcome menu */
	private static void print_welcome() {
		System.out.println("Welcome to Mastermind. Here are the rules.\n"
				+ "\nThis is a text version of the classic board game Mastermind.\n"
				+ "\nThe computer will think of a secret code. The code consists of "
				+ GameConfiguration.pegNumber 
				+ "\ncolored pegs. The pegs MUST be one of " + GameConfiguration.colors.length + " colors:");
		for(int i=0; i<GameConfiguration.colors.length; i++) { System.out.print(" " + GameConfiguration.colors[i]);}
		System.out.println(". A color may appear more than once in\nthe code. "
				+ "You try to guess what colored pegs are in the code and\nwhat order they are in. "
				+ "After you make a valid guess the result\n(feedback) will be displayed.\n"
				+ "\nThe result consists of a black peg for each peg you have guessed\nexactly correct (color and position) in your guess. "
				+ "For each peg in\nthe guess that is the correct color, but is out of position, you get\na white peg. "
				+ "For each peg, which is fully incorrect, you get no feedback.\n"
				+ "\nOnly the first letter of the color is displayed. B for Blue, R for\nRed, and so forth. "
				+ "When entering guesses you only need to enter the\nfirst character of each color as a capital letter.\n"
				+ "\nYou have " 
				+ GameConfiguration.guessNumber 
				+ " guesses to figure out the secret code or you lose the\ngame. "
				+ "Are you ready to play? (Y/N):");
		print_gen_code();
	}
	
	/* check if input is valid, if yes print "Generating secret code ..." or "Goodbye", if no ask for new input */
	private static void print_gen_code() {
		String ready_to_play = scanner.next();
		
		if(ready_to_play.equalsIgnoreCase("Y"))
			System.out.println("Generating secret code ...");
		else if (ready_to_play.equalsIgnoreCase("N")) {
			program_flag = false;
			System.out.println("Goodbye!");
		}
		else {
			System.out.println("	-> INVALID INPUT\n"
			+ "\nYou have " 
			+ GameConfiguration.guessNumber 
			+ " guesses to figure out the secret code or you lose the\ngame. "
			+ "Are you ready to play? (Y/N):");
			print_gen_code();
		}
		
	}
	
	/* check if guess is valid; if not, ask for new guess, return updated guess */
	private static String check_guess(String guess, String [] hist_guess, int [][] hist_peg, int index) {
		boolean valid = false;
		boolean first = true;
		
		while(!valid) {
			if(!first) {
				System.out.println("\nWhat is your next guess?" + "\nType in the characters for your guess and press enter." 
						+ "\nEnter guess:" );
				guess = scanner.next();
			}
			
			first = false;
			
			if(guess.equalsIgnoreCase("HISTORY")) { //if HISTORY, print history
				System.out.println();
				for(int i=0; i<index-1; i++) {
					System.out.println(hist_guess[i] + " " + hist_peg[i][0] + "B_" + hist_peg[i][1] + "W");
				}
			}
			else if(guess.length() != GameConfiguration.pegNumber) { //if wrong length, then invalid
				System.out.println("	-> INVALID GUESS\n");
			}
			else {
				valid = true;
				
				for(int i=0; i<GameConfiguration.pegNumber; i++) { //if there's a non-color char, then invalid
					int len = GameConfiguration.colors.length; //check number of colors
					
					boolean match = false;
					
					while(len>0) {
						len--;
						if(guess.substring(i, i+1).equals(GameConfiguration.colors[len])) //if char matches one color, then valid
							match = true;
					}
					if(!match)
						valid = false;
				}
				
				if(!valid)
					System.out.println("	-> INVALID GUESS\n");
			}
		}
		
		return guess;
	}
	
	/* print guess result, peg[0] is black, peg[1] is white*/
	private static void print_guess_result(String guess, int[] peg) { 
		if(!run_flag) //wins
			System.out.println(guess + " -> Result: " + peg[0] + " black pegs – You win !!");
		else
			System.out.println(guess + " -> Result: " + peg[0] + " black pegs " + peg[1] + " white pegs");
	}
}
