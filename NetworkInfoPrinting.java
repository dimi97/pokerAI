package network;

public class NetworkInfoPrinting {
	public static void printNewNetwork(double weight, double prediction) {
		System.out.printf("NEW Neural Weight %.3f \n", weight);
		System.out.printf("NEW Neural Output %.3f \n", prediction);
		System.out.println("----------------");
	}

	
}
