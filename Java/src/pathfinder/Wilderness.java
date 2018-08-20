package pathfinder;

public class Wilderness {
	private String name;
	private String description;
	private Terrain terrain;
	
	public Wilderness(String name, String description, Terrain terrain) {
		this.name = name;
		this.description = description;
		this.terrain = terrain;
	}
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Terrain getTerrain() {
		return terrain;
	}
}
