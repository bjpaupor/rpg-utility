package pathfinder;

import java.util.List;

import org.bson.Document;

/**
 * Persistent negative effect applied to a creature
 * @author Brandon
 *
 */
public class Affliction {
	public enum Type {
		CONTACT, CURSE, DISEASE, INGESTED, INJURY, INHALED, SPELL, TRAP;
		@Override
		public String toString() {
			return name();
		}
	}
	
	private String name;
	private String description;
	private Type[] types;
	private String save;
	private String onset;
	private String frequency;
	private String initialEffect;
	private String effect;
	private String cure;

	/**
	 * Given document must have a name
	 * @param d
	 */
	@SuppressWarnings("unchecked")
	public Affliction(Document d) {
		this.name = d.getString("name");
		this.description = d.getString("description");
		try {
			List<String> temp = (List<String>) d.get("types");
			types = new Type[temp.size()];
			for (int i = 0; i < types.length; i++) 
				types[i] = Type.valueOf(temp.get(i));
		} catch(ClassCastException e) {
			types = null;
		}
		this.save = d.getString("save");
		this.onset = d.getString("onset");
		this.frequency = d.getString("frequency");
		this.initialEffect =  d.getString("init_effect");
		this.effect = d.getString("effect");
		this.cure = d.getString("cure");
	}
	public Affliction(String name, String description, Type[] types, String save, String onset, String frequency,
			String initialEffect, String effect, String cure) {
		this.name = name;
		this.description = description;
		this.types = types;
		this.save = save;
		this.onset = onset;
		this.frequency = frequency;
		this.initialEffect = initialEffect;
		this.effect = effect;
		this.cure = cure;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Type[] getTypes() {
		return types;
	}
	public String getSave() {
		return save;
	}
	public String getOnset() {
		return onset;
	}
	public String getFrequency() {
		return frequency;
	}
	public String getInitialEffect() {
		return initialEffect;
	}
	public String getEffect() {
		return effect;
	}
	public String getCure() {
		return cure;
	}
	@Override
	public String toString() {
		String result = "Affliction:" + getName() + ":" + getDescription() + 
			":";
		for (Type t : getTypes())
			result += t + ":";
		result += getSave() + ":" + getOnset() + ":" + getFrequency() + ":" 
			+ getInitialEffect() + ":" + getEffect() + ":" + getCure();
		return result;
	}
}
