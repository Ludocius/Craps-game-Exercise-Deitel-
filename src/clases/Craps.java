/**
 * 
 */
package clases;

import java.security.SecureRandom;

/**
 * (Game of Craps) Write an application that runs 1,000,000 games of craps (Fig. 6.8) and
 * answers the following questions:
 * a) How many games are won on the first roll, second roll, …, twentieth roll and after the
 * twentieth roll?
 * 
 * b) How many games are lost on the first roll, second roll, …, twentieth roll and after the
 * twentieth roll?
 * 
 * c) What are the chances of winning at craps? [Note: You should discover that craps is one
 * of the fairest casino games. What do you suppose this means?]
 * 
 * d) What is the average length of a game of craps?
 * 
 * e) Do the chances of winning improve with the length of the game?
 * 
 * chances of winning in which you have 10 turns you'd get a probability of winning the game as: 0.2%
 * chances of winning in which you have 100 turns you'd get a probability of winning the game as: 0.26%
 * chances of winning in which you have 500 turns you'd get a probability of winning the game as: 0.216%
 * chances of winning in which you have 1000 turns you'd get a probability of winning the game as: 0.227%
 * chances of winning in which you have 10000 turns you'd get a probability of winning the game as: 0.2206%
 * chances of winning in which you have 500000 turns you'd get a probability of winning the game as: 0.21995%
 * chances of winning in which you have 1000000 turns you'd get a probability of winning the game as: 0.222688%
 *
 */

public class Craps
{

	private static final SecureRandom randomNumbers = new SecureRandom();
	
	private enum Status{
		CONTINUE,
		WON,
		LOST
	};
	
	private static final int SNAKE_EYES = 2;
	private static final int TREY = 3;
	private static final int SEVEN = 7;
	private static final int YO_LEVEN = 11;
	private static final int BOX_CARS = 12;
	private static int[] wins = new int[100];
	private static int[] loses = new int[1000];
	private static final int MAX_PLAYS = 10;
	private static int counter = 1;
	private static int[] storesTurns = new int[MAX_PLAYS + 1];
	
	public static int getCounter()
	{
		return counter;
	}
	
	public static void setCounter(int counter)
	{
		Craps.counter = counter;
	}
	
	public static int[] getStoresTurns()
	{
		return storesTurns;
	}
	
	public static void setStoresTurns(int index, int value)
	{
		Craps.storesTurns[index] = value;
	}
	
	public static int getWins(int index)
	{
		return wins[index];
	}
	
	public static int getLoses(int index)
	{
		return loses[index];
	}
	
	public static void setWins(int wins)
	{
		Craps.wins[wins] += 1;
	}
	
	public static void setLoses(int loses)
	{
		Craps.loses[loses] += 1;
	}
	
	public static void main(String[] args)
	{

		for(int counter = 1; counter <= MAX_PLAYS; ++counter )
		{
			int sumOfDice = rollDice();
			int myPoint = 0;
			Status gameStatus;
			String logMessage = "";
			gameStatus = isFirstWinOrLose(sumOfDice, myPoint);
			gameStatus = looping(gameStatus); 
			logMessage = message(gameStatus);
			//System.out.println("PLAY NUMBER " + counter  + " TIMES ROLLED: " + getCounter() + " STATUS: " + logMessage + "\n");
			setStoresTurns(counter,getCounter());
			setCounter(1);
		}
		
		System.out.println("Wins");
		for(int index = 1; index < wins.length; ++index)
		{
			if(getWins(index) > 0)
			{
				System.out.println("Turn: " + index + " amount " + getWins(index));
			}
		}
		
		System.out.println("\nLoses");
		for(int index = 1; index < loses.length; ++index)
		{
			if(getLoses(index) > 0)
			{
				System.out.println("Turn: " + index + " amount " + getLoses(index));
			}
		}
		
		//Estimation[Chances of winning]
		System.out.println("\nChances of winning in which you have " + MAX_PLAYS 
				+ " turns you'd get a probability of winning the game as: " + chancesOfWinning(wins) + "%");
		//Average
		System.out.println("The average length of a game of craps is: " + averageTurn(getStoresTurns()));
	}
	
	public static double averageTurn(int[] storeTurns)
	{
		double average = 0.0;
		int total = 0;
		
		for(int values: storeTurns)
		{
			total += values;
		}
		average = total / storeTurns.length;
		return average;
	}
	
	public static double chancesOfWinning(int[] wins)
	{
		double chancesEstimation  = 0.0;
		int sum = 0;
		for(int value: wins)
		{
			sum += value;
		}
		chancesEstimation = (double)sum / MAX_PLAYS;
		
		return chancesEstimation;
	}
	
	public static Status isFirstWinOrLose(int sumOfDice, int myPoint )
	{
		Status gameStatus;
		switch (sumOfDice) {
		case SEVEN:
		case YO_LEVEN:
			gameStatus = Status.WON;
			setCounter(counter + 1);
			setWins(getCounter());
			break;
		case SNAKE_EYES:
		case TREY:
		case BOX_CARS:
			gameStatus = Status.LOST;
			setCounter(counter + 1);
			setLoses(getCounter());
			break;
		default:
			gameStatus = Status.CONTINUE;
			myPoint = sumOfDice;
			///System.out.printf("Point is %d%n", myPoint);
			setCounter(counter + 1);
			break;
		}
		return gameStatus;
	}
	
	public static Status looping(Status gameStatus)
	{
		int sumOfDice = rollDice();
		int myPoint = 0;
		while(gameStatus == Status.CONTINUE)
		{
			sumOfDice = rollDice();
			setCounter(counter + 1);
			if(sumOfDice == myPoint)
			{
				gameStatus = Status.WON;
				setWins(getCounter());
			}
			else if(sumOfDice == SEVEN)
			{
				gameStatus = Status.LOST;
				setLoses(getCounter());
			}
		}
		
		return gameStatus;
	}
	

	public static String message(Status gameStatus)
	{
		String stateMessage = "";
		
		if(gameStatus == Status.WON)
		{
			stateMessage = "Player wins";
		}
		else 
		{
			stateMessage = "Player loses";
		}
		return stateMessage;
	}
	
	
	public static int rollDice()
	{
		int die1 = 1 + randomNumbers.nextInt(6);
		int die2 = 1 + randomNumbers.nextInt(6);
		int sum = die1 + die2;
		//System.out.printf("Player rolled %d + %d = %d%n", die1, die2, sum);
		return sum;
	}
}
