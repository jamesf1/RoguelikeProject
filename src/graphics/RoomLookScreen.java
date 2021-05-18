package graphics;

import java.io.IOException;

import com.googlecode.lanterna.graphics.TextGraphics;

import mob.Player;
import room.Room;

public class RoomLookScreen extends LookSubscreen {
	Room room;
	public RoomLookScreen(Player p, Room room) {
		super(p);
		this.room = room;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(int x, int y) {
		// TODO Auto-generated method stub
		if(x >= 0 && x < Room.width && y >= 0 && y < Room.height)
			return true;
		return false;
	}
	
	

	@Override
	public void draw(TextGraphics t) throws IOException {
		super.draw(t);
		if(room.isMobHere(x, y)) {
			drawMobInfo(t, room.getMobAt(x, y));
		}
	}
}
