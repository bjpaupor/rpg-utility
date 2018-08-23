package pathfinder;

public class Ring extends Item implements MagicItem {
	private Aura aura;
	private String activation;
	private int cl;
	private String[] requirements;
	private double cost;

	public Ring(String name, String description, double price, double weight, String material, int hp, int breakDC,
			Aura aura, String activation, int cl, String[] requirements, double cost) {
		super(name, description, price, weight, material, hp, breakDC);
		this.aura = aura;
		this.activation = activation;
		this.cl = cl;
		this.requirements = requirements;
		this.cost = cost;
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
		return Slot.RING;
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
}
