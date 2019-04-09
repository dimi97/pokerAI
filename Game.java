
package network;

import java.util.Random;

public class Game {
	public int busted = 0;
	public double pot;
	public double maxBet;
	public int fair = 0;
	public int maxPlayers = 2;
	public double result;
	public int maxRounds = 10;
	public int printFactor = 1;
	public int round = 0;
	public int winner;
	public int activePlayers;
	public int showdown;
	public double bb = 2;
	public double sb = bb / 2;
	public int sbPos = -1;
	public int bbPos = -1;
	public double raiseLowLimit = bb * 2;
	public Player allPlayers[] = new Player[maxPlayers];
	public int winners;
	public int bets;
	// Tracking variables

	public boolean flopRaise;
	public boolean flop3Bet;
	public boolean flop4Bet;
	public boolean flop5Plusbet;

	// Begin Main!----------------------------

	public static void RunGame(Network Neural) {
		Game myGame = new Game();

		createPlayers(myGame);

		// START GAME
		while (myGame.busted == 0 && myGame.round <= myGame.maxRounds) {

			newGame(myGame);

			// Player actions

			while (myGame.fair != 1) {

				if (myGame.activePlayers == 1) {
					myGame.fair = 1;
				}

				for (int p = 1; p <= myGame.maxPlayers; p++) {
					for (int k = 0; k < myGame.maxPlayers; k++) {

						if (myGame.allPlayers[k].Position == p && myGame.allPlayers[k].status == 1
								&& (myGame.allPlayers[k].rBet != myGame.maxBet
										|| (myGame.allPlayers[k].action == 0 && myGame.activePlayers > 1))) {
							System.out.println("Player " + myGame.allPlayers[k].Name + " it's your turn to act.");

							playerDecisions(Neural, myGame, k);
						}
					}
				}

			}

			findWinner(myGame);

			// Update Player tracking

			// updatePlayerTracking(myGame); is gonna be needed when we
			// need to update stuff like won at showdown etc.

			// (int i,int c1,int c2,double weight,double output,
			// double result,double foldRat,double prediction)
			GameInfoPrinting.controlledPrintGame(myGame, Neural);

			checkEndOfGame(myGame);

			myGame.round++;

		}

		// Game over.
		StatisticsPrinting.printStats(myGame);

	}

	public static void playerDecisions(Network Neural, Game myGame, int k) {
		if (myGame.allPlayers[k].type == 0) {
			if (myGame.allPlayers[k].Card1 >= myGame.allPlayers[k].raisePlat) {
				int callerPos = 1;
				if (k + 1 >= myGame.maxPlayers) {
					callerPos = 0;
				} else {
					callerPos = 1;
				}
				myGame.raise(myGame.allPlayers[k], myGame.allPlayers[callerPos], myGame, myGame.maxBet + myGame.bb);
			} else if (myGame.allPlayers[k].Card1 >= myGame.allPlayers[k].callPlat) {
				myGame.call(myGame.allPlayers[k], myGame);
			} else {
				myGame.fold(myGame.allPlayers[k], myGame);
			}
		} else if (myGame.allPlayers[k].type == 1) {
			// Neural Prediction

			playerInfoToNetwork(Neural, myGame);

			Neural.outputCalc();

			double goodBet = Neural.getOutput() * myGame.allPlayers[k].Money;
			if (goodBet + myGame.allPlayers[1].rBet > myGame.maxBet) {
				int callerPos = 1;
				if (k + 1 >= myGame.maxPlayers) {
					callerPos = 0;
				} else {
					callerPos = 1;
				}

				myGame.raise(myGame.allPlayers[k], myGame.allPlayers[callerPos], myGame,
						myGame.allPlayers[1].rBet + Math.round(goodBet));

			} else if (myGame.allPlayers[1].rBet == myGame.maxBet) {
				myGame.check(myGame.allPlayers[k], myGame);
			}

			else {
				myGame.fold(myGame.allPlayers[k], myGame);
			}
		}
	}

