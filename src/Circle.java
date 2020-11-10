public class Circle extends Shape {
	private Dot center;
	private double radius;

	public Circle(String color, Dot center,
			double radius) {
		super(color);
		this.center = center;
		this.radius = radius;
	}

	public double getRadius() { return radius; }
	
	@Override
	public Dot getCenter() { return center; }

	@Override
	public double getArea() {
		return Math.PI * Math.pow(radius, 2);
	}

	@Override
	public String toString() {
		return String.format("%s Circle(%s, %.1f)",
				getColor(), center, radius);
	}

}
