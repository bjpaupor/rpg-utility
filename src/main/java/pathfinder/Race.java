package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Race {
	private String name;
	private String description; // role physicalDescription society relations alignmentAndReligion adventurers
	private String[] maleNames;
	private String[] femaleNames;
	private Feature[] traits;
	
	public Race (String name, String description, String[] maleNames, String[] femaleNames, Feature[] traits) {
		this.name = name;
		this.description = description;
		this.maleNames = maleNames;
		this.femaleNames = femaleNames;
		this.traits = traits;
	}
	public Race(String fileName) {
		if (!fileName.endsWith(".race")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .race\n");
			return;
		}
		try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
			name = Tools.readALine(read);
			description = Tools.readALine(read);
			maleNames = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < maleNames.length; i++)
				maleNames[i] = Tools.readALine(read);
			femaleNames = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < femaleNames.length; i++)
				femaleNames[i] = Tools.readALine(read);
			traits = new Feature[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < traits.length; i++)
				traits[i] = new Feature(Tools.readALine(read), Tools.readALine(read));
		}
		catch (Exception ex) {
			System.out.println("->" + name + ": Failed to interpret race file!");
			ex.printStackTrace();
			return;
		}
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String[] getMaleNames() {
		return maleNames;
	}
	public String[] getFemaleNames() {
		return femaleNames;
	}
	public Feature[] getTraits() {
		return traits;
	}
	@Override
	public String toString() {
		String result = "Name:" + name + " Description:" + description + " Male Names:";
		for (int i = 0; i < maleNames.length; i++) {
			result += maleNames[i];
			if (i != maleNames.length - 1)
				result += ", ";
		}
		result += " Female Names:";
		for (int i = 0; i < femaleNames.length; i++) {
			result += femaleNames[i];
			if (i != femaleNames.length - 1)
				result += ", ";
		}
		result += " Features:";
		for (int i = 0; i < traits.length; i++) {
			result += traits[i];
			if (i != traits.length - 1)
				result += ", ";
		}
		return result;
	}
	/**
	 * Prints every file within the given folder
	 * @param folderPath - folder containing files
	 * @throws IOException
	 */
	public static void printSet(String folderPath) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
			paths
			.filter(Files::isRegularFile)
			.forEach(s -> System.out.println(new Race(s.toString()).toString())); 
		} 
	}
	public static void main(String[] args) throws IOException {
		printSet("src/Assets/RaceFiles/");
		for (String s : args)
			printSet(s);
	}
}
