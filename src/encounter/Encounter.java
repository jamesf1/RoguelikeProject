package encounter;

import com.googlecode.lanterna.input.KeyStroke;

import graphics.EncounterSubscreen;
import graphics.GameScreen;
import mob.Player;
import room.Room;

public abstract class Encounter {
String text;
String choices;
Player player;
Room room;
 
public String getText() {return text;}
public String getChoices() {return choices;}

public Encounter(Player player, Room room) {setPlayer(player);setRoom(room); player.resetTurnsSinceEncounter();}

//return null if returning to overmap
//return another encounter if more choices are to be presented
//return a roombuilder encounter if a room is to be generated
public Encounter makeChoice(char choice) {
	return null;
}
public boolean validChoice(KeyStroke key) {
	if(key.getCharacter() == null) return false;
	char choice = key.getCharacter();
	String c = (choice + ".").toUpperCase();
	if(choices.contains(c)) return true;
	else return false;
}
public boolean isRoomBuilder() {return false;}

public GameScreen newScreen(GameScreen parentScreen) {return new EncounterSubscreen(this, parentScreen);}
public Player getPlayer() {
	return player;
}
public void setPlayer(Player player) {
	this.player = player;
}
public Room getRoom() {
	return room;
}
public void setRoom(Room room) {
	this.room = room;
}

}
