package pathfinder;

public class MagicSiegeEngine extends MagicWeapon {
	private int crew;
	private Armor armor;

	public MagicSiegeEngine(String name, String description, String price, String weight, String material, int hp,
			int breakDC, String damage, String critical, int range, DamageType damageType, Feature[] features,
			Proficiency proficiency, Size size, int enhancement, SpecialAbility[] specialAbilities, String activation,
			String[] requirements, String cost, int crew, Armor armor) {
		super(name, description, price, weight, material, hp, breakDC, damage, critical, range, damageType, features,
				proficiency, size, enhancement, specialAbilities, activation, requirements, cost);
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