	public static void playerInfoToNetwork(Network Neural, Game myGame) {
		Neural.setC1(myGame.allPlayers[1].Card1);
		Neural.setMoney(myGame.allPlayers[1].Money);
		Neural.setOpponentMoney(myGame.allPlayers[0].Money);
		Neural.setPotMoney(myGame.pot);
		Neural.setPosition(myGame.allPlayers[1].Position);
		Neural.setBets(myGame.bets);
		Neural.setBb(myGame.bb);
		Neural.setToCall(myGame.allPlayers[1].toCall);
		Neural.setrBet(myGame.allPlayers[1].rBet);
		Neural.setActivePlayers(myGame.activePlayers);
		Neural.setPreflopOpponents(myGame.maxPlayers);

		// My stats
		Neural.setVp(myGame.allPlayers[1].vp);
		Neural.setPfr(myGame.allPlayers[1].pfr);
		Neural.setFpfr(myGame.allPlayers[1].fpfr);
		Neural.setThreeB(myGame.allPlayers[1].threeB);
		Neural.setFthreeB(myGame.allPlayers[1].fthreeB);
		Neural.setFourB(myGame.allPlayers[1].fourB);
		Neural.setFfourB(myGame.allPlayers[1].ffourB);
		Neural.setCb(myGame.allPlayers[1].cb);
		Neural.setFcb(myGame.allPlayers[1].fcb);

		// Villain Stats

		Neural.setVvp(myGame.allPlayers[0].vp);
		Neural.setVpfr(myGame.allPlayers[0].pfr);
		Neural.setVfpfr(myGame.allPlayers[0].fpfr);
		Neural.setVthreeB(myGame.allPlayers[0].threeB);
		Neural.setVfthreeB(myGame.allPlayers[0].fthreeB);
		Neural.setVfourB(myGame.allPlayers[0].fourB);
		Neural.setVffourB(myGame.allPlayers[0].ffourB);
		Neural.setVcb(myGame.allPlayers[0].cb);
		Neural.setVfcb(myGame.allPlayers[0].fcb);

		Neural.setInputs();
	}

	public static void dealCards(Game myGame) {
		Random rand = new Random();
		myGame.allPlayers[0].Card1 = rand.nextInt(10) + 1;
		myGame.allPlayers[1].Card1 = rand.nextInt(10) + 1;
	}

	public static void checkEndOfGame(Game myGame) {
		for (int j = 0; j < myGame.maxPlayers; j++) {
			if (myGame.allPlayers[j].Money == 0) {

				System.out.println(
						"GAME OVER after " + myGame.round + " rounds." + myGame.allPlayers[j].Name + " has lost.");
				myGame.busted = 1;
			}
		}
	}

	public static void findWinner(Game myGame) {
		if (myGame.activePlayers == 1) {
			for (int i = 0; i < myGame.maxPlayers; i++) {
				if (myGame.allPlayers[i].status == 1) {
					myGame.allPlayers[i].winner = 1;
					myGame.allPlayers[i].wins++;
					myGame.winners = 1;
					myGame.payWinner(myGame);
				}
			}
		} else {
			myGame.checkWinner(myGame);
			myGame.payWinner(myGame);
		}
	}

	public static void postBlinds(Game myGame) {
		double tempsB = myGame.sb;
		double tempbB = myGame.bb;
		// Small Blind

		if (myGame.allPlayers[myGame.sbPos].Money < myGame.sb) {
			myGame.sb = myGame.allPlayers[myGame.sbPos].Money;
		}

		myGame.allPlayers[myGame.sbPos].Money -= myGame.sb;
		myGame.allPlayers[myGame.sbPos].potMoney += myGame.sb;
		myGame.pot += myGame.sb;
		myGame.allPlayers[myGame.sbPos].rBet = myGame.sb;

		System.out.println("Player " + myGame.allPlayers[myGame.sbPos].Name + " Posts " + myGame.sb + "$ small blind.");

		myGame.sb = tempsB;

		// Big Blind

		if (myGame.allPlayers[myGame.bbPos].Money < myGame.bb) {
			myGame.bb = myGame.allPlayers[myGame.bbPos].Money;
		}

		myGame.allPlayers[myGame.bbPos].Money -= myGame.bb;
		myGame.allPlayers[myGame.bbPos].potMoney += myGame.bb;
		myGame.pot += myGame.bb;
		myGame.maxBet = myGame.bb;
		myGame.allPlayers[myGame.bbPos].rBet = myGame.bb;

		System.out.println("Player " + myGame.allPlayers[myGame.bbPos].Name + " Posts " + myGame.bb + "$ big blind.");

		myGame.bb = tempbB;

	}

	public static void changePositions(Game myGame) {
		int k;
		for (k = 0; k < myGame.maxPlayers; k++) {

			myGame.allPlayers[k].Position++;

			if (myGame.allPlayers[k].Position > myGame.maxPlayers) {
				myGame.allPlayers[k].Position = 1;
			}

			if (myGame.allPlayers[k].Position == 1) {
				myGame.sbPos = k;

			} else if (myGame.allPlayers[k].Position == 2) {
				myGame.bbPos = k;
			}

		}
	}

	public void payWinner(Game myGame) {
		double payment = (myGame.pot / myGame.winners);
		for (int k = 0; k < myGame.maxPlayers; k++) {
			if (myGame.allPlayers[k].winner == 1) {
				allPlayers[k].Money += payment;
				System.out.println(allPlayers[k].Name + " wins " + payment + "$");
			}
		}
	}

