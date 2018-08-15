package pathfinder;

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
	private boolean shortDescLong;
	
	//Top
	private String name;
	private String shortDesc;
	private String titleName;
	private String cr;
	private CreatureType type;
	private Terrain terrain; //OPT
	private Climate climate; //OPT
	
	//Header
	private String gender; //OPT
	private String race; //OPT Race
	private Pair<String, Integer>[] classes; //OPT <class, level> Classes
	private Alignment alignment;
	private Size size;
	private String[] subtypes; //OPT CreatureSubTypes
	private int init;
	private String[] senses; //OPT Features
	private int perception;
	private String[] auras; //OPT Features

	//Defense
	private int ac;
	private int touch;
	private int flatFooted;
	private	Pair<Integer, String>[] acModifiers; //OPT <modifier, type>
	private	Pair<Integer, String>[] conditionalACModifiers; //OPT <modifier, type>
	private int hp;
	private String hpBreakdown; //(xdy+z)
	private int fastHealing; //OPT
	private Pair<Integer, String[]> regeneration; //OPT <amount, bypass[]>
	private int fort;
	private int ref;
	private int will;
	private Pair<Integer, String>[] saveModifiers;  //OPT <modifier, Type and condition>
	private String[] defensiveAbilities;  //OPT Features
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
	private Pair<Integer[], String> psychicMagic; //OPT <[cl, concentration], spells>
	private Trio<String[], Integer[], String>[] spellLikeAbilities; //OPT <[use-spells], [cl, concentration], Source>
	private Trio<Integer[], String[], String[]>[] spellsKnown; //OPT <[cl, concentration], [use-spells], [className, special, special ...]>
	private Trio<Integer[], String[], String[]>[] spellsPrepared; //OPT <[cl, concentration], [use-spells], [className, special, special ...]>

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
	private float nameSize = 35, shortDescSize = 18, titleNameSize = 18, border = .9f * 69, 
			normalText = 9.5f, tinyText = 4.5f, largeText = 12;
	private PDFont italics = PDType1Font.TIMES_ITALIC, bold = PDType1Font.TIMES_BOLD, normal = PDType1Font.TIMES_ROMAN;
	private boolean firstPage = true;
	
	@SuppressWarnings("unchecked")
	public Creature(String fileName) {
		if (!fileName.endsWith(".creature")) {
			System.out.println("-> Incorrect file type, file must be .creature\n");
			return;
		}
		try (BufferedReader read = new BufferedReader(new FileReader(fileName))){
			creatureFileLocation = fileName;
			name = Tools.readALine(read);
			shortDesc = Tools.readALine(read);
			titleName = Tools.readALine(read);
			cr = Tools.readALine(read);
			type = CreatureType.valueOf(Tools.readALine(read).toUpperCase());
			String line = Tools.readALine(read);
			if (line.equals(""))
				terrain = null;
			else
				terrain = Terrain.valueOf(line.toUpperCase());
			line = Tools.readALine(read);
			if (line.equals(""))
				climate = null;
			else
				climate = Climate.valueOf(line.toUpperCase());
			gender = Tools.readALine(read);
			race = Tools.readALine(read);
			classes = new Pair[Integer.parseInt(Tools.readALine(read))];
			for(int i = 0; i < classes.length; i++) {
				String className = Tools.readALine(read);
				Integer level = Integer.parseInt(Tools.readALine(read));
				classes[i] = new Pair<String, Integer>(className, level);
			}
			alignment = Alignment.valueOf(Tools.readALine(read).toUpperCase());
			size = Size.valueOf(Tools.readALine(read).toUpperCase());
			subtypes = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < subtypes.length; i++)
				subtypes[i] = Tools.readALine(read);
			init = Integer.parseInt(Tools.readALine(read));
			senses = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < senses.length; i++) 
				senses[i] = Tools.readALine(read);
			perception = Integer.parseInt(Tools.readALine(read));
			auras = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < auras.length; i++) {
				auras[i] = Tools.readALine(read);
			}
			ac = 10;
			touch = 10;
			flatFooted = 10;
			acModifiers = new Pair[Integer.parseInt(Tools.readALine(read))];
			for(int i = 0; i < acModifiers.length; i++) {
				Integer modifier = Integer.parseInt(Tools.readALine(read));
				String modifierType = Tools.readALine(read);
				ac += modifier;
				if (!(modifierType.equals("armor") || modifierType.equals("shield") || modifierType.equals("natural")))
					touch += modifier;
				if (!(modifierType.equals("Dex") || modifierType.equals("dodge")))
					flatFooted += modifier;
				acModifiers[i] = new Pair<Integer, String>(modifier, modifierType);
			}
			conditionalACModifiers = new Pair[Integer.parseInt(Tools.readALine(read))];
			for(int i = 0; i < conditionalACModifiers.length; i++) {
				Integer modifier = Integer.parseInt(Tools.readALine(read));
				String modifierTypeAndCondition = Tools.readALine(read);
				conditionalACModifiers[i] = new Pair<Integer, String>(modifier, modifierTypeAndCondition);
			}
			hp = Integer.parseInt(Tools.readALine(read));
			hpBreakdown = Tools.readALine(read);
			fastHealing = Integer.parseInt(Tools.readALine(read));
			Integer value = Integer.parseInt(Tools.readALine(read));
			if(value > 0) {
				String[] bypass = new String[Integer.parseInt(Tools.readALine(read))];
				for(int i = 0; i < bypass.length; i++) 
					bypass[i] = Tools.readALine(read);
				regeneration = new Pair<Integer, String[]>(value, bypass);
			}
			fort = Integer.parseInt(Tools.readALine(read));
			ref = Integer.parseInt(Tools.readALine(read));
			will = Integer.parseInt(Tools.readALine(read));
			saveModifiers = new Pair[Integer.parseInt(Tools.readALine(read))];
			for(int i = 0; i < saveModifiers.length; i++) {
				Integer modifier = Integer.parseInt(Tools.readALine(read));
				String modifierTypeAndCondition = Tools.readALine(read);
				saveModifiers[i] = new Pair<Integer, String>(modifier, modifierTypeAndCondition);
			}

			defensiveAbilities = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < defensiveAbilities.length; i++)
				defensiveAbilities[i] = Tools.readALine(read);
			value = Integer.parseInt(Tools.readALine(read));
			if(value > 0) {
				String bypass = Tools.readALine(read);
				dr = new Pair<Integer, String>(value, bypass);
			}
			immunities = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < immunities.length; i++)
				immunities[i] = Tools.readALine(read);
			resistances = new Pair[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < resistances.length; i++) {
				String type = Tools.readALine(read);
				Integer resistance = Integer.parseInt(Tools.readALine(read));
				resistances[i] = new Pair<String, Integer>(type, resistance);
			}
			sr = Integer.parseInt(Tools.readALine(read));
			weaknesses = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < weaknesses.length; i++)
				weaknesses[i] = Tools.readALine(read);
			speed = Integer.parseInt(Tools.readALine(read));
			speeds = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < speeds.length; i++)
				speeds[i] = Tools.readALine(read);
			melee = Tools.readALine(read);
			ranged = Tools.readALine(read);
			space = Tools.readALine(read);
			reach = Integer.parseInt(Tools.readALine(read));
			specialAttacks = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < specialAttacks.length; i++)
				specialAttacks[i] = Tools.readALine(read);
			psychicMagic = new Pair<Integer[], String>(new Integer[0], "");
			{ 
				int temp = Integer.parseInt(Tools.readALine(read));
				if (temp > 0) {
					Integer[] PSYIntTemp = new Integer[2];
					PSYIntTemp[0] = temp;
					PSYIntTemp[1] = Integer.parseInt(Tools.readALine(read));
					psychicMagic.setX(PSYIntTemp);
					psychicMagic.setY(Tools.readALine(read));
				}
			}
			spellLikeAbilities = new Trio[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < spellLikeAbilities.length; i++) {
				spellLikeAbilities[i] = new Trio<String[], Integer[], String>(new String[0], new Integer[0], "");
				String[] slaTemp = new String[Integer.parseInt(Tools.readALine(read))];
				String source = Tools.readALine(read);
				Integer[] slaIntTemp = new Integer[2];
				slaIntTemp[0] = Integer.parseInt(Tools.readALine(read));
				slaIntTemp[1] = Integer.parseInt(Tools.readALine(read));
				spellLikeAbilities[i].setY(slaIntTemp);
				for (int j = 0; j < slaTemp.length; j++)
					slaTemp[j] = Tools.readALine(read);
				spellLikeAbilities[i].setX(slaTemp);
				spellLikeAbilities[i].setZ(source);
			}
			spellsKnown = new Trio[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < spellsKnown.length; i++) {
				spellsKnown[i] = new Trio<Integer[], String[], String[]>(new Integer[0], new String[0], new String[0]);
				String[] spellsKnownTemp = new String[Integer.parseInt(Tools.readALine(read))];
				Integer[] spellsKnownInt = new Integer[2];
				String name = Tools.readALine(read);
				spellsKnownInt[0] = Integer.parseInt(Tools.readALine(read));
				spellsKnownInt[1] = Integer.parseInt(Tools.readALine(read));
				spellsKnown[i].setX(spellsKnownInt);
				String[] spellsKnownSpecial = new String[Integer.parseInt(Tools.readALine(read)) + 1];
				spellsKnownSpecial[0] = name;
				for (int j = 1; j < spellsKnownSpecial.length; j++) 
					spellsKnownSpecial[j] = Tools.readALine(read);
				for (int j = 0; j < spellsKnownTemp.length; j++)
					spellsKnownTemp[j] = Tools.readALine(read);
				spellsKnown[i].setY(spellsKnownTemp);
				spellsKnown[i].setZ(spellsKnownSpecial);
			}
			spellsPrepared = new Trio[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < spellsPrepared.length; i++) {
				spellsPrepared[i] = new Trio<Integer[], String[], String[]>(new Integer[0], new String[0], new String[0]);
				String[] spellsPreparedTemp = new String[Integer.parseInt(Tools.readALine(read))];
				Integer[] spellsPreparedInt = new Integer[2];
				String name = Tools.readALine(read);
				spellsPreparedInt[0] = Integer.parseInt(Tools.readALine(read));
				spellsPreparedInt[1] = Integer.parseInt(Tools.readALine(read));
				spellsPrepared[i].setX(spellsPreparedInt);
				String[] spellsPreparedSpecial = new String[Integer.parseInt(Tools.readALine(read)) + 1];
				spellsPreparedSpecial[0] = name;
				for (int j = 1; j < spellsPreparedSpecial.length; j++) 
					spellsPreparedSpecial[j] = Tools.readALine(read);
				for (int j = 0; j < spellsPreparedTemp.length; j++)
					spellsPreparedTemp[j] = Tools.readALine(read);
				spellsPrepared[i].setY(spellsPreparedTemp);
				spellsPrepared[i].setZ(spellsPreparedSpecial);
			}
			beforeCombat = Tools.readALine(read);
			duringCombat = Tools.readALine(read);
			morale = Tools.readALine(read);
			baseStatistics = Tools.readALine(read);
			str = Integer.parseInt(Tools.readALine(read));
			dex = Integer.parseInt(Tools.readALine(read));
			con = Integer.parseInt(Tools.readALine(read));
			intelligence = Integer.parseInt(Tools.readALine(read));
			wis = Integer.parseInt(Tools.readALine(read));
			cha = Integer.parseInt(Tools.readALine(read));
			bab = Integer.parseInt(Tools.readALine(read));
			cmb = Integer.parseInt(Tools.readALine(read));
			cmbs = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < cmbs.length; i++) 
				cmbs[i] = Tools.readALine(read);
			cmd = Integer.parseInt(Tools.readALine(read));
			cmds = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < cmds.length; i++) 
				cmds[i] = Tools.readALine(read);
			feats = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < feats.length; i++)
				feats[i] = Tools.readALine(read);
			skills = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < skills.length; i++)
				skills[i] = Tools.readALine(read);
			racialModifiers = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < racialModifiers.length; i++)
				racialModifiers[i] = Tools.readALine(read);
			languages = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < languages.length; i++)
				languages[i] = Tools.readALine(read);
			languagesSpecial = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < languagesSpecial.length; i++)
				languagesSpecial[i] = Tools.readALine(read);
			sq = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < sq.length; i++)
				sq[i] = Tools.readALine(read);
			combatGear = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < combatGear.length; i++)
				combatGear[i] = Tools.readALine(read);
			otherGear = new String[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < otherGear.length; i++)
				otherGear[i] = Tools.readALine(read);
			environment = Tools.readALine(read);
			organization = Tools.readALine(read);
			treasure = Tools.readALine(read);
			specialAbilities = new Pair[Integer.parseInt(Tools.readALine(read))];
			for (int i = 0; i < specialAbilities.length; i++) {
				String bold = Tools.readALine(read);
				String text = Tools.readALine(read);
				String[] subDescriptions = new String[Integer.parseInt(Tools.readALine(read))];
				for (int j = 0; j < subDescriptions.length; j++) 
					subDescriptions[j] = Tools.readALine(read);
				Pair<String, String[]> description = new Pair<String, String[]>(text, subDescriptions);
				specialAbilities[i] = new Pair<String, Pair<String, String[]>>(bold, description);
			}
			description = Tools.readALine(read);
		} catch (Exception ex) {
			System.out.println("->" + name + ": Failed to interpret creature file!");
			ex.printStackTrace();
			return;
		}
	}
	//Obliterates comments
	public void saveToFile() throws IOException {
		BufferedWriter file = new BufferedWriter(new FileWriter(creatureFileLocation));
		Tools.writeALine(file, "Name: " + name);
		Tools.writeALine(file, "Short Description: " + shortDesc);
		Tools.writeALine(file, "Title Name: " + titleName);
		Tools.writeALine(file, "CR: " + cr);
		Tools.writeALine(file, "Type: " + type);
		Tools.writeALine(file, "Terrain: " + (terrain == null ? "" : terrain));
		Tools.writeALine(file, "Climate: " + (climate == null ? "" : climate));
		Tools.writeALine(file, "Gender: " + gender);
		Tools.writeALine(file, "Race: " + race);
		Tools.writeALine(file, "Number of classes with levels (First is favored): " + classes.length);
		for (Pair<String, Integer> p :  classes) {
			Tools.writeALine(file, "Class: " + p.getX());
			Tools.writeALine(file, "Levels in above: " + p.getY());
		}
		Tools.writeALine(file, "Alignment: " + alignment);
		Tools.writeALine(file, "Size: " + size);
		Tools.writeALine(file, "Number of Subtypes: " + subtypes.length);
		for (String s :  subtypes)
			Tools.writeALine(file, "Subtype: " + s);
		Tools.writeALine(file, "Init: " + init);
		Tools.writeALine(file, "Number of senses: " + senses.length);
		for (String s :  senses)
			Tools.writeALine(file, "Sense: " + s);
		Tools.writeALine(file, "Perception: " + perception);
		Tools.writeALine(file, "Number of auras: " + auras.length);
		for (String s :  auras)
			Tools.writeALine(file, "Aura: " + s);
		Tools.writeALine(file, "Number of AC Modifiers: " + acModifiers.length);
		for (Pair<Integer, String> p :  acModifiers) {
			Tools.writeALine(file, "Modifier: " + p.getX());
			Tools.writeALine(file, "Type of above Modifier: " + p.getY());
		}
		Tools.writeALine(file, "Number of Conditional AC Modifiers: " + conditionalACModifiers.length);
		for (Pair<Integer, String> p :  conditionalACModifiers) {
			Tools.writeALine(file, "Modifier: " + p.getX());
			Tools.writeALine(file, "Type of above Modifier: " + p.getY());
		}
		Tools.writeALine(file, "HP: " + hp);
		Tools.writeALine(file, "HP breakdown: " + hpBreakdown);
		Tools.writeALine(file, "Fast Healing: " + fastHealing);
		if (regeneration != null) {
			Tools.writeALine(file, "Regeneration: " + regeneration.getX());
			Tools.writeALine(file, "Number of Bypasses: " + regeneration.getY().length);
			for (String s : regeneration.getY())
				Tools.writeALine(file, "Bypass: " + s);
		}
		else
			Tools.writeALine(file, "Regeneration: 0");
		Tools.writeALine(file, "Fort: " + fort);
		Tools.writeALine(file, "Ref: " + ref);
		Tools.writeALine(file, "Will: " + will);
		Tools.writeALine(file, "Number of conditional save modifiers: " + saveModifiers.length);
		for (Pair<Integer, String> p :  saveModifiers) {
			Tools.writeALine(file, "Modifier: " + p.getX());
			Tools.writeALine(file, "Condition/Type of above Modifier: " + p.getY());
		}
		Tools.writeALine(file, "Number of Defensive Abilities: " + defensiveAbilities.length);
		for (String s :  defensiveAbilities)
			Tools.writeALine(file, "Defensive Ability: " + s);
		if (dr != null) {
			Tools.writeALine(file, "DR: " + dr.getX());
			Tools.writeALine(file, "Bypass: " + dr.getY());
		}
		else
			Tools.writeALine(file, "DR: 0");
		Tools.writeALine(file, "Number of Immunities: " + immunities.length);
		for (String s :  immunities)
			Tools.writeALine(file, "Immunity: " + s);
		Tools.writeALine(file, "Number of Resistances: " + resistances.length);
		for (Pair<String, Integer> p :  resistances) {
			Tools.writeALine(file, "Resistance Type: " + p.getX());
			Tools.writeALine(file, "Resistance Amount: " + p.getY());
		}
		Tools.writeALine(file, "SR: " + sr);
		Tools.writeALine(file, "Number of Weaknesses: " + weaknesses.length);
		for (String s :  weaknesses)
			Tools.writeALine(file, "Weakness: " + s);
		Tools.writeALine(file, "Speed: " + speed);
		Tools.writeALine(file, "Number of other speeds (#B means base speed): " + speeds.length);
		for (String s :  speeds) 
			Tools.writeALine(file, "Other Speed: " + s);
		Tools.writeALine(file, "Melee: " + melee);
		Tools.writeALine(file, "Ranged: " + ranged);
		Tools.writeALine(file, "Space: " + space);
		Tools.writeALine(file, "Reach: " + reach);
		Tools.writeALine(file, "Number of Special Attacks: " + specialAttacks.length);
		for (String s :  specialAttacks)
			Tools.writeALine(file, "Special Attack: " + s);
		if (psychicMagic.getX().length > 0) {
			Tools.writeALine(file, "Psychic Magic CL: " + psychicMagic.getX()[0]);
			Tools.writeALine(file, "Psychic Magic Concentration: " + psychicMagic.getX()[1]);
			Tools.writeALine(file, "Psychic Magic Spells: " + psychicMagic.getY());
		}
		else
			Tools.writeALine(file, "Psychic Magic CL: " + 0);
		//spellLikeAbilities <[use-spells], [cl, concentration]>
		Tools.writeALine(file, "SLA Sources: " + spellLikeAbilities.length);
		for (int i = 0; i < spellLikeAbilities.length; i++) {
			Tools.writeALine(file, "SLA Use Categories (1/day, etc.): " + spellLikeAbilities[i].getX().length);
			Tools.writeALine(file, "SLA Source: " + spellLikeAbilities[i].getZ());
			Tools.writeALine(file, "SLA CL: " + spellLikeAbilities[i].getY()[0]);
			Tools.writeALine(file, "SLA Concentration: " + spellLikeAbilities[i].getY()[1]);
			for (String s :  spellLikeAbilities[i].getX())
				Tools.writeALine(file, "SLA Use Category and Spells: " + s);
		}
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		Tools.writeALine(file, "Spells Known Levels: " + spellsKnown.length);
		for (int i = 0; i < spellsKnown.length; i++) {
			Tools.writeALine(file, "Spells Known Levels: " + spellsKnown[i].getY().length);
			Tools.writeALine(file, "Spells Known Class: " + spellsKnown[i].getZ()[0]);
			Tools.writeALine(file, "Spells Known CL: " + spellsKnown[i].getX()[0]);
			Tools.writeALine(file, "Spells Known Concentration: " + spellsKnown[i].getX()[1]);
			Tools.writeALine(file, "Number of Bolded Sections: " + (spellsKnown[i].getZ().length - 1));
			for (int j = 1; j < spellsKnown[i].getZ().length; j++)
				Tools.writeALine(file, "Bold Section: " + spellsKnown[i].getZ()[j]);
			for (String s :  spellsKnown[i].getY())
				Tools.writeALine(file, "Spells Known and Use Level: " + s);
		}
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		Tools.writeALine(file, "Spells Prepared Levels: " + spellsPrepared.length);
		for (int i = 0; i < spellsPrepared.length; i++) {
			Tools.writeALine(file, "Spells Prepared Levels: " + spellsPrepared[i].getY().length);
			Tools.writeALine(file, "Spells Prepared Class: " + spellsPrepared[i].getZ()[0]);
			Tools.writeALine(file, "Spells Prepared CL: " + spellsPrepared[i].getX()[0]);
			Tools.writeALine(file, "Spells Prepared Concentration: " + spellsPrepared[i].getX()[1]);
			Tools.writeALine(file, "Number of Bolded Sections: " + (spellsPrepared[i].getZ().length - 1));
			for (int j = 1; j < spellsPrepared[i].getZ().length; j++)
				Tools.writeALine(file, "Bold Section: " + spellsPrepared[i].getZ()[j]);				
			for (String s :  spellsPrepared[i].getY())
				Tools.writeALine(file, "Spells Prepared at Level: " + s);
		}
		Tools.writeALine(file, "Before Combat: " + beforeCombat);
		Tools.writeALine(file, "During Combat: " + duringCombat);
		Tools.writeALine(file, "Morale: " + morale);
		Tools.writeALine(file, "Base Statistics: " + baseStatistics);
		Tools.writeALine(file, "Str: " + str);
		Tools.writeALine(file, "Dex: " + dex);
		Tools.writeALine(file, "Con: " + con);
		Tools.writeALine(file, "Int: " + intelligence);
		Tools.writeALine(file, "Wis: " + wis);
		Tools.writeALine(file, "Cha: " + cha);
		Tools.writeALine(file, "BAB: " + bab);
		Tools.writeALine(file, "CMB: " + cmb);
		Tools.writeALine(file, "Number of Special CMBs: " + cmbs.length);
		for (String s :  cmbs)
			Tools.writeALine(file, "Special CMB: " + s);
		Tools.writeALine(file, "CMD: " + cmd);
		Tools.writeALine(file, "Number of Special CMDs: " + cmds.length);
		for (String s :  cmds)
			Tools.writeALine(file, "Special CMD: " + s);
		Tools.writeALine(file, "Number of Feats: " + feats.length);
		for (String s :  feats)
			Tools.writeALine(file, "Feat: " + s);
		Tools.writeALine(file, "Number of Skills: " + skills.length);
		for (String s :  skills)
			Tools.writeALine(file, "Skill: " + s);
		Tools.writeALine(file, "Number of Racial Modifiers: " + racialModifiers.length);
		for (String s :  racialModifiers)
			Tools.writeALine(file, "Racial Modifier: " + s);
		Tools.writeALine(file, "Number of Languages: " + languages.length);
		for (String s :  languages)
			Tools.writeALine(file, "Language: " + s);
		Tools.writeALine(file, "Number of Special Languages: " + languagesSpecial.length);
		for (String s :  languagesSpecial)
			Tools.writeALine(file, "Special Language: " + s);
		Tools.writeALine(file, "Number of Special Qualities: " + sq.length);
		for (String s :  sq)
			Tools.writeALine(file, "SQ: " + s);
		Tools.writeALine(file, "Number of Combat Gear: " + combatGear.length);
		for (String s :  combatGear)
			Tools.writeALine(file, "Combat Gear: " + s);
		Tools.writeALine(file, "Number of Other Gear: " + otherGear.length);
		for (String s :  otherGear)
			Tools.writeALine(file, "Other Gear: " + s);
		Tools.writeALine(file, "Environment: " + environment);
		Tools.writeALine(file, "Organization: " + organization);
		Tools.writeALine(file, "Treasure: " + treasure);
		Tools.writeALine(file, "Number of Special Abilities: " + specialAbilities.length);
		//OPT <Ability Name <Ability Description, Ability Sub-Description>>
		for (Pair<String, Pair<String, String[]>> p :  specialAbilities) {
			Tools.writeALine(file, "Ability Name: " + p.getX());
			Tools.writeALine(file, "Ability Text: " + p.getY().getX());
			Tools.writeALine(file, "Number of Sub-Descriptions: " + p.getY().getY().length);
			for (String s :  p.getY().getY())
				Tools.writeALine(file, "Sub-Description: " + s);
		}
		Tools.writeALine(file, "Description: " + description);
		file.close();
	}
	//Obliterates comments
	public void saveToFileCompressed() throws IOException {
		BufferedWriter file = new BufferedWriter(new FileWriter(creatureFileLocation));
		Tools.writeALine(file, ":" + name);
		Tools.writeALine(file, ":" + shortDesc);
		Tools.writeALine(file, ":" + titleName);
		Tools.writeALine(file, ":" + cr);
		Tools.writeALine(file, ":" + type);
		Tools.writeALine(file, ":" + (terrain == null ? "" : terrain));
		Tools.writeALine(file, ":" + (climate == null ? "" : climate));
		Tools.writeALine(file, ":" + gender);
		Tools.writeALine(file, ":" + race);
		Tools.writeALine(file, ":" + classes.length);
		for (Pair<String, Integer> p : classes) {
			Tools.writeALine(file, ":" + p.getX());
			Tools.writeALine(file, ":" + p.getY());
		}
		Tools.writeALine(file, ":" + alignment);
		Tools.writeALine(file, ":" + size);
		Tools.writeALine(file, ":" + subtypes.length);
		for (String s : subtypes)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + init);
		Tools.writeALine(file, ":" + senses.length);
		for (String s : senses)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + perception);
		Tools.writeALine(file, ":" + auras.length);
		for (String s : auras)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + acModifiers.length);
		for (Pair<Integer, String> p : acModifiers) {
			Tools.writeALine(file, ":" + p.getX());
			Tools.writeALine(file, ":" + p.getY());
		}
		Tools.writeALine(file, ":" + conditionalACModifiers.length);
		for (Pair<Integer, String> p : conditionalACModifiers) {
			Tools.writeALine(file, ":" + p.getX());
			Tools.writeALine(file, ":" + p.getY());
		}
		Tools.writeALine(file, ":" + hp);
		Tools.writeALine(file, ":" + hpBreakdown);
		Tools.writeALine(file, ":" + fastHealing);
		if (regeneration != null) {
			Tools.writeALine(file, ":" + regeneration.getX());
			Tools.writeALine(file, ":" + regeneration.getY().length);
			for (String s : regeneration.getY())
				Tools.writeALine(file, ":" + s);
		}
		else
			Tools.writeALine(file, ":0");
		Tools.writeALine(file, ":" + fort);
		Tools.writeALine(file, ":" + ref);
		Tools.writeALine(file, ":" + will);
		Tools.writeALine(file, ":" + saveModifiers.length);
		for (Pair<Integer, String> p : saveModifiers) {
			Tools.writeALine(file, ":" + p.getX());
			Tools.writeALine(file, ":" + p.getY());
		}
		Tools.writeALine(file, ":" + defensiveAbilities.length);
		for (String s : defensiveAbilities)
			Tools.writeALine(file, ":" + s);
		if (dr != null) {
			Tools.writeALine(file, ":" + dr.getX());
			Tools.writeALine(file, ":" + dr.getY());
		}
		else
			Tools.writeALine(file, ":0");
		Tools.writeALine(file, ":" + immunities.length);
		for (String s : immunities)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + resistances.length);
		for (Pair<String, Integer> p : resistances) {
			Tools.writeALine(file, ":" + p.getX());
			Tools.writeALine(file, ":" + p.getY());
		}
		Tools.writeALine(file, ":" + sr);
		Tools.writeALine(file, ":" + weaknesses.length);
		for (String s : weaknesses)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + speed);
		Tools.writeALine(file, ":" + speeds.length);
		for (String s : speeds) 
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + melee);
		Tools.writeALine(file, ":" + ranged);
		Tools.writeALine(file, ":" + space);
		Tools.writeALine(file, ":" + reach);
		Tools.writeALine(file, ":" + specialAttacks.length);
		for (String s : specialAttacks)
			Tools.writeALine(file, ":" + s);
		if (psychicMagic.getX().length > 0) {
			Tools.writeALine(file, ":" + psychicMagic.getX()[0]);
			Tools.writeALine(file, ":" + psychicMagic.getX()[1]);
			Tools.writeALine(file, ":" + psychicMagic.getY());
		}
		else
			Tools.writeALine(file, ":" + 0);
		//spellLikeAbilities <[use-spells], [cl, concentration]>
		Tools.writeALine(file, ":" + spellLikeAbilities.length);
		for (int i = 0; i < spellLikeAbilities.length; i++) {
			Tools.writeALine(file, ":" + spellLikeAbilities[i].getX().length);
			Tools.writeALine(file, ":" + spellLikeAbilities[i].getZ());
			Tools.writeALine(file, ":" + spellLikeAbilities[i].getY()[0]);
			Tools.writeALine(file, ":" + spellLikeAbilities[i].getY()[1]);
			for (String s :  spellLikeAbilities[i].getX())
				Tools.writeALine(file, ":" + s);
		}
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		Tools.writeALine(file, ":" + spellsKnown.length);
		for (int i = 0; i < spellsKnown.length; i++) {
			Tools.writeALine(file, ":" + spellsKnown[i].getY().length);
			Tools.writeALine(file, ":" + spellsKnown[i].getZ()[0]);
			Tools.writeALine(file, ":" + spellsKnown[i].getX()[0]);
			Tools.writeALine(file, ":" + spellsKnown[i].getX()[1]);
			Tools.writeALine(file, ":" + (spellsKnown[i].getZ().length - 1));
			for (int j = 1; j < spellsKnown[i].getZ().length; j++)
				Tools.writeALine(file, ":" + spellsKnown[i].getZ()[j]);
			for (String s :  spellsKnown[i].getY())
				Tools.writeALine(file, ":" + s);
		}
		//OPT <[cl, concentration], [use-spells], [className, special, special ...]>
		Tools.writeALine(file, ":" + spellsPrepared.length);
		for (int i = 0; i < spellsPrepared.length; i++) {
			Tools.writeALine(file, ":" + spellsPrepared[i].getY().length);
			Tools.writeALine(file, ":" + spellsPrepared[i].getZ()[0]);
			Tools.writeALine(file, ":" + spellsPrepared[i].getX()[0]);
			Tools.writeALine(file, ":" + spellsPrepared[i].getX()[1]);
			Tools.writeALine(file, ":" + (spellsPrepared[i].getZ().length - 1));
			for (int j = 1; j < spellsPrepared[i].getZ().length; j++)
				Tools.writeALine(file, ":" + spellsPrepared[i].getZ()[j]);				
			for (String s :  spellsPrepared[i].getY())
				Tools.writeALine(file, ":" + s);
		}
		Tools.writeALine(file, ":" + beforeCombat);
		Tools.writeALine(file, ":" + duringCombat);
		Tools.writeALine(file, ":" + morale);
		Tools.writeALine(file, ":" + baseStatistics);
		Tools.writeALine(file, ":" + str);
		Tools.writeALine(file, ":" + dex);
		Tools.writeALine(file, ":" + con);
		Tools.writeALine(file, ":" + intelligence);
		Tools.writeALine(file, ":" + wis);
		Tools.writeALine(file, ":" + cha);
		Tools.writeALine(file, ":" + bab);
		Tools.writeALine(file, ":" + cmb);
		Tools.writeALine(file, ":" + cmbs.length);
		for (String s : cmbs)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + cmd);
		Tools.writeALine(file, ":" + cmds.length);
		for (String s : cmds)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + feats.length);
		for (String s : feats)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + skills.length);
		for (String s : skills)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + racialModifiers.length);
		for (String s : racialModifiers)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + languages.length);
		for (String s : languages)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + languagesSpecial.length);
		for (String s : languagesSpecial)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + sq.length);
		for (String s : sq)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + combatGear.length);
		for (String s : combatGear)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + otherGear.length);
		for (String s : otherGear)
			Tools.writeALine(file, ":" + s);
		Tools.writeALine(file, ":" + environment);
		Tools.writeALine(file, ":" + organization);
		Tools.writeALine(file, ":" + treasure);
		Tools.writeALine(file, ":" + specialAbilities.length);
		//OPT <Ability Name <Ability Description, Ability Sub-Description>>
		for (Pair<String, Pair<String, String[]>> p : specialAbilities) {
			Tools.writeALine(file, ":" + p.getX());
			Tools.writeALine(file, ":" + p.getY().getX());
			Tools.writeALine(file, ":" + p.getY().getY().length);
			for (String s : p.getY().getY())
				Tools.writeALine(file, ":" + s);
		}
		Tools.writeALine(file, ":" + description);
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
			else if (s.startsWith("#C")) {
				nextLine();
				printTable(s.substring(2, s.length()));
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
	private void printCentered(String s) throws IOException {
		WrappingText line = new WrappingText(s, PDRectangle.LETTER.getWidth() / 2 - .9f * 72,
				normalText);
		contentStream.newLineAtOffset(-line.getLineWidth() / 2, 0);
		showWithEffects(s);
		contentStream.newLineAtOffset(line.getLineWidth() / 2, 0);
	}
	private void printCenteredBold(String s) throws IOException {
		WrappingText line = new WrappingText(s, bold, PDRectangle.LETTER.getWidth() / 2 - .9f * 72,
				normalText);
		contentStream.newLineAtOffset(-line.getLineWidth() / 2, 0);
		printBold(s);
		contentStream.newLineAtOffset(line.getLineWidth() / 2, 0);
	}
	private void printTable(String s) throws IOException {
		int columns = Integer.parseInt(s.substring(0, s.indexOf("x")));
		int rows = Integer.parseInt(s.substring(s.indexOf("x") + 1, s.indexOf("#")));
		WrappingText[][] entries = new WrappingText[columns][rows + 1];
		float[] widths = new float[columns];
		int[] heights = new int[rows + 1];
		float[] spaces = new float[columns];
		int index = s.indexOf("#") + 2;
		float spaceWidth = normal.getSpaceWidth() / 1000 * normalText;
		float lineHeight = normal.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * normalText;
		//get Entries
		for (int i = 0; i < rows + 1; i++)
			for (int j = 0; j < columns; j++) {
				if (s.indexOf("#E", index) != -1) {
					String ss = s.substring(index, s.indexOf("#E", index));
					index = s.indexOf("#E", index) + 3;
					entries[j][i] = new WrappingText(ss, i == 0 ? bold : normal,
							PDRectangle.LETTER.getWidth() / 2 - .9f * 72, normalText);
					int rowSize = entries[j][i].getStraightLines().size();
					if (heights[i] < rowSize)
						heights[i] = rowSize;
					if (widths[j] < entries[j][i].getLineWidth() + spaceWidth) {
						widths[j] = entries[j][i].getLineWidth() + spaceWidth;
						if (j > 0)
							spaces[j - 1] = entries[j][i].getLineWidth() / 2;
					}
				}
			}
		float totalWidth = 0;
		//find if Widths are ok
		for (float f : spaces)
			totalWidth += f;
		for (float f : widths)
			totalWidth += f;
		float adj = 1.5f;
		//fix widths if not ok
		while (totalWidth > PDRectangle.LETTER.getWidth() / 2 - .9f * 72) {
			int biggestIndex = 0;
			for (int i = 1; i < widths.length; i++)
				if (widths[i] > widths[biggestIndex]) 
					biggestIndex = i;
			spaces[biggestIndex] = 0;
			widths[biggestIndex] = 0;
			for (int i = 0; i < rows + 1; i++) {
				entries[biggestIndex][i] = new WrappingText(entries[biggestIndex][i].getText(),
						i == 0 ? bold : normal, entries[biggestIndex][i].getTotalLineWidth() / adj, normalText);
				int rowSize = entries[biggestIndex][i].getStraightLines().size();
				if (heights[i] < rowSize)
					heights[i] = rowSize;
				if (widths[biggestIndex] < entries[biggestIndex][i].getLineWidth() + spaceWidth) {
					widths[biggestIndex] = entries[biggestIndex][i].getLineWidth() + spaceWidth;
					if (biggestIndex > 0)
						spaces[biggestIndex - 1] = entries[biggestIndex][i].getLineWidth() / 2;
				}
			}
			totalWidth = 0;
			for (float f : spaces)
				totalWidth += f;
			for (float f : widths)
				totalWidth += f;
		}
		int k = 0;
		//fill out empty space
		while (totalWidth < PDRectangle.LETTER.getWidth() / 2 - .9f * 72 - spaceWidth) {
			totalWidth += spaceWidth;
			spaces[k++] += spaceWidth;
			if (k >= spaces.length)
				k = 0;
		}
		//don't start table with header on bottom
		if (nextLineTurns())
			nextLine();
		k = 0;
		//print the entries
		for (int i = 0; i < rows + 1; i++) {
			for (int j = 0; j < columns; j++) {
				if (i == 0) {
					if (j == 0)
						for (String ss : entries[j][i].getStraightLines()) {
							if (k++ != 0)
								contentStream.newLine();
							printBold(ss);
						}
					else 
						for (String ss : entries[j][i].getStraightLines()) {
							if (k++ != 0)
								contentStream.newLine();
							printCenteredBold(ss);
						}
				}
				else if (j == 0)
					for (String ss : entries[j][i].getStraightLines()) {
						if (k++ != 0)
							contentStream.newLine();
						showWithEffects(ss);
					}
				else 
					for (String ss : entries[j][i].getStraightLines()) {
						if (k++ != 0)
							contentStream.newLine();
						printCentered(ss);
					}
				contentStream.newLineAtOffset(widths[j] + spaces[j], (k - 1) * lineHeight);
				k = 0;
			}
			contentStream.newLineAtOffset(-totalWidth, 0);
			for (int j = 0; j < heights[i]; j++)
				nextLine();
		}			
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
	/**
	 * Returns if the next line doesn't just go down
	 * @return if the next line doesn't just go down
	 */
	private boolean nextLineTurns() {
		boolean result = false;
		if (lineNumber == 53 && shortDescLong && firstPage) 
			result = true;
		else if (lineNumber == 55 && !shortDescLong && firstPage) 
			result = true;
		else if (lineNumber == 63 && !firstPage)
			result = true;
		else if (lineNumber == 106 && shortDescLong && firstPage)
			result = true;
		else if (lineNumber == 110 && !shortDescLong && firstPage) 
			result = true;
		else if (lineNumber == 126 && !firstPage) 
			result = true;
		return result;
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
	
	public void printToPdf(String path) {
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
			String finalPath = path + creatureFileLocation.substring(creatureFileLocation.indexOf("/CreatureFiles/") 
					+ 15, creatureFileLocation.lastIndexOf("/") + 1);
			if (!Files.exists(Paths.get(finalPath)))
				new File(finalPath).mkdirs();
			pdoc.save(finalPath + name + ".pdf");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void writeSpecialAbilities() throws IOException {
		if (specialAbilities.length > 0) {
			printHeader("SPECIAL ABILITIES");
			for (int i = 0; i < specialAbilities.length; i++) {
				String text = Tools.embolden(specialAbilities[i].getX());
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
		for (int i = 0; i < spellsPrepared.length; i++) {
			String text = "";
			if (!spellsPrepared[i].getZ()[0].equals("none")) 
				text += "#H" + spellsPrepared[i].getZ()[0] + " #HSpells #HPrepared ";
			else
				text += "#HSpells #HPrepared ";
			int conc = spellsPrepared[i].getX()[1];
			text += "(CL " + Tools.getPlace(spellsPrepared[i].getX()[0]) + "; concentration " + 
					((conc > -1) ? ("+" + conc) : (conc))  + ')';
			printALine(text);
			for (int j = 0; j < spellsPrepared[i].getY().length; j++) 
				printAnIndentedLine(spellsPrepared[i].getY()[j]);
			text = "";
			for (int j = 1; j < spellsPrepared[i].getZ().length; j++) {
				text += "#H" + spellsPrepared[i].getZ()[j];
				if (j + 1 < spellsPrepared[i].getZ().length) 
					text += "; ";
			}
			if (!text.equals(""))
				printAnIndentedLine(text);
		}
	}
	private void writeSpellsKnown() throws IOException {
		for (int i = 0; i < spellsKnown.length; i++) {
			String text = "";
			if (!spellsKnown[i].getZ()[0].equals("none")) 
				text += "#H" + spellsKnown[i].getZ()[0] + " #HSpells #HKnown ";
			else
				text += "#HSpells #HKnown ";
			int conc = spellsKnown[i].getX()[1];
			text += "(CL " + Tools.getPlace(spellsKnown[i].getX()[0]) + "; concentration " + 
					((conc > -1) ? ("+" + conc) : (conc))  + ')';
			printALine(text);
			for (int j = 0; j < spellsKnown[i].getY().length; j++) 
				printAnIndentedLine(spellsKnown[i].getY()[j]);
			text = "";
			for (int j = 1; j < spellsKnown[i].getZ().length; j++) {
				text += "#H" + spellsKnown[i].getZ()[j];
				if (j + 1 < spellsKnown[i].getZ().length)
					text += "; ";
			}
			if (!text.equals(""))
				printAnIndentedLine(text);
		}
	}
	private void writeSLAs() throws IOException {
		for (int i = 0; i < spellLikeAbilities.length; i++) {
			int conc = spellLikeAbilities[i].getY()[1];
			String text = spellLikeAbilities[i].getZ().equals("none") ? "" : "#H" + spellLikeAbilities[i].getZ() + " ";
			text += "#HSpell-Like #HAbilities " + "(CL " + Tools.getPlace(spellLikeAbilities[i].getY()[0]) +
					"; concentration " + ((conc > -1) ? ("+" + conc) : (conc))  + ')';
			printALine(text);
			for (int j = 0; j < spellLikeAbilities[i].getX().length; j++) {
				text = spellLikeAbilities[i].getX()[j];
				printAnIndentedLine(text);
			}
		}
	}
	private void writePsychicMagic() throws IOException {
		if (psychicMagic.getX().length > 0) {
			int conc = psychicMagic.getX()[1];
			String text = "#HPsychic #HMagic " + "(CL " + Tools.getPlace(psychicMagic.getX()[0]) +
					"; concentration " + ((conc > -1) ? ("+" + conc) : (conc))  + ')';
			printALine(text);
			printAnIndentedLine(psychicMagic.getY());
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
		if (regeneration != null)  {
			text += "; " + "regeneration " + regeneration.getX() + " " + "(";
			for (int i = 0; i < regeneration.getY().length; i++) {
				if (i > 0)
					text += ", ";
				text += regeneration.getY()[i];
			}
			text += ")";
		}
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
		printBold("XP " + Tools.getXP(cr));
		lineNumber = 1;
		writeNPC();
		writeType();
		writeSenses();
		writeAuras();
	}
	private void writeHeader() throws IOException {
		contentStream.newLineAtOffset(border, PDRectangle.LETTER.getHeight() - border - bold.getFontDescriptor().getCapHeight() / 1000 * nameSize);
		contentStream.setFont(bold, nameSize);
		contentStream.showText(name.toUpperCase());
		contentStream.setLeading(italics.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * shortDescSize);
		contentStream.setFont(italics, shortDescSize);
		WrappingText shortDesc = new WrappingText(this.shortDesc, italics, PDRectangle.LETTER.getWidth() - 2 * border, shortDescSize);
		if (shortDesc.getStraightLines().size() > 2)
			System.out.println("->" + name + ": The short description for " + name + " is too long!");
		else 
			for (String s : shortDesc.getStraightLines()) {
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
			String text = gender + race + " ";
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
			if (shortDesc.getStraightLines().size() > 1)
				shortDescLong = true;
			contentStream.drawImage(PDImageXObject.createFromFile(type.getPic() , pdoc), 454, shortDescLong ? 630 : 650);
			if (terrain != null)
				contentStream.drawImage(PDImageXObject.createFromFile(terrain.getPic() , pdoc), 486, shortDescLong ? 630 : 650);
			if (climate != null)
				contentStream.drawImage(PDImageXObject.createFromFile(climate.getPic() , pdoc), 518, shortDescLong ? 630 : 650);
		} catch (Exception e) {
			System.out.println("->" + name + ": The images at the top right didn't print because they don't exist. You can add them yourself if you'd like, as described at https://github.com/bjpaupor/rpg-utility/tree/master.");
			e.printStackTrace();
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
			.forEach(s -> new Creature(s.toString()).printToPdf("/home/brandon/Documents/StatBloccs/")); 
		} 
	}
	/**
	 * Prints all creatures within folders given by args
	 * @param args - folders containing creature files
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
	//	printSet("src/Assets/CreatureFiles/");
		//for (String s : args)
		//	printSet(s);
		Creature boar = new Creature("src/Assets/CreatureFiles/AdventurePaths/RiseOfTheRunelords/BurntOfferings/Boar.creature");
		boar.printToPdf("/home/brandon/Documents/StatBloccs/");
	}
}
