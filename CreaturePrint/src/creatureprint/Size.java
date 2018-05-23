package creatureprint;

public enum Size {
	FINE, DIMINUITIVE, TINY, SMALL, MEDIUM, LARGE, HUGE, GARGANTUAN, COLOSSAL;
	@Override
	public String toString() {
		String result = this.name().toLowerCase();
		result = (char)(result.charAt(0) - 32) + result.substring(1);
		return result;
	}
}
