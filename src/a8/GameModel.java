package a8;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GameModel {

	private List<GameObserver> observers;
	private ArrayList<Spot> alive;
	private ArrayList<Spot> dead;
	
	public GameModel() {
		observers = new ArrayList<GameObserver>();
		alive = new ArrayList<Spot>();
		dead = new ArrayList<Spot>();
	}
	
	public void addObserver (GameObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(GameObserver observer) {
		observers.remove(observer);
	}
	
	private void notifyObserver() {
		for (GameObserver o: observers) {
			o.update(this, alive, dead);
		}
		
	}
	
	public ArrayList<Spot> whites(JSpotBoard board, int dimension, int surviveMax, int surviveMin, int birthMax, int birthMin, boolean torus) {
		ArrayList<Spot> white = new ArrayList<Spot>();
		for (Spot s: board) {
			if (willSurvive(s, board, dimension, surviveMax, surviveMin, birthMax, birthMin, torus)) {
				continue;
			}
			else 
				white.add(s);
		}
		
		dead = white;
		
		return white;
	}
	
	public ArrayList<Spot> blacks(JSpotBoard board, int dimension, int surviveMax, int surviveMin, int birthMax, int birthMin, boolean torus) {
		ArrayList<Spot> black = new ArrayList<Spot>();
		for (Spot s: board) {
			if (willSurvive(s, board, dimension, surviveMax, surviveMin, birthMax, birthMin, torus)) {
				black.add(s);
			}
		}
		
		alive = black;
		
		return black;
	}
	
	private boolean willSurvive(Spot s, JSpotBoard board, int dimension, int surviveMax, int surviveMin, int birthMax, int birthMin, boolean torus) {
		int alive = 0;
		int dead = 0;
	// up left
		if (s.getSpotX()-1 > -1 &&
				s.getSpotY()-1 > -1 &&
				board.getSpotAt(s.getSpotX()-1, s.getSpotY()-1).getBackground() == Color.BLACK ) {
			alive++;
		}
		else if (s.getSpotX()-1 > -1 &&
				s.getSpotY()-1 > -1 &&
				board.getSpotAt(s.getSpotX()-1, s.getSpotY()-1).getBackground() == Color.WHITE) {
			dead++;
		}
	// left	
		if (s.getSpotX()-1 > -1 &&
				board.getSpotAt(s.getSpotX()-1, s.getSpotY()).getBackground() == Color.BLACK ) {
			alive++;
		}
		else if (s.getSpotX()-1 > -1 &&
				board.getSpotAt(s.getSpotX()-1, s.getSpotY()).getBackground() == Color.WHITE) {
			dead++;
		}
	//	down left
		if (s.getSpotX()-1 > -1 &&
				s.getSpotY()+1 < dimension &&
				board.getSpotAt(s.getSpotX()-1, s.getSpotY()+1).getBackground() == Color.BLACK ) {
			alive++;
		}
		else if (s.getSpotX()-1 > -1 &&
				s.getSpotY()+1 < dimension &&
				board.getSpotAt(s.getSpotX()-1, s.getSpotY()+1).getBackground() == Color.WHITE) {
			dead++;
		}
		
	//  down	
		if (s.getSpotY()+1 < dimension &&
				board.getSpotAt(s.getSpotX(), s.getSpotY()+1).getBackground() == Color.BLACK ) {
			alive++;
		}
		else if (s.getSpotY()+1 < dimension &&
				board.getSpotAt(s.getSpotX(), s.getSpotY()+1).getBackground() == Color.WHITE) {
			dead++;
		}
	
	// down right
		if (s.getSpotY()+1 < dimension &&
				s.getSpotX()+1 < dimension &&
				board.getSpotAt(s.getSpotX()+1, s.getSpotY()+1).getBackground() == Color.BLACK ) {
			alive++;
		}
		else if (s.getSpotY()+1 < dimension &&
				s.getSpotX()+1 < dimension &&
				board.getSpotAt(s.getSpotX()+1, s.getSpotY()+1).getBackground() == Color.WHITE) {
			dead++;
		}
		
		// up 
		if (s.getSpotY()-1 > -1 &&
				board.getSpotAt(s.getSpotX(), s.getSpotY()-1).getBackground() == Color.BLACK) {
			alive++;
		}
		else if (s.getSpotY()-1 > -1 &&
				board.getSpotAt(s.getSpotX(), s.getSpotY()-1).getBackground() == Color.WHITE) {
			dead++;
		}
		
		// right 
		if (s.getSpotX()+1 < dimension &&
				board.getSpotAt(s.getSpotX()+1, s.getSpotY()).getBackground() == Color.BLACK) {
			alive++;
		}
		else if (s.getSpotX()+1 < dimension &&
				board.getSpotAt(s.getSpotX()+1, s.getSpotY()).getBackground() == Color.WHITE) {
			dead++;
		}
		
		// up right 
		if (s.getSpotY()-1 > -1 &&
				s.getSpotX()+1 < dimension &&
				board.getSpotAt(s.getSpotX()+1, s.getSpotY()-1).getBackground() == Color.BLACK) {
			alive++;
		}
		else if (s.getSpotY()-1 > -1 &&
				s.getSpotX()+1 < dimension &&
				board.getSpotAt(s.getSpotX()+1, s.getSpotY()-1).getBackground() == Color.WHITE) {
			dead++;
		}
		
		if (torus) {
			// bottom wrapping top
			if (s.getSpotY() == (dimension-1)) {
				if (board.getSpotAt(s.getSpotX(), 0).getBackground() == Color.BLACK ) {
					alive++;
				}
				else
					dead++;
				if (s.getSpotX()+1 < dimension) {
					if (board.getSpotAt(s.getSpotX()+1, 0).getBackground() == Color.BLACK) {
						alive++;
					}
					else
						dead++;
				}
				if (s.getSpotX()-1 > -1) {
					if (board.getSpotAt(s.getSpotX()-1, 0).getBackground() == Color.BLACK) {
						alive++;
					}
					else
						dead++;
				}
			}
			// top wrapping bottom
			if (s.getSpotY() == 0) {
				if (board.getSpotAt(s.getSpotX(), dimension -1).getBackground() == Color.BLACK) {
					alive++;
				}
				else
					dead++;
				if (s.getSpotX()+1 < dimension) {
					if (board.getSpotAt(s.getSpotX()+1, dimension-1).getBackground() == Color.BLACK) {
						alive++;
					}
					else
						dead++;
				}
				if (s.getSpotX()-1 > -1) {
					if (board.getSpotAt(s.getSpotX()-1, dimension-1).getBackground() == Color.BLACK) {
						alive++;
					}
					else
						dead++;
				}
			}
			//left wrapping right
			if (s.getSpotX() == 0) {
				if (board.getSpotAt(dimension-1, s.getSpotY()).getBackground() == Color.BLACK) {
					alive++;
				}
				else
					dead++;
				if (s.getSpotY()+1 < dimension) {
					if (board.getSpotAt(dimension-1, s.getSpotY()+1).getBackground() == Color.BLACK) {
						alive++;
					}
					else
						dead++;
				}
				if (s.getSpotY()-1 > -1) {
					if (board.getSpotAt(dimension-1, s.getSpotY()-1).getBackground() == Color.BLACK) {
						alive++;
					}
					else
						dead++;
				}
			}
			// right wrapping left
			if (s.getSpotX() == (dimension-1)) {
				if (board.getSpotAt(0, s.getSpotY()).getBackground() == Color.BLACK) {
					alive++;
				}
				else
					dead++;
				if (s.getSpotY()+1 < dimension) {
					if (board.getSpotAt(0, s.getSpotY()+1).getBackground() == Color.BLACK) {
						alive++;
					}
					else
						dead++;
				}
				if (s.getSpotY()-1 > -1) {
					if (board.getSpotAt(0, s.getSpotY()-1).getBackground() == Color.BLACK) {
						alive++;
					}
					else
						dead++;
				}
			}
			if (s.getSpotX() == 0 && s.getSpotY() == 0) {
				if (board.getSpotAt(dimension-1, dimension-1).getSpotColor() == Color.BLACK) {
					alive++;
				}
				else
					dead++;
			}
			if (s.getSpotX() == 0 && s.getSpotY() == dimension-1) {
				if (board.getSpotAt(dimension-1, 0).getBackground() == Color.BLACK) {
					alive++;
				}
				else
					dead++;
			}
			if (s.getSpotX()== dimension -1 && s.getSpotY() == 0) {
				if (board.getSpotAt(0, dimension-1).getBackground() == Color.BLACK) {
					alive++;
				}
				else
					dead++;
				
			}
			if (s.getSpotX() == dimension-1 && s.getSpotY() == dimension-1) {
				if (board.getSpotAt(0, 0).getBackground() == Color.BLACK) {
					alive++;
				}
				else
					dead++;
			}
		}
		
		if (s.getBackground() == Color.BLACK && (alive >= surviveMin && alive <= surviveMax)) {
			return true;
		}
		else if (s.getBackground() == Color.WHITE && alive >= birthMin && alive <= birthMax) {
			return true;
		}
		else 
			return false;
	}
}
