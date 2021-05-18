package item;

import java.util.ArrayList;

public class Inventory {
public Inventory() {
	inventory = new ArrayList<Item>();
}
ArrayList<Item> inventory;
public void add(Item item) {
	if(item != null)
		inventory.add(item);
}
public void remove(Item item) {
	inventory.remove(item); 
}
public ArrayList<Item> getItems() {return inventory;}

}
