package network;

public class Player {
	public String Name;
	public double Money;
	public double potMoney;
	public int Card1;
	public int Card2;
	public int Position;
	public double raisePlat;
	public double callPlat;
	public int type = 0;
	public int status;
	public int action;
	public double rBet = 0;
	public int seat;
	public int wins = 0;
	public int winner;

	public double toCall;

	public boolean rvp;

	public double vp;
	public double pfr;

	public double fpfr;

	public double threeB;
	public double fthreeB;

	public double fourB;
	public double ffourB;

	public double fiveBPlus;
	public double ffiveBPlus;

	public double cb;

	public double fcb;

	public int statistics = 13;

	public double flopTracking[][] = new double[9][this.statistics];
	// Vertical lines:Position
	// Horizontal
	// Lines:Vp,pfr,fpfr,3bet,f3bet,4bet,f4bet,5+bet,f5+bet,hands||significance

	public void initFlopTracking() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < this.statistics; j++) {
				flopTracking[i][j] = 0;
			}
		}
	}

	public Player(String Name, Double Money, int Card1, int Card2) {
		this.Money = Money;
		this.Name = Name;
		this.Card1 = Card1;
		this.Card2 = Card2;
	}

	public Player(String Name, Double Money) {
		this.Money = Money;
		this.Name = Name;
	}

	public Player(double callPlat, double raisePlat) {
		this.callPlat = 5;
		this.raisePlat = 8;
	}

	public void setCard1(int Card1) {
		this.Card1 = Card1;
	}

	public void setCard2(int Card2) {
		this.Card2 = Card2;
	}

}
