package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import graphics.GameScreen;
import graphics.StartScreen;
import item.ItemInfo;
import mob.MobInfo;

public class Component {
	public static final int terminalHeight = 45;
	public static final int terminalWidth = 150;
	public static final int infoPanelWidth = 30; 
	public static final int lowerSidebarY = (terminalHeight- 1)/2 + 1;
	public static final int sidebarX = terminalWidth - Component.infoPanelWidth + 1;
	
	public static Map<String, MobInfo> mobMap;
	public static Map<String, ItemInfo> itemMap;
	private GameScreen screen;
	
	public static void main(String[] args) {
		new Component();
	}
	
	public Component() {
		loadMobs();
		//loadItems();
		DefaultTerminalFactory tFact = new DefaultTerminalFactory();
		TerminalSize size = new TerminalSize(terminalWidth, terminalHeight);
		tFact.setInitialTerminalSize(size);
		TerminalScreen termScreen = null;
		try {
			termScreen = tFact.createScreen();
			termScreen.startScreen();
			termScreen.setCursorPosition(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		screen = new StartScreen();


		
		
		while(true) {
			try {
				TextGraphics t = termScreen.newTextGraphics();
				screen.draw(t);
				termScreen.refresh();
				KeyStroke key = termScreen.readInput();
				screen = screen.respond(key);
				termScreen.clear();

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*
		try {
			key = screen.readInput();
			Character c = key.getCharacter();
			if(c.equals('o'))
				screen.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		
		

	}
	
	public void loadMobs() {
		mobMap = new HashMap<String, MobInfo>();
		File file = new File("mobs");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("cant open mobs file");
			e.printStackTrace();
		}
		scan.nextLine();
		while(scan.hasNext()) {
			String name = scan.next();
			String info = scan.nextLine();
			String desc = "";
			String line = scan.nextLine();
			while(!line.equals("|")) {
				desc += line + "\n";
				line = scan.nextLine();
			}
			MobInfo mobInfo = new MobInfo(info, desc);
			mobMap.put(name, mobInfo);
		}
		scan.close();
	}

	//perhaps it would be good to load the list of fields for display as well
	public void loadItems() {
		//maps the type of item to its potential fields for display 
		itemMap = new HashMap<String, ItemInfo>();
		
		
		File file = new File("items");
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("cant open items file");
			e.printStackTrace();
		} 
		
		

		scan.nextLine();
		while(scan.hasNext()) {
			String name = scan.next();
			String info = scan.nextLine();
			String desc = "";
			String line = scan.nextLine();
			while(!line.equals("|")) {
				desc += line + "\n";
				line = scan.nextLine();
			}
			ItemInfo itemInfo = new ItemInfo(info, desc);
			itemMap.put(name, itemInfo);
		}
		scan.close();
		
	}
}
