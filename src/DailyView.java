import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.omg.CORBA.portable.InputStream;

public class DailyView
{
	static ArrayList<EventData> events;
//	static Model m = new Model();
	static Object o = new Object();

	public DailyView(ArrayList<EventData> e)
	{
	}

	static EventView ae = new EventView();

	//Removed static field Model m and made it a parameter so it pulls the most recent model
	public static void createAndShowDailyViewGUI(final Model m)
	{
		events = new ArrayList<EventData>();
		for(EventData d: m.eventData)
		{
			if(d.date[0] == MONTHS.valueOf(m.getCurrentMonth()+"").ordinal()+1 && d.date[1] == m.getCurrentDay() && d.date[2] == m.getCurrentYear())
				events.add(d);
		}
		
		final Color babyTeal = new Color(142, 229, 238);
		Font railWayBig = new Font("Raleway-Regular", Font.PLAIN, 26);
		Font railWayBigBold = new Font("Raleway-Regular", Font.BOLD, 36);
		Font railWay = new Font("Raleway-Regular", Font.PLAIN, 12);

		java.io.InputStream is = DailyView.class
				.getResourceAsStream("Raleway-Regular.ttf");
		try
		{
			Font font = Font.createFont(Font.TRUETYPE_FONT, is);
			railWay = font.deriveFont(Font.PLAIN, 12);
			railWayBig = font.deriveFont(Font.PLAIN, 26);
			railWayBigBold = font.deriveFont(Font.BOLD, 26);

		} catch (FontFormatException e1)
		{
			e1.printStackTrace();
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}

		// UPPER GUI
		JPanel upperDVPannel = new JPanel();
		upperDVPannel.setLayout(new GridLayout(2, 2));

		// UPPER LEFT
		JPanel upperLeftPannel = new JPanel();
		upperLeftPannel.setBackground(Color.WHITE);
		upperLeftPannel.setLayout(new FlowLayout(FlowLayout.CENTER));
		upperDVPannel.add(upperLeftPannel, BorderLayout.WEST);

		JLabel dateLabelOfMonth = new JLabel(" ");
		dateLabelOfMonth.setText(m.getCurrentMonth() + " "
				+ Integer.toString(m.getCurrentDay()) + ", ");
		dateLabelOfMonth.setFont(railWayBig);
		dateLabelOfMonth.setLayout(new FlowLayout(20, 20, 20));
		upperLeftPannel.add(dateLabelOfMonth);

		JLabel dateLabelOfYear = new JLabel(" ");
		dateLabelOfYear.setText(Integer.toString(m.getCurrentYear()));
		dateLabelOfYear.setForeground(babyTeal);
		dateLabelOfYear.setFont(railWayBig);
		dateLabelOfYear.setLayout(new FlowLayout(20, 20, 20));
		upperLeftPannel.add(dateLabelOfYear);

		// UPPER RIGHT
		JPanel upperRightPannel = new JPanel();
		upperRightPannel.setBackground(Color.WHITE);
		upperRightPannel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		upperDVPannel.add(upperRightPannel, BorderLayout.EAST);

		final JButton addButton = new JButton("+");
		addButton.setPreferredSize(new Dimension(80, 28));
		addButton.setFont(railWayBigBold);
		upperRightPannel.add(addButton);

		addButton.setBackground(babyTeal);
		addButton.setOpaque(true);
		addButton.setBorderPainted(false);

		addButton.addMouseListener(new MouseListener()
		{
			public void mouseEntered(MouseEvent evt)
			{
				addButton.setBackground(Color.CYAN);
			}// end mouse entered

			public void mouseExited(MouseEvent evt)
			{
				addButton.setBackground(babyTeal);
			}// end mouse exited

			public void mouseClicked(MouseEvent evt)
			{
				addButton.setBackground(Color.GREEN);
			}

			public void mousePressed(MouseEvent evt)
			{
			}

			public void mouseReleased(MouseEvent evt)
			{
			}
		}// end anonymous inner class
				);

		addButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				//If an event exists on this day, pulls the index of event for EventView (Derick)
				int n = -1;
				for(EventData d: m.eventData)
				{
					if(d.date[0] == MONTHS.valueOf(m.getCurrentMonth()+"").ordinal()+1 && d.date[1] == m.getCurrentDay() && d.date[2] == m.getCurrentYear())
						n = m.eventData.indexOf(d);
				}
				EventView.createAndShowGUI(m,n);
				
			}
			
		});

		// UPPER BOTTOM LEFT
		JPanel upperBottomLeftPannel = new JPanel();
		upperBottomLeftPannel.setBackground(babyTeal);
		upperBottomLeftPannel.setLayout(new FlowLayout(FlowLayout.LEFT));
		upperDVPannel.add(upperBottomLeftPannel, BorderLayout.SOUTH);

		JLabel dayOfWeekLabel = new JLabel("");
		dayOfWeekLabel.setText(m.getCurrentDayoftheWeek());
		dayOfWeekLabel.setForeground(Color.WHITE);
		dayOfWeekLabel.setFont(railWayBig);
		upperBottomLeftPannel.add(dayOfWeekLabel);

		// UPPER BOTTOM RIGHT
		JPanel upperBottomRightPannel = new JPanel();
		upperBottomRightPannel.setBackground(babyTeal);
		upperBottomRightPannel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		upperDVPannel.add(upperBottomRightPannel, BorderLayout.SOUTH);

		// LOWER GUI
		JPanel lowerDVPanel = new JPanel();
		lowerDVPanel.setLayout(new GridLayout(24, 0));

		JPanel dailyView12AMPanel = new JPanel();
		dailyView12AMPanel.setBackground(Color.WHITE);
		dailyView12AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView12AMPanel);

		JLabel dailyView12AMLabel = new JLabel("12 AM ");
		dailyView12AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 0 && d.time.meridiem == "AM")
				dailyView12AMLabel.setText(dailyView12AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView12AMPanel.add(dailyView12AMLabel);

		JPanel dailyView1AMPanel = new JPanel();
		dailyView1AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView1AMPanel);

		JLabel dailyView1AMLabel = new JLabel("1 AM ");
		dailyView1AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 1 && d.time.meridiem == "AM")
				dailyView1AMLabel.setText(dailyView1AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView1AMPanel.add(dailyView1AMLabel);

		JPanel dailyView2AMPanel = new JPanel();
		dailyView2AMPanel.setBackground(Color.WHITE);
		dailyView2AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView2AMPanel);

		JLabel dailyView2AMLabel = new JLabel("2 AM ");
		dailyView2AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 2 && d.time.meridiem == "AM")
				dailyView2AMLabel.setText(dailyView2AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView2AMPanel.add(dailyView2AMLabel);

		JPanel dailyView3AMPanel = new JPanel();
		dailyView3AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView3AMPanel);

		JLabel dailyView3AMLabel = new JLabel("3 AM ");
		dailyView3AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 3 && d.time.meridiem == "AM")
				dailyView3AMLabel.setText(dailyView3AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView3AMPanel.add(dailyView3AMLabel);

		JPanel dailyView4AMPanel = new JPanel();
		dailyView4AMPanel.setBackground(Color.WHITE);
		dailyView4AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView4AMPanel);

		JLabel dailyView4AMLabel = new JLabel("4 AM ");
		dailyView4AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 4 && d.time.meridiem == "AM")
				dailyView4AMLabel.setText(dailyView4AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView4AMPanel.add(dailyView4AMLabel);

		JPanel dailyView5AMPanel = new JPanel();
		dailyView5AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView5AMPanel);

		JLabel dailyView5AMLabel = new JLabel("5 AM ");
		dailyView5AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 5 && d.time.meridiem == "AM")
				dailyView5AMLabel.setText(dailyView5AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView5AMPanel.add(dailyView5AMLabel);

		JPanel dailyView6AMPanel = new JPanel();
		dailyView6AMPanel.setBackground(Color.WHITE);
		dailyView6AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView6AMPanel);

		JLabel dailyView6AMLabel = new JLabel("6 AM ");
		dailyView6AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 6 && d.time.meridiem == "AM")
				dailyView6AMLabel.setText(dailyView6AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView6AMPanel.add(dailyView6AMLabel);

		JPanel dailyView7AMPanel = new JPanel();
		dailyView7AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView7AMPanel);

		JLabel dailyView7AMLabel = new JLabel("7 AM ");
		dailyView7AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 7 && d.time.meridiem == "AM")
				dailyView7AMLabel.setText(dailyView7AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView7AMPanel.add(dailyView7AMLabel);

		JPanel dailyView8AMPanel = new JPanel();
		dailyView8AMPanel.setBackground(Color.WHITE);
		dailyView8AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView8AMPanel);

		JLabel dailyView8AMLabel = new JLabel("8 AM ");
		dailyView8AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 8 && d.time.meridiem == "AM")
				dailyView8AMLabel.setText(dailyView8AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView8AMPanel.add(dailyView8AMLabel);

		JPanel dailyView9AMPanel = new JPanel();
		dailyView9AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView9AMPanel);

		JLabel dailyView9AMLabel = new JLabel("9 AM ");
		dailyView9AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 9 && d.time.meridiem == "AM")
				dailyView9AMLabel.setText(dailyView9AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView9AMPanel.add(dailyView9AMLabel);

		JPanel dailyView10AMPanel = new JPanel();
		dailyView10AMPanel.setBackground(Color.WHITE);
		dailyView10AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView10AMPanel);

		JLabel dailyView10AMLabel = new JLabel("10 AM ");
		dailyView10AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 10 && d.time.meridiem == "AM")
				dailyView10AMLabel.setText(dailyView10AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView10AMPanel.add(dailyView10AMLabel);

		JPanel dailyView11AMPanel = new JPanel();
		dailyView11AMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView11AMPanel);

		JLabel dailyView11AMLabel = new JLabel("11 AM ");
		dailyView11AMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 11 && d.time.meridiem == "AM")
				dailyView11AMLabel.setText(dailyView11AMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView11AMPanel.add(dailyView11AMLabel);

		JPanel dailyView12PMPanel = new JPanel();
		dailyView12PMPanel.setBackground(Color.WHITE);
		dailyView12PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView12PMPanel);

		JLabel dailyView12PMLabel = new JLabel("12 PM ");
		dailyView12PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 12 && d.time.meridiem == "PM")
				dailyView12PMLabel.setText(dailyView12PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView12PMPanel.add(dailyView12PMLabel);

		JPanel dailyView1PMPanel = new JPanel();
		dailyView1PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView1PMPanel);

		JLabel dailyView1PMLabel = new JLabel("1 PM ");
		dailyView1PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 1 && d.time.meridiem == "PM")
				dailyView1PMLabel.setText(dailyView1PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView1PMPanel.add(dailyView1PMLabel);

		JPanel dailyView2PMPanel = new JPanel();
		dailyView2PMPanel.setBackground(Color.WHITE);
		dailyView2PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView2PMPanel);

		JLabel dailyView2PMLabel = new JLabel("2 PM ");
		dailyView2PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 2 && d.time.meridiem == "PM")
				dailyView2PMLabel.setText(dailyView2PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView2PMPanel.add(dailyView2PMLabel);

		JPanel dailyView3PMPanel = new JPanel();
		dailyView3PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView3PMPanel);

		JLabel dailyView3PMLabel = new JLabel("3 PM ");
		dailyView3PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 3 && d.time.meridiem == "PM")
				dailyView3PMLabel.setText(dailyView3PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView3PMPanel.add(dailyView3PMLabel);

		JPanel dailyView4PMPanel = new JPanel();
		dailyView4PMPanel.setBackground(Color.WHITE);
		dailyView4PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView4PMPanel);

		JLabel dailyView4PMLabel = new JLabel("4 PM ");
		dailyView4PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 4 && d.time.meridiem == "PM")
				dailyView4PMLabel.setText(dailyView4PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView4PMPanel.add(dailyView4PMLabel);

		JPanel dailyView5PMPanel = new JPanel();
		dailyView5PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView5PMPanel);

		JLabel dailyView5PMLabel = new JLabel("5 PM ");
		dailyView5PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 5 && d.time.meridiem == "PM")
				dailyView5PMLabel.setText(dailyView5PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView5PMPanel.add(dailyView5PMLabel);

		JPanel dailyView6PMPanel = new JPanel();
		dailyView6PMPanel.setBackground(Color.WHITE);
		dailyView6PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView6PMPanel);

		JLabel dailyView6PMLabel = new JLabel("6 PM ");
		dailyView6PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 6 && d.time.meridiem == "PM")
				dailyView6PMLabel.setText(dailyView6PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView6PMPanel.add(dailyView6PMLabel);

		JPanel dailyView7PMPanel = new JPanel();
		dailyView7PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView7PMPanel);

		JLabel dailyView7PMLabel = new JLabel("7 PM ");
		dailyView7PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 7 && d.time.meridiem == "PM")
				dailyView7PMLabel.setText(dailyView7PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView7PMPanel.add(dailyView7PMLabel);

		JPanel dailyView8PMPanel = new JPanel();
		dailyView8PMPanel.setBackground(Color.WHITE);
		dailyView8PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView8PMPanel);

		JLabel dailyView8PMLabel = new JLabel("8 PM ");
		dailyView8PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 8 && d.time.meridiem == "PM")
				dailyView8PMLabel.setText(dailyView8PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView8PMPanel.add(dailyView8PMLabel);

		JPanel dailyView9PMPanel = new JPanel();
		dailyView9PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView9PMPanel);

		JLabel dailyView9PMLabel = new JLabel("9 PM ");
		dailyView9PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 9 && d.time.meridiem == "PM")
				dailyView9PMLabel.setText(dailyView9PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView9PMPanel.add(dailyView9PMLabel);

		JPanel dailyView10PMPanel = new JPanel();
		dailyView10PMPanel.setBackground(Color.WHITE);
		dailyView10PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView10PMPanel);

		JLabel dailyView10PMLabel = new JLabel("10 PM ");
		dailyView10PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 10 && d.time.meridiem == "PM")
				dailyView10PMLabel.setText(dailyView10PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView10PMPanel.add(dailyView10PMLabel);

		JPanel dailyView11PMPanel = new JPanel();
		dailyView11PMPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		lowerDVPanel.add(dailyView11PMPanel);

		JLabel dailyView11PMLabel = new JLabel("11 PM ");
		dailyView11PMLabel.setFont(railWay);
		for(EventData d: events)
		{
			if(d.time.hour == 11 && d.time.meridiem == "PM")
				dailyView11PMLabel.setText(dailyView11PMLabel.getText()+"   "+d.name+" "+d.time.toString()+" "+d.location+" "+d.description+"           ");
		}
		dailyView11PMPanel.add(dailyView11PMLabel);

		// FRAME
		final JFrame dailyView = new JFrame("Event Planner Pro");
		dailyView.setLayout(new BorderLayout());
		dailyView.setBackground(Color.WHITE);

		dailyView.add(upperDVPannel, BorderLayout.NORTH);
		dailyView.add(lowerDVPanel, BorderLayout.SOUTH);

		// Display the window.
		dailyView.pack();
		dailyView.setVisible(true);
		
		
		addButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
//				//If an event exists on this day, pulls the index of event for EventView (Derick)
//				int n = -1;
//				for(EventData d: m.eventData)
//				{
//					if(d.date[0] == MONTHS.valueOf(m.getCurrentMonth()+"").ordinal()+1 && d.date[1] == m.getCurrentDay() && d.date[2] == m.getCurrentYear())
//						n = m.eventData.indexOf(d);
//				}
//				EventView.createAndShowGUI(m,n);
				dailyView.setVisible(false);
			}
			
		});

	}

	// MAIN
	// public static void main(String[] args) {
	// //Schedule a job for the event-dispatching thread:
	// //creating and showing this application's GUI.
	// javax.swing.SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// createAndShowDailyViewGUI();
	// }
	// });
	// }
}
