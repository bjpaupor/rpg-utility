package creatureprint;

public enum Climate {
	COLD {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureClimates/Cold.png";
		}
	}, EXTRAPLANAR {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureClimates/Extraplanar.png";
		}
	}, TEMPERATE {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureClimates/Temperate.png";
		}
	}, TROPICAL {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureClimates/Tropical.png";
		}
	};
	public String getPic() {
		return "src/Pictures/CreatureClimates/Temperate.png";
	}
}
