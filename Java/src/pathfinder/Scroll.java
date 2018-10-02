package pathfinder;

public class Scroll extends Item implements MagicItem {
	public enum Type {
		ARCANE, DIVINE;
	}
	private Spell spell;
	private Type type;
	private String language;
	private int cl;
	private String cost;
	
	public Scroll(String name, String description, String price, String weight, String material, int hp, int breakDC,
			Spell spell, Type type, String language, int cl, String cost) {
		super(name, description, price, weight, material, hp, breakDC);
		this.spell = spell;
		this.type = type;
		this.language = language;
		this.cl = cl;
		this.cost = cost;
	}

	@Override
	public Aura getAura() {
		return new Aura(spell.getSchool(), spell.getDescriptors(), Aura.getSpellStrength(spell.getLevel()));
	}

	@Override
	public String getActivation() {
		return "Spell Completion";
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
		return new String[] { "Scribe Scroll", spell.getName() };
	}

	@Override
	public String getCost() {
		return cost;
	}

	public Spell getSpell() {
		return spell;
	}

	public Type getType() {
		return type;
	}

	public String getLanguage() {
		return language;
	}

}
