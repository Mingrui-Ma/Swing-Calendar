package cal;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class CalendarMain extends JPanel	{
	JTable table;
	GregorianCalendar currentCal;
	DefaultTableModel model;
	JLabel timeLabel;
	boolean btnsPressed = false;
	
	CalendarMain()	{
    	//super(new GridBagLayout());
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
    	
		/*
		JLabel blank = new JLabel("");
    	GridBagConstraints gbc0 = new GridBagConstraints();
    	gbc0.gridx = 0;
    	gbc0.gridy = 0;
    	gbc0.gridheight = 1;
    	gbc0.gridwidth = 1;
    	gbc0.weightx = 0.5;
    	gbc0.weighty = 0.5;
    	gbc0.fill = GridBagConstraints.BOTH;
    	//gbc0.anchor = GridBagConstraints.PAGE_END;
    	add(blank,gbc0);
    	*/

    	
    	currentCal = new GregorianCalendar();
    	table = prepareCalendar(currentCal);

    	JScrollPane tableScroll = new JScrollPane(table);
    	GridBagConstraints gbc2 = new GridBagConstraints();
    	gbc2.gridx = 0;
    	gbc2.gridy = 1;
    	gbc2.gridheight = 1;
    	gbc2.gridwidth = 3;
    	gbc2.weightx = 0.5;
    	gbc2.weighty = 0.5;
    	gbc2.fill = GridBagConstraints.BOTH;
    	gbc2.anchor = GridBagConstraints.CENTER;
    	gbc2.insets = new Insets(0,0,0,0);
    	gbc2.ipady = 0;
    	//gbc.weightx
    	add(tableScroll, gbc2);

    	
    	JToolBar toolbar = prepareToolBar();
    	toolbar.setAlignmentX(GridBagConstraints.CENTER);
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.gridx = 1;
    	gbc.gridy = 0;
    	gbc.gridheight = 1;
    	gbc.gridwidth = 1;
    	gbc.weightx = 0.5;
    	gbc.weighty = 0.5;
    	gbc.ipadx = gbc.gridwidth;
    	gbc.fill = GridBagConstraints.BOTH;
    	gbc.insets = new Insets(0,10,0,0);
    	//gbc.anchor = GridBagConstraints.PAGE_END;
    	add(toolbar,gbc);
    }
	
	/*
	 * Part one: creating the calendar
	 */
	private JTable prepareCalendar(GregorianCalendar cal)	{
		Integer day = cal.get(Calendar.DAY_OF_MONTH), month = cal.get(Calendar.MONTH) + 1,  yr = cal.get(Calendar.YEAR);
		
    	model = new DefaultTableModel(); 
    	JTable table = new JTable(model); 
    	//disable interactivity with cells, rows and columns
    	table.setRowSelectionAllowed(false);
    	table.setColumnSelectionAllowed(false);
    	table.setCellSelectionEnabled(false);
    	table.getTableHeader().setReorderingAllowed(false);
    	
    	//add columns 
    	String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    	for(String d:days)
    		model.addColumn(d); 

    	// add data 
    	Object[][] monthLayout = getMonthLayout(cal);
    	for(int i=0;i<monthLayout.length;i++)
    		model.addRow(monthLayout[i]);

    	//set font 
    	table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
    	table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 28));
    	  	
    	//centering
    	DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    	renderer.setHorizontalAlignment(JLabel.CENTER);
    	table.setDefaultRenderer(Object.class, renderer);
    	
    	//tooltip
    	String timeStr = "Today is " + yr.toString() + "-" + month.toString() + "-" + day.toString();
    	table.setToolTipText(timeStr);
    	
    	//set row and column sizes
    	table.setRowHeight(50);
    	TableColumnModel colMod = table.getColumnModel();
    	for(int i=0;i<table.getColumnCount();i++)
    		colMod.getColumn(i).setPreferredWidth(100);		//doesn't do anything?
    	
    	return table;
	}
	
		/**
		 * 
		 * @return a 7 length Object array with all elements set to null.
		 */
		private static Object[] makeEmptyWeek()	{
			Object[] week = new Object[7];
			for(int i=0;i<7;i++)
				week[i] = null;
			return week;
		}
	
		/**
		 * 
		 * @return the layout of the month (day of week of the first day; number of days) in Object[][], compatible with JTable.
		 */
		private Object[][] getMonthLayout(GregorianCalendar gcal)	{
			Integer today = gcal.get(Calendar.DAY_OF_MONTH);
			String todayStr = today.toString() + " (today)";
			gcal.set(Calendar.DAY_OF_MONTH, 1);
			int firstDay = gcal.get(Calendar.DAY_OF_WEEK);	//gets the day of week of the first day this month
			//1 means Sunday, 2 means Monday etc.
			
			ArrayList<Object[]> monthList = new ArrayList<>();
			Object[] weekAr = makeEmptyWeek();
	
			int daysInMonth = gcal.getActualMaximum(Calendar.DAY_OF_MONTH),		/*# of days this month	*/
				dayNum = firstDay-1,		/*counter object. A new week begins when this reaches 6.	*/
				day = 1;
			
			//first week
			while(dayNum<7)	{
				if(day!=today)
					weekAr[dayNum++] = day++;
				else	{
					if(!btnsPressed)	{				//only prints "today" if buttons haven't been pressed
						weekAr[dayNum++] = todayStr;	
						day++;
					}
					else
						weekAr[dayNum++] = day++;
				}
			}
			monthList.add(weekAr);
			
			//rest of the month
			while(day<=daysInMonth)	{
				weekAr = makeEmptyWeek();
				for(dayNum=0;dayNum<7;dayNum++)	{
					if(day<=daysInMonth)	{
						if(day!=today)
							weekAr[dayNum] = day++;
						else	{
							weekAr[dayNum] = todayStr;
							day++;
						}
					}
				}
	
				monthList.add(weekAr);
			}
	
			//convert ArrayList to Object[][]
			Object[][] monthLayout = new Object[monthList.size()][7];
			int idx = 0;
			for(Object[] week:monthList)	{
				monthLayout[idx++] = week;
			}
	
			return monthLayout;	
		}
			
	/*
	 * Part two: prepare the toolbar
	 */
	private JToolBar prepareToolBar()	{
    	
		JToolBar bar = new JToolBar("Navigation tool bar");
		bar.setFloatable(false);
		bar.setRollover(true);
		//bar.setPreferredSize(new Dimension(100,50));
		UpdateCalendar updater = new UpdateCalendar(table);
		
		JButton minusYear = new JButton("<<");
		minusYear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minusYear.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		minusYear.setToolTipText("previous year");
		minusYear.setActionCommand("<<");
		minusYear.addActionListener(updater);
		bar.add(minusYear);
		
		JButton minusMonth = new JButton("<");
		minusMonth.setCursor(new Cursor(Cursor.HAND_CURSOR));
		minusMonth.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		minusMonth.setToolTipText("previous month");
		minusMonth.setActionCommand("<");
		minusMonth.addActionListener(updater);
		bar.add(minusMonth);
		
		GregorianCalendar cal = new GregorianCalendar();
		Integer yr = cal.get(Calendar.YEAR), month = cal.get(Calendar.MONTH) + 1;
		String monthStr = yr.toString() + "-" + month.toString();
		timeLabel = new JLabel(monthStr);
		timeLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		bar.add(timeLabel);
		
		JButton plusMonth = new JButton(">");
		plusMonth.setCursor(new Cursor(Cursor.HAND_CURSOR));
		plusMonth.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		plusMonth.setToolTipText("next month");
		plusMonth.setActionCommand(">");
		plusMonth.addActionListener(updater);
		bar.add(plusMonth);
		
		JButton plusYear = new JButton(">>");
		plusYear.setCursor(new Cursor(Cursor.HAND_CURSOR));
		plusYear.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
		plusYear.setToolTipText("next year");
		plusYear.setActionCommand(">>");
		plusYear.addActionListener(updater);
		bar.add(plusYear);
		
		return bar;
	}
	
		/*
		 * Event handler for the buttons to change month/year
		 */
		class UpdateCalendar implements ActionListener	{
			JTable t;
			boolean extraRow = false;
			
			UpdateCalendar(JTable tCons)	{
				this.t = tCons;
			}
			
			void updateTable(DefaultTableModel mod, Object[][] newCells)	{
				for(int i=0;i<5;i++)	{
					for(int j=0;j<7;j++)	
						mod.setValueAt(newCells[i][j], i, j);
				}
				if(newCells.length>5)	{
					extraRow = true;
					mod.addRow(newCells[5]);
				}
				if(newCells.length==5 && extraRow)	{
					extraRow = false;
					mod.removeRow(5);
				}
				
				mod.fireTableDataChanged();
			}
			
			public void actionPerformed(ActionEvent e)	{
		        String command = e.getActionCommand();
		        
		        if(command.equals("<"))	{
		        	currentCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH)-1);
		        	Object[][] nextMonth = getMonthLayout(currentCal);
		        	updateTable(model, nextMonth);
		        }	else if(command.equals(">"))	{
		        	currentCal.set(Calendar.MONTH, currentCal.get(Calendar.MONTH)+1);
		        	Object[][] nextMonth = getMonthLayout(currentCal);
		        	updateTable(model, nextMonth);
		        }	else if(command.equals("<<"))	{
		        	currentCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR)-1);
		        	Object[][] nextMonth = getMonthLayout(currentCal);
		        	updateTable(model, nextMonth);
		        }	else if(command.equals(">>"))	{
		        	currentCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR)+1);
		        	Object[][] nextMonth = getMonthLayout(currentCal);
		        	updateTable(model, nextMonth);
		        }
		        
		        btnsPressed = true;
		        
	    		Integer yr = currentCal.get(Calendar.YEAR), month = currentCal.get(Calendar.MONTH) + 1; 				//needs to be updated with button presses too
	    		String monthStr = yr.toString() + "-" + month.toString();
	    		timeLabel.setText(monthStr);
			}
		}
	
	/*
	 * Part three: pack everything together
	 */
	private static void prepareGUI()	{
		CalendarMain main = new CalendarMain();
        JFrame frame = new JFrame("Swing Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(main);
        frame.pack();
        frame.setSize(frame.getPreferredSize());
        frame.setVisible(true);
	}
	
	public static void main(String[] args)	{
		SwingUtilities.invokeLater(new Runnable()  {
			public void run()	{
				prepareGUI();
			}
		});
	}
}