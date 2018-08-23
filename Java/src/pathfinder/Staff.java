package pathfinder;

public class Staff extends Item implements MagicItem {
	private Pair<Spell, Integer>[] spells; //spell, charges
	private Aura aura;
	private int cl;
	private String[] requirements;
	private double cost;

	public Staff(String name, String description, double price, double weight, String material, int hp, int breakDC,
			Pair<Spell, Integer>[] spells, Aura aura, int cl, String[] requirements, double cost) {
		super(name, description, price, weight, material, hp, breakDC);
		this.spells = spells;
		this.aura = aura;
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
		return "Spell Trigger";
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
	public double getCost() {
		return cost;
	}

	public Pair<Spell, Integer>[] getSpells() {
		return spells;
	}
}
