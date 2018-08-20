package pathfinder;

public class Encounter {
	private String name;
	private String description;
	private int xp;
	
	public Encounter(String name, String description, int xp) {
		this.name = name;
		this.description = description;
		this.xp = xp;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getXP() {
		return xp;
	}
}
