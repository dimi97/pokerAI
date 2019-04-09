package network;

public class Matrix {
	private String name;
	private int rows;
	private int columns;
	private double v[][] = null;

	public Matrix(String Name, int r, int c) {
		this.name = Name;
		rows = r;
		columns = c;
		v = new double[rows][columns];
	}

	public Matrix(String Name, int c) {
		this.name = Name;
		rows = 1;
		columns = c;
		v = new double[rows][columns];
	}

	public double get(int i, int j) {
		return v[i][j];
	}

	public double get(int i) {
		return v[0][i];
	}

	public void set(int i, int j, double c) {
		v[i][j] = c;
	}

	public void set(int i, double c) {
		v[0][i] = c;
	}

	public static void set(Matrix myMatrix, double matrix2[]) {
		for (int i = 0; i < matrix2.length; i++) {
			myMatrix.v[0][i] = matrix2[i];

		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public void print() {
		for (int k = 0; k < columns * 6; k++) {
			System.out.print("-");
		}
		System.out.print("\n");

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {

				if (j == 0) {
					System.out.print("| ");
				}

				System.out.print(v[i][j] + " | ");

				if (j == columns - 1) {
					System.out.print("\n");

					for (int k = 0; k < columns * 6; k++) {
						System.out.print("-");
					}
					System.out.print("\n");
				}

			}
		}
	}

	public static void initializeMatrix(Matrix myMatrix) {
		int i, k;

		for (i = 0; i < myMatrix.rows; i++) {
			for (k = 0; k < myMatrix.columns; k++) {
				myMatrix.set(i, k, myMath.random());
			}
		}
	}

	public double[][] data() {
		return v;
	}

	public static Matrix matrixMultiplication(Matrix A, Matrix B) {
		Matrix result = new Matrix("result", A.rows, B.columns);
		for (int j = 0; j < A.rows; j++) {

			for (int i = 0; i < A.columns; i++) {
				result.set(j, i, A.get(j, i) * B.get(i, j));
			}
		}

		return result;
	}

	public static double dotProduct(Matrix A, Matrix B) {
		double result = 0;
		
		if (A.columns!=B.columns) {
			System.out.print("Wrong dot product!");
			
		}

		for (int i = 0; i < A.columns; i++) {
			result += A.get(i) * B.get(i);
		}
		
		return result;
	}
	
	public static Matrix matrixAddition(Matrix A,Matrix B) {
		Matrix result=new Matrix("result",A.columns);
		for (int i=0;i<A.columns;i++) {
			result.set(i, A.get(i)+B.get(i));
		}
		
		return result;
				
	}

}
