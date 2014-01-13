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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import main.Const;
import window.champions.Records.Record;
import window.main.GameDifficulty;
import window.main.MainFrame;


/**
 * <p>
 * Window for browsing high score tables. GUI contains JComboBox with 3
 * difficulty levels ({@link window.main.GameDifficulty}), JTable for displaying
 * records in chose difficulty and button for dropping this table.
 * </p>
 * 
 * <p>
 * Take the {@link Records} object from {@link MainFrame} to have access to high
 * score tables. Also contains inner class {@link RecordsTableModel} - table
 * model for JTable that updates records in table.
 * </p>
 * 
 * @author Vlad
 */
public class RecordsFrame extends JFrame {

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

	/**
	 * <p>
	 * Initiate frame, set selected difficulty, initiates fields.
	 * </p>
	 * 
	 * @param records - Records object that contains high score tables 
	 * @param defaultDifficulty - difficulty which must be selected in combobox by default
	 * 
	 * @author Vlad
	 */
	public RecordsFrame(Records records, GameDifficulty defaultDifficulty) {
		this.records = records;
		difficulty = defaultDifficulty;
		
		initFrame();
		
		setList(difficulty);
		
		initActions();
		
		pack();
		moveToCenter();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * <p>
	 * Initiates actions for UI components - combobox item changing and buttons
	 * for clearing and exit.
	 * </p>
	 * 
	 * @author Vlad
	 */
	private void initActions() {
		cbxDifficulty.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ev) {
				difficulty = (GameDifficulty) cbxDifficulty.getSelectedItem();
				setList(difficulty);
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
				
				setList(difficulty);
				
				pack();
				repaint();
			}
		});
	}
	
	/**
	 * <p>
	 * Initiates UI components - layouts, buttons, tables, etc.
	 * </p>
	 * 
	 * @author Vlad
	 */
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
		recordsTable.setFont(new Font("ArialBlack", Font.PLAIN, 15));
		scrlPane = new JScrollPane(recordsTable);
		
		resizeTable();
		
		scrlPane.setOpaque(false);
		scrlPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrlPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		pnlList.add(scrlPane, BorderLayout.CENTER);
		
		lblNoRecords = new JLabel("No any records yet");
		lblNoRecords.setFont(new Font("ArialBlack", Font.ITALIC, 14));
		
		cbxDifficulty.addItem(GameDifficulty.Easy);
		cbxDifficulty.addItem(GameDifficulty.Medium);
		cbxDifficulty.addItem(GameDifficulty.Hard);
		cbxDifficulty.setSelectedItem(difficulty);
	}

	/**
	 * <p>
	 * Reckons and applies size of records table. Also move frame to the center of screen.
	 * </p>
	 * 
	 * @author Vlad
	 */
	private void resizeTable() {
		int rowCount = recordsTable.getRowCount();
		int rowHeight = recordsTable.getRowHeight() + recordsTable.getRowMargin(); 
		int headerHeight = recordsTable.getTableHeader().getPreferredSize().height + 2; 
		int tableHeight = rowCount * rowHeight + headerHeight;
		int tableWidth = 200;
		Dimension tableSize = new Dimension(tableWidth, tableHeight);
		scrlPane.setPreferredSize(tableSize);
		scrlPane.setMinimumSize(scrlPane.getPreferredSize());

		moveToCenter();
	}

	private void moveToCenter() {
		int windowX = Const.MiddleOfTheScreenX - getSize().width / 2;
		int windowY = Const.MiddleOfTheScreenY - getSize().height / 2;
		setLocation(windowX, windowY);
	}

	/**
	 * <p>
	 * Update records table to display data of new difficulty level.
	 * </p>
	 * 
	 * @param difficulty - new difficulty for displaying
	 * 
	 * @author Vlad
	 */
	private void setList(GameDifficulty difficulty) {
		tableModel.setData(difficulty);
		if(tableModel.getRowCount() == 0) {
			pnlList.remove(scrlPane);
			pnlList.add(lblNoRecords, BorderLayout.CENTER);
		} else {
			pnlList.remove(lblNoRecords);
			pnlList.add(scrlPane);
		}
		
		resizeTable();
		pack();
	}
	
	/**
	 * <p>
	 * Table model for high score table.
	 * </p>
	 * 
	 * @author Vlad
	 */
	private class RecordsTableModel implements TableModel {

		private Object[][] data;
		private ArrayList<TableModelListener> listeners;
		
		/**
		 * <p>
		 * Initiates new {@link RecordsTableModel} with difficulty level to
		 * display fit data.
		 * </p>
		 * 
		 * @param difficulty
		 *            - specified data that should be displayed
		 * 
		 * @author Vlad
		 */
		public RecordsTableModel(GameDifficulty difficulty) {
			setData(difficulty);
			listeners = new ArrayList<TableModelListener>(0);
		}
		
		/**
		 * <p>
		 * Orders table model to update its data with new records list.
		 * </p>
		 * 
		 * @param difficulty - - specified data that should be displayed
		 * 
		 * @author Vlad
		 */
		public void setData(GameDifficulty difficulty) {
			listToData(records.getList(difficulty));
		}
		
		private void listToData(ArrayList<Record> list) {
			data = new Object[list.size()][2];
			
			for(int i = 0; i < list.size(); ++i) {
				data[i][0] = list.get(i).getName();
				data[i][1] = list.get(i).getTime();
			}
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
