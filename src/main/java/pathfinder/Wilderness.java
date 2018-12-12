package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Wilderness {
	private String name;
	private String description;
	private Terrain terrain;
	
	public Wilderness(String name, String description, Terrain terrain) {
		this.name = name;
		this.description = description;
		this.terrain = terrain;
	}
	public Wilderness(String fileName) {
			if (!fileName.endsWith(".wilderness")) {
				System.out.println("-> " + fileName + ": Incorrect file type, file must be .wilderness\n");
				return;
			}
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				name = Tools.readALine(read);
				terrain = Terrain.valueOf(Tools.readALine(read).toUpperCase());
				description = Tools.readALine(read);
			}
			catch (Exception ex) {
				System.out.println("->" + name + ": Failed to interpret wilderness file!");
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

	public Terrain getTerrain() {
		return terrain;
	}
	@Override
	public String toString() {
		return "Name:" + name + " Description:" + description + " Terrain:" + terrain.toString();
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
			.forEach(s -> System.out.println(new Wilderness(s.toString()).toString())); 
		} 
	}
	public static void main(String[] args) throws IOException {
		printSet("src/Assets/WildernessFiles/");
		for (String s : args)
			printSet(s);
	}
}
