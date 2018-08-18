package pathfinder;

public class Class {
	private String name;
	private String description; //description role
	private Alignment[] alignments; //allowed
	private HitDice hitDie;
	private String[] skills; //Skills
	private int skillRanksPerLevel;
	private Feature[] features;
	private BABProgression bab;
	private Save[] goodSaves;
	
	public Class(String name, String description, Alignment[] alignments, HitDice hitDie, String[] skills,
			int skillRanksPerLevel, Feature[] features, BABProgression bab, Save[] goodSaves) {
		this.name = name;
		this.description = description;
		this.alignments = alignments;
		this.hitDie = hitDie;
		this.skills = skills;
		this.skillRanksPerLevel = skillRanksPerLevel;
		this.features = features;
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
	public String[] getSkills() {
		return skills;
	}
	public int getSkillRanksPerLevel() {
		return skillRanksPerLevel;
	}
	public Feature[] getFeatures() {
		return features;
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
