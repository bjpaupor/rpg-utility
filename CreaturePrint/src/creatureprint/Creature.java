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
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Creature {
	private String creatureFileLocation; //where the file that generates all other values is located
	private String path; //Path to put created document
	private String name;
	private String fileName;
	private String basicName;
	private String shortDesc;
	private boolean shortDescLong;
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
	private Trio<String[], Integer[], String> spellLikeAbilities; //OPT <[use-spells], [cl, concentration], Source>
	private Pair<Integer[], String> psychicMagic; //OPT <[cl, concentration], spells>
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

	//PDF Stuff - some of this should be customizable one day
	private PDDocument pdoc;
	private PDPage page;
	private PDPageContentStream contentStream;
	private int lineNumber;
	private float nameSize = 36, shortDescSize = 18, titleNameSize = 18, border = .9f * 69, 
			normalText = 9.5f, tinyText = 4.5f, largeText = 12;
	private PDFont italics = PDType1Font.TIMES_ITALIC, bold = PDType1Font.TIMES_BOLD, normal = PDType1Font.TIMES_ROMAN;
	private boolean firstPage = true;
	
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
			psychicMagic = new Pair<Integer[], String>(new Integer[0], "");
			int temp = Integer.parseInt(readALine(read));
			if (temp > 0) {
				Integer[] PSYIntTemp = new Integer[2];
				PSYIntTemp[0] = temp;
				PSYIntTemp[1] = Integer.parseInt(readALine(read));
				psychicMagic.setX(PSYIntTemp);
				psychicMagic.setY(readALine(read));
			}
			spellLikeAbilities = new Trio<String[], Integer[], String>(new String[0], new Integer[0], "");
			temp = Integer.parseInt(readALine(read));
			if (temp > 0) {
				String source = readALine(read);
				String[] slaTemp = new String[temp];
				Integer[] slaIntTemp = new Integer[2];
				slaIntTemp[0] = Integer.parseInt(readALine(read));
				slaIntTemp[1] = Integer.parseInt(readALine(read));
				spellLikeAbilities.setY(slaIntTemp);
				for (int i = 0; i < slaTemp.length; i++)
					slaTemp[i] = readALine(read);
				spellLikeAbilities.setX(slaTemp);
				spellLikeAbilities.setZ(source);
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
		} catch (Exception ex) {
			System.out.println(name);
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
		writeALine(file, "Where to place finished PDF: " + path);
		writeALine(file, "Name: " + name);
		writeALine(file, "Basic Name: " + basicName);
		writeALine(file, "Short Description: " + shortDesc);
		writeALine(file, "Title Name: " + titleName);
		writeALine(file, "CR: " + cr);
		writeALine(file, "Type: " + type);
		writeALine(file, "Terrain: " + terrain);
		writeALine(file, "Climate: " + climate);
		writeALine(file, "XP: " + xp);
		writeALine(file, "Race: " + race);
		writeALine(file, "Number of classes with levels (First is favored): " + classes.length);
		for (Pair<String, Integer> p :  classes) {
			writeALine(file, "Class: " + p.getX());
			writeALine(file, "Levels in above: " + p.getY());
		}
		writeALine(file, "Alignment: " + alignment);
		writeALine(file, "Size: " + size);
		writeALine(file, "Number of Subtypes: " + subtypes.length);
		for (String s :  subtypes)
			writeALine(file, "Subtype: " + s);
		writeALine(file, "Init: " + init);
		writeALine(file, "Number of senses: " + senses.length);
		for (String s :  senses)
			writeALine(file, "Sense: " + s);
		writeALine(file, "Perception: " + perception);
		writeALine(file, "Number of auras: " + auras.length);
		for (String s :  auras)
			writeALine(file, "Aura: " + s);
		writeALine(file, "AC: " + ac);
		writeALine(file, "touch: " + touch);
		writeALine(file, "flat-footed: "  + flatFooted);
		writeALine(file, "Number of AC Modifiers: " + acModifiers.length);
		for (Pair<Integer, String> p :  acModifiers) {
			writeALine(file, "Modifier: " + p.getX());
			writeALine(file, "Type of above Modifier: " + p.getY());
		}
		writeALine(file, "Number of Conditional AC Modifiers: " + conditionalACModifiers.length);
		for (Pair<Integer, String> p :  conditionalACModifiers) {
			writeALine(file, "Modifier: " + p.getX());
			writeALine(file, "Type of above Modifier: " + p.getY());
		}
		writeALine(file, "HP: " + hp);
		writeALine(file, "HP breakdown: " + hpBreakdown);
		writeALine(file, "Fast Healing: " + fastHealing);
		if (regeneration != null) {
			writeALine(file, "Regeneration: " + regeneration.getX());
			writeALine(file, "Bypass: " + regeneration.getY());
		}
		else
			writeALine(file, "Regeneration: 0");
		writeALine(file, "Fort: " + fort);
		writeALine(file, "Ref: " + ref);
		writeALine(file, "Will: " + will);
		writeALine(file, "Number of conditional save modifiers: " + saveModifiers.length);
		for (Pair<Integer, String> p :  saveModifiers) {
			writeALine(file, "Modifier: " + p.getX());
			writeALine(file, "Condition/Type of above Modifier: " + p.getY());
		}
		writeALine(file, "Number of Defensive Abilities: " + defensiveAbilities.length);
		for (String s :  defensiveAbilities)
			writeALine(file, "Defensive Ability: " + s);
		if (dr != null) {
			writeALine(file, "DR: " + dr.getX());
			writeALine(file, "Bypass: " + dr.getY());
		}
		else
			writeALine(file, "DR: 0");
		writeALine(file, "Number of Immunities: " + immunities.length);
		for (String s :  immunities)
			writeALine(file, "Immunity: " + s);
		writeALine(file, "Number of Resistances: " + resistances.length);
		for (Pair<String, Integer> p :  resistances) {
			writeALine(file, "Resistance Type: " + p.getX());
			writeALine(file, "Resistance Amount: " + p.getY());
		}
		writeALine(file, "SR: " + sr);
		writeALine(file, "Number of Weaknesses: " + weaknesses.length);
		for (String s :  weaknesses)
			writeALine(file, "Weakness: " + s);
		writeALine(file, "Speed: " + speed);
		writeALine(file, "Number of other speeds (#B means base speed): " + speeds.length);
		for (String s :  speeds) 
			writeALine(file, "Other Speed: " + s);
		writeALine(file, "Melee: " + melee);
		writeALine(file, "Ranged: " + ranged);
		writeALine(file, "Space: " + space);
		writeALine(file, "Reach: " + reach);
		writeALine(file, "Number of Special Attacks: " + specialAttacks.length);
		for (String s :  specialAttacks)
			writeALine(file, "Special Attack: " + s);
		//spellLikeAbilities <[use-spells], [cl, concentration]>
		if (psychicMagic.getX().length > 0) {
			writeALine(file, "Psychic Magic CL: " + psychicMagic.getX()[0]);
			writeALine(file, "Psychic Magic Concentration: " + psychicMagic.getX()[1]);
			writeALine(file, "Psychic Magic Spells: " + psychicMagic.getY());
		}
		else
			writeALine(file, "Psychic Magic CL: " + 0);
		if (spellLikeAbilities.getX().length > 0) {
			writeALine(file, "SLA Use Categories (1/day, etc.): " + spellLikeAbilities.getX().length);
			writeALine(file, "SLA Source: " + spellLikeAbilities.getZ());
			writeALine(file, "SLA CL: " + spellLikeAbilities.getY()[0]);
			writeALine(file, "SLA Concentration: " + spellLikeAbilities.getY()[1]);
			for (String s :  spellLikeAbilities.getX())
				writeALine(file, "SLA Use Category and Spells: " + s);
		}
		else
			writeALine(file, "SLA Use Categories (1/day, etc.): 0");
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		if (spellsKnown.getY().length > 0) {
			writeALine(file, "Spells Known Levels: " + spellsKnown.getY().length);
			writeALine(file, "Spells Known Class: " + spellsKnown.getZ()[0]);
			writeALine(file, "Spells Known CL: " + spellsKnown.getX()[0]);
			writeALine(file, "Spells Known Concentration: " + spellsKnown.getX()[1]);
			writeALine(file, "Number of Bolded Sections: " + (spellsKnown.getZ().length - 1));
			for (int i = 1; i < spellsKnown.getZ().length; i++)
				writeALine(file, "Bold Section: " + spellsKnown.getZ()[i]);
			for (String s :  spellsKnown.getY())
				writeALine(file, "Spells Known and Use Level: " + s);
		}
		else
			writeALine(file, "Spells Known Levels: 0");
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		if (spellsPrepared.getY().length > 0) {
			writeALine(file, "Spells Prepared Levels: " + spellsPrepared.getY().length);
			writeALine(file, "Spells Prepared Class: " + spellsPrepared.getZ()[0]);
			writeALine(file, "Spells Prepared CL: " + spellsPrepared.getX()[0]);
			writeALine(file, "Spells Prepared Concentration: " + spellsPrepared.getX()[1]);
			writeALine(file, "Number of Bolded Sections: " + (spellsPrepared.getZ().length - 1));
			for (int i = 1; i < spellsPrepared.getZ().length; i++)
				writeALine(file, "Bold Section: " + spellsPrepared.getZ()[i]);				
			for (String s :  spellsPrepared.getY())
				writeALine(file, "Spells Prepared at Level: " + s);
		}
		else
			writeALine(file, "Spells Prepared Levels: 0");
		writeALine(file, "Before Combat: " + beforeCombat);
		writeALine(file, "During Combat: " + duringCombat);
		writeALine(file, "Morale: " + morale);
		writeALine(file, "Base Statistics: " + baseStatistics);
		writeALine(file, "Str: " + str);
		writeALine(file, "Dex: " + dex);
		writeALine(file, "Con: " + con);
		writeALine(file, "Int: " + intelligence);
		writeALine(file, "Wis: " + wis);
		writeALine(file, "Cha: " + cha);
		writeALine(file, "BAB: " + bab);
		writeALine(file, "CMB: " + cmb);
		writeALine(file, "Number of Special CMBs: " + cmbs.length);
		for (String s :  cmbs)
			writeALine(file, "Special CMB: " + s);
		writeALine(file, "CMD: " + cmd);
		writeALine(file, "Number of Special CMDs: " + cmds.length);
		for (String s :  cmds)
			writeALine(file, "Special CMD: " + s);
		writeALine(file, "Number of Feats: " + feats.length);
		for (String s :  feats)
			writeALine(file, "Feat: " + s);
		writeALine(file, "Number of Skills: " + skills.length);
		for (String s :  skills)
			writeALine(file, "Skill: " + s);
		writeALine(file, "Number of Racial Modifiers: " + racialModifiers.length);
		for (String s :  racialModifiers)
			writeALine(file, "Racial Modifier: " + s);
		writeALine(file, "Number of Languages: " + languages.length);
		for (String s :  languages)
			writeALine(file, "Language: " + s);
		writeALine(file, "Number of Special Languages: " + languagesSpecial.length);
		for (String s :  languagesSpecial)
			writeALine(file, "Special Language: " + s);
		writeALine(file, "Number of Special Qualities: " + sq.length);
		for (String s :  sq)
			writeALine(file, "SQ: " + s);
		writeALine(file, "Number of Combat Gear: " + combatGear.length);
		for (String s :  combatGear)
			writeALine(file, "Combat Gear: " + s);
		writeALine(file, "Number of Other Gear: " + otherGear.length);
		for (String s :  otherGear)
			writeALine(file, "Other Gear: " + s);
		writeALine(file, "Environment: " + environment);
		writeALine(file, "Organization: " + organization);
		writeALine(file, "Treasure: " + treasure);
		writeALine(file, "Number of Special Abilities: " + specialAbilities.length);
		//OPT <Ability Name <Ability Description, Ability Sub-Description>>
		for (Pair<String, Pair<String, String[]>> p :  specialAbilities) {
			writeALine(file, "Ability Name: " + p.getX());
			writeALine(file, "Ability Text: " + p.getY().getX());
			writeALine(file, "Number of Sub-Descriptions: " + p.getY().getY().length);
			for (String s :  p.getY().getY())
				writeALine(file, "Sub-Description: " + s);
		}
		writeALine(file, "Description: " + description);
		file.close();
	}
	//Obliterates comments
	public void saveToFileCompressed() throws IOException {
		BufferedWriter file = new BufferedWriter(new FileWriter(creatureFileLocation));
		writeALine(file, ":" + path);
		writeALine(file, ":" + name);
		writeALine(file, ":" + basicName);
		writeALine(file, ":" + shortDesc);
		writeALine(file, ":" + titleName);
		writeALine(file, ":" + cr);
		writeALine(file, ":" + type);
		writeALine(file, ":" + terrain);
		writeALine(file, ":" + climate);
		writeALine(file, ":" + xp);
		writeALine(file, ":" + race);
		writeALine(file, ":" + classes.length);
		for (Pair<String, Integer> p : classes) {
			writeALine(file, ":" + p.getX());
			writeALine(file, ":" + p.getY());
		}
		writeALine(file, ":" + alignment);
		writeALine(file, ":" + size);
		writeALine(file, ":" + subtypes.length);
		for (String s : subtypes)
			writeALine(file, ":" + s);
		writeALine(file, ":" + init);
		writeALine(file, ":" + senses.length);
		for (String s : senses)
			writeALine(file, ":" + s);
		writeALine(file, ":" + perception);
		writeALine(file, ":" + auras.length);
		for (String s : auras)
			writeALine(file, ":" + s);
		writeALine(file, ":" + ac);
		writeALine(file, ":" + touch);
		writeALine(file, ":"  + flatFooted);
		writeALine(file, ":" + acModifiers.length);
		for (Pair<Integer, String> p : acModifiers) {
			writeALine(file, ":" + p.getX());
			writeALine(file, ":" + p.getY());
		}
		writeALine(file, ":" + conditionalACModifiers.length);
		for (Pair<Integer, String> p : conditionalACModifiers) {
			writeALine(file, ":" + p.getX());
			writeALine(file, ":" + p.getY());
		}
		writeALine(file, ":" + hp);
		writeALine(file, ":" + hpBreakdown);
		writeALine(file, ":" + fastHealing);
		if (regeneration != null) {
			writeALine(file, ":" + regeneration.getX());
			writeALine(file, ":" + regeneration.getY());
		}
		else
			writeALine(file, ":0");
		writeALine(file, ":" + fort);
		writeALine(file, ":" + ref);
		writeALine(file, ":" + will);
		writeALine(file, ":" + saveModifiers.length);
		for (Pair<Integer, String> p : saveModifiers) {
			writeALine(file, ":" + p.getX());
			writeALine(file, ":" + p.getY());
		}
		writeALine(file, ":" + defensiveAbilities.length);
		for (String s : defensiveAbilities)
			writeALine(file, ":" + s);
		if (dr != null) {
			writeALine(file, ":" + dr.getX());
			writeALine(file, ":" + dr.getY());
		}
		else
			writeALine(file, ":0");
		writeALine(file, ":" + immunities.length);
		for (String s : immunities)
			writeALine(file, ":" + s);
		writeALine(file, ":" + resistances.length);
		for (Pair<String, Integer> p : resistances) {
			writeALine(file, ":" + p.getX());
			writeALine(file, ":" + p.getY());
		}
		writeALine(file, ":" + sr);
		writeALine(file, ":" + weaknesses.length);
		for (String s : weaknesses)
			writeALine(file, ":" + s);
		writeALine(file, ":" + speed);
		writeALine(file, ":" + speeds.length);
		for (String s : speeds) 
			writeALine(file, ":" + s);
		writeALine(file, ":" + melee);
		writeALine(file, ":" + ranged);
		writeALine(file, ":" + space);
		writeALine(file, ":" + reach);
		writeALine(file, ":" + specialAttacks.length);
		for (String s : specialAttacks)
			writeALine(file, ":" + s);
		//spellLikeAbilities <[use-spells], [cl, concentration]>
		if (psychicMagic.getX().length > 0) {
			writeALine(file, ":" + psychicMagic.getX()[0]);
			writeALine(file, ":" + psychicMagic.getX()[1]);
			writeALine(file, ":" + psychicMagic.getY());
		}
		else
			writeALine(file, ":" + 0);
		if (spellLikeAbilities.getX().length > 0) {
			writeALine(file, ":" + spellLikeAbilities.getX().length);
			writeALine(file, ":" + spellLikeAbilities.getZ());
			writeALine(file, ":" + spellLikeAbilities.getY()[0]);
			writeALine(file, ":" + spellLikeAbilities.getY()[1]);
			for (String s : spellLikeAbilities.getX())
				writeALine(file, ":" + s);
		}
		else
			writeALine(file, ":0");
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		if (spellsKnown.getY().length > 0) {
			writeALine(file, ":" + spellsKnown.getY().length);
			writeALine(file, ":" + spellsKnown.getZ()[0]);
			writeALine(file, ":" + spellsKnown.getX()[0]);
			writeALine(file, ":" + spellsKnown.getX()[1]);
			writeALine(file, ":" + (spellsKnown.getZ().length - 1));
			for (int i = 1; i < spellsKnown.getZ().length; i++)
				writeALine(file, ":" + spellsKnown.getZ()[i]);
			for (String s : spellsKnown.getY())
				writeALine(file, ":" + s);
		}
		else
			writeALine(file, ":0");
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		if (spellsPrepared.getY().length > 0) {
			writeALine(file, ":" + spellsPrepared.getY().length);
			writeALine(file, ":" + spellsPrepared.getZ()[0]);
			writeALine(file, ":" + spellsPrepared.getX()[0]);
			writeALine(file, ":" + spellsPrepared.getX()[1]);
			writeALine(file, ":" + (spellsPrepared.getZ().length - 1));
			for (int i = 1; i < spellsPrepared.getZ().length; i++)
				writeALine(file, ":" + spellsPrepared.getZ()[i]);				
			for (String s : spellsPrepared.getY())
				writeALine(file, ":" + s);
		}
		else
			writeALine(file, ":0");
		writeALine(file, ":" + beforeCombat);
		writeALine(file, ":" + duringCombat);
		writeALine(file, ":" + morale);
		writeALine(file, ":" + baseStatistics);
		writeALine(file, ":" + str);
		writeALine(file, ":" + dex);
		writeALine(file, ":" + con);
		writeALine(file, ":" + intelligence);
		writeALine(file, ":" + wis);
		writeALine(file, ":" + cha);
		writeALine(file, ":" + bab);
		writeALine(file, ":" + cmb);
		writeALine(file, ":" + cmbs.length);
		for (String s : cmbs)
			writeALine(file, ":" + s);
		writeALine(file, ":" + cmd);
		writeALine(file, ":" + cmds.length);
		for (String s : cmds)
			writeALine(file, ":" + s);
		writeALine(file, ":" + feats.length);
		for (String s : feats)
			writeALine(file, ":" + s);
		writeALine(file, ":" + skills.length);
		for (String s : skills)
			writeALine(file, ":" + s);
		writeALine(file, ":" + racialModifiers.length);
		for (String s : racialModifiers)
			writeALine(file, ":" + s);
		writeALine(file, ":" + languages.length);
		for (String s : languages)
			writeALine(file, ":" + s);
		writeALine(file, ":" + languagesSpecial.length);
		for (String s : languagesSpecial)
			writeALine(file, ":" + s);
		writeALine(file, ":" + sq.length);
		for (String s : sq)
			writeALine(file, ":" + s);
		writeALine(file, ":" + combatGear.length);
		for (String s : combatGear)
			writeALine(file, ":" + s);
		writeALine(file, ":" + otherGear.length);
		for (String s : otherGear)
			writeALine(file, ":" + s);
		writeALine(file, ":" + environment);
		writeALine(file, ":" + organization);
		writeALine(file, ":" + treasure);
		writeALine(file, ":" + specialAbilities.length);
		//OPT <Ability Name <Ability Description, Ability Sub-Description>>
		for (Pair<String, Pair<String, String[]>> p : specialAbilities) {
			writeALine(file, ":" + p.getX());
			writeALine(file, ":" + p.getY().getX());
			writeALine(file, ":" + p.getY().getY().length);
			for (String s : p.getY().getY())
				writeALine(file, ":" + s);
		}
		writeALine(file, ":" + description);
		file.close();
	}
	private void printHeader(String text) throws IOException {
		float width = bold.getStringWidth(text) / 1000 * normalText;
		float dashWidth = bold.getStringWidth("-") / 1000 * normalText;
		while (width + dashWidth  < PDRectangle.LETTER.getWidth() / 2 - .9f * 72) {
			width += dashWidth;
			text += "-";
		}
		nextLine();
		printBold(text);
	}
	private void printAParagraph(String line) throws IOException {
		WrappingText text = new WrappingText(line, PDRectangle.LETTER.getWidth() / 2 - .9f * 72, normalText);
		for (String s : text.getParagraphLines()) {
			nextLine();
			if (s.startsWith("#P"))
				showWithEffects("    ");
			if (s.startsWith("#T")) {
				nextLine();
				printTitle(s.substring(2, s.length() - 2));
			}
			else 
				for (String ss : s.split("(?<=\\s)")) 
					showWithEffects(ss);
		}
	}
	private void printAnIndentedLine(String line) throws IOException {
		WrappingText text = new WrappingText(line, PDRectangle.LETTER.getWidth() / 2 - .9f * 72, normalText);
		boolean first = true;
		for (String s : text.getIndentedLines()) {
			nextLine();
			showWithEffects("    ");
			if (!first)
				showWithEffects("    ");
			first = false;
			for (String ss : s.split("(?<=\\s)")) 
				showWithEffects(ss);
		}
	}
	private void printALine(String line) throws IOException {
		WrappingText text = new WrappingText(line, PDRectangle.LETTER.getWidth() / 2 - .9f * 72, normalText);
		boolean first = true;
		for (String s : text.getFancyLines()) {
			nextLine();
			if (!first)
				showWithEffects("    ");
			first = false;
			for (String ss : s.split("(?<=\\s)")) 
				showWithEffects(ss);
		}
	}
	private void showWithEffects(String line) throws IOException {
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
			if (line.indexOf("#P", current) > -1)
				if (next == -1 || line.indexOf("#", current) < next) {
					effect = 4;
					next = line.indexOf("#", current);
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
				printItalic(line.substring(next + 2, end));
				break;
			case 1:
				printBold(line.substring(next + 2, end));
				break;
			case 2:
				printSuper(line.substring(next + 2, end));
				break;
			case 3:
				contentStream.showText(line.substring(next + 1, end));
				break;
			case 4:
				contentStream.showText(line.substring(next + 2, end));
				break;
			}
			current = end;
		}
	}
	@SuppressWarnings("deprecation")
	private void printSuper(String s) throws IOException {
		contentStream.setFont(normal, tinyText);
		contentStream.appendRawCommands("\n5 Ts\n");
		contentStream.showText(s);
		contentStream.appendRawCommands("\n0 Ts\n");
		contentStream.setFont(normal, normalText);
	}
	private void printItalic(String s) throws IOException {
		contentStream.setFont(italics, normalText);
		contentStream.showText(s);
		contentStream.setFont(normal, normalText);
	}
	private void printBold(String s) throws IOException {
		contentStream.setFont(bold, normalText);
		contentStream.showText(s);
		contentStream.setFont(normal, normalText);
	}
	private void printTitle(String s) throws IOException {
		contentStream.setFont(bold, largeText);
		contentStream.newLineAtOffset(0, 2);
		contentStream.showText(s);
		contentStream.newLineAtOffset(0, -2);
		contentStream.setFont(normal, normalText);
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
	private void nextLine() throws IOException {
		if (lineNumber == 53 && shortDescLong && firstPage) 
			contentStream.newLineAtOffset(PDRectangle.LETTER.getWidth() / 2 - .9f * 66, 
					normal.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * normalText * 52);
		else if (lineNumber == 55 && !shortDescLong && firstPage) 
			contentStream.newLineAtOffset(PDRectangle.LETTER.getWidth() / 2 - .9f * 66, 
					normal.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * normalText * 54);
		else if (lineNumber == 63 && !firstPage)
			contentStream.newLineAtOffset(PDRectangle.LETTER.getWidth() / 2 - .9f * 66, 
					normal.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * normalText * 62);
		else if (lineNumber == 106 && shortDescLong && firstPage)
			nextPage();
		else if (lineNumber == 110 && !shortDescLong && firstPage) 
			nextPage();
		else if (lineNumber == 126 && !firstPage) 
			nextPage();
		else
			contentStream.newLine();
		++lineNumber;
	}
	private void nextPage() throws IOException {
		firstPage = false;
		contentStream.close();
		pdoc.addPage(page);
		page = new PDPage();
		contentStream = new PDPageContentStream(pdoc, page);
		contentStream.beginText();
		contentStream.setFont(normal, normalText);
		contentStream.newLineAtOffset(border, PDRectangle.LETTER.getHeight() - border - 
				normal.getFontDescriptor().getCapHeight() / 1000 * normalText);
		contentStream.setLeading(normal.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * normalText);
		lineNumber = 0;
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
			this.pdoc = pdoc;
			page = new PDPage();
			contentStream = new PDPageContentStream(pdoc, page);
			drawPictures(pdoc);
			contentStream.beginText();
			writeHeader();
			writeTop();
			writeDefense();
			writeOffense();
			writeTactics();
			writeStatistics();
			writeEcology();
			writeSpecialAbilities();
			if (!description.equals("")) {
				nextLine(); 
				printAParagraph(description);
			}
			contentStream.endText();
			contentStream.close();
			pdoc.addPage(page);
			if (!Files.exists(Paths.get(path)))
				new File(path).mkdirs();
			pdoc.save(path + fileName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	/**
	 * adds #H before each word in s
	 * @param s line to embolden
	 * @return embolened line
	 */
	private String embolden(String s) {
		String result = "";
		for (String ss : s.split("(?<=\\s)")) 
			result += "#H" + ss;
		return result.trim();
	}
	private void writeSpecialAbilities() throws IOException {
		if (specialAbilities.length > 0) {
			printHeader("SPECIAL ABILITIES");
			for (int i = 0; i < specialAbilities.length; i++) {
				String text = embolden(specialAbilities[i].getX());
				text += ' ' + specialAbilities[i].getY().getX();
				printALine(text);
				for (int j = 0; j < specialAbilities[i].getY().getY().length; j++) 
					printAnIndentedLine(specialAbilities[i].getY().getY()[j]);
			}
		}
	}
	private void writeEcology() throws IOException {
		if (!(environment.equals("") && organization.equals("") && treasure.equals(""))) {
			printHeader("ECOLOGY");
			if (!environment.equals("")) 
				printALine("#HEnvironment " + environment);
			if (!organization.equals("")) 
				printALine("#HOrganization " + organization);
			if (!treasure.equals("")) 
				printALine("#HTreasure " + treasure);
		}
	}
	private void writeStatistics() throws IOException {
		printHeader("STATISTICS");
		writeAbilityScores();
		writeBABCMBD();
		writeFeats();
		writeSkills();
		writeLanguages();
		writeSQs();
		writeGear();
	}
	private void writeGear() throws IOException {
		String text = "";
		if (combatGear.length > 0) {
			text += "#HCombat #HGear ";
			for (int i = 0; i < combatGear.length; i++) {
				text += combatGear[i];
				if (i + 1 < combatGear.length) 
					text += ", ";
			}
		}
		if (otherGear.length > 0) {
			if (combatGear.length > 0) 
				text += "; #HOther ";
			text += "#HGear ";
			for (int i = 0; i < otherGear.length; i++) {
				text += otherGear[i];
				if (i + 1 < otherGear.length) 
					text += ", ";
			}
		}
		if (!text.equals(""))
			printALine(text);
	}
	private void writeSQs() throws IOException {
		if (sq.length > 0) {
			String text = "#HSQ ";
			for (int i = 0; i < sq.length; i++) {
				text += sq[i];
				if (i + 1 < sq.length) 
					text += ", ";
			}
			printALine(text);
		}
	}
	private void  writeLanguages() throws IOException {
		if (languages.length > 0) {
			String text = "#HLanguages ";
			for (int i = 0; i < languages.length; i++) {
				text += languages[i];
				if (i + 1 < languages.length) 
					text += ", ";
			}
			if (languagesSpecial.length > 0) {
				text += "; ";
				for (int i = 0; i < languagesSpecial.length; i++) {
					text += languagesSpecial[i];
					if (i + 1 < languagesSpecial.length) 
						text += ", ";
				}
			}
			printALine(text);
		}
	}
	private void writeSkills() throws IOException {
		if (skills.length > 0) {
			String text = "#HSkills ";
			for (int i = 0; i < skills.length; i++) {
				text += skills[i];
				if (i + 1 < skills.length) 
					text += ", ";
			}
			if (racialModifiers.length > 0) {
				text += "; " + "#HRacial #HModifiers ";
				for (int i = 0; i < racialModifiers.length; i++) {
					text += racialModifiers[i];
					if (i + 1 < racialModifiers.length) 
						text += ", ";
				}
			}
			printALine(text);
		}
	}
	private void writeFeats() throws IOException {
		if (feats.length > 0) {
			String text = "#HFeats ";
			for (int i = 0; i < feats.length; i++) {
				text += feats[i];
				if (i + 1 < feats.length) 
					text += ", ";
			}
			printALine(text);
		}
	}
	private void writeBABCMBD() throws IOException {
		String text = "#HBase #HAtk " + "+" + bab + "; " + "#HCMB ";
		if (cmb == EMDASH)
			text += "—";
		else
			text += (cmb > -1 ? "+" : "") + cmb;
		if (cmbs.length > 0) {
			text += " (";
			for (int i = 0; i < cmbs.length; i++) {
				text += cmbs[i];
				if (i + 1 < cmbs.length) 
					text += ", ";
			}
			text += ")";
		}
		text += "; " + "#HCMD " + (cmd == EMDASH ? "—" : cmd);
		if (cmds.length > 0) {
			text += " (";
			for (int i = 0; i < cmds.length; i++) {
				text += cmds[i];
				if (i + 1 < cmds.length) 
					text += ", ";
			}
			text += ")";
		}
		printALine(text);
	}
	private void writeAbilityScores() throws IOException {
		String line = "#HStr " + (str == EMDASH ? "—" : str);
		line += ", #HDex " + (dex == EMDASH ? "—" : dex);
		line += ", #HCon " + (con == EMDASH ? "—" : con); 
		line += ", #HInt " + (intelligence == EMDASH ? "—" : intelligence);
		line += ", #HWis " + (wis == EMDASH ? "—" : wis);
		line += ", #HCha " + (cha == EMDASH ? "—" : cha); 
		printALine(line);
	}
	private void writeTactics() throws IOException {
		if (beforeCombat.equals("") && duringCombat.equals("") && morale.equals("") && baseStatistics.equals(""))
			return;
		printHeader("TACTICS");
		if (!beforeCombat.equals("")) 
			printALine("#HBefore #HCombat " + beforeCombat);
		if (!duringCombat.equals("")) 
			printALine("#HDuring #HCombat " + duringCombat);
		if (!morale.equals("")) 
			printALine("#HMorale " + morale);
		if (!baseStatistics.equals("")) 
			printALine("#HBase #HStatistics " + baseStatistics);
	}
	private void writeOffense() throws IOException {
		printHeader("OFFENSE");
		writeSpeeds();
		if (!melee.equals("")) 
			printALine("#HMelee " + melee);
		if (!ranged.equals("")) 
			printALine("#HRanged " + ranged);
		if (!(space.equals("5") && reach == 5))
			printALine("#HSpace " + space + " ft.; " + "#HReach " + reach + " ft.");
		writeSpecialAttacks();
		writePsychicMagic();
		writeSLAs();
		writeSpellsKnown();
		writeSpellsPrepared();
	}
	private void writeSpellsPrepared() throws IOException {
		if (spellsPrepared.getY().length > 0) {//OPT <[cl, concentration], [use-spells], [className, Bold, after, Bold ...]>
			String text = "";
			if (!spellsPrepared.getZ()[0].equals("none")) 
				text += "#H" + spellsPrepared.getZ()[0] + " #HSpells #HPrepared ";
			else
				text += "#HSpells #HPrepared ";
			int conc = spellsPrepared.getX()[1];
			text += "(CL " + getPlace(spellsPrepared.getX()[0]) + "; concentration " + 
					((conc > -1) ? ("+" + conc) : (conc))  + ')';
			printALine(text);
			for (int i = 0; i < spellsPrepared.getY().length; i++) 
				printAnIndentedLine(spellsPrepared.getY()[i]);
			text = "";
			for (int i = 1; i < spellsPrepared.getZ().length; i++) {
				text += "#H" + spellsPrepared.getZ()[i];
				if (i + 1 < spellsPrepared.getZ().length) 
					text += "; ";
			}
			if (!text.equals(""))
				printAnIndentedLine(text);
		}
	}
	private void writeSpellsKnown() throws IOException {
		if (spellsKnown.getY().length > 0) {
			String text = "";
			if (!spellsKnown.getZ()[0].equals("none")) 
				text += "#H" + spellsKnown.getZ()[0] + " #HSpells #HKnown ";
			else
				text += "#HSpells #HKnown ";
			int conc = spellsKnown.getX()[1];
			text += "(CL " + getPlace(spellsKnown.getX()[0]) + "; concentration " + 
					((conc > -1) ? ("+" + conc) : (conc))  + ')';
			printALine(text);
			for (int i = 0; i < spellsKnown.getY().length; i++) 
				printAnIndentedLine(spellsKnown.getY()[i]);
			text = "";
			for (int i = 1; i < spellsKnown.getZ().length; i++) {
				text += "#H" + spellsKnown.getZ()[i];
				if (i + 1 < spellsKnown.getZ().length)
					text += "; ";
			}
			if (!text.equals(""))
				printAnIndentedLine(text);
		}
	}
	private void writePsychicMagic() throws IOException {
		if (psychicMagic.getX().length > 0) {
			int conc = psychicMagic.getX()[1];
			String text = "#HPsychic #HMagic " + "(CL " + getPlace(psychicMagic.getX()[0]) +
					"; concentration " + ((conc > -1) ? ("+" + conc) : (conc))  + ')';
			printALine(text);
			printAnIndentedLine(psychicMagic.getY());
		}
	}
	private void writeSLAs() throws IOException {
		if (spellLikeAbilities.getX().length > 0) {
			int conc = spellLikeAbilities.getY()[1];
			String text = spellLikeAbilities.getZ().equals("none") ? "" : "#H" + spellLikeAbilities.getZ() + " ";
			text += "#HSpell-Like #HAbilities " + "(CL " + getPlace(spellLikeAbilities.getY()[0]) +
					"; concentration " + ((conc > -1) ? ("+" + conc) : (conc))  + ')';
			printALine(text);
			for (int i = 0; i < spellLikeAbilities.getX().length; i++) {
				text = spellLikeAbilities.getX()[i];
				printAnIndentedLine(text);
			}
		}
	}
	private void writeSpecialAttacks() throws IOException {
		if (specialAttacks.length > 0) {
			String text = "#HSpecial #HAttacks ";
			for (int i = 0; i < specialAttacks.length; i++) {
				text += specialAttacks[i];
				if (i + 1 < specialAttacks.length) 
					text += "; ";
			}
			printALine(text);
		}
	}
	private void writeSpeeds() throws IOException {
		String text = "#HSpeed ";
		if (speed >= 0)
			text += speed + " ft.";
		if (speeds.length > 0) {
			for (int i = 0; i < speeds.length; i++) {
				String p = speeds[i];
				if (p.startsWith("#B"))
					p = '(' + p.substring(2) + " base)";
				else if (!(i == 0 && speed < 0))
					text += ",";
				if (speed >= 0 || i > 0)
					text += " ";
				text += p;
			}
		}
		printALine(text);
	}
	private void writeDefense() throws IOException {
		printHeader("DEFENSE");
		writeAC();
		writeHP();
		writeSaves();
		writeDefensiveAbilities();
	}
	private void writeDefensiveAbilities() throws IOException {
		String text = "";
		if (defensiveAbilities.length > 0) {
			text += "#HDefensive #HAbilities ";
			for (int i = 0; i < defensiveAbilities.length; i++) {
				text += defensiveAbilities[i];
				if (i < defensiveAbilities.length - 1) 
					text += ", ";
			}
		}
		if (dr != null) {
			if (!text.equals("")) 
				text += "; ";
			text += "#HDR " + dr.getX() + "/" + dr.getY();
		}
		if (immunities.length > 0) {
			if (!text.equals("")) 
				text += "; ";
			text += "#HImmune ";
			for (int i = 0; i < immunities.length; i++) {
				text += immunities[i];
				if (i < immunities.length - 1) 
					text += ", ";
			}
		}
		if (resistances.length > 0) {
			if (!text.equals("")) 
				text += "; ";
			text += "#HResist ";
			for (int i = 0; i < resistances.length; i++) {
				text += resistances[i].getX() + " " + resistances[i].getY();
				if (i < resistances.length - 1)
					text += ", ";
			}
		}
		if (sr > 0) {
			if (!text.equals("")) 
				text += "; ";
			text += "#HSR " + sr;
		}
		if (weaknesses.length > 0) {
			if (!text.equals("")) 
				text += "; ";
			text += "#HWeaknesses ";
			for (int i = 0; i < weaknesses.length; i++) {
				text += weaknesses[i];
				if (i < weaknesses.length - 1) 
					text += ", ";
			}
		}
		if (!text.equals(""))
			printALine(text);
	}
	private void writeSaves() throws IOException {
		String text = "#HFort " + (fort > -1 ? "+" : "") + fort + ", " + "#HRef " +
				(ref > -1 ? "+" : "") + ref + ", " + "#HWill " + (will > -1 ? "+" : "") + will;
		if (saveModifiers.length > 0) {
			text += "; ";
			for (int i = 0; i < saveModifiers.length; i++) {
				Pair<Integer, String> p = saveModifiers[i];
				text += (p.getX() > -1 ? "+" : "") + p.getX() + " " + p.getY();
				if (i < saveModifiers.length - 1) 
					text += ", ";
			}
		}
		printALine(text);
	}
	private void writeHP() throws IOException {
		String text = "#Hhp " + hp + " (" + hpBreakdown + ")";
		if (fastHealing > 0) 
			text += "; fast healing " + fastHealing;
		if (regeneration != null) 
			text += "; " + "regeneration " + regeneration.getX() + " " + "(" + regeneration.getY() + ")";
		printALine(text);
	}
	private void writeAC() throws IOException {
		String text = "#HAC " + ac + ", touch " + touch + ", flat-footed " + flatFooted;
		if (acModifiers.length > 0) {
			text += " (";
			for (int i = 0; i < acModifiers.length; i++) {
				if (i > 0) 
					text += ", ";				
				Pair<Integer, String> p = acModifiers[i];
				text += (p.getX() > -1 ? "+" : "") + p.getX() + " " + p.getY();
			}
			if (conditionalACModifiers.length > 0) {
				text += "; ";
				for (int i = 0; i < conditionalACModifiers.length; i++) {
					if (i > 0) 
						text += ", ";
					Pair<Integer, String> p = conditionalACModifiers[i];
					text += (p.getX() > -1 ? "+" : "") + p.getX() + " " + p.getY();
				}
			}
			text += ")";
		}
		printALine(text);
	}
	private void writeTop() throws IOException {
		contentStream.setLeading(normal.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * normalText);
		printBold("XP " + xp);
		lineNumber = 1;
		writeNPC();
		writeType();
		writeSenses();
		writeAuras();
	}
	private void writeHeader() throws IOException {
		contentStream.newLineAtOffset(border, PDRectangle.LETTER.getHeight() - border - bold.getFontDescriptor().getCapHeight() / 1000 * nameSize);
		contentStream.setFont(bold, nameSize);
		if (!basicName.equals(""))
			contentStream.showText(basicName.toUpperCase() +", " + name.toUpperCase());
		else
			contentStream.showText(name.toUpperCase());
		contentStream.setLeading(italics.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * shortDescSize);
		contentStream.setFont(italics, shortDescSize);
		WrappingText shortDesc = new WrappingText(this.shortDesc, italics, PDRectangle.LETTER.getWidth() - 2 * border, shortDescSize);
		if (shortDesc.getLines().size() > 2)
			System.out.println("The short description for " + name + " is too long!");
		else 
			for (String s : shortDesc.getLines()) {
				contentStream.newLine();
				contentStream.showText(s);
			}
		contentStream.setLeading(2 * normal.getFontDescriptor().getCapHeight() / 1000 * titleNameSize - 
				italics.getFontDescriptor().getDescent() / 1000 * shortDescSize);
		contentStream.newLine();
		contentStream.setFont(normal, titleNameSize);
		contentStream.showText(titleName.toUpperCase());
		contentStream.newLineAtOffset(335, 0);
		contentStream.showText("CR " + cr);
		contentStream.newLineAtOffset(-335, -bold.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * titleNameSize);
	}
	private void writeNPC() throws IOException {
		if (!race.equals("")) {
			String text = race + " ";
			for (int i = 0; i < classes.length; i++) {
				if (i > 0) 
					text += "/";
				Pair<String, Integer> p = classes[i];
				text += p.getX() + " " + p.getY();
			}
			printALine(text);
		}
	}
	private void writeType() throws IOException {
		String text = alignment.name() + " " + size.toString() + " " + type.name().toLowerCase().replace('_', ' ');
		if (subtypes.length > 0) {
			text += " (";
			for (int i = 0; i < subtypes.length; i++) {
				if (i > 0) 
					text += ", ";
				text += subtypes[i];
			}
			text += ")";
		}
		printALine(text);
	}
	private void writeSenses() throws IOException {
		String text = "#HInit ";
		text += (init > -1 ? "+" : "" )+ init + "; ";
		if (senses.length > 0) {
			text += "#HSenses ";
			for (int i = 0; i < senses.length; i++) {
				if (i > 0) 
					text += ", ";
				text += senses[i];
			}
			text += "; ";
		}
		text += "Perception " + (perception > -1 ? "+" : "" ) + perception;
		printALine(text);
	}
	private void writeAuras() throws IOException {
		if (auras.length > 0) {
			String text = "#HAura ";
			for (int i = 0; i < auras.length; i++) {
				if (i > 0) 
					text += ", ";
				text += auras[i];
			}
			printALine(text);
		}
	}
	private void drawPictures(PDDocument pdoc) {
		try {
			WrappingText shortDesc = new WrappingText(this.shortDesc, italics, PDRectangle.LETTER.getWidth() - 2 * border, shortDescSize);
			if (shortDesc.getLines().size() > 1)
				shortDescLong = true;
			contentStream.drawImage(PDImageXObject.createFromFile(type.getPic() , pdoc), 454, shortDescLong ? 630 : 650);
			contentStream.drawImage(PDImageXObject.createFromFile(terrain.getPic() , pdoc), 486, shortDescLong ? 630 : 650);
			contentStream.drawImage(PDImageXObject.createFromFile(climate.getPic() , pdoc), 518, shortDescLong ? 630 : 650);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("->The images at the top right didn't print because they don't exist. You can add them yourself if you'd like, as described at https://github.com/bjpaupor/rpg-utility/tree/master.");
			return;
		}
	}
	/**
	 * Prints every creature within the given folder
	 * @param folderPath - folder containing creature files
	 * @throws IOException
	 */
	public static void printSet(String folderPath) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
			paths
			.filter(Files::isRegularFile)
			.forEach(s -> new Creature(s.toString()).printToPdf()); 
		} 
	}
	/**
	 * Prints all creatures within folders given by args
	 * @param args - folders containing creature files
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		printSet("src/CreatureFiles/Hollow'sLastHope/");
		printSet("src/CreatureFiles/Bestiary1/");
		printSet("src/CreatureFiles/");
		for (String s : args)
			printSet(s);
		Creature d = new Creature("src/CreatureFiles/StrangeAeonsPC's/DrErasmusKhan.creature");
		d.printToPdf();
		d.saveToFile();
		Creature c = new Creature("src/CreatureFiles/StrangeAeonsPC's/VeruccLellinstein.creature");
		c.printToPdf();
		c.saveToFile();
		Creature b = new Creature("src/CreatureFiles/StrangeAeonsPC's/UrorSiegfried.creature");
		b.printToPdf();
		b.saveToFile();
		Creature a = new Creature("src/CreatureFiles/StrangeAeonsPC's/LeonGrylls.creature");
		a.printToPdf();
		a.saveToFile();
		Creature m = new Creature("src/CreatureFiles/Monty.creature");
		m.printToPdf();
		m.saveToFile();
	}
}
