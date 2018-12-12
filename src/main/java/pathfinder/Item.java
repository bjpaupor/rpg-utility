package pathfinder;

public class Item {
	private String name;
	private String description;
	private String price; //In gp
	private String weight; //In lbs
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
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public void setHP(int hp) {
		this.hp = hp;
	}
	public void setBreakDC(int breakDC) {
		this.breakDC = breakDC;
	}

}
