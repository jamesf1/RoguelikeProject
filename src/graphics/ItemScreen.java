package graphics;

import java.io.IOException;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import item.Item;
import mob.Player;

public class ItemScreen extends InventoryScreen {
	Item item;
	GameScreen parent;
	boolean equipping;
	public ItemScreen(Item i, GameScreen parent, Player player) {
		super(parent, player);
		this.item = i;
	}

	@Override
	public void draw(TextGraphics t) throws IOException {
		drawSidebarDividers(t);
		drawPlayerInfo(t, player);
		if(equipping)
			assignAndDrawEquipment(player.getEquipment(), t);
		

		
	}

	@Override
	public GameScreen respond(KeyStroke key) {
		// TODO Auto-generated method stub
		return null;
	}
}