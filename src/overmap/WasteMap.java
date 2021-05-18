package overmap;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class WasteMap extends OvermapTile{
	public WasteMap() {
		TextColor col = new TextColor.RGB(105,105,105);
		glyph = new TextCharacter('-', col, getBackground());
	}
	@Override
	public void generate() {
		// TODO Auto-generated method stub
		
	}
	
}
