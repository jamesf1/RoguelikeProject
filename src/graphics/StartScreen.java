package graphics;

import java.io.IOException;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import mob.Player;
import overmap.Overmap;

public class StartScreen extends GameScreen {
	final String title = "Rogue";
	final String subtitle = "A game of roguery";
	
	@Override
	public void draw(TextGraphics t) throws IOException {
		writeInCenter(title + "\n" + subtitle, t);
	}

	@Override
	public GameScreen respond(KeyStroke key) {
		if(key.getKeyType().equals(KeyType.Enter)) {
			Overmap map = new Overmap(0);
			Player p = new Player(map, "Joe");
			return new OvermapScreen(map);
		}
		else return this;
	}

}
