package mob;

import com.googlecode.lanterna.TextColor;

import game.Game;
import room.Room;

public class MobAI {
Mob mob;
Mob target;
public enum State {
	PLAYER(TextColor.ANSI.WHITE), IDLE(TextColor.ANSI.WHITE), CIRCLING(TextColor.ANSI.YELLOW), ATTACKING(TextColor.ANSI.RED), FLEEING(TextColor.ANSI.YELLOW);
	private TextColor color;
	private State(TextColor color) {this.color= color;}
	public TextColor getColor() {return color;}
}
public State state;

public MobAI(Mob mob) {this.mob = mob;}
//needs updating - to target, not player
public void moveTowardsTarget(Mob target, int[] excludeX, int[] excludeY) {
	Room room = mob.getRoom();
	int[][] toTargetMap;
	if(excludeX == null && excludeY == null && target.isPlayer())
		toTargetMap = room.getToPlayerMap();
	else
		toTargetMap = room.generateDjikstra(target.getX(), target.getY(), excludeX, excludeY);
	moveTowardsDjikstra(toTargetMap);
}

public void moveAwayFromTarget(Mob target, int[] excludeX, int[] excludeY) {
	Room room = mob.getRoom();
	int[][] toTargetMap;
	if(excludeX == null && excludeY == null && target.isPlayer())
		toTargetMap = room.getToPlayerMap();
	else
		toTargetMap = room.generateDjikstra(target.getX(), target.getY(), excludeX, excludeY);
	moveAwayFromDjikstra(toTargetMap);
}

public void moveAwayFromDjikstra(int[][] djikstraMap) {
	int bestDX = 0; int bestDY = 0;
	int bestD = 0;
	int x = mob.getX();
	int y = mob.getY();
	for(int dx = -1; dx <=1; dx++) {
		for(int dy= -1; dy <=1; dy++) {
			if(mob.canMove(x + dx,y + dy) && djikstraMap[x+ dx][y+dy] > bestD) {
				bestDX = dx; bestDY = dy; bestD = djikstraMap[x+dx][y+dy];
			}
		}
	}
	mob.moveBy(bestDX, bestDY);
}

public void moveTowardsDjikstra(int[][] djikstraMap) {
	int bestDX = 0; int bestDY = 0;
	int bestD = 9999;
	int x = mob.getX();
	int y = mob.getY();
	for(int dx = -1; dx <=1; dx++) {
		for(int dy= -1; dy <=1; dy++) {
			if(mob.canMove(x + dx,y + dy) && djikstraMap[x+ dx][y+dy] < bestD) {
				bestDX = dx; bestDY = dy; bestD = djikstraMap[x+dx][y+dy];
			}
		}
	}
	mob.moveBy(bestDX, bestDY);
}
public void moveTowardsDjikstra(int[][] djikstraMap, int circleDist) {
	int bestDX = 0; int bestDY = 0;
	int bestD = 9999;
	int x = mob.getX();
	int y = mob.getY();
	for(int dx = -1; dx <=1; dx++) {
		for(int dy= -1; dy <=1; dy++) {
			if(mob.canMove(x + dx,y + dy) && (djikstraMap[x+ dx][y+dy] <= bestD && djikstraMap[x+dx][y+dy] >= circleDist)) {
				bestDX = dx; bestDY = dy; bestD = djikstraMap[x+dx][y+dy];
			}
		}
	}
	mob.moveBy(bestDX, bestDY);
}

public void update() { 

}

public  boolean isHostile(Mob mob) {
	if(mob.getClass().equals(Player.class))
		return true;
	return false;
}
public void takeDamage(int damage) {
	// TODO Auto-generated method stub
	
}
public void wanderRandom() {
	int dx = Game.getRand(-1, 1);
	int dy = Game.getRand(-1, 1);
	int newX = mob.x + dx;
	int newY = mob.y + dy;
	if(mob.canMove(newX, newY)) 
		mob.moveBy(dx, dy);
}
}
