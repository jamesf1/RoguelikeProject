package overmap;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

import game.Game;

public class DesertMap extends OvermapTile {

	public DesertMap(double desertification) {
		int green = (int) (100 + desertification * 80 * Game.getRand(.9, 1));
		if( green >253) green = 253;
		TextColor col = new TextColor.RGB(245, green,  66);
		this.glyph = new TextCharacter('~', col, getBackground());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void generate() {
		// TODO Auto-generated method stub
		
	}
}
