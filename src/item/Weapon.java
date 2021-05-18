package item;

import game.Game;
import item.Weapon.WeaponClass;
import item.Weapon.WeaponSize;
import mob.Mob;

public abstract class Weapon extends Item {
	public enum WeaponClass {KNIFE, SWORD, HAMMER, AXE, UNARMED}
	WeaponClass weaponClass;
	int toHit;
	int minDamage;
	int maxDamage;
	int acc;
	String secondVerb;
	String thirdVerb;

	WeaponSize size;
	SlotType slot;
	public enum WeaponSize {SMALL, LARGE}
	//public enum DamageType?

	
	//penalty to str and dex if dual
	public void attack(Mob mob, Mob target, int toHitBonus, int damageBonus) {
		//adjust for stats and dual and proficiency
		double multiplier = 1;
		int totalDamage = (int) (Game.getRand(minDamage, maxDamage + 1) * multiplier);
		int damageDone = target.takeDamage(totalDamage, mob);
		String message;
		if(mob.isPlayer()) {
			message = "You " + secondVerb + " the " + target.getName() +" for " + totalDamage + " damage.";
		} else {
			String attackedNoun = target.isPlayer() ? "you" : "the " + target.getName();
			message = "The " + mob.getName() + " " + thirdVerb + " " + attackedNoun + " for " + damageDone + " damage";
		}
		mob.getRoom().addMessage(message);
		
		
	}
	public WeaponClass getWeaponClass() {return weaponClass;}
	public abstract boolean isRanged();
	public String getSecondVerb() {
		return secondVerb;
	}
	public void setSecondVerb(String secondVerb) {
		this.secondVerb = secondVerb;
	}
	public String getThirdVerb() {
		return thirdVerb;
	}
	public void setThirdVerb(String thirdVerb) {
		this.thirdVerb = thirdVerb;
	}
	
	public static String getFormattedFieldList() {
		String[] vars = {"class", "minDam", "maxDam", "acc", "size", "ammo", "capacity", "range", "rangeAcc"};
		String list = String.format("%-8s %-8s %-8s %-8s %-8s %-8s" ,  vars);
		return list;
		
	}
	
	public static MeleeWeapon getWeapon(String weapon) {
		switch(weapon ) {
		case "axe": return new MeleeWeapon("axe", 5, 7, -1, WeaponClass.AXE, WeaponSize.LARGE);
		}
		return null;
	}

	public static MeleeWeapon getWeapon(int level) {
		return null;
	}

	
}
