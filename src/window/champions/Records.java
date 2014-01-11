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

import main.Const;

public class Records {

	private ArrayList easyList; 
	private ArrayList mediumList;
	private ArrayList hardList;
	private File easyFile;
	private File mediumFile;
	private File hardFile;
	
	public Records() {
		easyFile = new File(Const.EasyRecordsFile);
		mediumFile = new File(Const.MediumRecordsFile);
		hardFile = new File(Const.HardRecordsFile);
		
		ObjectInputStream easyIn = null;
		ObjectInputStream mediumIn = null;
		ObjectInputStream hardIn = null;
		try {
			if(easyFile.length() > 0) {
				easyIn = new ObjectInputStream(new FileInputStream(easyFile));
				easyList = (ArrayList) easyIn.readObject();
			} else {
				easyList = new ArrayList();
			}
			if(mediumFile.length() > 0) {
				mediumIn = new ObjectInputStream(new FileInputStream(mediumFile));
				mediumList = (ArrayList) mediumIn.readObject();
			} else {
				mediumList = new ArrayList();
			}
			if(hardFile.length() > 0) {
				hardIn = new ObjectInputStream(new FileInputStream(hardFile));
				hardList = (ArrayList) hardIn.readObject();
			} else {
				hardList = new ArrayList();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(easyList == null) easyList = new ArrayList();
			if(mediumList == null) mediumList = new ArrayList();
			if(hardList == null) hardList = new ArrayList();
			writeEasy();
			writeMedium();
			writeHard();
			System.err.println("Record file can't be read");
		} finally {
			try {
				easyIn.close();
				mediumIn.close();
				hardIn.close();
			} catch (Exception e) {
				System.err.println("Stream have not been closed!");
			}
		}
	}
	
	public void clearEasy() {
		easyList = new ArrayList();
		writeEasy();
	}
	
	public void clearMedium() {
		mediumList = new ArrayList();
		writeMedium();
	}
	
	public void clearHard() {
		hardList = new ArrayList();
		writeHard();
	}
	
	public void addEasy(String name, int time) {
		for(int i = 0; i < easyList.size() + 1 && i < 10; ++i) {
			if(i >= easyList.size() || time < ((Record) easyList.get(i)).getTime()) {
				easyList.add(i, new Record(name, time));
				break;
			}
		}
		
		if(easyList.size() > 10) {
			easyList.remove(10);
		}
		
		writeEasy();
	}

	private void writeEasy() {
		ObjectOutputStream out = null;
		try {
			easyFile.delete();
			easyFile.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(easyFile));
			out.writeObject(easyList);
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
	
	public void addMedium(String name, int time) {
		for(int i = 0; i < mediumList.size() + 1 && i < 10; ++i) {
			if(i >= mediumList.size() || time < ((Record) mediumList.get(i)).getTime()) {
				mediumList.add(i, new Record(name, time));
				break;
			}
		}
		
		if(mediumList.size() > 10) {
			mediumList.remove(10);
		}

		writeMedium();
	}

	private void writeMedium() {
		ObjectOutputStream out = null;
		try {
			mediumFile.delete();
			mediumFile.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(mediumFile));
			out.writeObject(mediumList);
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
	
	public void addHard(String name, int time) {
		for(int i = 0; i < hardList.size() + 1 && i < 10; ++i) {
			if(i >= hardList.size() || time < ((Record) hardList.get(i)).getTime()) {
				hardList.add(i, new Record(name, time));
				break;
			}
		}
		
		if(hardList.size() > 10) {
			hardList.remove(10);
		}
		
		writeHard();
	}

	private void writeHard() {
		ObjectOutputStream out = null;
		try {
			hardFile.delete();
			hardFile.createNewFile();
			out = new ObjectOutputStream(new FileOutputStream(hardFile));
			out.writeObject(hardList);
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
	
	public ArrayList<Record> getEasyList() {
		return easyList;
	}

	public ArrayList<Record> getMediumList() {
		return mediumList;
	}

	public ArrayList<Record> getHardList() {
		return hardList;
	}
	
	public boolean isWritableIntoEasy(int time) {
		if(easyList.size() < 10) {
			return true;
		}
		if( ((Record) easyList.get(9)).getTime() > time) {
			return true;
		}
		
		return false;
	}

	public boolean isWritableIntoMedium(int time) {
		if (mediumList.size() < 10) {
			return true;
		}
		if ( ((Record) mediumList.get(9)).getTime() > time) {
			return true;
		}

		return false;
	}

	public boolean isWritableIntoHard(int time) {
		if (hardList.size() < 10) {
			return true;
		}
		if ( ((Record) hardList.get(9)).getTime() > time) {
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
