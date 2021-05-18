package overmap;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

import room.Room;
import tile.GrassTile;
import tile.Tile;

public class PlainsMap extends OvermapTile {
public PlainsMap() {
	int green = (int) (100 + Math.random()*110);
	TextColor color = new TextColor.RGB(119, green, 40);
	this.glyph = new TextCharacter('\'', color, getBackground());
}

@Override
public void generate() {
	Tile[][] roomMap = new Tile[Room.width][Room.height];
	for(int x = 0; x < Room.width; x++) {
		for(int y = 0; y < Room.height; y++) {
			roomMap[x][y] = new GrassTile();
		}
	}
	setRoom(new Room(roomMap));
}
}
