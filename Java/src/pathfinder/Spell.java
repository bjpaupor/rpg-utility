package pathfinder;

public class Spell {

	public enum SubSchool {
		CALLING, CHARM, COMPULSION, CREATION, FIGMENT, GLAMER, HEALING, PATTERN, PHANTASM, POLYMORPH, SCRYING, 
		SHADOW, SUMMONING, TELEPORTATION;
	}

	public enum Component {
		VERBAL, SOMATIC, MATERIAL, FOCUS, DIVINE_FOCUS;
	}
	
	private String name;
	private String shortDescription;
	private School school;
	private SubSchool[] subSchools;
	private Descriptor[] descriptors;
	private int level;
	private String castingTime;
	private Component[] components;
	private String range;
	private String aiming; //target, effect, area
	private String duration;
	private String savingThrow;
	private boolean spellResistance;
	private String description;
	
	public Spell(String name, String shortDescription, School school, SubSchool[] subSchools, Descriptor[] descriptors,
			int level, String castingTime, Component[] components, String range, String aiming, String duration,
			String savingThrow, boolean spellResistance, String description) {
		this.name = name;
		this.shortDescription = shortDescription;
		this.school = school;
		this.subSchools = subSchools;
		this.descriptors = descriptors;
		this.level = level;
		this.castingTime = castingTime;
		this.components = components;
		this.range = range;
		this.aiming = aiming;
		this.duration = duration;
		this.savingThrow = savingThrow;
		this.spellResistance = spellResistance;
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public School getSchool() {
		return school;
	}
	public SubSchool[] getSubSchools() {
		return subSchools;
	}
	public Descriptor[] getDescriptors() {
		return descriptors;
	}
	public int getLevel() {
		return level;
	}
	public String getCastingTime() {
		return castingTime;
	}
	public Component[] getComponents() {
		return components;
	}
	public String getRange() {
		return range;
	}
	public String getAiming() {
		return aiming;
	}
	public String getDuration() {
		return duration;
	}
	public String getSavingThrow() {
		return savingThrow;
	}
	public boolean isSpellResistance() {
		return spellResistance;
	}
	public String getDescription() {
		return description;
	}

}
