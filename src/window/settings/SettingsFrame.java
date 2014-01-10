package window.settings;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import window.main.MainFrame;

import java.awt.Component;

import javax.swing.Box;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame {
	
	private MainFrame parent;
	
	private JTextField fldWidth;
	private JTextField fldHeight;
	private JTextField fldBombs;
	
	private JButton btnOk;

	public SettingsFrame(final MainFrame parent, int width, int height, int bombs) {
		this.parent = parent;
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel pnlData = new JPanel();
		getContentPane().add(pnlData);
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
		getContentPane().add(pnlButton);
		pnlButton.setLayout(new BoxLayout(pnlButton, BoxLayout.X_AXIS));
		
		btnOk = new JButton("New button");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				try {
				int width = Integer.parseInt(fldWidth.getText());				
				int height = Integer.parseInt(fldHeight.getText());				
				int bombs = Integer.parseInt(fldBombs.getText());
				
				if(bombs >= width * height) {
					throw new Exception("Cells amount less then bombs");
				}
				
				parent.changeSettings(width, height, bombs);
				dispose();
				} catch (Exception ex) {
					System.err.println("width: " + fldWidth.getText());
					System.err.println("height: " + fldHeight.getText());
					System.err.println("bombs: " + fldBombs.getText());
					JOptionPane
							.showMessageDialog(
									SettingsFrame.this,
									"Incorrect values! Only numbers between 5 and 100 allowed.",
									"Incorrect values!",
									JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pnlButton.add(btnOk);
		
		setBounds(150, 150, 100, 100);
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
