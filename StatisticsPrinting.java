package network;

public class StatisticsPrinting {
	public static void printStats(Game myGame) {
		System.out.println("-------------------------------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("STATS:");

		for (int i = 0; i < myGame.maxPlayers; i++) {
			double wPercentage = (double) 100 * myGame.allPlayers[i].wins / (double) (myGame.round);
			System.out.println(myGame.allPlayers[i].Name + " wins:" + Math.round(wPercentage) + "%");
			System.out.println(myGame.allPlayers[i].Name + " Money:" + myGame.allPlayers[i].Money);
		}
	}
	
}
