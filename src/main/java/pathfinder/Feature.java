package pathfinder;

import org.bson.Document;
/**
 * Special ability
 * @author brandon
 */
public class Feature {
	private String name;
	private String description;
	
	public Feature(Document d) {
		name = d.getString("name");
		description = d.getString("description");
	}
	public Feature(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Feature:" + name + ":" + description;
	}
}
