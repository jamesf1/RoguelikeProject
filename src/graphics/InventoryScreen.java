package graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import game.Component;
import item.Armor;
import item.Equipment;
import item.Inventory;
import item.Item;
import item.SlotType;
import item.Weapon;
import mob.Player;

public class InventoryScreen extends GameScreen {
	GameScreen parent;
	Player player;
	Map<Character, Item> letterMap;
	public InventoryScreen(GameScreen parent, Player p) {
		this.parent = parent;
		this.player = p;
		letterMap = new HashMap<Character, Item>();
	}
	@Override
	public void draw(TextGraphics t) throws IOException {
		drawSidebarDividers(t);
		drawPlayerInfo(t, player);
		TerminalPosition upLeft = new TerminalPosition(0,0);
		TerminalPosition upRight = new TerminalPosition(Component.terminalWidth, 0);
		
		t.drawLine(upLeft, upRight, '-');
		String titleString = "Inventory";
		int titleX = Component.terminalWidth/2 - titleString.length()/2;
		t.putString(titleX,  0, titleString);
		Inventory inventory = player.getInventory();
		
		char currLetter = assignAndDrawEquipment(player.getEquipment(), t);
		assignAndDrawInventory(currLetter, inventory, t);
	
	}
	//return the next unassigned character
	public char assignAndDrawEquipment(Equipment equipment, TextGraphics t) {
		SlotType[] slots = SlotType.values();
		String string = "";
		char letter = 'a';
		int x = Component.sidebarX;
		int y = Component.lowerSidebarY;
		for(SlotType slot : slots) {
			string = "";
			Item i = equipment.getEquipmentAt(slot);
			string = String.format(slot.toString(), "%10s");
			string += "                ";
			if(i != null) {
				letterMap.put(letter, i);
				string = String.format("%-10s %-10s %-5c", slot.toString(), i.getName(), letter);
				letter++;
			}

			t.putString(x, y, string);
			y++;
			
		}
		return letter;
	}
	
	public void assignAndDrawInventory(char currLetter, Inventory inventory, TextGraphics t) {
		ArrayList<Item> items = inventory.getItems();
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		ArrayList<Armor> armor = new ArrayList<Armor>();
		ArrayList<Item> misc = new ArrayList<Item>();
		for(Item item : items){
			if(item.getClass().equals(Weapon.class))
				weapons.add((Weapon) item);
			else if(item.getClass().equals(Armor.class))
				armor.add((Armor) item);
			else
				misc.add(item);
		}
		int y = 2;
		int x = 0;
		String fields = Weapon.getFormattedFieldList();
		String weaponsTitle = String.format("%-10s %s", "Weapons", fields);
		t.enableModifiers(SGR.FRAKTUR);
		t.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
		t.putCSIStyledString(x, y, weaponsTitle);
		t.putString(25, y, Weapon.getFormattedFieldList());
		y++;
		
		y++;
		for(Item item : weapons) {
			String weaponString = String.format("%-10s %-5s", item.getName(), currLetter);
			t.putString(x, y, weaponString);
			letterMap.put(currLetter, item);
			currLetter++;
			y++;
		}
		
		String armorTitle = "Armor";
		t.enableModifiers(SGR.FRAKTUR);
		t.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
		t.putCSIStyledString(x, y, armorTitle);
		
		for(Item item : armor) {
			String armorString = String.format("%-10s %-5s", item.getName(), currLetter);
			t.putString(x, y, armorString);
			letterMap.put(currLetter, item);
			currLetter++;
			y++;
		}
		y++;
		y++;
		
		String miscTitle = "MISC";
		t.enableModifiers(SGR.FRAKTUR);
		t.setForegroundColor(TextColor.ANSI.CYAN_BRIGHT);
		t.putCSIStyledString(x, y, miscTitle);
		
		for(Item item : misc) {
			String itemString = String.format("%-10s %-5s", item.getName(), currLetter);
			t.putString(x, y, itemString);
			letterMap.put(currLetter, item);
			currLetter++;
			y++;
		}
		
	}

	@Override
	public GameScreen respond(KeyStroke key) {
		KeyType type = key.getKeyType();
		switch(type) {
		case Escape: return parent; 
		
		}
		Character c = key.getCharacter();
		if(c != null) {
			Item i = letterMap.get(c);
			if(i != null) 
				return new ItemScreen(i, this, player);
			
		}
		
		return this;
	}
	
	

}
