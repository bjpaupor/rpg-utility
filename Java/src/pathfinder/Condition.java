package pathfinder;

public class Condition {
	private String name;
	private String description;
	
	public Condition(String name, String description) {
		this.name = name;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
}
