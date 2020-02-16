package assignment1_NLP;

//Name: Jack Diao
//Eid: qd572
//By signing your name and eid, you affirm that this is your own work.

/*Write Java code to find the at most n adjacent digits in the 1000-digit number that have the greatest product,
where n is between 2 and 20. Your code should take as input the value of n (e.g., 4) and output the greatest
product for at most n*/
//import java.util.*;

public class Problem1 {
	public static final int Digit = 1000;
	
	public static int n;
	public static String s;
	
	public Problem1(int n, String s) {
		Problem1.n = n;
        Problem1.s = s;
    }

	public static void main(String[] args) {
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("Enter the number of adjacent digits:");
//		int n = scanner.nextInt(); //at most n adjacent digits in the 1000-digit number
//		System.out.println("Enter the 1000-digit number:");
//		s = scanner.next();
//		scanner.close();
		
		//Problem1 problem1 = new Problem1(n, s);
		
		int result = find_greatest_product(n, s);
		System.out.println("greatest_product:" + result);
	}

	public static int problem1(int n, String s) {
		return find_greatest_product(n,s);
	}
	
	public static int find_greatest_product(int n, String s) {
		//int count = 0;
		int max = 0;
		for (int index = 0; index < Digit; index ++) {
			int temp = Integer.parseInt(s.substring(index,index+1));
			if((Digit - index)<n) {
				n = Digit - index;
			}
			for (int j = 1; j < n; j ++) {
				temp *= Integer.parseInt(s.substring(index+j,index+1+j));
				if (temp > max) max = temp;
				//System.out.println(temp); //print out every product
				//count++;
			}
		}
		//System.out.println("product_count:" + count);
		return max;
	}
	
}

