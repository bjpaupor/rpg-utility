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
}
