package pathfinder;

public class MagicArmor extends Armor implements MagicItem {
	public class SpecialAbility {
		private String name;
		private String description;
		private Aura aura;
		private int cl;
		private String[] requirements;
		private String price;
		
		public SpecialAbility(String name, String description, Aura aura, int cl, String[] requirements, String price) {
			this.name = name;
			this.description = description;
			this.aura = aura;
			this.cl = cl;
			this.requirements = requirements;
			this.price = price;
		}
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
		public Aura getAura() {
			return aura;
		}
		public int getCL() {
			return cl;
		}
		public String[] getRequirements() {
			return requirements;
		}
		public String getPrice() {
			return price;
		}
	}
	private int enhancement;
	private SpecialAbility[] specialAbilities;
	private int highestCL; //highest cl not 3 * enhancement;
	private Activation activation;
	private String[] requirements;
	private String cost;

	public MagicArmor(String name, String description, String price, String weight, String material, int hp,
			int breakDC, int bonus, int maxDex, int armorCheckPenalty, int arcaneSpellFailure, boolean speedReduced,
			boolean forHumanoid, Type type, Size size, int enhancement, SpecialAbility[] specialAbilities, 
			Activation activation, String[] requirements, String cost) {
		super(name, description, price, weight, material, breakDC, bonus, maxDex, armorCheckPenalty,
				arcaneSpellFailure, speedReduced, forHumanoid, type, size);
		this.enhancement = enhancement;
		this.specialAbilities = specialAbilities;
		highestCL = 0;
		for (SpecialAbility sa : specialAbilities)
			if (sa.getCL() > highestCL) 
				highestCL = sa.getCL();
		this.activation = activation;
		this.requirements = requirements;
		this.cost = cost;
	}
	@Override
	public Aura getAura() {
		Aura result = null;
		if (3 * enhancement < highestCL) 
			for (SpecialAbility sa : specialAbilities)
				if (sa.getCL() == highestCL) 
					result = sa.getAura();
		else 
			result = new Aura(School.ABJURATION, null, Aura.getItemStrength(3 * enhancement));
		return result;
	}

	@Override
	public Activation getActivation() {
		return activation;
	}

	@Override
	public Slot getSlot() {
		return Slot.ARMOR;
	}

	@Override
	public int getCL() {
		return 3 * enhancement > highestCL ? 3 * enhancement : highestCL;
	}

	@Override
	public String[] getRequirements() {
		return requirements;
	}

	@Override
	public String getCost() {
		return cost;
	}

	public int getEnhancement() {
		return enhancement;
	}

	public SpecialAbility[] getSpecialAbilities() {
		return specialAbilities;
	}

}
