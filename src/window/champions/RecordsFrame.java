package window.champions;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import main.Const;
import sun.awt.HorizBagLayout;
import window.main.GameDifficulty;

import javax.swing.JScrollPane;

public class RecordsFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1630134553055909176L;

	private JComboBox<GameDifficulty> cbxDifficulty;
	
	private GameDifficulty difficulty;
	private final Records records;

	private JPanel pnlList;
	private JButton btnClear;
	private JButton btnOk;
	private JLabel lblNoRecords;
	
	private JTable recordsTable;
	private RecordsTableModel tableModel;
	private JScrollPane scrlPane;

	public RecordsFrame(Records records, GameDifficulty defaultDifficulty) {
		this.records = records;
		difficulty = defaultDifficulty;
		
		initFrame();
		
		setList(difficulty);
		
		initActions();
		
		pack();
		setLocation(50, 50);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void initActions() {
		cbxDifficulty.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				setList((GameDifficulty) cbxDifficulty.getSelectedItem());
			}
		});
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				dispose();
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				records.clearTable(difficulty);
				
				tableModel.setData(difficulty);
				
				pack();
				repaint();
			}
		});
	}
	
	private void initFrame() {
		JPanel pnlChoose = new JPanel();
		getContentPane().add(pnlChoose, BorderLayout.NORTH);
		
		cbxDifficulty = new JComboBox<GameDifficulty>();
		pnlChoose.add(cbxDifficulty);
		
		JPanel pnlButtons = new JPanel();
		getContentPane().add(pnlButtons, BorderLayout.SOUTH);
		
		btnClear = new JButton("Clear");
		pnlButtons.add(btnClear);
		
		btnOk = new JButton("Ok");
		pnlButtons.add(btnOk);
		
		pnlList = new JPanel();
		getContentPane().add(pnlList, BorderLayout.CENTER);
		pnlList.setLayout(new BorderLayout(0, 0));
		pnlList.setBorder(new EmptyBorder(5, 15, 5, 15));
		
		tableModel = new RecordsTableModel(difficulty);
		recordsTable = new JTable(tableModel);
		recordsTable.setOpaque(false);
		scrlPane = new JScrollPane(recordsTable);
		scrlPane.setPreferredSize(new Dimension(200, 300));
		scrlPane.setOpaque(false);
		scrlPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrlPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		pnlList.add(scrlPane, BorderLayout.CENTER);
		
		lblNoRecords = new JLabel("No any records yet");
		lblNoRecords.setFont(new Font("ArialBlack", Font.ITALIC, 14));
		
		cbxDifficulty.addItem(GameDifficulty.Easy);
		cbxDifficulty.addItem(GameDifficulty.Medium);
		cbxDifficulty.addItem(GameDifficulty.Hard);
		cbxDifficulty.setSelectedItem(difficulty);
	}

	private void setList(GameDifficulty difficulty) {
		tableModel.setData(difficulty);
		if(tableModel.getRowCount() == 0) {
			pnlList.remove(scrlPane);
			pnlList.add(lblNoRecords, BorderLayout.CENTER);
//			scrlPane.setViewportView(lblNoRecords);
		} else {
//			scrlPane.setViewportView(recordsTable);
			pnlList.remove(lblNoRecords);
			pnlList.add(scrlPane);
		}
		
		pack();
		repaint();
	}
	
	private class RecordsTableModel implements TableModel {

		private Object[][] data;
		private ArrayList<TableModelListener> listeners;
		
		public RecordsTableModel(GameDifficulty difficulty) {
			data = records.getList(difficulty);
			listeners = new ArrayList<TableModelListener>(0);
		}
		
		public void setData(GameDifficulty difficulty) {
			data = records.getList(difficulty);
		}
		
		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public Object getValueAt(int row, int column) {
			if(column == 0) {
				return (String) data[row][column];
			} else if (column == 1) {
				return (Integer) data[row][column];
			} else {
				return null;
			}
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
			listeners.add(l);
		}

		@Override
		public Class<?> getColumnClass(int index) {
			 if(index == 0) {
				 return String.class;
			 } else if(index == 1) {
				 return Integer.class;
			 } else {
				 return Object.class;
			 }
		}

		@Override
		public String getColumnName(int columnIndex) {
			return Const.TableColumnNames[columnIndex];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			listeners.remove(l);
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			data[rowIndex][columnIndex] = aValue;
		}
		
	}
}
