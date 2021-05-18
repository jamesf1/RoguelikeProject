package overmap;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

import encounter.Encounter;
import encounter.WolfEncounter;
import mob.Player;
import room.Room;

public abstract class OvermapTile {
public final int height = 45;
public final int width = 150;
TextColor background;
TextCharacter glyph;
Room room;

public TextCharacter getGlyph() {return glyph;}
public void setGlyph(TextCharacter glyph) {this.glyph = glyph;}
public Room getRoom() {
	if(room == null)
		generate();
	return room;
	}

public TextColor getBackground() {
	return new TextColor.RGB(0, 0, 0);
}

public boolean isMountain() {
	if(this.getClass().equals(MountainMap.class)) return true;
	else return false;
}

public abstract void generate();

public Encounter getEncounter(Player player) { 
	
	int chance = (int) (Math.random() * 100);
	Room room = getRoom();
	if(chance > 0) {
		generate();
		return new WolfEncounter(player, room);
	}
	return null;
}
public void setRoom(Room room) {
	this.room = room;
}

public boolean enterable() {
	return false;
}





}
