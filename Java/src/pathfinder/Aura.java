package pathfinder;
/**
 * A magical aura that can be studied
 * @author brandon
 *
 */
public class Aura {
	public enum Strength {
		FAINT, MODERATE, STRONG, OVERWHELMING;
	}
	private School school;
	private Descriptor[] descriptors;
	private Strength strength;
	
	public Aura(School school, Descriptor[] descriptors, Strength strength) {
		this.school = school;
		this.descriptors = descriptors;
		this.strength = strength;
	}
	
	public School getSchool() {
		return school;
	}

	public Descriptor[] getDescriptors() {
		return descriptors;
	}

	public Strength getStrength() {
		return strength;
	}

	public static Strength getItemStrength(int cl) {
		Strength result = Strength.FAINT;
		if (cl > 5 && cl < 12) 
			result = Strength.MODERATE;
		else if (cl > 11 && cl < 21) 
			result = Strength.STRONG;
		else
			result = Strength.OVERWHELMING;
		return result;
	}
	public static Strength getSpellStrength(int spellLevel) {
		Strength result = Strength.FAINT;
		if (spellLevel > 3 && spellLevel < 7) 
			result = Strength.MODERATE;
		else if (spellLevel > 6 && spellLevel < 10) 
			result = Strength.STRONG;
		else
			result = Strength.OVERWHELMING;
		return result;
	}
}
