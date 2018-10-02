package pathfinder;

public class IntelligentItem extends Item implements MagicItem {
	private Alignment alignment;
	private int intelligence;
	private int wisdom;
	private int charisma;
	private String[] languages;
	private String[] senses;
	private String communication;
	private String[] powers;
	private String specialPurpose;
	private int ego;
	private MagicItem baseItem;
	
	public IntelligentItem(Alignment alignment, int intelligence, int wisdom, int charisma, String[] languages,
			String[] senses, String communication, String[] powers, String specialPurpose, int ego, MagicItem baseItem) {
		super(baseItem.getName(), baseItem.getDescription(), baseItem.getPrice(), baseItem.getWeight(), 
				baseItem.getMaterial(), baseItem.getHp(), baseItem.getBreakDC());
		this.alignment = alignment;
		this.intelligence = intelligence;
		this.wisdom = wisdom;
		this.charisma = charisma;
		this.languages = languages;
		this.senses = senses;
		this.communication = communication;
		this.powers = powers;
		this.specialPurpose = specialPurpose;
		this.ego = ego;
		this.baseItem = baseItem;
	}

	@Override
	public Aura getAura() {
		return baseItem.getAura();
	}

	@Override
	public String getActivation() {
		return baseItem.getActivation();
	}

	@Override
	public Slot getSlot() {
		return baseItem.getSlot();
	}

	@Override
	public int getCL() {
		return baseItem.getCL();
	}

	@Override
	public String[] getRequirements() {
		return baseItem.getRequirements();
	}

	@Override
	public String getCost() {
		return baseItem.getCost();
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getWisdom() {
		return wisdom;
	}

	public int getCharisma() {
		return charisma;
	}

	public String[] getLanguages() {
		return languages;
	}

	public String[] getSenses() {
		return senses;
	}

	public String getCommunication() {
		return communication;
	}

	public String[] getPowers() {
		return powers;
	}

	public String getSpecialPurpose() {
		return specialPurpose;
	}

	public int getEgo() {
		return ego;
	}

	public MagicItem getBaseItem() {
		return baseItem;
	}

}
