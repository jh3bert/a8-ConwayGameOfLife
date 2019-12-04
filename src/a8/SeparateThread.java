package a8;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SeparateThread implements Runnable {

	private JSpotBoard board; 
	private int dimension;
	private int surviveMax;
	private int surviveMin; 
	private int birthMax;
	private int birthMin; 
	private boolean torus;
	private boolean stop;
	private GameView view;
	private int delay;
	
	public SeparateThread(JSpotBoard board, int dimension, int surviveMax, int surviveMin, int birthMax, int birthMin,
			boolean torus, GameView view, int delay) {
		this.board = board;
		this.dimension = dimension;
		this.surviveMax = surviveMax;
		this.surviveMin = surviveMin;
		this.birthMax = birthMax;
		this.birthMin = birthMin;
		this.torus = torus;
		this.view = view;
		this.delay = delay;
		
		stop = false;
	}
	
	@Override
	public void run() {
		while (!stop) {
			ArrayList<Spot> white = new ArrayList<Spot>();
			for (Spot s: board) {
				if (willSurvive(s, board, dimension, surviveMax, surviveMin, birthMax, birthMin, torus)) {
					continue;
				}
				else 
					white.add(s);
			}
			ArrayList<Spot> black = new ArrayList<Spot>();
			for (Spot s: board) {
				if (willSurvive(s, board, dimension, surviveMax, surviveMin, birthMax, birthMin, torus)) {
					black.add(s);
				}
			}
			view.advanceGame(black, white);
			
			try {
				TimeUnit.MILLISECONDS.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void stop() {
		stop = true;
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
