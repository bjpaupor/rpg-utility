package pathfinder;

public class Community {
	public enum Type {
		THORP, HAMLET, VILLAGE, SMALL_TOWN, LARGE_TOWN, SMALL_CITY, LARGE_CITY, METROPOLIS;
	}
	private String name;
	private String description; //Laws, Walls, Guard, Paths, Locations, Lighting
	private Type type;
	private double baseValue;
	private String minorItems;
	private String mediumItems;
	private String majorItems;
	
	public Community(String name, String description, Type type, double baseValue, String minorItems,
			String mediumItems, String majorItems) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.baseValue = baseValue;
		this.minorItems = minorItems;
		this.mediumItems = mediumItems;
		this.majorItems = majorItems;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Type getType() {
		return type;
	}
	public double getBaseValue() {
		return baseValue;
	}
	public String getMinorItems() {
		return minorItems;
	}
	public String getMediumItems() {
		return mediumItems;
	}
	public String getMajorItems() {
		return majorItems;
	}
}
