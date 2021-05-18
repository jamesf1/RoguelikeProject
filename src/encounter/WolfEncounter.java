package encounter;

import game.Game;
import mob.Player;
import room.Room;
import roombuilderencounter.WolfEncounterBuilder;

public class WolfEncounter extends Encounter {

public WolfEncounter(Player player, Room room) {
	super(player, room);
	double tension = Game.getRand(0, .4);
	String tensionString = tension > .2 ? "They turn on you, snarling." : "They barely seem to notice you.";
	this.text = "A pack of wolves crowds a fresh corpse.";
	this.text += "\n" + tensionString;
	
	this.choices = "A. Approach the body \n"
			+ "B. Back away slowly";
}
//choice will already be made uppercase by encounterscreen
//no need to have a default case as only valid choices will come through
public Encounter makeChoice(char choice) {
	if(choice == 'A')
		return new WolfEncounterBuilder(player, room);
	else
		return null;
}

}
