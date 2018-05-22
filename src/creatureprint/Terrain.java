package creatureprint;

public enum Terrain {
	DESERT {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Desert.png";
		}
	}, FOREST_JUNGLE {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Forest-Jungle.png";
		}
	}, HILL {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Hill.png";
		}
	}, MOUNTAIN {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Mountain.png";
		}
	}, PLAIN {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Plain.png";
		}
	}, RUINS_DUNGEON {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Ruins-Dungeon.png";
		}
	}, SKY {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Sky.png";
		}
	}, SWAMP {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Swamp.png";
		}
	}, UNDERGROUND {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Underground.png";
		}
	}, URBAN {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Urban.png";
		}
	}, WATER {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTerrains/Water.png";
		}
	};
	public String getPic() {
		return "src/Pictures/CreatureTerrains/Plain.png";
	}
}
