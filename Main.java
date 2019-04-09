package network;

public class Main {
	public static void main(String[] args) {
		Network Neural = initializeNeuralNet();
		Game.RunGame(Neural);

	}

	public static Network initializeNeuralNet() {

		Network Neural = new Network();
		Neural.initializeNN();
		return Neural;
	}

}