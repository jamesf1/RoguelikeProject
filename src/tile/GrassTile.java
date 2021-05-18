package tile;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class GrassTile extends Tile{
public GrassTile() {
	setGlyph(new TextCharacter('\'', TextColor.ANSI.GREEN, getBackground()));
}
public GrassTile(int green) {
	//TextColor color = new TextColor.RGB()
}
}
