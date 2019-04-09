package network;

import java.util.Random;

public class Network {
	// input number

	private int inputNumber = 35;
	private int layers = 3;

	// inputs
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

//My tracking
	private double vp;
	private double pfr;
	private double fpfr;
	private double threeB;
	private double fthreeB;
	private double fourB;
	private double ffourB;
	private double fiveBPlus;
	private double ffiveBPlus;
	private double cb;
	private double fcb;
	private double myHands;
//Villain Tracking
	private double vvp;
	private double vpfr;
	private double vfpfr;
	private double vthreeB;
	private double vfthreeB;
	private double vfourB;
	private double vffourB;
	private double vfiveBPlus;
	private double vffiveBPlus;
	private double vcb;
	private double vfcb;
	private double vHands;
// END input vector

	public double getMyHands() {
		return myHands;
	}

	public void setMyHands(double myHands) {
		this.myHands = myHands;
	}

	public double getvHands() {
		return vHands;
	}

	public void setvHands(double vHands) {
		this.vHands = vHands;
	}

	private double inputs[] = new double[this.inputNumber];
	private String inputNames[] = new String[this.inputNumber];

	public String[] getInputNames() {
		return inputNames;
	}

	public void setInputNames(String[] inputNames) {
		this.inputNames = inputNames;
	}

//weights and biases

//	private double weights[][] = new double[this.inputNumber][this.inputNumber];
//	
//	private Matrix w = new Matrix(inputNumber, inputNumber);
//
//	private double biases[] = new double[this.inputNumber];
//
//	private double zA[] = new double[this.inputNumber];
//
//	private double wA[][] = new double[this.inputNumber][this.inputNumber];
//	private double bA[] = new double[this.inputNumber];
//
//	private double zB[] = new double[this.inputNumber];
//
//	private double wB[][] = new double[this.inputNumber][this.inputNumber];
//	private double bB[] = new double[this.inputNumber];
//
//	private double zC[] = new double[this.inputNumber];
//
//	private double wC[] = new double[this.inputNumber];
//	private double bC;

	
	private Matrix weightArray[] = new Matrix[layers];
	private Matrix biasArray[] = new Matrix[layers];
	private Matrix zArray[] = new Matrix[layers+1];
	private Matrix wL = new Matrix("weights", inputNumber);
	
	

//output
	public double z;
	public double output;

//general
	public double lf = 0.001;

	public Network() {

	}

	// myMethods

