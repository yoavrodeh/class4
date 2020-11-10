public class Printer2new extends AbsPrinter {
	public Printer2new(int[][] a) { super(a); }
	@Override
	protected String f(int x) {
		return Math.sqrt(x) + "";
	}
}
