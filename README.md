# Swing-Calendar
A calendar app, developed using Swing GUI.

App contents:
1. Calendar
2. Toolbar
3. Layout manager

#### - Calendar

The calendar itself is displayed on a JTable, which is itself wrapped around a JScrollPane.
The calendar layout is determined using the *Calendar* interface and its *GregorianCalendar* subclass, which determines the current date, number of days in the month, and the day of week of the first day of the month. The layout is held in an 2-dimensional array of *Object*s (in order to hold null values) and fed to the JTable.

Aside: I intended for the current date to be displayed in bold and its cell to have a different border, but this turned out to be surprisingly difficult. JTable renders the entire table as a single component, so by default it cannot render any single cell differently from the rest. But this can be done by creating a custom table cell renderer. I spent some time attempting to do so, but to no avail. In the end I opted to change the cell content to display "(today)" instead (CustomCellRenderer.java is currently unused), but only if displaying the current month.


#### - Toolbar

The toolbar, a JToolBar component, contains 4 buttons and 1 label. The buttons would increment or decrement the current time by one month or one year, while the label displays the current year and month.

I wrote an action listener that updates the table to display the new month, which also detects how many weeks the month has and increases or decreases the number of rows  in the table accordingly. It also updates the label. 

A *GregorianCalendar* instance keeps track of what the current month is (after button presses). 


#### - Layout

The components are laid out using the *GridBagLayout* manager (In retrospect *Boxlayout* may have worked just as well while being easier to use, but oh well). This is the first time I used *GridBagLayout*, it's certainly unique in that it is set up using assignment statements rather than with methods.
