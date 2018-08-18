package pathfinder;

public class Feat {
	public class Type {
		private String name;
		private String description;
		public Type(String name, String description) {
			this.name = name;
			this.description = description;
		}
		public String getName() {
			return name;
		}
		public String getDescription() {
			return description;
		}
	}
	private String name;
	private String description;
	private String type; //Feat type name
	public Feat(String name, String description, String type) {
		this.name = name;
		this.description = description;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getType() {
		return type;
	}
}
