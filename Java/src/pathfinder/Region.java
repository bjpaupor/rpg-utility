package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Region {

	private String name;
	private String description;
	private String[] wilds;
	private String[] locations; //not communities
	private String[] communities;
	
	public Region(String name, String description, String[] wilds, String[] locations, String[] communities) {
		this.name = name;
		this.description = description;
		this.wilds = wilds;
		this.locations = locations;
		this.communities = communities;
	}
	
	public Region(String fileName) {
			if (!fileName.endsWith(".land")) {
				System.out.println("-> " + fileName + ": Incorrect file type, file must be .land\n");
				return;
			}
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				name = Tools.readALine(read);
				description = Tools.readALine(read);
				wilds = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < wilds.length; i++)
					wilds[i] = Tools.readALine(read);
				locations = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < locations.length; i++)
					locations[i] = Tools.readALine(read);
				communities = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < communities.length; i++)
					communities[i] = Tools.readALine(read);
			}
			catch (Exception ex) {
				System.out.println("->" + name + ": Failed to interpret land file!");
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

	public String[] getWilds() {
		return wilds;
	}
	public String[] getLocations() {
		return locations;
	}
	public String[] getCommunities() {
		return communities;
	}
	@Override
	public String toString() {
		String result = "Name:" + name + " Description:" + description + " Wilds:";
		for (int i = 0; i < wilds.length; i++) {
			result += wilds[i];
			if (i != wilds.length - 1)
				result += ", ";
		}
		result += " Locations:";
		for (int i = 0; i < locations.length; i++) {
			result += locations[i];
			if (i != locations.length - 1)
				result += ", ";
		}
		result += " Communities:";
		for (int i = 0; i < communities.length; i++) {
			result += communities[i];
			if (i != communities.length - 1)
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
			.forEach(s -> System.out.println(new Region(s.toString()).toString())); 
		} 
	}
	public static void main(String[] args) throws IOException {
		printSet("src/Assets/RegionFiles/");
		for (String s : args)
			printSet(s);
	}
}
