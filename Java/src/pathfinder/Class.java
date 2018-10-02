package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;
/**
 * A class for characters 
 * @author brandon
 *
 */
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
	//Filename doesn't follow convention because .class files are already used
	public Class(String fileName) {
		if (!fileName.endsWith(".pclass")) {
			System.out.println("-> " + fileName + ": Incorrect file type, file must be .pclass\n");
			return;
		}
		else 
			try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
				name = Tools.readALine(read);
				description = Tools.readALine(read);
				alignments = new Alignment[Integer.parseInt(Tools.readALine(read))];
				if (alignments.length == 9)
					alignments = Alignment.values();
				else
					for (int i = 0; i < alignments.length; i++)
						alignments[i] = Alignment.valueOf(Tools.readALine(read).toUpperCase());
				hitDie = HitDice.valueOf(Tools.readALine(read).toLowerCase());
				skills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < skills.length; i++)
					skills[i] = Tools.readALine(read);
				skillRanksPerLevel = Integer.parseInt(Tools.readALine(read));
				features = new Feature[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < features.length; i++)
					features[i] = new Feature(Tools.readALine(read), Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				if (goodSaves.length == 3)
					goodSaves = Save.values();
				else
					for (int i = 0; i < goodSaves.length; i++)
						goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
			}
			catch (Exception ex) {
				System.out.println("->" + getName() + ": Failed to interpret class file!");
				ex.printStackTrace();
				return;
			}	
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
