package a8;

import java.util.ArrayList;

public interface GameObserver {
	
	public void update(GameModel model, ArrayList<Spot> alive, ArrayList<Spot> dead);
	
}
