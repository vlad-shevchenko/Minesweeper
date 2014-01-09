package window;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Canvas;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import main.Const;

/**
 * // * Main window of program. Contains field
 * 
 * @author Влад
 */
public class MainFrame extends JFrame {

	private JLabel lblTime;
	private JLabel lblBombs;
	private JButton btnNewGame;
	
	private JPanel field;
	
	private JMenuBar menuBar;
	
	private final String POSITIVE_SMILE_PATH = "D:\\Projects\\Java\\all\\Minesweeper\\res\\pos.png";
	private final String NEGATIVE_SMILE_PATH = "D:\\Projects\\Java\\all\\Minesweeper\\res\\neg.png";
	private final Image POSITIVE_SMILE = new ImageIcon(POSITIVE_SMILE_PATH)
			.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);
	private final Image NEGATIVE_SMILE = new ImageIcon(NEGATIVE_SMILE_PATH)
	.getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT);

	public MainFrame() {
		field = new Field();
		field.setLayout(new GridLayout(5, 5));
		field.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(field, BorderLayout.CENTER);

		JPanel pnlHeader = new JPanel();
		pnlHeader.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(pnlHeader, BorderLayout.NORTH);
		pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.X_AXIS));

		Component horizontalStrut = Box.createHorizontalStrut(10);
		pnlHeader.add(horizontalStrut);

		lblTime = new JLabel("012");
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
		btnNewGame.setIcon(new ImageIcon(POSITIVE_SMILE));
		pnlHeader.add(btnNewGame);

		Component horizontalGlue_1 = Box.createHorizontalGlue();
		pnlHeader.add(horizontalGlue_1);

		lblBombs = new JLabel("234");
		lblBombs.setOpaque(true);
		lblBombs.setBackground(new Color(0, 0, 139));
		lblBombs.setForeground(new Color(165, 42, 42));
		lblBombs.setFont(new Font("Asrock7Segment", Font.BOLD, 16));
		pnlHeader.add(lblBombs);

		Component horizontalStrut_1 = Box.createHorizontalStrut(10);
		pnlHeader.add(horizontalStrut_1);

		initMenuBar();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);
		setResizable(false);
		setResizable(false);
		pack();
		setVisible(true);
	}

	private void initMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);

			JMenuItem mntmNewGame = new JMenuItem("New game");
			mnGame.add(mntmNewGame);
			
			mnGame.addSeparator();
	
			JMenuItem mntmEasy = new JMenuItem("Easy");
			mnGame.add(mntmEasy);
	
			JMenuItem mntmMedium = new JMenuItem("Medium");
			mnGame.add(mntmMedium);
	
			JMenuItem mntmHard = new JMenuItem("Hard");
			mnGame.add(mntmHard);
	
			JMenuItem mntmSpecial = new JMenuItem("Special");
			mnGame.add(mntmSpecial);
	
			mnGame.addSeparator();
			
			JMenuItem mntmExit = new JMenuItem("Exit");
			mnGame.add(mntmExit);
	}
}
