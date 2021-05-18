package item;

import com.googlecode.lanterna.TextColor;

public class Armor extends Item{
	int AV;
	int warmth;
	SlotType slot;
	public ItemTag[] tags;
	public Armor(String name, SlotType slot, int AV, int warmth, ItemTag[] tags) {
		super(name, '%', TextColor.ANSI.RED);
		this.AV = AV;
		this.warmth = warmth;
		this.slot = slot;
		this.tags = tags;
		// TODO Auto-generated constructor stub
	}
	public int getAV() { return AV;}
	public SlotType getSlot() {return slot;}
	
	public static Armor getArmor(String armor) {
		switch(armor) {
		case "halfpants": return new Armor("halfpants", SlotType.LEGS, 1, 10, null);
		case "rag boots": return new Armor("rag shoes", SlotType.FEET, 0, 10, null);
		case "sun hat": return new Armor("sun hat", SlotType.HEAD, 0, 5, null);
		case "coat": return new Armor("coat", SlotType.BODY, 1, 10, null);
		default: return null;
		}
	}
}
