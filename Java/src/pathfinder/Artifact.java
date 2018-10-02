package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
/**
 * A difficult to destroy magical item
 * @author brandon
 *
 */
public class Artifact extends Item implements MagicItem {
	public enum Type {
		MINOR, MAJOR;
	}
	
	private Type type;
	private String destruction;
	private Aura aura;
	private Activation activation;
	private Slot slot;
	private int cl;

	public Artifact(String name, String description, String weight, String material, int hp, int breakDC,
			Aura aura, Activation activation, Slot slot, int cl, Type type, String destruction) {
		super(name, description, "none", weight, material, hp, breakDC);
		this.aura = aura;
		this.activation = activation;
		this.slot = slot;
		this.cl = cl;
		this.type = type;
		this.destruction = destruction;
	}
	public Artifact(String fileName) {
		super(null, null, null, null, null, 0, 0);
		if (!fileName.endsWith(".artifact")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .artifact\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				setName(Tools.readALine(read));
				setDescription(Tools.readALine(read));
				setPrice(Tools.readALine(read));
				setWeight(Tools.readALine(read));
				setMaterial(Tools.readALine(read));
				setHP(Integer.parseInt(Tools.readALine(read)));
				setBreakDC(Integer.parseInt(Tools.readALine(read)));
				School school = School.valueOf(Tools.readALine(read).toUpperCase());
				Descriptor[] descriptors = new Descriptor[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < descriptors.length; i++)
					descriptors[i] = Descriptor.valueOf(Tools.readALine(read).toUpperCase());
				activation = Activation.valueOf(Tools.readALine(read).toUpperCase());
				slot = Slot.valueOf(Tools.readALine(read).toUpperCase());
				cl = Integer.parseInt(Tools.readALine(read));
				aura = new Aura(school, descriptors, Aura.getItemStrength(cl));
				type = Type.valueOf(Tools.readALine(read).toUpperCase());
				destruction = Tools.readALine(read);
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret artifact file!");
				ex.printStackTrace();
				return;
			}	
	}
	@Override
	public Aura getAura() {
		return aura;
	}

	@Override
	public Activation getActivation() {
		return activation;
	}

	@Override
	public Slot getSlot() {
		return slot;
	}

	@Override
	public int getCL() {
		return cl;
	}

	@Override
	public String[] getRequirements() {
		return null;
	}

	@Override
	public String getCost() {
		return "none";
	}

	public Type getType() {
		return type;
	}

	public String getDestruction() {
		return destruction;
	}

}
