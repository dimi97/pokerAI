package network;

public class MatrixManipulation {


	public static void print1dMatrix(double matrix[]) {
		for (int k = 0; k < matrix.length * 6; k++) {
			System.out.print("-");
		}
		System.out.print("\n");

		for (int i = 0; i < matrix.length; i++) {
			if (i == 0) {
				System.out.print("| ");
			}

			System.out.print(i + "-->" + matrix[i] + " | ");

			if (i == matrix.length - 1) {
				System.out.print("\n");
				for (int k = 0; k < matrix.length * 6; k++) {
					System.out.print("-");
				}
				System.out.print("\n");
			}

		}

	}

	public static void print1dStringMatrix(String matrix[]) {
		for (int k = 0; k < matrix.length * 6; k++) {
			System.out.print("-");
		}
		System.out.print("\n");

		for (int i = 0; i < matrix.length; i++) {
			if (i == 0) {
				System.out.print("| ");
			}

			System.out.print(i + "-->" + matrix[i] + " | ");

			if (i == matrix.length - 1) {
				System.out.print("\n");
				for (int k = 0; k < matrix.length * 6; k++) {
					System.out.print("-");
				}
				System.out.print("\n");
			}

		}

	}

	public static void print2dMatrix(double matrix[][]) {
		for (int k = 0; k < matrix.length * 6; k++) {
			System.out.print("-");
		}
		System.out.print("\n");

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {

				if (j == 0) {
					System.out.print("| ");
				}

				System.out.print(matrix[i][j] + " | ");

				if (j == matrix.length - 1) {
					System.out.print("\n");

					for (int k = 0; k < matrix.length * 6; k++) {
						System.out.print("-");
					}
					System.out.print("\n");
				}

			}
		}
	}

	public static double dotProduct(double m1[], double m2[]) {
		double dProduct = 0;
		for (int i = 0; i < m1.length; i++) {
			dProduct += m1[i] * m2[i];
		}

		return dProduct;
	}

	public static double[] addVectors(double v1[], double v2[]) {
		double result[] = new double[v1.length];

		for (int i = 0; i < v1.length; i++) {
			result[i] = v1[i] + v2[i];
		}

		return result;
	}

	public static double[] matrixMultiply(double m1[], double m2[][]) {
		double result[] = new double[m1.length];
		for (int i = 0; i < m1.length; i++) {
			result[i] = 0;

		}

		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m1.length; j++) {

				result[i] += (m1[j]) * (m2[j][i]);
			}
		}

		return result;
	}

}