package pathfinder;

public class Weapon extends Item {
	public enum Proficiency {
		SIMPLE, MARTIAL, EXOTIC;
	}
	public enum Category {
		LIGHT, ONE_HANDED, TWO_HANDED; 
	}
	
	private String damage;
	private String critical;
	private int range;
	private DamageType damageType;
	private Feature[] features;
	private Proficiency proficiency;
	private Size size; //of wielder
	
	public Weapon(String name, String description, double price, double weight, String material, int hp, int breakDC,
			String damage, String critical, int range, DamageType damageType, Feature[] features,
			Proficiency proficiency, Size size) {
		super(name, description, price, weight, material, hp, breakDC);
		this.damage = damage;
		this.critical = critical;
		this.range = range;
		this.damageType = damageType;
		this.features = features;
		this.proficiency = proficiency;
		this.size = size;
	}

	public String getDamage() {
		return damage;
	}

	public String getCritical() {
		return critical;
	}

	public int getRange() {
		return range;
	}

	public DamageType getDamageType() {
		return damageType;
	}

	public Feature[] getFeatures() {
		return features;
	}

	public Proficiency getProficiency() {
		return proficiency;
	}

	public Size getSize() {
		return size;
	}

}
