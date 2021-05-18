package item;

public class MeleeWeapon extends Weapon {
WeaponClass weaponClass;
public MeleeWeapon(String name, int minDamage, int maxDamage, int toHit, WeaponClass weaponClass, WeaponSize size) {
	this.name = name;
	this.minDamage = minDamage;
	this.maxDamage = maxDamage;
	this.toHit = toHit;
	this.weaponClass = weaponClass;
	this.size = size;
	
	switch(weaponClass) {
	case AXE: setSecondVerb("slash"); setThirdVerb("slashes"); break;
	default: setSecondVerb("hit"); setThirdVerb("hit"); break;
	}
}



@Override
public boolean isRanged() {
	return false;
}




}
