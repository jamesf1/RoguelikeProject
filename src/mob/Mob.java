package mob;

import java.util.ArrayList;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;

import item.Armor;
import item.Equipment;
import item.Inventory;
import item.Item;
import item.MeleeWeapon;
import item.RangedWeapon;
import item.Weapon;
import item.Weapon.WeaponClass;
import item.Weapon.WeaponSize;
import mob.MobAI.State;
import room.Room;

public class Mob {
	ArrayList<Weapon.WeaponClass> proficiencies;
	String name;
	String desc;
	int hp;
	int maxhp;
	int x; 
	int y;
	int ap;
	int maxAP;
	int strength;
	int dex;
	RangedWeapon currRanged;
	TextCharacter glyph;
	Inventory inventory;
	boolean alive = true;
	MobAI ai;
	Equipment equipment; 
	Room room;
	
	public Mob() {
		equipment = new Equipment();
		inventory = new Inventory();
		proficiencies = new ArrayList<Weapon.WeaponClass>();
		currRanged= null;
	}

	
	public Mob(String name, MobInfo mobInfo, Room room) {
		this();
		this.name = name;
		this.desc = mobInfo.desc;
		this.glyph = mobInfo.glyph;
		this.hp = mobInfo.maxhp;
		this.maxhp = mobInfo.maxhp;
		this.maxAP = mobInfo.ap;
		this.ap = mobInfo.ap;
		String aiString = mobInfo.aiString;
		this.room = room;
		if(aiString.equals("WolfAI"))
			this.ai = new WolfAI(this);
	}
	/* deprecated constructor
	public Mob(String name, TextCharacter glyph, int hp, int maxhp, int x, int y, int speed, Inventory i, boolean alive,
			MobAI ai, Room room) {
		super();
		this.name = name;
		this.hp = hp;
		this.maxhp = maxhp;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.glyph = glyph;
		this.i = i;
		this.alive = alive;
		this.ai = ai;
		this.room = room;
	}
	*/
	
	public boolean moveBy(int dx, int dy) {
		int newX = x + dx;
		int newY = y + dy;
		if(room.isMobHere(newX, newY)) {
			Mob adjacentMob = room.getMobAt(newX, newY);
			if(getAi().isHostile(adjacentMob)) {
				attack(adjacentMob);
				return true;
			}
		}
		if(!room.isPassable(newX, newY))
			return false;
		this.x += dx;
		this.y += dy;
		return true;
	}
	
	public void attack(Mob mob) {
		ArrayList<Weapon> weapons = equipment.getMeleeWeapons();
		int numWep = weapons.size();
		if(numWep == 0) {
			Weapon unarmed = getUnarmed();
			unarmed.attack(this, mob, getToHit(unarmed, false),getDamageBonus(unarmed, false));
		} else if(numWep ==1 ){
			Weapon wep = weapons.get(0);
			wep.attack(this, mob, getToHit(wep, false), getDamageBonus(wep, false));
		} else if(numWep==2) {
			for(Weapon weapon: weapons)
				weapon.attack(this, mob, getToHit(weapon, true), getDamageBonus(weapon, true));
		}
	}
	public int getToHit(Weapon weapon, boolean dual) {
		int bonus = 0;
		if(weapon.getWeaponClass() == null)
		if(proficiencies.contains(weapon.getWeaponClass()))
			bonus++;
		if(dual) 
			bonus-=2;
		bonus += dex %3;
		return bonus;
	}
	public int getDamageBonus(Weapon weapon, boolean dual) {
		int bonus = 0;
		if(proficiencies.contains(weapon.getWeaponClass()))
			bonus++;
		if(dual) 
			bonus-= 2;
		bonus += strength %3;
		return bonus;
	}


	public void update() {
		ai.update();
	}
	
	public void giveItem(Item i) {
		
	}

	
	public void draw(TextGraphics t) {
		if(isAlive())
			t.setCharacter(x, y, glyph);
	}
	
	public int getAP() {return ap;}
	
	public int getX() {return x;}
	public int getY() {return y;}
	
	//return -1 if dead
	public int takeDamage(int damage, Mob attacker) {
		int totalDamage = damage - getArmor();
		ai.takeDamage(damage);
		if(totalDamage >  0)
			hp -= totalDamage;
		if(hp <= 0) {
			setDead();
			return -1;
		}
		return totalDamage;
	}
	
	public boolean isAlive() {return alive;}
	
	public TextCharacter getGlyph() { return glyph;}
	public void setGlyph(TextCharacter glyph) {this.glyph = glyph;}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMaxhp() {
		return maxhp;
	}

	public void setMaxhp(int maxhp) {
		this.maxhp = maxhp;
	}

	public MobAI getAi() {
		return ai;
	}

	public void setAi(MobAI ai) {
		this.ai = ai;
	}

	public void setSpeed(int speed) {
		this.ap = speed;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public boolean canMove(int x, int y) {
		Mob mobAt = room.getMobAt(x, y);
		boolean canMoveMob = (mobAt == null || ai.isHostile(mobAt));
		return (room.isValid(x, y) && room.isPassable(x, y) && canMoveMob);
	}
	
	public boolean isAdjactentTo(Mob mob) {
		int xDist = Math.abs(x - mob.getX());
		int yDist = Math.abs(y - mob.getY());
		if(xDist <= 1 && yDist <= 1)
			return true;
		else
			return false;
	}
	
	public void setDead() {
		this.alive = false;
		//room.removeMob(this);
	}
	


	public int getAp() {
		return ap;
	}

	public void incrementAp(int ap) {
		this.ap += ap;
	}

	public int getMaxAP() {
		return maxAP;
	}

	public void setMaxAP(int maxAP) {
		this.maxAP = maxAP;
	}
	
	public void produceBody() {

	}
	
	public Weapon getUnarmed() {
		return new MeleeWeapon("fists", 3, 6, 1, WeaponClass.UNARMED, WeaponSize.SMALL);
	}
	
	public int getArmor() {
		if(equipment == null)
			System.out.println("hey");
		return equipment.getTotalArmor();
	}
	public State getState() {
		return ai.state;
	}
	public String getDesc() {
		return desc;
	}
	
	public boolean isPlayer() {
		return false;
	}
	
	public void equipWeapon(Weapon weapon, int slot) {
		Item former = equipment.equipWeapon(weapon, slot);
		if(weapon.isRanged())
			currRanged = (RangedWeapon) weapon;
		inventory.add(former);
	}
	public Item equipArmor(Armor arm) {
		return equipment.equipArmor(arm);
	}
	
	public Inventory getInventory() {return inventory;}
	public Equipment getEquipment() {return equipment;}

}

