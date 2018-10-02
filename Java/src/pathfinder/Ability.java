package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * A Feature with a type - Extraordinary, Supernatural, Spell-Like Ability
 * @author Brandon
 */
public class Ability extends Feature {
	public enum Type {
		EX, SU, SLA;
	}
	
	private Type type;

	public Ability(String name, String description, Type type) {
		super(name, description);
		this.type = type;
	}
	public Ability(String fileName) {
		super(null, null);
		if (!fileName.endsWith(".ability")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .ability\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				setName(Tools.readALine(read));
				setDescription(Tools.readALine(read));
				type = Type.valueOf(Tools.readALine(read).toUpperCase());
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret ability file!");
				ex.printStackTrace();
				return;
			}	
	}
	public Type getType() {
		return type;
	}

}
