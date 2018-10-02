package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;

public class Armor extends Item {
	public enum Type {
		LIGHT, MEDIUM, HEAVY, SHIELD;
	}
	
	private int bonus;
	private int maxDex;
	private int armorCheckPenalty;
	private int arcaneSpellFailure; //a percent
	private boolean speedReduced;
	private Type type;
	private Size size; //of wearer
	
	public Armor(String name, String description, String price, String weight, String material, int breakDC,
			int bonus, int maxDex, int armorCheckPenalty, int arcaneSpellFailure, boolean speedReduced, Type type,
			Size size) {
		super(name, description, price, weight, material, getArmorHP(bonus, size), breakDC);
		this.bonus = bonus;
		this.maxDex = maxDex;
		this.armorCheckPenalty = armorCheckPenalty;
		this.arcaneSpellFailure = arcaneSpellFailure;
		this.speedReduced = speedReduced;
		this.type = type;
		this.size = size;
	}
	public Armor(String fileName) {
		super(null, null, null, null, null, 0, 0);
		if (!fileName.endsWith(".armor")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .armor\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				setName(Tools.readALine(read));
				setDescription(Tools.readALine(read));
				setPrice(Tools.readALine(read));
				setWeight(Tools.readALine(read));
				setMaterial(Tools.readALine(read));
				setBreakDC(Integer.parseInt(Tools.readALine(read)));
				bonus = Integer.parseInt(Tools.readALine(read));
				maxDex = Integer.parseInt(Tools.readALine(read));
				armorCheckPenalty = Integer.parseInt(Tools.readALine(read));
				arcaneSpellFailure = Integer.parseInt(Tools.readALine(read));
				speedReduced = Boolean.parseBoolean(Tools.readALine(read));
				type = Type.valueOf(Tools.readALine(read));
				size = Size.valueOf(Tools.readALine(read));
				setHP(getArmorHP(bonus, size));
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret armor file!");
				ex.printStackTrace();
				return;
			}	
	}
	public static int getArmorHP(int bonus, Size size) {
		return (int)(bonus * 5 * Math.pow(2, size.compareTo(Size.MEDIUM)));
	}
	public int getBonus() {
		return bonus;
	}

	public int getMaxDex() {
		return maxDex;
	}

	public int getArmorCheckPenalty() {
		return armorCheckPenalty;
	}

	public int getArcaneSpellFailure() {
		return arcaneSpellFailure;
	}

	public boolean isSpeedReduced() {
		return speedReduced;
	}

	public Type getType() {
		return type;
	}

	public Size getSize() {
		return size;
	}

}
