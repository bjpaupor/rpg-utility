package pathfinder;

public class Item {
	private String name;
	private String description;
	private double price;
	private double weight;
	private String material;
	private int hp;
	private int breakDC;
	
	public Item(String name, String description, double price, double weight, String material, int hp, int breakDC) {
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
	public double getPrice() {
		return price;
	}
	public double getWeight() {
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
