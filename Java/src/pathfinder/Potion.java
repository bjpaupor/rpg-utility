package pathfinder;

public class Potion extends Item implements MagicItem {
	private Spell spell;
	private int cl;
	private String cost;
	
	public Potion(String name, String description, String price, String weight, String material, int hp, int breakDC, 
			Spell spell, int cl, String cost) {
		super(name, description, price, weight, material, hp, breakDC);
		this.spell = spell;
		this.cl = cl;
		this.cost = cost;
	}

	public Spell getSpell() {
		return spell;
	}
	
	@Override
	public Aura getAura() {
		return new Aura(spell.getSchool(), spell.getDescriptors(), Aura.getSpellStrength(spell.getLevel()));
	}

	@Override
	public String getActivation() {
		return "Use Activated";
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
		return new String[] {"Brew Potion", spell.getName()};
	}

	@Override
	public String getCost() {
		return cost;
	}

}
