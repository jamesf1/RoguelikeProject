package overmap;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

import room.Room;
import tile.Tile;
import tile.WasteTile;

public class DungeonMap extends OvermapTile {
	public DungeonMap() {
		TextColor col = TextColor.ANSI.WHITE;
		glyph = new TextCharacter('>', col, getBackground());
	}
	@Override
	public void generate() {
		Tile[][] roomMap = new Tile[Room.width][Room.height];
		for(int x = 0; x < Room.width; x++) {
			for(int y = 0; y < Room.height; y++) {
				roomMap[x][y] = new WasteTile();
			}
		}
		room = new Room(roomMap);
		
	}
	@Override
	public boolean enterable() {return true;}

}
