package pathfinder;

public class Trap {
	public enum Type {
		MECHANICAL, MAGICAL;
	}
	private String name;
	private String description;
	private Type type;
	private String cr;
	private int perceptionDC;
	private int disableDeviceDC;
	private String trigger;
	private String duration;
	private String reset;
	private String bypass;
	private String effect;
	
	public Trap(String name, String description, Type type, String cr, int perceptionDC, int disableDeviceDC,
			String trigger, String duration, String reset, String bypass, String effect) {
		this.name = name;
		this.description = description;
		this.type = type;
		this.cr = cr;
		this.perceptionDC = perceptionDC;
		this.disableDeviceDC = disableDeviceDC;
		this.trigger = trigger;
		this.duration = duration;
		this.reset = reset;
		this.bypass = bypass;
		this.effect = effect;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Type getType() {
		return type;
	}

	public String getCr() {
		return cr;
	}

	public int getPerceptionDC() {
		return perceptionDC;
	}

	public int getDisableDeviceDC() {
		return disableDeviceDC;
	}

	public String getTrigger() {
		return trigger;
	}

	public String getDuration() {
		return duration;
	}

	public String getReset() {
		return reset;
	}

	public String getBypass() {
		return bypass;
	}

	public String getEffect() {
		return effect;
	}

}
