package window.champions;

import javax.swing.JFrame;

import window.champions.Records.Record;
import window.main.GameDifficulty;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;

public class RecordsFrame extends JFrame {

	private JComboBox cbxDifficulty;
	
	private GameDifficulty difficulty;
	private Records records;
	private JList<Record> recordsList;

	private JButton btnClear;

	private JButton btnOk;

	public RecordsFrame(Records records, GameDifficulty defaultDifficulty) {
		this.records = records;
		
		initFrame();
		
		setList(defaultDifficulty);
		
		cbxDifficulty.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				switch (((JComboBox) ev.getSource()).getSelectedIndex()) {
				case 0:
					setList(GameDifficulty.Easy);
					break;
				case 1:
					setList(GameDifficulty.Medium);
					break;
				case 2:
					setList(GameDifficulty.Hard);
					break;
				}
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				switch (cbxDifficulty.getSelectedIndex()) {
				case 0:
					RecordsFrame.this.records.clearEasy();
					break;
				case 1:
					RecordsFrame.this.records.clearMedium();
					break;
				case 2:
					RecordsFrame.this.records.clearHard();
					break;
				}
				
				recordsList.setListData(new Record[]{new Record("No any records here...", 0)});
				
				pack();
				repaint();
			}
		});
		
		pack();
		setLocation(200, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	private void initFrame() {
		JPanel pnlChoose = new JPanel();
		getContentPane().add(pnlChoose, BorderLayout.NORTH);
		
		cbxDifficulty = new JComboBox();
		pnlChoose.add(cbxDifficulty);
		
		JPanel pnlButtons = new JPanel();
		getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		
		btnClear = new JButton("Clear");
		pnlButtons.add(btnClear);
		
		btnOk = new JButton("Ok");
		pnlButtons.add(btnOk);
		
		JPanel pnlList = new JPanel();
		getContentPane().add(pnlList, BorderLayout.CENTER);
		
		recordsList = new JList<Record>();
		
		cbxDifficulty.addItem("Easy");
		cbxDifficulty.addItem("Medium");
		cbxDifficulty.addItem("Hard");
		
		switch(difficulty) {
		case Easy:
			cbxDifficulty.setSelectedIndex(0);
			break;
		case Medium:
			cbxDifficulty.setSelectedIndex(1);
			break;
		case Hard:
			cbxDifficulty.setSelectedIndex(2);
			break;
		}
		
		pnlList.add(recordsList);
	}

	private void setList(GameDifficulty difficulty) {
		Object[] list = null;
		Record[] rList = null;
		
		switch(difficulty) {
		case Easy:
			list = records.getEasyList().toArray();
			 rList = new Record[list.length];
			for(int i = 0; i < list.length; ++i) {
				rList[i] = (Record) list[i];
			}
			
			if(rList.length > 0) {
				recordsList.setListData(rList);
			} else {
				recordsList.setListData(new Record[]{new Record("No any records here...", 0)});
			}
			break;
		case Medium:
			list = records.getMediumList().toArray();
			rList = new Record[list.length];
			for(int i = 0; i < list.length; ++i) {
				rList[i] = (Record) list[i];
			}

			if(rList.length > 0) {
				recordsList.setListData(rList);
			} else {
				recordsList.setListData(new Record[]{new Record("No any records here...", 0)});
			}
			break;
		case Hard:
			list = records.getHardList().toArray();
			rList = new Record[list.length];
			for(int i = 0; i < list.length; ++i) {
				rList[i] = (Record) list[i];
			}

			if(rList.length > 0) {
				recordsList.setListData(rList);
			} else {
				recordsList.setListData(new Record[]{new Record("No any records here...", 0)});
			}
			break;
		}
		
		pack();
		repaint();
	}
}
