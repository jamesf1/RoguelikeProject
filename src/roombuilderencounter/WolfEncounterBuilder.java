package roombuilderencounter;

import com.googlecode.lanterna.TextColor;

import mob.Player;
import mob.StuffFactory;
import room.Room;

public class WolfEncounterBuilder extends RoomBuilderEncounter {

	public WolfEncounterBuilder(Player player, Room room) {
		super(player, room);	

		StuffFactory stuffFactory = new StuffFactory(room);
		room.setStuffFactory(stuffFactory);
		room.setPlayer(player, Room.width/2, Room.height-2);
		stuffFactory.createItem("corpse", '%' , TextColor.ANSI.WHITE, Room.width/2, Room.height/2);
		stuffFactory.generateWolfPack(1, Room.width /2, Room.height/2);
		
		// TODO Auto-generated constructor stub
	}
	
	

}
