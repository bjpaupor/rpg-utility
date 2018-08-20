package pathfinder;

public interface MagicItem {
	public enum Slot {
		ARMOR, BELT, BODY, CHEST, EYES, FEET, HANDS, HEAD, HEADBAND, NECK, RING, SHIELD, SHOULDERS, WRIST;
	}
	public String getAura();
	public String getActivation();
	public Slot getSlot();
	public int getCL();
	public String getConstruction();
	public String[] getRequirements();
	public String getCost();
}
