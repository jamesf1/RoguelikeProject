package mob;

import java.util.List;

import game.Game;
import room.Room;

public class WolfAI extends MobAI {
	//track aggression to given mobs, instead of as a total? Or specify target somewhere
	//aggression increases with proximity to player
	//decreases with damage taken, and with friends killed
	private int aggression;
	private int maxAggression;
	final int attackThreshold = 800;
	final int circleThreshold = 100;
	final int fleeThreshold = -100;
	final double partnerMultiplier = .1;
	final int deadPartner = -20;
	final int adjacentPenalty = -20;
	private WolfAI prev;
	private WolfAI next;
	//-1 for flee, 0 for wander, 1 for circle, 2 for attack
	Mob target;
	
	
	public WolfAI(Mob mob) {
		super(mob);
		state = State.IDLE;
		aggression = 0;
	}
	private void incrementAggression(int inc) {
		aggression += inc;
		if(inc > maxAggression)
			inc = maxAggression;
		if(inc < -maxAggression)
			inc = - maxAggression;
	}
	
	@Override
	public void update() {
		int closestDist = 9999;
		if(target == null) {
			Mob closestTarget = null;
			Room room = mob.getRoom();
			List<Mob> mobs = room.getMobs();
			for(Mob otherMob : mobs) 
				if(isHostile(otherMob)) {
					int dist = distTo(otherMob.getX(), otherMob.getY());
					if(dist < closestDist) {
						closestDist = dist;
						closestTarget = otherMob;
					}
				}
			target = closestTarget;
		}
		
		int aggressionRand = (int) Game.getRand(-15, 20);
		double distMultiplier = 15/ distTo(target.getX(), target.getY());
		incrementAggression((int) (aggressionRand * distMultiplier));
		
		
		if(prev.getMob().isAlive())
			incrementAggression((int) (prev.getAggression()*partnerMultiplier));
		else 
			incrementAggression(deadPartner);
		
		if(next.getMob().isAlive())
			incrementAggression((int) (next.getAggression() * partnerMultiplier));
		else 
			incrementAggression(deadPartner);
		
		
		 
		if(state == State.IDLE) 
			idle();
		else if(state == State.ATTACKING)
			attack();
		else if(state == State.FLEEING)
			flee();
		else if(state == State.CIRCLING)
			circle();
		
	}
	@Override
	public void takeDamage(int damage) {
		incrementAggression(-damage*4);
	}
	
	
	public WolfAI getPrev() {
		return prev;
	}
	public void setPrev(WolfAI prev) {
		this.prev = prev;
	}
	public WolfAI getNext() {
		return next;
	}
	public void setNext(WolfAI next) {
		this.next = next;
	}
	public void setAggression(int aggression) {
		this.aggression = aggression;
	}
	public int getAggression() {
		return aggression;
	}
	public Mob getMob() {
		return mob;
	}
	
	
	public void idle() {
		wanderRandom();
		if(aggression < fleeThreshold)
			state = State.FLEEING;
		else if(aggression > circleThreshold)
			state = State.CIRCLING;
	}
	public void circle() {
		int circleDist = (int) Game.getRand(5,  8);
		circleTarget(target.getX(), target.getY(), circleDist);
		if(aggression > attackThreshold)
			state = State.ATTACKING;
		else if(aggression < fleeThreshold)
			state = State.FLEEING;
	}
	public void attack() {
		if(target != null) {
			moveTowardsTarget(mob.getRoom().getPlayer(), null, null);
			if(mob.isAdjactentTo(target))
				incrementAggression(adjacentPenalty);
		}
		if(aggression < attackThreshold)
			state = State.CIRCLING;
			
	}
	public void flee() {
		moveAwayFromTarget(target, null, null);
	}
	
	
	private int distTo(int dx, int dy) {
		int dist = (int) Math.pow( (Math.pow((dx - mob.x), 2) + Math.pow((dy - mob.y), 2)), .5);
		return dist;
	}
	
	private void circleTarget(int targetX, int targetY, int circleDist) {
		int[][] targetMap;
		if(mob.room.getPlayer().x == targetX && mob.room.getPlayer().y== targetY)
			targetMap = mob.room.getToPlayerMap();
		else 
			targetMap = mob.room.generateDjikstra(targetX, targetY, null, null);
		moveTowardsDjikstra(targetMap, 5);
		/*
		//exclude the squares around target
		int[] excludeX = {-1, -1, -1, 0, 0, 1, 1, 1 };
		int[] excludeY = {-1, 0, 1, -1, 1, -1, 0, 1};
		
		boolean validDest = false;
		int dx = 0;
		int dy = 0;
		while(!validDest) {
			dx = targetX + (int)  Game.getRand(-5, 5);
			dy = targetY + (int) Game.getRand(-5, 5);
			boolean isAdjacent = Math.abs(targetX-dx) <= 1 && Math.abs(targetY - dy) <= 1;
			if(!isAdjacent && distTo(targetX, targetY) < 100)
				validDest = true;
			
		}
		int[][] djikstraMap = mob.room.generateDjikstra(dx, dy, excludeX, excludeY);
		moveTowardsDjikstra(djikstraMap);
		*/
	}
	
}
