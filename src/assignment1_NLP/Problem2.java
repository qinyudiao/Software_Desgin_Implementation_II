package assignment1_NLP;

//Name: Jack Diao
//Eid: qd572
//By signing your name and eid, you affirm that this is your own work.

//import java.util.Scanner;

/* Assume a paragraph start with a letter and ends with "." */
/* Assume the program runs only once */
/* Assume a paragraph has at most 100 words */

public class Problem2 {
	
	public static String paragraph;
	public static String[] one_dollar_word = new String[100];	// store one_dollar_words
	
	Problem2(String s2){
		paragraph = s2;
	}

	public static void main(String[] args) {
		
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Enter a paragraph in English:");
//		String paragraph = scanner.nextLine(); //at most n adjacent digits in the 1000-digit number
//		//System.out.println(paragraph); // test if the same input output
//		scanner.close();
		
		int num_one_dollar_word = get_num_one_dollar_word(paragraph);
		System.out.println("There are " + num_one_dollar_word + " $1.00 words");
	}
	
	public static String[] problem2(String s) {
		int temp = get_num_one_dollar_word(s);
		System.out.println(temp);
		return one_dollar_word;
	}
	
	protected static int get_num_one_dollar_word(String par) {
		//int len_par = par.length();
		int num_one_dollar_word = 0;

		int num_word = get_num_word(par);
		//System.out.println("There are " + num_word + " words");
		//System.out.println("len_par is: " + len_par);
		

		String temp_word;
		int index_1 = 0;	// index of start of temp word
		int index_2 = 1;	// index of end of temp word
		int index = 0;		// index of one_dollar_word[]
		
		/* Store every word matches one dollar value in the one_dollar_word array */
		while(num_word > 0) {
			boolean is_one_dollar = false; // inside while loop to reset
			
			while((int)par.charAt(index_2) != 32 && (int)par.charAt(index_2) != 46) {
				//System.out.println("index_2 is: " + index_2);
				//System.out.println((int)par.charAt(index_2));
				index_2++;
			}
			temp_word = par.substring(index_1, index_2);
			//System.out.println("temp_word is: " + temp_word);
			index_1 = index_2+1;
			index_2 = index_2+2;

			int word_dollar_value = 0;
			int len_word = temp_word.length();
			for(int i = 0; i< len_word; i++) {
				word_dollar_value += get_value(temp_word.substring(i));
				//System.out.println("word_dollar_value is: " + word_dollar_value);
			}
			//System.out.println("word_dollar_value is: " + word_dollar_value);
			if(word_dollar_value == 100)
				is_one_dollar = true;		
			if(is_one_dollar) {	// if there's a one dollar word, store it in the array
				one_dollar_word[index] = temp_word;
				index ++;
				num_one_dollar_word++;
			}
			num_word--;
		}

		print_one_dollar_word(one_dollar_word, num_one_dollar_word);
		
		return num_one_dollar_word;
	}
	
	private static void print_one_dollar_word(String[] one_dollar_word, int num) {
		int i = 0;
		while(i < num) {
			//System.out.println(one_dollar_word[i]); // print words
			i ++;
		}
		
	}
	
	/* return the total number of words */
	private static int get_num_word(String par) {
		int num = 0; // num_word
		int len = par.length();
		//System.out.println(" len:" + len);
		for(int i = 0; i < len; i++) {
			if((int) par.charAt(i) == 32) { // if = " " / space
				//array_index_space[num] = i;
				num++;
			}
			if((int) par.charAt(i) == 46) // if = "." / period
				num++;
		}
		return num;
	}
	
	/* return the penny value of a single letter */
	private static int get_value(String letter) {
		int ascii = (int) letter.charAt(0);
		if(ascii <= 122 && ascii >= 97 )		// is lowercase
			return ascii - 96;
		else if(ascii <= 90 && ascii >= 65 )	// is uppercase
			return ascii - 64;
		else									// is special character
			return 0;
	}
	//Java is a fat boy.
}
