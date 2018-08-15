package pathfinder;

public class CreatureSubType {
	private String name;
	private String description;
	private Feature[] features;
	
	public CreatureSubType(String name, String description, Feature[] features) {
		this.name = name;
		this.description = description;
		this.features = features;
	}
	public String getDescription() {
		return description;
	}
	public Feature[] getFeatures() {
		return features;
	}
	public String getName() {
		return name;
	}

}
