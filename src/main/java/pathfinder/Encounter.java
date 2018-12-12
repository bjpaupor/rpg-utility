package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * An event that grants experience 
 * @author brandon
 */
public class Encounter {
	private String name;
	private String description;
	private int xp;
	
	public Encounter(String name, String description, int xp) {
		this.name = name;
		this.description = description;
		this.xp = xp;
	}
	public Encounter(String fileName) {
		if (!fileName.endsWith(".encounter")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .encounter\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				name = Tools.readALine(read);
				description = Tools.readALine(read);
				xp = Integer.parseInt(Tools.readALine(read));
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret encounter file!");
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
	public int getXP() {
		return xp;
	}
}
