public class Dot {
	private double x, y;
	
	public Dot(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() { return x; }	
	public double getY() { return y; }
	
	@Override
	public String toString() {
		return String.format("<%.1f, %.1f>", x, y);
	}
}
