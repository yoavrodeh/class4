public class ShapeSet {
	private Shape[] shapes = new Shape[10];
	private int numShapes = 0;

	public void add(Shape s) {
		shapes[numShapes++] = s;
	}

	public Dot getCenter() {
		double sumX = 0.0, sumY = 0.0;
		for (int i = 0; i < numShapes; i++) {
			sumX += shapes[i].getCenter().getX();
			sumY += shapes[i].getCenter().getY();
		}
		return new Dot(sumX/numShapes, sumY/numShapes);
	}

	public double avgRadius() {
		double sum = 0.0;
		double count = 0;
		for (int i = 0; i < numShapes; i++)
			if (shapes[i] instanceof Circle) {
				Circle c = (Circle) shapes[i];
				sum += c.getRadius();
				count++;
			}
		return sum / count;
	}
}
