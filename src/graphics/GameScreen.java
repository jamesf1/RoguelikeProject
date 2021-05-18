package graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import game.Component;
import item.Equipment;
import item.Item;
import item.SlotType;
import mob.Player;

public abstract class GameScreen {
	public GameScreen subscreen;
	public static final TextColor sidebarColor = TextColor.ANSI.BLACK;
	public abstract void draw(TextGraphics t) throws IOException;
	public abstract GameScreen respond(KeyStroke key);
	
	public void writeInCenter(String string, TextGraphics t) {
		t.setForegroundColor(TextColor.ANSI.WHITE);
		String[] lines = string.split("\r\n|\r|\n");
		int maxLineLength = 0;
		for(int i = 0; i < lines.length; i++)
			if(lines[i].length() > maxLineLength)
				maxLineLength = lines[i].length();
		int height = lines.length;
		int centerX = Component.terminalWidth/2;
		int centerY = Component.terminalHeight/2;
		int startY = centerY - height/2;
		int endY = startY + height;
		//need to add support for multiline
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			int startX = centerX- line.length()/2;
			t.putString(startX, startY + i, line);
		}
		int rectangleStartX = centerX - maxLineLength/2;
		int rectangleEndX = rectangleStartX + maxLineLength;

		TerminalPosition upperLeft = new TerminalPosition(rectangleStartX-2, startY-2);
		TerminalPosition lowerLeft = new TerminalPosition(rectangleStartX-2, endY+2);
		TerminalPosition upperRight = new TerminalPosition(rectangleEndX+2, startY-2);
		TerminalPosition lowerRight = new TerminalPosition(rectangleEndX+2,endY+2);
		t.drawLine(upperLeft, upperRight, '-');
		t.drawLine(lowerLeft, lowerRight, '-');
		t.drawLine(upperLeft, lowerLeft, '|');
		t.drawLine(upperRight, lowerRight, '|');
	}
	
	public void drawSidebarDividers(TextGraphics t) {
		int startX = Component.terminalWidth - Component.infoPanelWidth;
		int endX = Component.terminalWidth;
		int endY = Component.terminalHeight-1;
		TerminalPosition upLeft = new TerminalPosition(startX, 0);
		TerminalPosition lowLeft = new TerminalPosition(startX, endY);
		TerminalPosition midLeft = new TerminalPosition(startX, endY/2);
		TerminalPosition midRight = new TerminalPosition(endX, endY/2);
		t.drawLine(upLeft, lowLeft, '|');
		t.drawLine(midLeft, midRight, '-');
	}
	public void writeInSidebar(TextGraphics t, ArrayList<String> messages) {
		if(messages.size()== 0 )return;
		int startX = Component.sidebarX;
		int endX = Component.terminalWidth-1;
		int endY = Component.terminalHeight-1;
		int startY = Component.lowerSidebarY;
		int y = startY;
		for(int i = messages.size()-1; i >= 0; i--) {
			int x = startX;
			for(char c : messages.get(i).toCharArray()) {
				t.setCharacter(x, y, c); 
				x++;
				if(x == endX) {
					y++;
					x = startX;
				}
				if(y == endY)
					return;
			}
			y++;
			y++;
			x = startX;
			if(y== endY)
				return;
		}
	}
	
	public void drawPlayerInfo(TextGraphics t, Player p) {
		int startY = 1;
		int startX = Component.terminalWidth - Component.infoPanelWidth + 1;
		int x = startX;
		int y = startY;
		String name = p.getName();
		t.putString(x, y, name);
		y++; y++;
		String hp = "HP " + p.getHp() + " / " + p.getMaxhp();
		String armor = "AV: " + p.getArmor();
		t.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
		t.putCSIStyledString(x, y, hp);
		y++;
		t.putCSIStyledString(x, y, armor);
	}
	
	public void drawPlayerEquipment(TextGraphics t, Player p, int x, int y) {
		Equipment equipment = p.getEquipment();
		Scanner scan = new Scanner(equipment.toString());
		while(scan.hasNext()) {
			
			t.putString(x, y, scan.nextLine());
			y++;
		}
		scan.close();
	}
	


}
