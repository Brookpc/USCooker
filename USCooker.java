import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Skill;

@ScriptManifest(author = "Ark",
category = Category.COOKING, 
description = "Ultimate Scape 2 - AIO Cooker",
name = "USCook by Ark",
servers = { "Ultimate Scape" },
version = 2.5)

public class USCooker extends Script implements Paintable {

	//Strategies List
	private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();

	//General variables
	public static int cookID = 0;
	public static int rawID = 0;
	public static int burnID = 0;

	//Paint Variables
	private final Color textColor = new Color(255, 255, 255);
	private final Font textFont = new Font("Arial", 0, 14);
	private final Timer RUNTIME = new Timer();
	private static Image img;
	public static int cookCount;
	private static int gainedLvl;
	private static int startLvl;

	//GUI
	Gui x = new Gui();
	public boolean guiWait = true;

	/***************************************************************************************************************************************/
	
	public boolean onExecute() {
		x.setVisible(true);
		while (x.isRunning && guiWait) {
			Time.sleep(200);
		}
		strategies.add(new Log());
		strategies.add(new Walking());
		strategies.add(new Cook());
		strategies.add(new Banking());

		startLvl = Skill.COOKING.getRealLevel();

		img = getImage("http://i.imgur.com/gTXu4Ww.png");

		provide(strategies);

		System.out.println("Loaded...");
		return true;
	}
	
	/***************************************************************************************************************************************/
	@Override
	public void paint(Graphics arg0) {
		Graphics2D g = (Graphics2D) arg0;

		if(startLvl == 1) 
			gainedLvl = Skill.COOKING.getRealLevel() - startLvl - 1;

		gainedLvl = Skill.COOKING.getRealLevel() - startLvl;

		g.drawImage(img, 4, 23, null);
		g.setFont(textFont);
		g.setColor(textColor);
		g.drawString(addDecimals(gainedLvl), 82, 57);
		g.drawString(addDecimals(cookCount), 82, 70);
		g.drawString("" + RUNTIME, 82, 83);

	}
	
	/***************************************************************************************************************************************/

	public static Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/***************************************************************************************************************************************/

	public String addDecimals(int i) {
		DecimalFormat x = new DecimalFormat("#,###");
		return "" + x.format(i);
	}

	/***************************************************************************************************************************************/
	
	public class Gui extends JFrame {
		
		private static final long serialVersionUID = -6241803601296202605L;
		public boolean isRunning = true;
		private JPanel contentPane;

		public void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						Gui frame = new Gui();
						frame.setVisible(true);
					} catch (Exception e) {
						System.out.println("Line 136");
					}
				}
			});
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Gui() {
			initComponents();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 150, 180);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			//Title Label
			lblUSCook = new JLabel("USCooker");
			lblUSCook.setFont(new Font("Arial", Font.PLAIN, 20));
			lblUSCook.setBounds(15, 11, 101, 16);
			contentPane.add(lblUSCook);

			//Ready Label
			lblReady = new JLabel("by Ark");
			lblReady.setBounds(17, 49, 82, 14);
			contentPane.add(lblReady);

			//Combo Box
			JComboBox fishList = new JComboBox();
			fishList.setModel(new DefaultComboBoxModel(foodStrings));
			fishList.setSelectedIndex(0);
			fishList.setBounds(10, 75, 95, 23);
			contentPane.add(fishList);

			//Start Button
			btnStart = new JButton("Start");
			btnStart.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(fishList.getSelectedIndex() == 0) {
						//shrimp
						rawID = 318;
						cookID = 316;
						burnID = 324;
					} else if (fishList.getSelectedIndex() == 1) {
						//trout
						rawID = 336;
						cookID = 334;
						burnID = 344;
					} else if (fishList.getSelectedIndex() == 2) {
						//lobster
						rawID = 378;
						cookID = 380;
						burnID = 382;
					} else if (fishList.getSelectedIndex() == 3) {
						//tuna
						rawID = 360;
						cookID = 362;
						burnID = 368;
					} else if (fishList.getSelectedIndex() == 4) {
						//shark
						rawID = 384;
						cookID = 386;
						burnID = 388;
					} else if (fishList.getSelectedIndex()== 5) {
						//rocktail
						rawID = 15271;
						cookID = 15273;
						burnID = 15275;
					}
					
					guiWait = false;
					isRunning = false;
					x.dispose();
				}

			});
			btnStart.setBounds(10, 112, 95, 23);
			contentPane.add(btnStart);

		}

		private void initComponents() {
			lblUSCook = new JLabel();
			lblReady = new JLabel();
		}

		public String[] foodStrings = { "Shrimp", "Trout", "Lobster", "Tuna", "Shark", "RockTail" };
		private JLabel lblUSCook;
		private JButton btnStart;
		@SuppressWarnings({ "rawtypes", "unused"})
		private JComboBox fishList;
		private JLabel lblReady;
	}
}
