package pathfinder;

public enum Terrain {
	DESERT {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Desert.png";
		}
	}, FOREST_JUNGLE {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Forest-Jungle.png";
		}
//		//Toggles are "Category:[sparse][medium][dense];
//		//Typical Trees (partial cover, can be stood in)
//		//Light Undergrowth (concealment, difficult terrain, +2 DC Acrobatics/Stealth)
//		@Override
//		public String[][] getMap(String toggles) {
//			String category = toggles.substring(toggles.indexOf(":") + 1, toggles.indexOf(";"));
//			String[][] result = new String[20][20];
//			for (int i = 0; i < result.length; i++)
//				for (int j = 0; j < result[i].length; j++) {
//					double adjacentUndergrowth = 0;
//					boolean iExtreme = false;
//					if (i == 0 || i == result.length - 1) {
//						adjacentUndergrowth += 1.5;
//						iExtreme = true;
//					}
//					if (j == 0 || j == result[i].length - 1)
//						adjacentUndergrowth += iExtreme ? 1 : 1.5;
//					
//					|| result[i - 1][j].contains("Undergrowth"))
//						adjacentUndergrowth += 1;
//					//set this (TODO)
//					if (category.equals("sparse")) {
//						if (Dice.d2() == 1)
//							result[i][j] += "Typical Trees";
//						if (Dice.d100() > 30 + adjacentUndergrowth * 5)
//							result[i][j] += "Light Undergrowth";
//					}
//					if (Dice.d100() > 50)
//						return null;
//				}
//			return result;
//		}
	}, HILL {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Hill.png";
		}
	}, MOUNTAIN {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Mountain.png";
		}
	}, PLAIN {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Plain.png";
		}
	}, RUINS_DUNGEON {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Ruins-Dungeon.png";
		}
	}, SKY {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Sky.png";
		}
	}, SWAMP {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Swamp.png";
		}
	}, UNDERGROUND {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Underground.png";
		}
	}, URBAN {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Urban.png";
		}
	}, WATER {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureTerrains/Water.png";
		}
	};
	public String getPic() {
		return "src/Assets/Pictures/CreatureTerrains/Plain.png";
	}
//	public String[][] getMap(String toggles) {
//		return null;
//	}
}