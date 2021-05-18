package mob;

import com.googlecode.lanterna.TextCharacter;

import item.Armor;
import item.MeleeWeapon;
import item.SlotType;
import item.Weapon;
import overmap.Overmap;
import room.Room;

public class Player extends Mob {
	private int turnsSinceEncounter;
	private int overmapX;
	private int overmapY;
	private  Overmap overmap;
	public Player(Overmap overmap, String name) {
		super();
		this.name = name;
		this.hp = 10;
		this.maxhp=10;
		this.desc = "";
		
		setAi(new PlayerAI(this));
		setGlyph(new TextCharacter('@'));
		setOvermap(overmap);
		turnsSinceEncounter = 0;
		overmapX = 0;
		overmapY = 0;
		
		Weapon axe = Weapon.getWeapon("axe");
		equipWeapon(axe, 2);
		Armor coat = Armor.getArmor("coat");
		equipArmor(coat);
		inventory.add(Armor.getArmor("coat"));
		inventory.add(Armor.getArmor("halfpants"));
		inventory.add(Armor.getArmor("sun hat"));
		inventory.add(Armor.getArmor("rag boots"));
		
		inventory.add(Weapon.getWeapon("axe"));
		inventory.add(Weapon.getWeapon("axe"));
	}
	
	public int getOvermapX() {return overmapX;}
	public int getOvermapY() {return overmapY;}
	
	public boolean moveOvermap(int dx, int dy) {
		if(overmap.isValid(overmapX+ dx, overmapY + dy)) {
			overmapX+=dx;
			overmapY+= dy;
			turnsSinceEncounter++;
			return true;
		}
		else return false;
		
	}
	
	public void setOvermap(Overmap overmap) {
		this.overmap = overmap;
		overmap.setPlayer(this);
	}
	
	//remember to reset this somewhere when encounter is initiated
	public int getTurnsSinceEncounter() {return turnsSinceEncounter;}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	

	
	public void resetTurnsSinceEncounter() {
		turnsSinceEncounter = 0;
	}
	
	@Override 
	public boolean isPlayer() {
		return true;
	}
	
	
}
