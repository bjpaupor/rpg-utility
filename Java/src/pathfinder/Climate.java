package pathfinder;
/**
 * General conditions of an environment
 * @author brandon
 *
 */
public enum Climate {
	COLD {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureClimates/Cold.png";
		}
	}, EXTRAPLANAR {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureClimates/Extraplanar.png";
		}
	}, TEMPERATE {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureClimates/Temperate.png";
		}
	}, TROPICAL {
		@Override
		public String getPic() {
			return "src/Assets/Pictures/CreatureClimates/Tropical.png";
		}
	};
	public String getPic() {
		return null;
	}
}
