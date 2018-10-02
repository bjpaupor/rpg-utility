package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Persistent negative effect applied to a creature
 * @author Brandon
 *
 */
public class Affliction {
	public enum Type {
		CONTACT, CURSE, DISEASE, INGESTED, INJURY, INHALED, SPELL, TRAP;
	}
	
	private String name;
	private String description;
	private Type[] types;
	private String save;
	private String onset;
	private String frequency;
	private String initialEffect;
	private String effect;
	private String cure;
	
	public Affliction(String name, String description, Type[] types, String save, String onset, String frequency,
			String initialEffect, String effect, String cure) {
		this.name = name;
		this.description = description;
		this.types = types;
		this.save = save;
		this.onset = onset;
		this.frequency = frequency;
		this.initialEffect = initialEffect;
		this.effect = effect;
		this.cure = cure;
	}
	public Affliction(String fileName) {
		if (!fileName.endsWith(".affliction")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .affliction\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				name = Tools.readALine(read);
				description = Tools.readALine(read);
				types = new Type[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < types.length; i++)
					types[i] = Type.valueOf(Tools.readALine(read));
				save = Tools.readALine(read);
				onset = Tools.readALine(read);
				frequency = Tools.readALine(read);
				initialEffect = Tools.readALine(read);
				effect = Tools.readALine(read);
				cure = Tools.readALine(read);
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret affliction file!");
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
	public Type[] getTypes() {
		return types;
	}
	public String getSave() {
		return save;
	}
	public String getOnset() {
		return onset;
	}
	public String getFrequency() {
		return frequency;
	}
	public String getInitialEffect() {
		return initialEffect;
	}
	public String getEffect() {
		return effect;
	}
	public String getCure() {
		return cure;
	}

}
