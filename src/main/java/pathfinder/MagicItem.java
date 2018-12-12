package pathfinder;

public interface MagicItem {
	public enum Slot {
		ARMOR, BELT, BODY, CHEST, EYES, FEET, HANDS, HEAD, HEADBAND, NECK, NONE, RING, SHIELD, SHOULDERS, WRIST;
	}
	public enum Activation {
		COMMAND_WORD, SPELL_COMPLETION, SPELL_TRIGGER, USE_ACTIVATED;
	}
	public Aura getAura();
	public Activation getActivation();
	public Slot getSlot();
	public int getCL();
	public String[] getRequirements();
	public String getCost();
	public String getName();
	public String getDescription();
	public String getPrice();
	public String getWeight();
	public String getMaterial();
	public int getHp();
	public int getBreakDC();
}
