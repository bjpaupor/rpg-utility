package pathfinder;

import org.bson.Document;

/**
 * A Feature with a type - Extraordinary, Supernatural, Spell-Like Ability
 * @author Brandon
 */
public class Ability extends Feature {
	public enum Type {
		EX, SU, SLA;
	}
	
	private Type type;
	
	/**
	 * Given document must have a name, description, and type
	 * @param d
	 */
	public Ability(Document d) {
		super(d.getString("name"), d.getString("description"));
		type = Type.valueOf(d.getString("type").toUpperCase());
	}
	public Ability(String name, String description, Type type) {
		super(name, description);
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	@Override
	public String toString() {
		return "Ability:" + getName() + ":" + getDescription() + ":" + type;
	}
}
