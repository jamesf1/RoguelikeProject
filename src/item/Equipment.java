package item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Equipment {
	//index i of weaponsSlots will map to index i in weapons
	//player should only be able to equip melee weapons either at hips or at back, not at both
	
	Map<SlotType, Weapon> weaponSlots;
	Map<SlotType, Armor> armorSlots;
	public Equipment() { 
		weaponSlots = new HashMap<SlotType, Weapon>();
		armorSlots = new HashMap<SlotType, Armor>();
	}
	
	public int getTotalArmor() {
		int totalArm = 0;
		Collection<Armor> armor = armorSlots.values();
		for(Armor arm : armor) {
			totalArm+= arm.getAV();
		}
		return totalArm; 
	}
	
	public ArrayList<Weapon> getMeleeWeapons() {
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		Collection<Weapon> weaponsEquipped = weaponSlots.values();
		for(Weapon weapon : weaponsEquipped)
			if(!weapon.isRanged())
				weapons.add(weapon);
		return weapons;
	}
	public Item equipWeapon(Weapon weapon, int slot) {
		switch(slot) {
		case 0: return weaponSlots.put(SlotType.LEFTHIP, weapon);
		case 1 : return weaponSlots.put(SlotType.RIGHTHIP, weapon);
		case 2: return weaponSlots.put(SlotType.BACK, weapon);
		}
		return null;
	}
	public Item equipArmor(Armor arm) {
		return armorSlots.put(arm.getSlot(), arm);
	}
	@Override
	public String toString() {
		SlotType[] slots = SlotType.values();
		String string = "";
		for(SlotType slot : slots) {
			Item i = getEquipmentAt(slot);
			string += String.format(slot.toString(), "%10s");
			string += "                ";
			if(i != null)
				string += String.format(i.getName(), "%-20s");
			string += '\n';
		}
		
		return string;
	}
	public Item getEquipmentAt(SlotType slot) {
		Item i = weaponSlots.get(slot);
		if(i == null)
			i = armorSlots.get(slot);
		return i;
	}
}
