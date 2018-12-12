package pathfinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Tools {

	/**
	 * Reads a single line from a file after a ':' char
	 * 	// starts a comment, will return left of if present
	 * @param read - what to read from
	 * @return null if unable to read
	 */
	public static String readALine(BufferedReader read) {
		try {
			String line = read.readLine();
			while (line.contains("//")) {
				if (line.substring(0, line.indexOf("//")).trim().equals(""))
					line = read.readLine();
				else
					line = line.substring(0, line.indexOf("//"));
			}
			return line.substring(line.indexOf(58) + 1).trim();
		} catch (IOException e) {
			System.out.println("-> Unable to read Line, returning null");
			return null;
		} catch (NullPointerException e) {
			System.out.println("-> Reached EOF, returning null");
			return null;
		}
	}

	//writes a single string to a file and a newLine 
	public static void writeALine(BufferedWriter file, String s) throws IOException{
		file.write(s);
		file.newLine();
	}
	/**
	 * Returns a place from a number as String
	 *  1st from 1
	 *  2nd from 2
	 *  @param i
	 *  @return i as a place
	 */
	public static String getPlace(int i) {
		String result = Integer.toString(i);
		if (result.endsWith("1") && Math.abs(i) != 11)
			result += "st";
		else if (result.endsWith("2") && Math.abs(i) != 12)
			result += "nd";
		else if (result.endsWith("3") && Math.abs(i) != 13)
			result += "rd";
		else
			result += "th";
		return result;
	}
	/**
	 * adds #H before each word in s
	 * @param s line to embolden
	 * @return embolened line
	 */
	public static String embolden(String s) {
		String result = "";
		for (String ss : s.split("(?<=\\s)")) 
			result += "#H" + ss;
		return result.trim();
	}
	//Returns the xp for the Challenge Rating
	public static int getXP(String cr) {
		int result = 0;
		if (cr.equals("1/8"))
			result = 50;
		else if (cr.equals("1/6"))
			result = 65;
		else if (cr.equals("1/4"))
			result = 100;
		else if (cr.equals("1/3"))
			result = 135;
		else if (cr.equals("1/2"))
			result = 200;
		else {
			result = 400;
			int cri = Integer.parseInt(cr);
			for (int i = 2; i <= cri; i++)
				if (i % 2 == 0)
					result = result * 3 / 2;
				else 
					result = result * 4 / 3;
		}
		return result;
	}
}
