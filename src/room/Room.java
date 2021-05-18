package room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.googlecode.lanterna.TextColor;

import game.Component;
import game.Game;
import item.Item;
import mob.Mob;
import mob.Player;
import mob.StuffFactory;
import tile.Tile;

public class Room {
public static final int width = Component.terminalWidth - Component.infoPanelWidth;
public static final int height = Component.terminalHeight;

private Tile[][] room;
private Item[][][] items;
private int[][] toPlayerMap;
private List<Mob> mobs;
private StuffFactory mobFactory;
private Player player;
private static TextColor background = new TextColor.RGB(0, 0, 0);
private boolean mustDefeatMobs;
private int z;
private ArrayList<String> messages;
boolean messagesThisTurn = false;
String messageDivider;
int mobNum;

//insert a queue to deal with speed
public void update() {
	messagesThisTurn = false;
	generateToPlayerMap();
	boolean mobWithAP = true;
	while(mobWithAP) {
		mobWithAP = false;
		for(Mob mob : mobs) { 
			if(mob.getAp() >= 100 && mob.isAlive() && !mob.getClass().equals(Player.class)) {
				mob.update();
				mob.incrementAp(-100);
				if(mob.getAp() >= 100)
					mobWithAP = true;
			}

		}
	}
	
	Collection<Mob> deadMobs = new ArrayList<Mob>();
	for(Mob mob : mobs) {
		if(!mob.isAlive())
			deadMobs.add(mob);
		mob.incrementAp(mob.getMaxAP());;
	}
	mobs.removeAll(deadMobs);
	
	if(messagesThisTurn) {
		addMessage(messageDivider);
	}
}



public Room(Tile[][] room) {
	this.room = room;
	mobNum=0;
	messages = new ArrayList<String>();
	mobs = new ArrayList<Mob>();
	items = new Item[Room.width][Room.height][100];
	mustDefeatMobs = false;
	messageDivider = "";
	for(int x = 0; x < Component.infoPanelWidth-3; x++)
		messageDivider += "-";
}

public void addMessage(String message) {
	messages.add(message);
	messagesThisTurn = true;
}
public ArrayList<String> getMessages() {return messages;}

public Tile getTile(int x, int y) {
	return room[x][y];
}
public void setStuffFactory(StuffFactory mobFactory) {
	this.mobFactory = mobFactory;
}
public StuffFactory getMobFactory() {return mobFactory;}

public void addMob(Mob mob) {
	mobs.add(mob);
}
public void addMobAround(Mob mob, int x, int y) {
	int mobX = -1;
	int mobY = -1;
	int tries = 0;
	do {
		mobX = (int) (x + Game.getRand(-10, 10));
		mobY = (int) (y + Game.getRand(-10, 10));
		tries++;
	} while(!room[mobX][mobY].isPassable() || mobAt(mobX, mobY) && tries < 100);
	mob.setX(mobX);
	mob.setY(mobY);
	addMob(mob);
	
}

public boolean mobAt(int x, int y) {
	for(Mob mob : mobs) {
		if(mob.getX() == x && mob.getY() == y)
			return true;
	}
	return false;
}

public List<Mob> getMobs() {
	return mobs;
}

public void setPlayer(Player p, int x, int y) {
	this.player = p;
	player.setX(x);
	player.setY(y);
	mobs.add(p);
	generateToPlayerMap();
	
	
}
public Player getPlayer() {return player;}
public static TextColor getBackground() {return background;}

public Item[][][] getItems() {
	return items;
}
public Item[] getItems(int x, int y) {
	return items[x][y];
}

public void putItem(Item item, int x, int y) {
	Item[] items = getItems(x, y);
	for(int i = 0; i < items.length; i++) {
		if(items[i] == null) {
			items[i] = item;
			break;
		}
	}
}

//return if the tile is within bounds
public boolean isValid(int x, int y) {	
	if(x > 0 && x < Room.width && y > 0 && y < Room.height)
		return true;
	else return false;
}

//return if a mob is here
public boolean isMobHere(int x, int y) {
	for(Mob mob : mobs) 
		if(mob.getX() ==x && mob.getY() == y)
			return true;
	if(player.getX() == x && player.getY() == y)
		return true;
	return false;
}

//return if tile is moveable
public boolean isPassable(int x, int y) {
	if(room[x][y].isPassable())
		return true;
	return false;
}
 
private void generateToPlayerMap() {
	toPlayerMap = generateDjikstra(player.getX(), player.getY(), null, null);
}


public int[][] getToPlayerMap() {
	return toPlayerMap;
}

public void setMustDefeatMobs() {
	this.mustDefeatMobs = true;
}
public boolean mustDefeatMobs() {
	return mustDefeatMobs;
}

public boolean canExit() {
	if(z == 0) 
		return true;
	return false;
}
public int getZ() {return z;}

public Mob getMobAt(int x, int y) {
	for(Mob mob : mobs) 
		if(mob.getX() == x && mob.getY() == y)
			return mob;
	if(player.getX() == x && player.getY() == y)
		return player;
	return null;
}

public void removeMob(Mob mob) {
	mobs.remove(mob);
}


public int[][] generateDjikstra(int targetX, int targetY, int[] excludeX, int[] excludeY) {
	int[][] djikstraMap = new int[width][height];
	for(int x = 0; x< width; x++) 
		for(int y = 0; y < height; y++) 
			djikstraMap[x][y] = 9999;
	djikstraMap[player.getX()][player.getY()] = 0;
	
	int changes = 1;
	while(changes  != 0) {
		changes = 0;
		for(int x = 0; x< width; x++) {
			for(int y = 0; y < height; y++) {
				//if lowest neighbor is less than current D, set current d to lowest nbor + 1
				int currentD = djikstraMap[x][y];
				int lowestNeighbor = getLowestNeighbor(djikstraMap, x, y, excludeX, excludeY);
				if(lowestNeighbor + 1 < currentD) {
					changes++;
					djikstraMap[x][y] = lowestNeighbor + 1;
				}
			}
		}
	}
	return djikstraMap;
}

private int getLowestNeighbor(int[][] djikstraMap, int x, int y, int[] excludeX, int[] excludeY) {
	int neighborD = 999;
	int lowestNeighbor = 999;
	if(isValid(x-1, y) && isPassable(x-1, y) && !containsCoordinate(excludeX, excludeY, x-1, y)) {
		neighborD = djikstraMap[x-1][y];
		if(neighborD < lowestNeighbor)
			lowestNeighbor = neighborD;
	}
	if(isValid(x+1, y) && isPassable(x+1, y) && !containsCoordinate(excludeX, excludeY, x+1, y)) {
		neighborD = djikstraMap[x+1][y];
		if(neighborD < lowestNeighbor)
			lowestNeighbor = neighborD;
	}
	if(isValid(x, y-1) && isPassable(x, y-1) && !containsCoordinate(excludeX, excludeY, x, y-1)) {
		neighborD = djikstraMap[x][y-1];
		if(neighborD < lowestNeighbor)
			lowestNeighbor = neighborD;
	}
	if(isValid(x, y+1) && isPassable(x, y+1) && !containsCoordinate(excludeX, excludeY, x, y+1)) { 
		neighborD = djikstraMap[x][y+1];
		if(neighborD < lowestNeighbor)
			lowestNeighbor = neighborD;
	}
	return lowestNeighbor;
}
private int getLowestNeighbor(int[][] djikstraMap, int x, int y) {
	int neighborD = 999;
	int lowestNeighbor = 999;
	if(isValid(x-1, y) && isPassable(x-1, y)) {
		neighborD = djikstraMap[x-1][y];
		if(neighborD < lowestNeighbor)
			lowestNeighbor = neighborD;
	}
	if(isValid(x+1, y) && isPassable(x+1, y)) {
		neighborD = djikstraMap[x+1][y];
		if(neighborD < lowestNeighbor)
			lowestNeighbor = neighborD;
	}
	if(isValid(x, y-1) && isPassable(x, y-1)) {
		neighborD = djikstraMap[x][y-1];
		if(neighborD < lowestNeighbor)
			lowestNeighbor = neighborD;
	}
	if(isValid(x, y+1) && isPassable(x, y+1)) { 
		neighborD = djikstraMap[x][y+1];
		if(neighborD < lowestNeighbor)
			lowestNeighbor = neighborD;
	}
	return lowestNeighbor;
}
private boolean containsCoordinate(int[] xArr, int[] yArr, int x, int y) {
	if(xArr == null)
		return false;
	for(int i = 0; i < xArr.length; i++) {
		if(xArr[i] == x && yArr[i] == y)
			return true;
	}
	return false;
}

}
