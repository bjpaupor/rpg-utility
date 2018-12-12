package pathfinder;

public class Material {
	private String name;
	private String description;
	private int hardness;
	private int hpPerInch;
	
	public Material(String name, String description, int hardness, int hpPerInch) {
		this.name = name;
		this.description = description;
		this.hardness = hardness;
		this.hpPerInch = hpPerInch;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getHardness() {
		return hardness;
	}
	public int getHpPerInch() {
		return hpPerInch;
	}

}
