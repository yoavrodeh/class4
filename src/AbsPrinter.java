public abstract class AbsPrinter {
	private int[][] a;
	public AbsPrinter(int[][] a) {
		this.a = a;
	}	
	public void doAll() {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a.length; j++)
				System.out.println(f(a[i][j]));
	}
	protected abstract String f(int x);
}
