package graphics;

import java.io.IOException;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import game.Component;
import mob.Mob;
import mob.MobAI.State;
import mob.Player;

public abstract class LookSubscreen extends GameScreen {
	int x, y;
	public LookSubscreen(Player p) {
		this.x = p.getX();
		this.y = p.getY();
	}
	//make it blink?
	@Override
	public void draw(TextGraphics t) throws IOException {
		TextCharacter c = t.getCharacter(x, y);
		TextCharacter newC = c.withBackgroundColor(TextColor.ANSI.WHITE);

		t.setCharacter(x, y, newC);
		
		
		
	}

	@Override
	public GameScreen respond(KeyStroke key) {
		KeyType type = key.getKeyType();
		boolean playerMoved = false;
		switch(type) {
		case ArrowRight: moveTo(x+1, y);
		break;
		case ArrowLeft: moveTo(x-1, y);
		break;
		case ArrowDown: moveTo(x, y+1);
		break;
		case ArrowUp: moveTo(x,y-1);
		break;
		case Escape: return null;
		default:
			break;
		}
		return this;
	}
	public abstract boolean isValid(int x, int y);
	
	public boolean moveTo(int dx, int dy) {
		if(isValid(dx, dy)) {
			x = dx; y = dy;
			return true;
		}
		return false;
			
	}
	public void drawMobInfo(TextGraphics t, Mob mob) {
		String name = mob.getName();
		State state = mob.getState();
		TextColor stateColor = state.getColor();
		String description = mob.getDesc();
		int x = Component.sidebarX;
		int y = Component.lowerSidebarY;
		t.putString(x, y, name);
		y++; y++;
		t.setForegroundColor(stateColor);
		t.putCSIStyledString(x, y, state.toString());
		y++; y++;
		t.putString(x, y, mob.getDesc());
		
		
	}
	

}
