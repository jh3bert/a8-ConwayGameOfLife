package a8;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		GameView view = new GameView();
		GameModel model = new GameModel();
		GameController controller = new GameController(model, view);
		
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Conway's Game of Life");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		main_frame.setContentPane(view);

		main_frame.pack();
		main_frame.setVisible(true);

	}

}
