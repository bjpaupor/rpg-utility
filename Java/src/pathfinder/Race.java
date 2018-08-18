package pathfinder;

public class Race {
	private String name;
	private String description; // role physicalDescription society relations alignmentAndReligion adventurers
	private String[] maleNames;
	private String[] femaleNames;
	private Feature[] traits;
	
	public Race (String name, String description, String[] maleNames, String[] femaleNames, Feature[] traits) {
		this.name = name;
		this.description = description;
		this.maleNames = maleNames;
		this.femaleNames = femaleNames;
		this.traits = traits;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String[] getMaleNames() {
		return maleNames;
	}
	public String[] getFemaleNames() {
		return femaleNames;
	}
	public Feature[] getTraits() {
		return traits;
	}
	public static void main(String[] args) {
		
	}
}
