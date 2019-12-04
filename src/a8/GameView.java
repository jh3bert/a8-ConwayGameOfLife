package a8;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.*; 
import java.awt.*; 
import javax.swing.*; 

import java.util.ArrayList;
import java.util.List;

public class GameView extends JPanel {

	private JPanel buttonPanel;
	private JSpotBoard board;
	private int dimension;
	private JButton confirm;
	private JButton torusToggle;
	private JButton randomize;
	private JButton clear;
	private JButton advance;
	private JButton start;
	private JTextField size;
	private JTextField delay;
	private JPanel threshold;
	
	private int surviveMin;
	private int surviveMax;
	private int birthMin;
	private int birthMax;
	private int delayNum;
	
	JTextField survive;
	JTextField min;
	JTextField max;
	JTextField minB;
	
	public GameView() {
		setLayout(new BorderLayout());
		
		dimension = 10;
		delayNum = 500;
		
		board = new JSpotBoard(dimension, dimension);
		add(board, BorderLayout.CENTER);
		
	//	torusBoard = new JSpotBoard(dimension+1, dimension+1);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		JPanel inputPanel = new JPanel(new GridLayout(2,2));
		size = new JTextField(5);
		size.setText("10");
		
		inputPanel.add(new JLabel("Enter Board Size: "));
		inputPanel.add(size);
		
		inputPanel.add(new JLabel("10-500"));
		confirm = new JButton("Ok");
		inputPanel.add(confirm);
		
		buttonPanel.add(inputPanel);
		
		clear = new JButton("Clear");
		buttonPanel.add(clear);
		
		advance = new JButton("Advance");
		buttonPanel.add(advance);
		
		randomize = new JButton("Randomize");
		buttonPanel.add(randomize);
		
		torusToggle = new JButton("Torus: Off");
		torusToggle.setBackground(Color.RED);
		buttonPanel.add(torusToggle);
		
		JPanel startPanel = new JPanel();
		startPanel.setLayout(new BorderLayout());
		start = new JButton("Start");	
		delay = new JTextField(5);
		delay.setText("500");
		JLabel prompt = new JLabel("Enter delay(ms):");
		JPanel field = new JPanel();
		field.setLayout(new FlowLayout());
		field.add(prompt);
		field.add(delay);
		field.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		startPanel.add(field, BorderLayout.NORTH);
		startPanel.add(start, BorderLayout.SOUTH);
		
		buttonPanel.add(startPanel);
		
		buttonPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		add(buttonPanel, BorderLayout.SOUTH); 
		
		JPanel big = new JPanel();
		big.setLayout(new BorderLayout());
		
		threshold = new JPanel();
		threshold.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 5));
		
		threshold.add(new JLabel("Survive Thresholds"));
		threshold.add(new JLabel("Birth Thresholds"));
		
		threshold.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		JPanel threshold1 = new JPanel();
		threshold1.setLayout(new FlowLayout());
		threshold1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		survive = new JTextField(5);
		survive.setText("3");
		threshold1.add(new JLabel("Max: "));
		threshold1.add(survive);
		threshold1.add(new JLabel("Min: "));
		min = new JTextField(5);
		min.setText("2");
		threshold1.add(min);
		
		threshold1.add(new JLabel("Max: "));
		max = new JTextField(5);
		max.setText("3");
		threshold1.add(max);
		threshold1.add(new JLabel("Min: "));
		minB = new JTextField(5);
		minB.setText("3");
		threshold1.add(minB);	
		
		big.add(threshold, BorderLayout.NORTH);
		big.add(threshold1, BorderLayout.SOUTH);
		
		add(big, BorderLayout.NORTH);
		
		surviveMin = 2;
		surviveMax = 3;
		birthMin = 3;
		birthMax = 3;
		
	}
	
	public void addActionListener(ActionListener l) {
		clear.addActionListener(l);
		advance.addActionListener(l);
		randomize.addActionListener(l);
		confirm.addActionListener(l);
		torusToggle.addActionListener(l);
		start.addActionListener(l);
	}
	
	public void addSpotListener(SpotListener s) {
		board.addSpotListener(s);
	}
	
	public void clearBoard() {
		for (Spot s: board) {
			s.setBackground(Color.WHITE);
		}
	}
	
	public void randomizeBoard() {
		for (Spot s: board) {
			int rand = (int)(Math.random()*2+1);
			if (rand == 2) {
				s.setBackground(Color.BLACK);
			}
			else 
				s.setBackground(Color.WHITE);
		}
	}
	
	
	public void resize(SpotListener sL) {
		String s = size.getText();
		if (!isInteger(s)) {
			return;
		}
		int x = Integer.parseInt(s);
		if (x != dimension) {
			System.out.print(x);
			dimension = x;
			remove(board);
			board = new JSpotBoard(dimension, dimension);
			add(board, BorderLayout.CENTER);
			
			validate();
			repaint();
			
			board.addSpotListener(sL);
		}
	}
	
	public void advanceGame(ArrayList<Spot> black, ArrayList<Spot> white) {
		for (Spot x: black) {
			x.setBackground(Color.BLACK);
		}
		for (Spot y: white) {
			y.setBackground(Color.WHITE);
		}
	}
	
	public int getDimension() {
		return dimension;
	}
	
	public JSpotBoard getBoard() {
		return board;
	}
	public int getDelay() {
		String s = delay.getText();
		if (isInteger(s)) {
			if (Integer.parseInt(s) >= 10 && Integer.parseInt(s) <= 1000) {
				delayNum = Integer.parseInt(s);
			}
		}
		delay.setText(""+delayNum);
		return delayNum;
	}
	
	public int getSurviveMax() {
		if (isInteger(survive.getText())) {
			surviveMax = Integer.parseInt(survive.getText());
			return surviveMax;
		}
		survive.setText(""+surviveMax);
		return surviveMax;
	}
	
	public int getSurviveMin() {
		if (isInteger(min.getText())) {
			surviveMin = Integer.parseInt(min.getText());
			return surviveMin;
		}
		min.setText(""+surviveMin);
		return surviveMin;
	}
	
	public int getBirthMax() {
		if (isInteger(max.getText())) {
			birthMax = Integer.parseInt(max.getText());
			return birthMax;
		}
		max.setText(""+birthMax);
		return birthMax;
	}
	
	public int getBirthMin() {
		if (isInteger(minB.getText())) {
			birthMin = Integer.parseInt(minB.getText());
			return birthMin;
		}
		minB.setText(""+birthMin);
		return birthMin;
	}
	
	private boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
} 
