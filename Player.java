package network;

public class Player {
	public String Name;
	public Double Money;
	public double potMoney;
	public int Card1;
	public int Card2;
	public int Position;
	public double raisePlat;
	public double callPlat;
	public int type=0;
	public int status;
	public int action;
	public double rBet=0;
	public int seat;
	public int wins=0;
	public int winner;
	
	
	public Player(String Name,Double Money,int Card1,int Card2) {
		this.Money=Money;
		this.Name=Name;
		this.Card1=Card1;
		this.Card2=Card2;
	}
	
	public Player(String Name,Double Money) {
		this.Money=Money;
		this.Name=Name;
	}
	
	public Player(double callPlat,double raisePlat) {
		this.callPlat=5;
		this.raisePlat=8;
	}
	
	public void setCard1(int Card1) {
		this.Card1=Card1;
	}
	public void setCard2(int Card2) {
		this.Card2=Card2;
	}
	
}
