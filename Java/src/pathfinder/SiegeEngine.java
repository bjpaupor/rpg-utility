package pathfinder;

public class SiegeEngine extends Weapon {
	private int crew;
	private Armor armor;
	
	public SiegeEngine(String name, String description, double price, double weight, String material, int hp,
			int breakDC, String damage, String critical, int range, DamageType damageType, Feature[] features,
			Proficiency proficiency, Size size, int crew, Armor armor) {
		super(name, description, price, weight, material, hp, breakDC, damage, critical, range, damageType, features,
				proficiency, size);
		this.crew = crew;
		this.armor = armor;
	}

	public int getCrew() {
		return crew;
	}

	public Armor getArmor() {
		return armor;
	}
}
