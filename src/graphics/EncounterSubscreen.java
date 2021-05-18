package graphics;

import java.io.IOException;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import encounter.Encounter;

public class EncounterSubscreen extends GameScreen {
	public GameScreen overmapScreen;
	Encounter encounter;

	public EncounterSubscreen(Encounter encounter, GameScreen overmapScreen) {
		this.encounter = encounter;
		this.overmapScreen = overmapScreen;
	}
	@Override
	public void draw(TextGraphics t) throws IOException {
		overmapScreen.draw(t);
		String text = encounter.getText();
		String choices = encounter.getChoices();
		writeInCenter(text + "\n" + choices, t);
	}
	@Override
	public GameScreen respond(KeyStroke key) {
		//determine if encounter is valid
		if(!encounter.validChoice(key)) 
			return this;
		char c = key.getCharacter();
		c = Character.toUpperCase(c);
		Encounter next  = encounter.makeChoice(c);
		//go back to overmap if null
		if(next == null)
			return overmapScreen;
		else
			return next.newScreen(overmapScreen);
	}
}
