package network;

public class Statistics {

	
	
	
	public static void updatePlayerFlopTracking(Game myGame, Player player, int type) {
		if (type == 1) {

			if (myGame.flop5Plusbet == true) {

				player.flopTracking[player.Position][8]++;
			} else if (myGame.flop4Bet == true) {

				player.flopTracking[player.Position][6]++;
			} else if (myGame.flop3Bet == true) {

				player.flopTracking[player.Position][4]++;
			} else if (myGame.flopRaise == true) {

				player.flopTracking[player.Position][2]++;
			}

		} else if (type == 2) {
			if (player.rvp == false) {
				player.vp++;
			}
		} else if (type == 3) {

			if (player.rvp == false) {
				player.vp++;
			}

			if (myGame.flop4Bet == true || myGame.flop5Plusbet == true) {
				myGame.flop5Plusbet = true;
				player.flopTracking[player.Position][7]++;
			} else if (myGame.flop3Bet == true) {
				myGame.flop4Bet = true;
				player.flopTracking[player.Position][5]++;
			} else if (myGame.flopRaise == true) {
				myGame.flop3Bet = true;
				player.flopTracking[player.Position][3]++;
			} else {

				myGame.flopRaise = true;
				player.flopTracking[player.Position][1]++;
			}
		}

	}
	
}
