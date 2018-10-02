package pathfinder;

public class Rod extends Item implements MagicItem {
	private Aura aura;
	private String activation;
	private int cl;
	private String[] requirements;
	private String cost;

	public Rod(String name, String description, String price, String weight, String material, int hp, int breakDC,
			Aura aura, String activation, int cl, String[] requirements, String cost) {
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
		return Slot.NONE;
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
	public String getCost() {
		return cost;
	}
}
