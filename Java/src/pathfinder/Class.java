package pathfinder;

public class Class {
	private String name;
	private String description; //description role
	private Alignment[] alignments; //allowed
	private HitDice hitDie;
	private String[] classSkills; //Skills
	private int skillRanksPerLevel;
	private Feature[] classFeatures;
	private BABProgression bab;
	private Save[] goodSaves;
	
	public Class(String name, String description, Alignment[] alignments, HitDice hitDie, String[] classSkills,
			int skillRanksPerLevel, Feature[] classFeatures, BABProgression bab, Save[] goodSaves) {
		this.name = name;
		this.description = description;
		this.alignments = alignments;
		this.hitDie = hitDie;
		this.classSkills = classSkills;
		this.skillRanksPerLevel = skillRanksPerLevel;
		this.classFeatures = classFeatures;
		this.bab = bab;
		this.goodSaves = goodSaves;
	}
	public String getDescription() {
		return description;
	}
	public Alignment[] getAlignments() {
		return alignments;
	}
	public HitDice getHitDie() {
		return hitDie;
	}
	public String[] getClassSkills() {
		return classSkills;
	}
	public int getSkillRanksPerLevel() {
		return skillRanksPerLevel;
	}
	public Feature[] getClassFeatures() {
		return classFeatures;
	}
	public BABProgression getBab() {
		return bab;
	}
	public Save[] getGoodSaves() {
		return goodSaves;
	}
	public String getName() {
		return name;
	}
}
