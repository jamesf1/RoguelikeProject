package tile;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class StairTile extends Tile {
	public StairTile() {
		TextColor col = TextColor.ANSI.YELLOW;
		setGlyph(new TextCharacter('>', col, getBackground()));
	}
}
