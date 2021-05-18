package tile;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public abstract class Tile {
TextCharacter glyph;
TextColor background;
boolean isPassable = true;
public TextColor getBackground() {return TextColor.ANSI.BLACK;}
public TextCharacter getGlyph() {return glyph;}
public void setGlyph(TextCharacter glyph) {this.glyph = glyph;}
public boolean isPassable() {return isPassable;}


}
