package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
/**
 * Special ability
 * @author brandon
 */
public class Feature {
	private String name;
	private String description;
	
	public Feature(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public Feature(String fileName) {
		if (!fileName.endsWith(".pfeature")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .pfeature\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				name = Tools.readALine(read);
				description = Tools.readALine(read);
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret feature file!");
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
	@Override
	public String toString() {
		return "Name:" + name + " Description:" + description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
