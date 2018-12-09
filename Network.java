package network;

import java.util.Random;

public class Network {
	//input number
	
	private int inputNumber=11;
	
	//inputs
private int c1;
private double money;
private double opponentMoney;
private double potMoney;
private int position;
private double bb;
private double bets;
private double rBet;
private double toCall;
private int activePlayers;
private int preflopOpponents;



//input vector





private double inputs[]=new double[this.inputNumber];


//weights and biases
private double weights[][]=new double [this.inputNumber][this.inputNumber];
private double biases[]=new double [this.inputNumber];

private double zA[]=new double [this.inputNumber];

private double wA[]=new double [this.inputNumber];
private double bA;

//output
public double z;
public double output;

//general
public double lf=0.001;

public Network() {
	
}

public void initializewA() {
	for (int k=0;k<this.inputNumber;k++) {
		//TODO:Change this to random()!
		wA[k]=random();
	}
}

public void initializebA(){
	bA=random();
}

public void setWeights() {
	int i,k;
	
	for (i=0;i<this.inputNumber;i++) {
		for (k=0;k<this.inputNumber;k++) {
			//TODO:CHANGE THIS TO RANDOM();!
			this.weights[i][k]=random();
		}
	}
}

public void setBiases() {
	for (int i=0;i<this.inputNumber;i++) {
		this.biases[i]=random();
	}
}

public double[] getzA() {
	return this.zA;
}

public int getPreflopOpponents() {
	return preflopOpponents;
}

public void setPreflopOpponents(int preflopOpponents) {
	this.preflopOpponents = preflopOpponents;
}

public int getActivePlayers() {
	return activePlayers;
}

public void setActivePlayers(int activePlayers) {
	this.activePlayers = activePlayers;
}

public void setInputs() {
	this.inputs[0]=(this.c1);
	this.inputs[1]=(this.money);
	this.inputs[2]=(this.opponentMoney);
	this.inputs[3]=(this.potMoney);
	this.inputs[4]=(this.position);
	this.inputs[5]=(this.bets);
	this.inputs[6]=(this.bb);
	this.inputs[7]=(this.toCall);
	this.inputs[8]=(this.rBet);
	this.inputs[9]=(this.activePlayers);
	this.inputs[10]=(this.preflopOpponents);
}

public double getrBet() {
	return rBet;
}

public void setrBet(double rBet) {
	this.rBet = rBet;
}

public double getToCall() {
	return toCall;
}

public void setToCall(double toCall) {
	this.toCall = toCall;
}

public int getInputNumber() {
	return inputNumber;
}

public void setInputNumber(int inputNumber) {
	this.inputNumber = inputNumber;
}

public int getC1() {
	return c1;
}

public void setC1(int c1) {
	this.c1 = c1;
}

public double getMoney() {
	return money;
}

public void setMoney(double money) {
	this.money = money;
}

public double getOpponentMoney() {
	return opponentMoney;
}

public void setOpponentMoney(double opponentMoney) {
	this.opponentMoney = opponentMoney;
}

public double getPotMoney() {
	return potMoney;
}

public void setPotMoney(double potMoney) {
	this.potMoney = potMoney;
}

public int getPosition() {
	return position;
}

public void setPosition(int position) {
	this.position = position;
}

public double getBets() {
	return bets;
}

public void setBets(double bets) {
	this.bets = bets;
}

public double getBb() {
	return bb;
}

public void setBb(double bb) {
	this.bb = bb;
}

public double[] getInputs() {
	return inputs;
}


public double getOutput() {
	return output;
}

public void setOutput(double output) {
	this.output = output;
}

public double getLf(){
	return lf;
}

public void setLf(double lf) {
	this.lf = lf;
}

public double[][] getWeights() {
	return weights;
}

public double[] getBiases() {
	return biases;
}

public double[] getwA() {
	return wA;
}

public double getbA() {
	return bA;
}


public void zACalc() {
	double resA[]=new double[this.inputNumber];
	double resB[]=new double[this.inputNumber];
	 resA=matrixMultiply(this.inputs,this.weights);
	 resB=addVectors(resA,biases);
	
	 for (int i=0;i<this.inputNumber;i++) {
		 resB[i]=sig(resB[i]);
	 }
	 
	 this.zA=resB;
	 
}

public double[] matrixMultiply(double m1[],double m2[][]) {
	double result[]=new double[m1.length];
	for (int i=0;i<m1.length;i++) {
		result[i]=0;
	}
	
	
	for (int i=0;i<m1.length;i++) {
		for (int j=0;j<m1.length;j++) {
			
			result[i]+=(m1[j])*(m2[j][i]);
		}
	}
	
	return result;
}

public double[] addVectors(double v1[],double v2[]) {
	double result[]=new double[v1.length];
	
	for (int i=0;i<v1.length;i++) {
		result[i]=v1[i]+v2[i];
	}
	
	return result;
}


public void zCalc() {
	zACalc();
	
	double myZ=dotProduct(zA,wA)+bA;
	
this.z=myZ;
}

public double dotProduct(double m1[],double m2[]) {
	double dProduct=0;
	for (int i=0;i<m1.length;i++) {
		dProduct+=m1[i]*m2[i];
	}
	
	return dProduct;
}




public void outputCalc() {
	zCalc();
	this.output=sig(z);
}

public double sig(double z) {
	return 1/(1+Math.exp(-z));
}

public double getZ() {
	return z;
}

public void setZ(double z) {
	this.z = z;
}

public double random() {
	Random rand = new Random();
	int r = rand.nextInt(21) -10;
	double rd=(r)/10.0;
	
return rd;
}

}
