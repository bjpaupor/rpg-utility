package pathfinder;

public class Wand extends Item implements MagicItem {
	private Spell spell;
	private int cl;
	private double cost;

	public Wand(String name, String description, double price, double weight, String material, int hp, int breakDC,
			Spell spell, int cl, double cost) {
		super(name, description, price, weight, material, hp, breakDC);
		this.spell = spell;
		this.cl = cl;
		this.cost = cost;
	}

	@Override
	public Aura getAura() {
		return new Aura(spell.getSchool(), spell.getDescriptors(), Aura.getSpellStrength(spell.getLevel()));
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
		return new String[] { "Craft Wand", spell.getName() };
	}

	@Override
	public double getCost() {
		return cost;
	}
}
