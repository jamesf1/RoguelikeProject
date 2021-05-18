package tile;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class WasteTile extends Tile{
	public WasteTile() {
		TextColor col = new TextColor.RGB(105,105,105);
		setGlyph(new TextCharacter('-', col, getBackground()));
	}
}
