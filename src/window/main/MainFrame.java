package window.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import main.Const;
import window.champions.Records;
import window.champions.RecordsFrame;
import window.settings.SettingsFrame;
import events.BombsCountListener;
import events.GameListener;

/**
 * Main window of program. Contains field [...]
 * 
 * @author Влад
 */
public class MainFrame extends JFrame implements GameListener, BombsCountListener {

	private final JLabel lblTime = new JLabel();
	private final JLabel lblBombs = new JLabel();
	private JButton btnNewGame;
	
	private Field field;
	
	private Timer timer;
	private int secondsOfGame;
	
	private int fieldWidth = Const.EasyFieldWidth;
	private int fieldHeight = Const.EasyFieldHeight;
	private int bombs = Const.EasyBombsCount;
	
	private JMenuBar menuBar;
	
	private GameDifficulty difficulty;
	
	private Records records;
	
	public MainFrame() {
		initFrameContent();
		initMenuBar();
		
		timer = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				lblTime.setText(numberConvert(secondsOfGame++));
			}
		});
		
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});
		
		records = new Records();
		difficulty = GameDifficulty.Easy;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);
		setResizable(false);
		pack();
		setVisible(true);
	}
	
	private void restart() {
		timer.stop();
		lblTime.setText(numberConvert(0));
		lblBombs.setText(numberConvert(bombs));
		field.restart(fieldWidth, fieldHeight, bombs);
		pack();
		repaint();
	}
	
	private String numberConvert(int number) {
		StringBuilder num = new StringBuilder(String.valueOf(number));
		if(num.length() < 3) {
			num.insert(0, fillChar('0', 3 - num.length()));
		}
		
		return num.toString();
	}
	
	private String fillChar(char ch, int amount) {
		StringBuilder result = new StringBuilder(amount);
		for(int i = 0; i < amount; ++i) {
			result.append(ch);
		}
		
		return result.toString();
	}
	
	private void initFrameContent() {
		field = new Field(fieldWidth, fieldHeight, bombs);
		field.setGameListener(this);
		field.setBombsCountListener(this);
		field.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(field, BorderLayout.CENTER);

		JPanel pnlHeader = new JPanel();
		pnlHeader.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(pnlHeader, BorderLayout.NORTH);
		pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.X_AXIS));

		Component horizontalStrut = Box.createHorizontalStrut(10);
		pnlHeader.add(horizontalStrut);

		lblTime.setText(numberConvert(0));
		lblTime.setOpaque(true);
		lblTime.setBackground(new Color(0, 0, 139));
		lblTime.setForeground(new Color(165, 42, 42));
		lblTime.setFont(new Font("Asrock7Segment", Font.BOLD, 16));
		pnlHeader.add(lblTime);

		Component horizontalGlue = Box.createHorizontalGlue();
		pnlHeader.add(horizontalGlue);

		btnNewGame = new JButton("");
		btnNewGame.setPreferredSize(new Dimension(32, 32));
		btnNewGame.setOpaque(false);
		btnNewGame.setBackground(SystemColor.controlHighlight);
		btnNewGame.setForeground(SystemColor.controlHighlight);
		btnNewGame.setMaximumSize(new Dimension(32, 32));
		btnNewGame.setIcon(new ImageIcon(Const.PositiveSmileIcon));
		pnlHeader.add(btnNewGame);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		pnlHeader.add(horizontalGlue_1);

		lblBombs.setText(numberConvert(Const.EasyBombsCount));
		lblBombs.setOpaque(true);
		lblBombs.setBackground(new Color(0, 0, 139));
		lblBombs.setForeground(new Color(165, 42, 42));
		lblBombs.setFont(new Font("Asrock7Segment", Font.BOLD, 16));
		pnlHeader.add(lblBombs);

		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		pnlHeader.add(horizontalStrut_1);
	}

	private void initMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

			JMenuItem mntmNewGame = new JMenuItem("New game");
			mntmNewGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					restart();
				}
			});
			mnGame.add(mntmNewGame);
			
			mnGame.addSeparator();
	
			JMenuItem mntmEasy = new JMenuItem("Easy");
			mntmEasy.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				changeSettings(Const.EasyFieldWidth, Const.EasyFieldHeight,
						Const.EasyBombsCount);
				difficulty = GameDifficulty.Easy;
				}
			});
			mnGame.add(mntmEasy);
	
			JMenuItem mntmMedium = new JMenuItem("Medium");
			mntmMedium.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				changeSettings(Const.MediumFieldWidth, Const.MediumFieldHeight,
						Const.MediumBombsCount);
				difficulty = GameDifficulty.Medium;
				}
			});
			mnGame.add(mntmMedium);
	
			JMenuItem mntmHard = new JMenuItem("Hard");
			mntmHard.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				changeSettings(Const.HardFieldWidth, Const.HardFieldHeight,
						Const.HardBombsCount);
				difficulty = GameDifficulty.Hard;
				}
			});
			mnGame.add(mntmHard);
	
			JMenuItem mntmSpecial = new JMenuItem("Special");
			mntmSpecial.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev) {
					invokeSettingsFrame();
					difficulty = GameDifficulty.Special;
				}
			});
			mnGame.add(mntmSpecial);
			
			mnGame.addSeparator();
			
			JMenuItem mntmRecords = new JMenuItem("Records");
			mntmRecords.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					invokeRecordsFrame(difficulty);
				}
			});
			mnGame.add(mntmRecords);
	
			mnGame.addSeparator();
			
			JMenuItem mntmExit = new JMenuItem("Exit");
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			mnGame.add(mntmExit);
	}
	
	private void invokeRecordsFrame(GameDifficulty defaultList) {
		RecordsFrame recordsFrame = new RecordsFrame(records, defaultList);
		recordsFrame.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
	}
	
	private void invokeSettingsFrame() {
		SettingsFrame settings = new SettingsFrame(this, fieldWidth, fieldHeight, bombs);
		settings.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
	}
	
	public void changeSettings(int width, int height, int bombs) {
		fieldWidth = width;
		fieldHeight = height;
		this.bombs = bombs;
		
		if (width == Const.EasyFieldWidth 
				&& height == Const.EasyFieldHeight
				&& bombs == Const.EasyBombsCount) {
			difficulty = GameDifficulty.Easy;
		} else if (width == Const.MediumFieldWidth
				&& height == Const.MediumFieldHeight
				&& bombs == Const.MediumBombsCount) {
			difficulty = GameDifficulty.Medium;
		} else if (width == Const.HardFieldWidth
				&& height == Const.HardFieldHeight
				&& bombs == Const.HardBombsCount) {
			difficulty = GameDifficulty.Hard;
		}
		
		restart();
	}

	@Override
	public void endOfGame(boolean win) {
		timer.stop();
		if(win) {
			JOptionPane.showMessageDialog(this, "Congratulations! You'he won in "
					+ secondsOfGame + " seconds!", "Win!",
					JOptionPane.INFORMATION_MESSAGE);
			
			String name = null;
			switch(difficulty) {
			case Easy:
				if(records.isWritableIntoEasy(secondsOfGame)) {
					name = JOptionPane.showInputDialog(this, "Type your name to carry it in the high score");
					records.addEasy(name, secondsOfGame);
					invokeRecordsFrame(GameDifficulty.Easy);
				}
				break;
			case Medium:
				if(records.isWritableIntoMedium(secondsOfGame)) {
					name = JOptionPane.showInputDialog(this, "Type your name to carry it in the high score");
					records.addMedium(name, secondsOfGame);
					invokeRecordsFrame(GameDifficulty.Medium);
				}
				break;
			case Hard:
				if(records.isWritableIntoHard(secondsOfGame)) {
					name = JOptionPane.showInputDialog(this, "Type your name to carry it in the high score");
					records.addHard(name, secondsOfGame);
					invokeRecordsFrame(GameDifficulty.Hard);
				}
				break;
			case Special:
				break;
			}
		} else {
			JOptionPane.showMessageDialog(this, "I'm sorry, you've losed :(",
					"Lose!", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void startGame() {
		secondsOfGame = 0;
		timer.start();
	}

	@Override
	public void updateBombsCount(int newCount) {
		lblBombs.setText(numberConvert(newCount));
	}
}
