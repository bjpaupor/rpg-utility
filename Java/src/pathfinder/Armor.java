package pathfinder;

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
	
	public Armor(String name, String description, String price, String weight, String material, int hp, int breakDC,
			int bonus, int maxDex, int armorCheckPenalty, int arcaneSpellFailure, boolean speedReduced, Type type,
			Size size) {
		super(name, description, price, weight, material, hp, breakDC);
		this.bonus = bonus;
		this.maxDex = maxDex;
		this.armorCheckPenalty = armorCheckPenalty;
		this.arcaneSpellFailure = arcaneSpellFailure;
		this.speedReduced = speedReduced;
		this.type = type;
		this.size = size;
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
