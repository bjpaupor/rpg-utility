package creatureprint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Creature {
	private String creatureFileLocation; //where the file that generates all other values is located
	private String path; //Path to put created document
	private String name;
	private String fileName;
	private String basicName;
	private String shortDesc;
	private String titleName;
	private String cr;
	private Type type;
	private Terrain terrain; //OPT
	private Climate climate; //OPT
	private String xp;
	private String race; //OPT
	private Pair<String, Integer>[] classes; //OPT <class, level>
	private Alignment alignment;
	private Size size;
	private String[] subtypes; //OPT
	private int init;
	private String[] senses; //OPT
	private int perception;
	private String[] auras; //OPT
	
	//Defense
	private int ac;
	private int touch;
	private int flatFooted;
	private	Pair<Integer, String>[] acModifiers; //OPT <modifier, type>
	private	Pair<Integer, String>[] conditionalACModifiers; //OPT <modifier, type>
	private int hp;
	private String hpBreakdown; //(xdy+z)
	private int fastHealing; //OPT
	private Pair<Integer, String> regeneration; //OPT <amount, bypass>
	private int fort;
	private int ref;
	private int will;
	private Pair<Integer, String>[] saveModifiers;  //OPT <modifier, Type and condition>
	private String[] defensiveAbilities;  //OPT
	private Pair<Integer, String> dr; //OPT <amount, bypass>
	private String[] immunities; //OPT
	private Pair<String, Integer>[] resistances; //OPT <type, amount>
	private int sr;
	private String[] weaknesses;
	
	//Offense
	private int speed;
	private String[] speeds; //OPT each additional speed
	private String melee; //OPT
	private String ranged; //OPT
	private String space; //OPT
	private int reach; //OPT
	private String[] specialAttacks; //OPT
	private Pair<String[], Integer[]> spellLikeAbilities; //OPT <[use-spells], [cl, concentration]>
	private Trio<Integer[], String[], String[]> spellsKnown; //OPT <[cl, concentration], [use-spells], [className, special, special ...]>
	private Trio<Integer[], String[], String[]> spellsPrepared; //OPT <[cl, concentration], [use-spells], [className, special, special ...]>
	
	//Tactics
	private String beforeCombat; //OPT
	private String duringCombat; //OPT
	private String morale; //OPT
	private String baseStatistics; //OPT
	
	//Statistics
	private final int EMDASH = -25000;
	// -25000 represents —
	private int str;
	private int dex;
	private int con;
	private int intelligence;
	private int wis;
	private int cha;
	private int bab;
	private int cmb; 
	private String[] cmbs; //OPT
	private int cmd;
	private String[] cmds; //OPT
	private String[] feats; //OPT
	private String[] skills; //OPT
	private String[] racialModifiers; //OPT <modifier, skill>
	private String[] languages; //OPT
	private String[] languagesSpecial; //OPT, truespeech
	private String[] sq; //OPT
	private String[] combatGear; //OPT
	private String[] otherGear; //OPT
	
	//Ecology
	private String environment; //OPT
	private String organization; //OPT
	private String treasure; //OPT
	
	//Special Abilities
	private Pair<String, Pair<String, String[]>>[] specialAbilities; //OPT <Ability Name <Ability Description, Ability Sub-Description>>
	
	//Description
	private String description;
	
	@SuppressWarnings("unchecked")
	public Creature(String fileName) {
		if (!fileName.endsWith(".creature")) {
			System.out.println("Incorrect file type, file must be .creature\n");
			return;
		}
		try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
			creatureFileLocation = fileName;
			path = readALine(read);
			name = readALine(read);
			basicName = readALine(read);
			shortDesc = readALine(read);
			if (shortDesc.length() > 128)
				System.out.println("Short Description of " + name + " is long!(max sugg. 128 chars)");
			titleName = readALine(read);
			this.fileName = titleName + ".pdf";
			cr = readALine(read);
			type = Type.valueOf(readALine(read).toUpperCase());
			terrain = Terrain.valueOf(readALine(read).toUpperCase());
			climate = Climate.valueOf(readALine(read).toUpperCase());
			xp = readALine(read);
			race = readALine(read);
			classes = new Pair[Integer.parseInt(readALine(read))];
			for(int i = 0; i < classes.length; i++) {
				String className = readALine(read);
				Integer level = Integer.parseInt(readALine(read));
				classes[i] = new Pair<String, Integer>(className, level);
			}
			alignment = Alignment.valueOf(readALine(read).toUpperCase());
			size = Size.valueOf(readALine(read).toUpperCase());
			subtypes = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < subtypes.length; i++)
				subtypes[i] = readALine(read);
			init = Integer.parseInt(readALine(read));
			senses = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < senses.length; i++) 
				senses[i] = readALine(read);
			perception = Integer.parseInt(readALine(read));
			auras = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < auras.length; i++) {
				auras[i] = readALine(read);
			}
			ac = Integer.parseInt(readALine(read));
			touch = Integer.parseInt(readALine(read));
			flatFooted = Integer.parseInt(readALine(read));
			acModifiers = new Pair[Integer.parseInt(readALine(read))];
			for(int i = 0; i < acModifiers.length; i++) {
				Integer modifier = Integer.parseInt(readALine(read));
				String modifierType = readALine(read);
				acModifiers[i] = new Pair<Integer, String>(modifier, modifierType);
			}
			conditionalACModifiers = new Pair[Integer.parseInt(readALine(read))];
			for(int i = 0; i < conditionalACModifiers.length; i++) {
				Integer modifier = Integer.parseInt(readALine(read));
				String modifierTypeAndCondition = readALine(read);
				conditionalACModifiers[i] = new Pair<Integer, String>(modifier, modifierTypeAndCondition);
			}
			hp = Integer.parseInt(readALine(read));
			hpBreakdown = readALine(read);
			fastHealing = Integer.parseInt(readALine(read));
			Integer value = Integer.parseInt(readALine(read));
			if(value > 0) {
				String bypass = readALine(read);
				regeneration = new Pair<Integer, String>(value, bypass);
			}
			fort = Integer.parseInt(readALine(read));
			ref = Integer.parseInt(readALine(read));
			will = Integer.parseInt(readALine(read));
			saveModifiers = new Pair[Integer.parseInt(readALine(read))];
			for(int i = 0; i < saveModifiers.length; i++) {
				Integer modifier = Integer.parseInt(readALine(read));
				String modifierTypeAndCondition = readALine(read);
				saveModifiers[i] = new Pair<Integer, String>(modifier, modifierTypeAndCondition);
			}
			defensiveAbilities = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < defensiveAbilities.length; i++)
				defensiveAbilities[i] = readALine(read);
			value = Integer.parseInt(readALine(read));
			if(value > 0) {
				String bypass = readALine(read);
				dr = new Pair<Integer, String>(value, bypass);
			}
			immunities = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < immunities.length; i++)
				immunities[i] = readALine(read);
			resistances = new Pair[Integer.parseInt(readALine(read))];
			for (int i = 0; i < resistances.length; i++) {
				String type = readALine(read);
				Integer resistance = Integer.parseInt(readALine(read));
				resistances[i] = new Pair<String, Integer>(type, resistance);
			}
			sr = Integer.parseInt(readALine(read));
			weaknesses = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < weaknesses.length; i++)
				weaknesses[i] = readALine(read);
			speed = Integer.parseInt(readALine(read));
			speeds = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < speeds.length; i++)
				speeds[i] = readALine(read);
			melee = readALine(read);
			ranged = readALine(read);
			space = readALine(read);
			reach = Integer.parseInt(readALine(read));
			specialAttacks = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < specialAttacks.length; i++)
				specialAttacks[i] = readALine(read);
			spellLikeAbilities = new Pair<String[], Integer[]>(new String[0], new Integer[0]);
			int temp = Integer.parseInt(readALine(read));
			if (temp > 0) {
				String[] slaTemp = new String[temp];
				Integer[] slaIntTemp = new Integer[2];
				slaIntTemp[0] = Integer.parseInt(readALine(read));
				slaIntTemp[1] = Integer.parseInt(readALine(read));
				spellLikeAbilities.setY(slaIntTemp);
				for (int i = 0; i < slaTemp.length; i++)
					slaTemp[i] = readALine(read);
				spellLikeAbilities.setX(slaTemp);
			}
			spellsKnown = new Trio<Integer[], String[], String[]>(new Integer[0], new String[0], new String[0]);
			temp = Integer.parseInt(readALine(read));
			if (temp > 0) {
				String[] spellsKnownTemp = new String[temp];
				Integer[] spellsKnownInt = new Integer[2];
				String name = readALine(read);
				spellsKnownInt[0] = Integer.parseInt(readALine(read));
				spellsKnownInt[1] = Integer.parseInt(readALine(read));
				spellsKnown.setX(spellsKnownInt);
				String[] spellsKnownSpecial = new String[Integer.parseInt(readALine(read)) + 1];
				spellsKnownSpecial[0] = name;
				for (int i = 1; i < spellsKnownSpecial.length; i++) 
					spellsKnownSpecial[i] = readALine(read);
				for (int i = 0; i < spellsKnownTemp.length; i++)
					spellsKnownTemp[i] = readALine(read);
				spellsKnown.setY(spellsKnownTemp);
				spellsKnown.setZ(spellsKnownSpecial);
			}
			spellsPrepared = new Trio<Integer[], String[], String[]>(new Integer[0], new String[0], new String[0]);
			temp = Integer.parseInt(readALine(read));
			if (temp > 0) {
				String[] spellsPreparedTemp = new String[temp];
				Integer[] spellsPreparedInt = new Integer[2];
				String name = readALine(read);
				spellsPreparedInt[0] = Integer.parseInt(readALine(read));
				spellsPreparedInt[1] = Integer.parseInt(readALine(read));
				spellsPrepared.setX(spellsPreparedInt);
				String[] spellsPreparedSpecial = new String[Integer.parseInt(readALine(read)) + 1];
				spellsPreparedSpecial[0] = name;
				for (int i = 1; i < spellsPreparedSpecial.length; i++) 
					spellsPreparedSpecial[i] = readALine(read);
				for (int i = 0; i < spellsPreparedTemp.length; i++)
					spellsPreparedTemp[i] = readALine(read);
				spellsPrepared.setY(spellsPreparedTemp);
				spellsPrepared.setZ(spellsPreparedSpecial);
			}
			beforeCombat = readALine(read);
			duringCombat = readALine(read);
			morale = readALine(read);
			baseStatistics = readALine(read);
			str = Integer.parseInt(readALine(read));
			dex = Integer.parseInt(readALine(read));
			con = Integer.parseInt(readALine(read));
			intelligence = Integer.parseInt(readALine(read));
			wis = Integer.parseInt(readALine(read));
			cha = Integer.parseInt(readALine(read));
			bab = Integer.parseInt(readALine(read));
			cmb = Integer.parseInt(readALine(read));
			cmbs = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < cmbs.length; i++) 
				cmbs[i] = readALine(read);
			cmd = Integer.parseInt(readALine(read));
			cmds = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < cmds.length; i++) 
				cmds[i] = readALine(read);
			feats = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < feats.length; i++)
				feats[i] = readALine(read);
			skills = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < skills.length; i++)
				skills[i] = readALine(read);
			racialModifiers = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < racialModifiers.length; i++)
				racialModifiers[i] = readALine(read);
			languages = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < languages.length; i++)
				languages[i] = readALine(read);
			languagesSpecial = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < languagesSpecial.length; i++)
				languagesSpecial[i] = readALine(read);
			sq = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < sq.length; i++)
				sq[i] = readALine(read);
			combatGear = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < combatGear.length; i++)
				combatGear[i] = readALine(read);
			otherGear = new String[Integer.parseInt(readALine(read))];
			for (int i = 0; i < otherGear.length; i++)
				otherGear[i] = readALine(read);
			environment = readALine(read);
			organization = readALine(read);
			treasure = readALine(read);
			specialAbilities = new Pair[Integer.parseInt(readALine(read))];
			for (int i = 0; i < specialAbilities.length; i++) {
				String bold = readALine(read);
				String text = readALine(read);
				String[] subDescriptions = new String[Integer.parseInt(readALine(read))];
				for (int j = 0; j < subDescriptions.length; j++) 
					subDescriptions[j] = readALine(read);
				Pair<String, String[]> description = new Pair<String, String[]>(text, subDescriptions);
				specialAbilities[i] = new Pair<String, Pair<String, String[]>>(bold, description);
			}
			description = readALine(read);
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}
	}
	//writes a single string to a file and a newLine 
	private void writeALine(BufferedWriter file, String s) throws IOException{
		file.write(s);
		file.newLine();
	}
	//Obliterates comments
	public void saveToFile() throws IOException {
		BufferedWriter file = new BufferedWriter(new FileWriter(creatureFileLocation));
		writeALine(file, "Where to place finished PDF:" + path);
		writeALine(file, "Name:" + name);
		writeALine(file, "Basic Name:" + basicName);
		writeALine(file, "Short Description:" + shortDesc);
		writeALine(file, "Title Name:" + titleName);
		writeALine(file, "CR:" + cr);
		writeALine(file, "Type:" + type);
		writeALine(file, "Terrain:" + terrain);
		writeALine(file, "Climate:" + climate);
		writeALine(file, "XP:" + xp);
		writeALine(file, "Race:" + race);
		writeALine(file, "Number of classes with levels (First is favored):" + classes.length);
		for (Pair<String, Integer> p : classes) {
			writeALine(file, "Class:" + p.getX());
			writeALine(file, "Levels in above:" + p.getY());
		}
		writeALine(file, "Alignment:" + alignment);
		writeALine(file, "Size:" + size);
		writeALine(file, "Number of Subtypes:" + subtypes.length);
		for (String s : subtypes)
			writeALine(file, "Subtype:" + s);
		writeALine(file, "Init:" + init);
		writeALine(file, "Number of senses (#I makes entire thing italic for spells):" + senses.length);
		for (String s : senses)
			writeALine(file, "Sense:" + s);
		writeALine(file, "Perception:" + perception);
		writeALine(file, "Number of auras (#I makes entire thing italic for spells):" + auras.length);
		for (String s : auras)
			writeALine(file, "Aura:" + s);
		writeALine(file, "AC:" + ac);
		writeALine(file, "touch:" + touch);
		writeALine(file, "flat-footed:"  + flatFooted);
		writeALine(file, "Number of AC Modifiers:" + acModifiers.length);
		for (Pair<Integer, String> p : acModifiers) {
			writeALine(file, "Modifier:" + p.getX());
			writeALine(file, "Type of above Modifier:" + p.getY());
		}
		writeALine(file, "Number of Conditional AC Modifiers:" + conditionalACModifiers.length);
		for (Pair<Integer, String> p : conditionalACModifiers) {
			writeALine(file, "Modifier:" + p.getX());
			writeALine(file, "Type of above Modifier:" + p.getY());
		}
		writeALine(file, "HP:" + hp);
		writeALine(file, "HP breakdown:" + hpBreakdown);
		writeALine(file, "Fast Healing:" + fastHealing);
		if (regeneration != null) {
			writeALine(file, "Regeneration:" + regeneration.getX());
			writeALine(file, "Bypass:" + regeneration.getY());
		}
		else
			writeALine(file, "Regeneration:0");
		writeALine(file, "Fort:" + fort);
		writeALine(file, "Ref:" + ref);
		writeALine(file, "Will:" + will);
		writeALine(file, "Number of conditional save modifiers:" + saveModifiers.length);
		for (Pair<Integer, String> p : saveModifiers) {
			writeALine(file, "Modifier:" + p.getX());
			writeALine(file, "Condition/Type of above Modifier:" + p.getY());
		}
		writeALine(file, "Number of Defensive Abilities:" + defensiveAbilities.length);
		for (String s : defensiveAbilities)
			writeALine(file, "Defensive Ability:" + s);
		if (dr != null) {
			writeALine(file, "DR:" + dr.getX());
			writeALine(file, "Bypass:" + dr.getY());
		}
		else
			writeALine(file, "DR:0");
		writeALine(file, "Number of Immunities:" + immunities.length);
		for (String s : immunities)
			writeALine(file, "Immunity:" + s);
		writeALine(file, "Number of Resistances:" + resistances.length);
		for (Pair<String, Integer> p : resistances) {
			writeALine(file, "Resistance Type:" + p.getX());
			writeALine(file, "Resistance Amount:" + p.getY());
		}
		writeALine(file, "SR:" + sr);
		writeALine(file, "Number of Weaknesses:" + weaknesses.length);
		for (String s : weaknesses)
			writeALine(file, "Weakness:" + s);
		writeALine(file, "Speed:" + speed);
		writeALine(file, "Number of other speeds (#B means base speed):" + speeds.length);
		for (String s : speeds) 
			writeALine(file, "Other Speed:" + s);
		writeALine(file, "Melee:" + melee);
		writeALine(file, "Ranged:" + ranged);
		writeALine(file, "Space:" + space);
		writeALine(file, "Reach:" + reach);
		writeALine(file, "Number of Special Attacks:" + specialAttacks.length);
		for (String s : specialAttacks)
			writeALine(file, "Special Attack:" + s);
		//spellLikeAbilities <[use-spells], [cl, concentration]>
		if (spellLikeAbilities.getX().length > 0) {
			writeALine(file, "SLA Use Categories (1/day, etc.):" + spellLikeAbilities.getX().length);
			writeALine(file, "SLA CL:" + spellLikeAbilities.getY()[0]);
			writeALine(file, "SLA Concentration:" + spellLikeAbilities.getY()[1]);
			for (String s : spellLikeAbilities.getX())
				writeALine(file, "SLA Use Category and Spells:" + s);
		}
		else
			writeALine(file, "SLA Use Categories (1/day, etc.):0");
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		if (spellsKnown.getY().length > 0) {
			writeALine(file, "Spells Known Levels:" + spellsKnown.getY().length);
			writeALine(file, "Spells Known Class:" + spellsKnown.getZ()[0]);
			writeALine(file, "Spells Known CL:" + spellsKnown.getX()[0]);
			writeALine(file, "Spells Known Concentration:" + spellsKnown.getX()[1]);
			writeALine(file, "Number of Bolded Sections:" + (spellsKnown.getZ().length - 1));
			for (int i = 1; i < spellsKnown.getZ().length; i++)
				writeALine(file, "Bold Section:" + spellsKnown.getZ()[i]);
			for (String s : spellsKnown.getY())
				writeALine(file, "Spells Known and Use Level:" + s);
		}
		else
			writeALine(file, "Spells Known Levels:0");
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		if (spellsPrepared.getY().length > 0) {
			writeALine(file, "Spells Prepared Levels:" + spellsPrepared.getY().length);
			writeALine(file, "Spells Prepared Class:" + spellsPrepared.getZ()[0]);
			writeALine(file, "Spells Prepared CL:" + spellsPrepared.getX()[0]);
			writeALine(file, "Spells Prepared Concentration:" + spellsPrepared.getX()[1]);
			writeALine(file, "Number of Bolded Sections:" + (spellsPrepared.getZ().length - 1));
			for (int i = 1; i < spellsPrepared.getZ().length; i++)
				writeALine(file, "Bold Section:" + spellsPrepared.getZ()[i]);				
			for (String s : spellsPrepared.getY())
				writeALine(file, "Spells Prepared at Level:" + s);
			}
		else
			writeALine(file, "Spells Prepared Levels:0");
		writeALine(file, "Before Combat:" + beforeCombat);
		writeALine(file, "During Combat:" + duringCombat);
		writeALine(file, "Morale:" + morale);
		writeALine(file, "Base Statistics:" + baseStatistics);
		writeALine(file, "Str:" + str);
		writeALine(file, "Dex:" + dex);
		writeALine(file, "Con:" + con);
		writeALine(file, "Int:" + intelligence);
		writeALine(file, "Wis:" + wis);
		writeALine(file, "Cha:" + cha);
		writeALine(file, "BAB:" + bab);
		writeALine(file, "CMB:" + cmb);
		writeALine(file, "Number of Special CMBs:" + cmbs.length);
		for (String s : cmbs)
			writeALine(file, "Special CMB:" + s);
		writeALine(file, "CMD:" + cmd);
		writeALine(file, "Number of Special CMDs:" + cmds.length);
		for (String s : cmds)
			writeALine(file, "Special CMD:" + s);
		writeALine(file, "Number of Feats:" + feats.length);
		for (String s : feats)
			writeALine(file, "Feat:" + s);
		writeALine(file, "Number of Skills:" + skills.length);
		for (String s : skills)
			writeALine(file, "Skill:" + s);
		writeALine(file, "Number of Racial Modifiers:" + racialModifiers.length);
		for (String s : racialModifiers)
			writeALine(file, "Racial Modifier:" + s);
		writeALine(file, "Number of Languages:" + languages.length);
		for (String s : languages)
			writeALine(file, "Language:" + s);
		writeALine(file, "Number of Special Languages:" + languagesSpecial.length);
		for (String s : languagesSpecial)
			writeALine(file, "Special Language:" + s);
		writeALine(file, "Number of Special Qualities:" + sq.length);
		for (String s : sq)
			writeALine(file, "SQ:" + s);
		writeALine(file, "Number of Combat Gear:" + combatGear.length);
		for (String s : combatGear)
			writeALine(file, "Combat Gear:" + s);
		writeALine(file, "Number of Other Gear:" + otherGear.length);
		for (String s : otherGear)
			writeALine(file, "Other Gear:" + s);
		writeALine(file, "Environment:" + environment);
		writeALine(file, "Organization:" + organization);
		writeALine(file, "Treasure:" + treasure);
		writeALine(file, "Number of Special Abilities:" + specialAbilities.length);
		//OPT <Ability Name <Ability Description, Ability Sub-Description>>
		for (Pair<String, Pair<String, String[]>> p : specialAbilities) {
			writeALine(file, "Ability Name:" + p.getX());
			writeALine(file, "Ability Text:" + p.getY().getX());
			writeALine(file, "Number of Sub-Descriptions:" + p.getY().getY().length);
			for (String s : p.getY().getY())
				writeALine(file, "Sub-Description:" + s);
		}
		writeALine(file, "Description:" + description);
		file.close();
	}
	
	private void showWithEffects(PDPageContentStream contentStream, String line) throws IOException {
		int current = 0;
		while (current < line.length()) {
			int next = line.indexOf("#I", current);
			int effect = 0; //0 = Italics, 1 = Bold, 2 = SuperScript, 3 = break
			if (line.indexOf("#U", current) > -1)
				if (next == -1 || line.indexOf("#U", current) < next) {
					effect = 2;
					next = line.indexOf("#U", current);
				}
			if (line.indexOf("#H", current) > -1)
				if (next == -1 || line.indexOf("#H", current) < next) {
					effect = 1;
					next = line.indexOf("#H", current);
				}
			if (line.indexOf("#", current) > -1)
				if (next == -1 || line.indexOf("#", current) < next) {
					effect = 3;
					next = line.indexOf("#", current);
				}
			if (next == -1) {
				contentStream.showText(line.substring(current, line.length()));
				current = line.length();
				break;
			}
			contentStream.showText(line.substring(current, next));
			int end = line.indexOf(' ', next);
			if (line.indexOf('#', next + 1) != -1)
				if (end == -1 || line.indexOf('#', next + 1) < end)
					end = line.indexOf('#', next + 1);
			if (end == -1)
				end = line.length();
			switch (effect) {
			case 0:
				printItalic(contentStream, line.substring(next + 2, end));
				break;
			case 1:
				printBold(contentStream, line.substring(next + 2, end));
				break;
			case 2:
				printSuper(contentStream, line.substring(next + 2, end));
				break;
			case 3:
				contentStream.showText(line.substring(next + 1, end));
				break;
			}
			current = end;
		}
	}
	@SuppressWarnings("deprecation")
	private void printSuper(PDPageContentStream contentStream, String s) throws IOException {
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 6);
		contentStream.appendRawCommands("\n5 Ts\n");
		contentStream.showText(s);
		contentStream.appendRawCommands("\n0 Ts\n");
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
	}
	private void printItalic(PDPageContentStream contentStream, String s) throws IOException {
		contentStream.setFont(PDType1Font.TIMES_ITALIC, 11);
		contentStream.showText(s);
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
	}
	private void printBold(PDPageContentStream contentStream, String s) throws IOException {
		contentStream.setFont(PDType1Font.TIMES_BOLD, 11);
		contentStream.showText(s);
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
	}
	/**
	 * Returns a place from a number as String
	 *  1st from 1
	 *  2nd from 2
	 *  @param i
	 *  @return i as a place
	 */
	private String getPlace(int i) {
		String result = Integer.toString(i);
		if (result.endsWith("1") && Math.abs(i) != 11)
			result += "st";
		else if (result.endsWith("2") && Math.abs(i) != 12)
			result += "nd";
		else if (result.endsWith("3") && Math.abs(i) != 13)
			result += "rd";
		else
			result += "th";
		return result;
	}
	/**
	 * Supposed to handle moving line to right side
	 * -Numbers should be %, figure out with page turn
	 * @param contentStream
	 * @param lineNumber -after the header
	 * @throws IOException
	 */
	private int nextLine(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (lineNumber == 53 && shortDesc.length() > 62) 
			contentStream.newLineAtOffset(256, 583);
		else if (lineNumber == 55 && shortDesc.length() < 63) 
			contentStream.newLineAtOffset(256, 605);
		else if (lineNumber == 107 && shortDesc.length() > 62) {
			//TODO Handle page turn
			contentStream.newLine();
		}
		else if (lineNumber == 111 && shortDesc.length() < 63) {
			//TODO Handle page turn
			contentStream.newLine();
		}
		else
			contentStream.newLine();
		return ++lineNumber;
	}
	/**
	 * Reads a single line from a file after a ':' char
	 * 	// starts a comment, will return left of if present
	 * @param read - what to read from
	 * @return null if unable to read
	 */
	private String readALine(BufferedReader read) {
		try {
			String line = read.readLine();
			while (line.contains("//")) {
				if (line.substring(0, line.indexOf("//")).trim().equals(""))
					line = read.readLine();
				else
					line = line.substring(0, line.indexOf("//"));
			}
			return line.substring(line.indexOf(58) + 1).trim();
		} catch (IOException e) {
			System.out.println("Unable to read Line, returning null");
			return null;
		} catch (NullPointerException e) {
			System.out.println("Reached EOF, returning null");
			return null;
		}
	}
	
	public void printToPdf() {
		try (PDDocument pdoc = new PDDocument();) {
			PDPage page = new PDPage();
			PDPageContentStream contentStream = new PDPageContentStream(pdoc, page);
			contentStream.beginText();
			writeHeader(contentStream);
			int lineNumber = writeTop(contentStream);
			lineNumber = writeDefense(contentStream, lineNumber);
			lineNumber = writeOffense(contentStream, lineNumber);
			lineNumber = writeTactics(contentStream, lineNumber);
			lineNumber = writeStatistics(contentStream, lineNumber);
			lineNumber = writeEcology(contentStream, lineNumber);
			lineNumber = writeSpecialAbilities(contentStream, lineNumber);
			lineNumber = writeDescription(contentStream, lineNumber);
			contentStream.endText();
			drawPictures(contentStream, pdoc);
			contentStream.close();
			pdoc.addPage(page);
			if (!Files.exists(Paths.get(path)))
				new File(path).mkdirs();
			pdoc.save(path + fileName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private int writeDescription(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!description.equals("")) {
			lineNumber = nextLine(contentStream, lineNumber);
			int limit = 54;
			if (description.length() > limit) {
				int index = description.lastIndexOf(' ', limit);
				String tempLine = description.substring(0, index);
				if (tempLine.contains("#P")) {
					tempLine = tempLine.substring(0, tempLine.indexOf("#P"));
				}
				showWithEffects(contentStream, tempLine);
				while (index != description.length()) {
					lineNumber = nextLine(contentStream, lineNumber);
					int next = description.lastIndexOf(' ', index + limit);
					if (index + limit > description.length() || index == -1)
						next = description.length();
					tempLine = description.substring(index, next).trim();
					while (tempLine.contains("#P")) {
						tempLine = tempLine.substring(0, tempLine.indexOf("#P"));
						showWithEffects(contentStream, tempLine);
						if (!tempLine.equals(""))
							lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("   ");
						next = description.lastIndexOf(' ', index + limit + tempLine.length() - 2);
						tempLine = description.substring(description.indexOf("#P", index) + 2, next).trim();
					}
					showWithEffects(contentStream, tempLine);
					index = next;
				}
			}
			else {
				showWithEffects(contentStream, description);
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;	
	}
	private int writeSpecialAbilities(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (specialAbilities.length > 0) {
			printBold(contentStream, "SPECIAL ABILITIES----------------------------------------");
			lineNumber = nextLine(contentStream, lineNumber);
			for (int i = 0; i < specialAbilities.length; i++) {
				printBold(contentStream, specialAbilities[i].getX() + ' ');
				int limit = 51;
				int printed = specialAbilities[i].getX().length() - 1;
				if (specialAbilities[i].getX().contains("EX") || specialAbilities[i].getX().contains("SU") || specialAbilities[i].getX().contains("SP"))
					printed += 3;
				String line = specialAbilities[i].getY().getX();
				if (printed + line.length() > limit) {
					int index = line.lastIndexOf(' ', limit - printed);
					String tempLine = line.substring(0, index);
					if (tempLine.contains("#")) {
						index = line.lastIndexOf(' ', limit  - printed + 2);
						tempLine = line.substring(0, index);
					}
					showWithEffects(contentStream, tempLine);
					while (index != line.length()) {
						lineNumber = nextLine(contentStream, lineNumber);
						int next = line.lastIndexOf(' ', index + limit);
						if (index + limit > line.length() || index == -1)
							next = line.length();
						contentStream.showText("    ");
						tempLine = line.substring(index, next).trim();
						if (tempLine.contains("#")) {
							next = line.lastIndexOf(' ', index + limit + 2);
							if (index + limit + 2 > line.length() || index == -1)
								next = line.length();
							tempLine = line.substring(index, next).trim();
						}
						showWithEffects(contentStream, tempLine);
						index = next;
					}
				}
				else {
					showWithEffects(contentStream, line);
				}
				lineNumber = nextLine(contentStream, lineNumber);
				for (int j = 0; j < specialAbilities[i].getY().getY().length; j++) {
					contentStream.showText("    ");
					line = specialAbilities[i].getY().getY()[j];
					int index = line.lastIndexOf(' ', limit);
					String tempLine = line.substring(0, index);
					if (tempLine.contains("#")) {
						index = line.lastIndexOf(' ', limit + 7);
						tempLine = line.substring(0, index);
					}
					showWithEffects(contentStream, tempLine);
					boolean messedWithLimit = false;
					if (index != line.length()) {
						limit -= 2;
						messedWithLimit = true;
					}
					while (index != line.length()) {
						lineNumber = nextLine(contentStream, lineNumber);
						int next = line.lastIndexOf(' ', index + limit);
						if (index + limit > line.length() || index == -1)
							next = line.length();
						contentStream.showText("      "); //Extra 2 spaces
						tempLine = line.substring(index, next).trim();
						if (tempLine.contains("#")) {
							next = line.lastIndexOf(' ', index + limit + 7);
							if (index + limit + 7 > line.length() || index == -1)
								next = line.length();
							tempLine = line.substring(index, next).trim();
						}
						showWithEffects(contentStream, tempLine);
						index = next;
					}
					if (messedWithLimit)
						limit += 2;
					lineNumber = nextLine(contentStream, lineNumber);
				}
			}
		}
		return lineNumber;
	}
	private int writeEcology(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (environment.equals("") && organization.equals("") && treasure.equals(""))
			return lineNumber;
		printBold(contentStream, "ECOLOGY------------------------------------------------------");
		lineNumber = nextLine(contentStream, lineNumber);
		lineNumber = writeEnvironment(contentStream, lineNumber);
		lineNumber = writeOrganization(contentStream, lineNumber);
		lineNumber = writeTreasure(contentStream, lineNumber);
		return lineNumber;
	}
	private int writeTreasure(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!treasure.equals("")) {
			printBold(contentStream, "Treasure ");
			int limit = 55;
			if (10 + treasure.length() > limit) {
				int index = treasure.lastIndexOf(' ', limit - 10);
				String tempLine = treasure.substring(0, index);
				if (tempLine.contains("#")) {
					index = treasure.lastIndexOf(' ', limit  - 1);
					tempLine = treasure.substring(0, index);
				}
				showWithEffects(contentStream, tempLine);
				while (index != treasure.length()) {
					lineNumber = nextLine(contentStream, lineNumber);
					int next = treasure.lastIndexOf(' ', index + limit);
					if (index + limit > treasure.length() || index == -1)
						next = treasure.length();
					contentStream.showText("    ");
					tempLine = treasure.substring(index, next).trim();
					if (tempLine.contains("#")) {
						next = treasure.lastIndexOf(' ', index + limit + 9);
						if (index + limit + 9 > treasure.length() || index == -1)
							next = treasure.length();
						tempLine = treasure.substring(index, next).trim();
					}
					showWithEffects(contentStream, tempLine);
					index = next;
				}
			}
			else {
				showWithEffects(contentStream, treasure);
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;	
	}
	private int writeOrganization(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!organization.equals("")) {
			printBold(contentStream, "Organization ");
			if (15 + organization.length() > 55) {
				int index = organization.lastIndexOf(' ', 40);
				String tempLine = organization.substring(0, index);
				showWithEffects(contentStream, tempLine);
				while (index != organization.length()) {
					lineNumber = nextLine(contentStream, lineNumber);
					int next = organization.lastIndexOf(' ', index + 55);
					if (index + 55 > organization.length() || index == -1)
						next = organization.length();
					contentStream.showText("    ");
					tempLine = organization.substring(index, next).trim();
					showWithEffects(contentStream, tempLine);
					index = next;
				}
			}
			else {
				showWithEffects(contentStream, organization);
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;	
	}
	private int writeEnvironment(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!environment.equals("")) {
			printBold(contentStream, "Environment ");
			if (15 + environment.length() > 55) {
				int index = environment.lastIndexOf(' ', 40);
				String tempLine = environment.substring(0, index);
				showWithEffects(contentStream, tempLine);
				while (index != environment.length()) {
					lineNumber = nextLine(contentStream, lineNumber);
					int next = environment.lastIndexOf(' ', index + 55);
					if (index + 55 > environment.length() || index == -1)
						next = environment.length();
					contentStream.showText("    ");
					tempLine = environment.substring(index, next).trim();
					showWithEffects(contentStream, tempLine);
					index = next;
				}
			}
			else {
				showWithEffects(contentStream, environment);
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeStatistics(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "STATISTICS----------------------------------------------------");
		lineNumber = nextLine(contentStream, lineNumber);
		lineNumber = writeAbilityScores(contentStream, lineNumber);
		lineNumber = writeBABCMBD(contentStream, lineNumber);
		lineNumber = writeFeats(contentStream, lineNumber);
		lineNumber = writeSkills(contentStream, lineNumber);
		lineNumber = writeLanguages(contentStream, lineNumber);
		lineNumber = writeSQs(contentStream, lineNumber);
		lineNumber = writeGear(contentStream, lineNumber);
		return lineNumber;
	}
	private int writeGear(PDPageContentStream contentStream, int lineNumber) throws IOException {
		int printed = 0;
		if (combatGear.length > 0) {
			printBold(contentStream, "Combat Gear ");
			printed += 14;
			for (int i = 0; i < combatGear.length; i++) {
				String line = combatGear[i];
				
				
				///////TODO This part might(definitely) still need some work
				if (!line.startsWith("#")) 
					printed += 2;
				int temp = 0;
				while (temp != -1) {
					if (line.substring(temp).contains(" ")) {
						temp = line.indexOf(" ", temp) + 1;
						if (line.charAt(temp) != '#')
							printed += 2;
					}
					else
						temp = -1;
				}
				if (printed + line.length() > 65) {		
					int index = line.lastIndexOf(' ', 65 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						if (line.length() < 65)
							index = line.length();
						else
							index = line.lastIndexOf(' ', 65);
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						printed = tempLine.length();
						while (index != line.length()) {
							lineNumber = nextLine(contentStream, lineNumber);
							int next = line.lastIndexOf(' ', index + 65);
							if (index + 65 > line.length() || index == -1)
								next = line.length();
							contentStream.showText("    ");
							tempLine = line.substring(index, next).trim();
							showWithEffects(contentStream, tempLine);
							printed = tempLine.length();
							index = next;
						}
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						while (index != line.length()) {
							lineNumber = nextLine(contentStream, lineNumber);
							int next = line.lastIndexOf(' ', index + 65);
							if (index + 65 > line.length() || index == -1)
								next = line.length();
							contentStream.showText("    ");
							tempLine = line.substring(index, next).trim();
							showWithEffects(contentStream, tempLine);
							printed = tempLine.length();
							index = next;
						}
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < combatGear.length) {
					contentStream.showText(", ");
					printed += 2;
				}
			}
		}
		if (otherGear.length > 0) {
			if (combatGear.length > 0) {
				contentStream.showText("; ");
				printed += 2;
			}
			if (printed + 6 > 65) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			printBold(contentStream, "Other ");
			printed += 7;
			if (printed + 5 > 65) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			printBold(contentStream, "Gear ");
			printed += 7;
			for (int i = 0; i < otherGear.length; i++) {
				String line = otherGear[i];
				if (!line.startsWith("#")) 
					printed += 2;
				int temp = 0;
				while (temp != -1) {
					if (line.substring(temp).contains(" ")) {
						temp = line.indexOf(" ", temp) + 1;
						if (line.charAt(temp) != '#')
							printed += 2;
					}
					else
						temp = -1;
				}
				if (printed + line.length() > 65) {		
					int index = line.lastIndexOf(' ', 65 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						if (line.length() < 65)
							index = line.length();
						else
							index = line.lastIndexOf(' ', 65);
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						printed = tempLine.length();
						while (index != line.length()) {
							lineNumber = nextLine(contentStream, lineNumber);
							int next = line.lastIndexOf(' ', index + 65);
							if (index + 65 > line.length() || index == -1)
								next = line.length();
							contentStream.showText("    ");
							tempLine = line.substring(index, next).trim();
							showWithEffects(contentStream, tempLine);
							printed = tempLine.length();
							index = next;
						}
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						while (index != line.length()) {
							lineNumber = nextLine(contentStream, lineNumber);
							int next = line.lastIndexOf(' ', index + 65);
							if (index + 65 > line.length() || index == -1)
								next = line.length();
							contentStream.showText("    ");
							tempLine = line.substring(index, next).trim();
							showWithEffects(contentStream, tempLine);
							printed = tempLine.length();
							index = next;
						}
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < otherGear.length) {
					contentStream.showText(", ");
					printed += 2;
				}
			}
		}
		if (printed != 0)
			lineNumber = nextLine(contentStream, lineNumber);
		return lineNumber;
	}
	private int writeSQs(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (sq.length > 0) {
			printBold(contentStream, "SQ ");
			int printed = 0;
			for (int i = 0; i < sq.length; i++) {
				String line = sq[i];
				if (printed + line.length() > 53) {
					int index = line.lastIndexOf(' ', 53 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						showWithEffects(contentStream, line);
						printed = line.length();
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						tempLine = line.substring(index, line.length()).trim();
						showWithEffects(contentStream, tempLine);
						printed = tempLine.length();
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < sq.length) {
					contentStream.showText(", ");
					printed += 2;
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeLanguages(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (languages.length > 0) {
			printBold(contentStream, "Languages ");
			int printed = 13;
			for (int i = 0; i < languages.length; i++) {
				String line = languages[i];
				if (printed + line.length() > 55) {
					int index = line.lastIndexOf(' ', 55 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						showWithEffects(contentStream, line);
						printed = line.length();
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						tempLine = line.substring(index, line.length()).trim();
						showWithEffects(contentStream, tempLine);
						printed = tempLine.length();
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < languages.length) {
					contentStream.showText(", ");
					printed += 2;
				}
			}
			if (languagesSpecial.length > 0) {
				contentStream.showText("; ");
				printed += 2;
				for (int i = 0; i < languagesSpecial.length; i++) {
					String line = languagesSpecial[i];
					if (printed + line.length() > 55) {
						int index = line.lastIndexOf(' ', 55 - printed);
						if (index == -1) {
							lineNumber = nextLine(contentStream, lineNumber);
							contentStream.showText("    ");
							showWithEffects(contentStream, line);
							printed = line.length();
						}
						else {
							String tempLine = line.substring(0, index);
							showWithEffects(contentStream, tempLine);
							lineNumber = nextLine(contentStream, lineNumber);
							contentStream.showText("    ");
							tempLine = line.substring(index, line.length()).trim();
							showWithEffects(contentStream, tempLine);
							printed = tempLine.length();
						}
					}
					else {
						showWithEffects(contentStream, line);
						printed += line.length();
					}
					if (i + 1 < languagesSpecial.length) {
						contentStream.showText(", ");
						printed += 2;
					}
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeSkills(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (skills.length > 0) {
			printBold(contentStream, "Skills ");
			int printed = 3;
			for (int i = 0; i < skills.length; i++) {
				String line = skills[i];
				if (printed + line.length() > 50) {
					int index = line.lastIndexOf(' ', 50 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						showWithEffects(contentStream, line);
						printed = line.length();
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						tempLine = line.substring(index, line.length()).trim();
						showWithEffects(contentStream, tempLine);
						printed = tempLine.length();
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < skills.length) {
					contentStream.showText(", ");
					printed += 2;
				}
			}
			if (racialModifiers.length > 0) {
				contentStream.showText("; ");
				printed += 2;
				if (printed + 7 > 50) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				printed += 7;
				printBold(contentStream, "Racial ");
				if (printed + 9 > 50) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				printed += 9;
				printBold(contentStream, "Modifiers ");
				for (int i = 0; i < racialModifiers.length; i++) {
					String line = racialModifiers[i];
					if (printed + line.length() > 50) {
						int index = line.lastIndexOf(' ', 50 - printed);
						if (index == -1) {
							lineNumber = nextLine(contentStream, lineNumber);
							contentStream.showText("    ");
							showWithEffects(contentStream, line);
							printed = line.length();
						}
						else {
							String tempLine = line.substring(0, index);
							showWithEffects(contentStream, tempLine);
							lineNumber = nextLine(contentStream, lineNumber);
							contentStream.showText("    ");
							tempLine = line.substring(index, line.length()).trim();
							showWithEffects(contentStream, tempLine);
							printed = tempLine.length();
						}
					}
					else {
						showWithEffects(contentStream, line);
						printed += line.length();
					}
					if (i + 1 < racialModifiers.length) {
						contentStream.showText(", ");
						printed += 2;
					}
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeFeats(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (feats.length > 0) {
			printBold(contentStream, "Feats ");
			int printed = 1;
			for (int i = 0; i < feats.length; i++) {
				String line = feats[i];
				if (printed + line.length() > 48) {
					int index = line.lastIndexOf(' ', 48 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						showWithEffects(contentStream, line);
						printed = line.length();
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						tempLine = line.substring(index, line.length()).trim();
						showWithEffects(contentStream, tempLine);
						printed = tempLine.length();
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < feats.length) {
					contentStream.showText(", ");
					printed += 2;
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeBABCMBD(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "Base Atk ");
		contentStream.showText("+" + bab + "; ");
		printBold(contentStream, "CMB ");
		if (cmb == EMDASH)
			contentStream.showText("—");
		else
			contentStream.showText((cmb > -1 ? "+" : "") + cmb);
		int printed = 25;
		if (cmbs.length > 0) {
			contentStream.showText(" (");
			printed += 2;
			for (int i = 0; i < cmbs.length; i++) {
				String line = cmbs[i];
				if (printed + line.length() > 56) {
					int index = line.lastIndexOf(' ', 56 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						showWithEffects(contentStream, line);
						printed = line.length();
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						tempLine = line.substring(index, line.length()).trim();
						showWithEffects(contentStream, tempLine);
						printed = tempLine.length();
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < cmbs.length) {
					printed += 2;
					contentStream.showText(", ");
				}
			}
			contentStream.showText(")");
			printed++;
		}
		contentStream.showText("; ");
		printed += 2;
		if (printed + 9 > 56) {
			lineNumber = nextLine(contentStream, lineNumber);
			contentStream.showText("    ");
			printed = 0;
		}
		printBold(contentStream, "CMD ");
		contentStream.showText(cmd == EMDASH ? "—" : Integer.toString(cmd));
		printed += 9;
		if (cmds.length > 0) {
			contentStream.showText(" (");
			printed += 2;
			for (int i = 0; i < cmds.length; i++) {
				String line = cmds[i];
				if (printed + line.length() > 56) {
					int index = line.lastIndexOf(' ', 56 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						showWithEffects(contentStream, line);
						printed = line.length();
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						tempLine = line.substring(index, line.length()).trim();
						showWithEffects(contentStream, tempLine);
						printed = tempLine.length();
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < cmds.length) {
					printed += 2;
					contentStream.showText(", ");
				}
			}
			contentStream.showText(")");
			printed++;
		}
		return nextLine(contentStream, lineNumber);
	}
	private int writeAbilityScores(PDPageContentStream contentStream, int lineNumber) throws IOException {
		String line = "#HStr " + (str == EMDASH ? "—" : str);
		line += ", #HDex " + (dex == EMDASH ? "—" : dex);
		line += ", #HCon " + (con == EMDASH ? "—" : con); 
		line += ", #HInt " + (intelligence == EMDASH ? "—" : intelligence);
		line += ", #HWis " + (wis == EMDASH ? "—" : wis);
		line += ", #HCha " + (cha == EMDASH ? "—" : cha); 
		showWithEffects(contentStream, line);
		return nextLine(contentStream, lineNumber);
	}
	private int writeTactics(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (beforeCombat.equals("") && duringCombat.equals("") && morale.equals("") && baseStatistics.equals(""))
			return lineNumber;
		printBold(contentStream, "TACTICS--------------------------------------------------------");
		lineNumber = nextLine(contentStream, lineNumber);
		lineNumber = writeBeforeCombat(contentStream, lineNumber);
		lineNumber = writeDuringCombat(contentStream, lineNumber);
		lineNumber = writeMorale(contentStream, lineNumber);
		lineNumber = writeBaseStatistics(contentStream, lineNumber);
		return lineNumber;
	}
	private int writeBaseStatistics(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!baseStatistics.equals("")) {
			int printed = 15;
			printBold(contentStream, "Base Statistics ");
			if (printed + baseStatistics.length() < 56)
				showWithEffects(contentStream, baseStatistics);
			else {
				int index = baseStatistics.lastIndexOf(' ', 41);
				String line = baseStatistics.substring(0, index);
				showWithEffects(contentStream, line);
				while (index != baseStatistics.length()) {
					lineNumber = nextLine(contentStream, lineNumber);
					int next = baseStatistics.lastIndexOf(' ', index + 56);
					if (index + 55 > baseStatistics.length() || index == -1)
						next = baseStatistics.length();
					contentStream.showText("    ");
					line = baseStatistics.substring(index, next);
					showWithEffects(contentStream, line);
					index = next;
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeMorale(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!morale.equals("")) {
			int printed = 8;
			printBold(contentStream, "Morale ");
			if (printed + morale.length() < 56)
				showWithEffects(contentStream, morale);
			else {
				int index = morale.lastIndexOf(' ', 48);
				String line = morale.substring(0, index);
				showWithEffects(contentStream, line);
				while (index != morale.length()) {
					lineNumber = nextLine(contentStream, lineNumber);
					int next = morale.lastIndexOf(' ', index + 56);
					if (index + 55 > morale.length() || index == -1)
						next = morale.length();
					contentStream.showText("    ");
					line = morale.substring(index, next);
					showWithEffects(contentStream, line);
					index = next;
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeDuringCombat(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!duringCombat.equals("")) {
			int printed = 17;
			printBold(contentStream, "During Combat ");
			if (printed + duringCombat.length() < 56)
				showWithEffects(contentStream, duringCombat);
			else {
				int index = duringCombat.lastIndexOf(' ', 39);
				String line = duringCombat.substring(0, index);
				showWithEffects(contentStream, line);
				while (index != duringCombat.length()) {
					lineNumber = nextLine(contentStream, lineNumber);
					int next = duringCombat.lastIndexOf(' ', index + 56);
					if (index + 55 > duringCombat.length() || index == -1)
						next = duringCombat.length();
					contentStream.showText("    ");
					line = duringCombat.substring(index, next);
					showWithEffects(contentStream, line);
					index = next;
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeBeforeCombat(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!beforeCombat.equals("")) {
			int printed = 16;
			printBold(contentStream, "Before Combat ");
			if (printed + beforeCombat.length() < 56)
				showWithEffects(contentStream, beforeCombat);
			else {
				int index = beforeCombat.lastIndexOf(' ', 40);
				String line = beforeCombat.substring(0, index);
				showWithEffects(contentStream, line);
				while (index != beforeCombat.length()) {
					lineNumber = nextLine(contentStream, lineNumber);
					int next = beforeCombat.lastIndexOf(' ', index + 56);
					if (index + 55 > beforeCombat.length() || index == -1)
						next = beforeCombat.length();
					contentStream.showText("    ");
					line = beforeCombat.substring(index, next);
					showWithEffects(contentStream, line);
					index = next;
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeOffense(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "OFFENSE--------------------------------------------------------");
		lineNumber = nextLine(contentStream, lineNumber);
		lineNumber = writeSpeeds(contentStream, lineNumber);
		lineNumber = writeMelee(contentStream, lineNumber);
		lineNumber = writeRanged(contentStream, lineNumber);
		lineNumber = writeSpaceReach(contentStream, lineNumber);
		lineNumber = writeSpecialAttacks(contentStream, lineNumber);
		lineNumber = writeSLAs(contentStream, lineNumber);
		lineNumber = writeSpellsKnown(contentStream, lineNumber);
		lineNumber = writeSpellsPrepared(contentStream, lineNumber);
		return lineNumber;
	}
	private int writeSpellsPrepared(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (spellsPrepared.getY().length > 0) {//OPT <[cl, concentration], [use-spells], [className, Bold, after, Bold ...]>
			if (!spellsPrepared.getZ()[0].equals("none")) {
				printBold(contentStream, spellsPrepared.getZ()[0]);
				printBold(contentStream, " Spells Prepared ");
			}
			else
				printBold(contentStream, "Spells Prepared ");
			int conc = spellsPrepared.getX()[1];
			contentStream.showText("(CL " + getPlace(spellsPrepared.getX()[0]) + "; concentration " + ((conc > -1) ? ("+" + conc) : (conc))  + ')');
			lineNumber = nextLine(contentStream, lineNumber);
			for (int i = 0; i < spellsPrepared.getY().length; i++) {
				contentStream.showText("    ");
				String slaLine = spellsPrepared.getY()[i];
				if (slaLine.length() < 59) 
					showWithEffects(contentStream, slaLine);
				else { 
					int index = slaLine.lastIndexOf(' ', 59);
					String line = slaLine.substring(0, index);
					showWithEffects(contentStream, line);
					while (index != slaLine.length()) {
						lineNumber = nextLine(contentStream, lineNumber);
						int next = slaLine.lastIndexOf(' ', index + 59);
						if (index + 59 > slaLine.length() || index == -1)
							next = slaLine.length();
						contentStream.showText("      "); //Extra 2 spaces
						line = slaLine.substring(index, next).trim();
						showWithEffects(contentStream, line);
						index = next;
					}
				}
				lineNumber = nextLine(contentStream, lineNumber);
			}
			contentStream.showText("    ");
			int printed = 0;
			for (int i = 1; i < spellsPrepared.getZ().length; i++) {
				String bold = spellsPrepared.getZ()[i];
				if (printed + bold.length() > 55) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("      ");
					printed = 2;
				}
				printed += bold.length();
				showWithEffects(contentStream, bold);
				if (i + 1 < spellsPrepared.getZ().length) {
					contentStream.showText("; ");
					printed += 2;
				}	
			}
			if (printed != 0)
				lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeSpellsKnown(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (spellsKnown.getY().length > 0) {
			if (!spellsKnown.getZ()[0].equals("none")) {
				printBold(contentStream, spellsKnown.getZ()[0]);
				printBold(contentStream, " Spells Known ");
			}
			else
				printBold(contentStream, "Spells Known ");
			int conc = spellsKnown.getX()[1];
			contentStream.showText("(CL " + getPlace(spellsKnown.getX()[0]) + "; concentration " + ((conc > -1) ? ("+" + conc) : (conc))  + ')');
			lineNumber = nextLine(contentStream, lineNumber);
			for (int i = 0; i < spellsKnown.getY().length; i++) {
				contentStream.showText("    ");
				String slaLine = spellsKnown.getY()[i];
				if (slaLine.length() < 59) 
					showWithEffects(contentStream, slaLine);
				else { 
					int index = slaLine.lastIndexOf(' ', 59);
					String line = slaLine.substring(0, index);
					showWithEffects(contentStream, line);
					while (index != slaLine.length()) {
						lineNumber = nextLine(contentStream, lineNumber);
						int next = slaLine.lastIndexOf(' ', index + 59);
						if (index + 59 > slaLine.length() || index == -1)
							next = slaLine.length();
						contentStream.showText("      "); //Extra 2 spaces
						line = slaLine.substring(index, next).trim();
						showWithEffects(contentStream, line);
						index = next;
					}
				}
				lineNumber = nextLine(contentStream, lineNumber);
			}
			contentStream.showText("    ");
			int printed = 0;
			for (int i = 1; i < spellsKnown.getZ().length; i++) {
				String bold = spellsKnown.getZ()[i];
				if (printed + bold.length() > 55) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("      ");
					printed = 2;
				}
				printed += bold.length();
				showWithEffects(contentStream, bold);
				if (i + 1 < spellsKnown.getZ().length) {
					contentStream.showText("; ");
					printed += 2;
				}	
			}
			if (printed != 0)
				lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeSLAs(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (spellLikeAbilities.getX().length > 0) {
			printBold(contentStream, "Spell-Like Abilities ");
			int conc = spellLikeAbilities.getY()[1];
			contentStream.showText("(CL " + getPlace(spellLikeAbilities.getY()[0]) + "; concentration " + ((conc > -1) ? ("+" + conc) : (conc))  + ')');
			lineNumber = nextLine(contentStream, lineNumber);
			for (int i = 0; i < spellLikeAbilities.getX().length; i++) {
				contentStream.showText("    ");
				String slaLine = spellLikeAbilities.getX()[i];
				if (slaLine.length() < 59) 
					showWithEffects(contentStream, slaLine);
				else { 
					int index = slaLine.lastIndexOf(' ', 59);
					String line = slaLine.substring(0, index);
					showWithEffects(contentStream, line);
					while (index != slaLine.length()) {
						lineNumber = nextLine(contentStream, lineNumber);
						int next = slaLine.lastIndexOf(' ', index + 59);
						if (index + 59 > slaLine.length() || index == -1)
							next = slaLine.length();
						contentStream.showText("      "); //Extra 2 spaces
						line = slaLine.substring(index, next).trim();
						showWithEffects(contentStream, line);
						index = next;
					}
				}
				lineNumber = nextLine(contentStream, lineNumber);
			}
		}
		return lineNumber;
	}
	private int writeSpecialAttacks(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (specialAttacks.length > 0) {
			printBold(contentStream, "Special Attacks ");
			int printed = 14;
			for (int i = 0; i < specialAttacks.length; i++) {
				String line = specialAttacks[i];
				if (printed + line.length() > 53) {
					int index = line.lastIndexOf(' ', 53 - printed);
					if (index == -1) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						showWithEffects(contentStream, line);
						printed = line.length();
					}
					else {
						String tempLine = line.substring(0, index);
						showWithEffects(contentStream, tempLine);
						while (index != line.length()) {
							lineNumber = nextLine(contentStream, lineNumber);
							int next = line.lastIndexOf(' ', index + 53);
							if (index + 53 > line.length() || index == -1)
								next = line.length();
							contentStream.showText("    ");
							tempLine = line.substring(index, next).trim();
							showWithEffects(contentStream, tempLine);
							printed = tempLine.length();
							index = next;
						}
					}
				}
				else {
					showWithEffects(contentStream, line);
					printed += line.length();
				}
				if (i + 1 < specialAttacks.length) {
					contentStream.showText("; ");
					printed += 2;
				}
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeSpaceReach(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (space.equals("5") && reach == 5)
			return lineNumber;
		printBold(contentStream, "Space ");
		contentStream.showText(space + " ft.; ");
		printBold(contentStream, "Reach ");
		contentStream.showText(reach + " ft.");
		return nextLine(contentStream, lineNumber);
	}
	private int writeRanged(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (ranged.equals(""))
			return lineNumber;
		printBold(contentStream, "Ranged ");
		if (ranged.length() < 47)
			showWithEffects(contentStream, ranged);
		else {
			int index = ranged.lastIndexOf(' ', 47);
			String line = ranged.substring(0, index);
			showWithEffects(contentStream, line);
			while (index != ranged.length()) {
				lineNumber = nextLine(contentStream, lineNumber);
				int next = ranged.lastIndexOf(' ', index + 53);
				if (index + 53 > ranged.length() || index == -1)
					next = ranged.length();
				contentStream.showText("    ");
				showWithEffects(contentStream, ranged.substring(index, next));
				index = next;
			}
		}
		return nextLine(contentStream, lineNumber);
	}
	private int writeMelee(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (melee.equals(""))
			return lineNumber;
		printBold(contentStream, "Melee ");
		if (melee.length() < 48)
			showWithEffects(contentStream, melee);
		else {
			int index = melee.lastIndexOf(' ', 48);
			String line = melee.substring(0, index);
			showWithEffects(contentStream, line);
			while (index != melee.length()) {
				lineNumber = nextLine(contentStream, lineNumber);
				int next = melee.lastIndexOf(' ', index + 53);
				if (index + 53 > melee.length() || index == -1)
					next = melee.length();
				contentStream.showText("    ");
				showWithEffects(contentStream, melee.substring(index, next));
				index = next;
			}
		}
		return nextLine(contentStream, lineNumber);
	}
	private int writeSpeeds(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "Speed ");
		contentStream.showText(speed + " ft.");
		int printed = 14;
		if (speeds.length > 0) {
			
			for (int i = 0; i < speeds.length; i++) {
				String p = speeds[i];
				if (p.startsWith("#B"))
					p = '(' + p.substring(2) + " base)";
				else if (i < speeds.length - 1 || speeds.length == 1) {
					printed += 1;
					contentStream.showText(",");
				}
				printed += 1;
				contentStream.showText(" ");
				if (printed + p.length() > 58) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				printed += (p.length());
				contentStream.showText(p);
				
			}
		}
		return nextLine(contentStream, lineNumber);
	}
	private int writeDefense(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "DEFENSE--------------------------------------------------------");
		lineNumber = nextLine(contentStream, lineNumber);
		lineNumber = writeAC(contentStream, lineNumber);
		lineNumber = writeHP(contentStream, lineNumber);
		lineNumber = writeSaves(contentStream, lineNumber);
		lineNumber = writeDefensiveAbilities(contentStream, lineNumber);
		return lineNumber;
	}
	private int writeDefensiveAbilities(PDPageContentStream contentStream, int lineNumber) throws IOException {
		int printed = 0;
		boolean hasPrinted = false;
		if (defensiveAbilities.length > 0) {
			printBold(contentStream, "Defensive Abilities ");
			printed += 23;
			for (int i = 0; i < defensiveAbilities.length; i++) {
				String p = defensiveAbilities[i];
				if (printed + p.length() > 59) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				printed += (p.length());
				contentStream.showText(p);
				if (i < defensiveAbilities.length - 1) {
					printed += 2;
					contentStream.showText(", ");
				}
			}
			hasPrinted = true;
		}
		if (dr != null) {
			if (hasPrinted) {
				contentStream.showText("; ");
				printed += 2;
			}
			if (printed + 4 > 59) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			printBold(contentStream, "DR ");
			printed += 4;
			if (printed + 3 + dr.getY().length() > 59) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			printed += (3 + dr.getY().length());
			contentStream.showText(dr.getX() + "/" + dr.getY());
			hasPrinted = true;
		}
		if (immunities.length > 0) {
			if (hasPrinted) {
				contentStream.showText("; ");
				printed += 2;
			}
			if (printed + 9 > 59) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			printBold(contentStream, "Immune ");
			printed += 9;
			for (int i = 0; i < immunities.length; i++) {
				String p = immunities[i];
				if (printed + p.length() > 59) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				printed += (p.length());
				contentStream.showText(p);
				if (i < immunities.length - 1) {
					printed += 2;
					contentStream.showText(", ");
				}
			}
			hasPrinted = true;
		}
		if (resistances.length > 0) {
			if (hasPrinted) {
				contentStream.showText("; ");
				printed += 2;
			}
			printBold(contentStream, "Resist ");
			printed += 9;
			for (int i = 0; i < resistances.length; i++) {
				Pair<String, Integer> p = resistances[i];
				if (printed + 3 + p.getX().length() > 59) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				printed += 3 + p.getX().length();
				contentStream.showText(p.getX() + " " + p.getY());
				if (i < resistances.length - 1) {
					printed += 2;
					contentStream.showText(", ");
				}
			}
			hasPrinted = true;
		}
		if (sr > 0) {
			if (hasPrinted) {
				contentStream.showText("; ");
				printed += 2;
			}
			if (printed + 6 > 59) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			printBold(contentStream, "SR ");
			contentStream.showText(Integer.toString(sr));
			printed += 6;
			hasPrinted = true;
		}
		if (weaknesses.length > 0) {
			if (hasPrinted) {
				contentStream.showText("; ");
				printed += 2;
			}
			if (printed + 13 > 59) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			printBold(contentStream, "Weaknesses ");
			printed += 13;
			for (int i = 0; i < weaknesses.length; i++) {
				String p = weaknesses[i];
				if (printed + p.length() > 59) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				printed += (p.length());
				contentStream.showText(p);
				if (i < weaknesses.length - 1) {
					printed += 2;
					contentStream.showText(", ");
				}
			}
			hasPrinted = true;
		}
		if (hasPrinted)
			lineNumber = nextLine(contentStream, lineNumber);
		return lineNumber;
	}
	//Doesn't wrap nicely with save modifiers
	private int writeSaves(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "Fort ");
		contentStream.showText((fort > -1 ? "+" : "") + fort + ", ");
		printBold(contentStream, "Ref ");
		contentStream.showText((ref > -1 ? "+" : "") + ref + ", ");
		printBold(contentStream, "Will ");
		contentStream.showText((will > -1 ? "+" : "") + will);
		if (saveModifiers.length > 0) {
			contentStream.showText("; ");
			int printed = 35;
			for (int i = 0; i < saveModifiers.length; i++) {
				Pair<Integer, String> p = saveModifiers[i];
				if (printed + 4 + p.getY().length() > 59) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				printed += (3 + p.getY().length());
				contentStream.showText((p.getX() > -1 ? "+" : "") + p.getX() + " ");
				contentStream.showText(p.getY());
				if (i < saveModifiers.length - 1) {
					printed += 2;
					contentStream.showText(", ");
				}
			}
		}
		return nextLine(contentStream, lineNumber);
	}
	private int writeHP(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "hp ");
		contentStream.showText(hp + " (" + hpBreakdown + ")");
		int printed = 7 + hpBreakdown.length();
		if (fastHealing > 0) {
			contentStream.showText("; fast healing " + fastHealing);
			printed += 17;
		}
		if (regeneration != null) {
			contentStream.showText("; ");
			printed += 2;
			if (printed + 16 > 53) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			contentStream.showText("regeneration " + regeneration.getX() + " ");
			printed += 17;
			if (printed + regeneration.getY().length() + 2 > 53) {
				lineNumber = nextLine(contentStream, lineNumber);
				contentStream.showText("    ");
				printed = 0;
			}
			contentStream.showText("(" + regeneration.getY() + ")");
		}
		return nextLine(contentStream, lineNumber);
	}
	private int writeAC(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "AC ");
		contentStream.showText(ac + ", touch " + touch + ", flat-footed " + flatFooted);
		if (acModifiers.length > 0) {
			contentStream.showText(" (");
			int printed = 31;
			for (int i = 0; i < acModifiers.length; i++) {
				if (i > 0) {
					contentStream.showText(", ");
					printed += 2;
				}
				Pair<Integer, String> p = acModifiers[i];
				if (printed + 4 > 55) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				contentStream.showText((p.getX() > -1 ? "+" : "") + p.getX() + " ");
				printed += (Math.abs(p.getX()) > 9  ? 4 : 3);
				if (printed + p.getY().length() + 1 > 55) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				contentStream.showText(p.getY());
				printed += p.getY().length();
			}
			if (conditionalACModifiers.length > 0) {
				contentStream.showText("; ");
				printed += 2;
				for (int i = 0; i < conditionalACModifiers.length; i++) {
					if (i > 0) {
						contentStream.showText(", ");
						printed += 2;
					}
					Pair<Integer, String> p = conditionalACModifiers[i];
					if (printed + 4 > 55) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						printed = 0;
					}
					contentStream.showText((p.getX() > -1 ? "+" : "") + p.getX() + " ");
					printed += (Math.abs(p.getX()) > 9  ? 4 : 3);
					if (printed + p.getY().length() + 1 > 55) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						printed = 0;
					}
					contentStream.showText(p.getY());
					printed += p.getY().length();
				}
			}
			contentStream.showText(")");
		}
		return nextLine(contentStream, lineNumber);
	}
	private int writeTop(PDPageContentStream contentStream) throws IOException {
		printBold(contentStream, "XP " + xp);
		contentStream.newLine();
		int lineNumber = 1;
		lineNumber = writeNPC(contentStream, lineNumber);
		lineNumber = writeType(contentStream, lineNumber);
		lineNumber = writeSenses(contentStream, lineNumber);
		return writeAuras(contentStream, lineNumber);
	}
	private void writeHeader(PDPageContentStream contentStream) throws IOException {
		contentStream.newLineAtOffset(50, 715);
		contentStream.setFont(PDType1Font.TIMES_BOLD, 38);
		if (!basicName.equals(""))
			contentStream.showText(basicName.toUpperCase() +", " + name.toUpperCase());
		else
			contentStream.showText(name.toUpperCase());
		contentStream.setLeading(22);
		contentStream.newLine();
		contentStream.setLeading(18);
		contentStream.setFont(PDType1Font.TIMES_ITALIC, (float) 19.5);
		if (shortDesc.length() > 62) {
			int lineBreak = 62;
			char c = shortDesc.charAt(62);
			while (c != ' ') 
				c = shortDesc.charAt(--lineBreak);
			contentStream.showText(shortDesc.substring(0, lineBreak));
			contentStream.newLine();
			contentStream.showText(shortDesc.substring(lineBreak));
		}
		else
			contentStream.showText(shortDesc);
		contentStream.newLineAtOffset(0, -27);
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 18);
		contentStream.showText(titleName.toUpperCase());
		contentStream.newLineAtOffset(357, 0);
		contentStream.showText("CR " + cr);
		contentStream.newLineAtOffset(-357, -25);
		contentStream.setLeading(11);
	}
	private int writeNPC(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (!race.equals("")) {
			contentStream.showText(race + " ");
			int printed = race.length() + 1;
			for (int i = 0; i < classes.length; i++) {
				if (i > 0) {
					contentStream.showText("/");
					printed += 1;
				}
				Pair<String, Integer> p = classes[i];
				if (printed + p.getX().length() + 4 > 53) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				contentStream.showText(p.getX() + " " + p.getY());
				printed += p.getX().length() + 2;
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private int writeType(PDPageContentStream contentStream, int lineNumber) throws IOException {
		contentStream.showText(alignment.name() + " " + size.toString() + " " + type.name().toLowerCase().replace('_', ' '));
		if (subtypes.length > 0) {
			contentStream.showText(" (");
			int printed = type.name().length() + 4 + size.toString().length();
			for (int i = 0; i < subtypes.length; i++) {
				if (i > 0) {
					contentStream.showText(", ");
					printed += 2;
				}
				if (printed + subtypes[i].length() + 1 > 53) {
					lineNumber = nextLine(contentStream, lineNumber);
					contentStream.showText("    ");
					printed = 0;
				}
				contentStream.showText(subtypes[i]);
				printed += subtypes[i].length();
			}
			contentStream.showText(")");
		}
		return nextLine(contentStream, lineNumber);
	}
	private int writeSenses(PDPageContentStream contentStream, int lineNumber) throws IOException {
		printBold(contentStream, "Init ");
		contentStream.showText((init > -1 ? "+" : "" )+ init + "; ");
		int printed = 6;
		if (senses.length > 0) {
			printBold(contentStream, "Senses ");
			printed += 7;
			for (int i = 0; i < senses.length; i++) {
				if (i > 0) {
					contentStream.showText(", ");
					printed += 2;
				}
				if (senses[i].startsWith("#I")) {
					if (printed + senses[i].length() - 1 > 56) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						printed = 0;
					}
					contentStream.setFont(PDType1Font.TIMES_ITALIC, 11);
					contentStream.showText(senses[i].substring(2));
					contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
					printed += senses[i].length() - 2;
				}
				else {
					if (printed + senses[i].length() + 1 > 56) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						printed = 0;
					}
					contentStream.showText(senses[i]);
					printed += senses[i].length();
				}
			}
			contentStream.showText("; ");
			printed += 1;
		}
		if (printed + 14 > 56) {
			lineNumber = nextLine(contentStream, lineNumber);
			contentStream.showText("    ");
			printed = 0;
		}
		contentStream.showText("Perception " + (perception > -1 ? "+" : "" ) + perception);
		return nextLine(contentStream, lineNumber);
	}
	private int writeAuras(PDPageContentStream contentStream, int lineNumber) throws IOException {
		if (auras.length > 0) {
			printBold(contentStream, "Aura ");
			int printed = 4;
			for (int i = 0; i < auras.length; i++) {
				if (i > 0) {
					contentStream.showText(", ");
					printed += 2;
				}
				if (auras[i].startsWith("#I")) {
					if (printed + auras[i].length() - 1 > 54) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						printed = 0;
					}
					contentStream.setFont(PDType1Font.TIMES_ITALIC, 11);
					contentStream.showText(auras[i].substring(2));
					contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
					printed += auras[i].length() - 2;
				}
				else {
					if (printed + auras[i].length() + 1 > 54) {
						lineNumber = nextLine(contentStream, lineNumber);
						contentStream.showText("    ");
						printed = 0;
					}
					contentStream.showText(auras[i]);
					printed += auras[i].length();
				}
				
			}
			lineNumber = nextLine(contentStream, lineNumber);
		}
		return lineNumber;
	}
	private void drawPictures(PDPageContentStream contentStream, PDDocument pdoc) throws IOException {
		contentStream.drawImage(PDImageXObject.createFromFile(type.getPic() , pdoc), 458, shortDesc.length() > 62 ? 638 : 655);
		contentStream.drawImage(PDImageXObject.createFromFile(terrain.getPic() , pdoc), 490, shortDesc.length() > 62 ? 638 : 655);
		contentStream.drawImage(PDImageXObject.createFromFile(climate.getPic() , pdoc), 522, shortDesc.length() > 62 ? 638 : 655);
	}
	//Prints every creature within the given folder
	public static void printSet(String folderPath) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
		    paths
		        .filter(Files::isRegularFile)
		        .forEach(s -> new Creature(s.toString()).printToPdf()); 
		} 
	}
	public static void main(String[] args) throws IOException  {
		printSet("src/CreatureFiles/Hollow'sLastHope/");
		printSet("src/CreatureFiles/StrangeAeonsPC's/");
		//Creature d = new Creature("src/CreatureFiles/Monty.creature");
		//d.printToPdf();
		//d.saveToFile();
	}
}
