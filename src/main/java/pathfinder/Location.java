package pathfinder;

import java.awt.Image;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;

public class Location implements Describable {
	private String name;
	private String description;
	private String map; //file name of a map for this location
	private List<String> childLocations; //locations contained within this location
	private String parentLocation; //location this location is contained within
	private List<String> connectedLocations; //locations of the same scale reachable without entering another such location

	/**
	 * Attempts to load the image in map
	 * @return map or null if loading failed
	 */
	public Image getMap() {
		Image result = null;
		try {
			result = ImageIO.read(new File(map));
		} catch (Exception e) {
			System.err.println("Warning:Location " + name + " unable to load map " + map);	
		}
		return result;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(String map) {
		this.map = map;
	}

	/**
	 * @return the childLocations
	 */
	public List<String> getChildLocations() {
		return childLocations;
	}

	/**
	 * @param childLocations the childLocations to set
	 */
	public void setChildLocations(List<String> childLocations) {
		this.childLocations = childLocations;
	}

	/**
	 * @return the parentLocation
	 */
	public String getParentLocation() {
		return parentLocation;
	}

	/**
	 * @param parentLocation the parentLocation to set
	 */
	public void setParentLocation(String parentLocation) {
		this.parentLocation = parentLocation;
	}

	/**
	 * @return the connectedLocations
	 */
	public List<String> getConnectedLocations() {
		return connectedLocations;
	}

	/**
	 * @param connectedLocations the connectedLocations to set
	 */
	public void setConnectedLocations(List<String> connectedLocations) {
		this.connectedLocations = connectedLocations;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "Location [childLocations=" + childLocations + ", connectedLocations=" + connectedLocations
				+ ", description=" + description + ", map=" + map + ", name=" + name + ", parentLocation="
				+ parentLocation + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((childLocations == null) ? 0 : childLocations.hashCode());
		result = prime * result + ((connectedLocations == null) ? 0 : connectedLocations.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parentLocation == null) ? 0 : parentLocation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (childLocations == null) {
			if (other.childLocations != null)
				return false;
		} else if (!childLocations.equals(other.childLocations))
			return false;
		if (connectedLocations == null) {
			if (other.connectedLocations != null)
				return false;
		} else if (!connectedLocations.equals(other.connectedLocations))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentLocation == null) {
			if (other.parentLocation != null)
				return false;
		} else if (!parentLocation.equals(other.parentLocation))
			return false;
		return true;
	}
}
