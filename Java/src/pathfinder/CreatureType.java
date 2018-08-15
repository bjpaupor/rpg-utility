package pathfinder;

import java.io.BufferedReader;
import java.io.FileReader;

public enum CreatureType {
	ABERRATION {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Aberration.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Aberration.png";
		}

		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
	}, ANIMAL {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Animal.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Animal.png";
		}
	}, CONSTRUCT {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Construct.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Construct.png";
		}
	}, DRAGON {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Dragon.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Dragon.png";
		}
	}, FEY {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Fey.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Fey.png";
		}
	}, HUMANOID {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Humanoid.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Humanoid.png";
		}
	}, MAGICAL_BEAST {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/MagicalBeast.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/MagicalBeast.png";
		}
	}, MONSTROUS_HUMANOID {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/MonstrousHumanoid.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/MonstrousHumanoid.png";
		}
	}, OOZE {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Ooze.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Ooze.png";
		}
	}, OUTSIDER {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Outsider.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Outsider.png";
		}
	}, PLANT {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Plant.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Plant.png";
		}
	}, UNDEAD {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Undead.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Undead.png";
		}
	}, VERMIN {
		private String description;
		private HitDice hitDie;
		private BABProgression bab;
		private Save[] goodSaves;
		private int skillRanksPerHD;
		private String[] classSkills; //Skills
		private String[] traits;
		private void init() {
			try (BufferedReader read = new BufferedReader(new FileReader("src/Assets/CreatureTypeFiles/Vermin.creatureType"))){
				description = Tools.readALine(read);
				hitDie = HitDice.valueOf(Tools.readALine(read));
				bab = BABProgression.valueOf(Tools.readALine(read).toUpperCase());
				goodSaves = new Save[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < goodSaves.length; i++)
					goodSaves[i] = Save.valueOf(Tools.readALine(read).toUpperCase());
				skillRanksPerHD = Integer.parseInt(Tools.readALine(read));
				classSkills = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < classSkills.length; i++)
					classSkills[i] = Tools.readALine(read);
				traits = new String[Integer.parseInt(Tools.readALine(read))];
				for (int i = 0; i < traits.length; i++)
					traits[i] = Tools.readALine(read);
			} catch (Exception ex) {
				System.out.println("->" + toString() + " Failed to interpret creature type file!");
				ex.printStackTrace();
				return;
			}
		}
		@Override
		public String getDescription() {
			if (description == null)
				init();
			return description;
		}

		@Override
		public HitDice getHD() {
			if (hitDie == null)
				init();
			return hitDie;
		}

		@Override
		public BABProgression getBAB() {
			if (bab == null)
				init();
			return bab;
		}

		@Override
		public Save[] getGoodSaves() {
			if (goodSaves == null)
				init();
			return goodSaves;
		}

		@Override
		public Integer getSkillRanksPerHD() {
			if (skillRanksPerHD == 0)
				init();
			return skillRanksPerHD;
		}

		@Override
		public String[] getClassSkills() {
			if (classSkills == null)
				init();
			return classSkills;
		}

		@Override
		public String[] getTraits() {
			if (traits == null)
				init();
			return traits;
		}
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTypes/Vermin.png";
		}
	};
	public String getPic() {
		return null;
	}
	public String getDescription() {
		return null;
	}
	public HitDice getHD() {
		return null;
	}
	public BABProgression getBAB() {
		return null;
	}
	public Save[] getGoodSaves() {
		return null;
	}
	public Integer getSkillRanksPerHD() {
		return null;
	}
	public String[] getClassSkills() {
		return null;
	}
	public String[] getTraits() {
		return null;
	}
	public static void main(String[] args) {
		
	}
}
