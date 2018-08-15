package pathfinder;

public class Ability extends Feature {
	private AbilityType type;

	public Ability(String name, String description, AbilityType type) {
		super(name, description);
		this.type = type;
	}

	public AbilityType getType() {
		return type;
	}

}
