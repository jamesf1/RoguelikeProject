package graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import item.Item;
import mob.Mob;
import mob.Player;
import room.Room;
import tile.Tile;

public class RoomScreen extends GameScreen {
Room room;
GameScreen parent;
GameScreen subScreen;
public RoomScreen(Room room, GameScreen parent, Player p) {
	setRoom(room);
	p.setRoom(room);
	//this will have to be overridden otherwise player will always spawn same place
	this.parent = parent;
}
	
@Override
public void draw(TextGraphics t) throws IOException { 
	//displaytiles
	Item[][][] items = room.getItems();
	for(int x = 0; x< Room.width; x++) {
		for(int y = 0; y < Room.height; y++) {
			Tile tile = room.getTile(x, y);
			TextCharacter glyph = tile.getGlyph();
			t.setCharacter(x, y, glyph);
			//display items
			for(int i = 0; i < 100; i++) {
				Item item = items[x][y][i];
				if(item != null)
					t.setCharacter(x, y, item.getGlyph());
				break;
			}
		}
	}
	//display mobs
	List<Mob> mobs = room.getMobs();
	for(Mob mob : mobs) 
		mob.draw(t);;
	//display player
	Player p = room.getPlayer();
	t.setCharacter(p.getX(), p.getY(), p.getGlyph());
	
	drawSidebarDividers(t);
	ArrayList<String> messages = room.getMessages();
	if(subscreen == null)
		writeInSidebar(t, messages);
	
	if(subscreen != null)
		subscreen.draw(t);

}
@Override
public GameScreen respond(KeyStroke key) {
	if(subscreen != null) {
		subscreen = subscreen.respond(key);
		return this;
	}
	
	boolean playerMoved = false;
	Player p = room.getPlayer();
	KeyType type = key.getKeyType();
	if(!type.equals(KeyType.Character)) {
		switch(type) {
		case ArrowRight: playerMoved = p.moveBy(1,0);
		break;
		case ArrowLeft: playerMoved = p.moveBy(-1, 0);
		break;
		case ArrowDown: playerMoved = p.moveBy(0, 1);
		break;
		case ArrowUp: playerMoved = p.moveBy(0, -1);
		break;
		default:
			break;
		}
	} else {
		if(key.getCharacter().equals('<')){
			if(room.canExit()) {
				if(room.getZ() == 0) {
					return parent;
				}
			}
		} else if(key.getCharacter().equals('i')) {
			return new InventoryScreen(this, p);
		} else if(key.getCharacter().equals(';')) {
			subscreen = new RoomLookScreen(p, room);
		}
	}
		
	
	if(playerMoved) {
		room.update();
	}
	return this;
	}


public void setRoom(Room room) {this.room = room;}

}

