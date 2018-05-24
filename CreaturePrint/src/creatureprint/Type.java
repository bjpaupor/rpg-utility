package creatureprint;

public enum Type {
	ABERRATION {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Aberration.png";
		}
	}, ANIMAL {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Animal.png";
		}
	}, CONSTRUCT {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Construct.png";
		}
	}, DRAGON {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Dragon.png";
		}
	}, FEY {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Fey.png";
		}
	}, HUMANOID {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Humanoid.png";
		}
	}, MAGICAL_BEAST {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/MagicalBeast.png";
		}
	}, MONSTROUS_HUMANOID {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/MonstrousHumanoid.png";
		}
	}, OOZE {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Ooze.png";
		}
	}, OUTSIDER {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Outsider.png";
		}
	}, PLANT {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Plant.png";
		}
	}, UNDEAD {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Undead.png";
		}
	}, VERMIN {
		@Override
		public String getPic() {
			return "src/Pictures/CreatureTypes/Vermin.png";
		}
	};
	public String getPic() {
		return null;
	}
	public static void main(String[] args) {
		
	}
}
