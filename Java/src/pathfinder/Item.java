package pathfinder;

public class Item {
	private String name;
	private String description;
	private String price;
	private String weight;
	private String material;
	private int hp;
	private int breakDC;
	
	public Item(String name, String description, String price, String weight, String material, int hp, int breakDC) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.weight = weight;
		this.material = material;
		this.hp = hp;
		this.breakDC = breakDC;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getPrice() {
		return price;
	}
	public String getWeight() {
		return weight;
	}
	public String getMaterial() {
		return material;
	}
	public int getHp() {
		return hp;
	}
	public int getBreakDC() {
		return breakDC;
	}

}
