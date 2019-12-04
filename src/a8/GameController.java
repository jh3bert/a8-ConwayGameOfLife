package a8;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;

public class GameController implements ActionListener, SpotListener, GameObserver{

	private GameModel model;
	private GameView view;
	private boolean torus;
	private SeparateThread t;
	
	public GameController(GameModel model, GameView view) {
		this.model = model;
		this.view = view;
		torus = false;
		
		view.addActionListener(this);
		view.addSpotListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		
		String bText = b.getText();
		
		if (bText.equalsIgnoreCase("Clear")) {
			view.clearBoard();
		}
		else if (bText.equalsIgnoreCase("Advance")) {
			view.advanceGame(model.blacks(view.getBoard(), view.getDimension(), view.getSurviveMax(), view.getSurviveMin(), 
					view.getBirthMax(), view.getBirthMin(), torus), 
					model.whites(view.getBoard(), view.getDimension(), view.getSurviveMax(), view.getSurviveMin(), 
					view.getBirthMax(), view.getBirthMin(), torus)); 
		}
		else if (bText.equalsIgnoreCase("Randomize")) {
			view.randomizeBoard();
		}
		else if (bText.equalsIgnoreCase("Ok")) {
			view.resize(this);
		}
		else if (bText.equalsIgnoreCase("Torus: Off")) {
			b.setBackground(Color.GREEN);
			b.setText("Torus: On");
			torus = true;
		}
		else if (bText.equalsIgnoreCase("Torus: On")) {
			b.setBackground(Color.RED);
			b.setText("Torus: Off");
			torus = false;
		}
		else if (bText.equalsIgnoreCase("Start")) {
			b.setText("Stop");
			t = new SeparateThread(view.getBoard(), view.getDimension(), view.getSurviveMax(), view.getSurviveMin(), 
					view.getBirthMax(), view.getBirthMin(), torus, view, view.getDelay());
			Thread x = new Thread(t);
			x.start();
		}
		else if (bText.equalsIgnoreCase("Stop")) {
			t.stop();
			b.setText("Start");
		}
	}
	
	public void spotClicked(Spot spot) {
		if (spot.getBackground() == Color.WHITE) {
			spot.setBackground(Color.BLACK);
		}
		else if (spot.getBackground() == Color.BLACK) {
			spot.setBackground(Color.WHITE);
		}
		
	}

	@Override
	public void spotEntered(Spot spot) {
		// TODO Auto-generated method stub
		spot.setHighlight(Color.BLUE);
		spot.highlightSpot();
	}

	@Override
	public void spotExited(Spot spot) {
		// TODO Auto-generated method stub
		spot.setHighlight(Color.GRAY);
	
	}

	@Override
	public void update(GameModel model, ArrayList<Spot> alive, ArrayList<Spot> dead) {
		// TODO Auto-generated method stub
		view.advanceGame(alive, dead);
	}
	
}
