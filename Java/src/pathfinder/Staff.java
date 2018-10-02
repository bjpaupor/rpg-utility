package pathfinder;

public class Staff extends Item implements MagicItem {
	private Pair<Spell, Integer>[] spells; //spell, charges
	private Aura aura;
	private int cl;
	private String[] requirements;
	private String cost;

	public Staff(String name, String description, String price, String weight, String material, int hp, int breakDC,
			Pair<Spell, Integer>[] spells, Aura aura, int cl, String[] requirements, String cost) {
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
	public Activation getActivation() {
		return Activation.SPELL_TRIGGER;
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

	public Pair<Spell, Integer>[] getSpells() {
		return spells;
	}
}
