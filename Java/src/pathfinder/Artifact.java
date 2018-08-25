package pathfinder;

public class Artifact extends Item implements MagicItem {
	public enum Type {
		MINOR, MAJOR;
	}
	
	private Type type;
	private String destruction;
	private Aura aura;
	private String activation;
	private Slot slot;
	private int cl;
	private String[] requirements;
	private double cost;

	public Artifact(String name, String description, double price, double weight, String material, int hp, int breakDC,
			Aura aura, String activation, Slot slot, int cl, String[] requirements, double cost, Type type, 
			String destruction) {
		super(name, description, price, weight, material, hp, breakDC);
		this.aura = aura;
		this.activation = activation;
		this.slot = slot;
		this.cl = cl;
		this.requirements = requirements;
		this.cost = cost;
		this.type = type;
		this.destruction = destruction;
	}
	
	@Override
	public Aura getAura() {
		return aura;
	}

	@Override
	public String getActivation() {
		return activation;
	}

	@Override
	public Slot getSlot() {
		return slot;
	}

	@Override
	public int getCL() {
		return cl;
	}

	@Override
	public String[] getRequirements() {
		return requirements;
	}

	@Override
	public double getCost() {
		return cost;
	}

	public Type getType() {
		return type;
	}

	public String getDestruction() {
		return destruction;
	}

}
