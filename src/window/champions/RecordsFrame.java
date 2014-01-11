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
	
	private Records records;
	private JList<Record> recordsList;

	private JButton btnClear;

	private JButton btnOk;

	public RecordsFrame(Records records, GameDifficulty defaultDifficulty) {
		this.records = records;
		
		initFrame();
		
		cbxDifficulty.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				String item = (String) ev.getItem();
				if(item.equals("Easy")) {
					setList(GameDifficulty.Easy);
				} else if(item.equals("Medium")) {
					setList(GameDifficulty.Medium);
				} else if(item.equals("Hard")) {
					setList(GameDifficulty.Hard);
				}
			}
		});
		
		setList(defaultDifficulty);
		
		pack();
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
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});
		pnlButtons.add(btnOk);
		
		JPanel pnlList = new JPanel();
		getContentPane().add(pnlList, BorderLayout.CENTER);
		
		recordsList = new JList<Record>();
		
		cbxDifficulty.addItem("Easy");
		cbxDifficulty.addItem("Medium");
		cbxDifficulty.addItem("Hard");
		
		pnlList.add(recordsList);
	}

	private void setList(GameDifficulty defaultDifficulty) {
		Object[] list = null;
		Record[] rList = null;
		
		switch(defaultDifficulty) {
		case Easy:
			cbxDifficulty.setSelectedIndex(0);
			list = records.getEasyList().toArray();
			 rList = new Record[list.length];
			for(int i = 0; i < list.length; ++i) {
				rList[i] = (Record) list[i];
			}
			recordsList.setListData(rList);
			break;
		case Medium:
			cbxDifficulty.setSelectedIndex(1);
			list = records.getEasyList().toArray();
			rList = new Record[list.length];
			for(int i = 0; i < list.length; ++i) {
				rList[i] = (Record) list[i];
			}
			recordsList.setListData(rList);
			break;
		case Hard:
			cbxDifficulty.setSelectedIndex(2);
			list = records.getEasyList().toArray();
			rList = new Record[list.length];
			for(int i = 0; i < list.length; ++i) {
				rList[i] = (Record) list[i];
			}
			recordsList.setListData(rList);
			break;
		}
	}
}
