package pathfinder;
/**
 * Persistent negative effect applied to a creature
 * @author Brandon
 *
 */
public class Affliction {
	public enum Type {
		CONTACT, CURSE, DISEASE, INGESTED, INJURY, INHALED, SPELL, TRAP;
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

}
