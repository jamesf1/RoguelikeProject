package item;

public class RangedWeapon extends Weapon{
	public RangedWeapon(int min, int max, int minRanged, int maxRanged, AmmunitionType ammType, int capacity) {
		this.minDamage = min;
		this.maxDamage= max;
		this.minRangedDamage = minRanged;
		this.maxRangedDamage = maxRanged;
		this.ammType = ammType;
		this.capacity = capacity;
	}
	AmmunitionType ammType;
	int minRangedDamage;
	int maxRangedDamage;
	int range;
	int rangeAcc;
	int capacity;
	public enum AmmunitionType {PAPERCARTRIDGE, SHOTGUNSHELL, SMALLROUND, LONGROUND}
	@Override
	public boolean isRanged() {
		return true;
	}
	
	public static String getFormattedFieldList() {
		return null;
	}
	@Override
	public String getFormattedFields() { 
		String[] fields = {this.getWeaponClass().toString(), String.valueOf(this.minDamage), String.valueOf(this.maxDamage), String.valueOf(this.acc), String.valueOf(ammType), String.valueOf(capacity), String.valueOf(range), String.valueOf(rangeAcc)}; 
		String formattedFields = String.format("%-8s %-8s %-8s %-8s %-8s %-8s" ,  fields);
		return formattedFields;
	}

}
