package graphics;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import encounter.Encounter;
import mob.Player;
import overmap.Overmap;
import overmap.OvermapTile;
import room.Room;

public class OvermapScreen extends GameScreen {
	private Overmap map;
	private Player player;
	Room room;
	public OvermapScreen(Overmap overmap) {
		this.map = overmap;
		player = map.getPlayer();
		
	}
	@Override
	public void draw(TextGraphics t) throws IOException {
		
		int height = Overmap.height;
		int width = Overmap.width;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				OvermapTile room = map.getRoom(x, y);

				TextCharacter glyph = room.getGlyph();
				TerminalPosition pos = new TerminalPosition(x, y);
				t.setCharacter(pos, glyph);
			}
		}
		Player p = map.getPlayer();
		int playerX = p.getOvermapX();
		int playerY = p.getOvermapY();
		t.setCharacter(new TerminalPosition(playerX, playerY), '@');
		drawSidebarDividers(t);
		drawPlayerInfo(t, p);
	}
	//return an encounterscreen if there is an encounter and set this as parentscreen
	@Override
	public GameScreen respond(KeyStroke key) {
		KeyType type = key.getKeyType();
		boolean playerMoved = false;
		if(key.getCharacter() == null) {
			switch(type) {
			case ArrowRight: playerMoved = player.moveOvermap(1,0);
			break;
			case ArrowLeft: playerMoved = player.moveOvermap(-1, 0);
			break;
			case ArrowDown: playerMoved = player.moveOvermap(0, 1);
			break;
			case ArrowUp: playerMoved = player.moveOvermap(0, -1);
			break;
			default:
				break;
			}
		} else {
			char c = key.getCharacter();
			if(c == '>') {
				 OvermapTile mapTile = map.getRoom(player.getOvermapX(), player.getOvermapY());
				 if(mapTile.enterable()) {
					 Room room = mapTile.getRoom();
					 room.setPlayer(player, Room.width/2, Room.height-1);
					 return new RoomScreen(mapTile.getRoom(), this, player);
				 }
			} else if(key.getCharacter().equals('i')) 
				return new InventoryScreen(this, player);
		}
		
		//try and get an encounter and display it
		if(playerMoved) {
			Encounter encounter = map.getEncounter();
			if(encounter != null)
				return new EncounterSubscreen(encounter, this);
		}
		

		return this;
		
	}
	
	
	public void displayEncounter(Encounter encounter) {
		subscreen = new EncounterSubscreen(encounter, this);
	}
	public Player getPlayer() {
		return player;
	}

}
