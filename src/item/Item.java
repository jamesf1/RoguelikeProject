package item;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

import room.Room;

public class Item {
	TextCharacter glyph;
	enum ItemTag {
		
	}
	boolean butcherable;
	String name;
	public Item(String name, char c, TextColor color) {
		this.name = name;
		glyph = new TextCharacter(c, color, Room.getBackground());
		butcherable = false;
	}

	public Item() {

	}

	public TextCharacter getGlyph() {
		return glyph;
	}
	
	public void setButcherable() {this.butcherable = true;}
	public boolean isButcherable() {return butcherable;}
	
	public String getName() {return name;}
	
	//return a list of important variables
	public static String getFormattedFieldList() {
		return null;
	}
	//return the values of the variables
	public String getFormattedFields() {
		return null;
	}
	
}
