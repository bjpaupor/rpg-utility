package pathfinder;

import java.util.Random;
/**
 * For rolling dice
 * @author brandon
 */
public class Dice {
	private static Random rand;
	/**
	 * Must be called before any Dice methods
	 * Sets Random variable
	 */
	private static void prime() {
		rand = new Random();
	}
	/**
	 * Returns an int from 1-d
	 * @param d - max value random int
	 * @return 1-d random
	 */
	public static int d(int d) {
		if (rand == null)
			prime();
		return rand.nextInt(d) + 1;
	}
	/**
	 * Returns an int from 1-20
	 * @return 1-20 random
	 */
	public static int d20() {
		if (rand == null)
			prime();
		return rand.nextInt(20) + 1;
	}
	/**
	 * Returns an int from 1-10
	 * @return 1-10 random
	 */
	public static int d10() {
		if (rand == null)
			prime();
		return rand.nextInt(10) + 1;
	}
	/**
	 * Returns an int from 1-12
	 * @return 1-12 random
	 */
	public static int d12() {
		if (rand == null)
			prime();
		return rand.nextInt(12) + 1;
	}
	/**
	 * Returns an int from 1-6
	 * @return 1-6 random
	 */
	public static int d6() {
		if (rand == null)
			prime();
		return rand.nextInt(6) + 1;
	}
	/**
	 * Returns an int from 1-4
	 * @return 1-4 random
	 */
	public static int d4() {
		if (rand == null)
			prime();
		return rand.nextInt(4) + 1;
	}
	/**
	 * Returns an int from 1-2
	 * @return 1-2 random
	 */
	public static int d2() {
		if (rand == null)
			prime();
		return rand.nextInt(2) + 1;
	}
	/**
	 * Returns an int from 1-8
	 * @return 1-8 random
	 */
	public static int d8() {
		if (rand == null)
			prime();
		return rand.nextInt(8) + 1;
	}
	/**
	 * Returns an int from 1-100
	 * @return 1-100 random
	 */
	public static int d100() {
		if (rand == null)
			prime();
		return rand.nextInt(100) + 1;
	}
	public static void main(String[] args) {
		System.out.println(d(39));
		System.out.println(d20());
	}
//	/**
//	 * Runs some tests to discover how many rolls on average are needed before
//	 *  the averages of those rolls are as expected
//	 * Also Prints statistics of dice rolls
//	 */
//	public static void main(String[] args) {
//		System.out.println("This is testing d20 averages");
//		int num = Integer.parseInt(args[0]);
//		int numTrials = Integer.parseInt(args[1]);
//		int[] results = new int[num];
//		double[] avgs = new double[numTrials];
//		int[] oness = new int[numTrials];
//		int[] twentiess = new int[numTrials];
//		for (int j = 0; j < numTrials; j++) {
//			//generate rolls
//			for (int i = 0; i < num; i++)
//				results[i] = d20();
//			//find stats
//			double avg = 0;
//			int ones = 0;
//			int twenties = 0;
//			for (int i = 0; i < num; i++) {
//				if (results[i] == 1)
//					ones++;
//				if (results[i] == 20)
//					twenties++;
//				avg += results[i];
//			}
//			avg /= num;
////			System.out.println("AVG: " + avg);
////			System.out.println("EXPECTED 1s and 20s: " + num / 20);
////			System.out.println("1s: " + ones);
////			System.out.println("20s: " + twenties);
//			avgs[j] = avg;
//			oness[j] = ones;
//			twentiess[j] = twenties;
//		}
//		double avg = 0;
//		double avgOnes = 0;
//		double avgTwenties = 0;
//		for (int j = 0; j < numTrials; j++) {
//			avg += avgs[j];
//			avgOnes += oness[j];
//			avgTwenties += twentiess[j];
//		}
//		avg /= numTrials;
//		avgOnes /= numTrials;
//		avgTwenties /= numTrials;
//		System.out.println("Total AVG: " + avg);
//		System.out.println("EXPECTED 1s and 20s: " + num / 20);
//		System.out.println("5% bounds: " + (num / 20 - (num / 20) * .05) + " to " + (num / 20 + (num / 20) * .05));
//		System.out.println("Avg num 1s: " + avgOnes);
//		System.out.printf("Difference: %.2f\n", Math.abs(avgOnes - num / 20));
//		System.out.println("Avg num 20s: " + avgTwenties);
//		System.out.printf("Difference: %.2f\n", Math.abs(avgTwenties - num / 20));
//	}
}