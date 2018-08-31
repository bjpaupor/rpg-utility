package pathfinder;

public class Feature {
	private String name;
	private String description;
	
	public Feature(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	@Override
	public String toString() {
		return "Name:" + name + " Description:" + description;
	}
}
