package pathfinder;

public class Affliction {
	private String name;
	private String description;
	private String[] types;
	private String save;
	private String onset;
	private String frequency;
	private String initialEffect;
	private String secondaryEffect;
	private String cure;
	
	public Affliction(String name, String description, String[] types, String save, String onset, String frequency,
			String initialEffect, String secondaryEffect, String cure) {
		this.name = name;
		this.description = description;
		this.types = types;
		this.save = save;
		this.onset = onset;
		this.frequency = frequency;
		this.initialEffect = initialEffect;
		this.secondaryEffect = secondaryEffect;
		this.cure = cure;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String[] getTypes() {
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
	public String getSecondaryEffect() {
		return secondaryEffect;
	}
	public String getCure() {
		return cure;
	}

}
