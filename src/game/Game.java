package game;

import java.util.Random;

public class Game {

	
	public static boolean getProb(double prob) {
		double rand = Math.random();
		if(rand < prob) return true;
		else return false;
	}
	public static double getRand(double min, double max) {
		return Math.random()* (max - min) + min;
	}
	public static int getRand(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
