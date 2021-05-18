package overmap;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class MountainMap extends OvermapTile {
	public MountainMap(int blue) {
		TextColor col = new TextColor.RGB(223, 235, blue);
		this.glyph = new TextCharacter('^', col, getBackground());
	}

	@Override
	public void generate() {
		// TODO Auto-generated method stub
		
	}
}
