package pathfinder;

public class Trio<X, Y, Z> {
	private X x;
	private Y y;
	private Z z;
	
	public Trio(X x, Y y, Z z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public X getX() {
		return x;
	}
	public void setX(X x) {
		this.x = x;
	}
	public Y getY() {
		return y;
	}
	public void setY(Y y) {
		this.y = y;
	}
	public Z getZ() {
		return z;
	}
	public void setZ(Z z) {
		this.z = z;
	}
}
