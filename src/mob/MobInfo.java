package mob;

import java.util.Scanner;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

import room.Room;

public class MobInfo {
	TextCharacter glyph;
	private TextColor color;
	int maxhp;
	int ap;
	int strength;
	int dex;
	String aiString;	
	String desc;
	
	
	public MobInfo(String string, String desc) {
		Scanner scan = new Scanner(string);
		char glyphChar = scan.next().charAt(0);
		String colorname = scan.next();
		color = TextColor.ANSI.valueOf(colorname);
		glyph = new TextCharacter(glyphChar, color, Room.getBackground());;
		maxhp = scan.nextInt();
		ap = scan.nextInt();
		strength = scan.nextInt();
		dex = scan.nextInt();
		aiString = scan.next();
		this.desc = desc;
		scan.close();

	}
}
