package item;
//contains all possible fields for all items
//only relevant fields will be loaded by loadItems()
public class ItemInfo {
public ItemInfo(String info, String desc) {
	Class weapon = Weapon.class;
	
		// TODO Auto-generated constructor stub
	}
int armor;
//warmth might be static for some items, ie. armor, but for others such as cloaks can 
//be adjusted to provide optimal warmth
int warmth;
int weight;
int damage;


}
