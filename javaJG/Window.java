package javaJG;

import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Toolkit;
import java.util.Timer;

import javax.swing.JFrame;
import javaJG.panel.Panel;

public class Window extends JFrame {
	private static final long serialVersionUID = 2318811948389069875L;

	public static void main(String[] args) {
		Window w = new Window();
		w.setFullscreen(true);
		w.showWindow();
		MenuBar bar = new MenuBar();
		bar.add(new Menu("HEY"));
		w.setMenuBar(bar);
	}

	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Timer timerThread = new Timer();

	public Window() {

	}

	public void showWindow() {
		setVisible(true);
	}

	public void hideWindow() {
		setVisible(false);
	}

	public void setFullscreen(boolean full) {
		if (full) {
			setUndecorated(true);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		} else {
			setUndecorated(false);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}

	public void setPanel(Panel panel) {
		setContentPane(panel);
		timerThread.schedule(panel.createNewTask(), 0, 1);
	}

}
