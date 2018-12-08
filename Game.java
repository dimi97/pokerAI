//TODO: GAME 9

/**
----------------
Player Player 1 Posts 1.0$ small blind.
Player Engine Posts 2.0$ big blind.
Player Player 1 it's your turn to act.
Player 1 Raises to 4.0$
Player Engine it's your turn to act.
Engine Raises to 8.0$
Player Player 1 it's your turn to act.
Player 1 Raises to 10.0$
Player Engine it's your turn to act.
Engine Raises to 11.0$
Player Player 1 it's your turn to act.
Player 1 Raises to 13.0$
Player Engine it's your turn to act.
Engine Raises to 13.0$
*/

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
	public double raiseLowLimit=bb*2;
	public Player allPlayers[] = new Player[maxPlayers];
	public int winners;

	public static void main(String[] args) {
		Game myGame = new Game();

		// General

		// Initialization

		createPlayers(myGame);

		Network Neural = initializeNeuralNet();

		int k, p;

		// START GAME
		while (myGame.busted == 0 && myGame.round <= myGame.maxRounds) {

			newGame(myGame);

			// Change Player Positions and Post Blinds

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

			// Post Blinds

			myGame.allPlayers[myGame.sbPos].Money -= myGame.sb;
			myGame.allPlayers[myGame.sbPos].potMoney += myGame.sb;
			myGame.pot += myGame.sb;
			myGame.allPlayers[myGame.sbPos].rBet = myGame.sb;

			System.out.println(
					"Player " + myGame.allPlayers[myGame.sbPos].Name + " Posts " + myGame.sb + "$ small blind.");

			myGame.allPlayers[myGame.bbPos].Money -= myGame.bb;
			myGame.allPlayers[myGame.bbPos].potMoney += myGame.bb;
			myGame.pot += myGame.bb;
			myGame.maxBet = myGame.bb;
			myGame.allPlayers[myGame.bbPos].rBet = myGame.bb;

			System.out
					.println("Player " + myGame.allPlayers[myGame.bbPos].Name + " Posts " + myGame.bb + "$ big blind.");

			// Deal Cards

			Random rand = new Random();
			myGame.allPlayers[0].Card1 = rand.nextInt(10) + 1;
			myGame.allPlayers[1].Card1 = rand.nextInt(10) + 1;

			// Player actions

			while (myGame.fair != 1) {

				if (myGame.activePlayers == 1) {
					myGame.fair = 1;
				}

				for (p = 1; p <= myGame.maxPlayers; p++) {
					for (k = 0; k < myGame.maxPlayers; k++) {

						// if (allPlayers[k].Position == p) {
						if (myGame.allPlayers[k].Position == p && myGame.allPlayers[k].status == 1
								&& (myGame.allPlayers[k].rBet != myGame.maxBet
										|| (myGame.allPlayers[k].action == 0 && myGame.activePlayers > 1))) {
							System.out.println("Player " + myGame.allPlayers[k].Name + " it's your turn to act.");

							if (myGame.allPlayers[k].type == 0) {
								if (myGame.allPlayers[k].Card1 >= myGame.allPlayers[k].raisePlat) {
									int callerPos = 1;
									if (k + 1 >= myGame.maxPlayers) {
										callerPos = 0;
									} else {
										callerPos = 1;
									}
									myGame.raise(myGame.allPlayers[k], myGame.allPlayers[callerPos], myGame,
											myGame.maxBet + myGame.bb);
								} else if (myGame.allPlayers[k].Card1 >= myGame.allPlayers[k].callPlat) {
									myGame.call(myGame.allPlayers[k], myGame);
								} else {
									myGame.fold(myGame.allPlayers[k], myGame);
								}
							} else if (myGame.allPlayers[k].type == 1) {
								// Neural Prediction

								Neural.setC1(myGame.allPlayers[1].Card1);
								Neural.setMoney(myGame.allPlayers[1].Money);
								Neural.setOpponentMoney(myGame.allPlayers[0].Money);
								Neural.setPotMoney(myGame.pot);
								Neural.setPosition(myGame.allPlayers[1].Position);
								Neural.setOpponentAction(myGame.allPlayers[0].rBet);
								Neural.setBb(myGame.bb);

								Neural.setInputs();

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
					}
				}

			}

			if (myGame.activePlayers == 1) {
				for (int i = 0; i < myGame.maxPlayers; i++) {
					if (myGame.allPlayers[i].status == 1) {
						myGame.allPlayers[i].winner =1;
						myGame.allPlayers[i].wins++;
						myGame.winners=1;
						myGame.payWinner(myGame);
					}
				}
			} else {
				myGame.checkWinner(myGame);
				myGame.payWinner(myGame);
			}

			
			
			
			
			
			
			

			// (int i,int c1,int c2,double weight,double output,
			// double result,double foldRat,double prediction)
			if ((myGame.round) % myGame.printFactor == 0) {
				printGame(myGame.round, myGame.allPlayers[0].Card1, myGame.allPlayers[1].Card1, Neural.getWeights(),
						Neural.output, myGame.result, Neural.getOutput(), Neural.getBiases(),
						myGame.allPlayers[0].Money, myGame.allPlayers[1].Money, myGame.pot, Neural.getwA(),
						Neural.getbA(), Neural.getzA(), Neural.getInputs(), Neural.getZ());
			}

			// Check if busted

			for (int j = 0; j < myGame.maxPlayers; j++) {
				if (myGame.allPlayers[j].Money == 0) {

					System.out.println(
							"GAME OVER after " + myGame.round + " rounds." + myGame.allPlayers[j].Name + " has lost.");
					myGame.busted = 1;
				}
			}

			// End Check if busted

			/*
			 * 
			 * //Learn
			 * 
			 * Neural.setWeight(Neural.getWeights() + learn(myGame.result,
			 * Neural.getOutput(), Neural.getInputs(), Neural.getZ(), Neural.getLf()));
			 * Neural.setB(Neural.getBiases() + learnB(myGame.result, Neural.getOutput(),
			 * Neural.getInputs(), Neural.getZ(), Neural.getLf()));
			 * 
			 * 
			 * Neural.outputCalc();
			 * 
			 * 
			 * 
			 * if ((myGame.round) % myGame.printFactor == 0) {
			 * printNewNetwork(Neural.getWeights(), Neural.getOutput()); }
			 */

			// End Learn
			myGame.round++;
			
		}

		// Stats

		printStats(myGame);

		// End Stats

		//End game
		
		//End end game
		
	}

	/// END MAIN -------------------------------------------------------------
	/// END MAIN -------------------------------------------------------------
	/// END MAIN -------------------------------------------------------------
	/// END MAIN -------------------------------------------------------------
	/// END MAIN -------------------------------------------------------------
	/// END MAIN -------------------------------------------------------------
	/// END MAIN -------------------------------------------------------------

	public void payWinner(Game myGame) {
		double payment=(myGame.pot/myGame.winners);
		for (int k=0;k<myGame.maxPlayers;k++) {
			if (myGame.allPlayers[k].winner==1) {
				allPlayers[k].Money+=payment;
				System.out.println(allPlayers[k].Name+" wins "+payment+"$");
			}
		}
	}
	
	
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

	public static Network initializeNeuralNet() {
		Network Neural = new Network();
		Neural.setWeights();
		Neural.setBiases();
		Neural.initializebA();
		Neural.initializewA();
		return Neural;
	}

	public static void createPlayers(Game myGame) {
		for (int i = 0; i < myGame.maxPlayers; i++) {

			myGame.allPlayers[i] = new Player("Player " + (i + 1), 0.0);
			myGame.allPlayers[i].seat = i;
			myGame.allPlayers[i].Money = 100.0;
			myGame.allPlayers[i].Position = i + 1;

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
		myGame.winners=0;
		myGame.pot = 0;
		myGame.maxBet = 0;
		myGame.fair = 0;
		myGame.showdown = 1;
		myGame.raiseLowLimit = 0;
		
		for (int k=0;k<myGame.maxPlayers;k++) {
			myGame.allPlayers[k].potMoney = 0;
			myGame.allPlayers[k].action = 0;
			myGame.allPlayers[k].status = 1;
			myGame.allPlayers[k].winner = 0;
			
			myGame.allPlayers[k].rBet = 0;
		}
	}

	public static void printGame(int i, int c1, int c2, double[][] w1, double output, double result, double prediction,
			double[] b1, double m1, double m2, double pot, double wA[], double bA, double zA[], double inputs[],
			double z) {
		// System.out.println("GAME " + (i));
		System.out.println("ZKD Card: " + c2);
		System.out.printf("Neural Inputs (Card,ZKD Money,Opponent Money,Pot,Position,Opponent Action,BB)\n");
		print1dMatrix(inputs);

		System.out.printf("Neural Weights\n");
		print2dMatrix(w1);
		System.out.printf("Neural Biases\n");
		print1dMatrix(b1);

		System.out.printf("Neural zA\n");
		print1dMatrix(zA);

		System.out.printf("Neural wA\n");
		print1dMatrix(wA);
		System.out.printf("Neural bA: %.3f \n", bA);

		System.out.printf("Neural z: %.3f \n", z);

		System.out.printf("---------------------- \n");

		System.out.printf("Neural Output %.3f \n", prediction);
		System.out.printf("---------------------- \n");

		System.out.println("John Card: " + c1);

		System.out.println("ZKD Money: " + m2);
		System.out.println("John Money: " + m1);
		System.out.println("\n \n");
	}

	public static void printNewNetwork(double weight, double prediction) {
		System.out.printf("NEW Neural Weight %.3f \n", weight);
		System.out.printf("NEW Neural Output %.3f \n", prediction);
		System.out.println("----------------");
	}

	public static double sig(double x) {
		return (1 / (Math.exp(-x) + 1));
	}
	// This is for derivatives
	/*
	 * 
	 * public static double derivative(double r, double p, double input, double z) {
	 * return -2 * (r - p) * (1 - sig(z)) * sig(z) * input; }
	 * 
	 * public static double derivativeB(double r, double p, double input, double z)
	 * { return -2 * (r - p) * (1 - sig(z)) * sig(z); }
	 * 
	 * public double learn(Game myGame,Network Neural) { Double c;
	 * 
	 * c = -(derivative(myGame.result,
	 * Neural.getOutput(),Neural.getInputs(),Neural.getZ())) * lf;
	 * 
	 * return c; }
	 * 
	 * public static Double learnB(Double result, Double prediction, double[] ds,
	 * double z, double lf) { Double c;
	 * 
	 * c = -(derivativeB(result, prediction, ds, z)) * lf;
	 * 
	 * return c; }
	 */
	// This is for derivatives

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

	public static void print1dMatrix(double matrix[]) {
		for (int k = 0; k < matrix.length * 6; k++) {
			System.out.print("-");
		}
		System.out.print("\n");

		for (int i = 0; i < matrix.length; i++) {
			if (i == 0) {
				System.out.print("| ");
			}

			System.out.print(matrix[i] + " | ");

			if (i == matrix.length - 1) {
				System.out.print("\n");
				for (int k = 0; k < matrix.length * 6; k++) {
					System.out.print("-");
				}
				System.out.print("\n");
			}

		}

	}

	public void call(Player player, Game myGame) {
		double dif = myGame.maxBet - player.rBet;

		player.Money -= dif;
		player.potMoney += dif;
		player.rBet += dif;
		myGame.pot += dif;
		player.action = 3;
		myGame.fair = 1;

		System.out.println(player.Name + " Calls " + dif + "$");

	}

	public void raise(Player raiser, Player caller, Game myGame, double bet) {
		double moneyInPotRightNow = bet - raiser.rBet;

		if (bet > raiser.Money + raiser.rBet || bet < myGame.raiseLowLimit) {
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
		}
	}

	public void check(Player player, Game myGame) {
		player.action = 2;
		System.out.println(player.Name + " Checks ");
	}

	public void fold(Player player, Game myGame) {
		System.out.println(player.Name + " Folds.");
		player.status = 0;
		player.action = 1;
		myGame.activePlayers--;

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
		
		for (int k=0;k<myGame.maxPlayers;k++) {
			if (allPlayers[k].Card1==maxCard) {
				allPlayers[k].winner=1;
				allPlayers[k].wins++;
				myGame.winners++;
				
			}
		}
	}
}
