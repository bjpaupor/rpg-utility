package pathfinder;
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

	public Type getType() {
		return type;
	}

}
