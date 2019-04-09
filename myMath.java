package network;

import java.util.Random;

public class myMath {

	public static double sig(double z) {
		return 1 / (1 + Math.exp(-z));
	}
	
	public static Matrix sig(Matrix A) {
		for (int i=0;i<A.getRows();i++) {
			for (int j=0;j<A.getColumns();j++) {
				A.set(i, j, sig(A.get(i, j)));
			}
		}
		
		return A;
	}

	public static double random() {
		Random rand = new Random();
		int r = rand.nextInt(21) - 10;
		double rd = (r) / 10.0;

		return rd;
	}
	
	public static double power(double j,int k) {
		double result=j;
		for (int i=1;i<k;i++) {
			result=result*j;
		}
		return result;
		
	}

}
