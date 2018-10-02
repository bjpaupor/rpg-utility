package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
/**
 * A gathering of civilization
 * @author brandon
 *
 */
public class Community {
	public enum Type {
		THORP, HAMLET, VILLAGE, SMALL_TOWN, LARGE_TOWN, SMALL_CITY, LARGE_CITY, METROPOLIS;
	}
	private String name;
	private String description; //Laws, Walls, Guard, Paths, Locations, Lighting
	private Type type;
	private String baseValue; //in gp
	private String minorItems;
	private String mediumItems;
	private String majorItems;
	
	public Community(String name, String description, Type type, String baseValue, String minorItems,
			String mediumItems, String majorItems) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.baseValue = baseValue;
		this.minorItems = minorItems;
		this.mediumItems = mediumItems;
		this.majorItems = majorItems;
	}
	public Community(String fileName) {
		if (!fileName.endsWith(".community")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .community\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				name = Tools.readALine(read);
				description = Tools.readALine(read);
				type = Type.valueOf(Tools.readALine(read).toUpperCase());
				baseValue = Tools.readALine(read);
				minorItems = Tools.readALine(read);
				mediumItems = Tools.readALine(read);
				majorItems = Tools.readALine(read);
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret community file!");
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
	public Type getType() {
		return type;
	}
	public String getBaseValue() {
		return baseValue;
	}
	public String getMinorItems() {
		return minorItems;
	}
	public String getMediumItems() {
		return mediumItems;
	}
	public String getMajorItems() {
		return majorItems;
	}
}
