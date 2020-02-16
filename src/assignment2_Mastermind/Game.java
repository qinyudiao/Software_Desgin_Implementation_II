package assignment2_Mastermind;

/*
public class GameConfiguration {
	public static final int guessNumber = 12;
	public static final String[] colors = {"B","G","O","P","R","Y"};
	public static final int pegNumber = 4;
}
*/
public class Game {
	
	public int index_of_guess = 1; //1st guess, 12 in total
	public int guess_number;
	public int peg_number;
	public int [][] hist_peg = new int[GameConfiguration.guessNumber][2];
	public String [] hist_guess = new String[GameConfiguration.guessNumber];
	public boolean reveal_flag;
	
	public Game(int guess_number, int peg_number, boolean reveal_flag) {
		this.guess_number = guess_number;
		this.peg_number = peg_number;
		this.reveal_flag = reveal_flag;
	}	
	public Game() { //default constructor with 12 guesses and 4 pegs
		this.guess_number = 12;
		this.peg_number = 4;
		this.reveal_flag = true;
	}
	
	public int[] get_peg(String guess, String code) {
		hist_guess[index_of_guess-1] = guess;
		StringBuilder temp_guess = new StringBuilder(guess);
		StringBuilder temp_code = new StringBuilder(code);
		for(int i=0; i<GameConfiguration.pegNumber; i++) { //check chars at the same positions
			if(temp_guess.substring(i, i+1).equals(temp_code.substring(i, i+1))) {
				hist_peg[index_of_guess-1][0]++; //black peg ++
				temp_guess.setCharAt(i,'g');
				temp_code.setCharAt(i,'c');
			}
		}
		for(int i=0; i<GameConfiguration.pegNumber; i++) { //check chars at the different positions
			for(int j=0; j<GameConfiguration.pegNumber; j++) {
				if(temp_guess.substring(i, i+1).equals(temp_code.substring(j, j+1))) {
					hist_peg[index_of_guess-1][1]++; //white peg ++
					temp_guess.setCharAt(i,'g');
					temp_code.setCharAt(j,'c');
				}
			}
		}
		return hist_peg[index_of_guess-1]; //stores pegs in history
	}
	
	public void incr_index_of_guess(){
		index_of_guess ++;
	}
	
	public boolean check_win(int black_peg) {
		if (black_peg == GameConfiguration.pegNumber) { // wins
			return false;
		}
		else
			return true;
	}

}
