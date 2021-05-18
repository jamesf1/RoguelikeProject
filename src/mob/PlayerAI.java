package mob;

public class PlayerAI extends MobAI {

	public PlayerAI(Mob mob) {
		super(mob);
		this.state = State.PLAYER;
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isHostile(Mob mob) {
		if(mob.getAi().isHostile(this.mob))
			return true;
		return false;
	}
}
