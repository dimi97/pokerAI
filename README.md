Welcome to the pokerAI wiki!

# Version V1:



pokerAI.name="**PzkD**";

![123](https://www.gutshotmagazine.com/uploads/news/5691.jpg)

## The Neural Network:

### General Information:
The neural network is fed a 10-dimensional vector:

1. **PzkD Card** (The card the AI gets dealt).
2. **PzkD Money** (The amount of money the AI has available to bet).
3. **Villain Money** (The amount of money the Opponent has available to bet).
4. **Pot Money** (The amount of money currently in the pot).
5. **PzkD Position** (The position of the AI, indicating when it's the AI turn to act).
6. **Previous Bets** (The amount of times the minimum calling amount has been raised. AKA 3bet Pots,4bet Pots etc.). 
7. **Active Players** (The amount of players that are currently still playing).
8. **Big Blind Value** (The mandatory bet for the player currently in the "Big blind" position).
9. **Current round PzkD bet** (The amount of money the AI has put into the pot in the current round).
10. **Current round max bet** (The biggest amount of money put into the pot in this round. All players have to match this amount to continue playing).

After that the vector goes into one hidden layer before exiting the neural net, using the sigmoid activating function. The single output of the Neural Network indicates the percentage of the AI's current stack that should go into the pot.

![Neural Network](https://cdn-images-1.medium.com/max/1200/1*RGV6Bb3ChmVWsA8Q6Qth6Q.png)

### Training (Genetic Algorithm):

1000 AIs are created, and each has a random set of weights. Each AI plays 10.000 rounds of poker against a Bot Opponent with clear playing instructions (ex. Call all hands above 5, raise all hands above 8). After each round the winnings of the AI are counted and added to its total. The AIs Money as well as its opponents money is renewed. The best performing AIs (fitness function:Amount of money earned) after the 10.000 rounds are then used to generate a new generation of 1000 AIs and so on (genetic algorithm).

![Genetic Algorithm](https://s3-ap-south-1.amazonaws.com/av-blog-media/wp-content/uploads/2017/07/22175311/gadiagram-300x196.png)

### Second Stage:
When the first round of training gives sufficient results, the game gets updated. A second card is added and the game will continue post-flop, making the game a genuine No Limit-Texas Holdem Poker game. Neural Networks will be created that are experts in flop,turn and river play.

![Texas Holdem](http://www.topfivepoker.com/wp-content/uploads/texasholdem.jpg)

### Second Stage training:
First stage training will be used again until the AI has reached a significant level o play.

### Second Stage training vol. 2:
 After the AI has mastered playing against the simple bot, the AI will train playing against its self covering more variety of playing styles.


