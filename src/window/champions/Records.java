package window.champions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import window.main.GameDifficulty;
import main.Const;

public class Records {

	private HashMap<GameDifficulty, ArrayList<Record>> records;
	private File recordsFile;
	
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
			records.put(GameDifficulty.Easy, new ArrayList<Record>());
			records.put(GameDifficulty.Medium, new ArrayList<Record>());
			records.put(GameDifficulty.Hard, new ArrayList<Record>());
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
	
	public void clearTable(GameDifficulty table) {
		records.put(table, new ArrayList<Record>());
		write();
	}
	
	public void addRecord(GameDifficulty table, String name, int time) {
		ArrayList<Record> list = records.get(table);
		
		for(int i = 0; i < list.size() + 1 && i < 10; ++i) {
			if(i >= list.size() || time < ((Record) list.get(i)).getTime()) {
				list.add(i, new Record(name, time));
				break;
			}
		}
		
		if(list.size() > 10) {
			list.remove(10);
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

	public Object[][] getList(GameDifficulty table) {
		ArrayList<Record> list = records.get(table);
		Object[][] result = new Object[list.size()][2];
		
		for(int i = 0; i < list.size(); ++i) {
			result[i][0] = list.get(i).name;
			result[i][1] = list.get(i).time;
		}
		
		return result;
	}
	
	public boolean canWrite(GameDifficulty table, int time) {
		ArrayList<Record> list = records.get(table);
		
		if(list.size() < 10) {
			return true;
		}
		if( ((Record) list.get(9)).getTime() > time) {
			return true;
		}
		
		return false;
	}

	public static class Record implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6069766564430003969L;
		
		private String name;
		private int time;

		public Record(String name, int time) {
			this.name = name;
			this.time = time;
		}
		
		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}
		
		@Override
		public String toString() {
			return name + " - " + time;
		}
	}
}
