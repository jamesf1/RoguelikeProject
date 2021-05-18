package overmap;

import encounter.Encounter;
import game.Component;
import game.Game;
import mob.Player;


public class Overmap {
	private OvermapTile[][] overmap;
	private Player player;

	public static final int width = Component.terminalWidth - Component.infoPanelWidth;
	public static final int height = 44;
	private int level;
	
	
	public Overmap(int level) {
		overmap = new OvermapTile[width][height];
		this.level = level;
		initialize();
		generateMountains(1, 10,20);
		generateMountains(1, 20, 40);
		generateMountains(1,60, 80);
		generateDesert(1,20);
		generateDesert(1, 15);
		generateDesert(1, 10);
		generateDesert(1, 10);
		generateDesert(1, 10);
		generateDungeon();
	}
	
	private void initialize() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++)  {
				OvermapTile room = new PlainsMap();
				overmap[i][j] = room;
			}
		}
	}
	
	public void generateDungeon() {
		int x = (int) Game.getRand(width - 15, width - 2);
		int y = (int) Game.getRand(3, height - 3);
		overmap[x][y] = new DungeonMap();
		for(int dx = -1; dx <= 1; dx++) 
			for(int dy = -1; dy <= 1; dy++) 
				if(dx != 0 || dy != 0)
					overmap[x+dx][y+dy] = new WasteMap();
		
		
		
		
	}
	
	public void generateDesert(float prob, int radius) {
		if(Game.getProb(prob) == false) return;
		
		int centerX = (int) Game.getRand(0, width);
		int centerY = (int) Game.getRand(0, height);	
		
		int x = centerX - radius;
		int y = centerY;
		for(int dx = radius * -1; dx < radius; dx++) {
			//thicker at center
			int dist = Math.abs(dx);
			//proportion to which thickness decreases at the outside
			double inverseProportion = 1/Math.pow(dist, .5);
			int upwardThickness = (int) (inverseProportion * Math.random() * radius);
			int downwardThickness = (int) (inverseProportion * Math.random() * radius);
			if(isValid(x+dx, centerY) && overmap[x+dx][centerY].isMountain())
				break;
			for(int dy = y - downwardThickness; dy < y + upwardThickness; dy++) {
				if(isValid(x + dx, dy)) {
					if(overmap[x+dx][dy].isMountain())
						break;
					overmap[x + dx][dy] = new DesertMap(inverseProportion);
				}
				//if is mountain, stop generating desert

			}
			
			double rand = Math.random();
			//adjust y
			if(rand < .33) y --;
			else if(rand < .66) y++;
			
		}
	}
	
	//generate random slope, use y=mx+b
	public void generateMountains(float prob, int minLength, int maxLength) {
		if(Game.getProb(prob) == false) return;
		double slope = Game.getRand(-1,1);
		double length = Game.getRand(minLength, maxLength);
		int startX = (int) Game.getRand(0, width - length);
		int intercept = (int) Game.getRand(-20, height + 20);
		
		int blue = 190;
		for(int x = startX; x < startX + length; x++) {
			blue += Game.getRand(-15, 15);
			if(blue >= 228) blue -=15;
			
			int y = (int) (slope*x + intercept);

			int upY = (int) Game.getRand(y, y+2);
			int downY = (int) Game.getRand(y-2, y);
			for(y = downY; y <= upY; y++) {
				if(isValid(x, y))
						overmap[x][y] = new MountainMap(blue);
			}
		}
	}
	
	
	
	public boolean isValid(int x, int y) {
		if(x >= 0 && x < width && y >= 0 && y < height) return true;
		else return false;
	}
	
	public OvermapTile getRoom(int x, int y) {
		return overmap[x][y];
	}
	
	//return an encounter if one is to be had, else return null;
	public Encounter getEncounter() {
		int turnsSinceEncounter = player.getTurnsSinceEncounter();
		double inverseChance = Math.random()*(turnsSinceEncounter+10);
		if(inverseChance > 10)
			return overmap[player.getOvermapX()][player.getOvermapY()].getEncounter(player);
		return null;
	}
	
	
	public Player getPlayer() {return player;}
	
	public void setPlayer(Player p) {this.player = p;}
}