	public void initializeNN() {
		setInputNames();
		fillWeightArray(weightArray);
		fillBiasArray(biasArray);
		Matrix.initializeMatrix(wL);

		for (int i = 0; i < weightArray.length; i++) {
			Matrix.initializeMatrix(weightArray[i]);
		}

		for (int i = 0; i < biasArray.length; i++) {
			Matrix.initializeMatrix(biasArray[i]);
		}

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

	public void setInputNames() {
		this.inputNames[0] = "Card";
		this.inputNames[1] = "PzkD Money";
		this.inputNames[2] = "Villain Money";
		this.inputNames[3] = "Pot Money";
		this.inputNames[4] = "AI Position";
		this.inputNames[5] = "Number of bets (3bet Pot,4bet Pot)";
		this.inputNames[6] = "BB Value";
		this.inputNames[7] = "Money to call";
		this.inputNames[8] = "rBet";
		this.inputNames[9] = "Active Players";
		this.inputNames[10] = "Preflop Opponents";
		this.inputNames[11] = "My Vp";
		this.inputNames[12] = "My pfr";
		this.inputNames[13] = "My fpfr";
		this.inputNames[14] = "My 3bet";
		this.inputNames[15] = "My f3bet";
		this.inputNames[16] = "My 4bet";
		this.inputNames[17] = "My f4bet";
		this.inputNames[18] = "My 5bet+";
		this.inputNames[19] = "My f5bet+";
		this.inputNames[20] = "My cb";
		this.inputNames[21] = "My fcb";
		this.inputNames[22] = "My hands";
		this.inputNames[23] = "Villain Vp";
		this.inputNames[24] = "Vaillain pfr";
		this.inputNames[25] = "Villain fpfr";
		this.inputNames[26] = "Villain 3bet";
		this.inputNames[27] = "Villain f3bet";
		this.inputNames[28] = "Villain 4bet";
		this.inputNames[29] = "Villain f4bet";
		this.inputNames[30] = "Villain 5bet+";
		this.inputNames[31] = "Villain f5bet+";
		this.inputNames[32] = "Villain cb";
		this.inputNames[33] = "Villain fcb";
		this.inputNames[34] = "Villain Hands";
	}

	public void setInputs() {
		this.inputs[0] = (this.c1);
		this.inputs[1] = (this.money);
		this.inputs[2] = (this.opponentMoney);
		this.inputs[3] = (this.potMoney);
		this.inputs[4] = (this.position);
		this.inputs[5] = (this.bets);
		this.inputs[6] = (this.bb);
		this.inputs[7] = (this.toCall);
		this.inputs[8] = (this.rBet);
		this.inputs[9] = (this.activePlayers);
		this.inputs[10] = (this.preflopOpponents);

		this.inputs[11] = (this.vp);
		this.inputs[12] = (this.pfr);
		this.inputs[13] = (this.fpfr);
		this.inputs[14] = (this.threeB);
		this.inputs[15] = (this.fthreeB);
		this.inputs[16] = (this.fourB);
		this.inputs[17] = (this.ffourB);
		this.inputs[18] = (this.fiveBPlus);
		this.inputs[19] = (this.ffiveBPlus);
		this.inputs[20] = (this.cb);
		this.inputs[21] = (this.fcb);
		this.inputs[22] = (this.myHands);

		this.inputs[23] = (this.vvp);
		this.inputs[24] = (this.vpfr);
		this.inputs[25] = (this.vfpfr);
		this.inputs[26] = (this.vthreeB);
		this.inputs[27] = (this.vfthreeB);
		this.inputs[28] = (this.vfourB);
		this.inputs[29] = (this.vffourB);
		this.inputs[30] = (this.vfiveBPlus);
		this.inputs[31] = (this.vffiveBPlus);
		this.inputs[32] = (this.vcb);
		this.inputs[33] = (this.vfcb);
		this.inputs[34] = (this.vHands);

		// Villain Stats

		// My stats
	}

	public void fillZArray(Matrix zArray[]) {
		for (int i = 0; i < zArray.length; i++) {
			zArray[i] = new Matrix("z" + i, inputNumber);
		}
	}

	public void fillWeightArray(Matrix weightArray[]) {
		for (int i = 0; i < weightArray.length; i++) {
			weightArray[i] = new Matrix("weight" + i, inputNumber, inputNumber);
		}
	}

	public void fillBiasArray(Matrix biasArray[]) {
		for (int i = 0; i < biasArray.length; i++) {
			biasArray[i] = new Matrix("bias" + i, inputNumber);
		}
	}

//	public double[] tempOutCalc(double myInputs[], double myWeights[][], double myBias[]) {
//		double resA[] = new double[this.inputNumber];
//		double resB[] = new double[this.inputNumber];
//		resA = MatrixManipulation.matrixMultiply(this.inputs, this.weights);
//		resB = MatrixManipulation.addVectors(resA, biases);
//
//		for (int i = 0; i < this.inputNumber; i++) {
//			resB[i] = myMath.sig(resB[i]);
//		}
//
//		return resB;
//
//	}

	public void ziCalc() {

		zArray[0] = new Matrix("z" + 0, inputNumber);
		Matrix.set(zArray[0], inputs);
		for (int i = 1; i < layers+1; i++) {
			zArray[i] = Matrix.matrixMultiplication(zArray[i - 1], weightArray[i - 1]);
			zArray[i] = Matrix.matrixAddition(zArray[i], biasArray[i - 1]);
			zArray[i] = myMath.sig(zArray[i]);

		}

	}

	public void outputCalc() {
		ziCalc();
		int lastZ = zArray.length;
		double z = Matrix.dotProduct(zArray[lastZ-1], wL);
		this.output = myMath.sig(z);
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getVp() {
		return vp;
	}

	public void setVp(double vp) {
		this.vp = vp;
	}

	public double getPfr() {
		return pfr;
	}

	public void setPfr(double pfr) {
		this.pfr = pfr;
	}

	public double getFpfr() {
		return fpfr;
	}

	public void setFpfr(double fpfr) {
		this.fpfr = fpfr;
	}

	public double getThreeB() {
		return threeB;
	}

	public void setThreeB(double threeB) {
		this.threeB = threeB;
	}

	public double getFthreeB() {
		return fthreeB;
	}

	public void setFthreeB(double fthreeB) {
		this.fthreeB = fthreeB;
	}

	public double getFourB() {
		return fourB;
	}

	public void setFourB(double fourB) {
		this.fourB = fourB;
	}

	public double getFfourB() {
		return ffourB;
	}

	public void setFfourB(double ffourB) {
		this.ffourB = ffourB;
	}

	public double getFiveBPlus() {
		return fiveBPlus;
	}

	public void setFiveBPlus(double fiveBPlus) {
		this.fiveBPlus = fiveBPlus;
	}

	public double getFfiveBPlus() {
		return ffiveBPlus;
	}

	public void setFfiveBPlus(double ffiveBPlus) {
		this.ffiveBPlus = ffiveBPlus;
	}

	public double getCb() {
		return cb;
	}

	public void setCb(double cb) {
		this.cb = cb;
	}

	public double getFcb() {
		return fcb;
	}

	public void setFcb(double fcb) {
		this.fcb = fcb;
	}

	public double getVvp() {
		return vvp;
	}

	public void setVvp(double vvp) {
		this.vvp = vvp;
	}

	public double getVpfr() {
		return vpfr;
	}

	public void setVpfr(double vpfr) {
		this.vpfr = vpfr;
	}

	public double getVfpfr() {
		return vfpfr;
	}

	public void setVfpfr(double vfpfr) {
		this.vfpfr = vfpfr;
	}

	public double getVthreeB() {
		return vthreeB;
	}

	public void setVthreeB(double vthreeB) {
		this.vthreeB = vthreeB;
	}

	public double getVfthreeB() {
		return vfthreeB;
	}

	public void setVfthreeB(double vfthreeB) {
		this.vfthreeB = vfthreeB;
	}

	public double getVfourB() {
		return vfourB;
	}

	public void setVfourB(double vfourB) {
		this.vfourB = vfourB;
	}

	public double getVffourB() {
		return vffourB;
	}

	public void setVffourB(double vffourB) {
		this.vffourB = vffourB;
	}

	public double getVfiveBPlus() {
		return vfiveBPlus;
	}

	public void setVfiveBPlus(double vfiveBPlus) {
		this.vfiveBPlus = vfiveBPlus;
	}

	public double getVffiveBPlus() {
		return vffiveBPlus;
	}

	public void setVffiveBPlus(double vffiveBPlus) {
		this.vffiveBPlus = vffiveBPlus;
	}

	public double getVcb() {
		return vcb;
	}

	public void setVcb(double vcb) {
		this.vcb = vcb;
	}

	public double getVfcb() {
		return vfcb;
	}

	public void setVfcb(double vfcb) {
		this.vfcb = vfcb;
	}

	public void setInputs(double[] inputs) {
		this.inputs = inputs;
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

	public double getLf() {
		return lf;
	}

	public void setLf(double lf) {
		this.lf = lf;
	}
	
	
	public int getLayers() {
		return layers;
	}

	public Matrix[] getWeightArray() {
		return weightArray;
	}

	public Matrix[] getBiasArray() {
		return biasArray;
	}

	public Matrix[] getzArray() {
		return zArray;
	}

	public Matrix getwL() {
		return wL;
	}

}
