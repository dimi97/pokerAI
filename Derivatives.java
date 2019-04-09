package network;

public class Derivatives {

	public static double sigDer(double x) {
		return myMath.sig(x) * (1 - myMath.sig(x));

	}

	public static double costDerOut(double r, double out) {
		return -2 * Learning.cost(r, out);

	}

	public static double lastZDerT(double T) {
		return sigDer(T);
	}

	public static double[] lastTDerW(double[] weight) {
		return weight;

	}

	public static double[] zDerT(double[] T) {
		int length = T.length;
		double result[] = new double[length];

		for (int i = 0; i < length; i++) {
			result[i] = sigDer(T[i]);
		}

		return result;
	}

//	public static double[] tDerW(double[][] weights) {
//		
//	}
}