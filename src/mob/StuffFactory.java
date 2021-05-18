package mob;

import java.util.Map;

import com.googlecode.lanterna.TextColor;

import game.Component;
import game.Game;
import item.Item;
import room.Room;

public class StuffFactory {
	Room room;
	Map<String, MobInfo> mobMap;
	public StuffFactory(Room room) {
		this.room = room;
		this.mobMap = Component.mobMap;
	}
	
	public void createMob(String name, int x, int y) {
		
	}
	
	public Item createItem(String name, char c, TextColor color, int x, int y) {
		Item item = new Item(name, c, color);
		room.putItem(item, x, y);
		return item;
	}
	
	
	
	public void generateWolfPack(int level, int x, int y) {
		int numWolves = (int) Game.getRand(3, 8);
		Mob firstWolf = getMob("Wolf");
		Mob lastWolf = firstWolf;
		for(int wolfNum = 1; wolfNum <= numWolves; wolfNum++) {
			Mob wolf = getMob("Wolf");
			WolfAI ai = (WolfAI) wolf.getAi();
			WolfAI lastAI = (WolfAI) lastWolf.getAi();
			ai.setPrev(lastAI);
			lastAI.setNext(ai);
			lastWolf = wolf;
			//if this is the last wolf, link with the first
			if(wolfNum == numWolves) {
				WolfAI firstAI = (WolfAI) firstWolf.getAi();
				ai.setNext(firstAI);
				firstAI.setPrev(ai);
			}
			
			
			room.addMobAround(wolf, x, y);
		}
		
	}
	public void generateItem(Item item, int x, int y) {
		Item[] items = room.getItems(x, y);
		for(int i = 0; i < items.length; i++) {
			if(items[i] == null) {
				items[i] = item;
				break;
			}
			
		}
	}
	
	public Mob getMob(String name) {
		MobInfo info = mobMap.get(name);
		if(info == null)
			System.out.println("unable to get mob: " + name);
		return new Mob(name, info, room);
	}
}
