public class Printer1new extends AbsPrinter {
	public Printer1new(int[][] a) { super(a); }
	@Override
	protected String f(int x) {
		return x * 2 + "";
	}
}
