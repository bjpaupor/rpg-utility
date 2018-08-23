package pathfinder;

public interface MagicItem {
	public enum Slot {
		ARMOR, BELT, BODY, CHEST, EYES, FEET, HANDS, HEAD, HEADBAND, NECK, NONE, RING, SHIELD, SHOULDERS, WRIST;
	}
	public Aura getAura();
	public String getActivation();
	public Slot getSlot();
	public int getCL();
	public String[] getRequirements();
	public double getCost();
}
