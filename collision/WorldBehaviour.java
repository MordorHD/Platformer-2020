package platformer.collision;

public interface WorldBehaviour {
	
	public void forTimeSpan(long time);
	public void affectSurrounding(Collision[] surroundings);
}
