package roombuilderencounter;

import encounter.Encounter;
import graphics.GameScreen;
import graphics.RoomScreen;
import mob.Player;
import room.Room;

public class RoomBuilderEncounter extends Encounter{
	@Override
	public boolean isRoomBuilder() {return true;}

	public RoomBuilderEncounter(Player player, Room room) {
		super(player, room);
		player.setRoom(room);

		// TODO Auto-generated constructor stub
	}
	

	@Override
	public GameScreen newScreen(GameScreen overmapScreen) {
		return new RoomScreen(getRoom(), overmapScreen, getPlayer());
	}

}
