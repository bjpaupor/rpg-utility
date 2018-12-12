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
}