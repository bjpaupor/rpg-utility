package pathfinder;

public class Feat {
	private String name;
	private String description;
	private String type; //Feat type name
	public Feat(String name, String description, String type) {
		this.name = name;
		this.description = description;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getType() {
		return type;
	}
}
