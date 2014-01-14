package main;

import window.main.MainFrame;
import constant.ConstInitializer;

/**
 * <p>
 * Start point of the program. Just initiates {@link MainFrame} object.
 * </p>
 * 
 * @author Vlad
 */
public class Main {

	/**
	 * <p>
	 * Loads constants from *.xml file. Then initiates {@link MainFrame} object
	 * to start the program work.
	 * </p>
	 * 
	 * @param args
	 *            - parameters of command line
	 */
	public static void main(String[] args) {
		new ConstInitializer().initialize();
		new MainFrame();
	}
}
