package pathfinder;

public class Dungeon {
	private String name;
	private String description; //walls, floors, doors, and stairs
	
	public Dungeon(String name, String description) {
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