	public static void createPlayers(Game myGame) {
		for (int i = 0; i < myGame.maxPlayers; i++) {

			myGame.allPlayers[i] = new Player("Player " + (i + 1), 0.0);
			myGame.allPlayers[i].seat = i;
			myGame.allPlayers[i].Money = 100.0;
			myGame.allPlayers[i].Position = i + 1;
			myGame.allPlayers[i].initFlopTracking();

			if (i == 0) {
				myGame.allPlayers[i].callPlat = 5;
				myGame.allPlayers[i].raisePlat = 8;
			}

			if (i == 1) {
				myGame.allPlayers[i].type = 1;
				myGame.allPlayers[i].Name = "Engine";
			}
		}
	}

	public static void newGame(Game myGame) {
		System.out.println("GAME " + myGame.round);
		System.out.println("----------------");

		myGame.activePlayers = myGame.maxPlayers;
		myGame.winners = 0;
		myGame.pot = 0;
		myGame.maxBet = 0;
		myGame.fair = 0;
		myGame.showdown = 1;
		myGame.raiseLowLimit = myGame.bb;

		for (int k = 0; k < myGame.maxPlayers; k++) {
			myGame.allPlayers[k].potMoney = 0;
			myGame.allPlayers[k].action = 0;
			myGame.allPlayers[k].status = 1;
			myGame.allPlayers[k].winner = 0;

			myGame.allPlayers[k].rvp = false;
			// myGame.allPlayers[k].rpfr=false;
			// myGame.allPlayers[k].rthreeB=false;
			// myGame.allPlayers[k].rfourB=false;
			// myGame.allPlayers[k].rcb=false;
			// myGame.allPlayers[k].rfcb=false;

			myGame.allPlayers[k].rBet = 0;
		}

		// Change Player Positions and Post Blinds

		changePositions(myGame);

		// Post Blinds

		postBlinds(myGame);

		// Deal Cards

		dealCards(myGame);

	}

	public void call(Player player, Game myGame) {

		Statistics.updatePlayerFlopTracking(myGame, player, 2);

		player.rvp = true;

		double dif = myGame.maxBet - player.rBet;

		if (dif >= player.Money) {

			player.toCall = player.Money;

		}

		player.Money -= player.toCall;
		player.potMoney += player.toCall;
		player.rBet += player.toCall;
		myGame.pot += player.toCall;
		player.action = 3;
		myGame.fair = 1;

		System.out.println(player.Name + " Calls " + dif + "$");

	}

	public void raise(Player raiser, Player caller, Game myGame, double bet) {

		double moneyInPotRightNow = bet - raiser.rBet;

		if (bet > raiser.Money + raiser.rBet || bet < myGame.raiseLowLimit
				|| (bet > (caller.Money + caller.rBet) && (caller.Money + caller.rBet < myGame.raiseLowLimit))) {
			myGame.call(raiser, myGame);

		} else {

			if ((bet) > (caller.Money + caller.rBet)) {

				bet = caller.Money + caller.rBet;
			}

			myGame.raiseLowLimit += (bet - myGame.maxBet) * 2;

			myGame.pot += moneyInPotRightNow;
			raiser.potMoney += moneyInPotRightNow;
			raiser.Money -= moneyInPotRightNow;

			raiser.rBet = bet;
			myGame.maxBet = bet;

			myGame.fair = 0;
			raiser.action = 4;
			System.out.println(raiser.Name + " Raises to " + myGame.maxBet + "$");

			// Update bet status

			Statistics.updatePlayerFlopTracking(myGame, raiser, 3);

			raiser.rvp = true;

		}
	}

	public void check(Player player, Game myGame) {
		player.action = 2;
		System.out.println(player.Name + " Checks ");
	}

	public void fold(Player player, Game myGame) {
		if (player.Money != 0) {

			Statistics.updatePlayerFlopTracking(myGame, player, 1);

			System.out.println(player.Name + " Folds.");
			player.status = 0;
			player.action = 1;
			myGame.activePlayers--;
		} else {
			check(player, myGame);
		}
	}

	public void checkWinner(Game myGame) {
		int maxCard = -1;

		for (int k = 0; k < myGame.maxPlayers; k++) {
			if (allPlayers[k].status == 1) {
				if (allPlayers[k].Card1 >= maxCard) {
					maxCard = allPlayers[k].Card1;
				}
			}

		}

		for (int k = 0; k < myGame.maxPlayers; k++) {
			if (allPlayers[k].Card1 == maxCard) {
				allPlayers[k].winner = 1;
				allPlayers[k].wins++;
				myGame.winners++;

			}
		}
	}
}
