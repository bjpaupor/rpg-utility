package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
/**
 * Special ability with a type
 * @author brandon
 */
public class Feat {
	/**
	 * The type of feat
	 * @author brandon
	 */
	public class Type {
		private String name;
		private String description;
		public Type(String name, String description) {
			this.name = name;
			this.description = description;
		}
		public Type(String fileName) {
			if (!fileName.endsWith(".featType")) {
				System.out.println("-> " + fileName + ": Incorrect file type, file must be .featType\n");
				return;
			}
			else 
				try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
					name = Tools.readALine(read);
					description = Tools.readALine(read);
				}
				catch (Exception ex) {
					System.out.println("->" + getName() + ": Failed to interpret feat type file!");
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
	}
	
	private String name;
	private String description;
	private String type; //Feat type name
	
	public Feat(String name, String description, String type) {
		this.name = name;
		this.description = description;
		this.type = type;
	}
	public Feat(String fileName) {
		if (!fileName.endsWith(".feat")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .feat\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				name = Tools.readALine(read);
				description = Tools.readALine(read);
				type = Tools.readALine(read);
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret feat file!");
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
	public String getType() {
		return type;
	}
}
