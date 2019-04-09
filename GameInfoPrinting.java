package network;

public class GameInfoPrinting {

	public static void controlledPrintGame(Game myGame, Network myNetwork) {
		if ((myGame.round) % myGame.printFactor == 0) {
			printGame(myGame,myNetwork);
		}
	}
	
	public static void printGame(Game myGame,Network myNetwork) {
	
		Matrix[] myZArray=myNetwork.getzArray();
		Matrix[] myWeightArray=myNetwork.getWeightArray();
		Matrix[] myBiasArray=myNetwork.getBiasArray();
		
		System.out.println("ZKD Card: " + myGame.allPlayers[1].Card1);
		System.out.println("John Card: " + myGame.allPlayers[0].Card1);
		MatrixManipulation.print1dStringMatrix(myNetwork.getInputNames());
		
		for (int i=0; i<myZArray.length-1;i++) {
			System.out.println("Input vector "+i+":");
			myZArray[i].print();
			
			System.out.println("Weight matrix "+i+":");
			myWeightArray[i].print();
			
			System.out.println("Bias vector "+i+":");
			myBiasArray[i].print();
			
			
			
			
		}
		
		System.out.printf("Neural Network Output: %.3f \n", myNetwork.getOutput());

		System.out.printf("---------------------- \n");


		System.out.println(myGame.allPlayers[1].Name+" Money: " + myGame.allPlayers[1].Money);
		System.out.println(myGame.allPlayers[0].Name+" Money: " + myGame.allPlayers[0].Money);
		System.out.println("\n \n");
	}
	
}
