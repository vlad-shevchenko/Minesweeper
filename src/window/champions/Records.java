package window.champions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;

import main.Const;
import window.main.GameDifficulty;

/**
 * <p>
 * Class responsible for high score tables loading, editing, saving and allow
 * other classes to get tables for specified {@link GameDifficulty}.
 * </p>
 * <p>
 * Tables stored in HashMap, wherein {@link GameDifficulty} object is a key and
 * ArrayList is a value. ArrayList contains list of ten most quickly players in
 * current difficulty level. Method {@link #write()} save HashMap into a file
 * (specified by the constant {@link main.Const#RecordsFile}). Rewrites after
 * every record adding.
 * </p>
 * 
 * <p>
 * Inner class {@link Record} contains name of player and the time he took for
 * win. Implements {@link Serializable} interface.
 * </p>
 * 
 * @author Vlad
 */
public class Records {

	private HashMap<GameDifficulty, ArrayList<Record>> records;
	private File recordsFile;
	
	/**
	 * <p>
	 * Try to load HashMap from file specified in {@link Const}. If file is not
	 * found or it is empty or object is invalid, creates HashMap and writes it
	 * to the new file.
	 * </p>
	 */
	public Records() {
		recordsFile = new File(Const.RecordsFile);
		
		ObjectInputStream in = null;
		try {
			if(recordsFile.length() > 0) {
				in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(recordsFile)));
				Object obj = in.readObject();
				if(obj instanceof HashMap<?, ?>) {
					records = (HashMap<GameDifficulty, ArrayList<Record>>) obj;
				} else {
					throw new ClassCastException(obj.getClass().getCanonicalName());
				}
			} else {
				records = new HashMap<GameDifficulty, ArrayList<Record>>(3);
				throw new Exception("File is empty. Created new high score table");
			}
		} catch (Exception e) {
			records = new HashMap<GameDifficulty, ArrayList<Record>>(3);
			records.put(GameDifficulty.Easy, new ArrayList<Record>());
			records.put(GameDifficulty.Medium, new ArrayList<Record>());
			records.put(GameDifficulty.Hard, new ArrayList<Record>());
			write();
		} finally {
			try {
				if(in != null) {
					in.close();
				}
			} catch (Exception e) {
				System.err.println("Stream have not been closed!");
			}
		}
	}
	
	/**
	 * <p>
	 * Drop all data in the specified table and write it into the file.
	 * </p>
	 * 
	 * @param difficulty
	 *            - game difficult specifies the table to clear
	 */
	public void clearTable(GameDifficulty difficulty) {
		records.put(difficulty, new ArrayList<Record>());
		write();
	}
	
	/**
	 * <p>
	 * Try to add new record into the specified table. Table is ordered by
	 * increasing (most quickly players in the top).
	 * </p>
	 * 
	 * <p>
	 * Maximum number of players in one table is 10. So, if player time is more
	 * then time of 10th player's one, records will not be added. So <b>adding a
	 * record is not guaranteed</b>. To check the possibility of adding use the
	 * {@link #canAdd(GameDifficulty, int) canAdd} method.
	 * </p>
	 * 
	 * <p>
	 * After adding write new object to the file.
	 * </p>
	 * 
	 * @param difficulty
	 *            - game difficulty specifies the table for adding
	 * @param name
	 *            - name of player
	 * @param time
	 *            - time player took for win
	 */
	public void addRecord(GameDifficulty difficulty, String name, int time) {
		ArrayList<Record> list = records.get(difficulty);
		
		for(int i = 0; i < list.size() + 1 && i < Const.MaxRecordsNumber; ++i) {
			if(i >= list.size() || time < ((Record) list.get(i)).getTime()) {
				list.add(i, new Record(name, time));
				break;
			}
		}
		
		if(list.size() > Const.MaxRecordsNumber) {
			list.remove(Const.MaxRecordsNumber);
		}
		
		write();
	}
	
	private void write() {
		ObjectOutputStream out = null;
		try {
			recordsFile.delete();
			recordsFile.createNewFile();
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(recordsFile)));
			out.writeObject(records);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				System.err.println("Stream have not been closed!");
			}
		}
	}

	/**
	 * <p>
	 * Returns high score table for specified game difficulty.
	 * </p>
	 * 
	 * @param difficulty
	 *            - game difficulty specifies table to get
	 * @return ArrayList of Record objects, than contains players name and times
	 */
	public Object[][] getList(GameDifficulty difficulty) {
		ArrayList<Record> list = records.get(difficulty);
		Object[][] result = new Object[list.size()][2];
		
		for(int i = 0; i < list.size(); ++i) {
			result[i][0] = list.get(i).name;
			result[i][1] = list.get(i).time;
		}
		
		return result;
	}
	
	/**
	 * <p>
	 * Check is it possible to add new record to a specified table with the
	 * specified result (time).
	 * </p>
	 * 
	 * @param difficulty
	 *            - specifies table to add new record
	 * @param time
	 *            - time user took for a win
	 * @return <b>true</b> if record with such time can be added with the
	 *         {@link #addRecord(GameDifficulty, String, int) addRecord} method
	 *         and <b>false</b> - otherwise
	 */
	public boolean canAdd(GameDifficulty difficulty, int time) {
		ArrayList<Record> list = records.get(difficulty);
		
		if(list.size() < Const.MaxRecordsNumber) {
			return true;
		}
		if( ((Record) list.get(Const.MaxRecordsNumber - 1)).getTime() > time) {
			return true;
		}
		
		return false;
	}

	/**
	 * <p>
	 * Class than contains name of player and time he took for a win.
	 * </p>
	 * 
	 * <p>
	 * Implements {@link Serializable} interface.
	 * </p>
	 * 
	 * @author Vlad
	 */
	public static class Record implements Serializable {
		
		private String name;
		private int time;

		/**
		 * <p>Initiates fields of object with data was passed</p>
		 * 
		 * @param name - name of player
		 * @param time - time for win
		 */
		public Record(String name, int time) {
			this.name = name;
			this.time = time;
		}
		
		/**
		 * <p>Returns name of player.</p>
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * <p>Returns time for win.</p>
		 * 
		 * @return time player took for win
		 */
		public int getTime() {
			return time;
		}
		
		@Override
		public String toString() {
			return name + " - " + time;
		}
	}
}
