package pathfinder;

public class Skill {
	private String name;
	private AbilityScore keyAbility;
	private String description; //description check action tryAgain special restriction untrained
	public String getName() {
		return name;
	}
	public AbilityScore getKeyAbility() {
		return keyAbility;
	}
	public String getDescription() {
		return description;
	}
	public Skill(String name, AbilityScore keyAbility, String description) {
		this.name = name;
		this.keyAbility = keyAbility;
		this.description = description;
	}

}
