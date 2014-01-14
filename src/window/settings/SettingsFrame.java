package window.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import window.main.MainFrame;

/**
 * <p>
 * Window for specifying parameters of game: field width, height and amount of
 * bombs. Contains three TextField's for input and Ok button.
 * </p>
 * 
 * @author Vlad
 */
public class SettingsFrame extends JFrame {

	private MainFrame parent;

	private JTextField fldWidth;
	private JTextField fldHeight;
	private JTextField fldBombs;

	private JButton btnOk;

	/**
	 * <p>
	 * Initiates frame and fields. Take MainFrame object as parameter for
	 * returning new settings after confirming (by invoking
	 * {@link MainFrame#changeSettings(int, int, int)} method). Also take
	 * current field width, height and amount of bombs to initiate text fields.
	 * </p>
	 * 
	 * @param parent
	 *            - parent window for returning new settings
	 * @param width
	 *            - width of the field
	 * @param height
	 *            - height of the field
	 * @param bombs
	 *            - amount of bombs at the field
	 * 
	 * @author Vlad
	 */
	public SettingsFrame(MainFrame parent, int width, int height, int bombs) {
		this.parent = parent;

		initFrame(width, height, bombs);
		initActions();
		
		pack();
		
		int centerX = (int) GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds().getCenterX();
		int centerY = (int) GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds().getCenterY();
		int windowX = centerX - getSize().width / 2;
		int windowY = centerY - getSize().height / 2;
		setLocation(windowX, windowY);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void initFrame(int width, int height, int bombs) {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel pnlMain = new JPanel();
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlMain);
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));

		JPanel pnlData = new JPanel();
		pnlMain.add(pnlData);
		pnlData.setLayout(new BoxLayout(pnlData, BoxLayout.X_AXIS));

		JPanel pnlLabels = new JPanel();
		pnlData.add(pnlLabels);
		pnlLabels.setLayout(new BoxLayout(pnlLabels, BoxLayout.Y_AXIS));

		Component verticalStrut = Box.createVerticalStrut(3);
		pnlLabels.add(verticalStrut);

		JLabel lblWidth = new JLabel("Width:");
		pnlLabels.add(lblWidth);

		Component verticalGlue = Box.createVerticalGlue();
		pnlLabels.add(verticalGlue);

		JLabel lblHeight = new JLabel("Height:");
		pnlLabels.add(lblHeight);

		Component verticalGlue_1 = Box.createVerticalGlue();
		pnlLabels.add(verticalGlue_1);

		JLabel lblBombs = new JLabel("Bombs:");
		pnlLabels.add(lblBombs);

		Component horizontalGlue = Box.createHorizontalGlue();
		pnlData.add(horizontalGlue);

		JPanel pnlFields = new JPanel();
		pnlData.add(pnlFields);
		pnlFields.setLayout(new BoxLayout(pnlFields, BoxLayout.Y_AXIS));

		fldWidth = new JTextField(String.valueOf(width));
		lblWidth.setLabelFor(fldWidth);

		Component verticalStrut_1 = Box.createVerticalStrut(3);
		pnlLabels.add(verticalStrut_1);
		fldWidth.setMaximumSize(new Dimension(2147483647, 20));
		pnlFields.add(fldWidth);
		fldWidth.setColumns(5);

		Component verticalGlue_3 = Box.createVerticalGlue();
		pnlFields.add(verticalGlue_3);

		fldHeight = new JTextField(String.valueOf(height));
		fldHeight.setMaximumSize(new Dimension(2147483647, 20));
		pnlFields.add(fldHeight);
		fldHeight.setColumns(5);

		Component verticalGlue_2 = Box.createVerticalGlue();
		pnlFields.add(verticalGlue_2);

		fldBombs = new JTextField(String.valueOf(bombs));
		fldBombs.setMaximumSize(new Dimension(2147483647, 20));
		pnlFields.add(fldBombs);
		fldBombs.setColumns(5);

		JPanel pnlButton = new JPanel();
		pnlMain.add(pnlButton);
		pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.X_AXIS));

		btnOk = new JButton("Ok");
		pnlButton.add(btnOk);
	}

	private void initActions() {
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
					int width = Integer.parseInt(fldWidth.getText());
					int height = Integer.parseInt(fldHeight.getText());
					int bombs = Integer.parseInt(fldBombs.getText());

					if (bombs >= width * height) {
						throw new NumberFormatException(
								"Cells amount less then bombs");
					}

					parent.changeSettings(width, height, bombs);
					dispose();
				} catch (NumberFormatException ex) {
					System.err.println("width: " + fldWidth.getText());
					System.err.println("height: " + fldHeight.getText());
					System.err.println("bombs: " + fldBombs.getText());
					JOptionPane
							.showMessageDialog(
									SettingsFrame.this,
									"Incorrect values! Only numbers allowed and number of bombs must be less then number of cells.",
									"Incorrect values!",
									JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
}
