import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * The MesonetFrame class extends the JFrame and stores everything that is needed
 * 
 * @author Pooja Parikh, my partner was Jacob Courtney
 * @version Tuesday, Dec 1st
 * 
 */
	public class MesonetFrame extends JFrame
	{
	    private static final long serialVersionUID = 1L;
	    private JPanel menuBar;
	    private ParameterPanel paramPanel;
	    private StatisticsPanel statPanel;
	    private TablePanel table;
	    private JPanel paramAndStatPanel;

	    private ButtonGroup stats = new ButtonGroup();

	    private JRadioButton min = new JRadioButton("MINIMUM");
	    private JRadioButton average = new JRadioButton("AVERAGE");
	    private JRadioButton max = new JRadioButton("MAXIMUM");

	    private File chosenFile;

	    private String filePath;
	    private String fileName;

	    private MapData mapData;

	    private boolean isTAIRSel;
	    private boolean isTA9MSel;
	    private boolean isSRADSel;
	    private boolean isWSPDSel;
	    private boolean isPRESSel;

	    private String[] columns = { "Station", "Parameter", "Statistic", "Value", "Reporting Stations", "Date" };
	    private String[][] data = {};

	    private DefaultTableModel selectedStatTable = new DefaultTableModel(data, columns);

	    /**
	     * Constructor
	     */
	    @SuppressWarnings("rawtypes")
		public MesonetFrame()
	    {
	        super("Oklahoma Mesonet - Statistics Calculator");
	        setLayout(new BorderLayout());

	        // This part creates the menu bar that will show up in the "north" section of panel
	        menuBar = new JPanel();
	        menuBar.setLayout(new GridLayout(2, 1));
	        menuBar.add(new FileMenuBar());
	        menuBar.add(new JLabel("Mesonet - We don't set records, we report them!", JLabel.CENTER));
	        menuBar.setBackground(Color.BLUE);

	        // Creates the parameter button chooser that will appear in the "west" section of panel
	        paramPanel = new ParameterPanel();

	        // Creates the Statistics radio button and button group that will appear in the "center" section of panel
	        statPanel = new StatisticsPanel();

	        // creating the jtable and jscrollpane
	        table = new TablePanel();

	        // JPanel that holds the calculate and exit buttons
	        JPanel calcAndExit = new JPanel();
	        calcAndExit.setLayout(new FlowLayout(FlowLayout.CENTER));
	        JButton calculate = new JButton("Calculate");

	        //Action listener for the calculate button.
	        calculate.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {
	                if (chosenFile != null)
	                {
	                    if (min.isSelected())
	                    {
	                        if (isTAIRSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MINIMUM).get("TAIR").getStid(), "TAIR",
	                                    "MINIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("TAIR").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("TAIR")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MINIMUM).get("TAIR").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isTA9MSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MINIMUM).get("TA9M").getStid(), "TA9M",
	                                    "MINIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("TA9M").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("TA9M")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MINIMUM).get("TA9M").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isSRADSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MINIMUM).get("SRAD").getStid(), "SRAD",
	                                    "MINIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("SRAD").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("SRAD")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MINIMUM).get("SRAD").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isWSPDSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MINIMUM).get("WSPD").getStid(), "WSPD",
	                                    "MINIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("WSPD").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("WSPD")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MINIMUM).get("WSPD").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isPRESSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MINIMUM).get("PRES").getStid(), "PRES",
	                                    "MINIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("PRES").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MINIMUM).get("PRES")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MINIMUM).get("PRES").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                    }
	                    else if (average.isSelected())
	                    {
	                        if (isTAIRSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.AVERAGE).get("TAIR").getStid(), "TAIR",
	                                    "AVERAGE",
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("TAIR").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("TAIR")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.AVERAGE).get("TAIR").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isTA9MSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.AVERAGE).get("TA9M").getStid(), "TA9M",
	                                    "AVERAGE",
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("TA9M").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("TA9M")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.AVERAGE).get("TA9M").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isSRADSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.AVERAGE).get("SRAD").getStid(), "SRAD",
	                                    "AVERAGE",
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("SRAD").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("SRAD")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.AVERAGE).get("SRAD").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isWSPDSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.AVERAGE).get("WSPD").getStid(), "WSPD",
	                                    "AVERAGE",
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("WSPD").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("WSPD")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.AVERAGE).get("WSPD").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isPRESSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.AVERAGE).get("PRES").getStid(), "PRES",
	                                    "AVERAGE",
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("PRES").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.AVERAGE).get("PRES")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.AVERAGE).get("PRES").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                    }
	                    else
	                    {
	                        if (isTAIRSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MAXIMUM).get("TAIR").getStid(), "TAIR",
	                                    "MAXIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("TAIR").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("TAIR")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MAXIMUM).get("TAIR").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isTA9MSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MAXIMUM).get("TA9M").getStid(), "TA9M",
	                                    "MAXIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("TA9M").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("TA9M")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MAXIMUM).get("TA9M").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isSRADSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MAXIMUM).get("SRAD").getStid(), "SRAD",
	                                    "MAXIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("SRAD").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("SRAD")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MAXIMUM).get("SRAD").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isWSPDSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MAXIMUM).get("WSPD").getStid(), "WSPD",
	                                    "MAXIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("WSPD").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("WSPD")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MAXIMUM).get("WSPD").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                        if (isPRESSel)
	                        {
	                            String[] newRow = { mapData.statistics.get(StatsType.MAXIMUM).get("PRES").getStid(), "PRES",
	                                    "MAXIMUM",
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("PRES").getValue()),
	                                    String.valueOf(mapData.statistics.get(StatsType.MAXIMUM).get("PRES")
	                                            .getNumberOfReportingStation()),
	                                    mapData.statistics.get(StatsType.MAXIMUM).get("PRES").getUtcDateTimeString() };
	                            selectedStatTable.addRow(newRow);
	                        }
	                    }
	                }
	            }

	        });
	        calcAndExit.add(calculate);

	        //This part creates the exit button
	        JButton exitButton = new JButton("Exit");
	        exitButton.addActionListener(new ActionListener()
	        {
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {
	                System.exit(0);
	            }
	        });

	        calcAndExit.add(exitButton);

	        //Here we are combining the parameter and stat panel
	        paramAndStatPanel = new JPanel();
	        paramAndStatPanel.setLayout(new GridLayout(1, 2));
	        paramAndStatPanel.add(paramPanel);
	        paramAndStatPanel.add(statPanel);

	        //Adds the components to the frame
	        add(menuBar, BorderLayout.NORTH);
	        add(paramAndStatPanel, BorderLayout.WEST);
	        add(table, BorderLayout.CENTER);
	        add(calcAndExit, BorderLayout.SOUTH);
	    }

	    /**
	     * FileMenuBar class adds the File menu, and adds Open File and exit to it. 
	     * @param <chosenFile1>
	     * @param <chosenFile1>
	     */
	    @SuppressWarnings("hiding")
		public class FileMenuBar<chosenFile, chosenFile1> extends JMenuBar
	    {
	        private static final long serialVersionUID = 1L;

	        /**
	         * Constructor adds the File button
	         */
	        public FileMenuBar()
	        {
	            //the file menu
	            JMenu file = new JMenu("File");

	            JMenuItem openFileMenuItem = new JMenuItem("Open file...");
	            openFileMenuItem.addActionListener(new ActionListener()
	            {
	                @Override
	                public void actionPerformed(ActionEvent e)
	                {
	                    //This creates a fileChooser object
	                    JFileChooser fileChooser = new JFileChooser();
	                    fileChooser.showOpenDialog(null);
	                    chosenFile = fileChooser.getSelectedFile();
	                    
	                    //Command to make sure user did not click cancel
	                    if (chosenFile != null)
	                    {
	                        filePath = chosenFile.getPath();
	                        fileName = chosenFile.getName();
	                        mapData = new MapData (fileName, filePath);
	                        
	                        //parses the file and calculates the stats
	                        try
	                        {
	                            mapData.parseFile();
	                            mapData.calculateAllStatistics();
	                        }
	                        catch (IOException e1)
	                        {
	                            e1.printStackTrace();
	                        }
	                    }

	                }

	            });

	            //This creates the exit menu item and adds functionality
	            JMenuItem exitMenuItem = new JMenuItem("Exit");
	            exitMenuItem.addActionListener(new ActionListener()
	            {
	                @Override
	                public void actionPerformed(ActionEvent e)
	                {
	                    System.exit(0);
	                }

	            });

	            //adds items to the menu
	            file.add(openFileMenuItem);
	            file.add(exitMenuItem);

	            add(file);
	        }
	    }

	    /**
	     * ParameterPanel Class adds the radio buttons with a TitledBorder
	     */
	    public class ParameterPanel extends JPanel
	    {
	        private static final long serialVersionUID = 1L;

	        //Constructor adds the border and the check boxes. The check boxes have listeners attached to see
	        //if they are checked
	        public ParameterPanel()
	        {
	            setLayout(new GridLayout(5, 1));
	            JCheckBox tair = new JCheckBox("TAIR");
	            tair.addActionListener(new ActionListener()
	            {

	                @Override
	                public void actionPerformed(ActionEvent e)
	                {
	                    isTAIRSel = tair.isSelected();
	                }

	            });
	            JCheckBox ta9m = new JCheckBox("TA9M");
	            ta9m.addActionListener(new ActionListener()
	            {

	                @Override
	                public void actionPerformed(ActionEvent e)
	                {
	                    isTA9MSel = ta9m.isSelected();
	                }

	            });
	            JCheckBox srad = new JCheckBox("SRAD");
	            srad.addActionListener(new ActionListener()
	            {

	                @Override
	                public void actionPerformed(ActionEvent e)
	                {
	                    isSRADSel = srad.isSelected();
	                }

	            });
	            JCheckBox wspd = new JCheckBox("WSPD");
	            wspd.addActionListener(new ActionListener()
	            {

	                @Override
	                public void actionPerformed(ActionEvent e)
	                {
	                    isWSPDSel = wspd.isSelected();
	                }

	            });
	            JCheckBox pres = new JCheckBox("PRES");
	            pres.addActionListener(new ActionListener()
	            {

	                @Override
	                public void actionPerformed(ActionEvent e)
	                {
	                    isPRESSel = pres.isSelected();
	                }

	            });

	            //Adds buttons to the panel
	            add(tair);
	            add(ta9m);
	            add(srad);
	            add(wspd);
	            add(pres);

	            //This sets the border and background
	            setBorder(new TitledBorder("Parameter"));
	            setBackground(Color.BLUE);
	        }
	    }

	    /**
	     * StatisticsPanel class adds the radio buttons to select which statistics you want
	     */
	    public class StatisticsPanel extends JPanel
	    {
	        private static final long serialVersionUID = 1L;

	        /**
	         * Constructor adds the TitledBorder and the min average and max button
	         */
	        public StatisticsPanel()
	        {
	            setBorder(new TitledBorder("Statistics"));
	            setBackground(Color.BLUE);
	            setLayout(new GridLayout(3, 1));

	            stats.add(min);
	            stats.add(average);
	            stats.add(max);

	            add(min);
	            add(average);
	            add(max);
	        }
	    }

	    /**
	     * TabelPanel
	     */
	    public class TablePanel extends JPanel
	    {
	        private static final long serialVersionUID = 1L;
	        /**
	         * The constructor for tablePanel sets the layout, creates both array and
	         * creates a dataTable and scrollPane. 
	         * The data table uses the selectedStatsTable TableModel
	         */
	        public TablePanel()
	        {
	            setLayout(new BorderLayout());
	            JTable dataTable = new JTable(selectedStatTable);
	            JScrollPane scrollPane = new JScrollPane(dataTable);
	            add(scrollPane);
	        }

	        public TablePanel(String[][] data)
	        {
	            setLayout(new BorderLayout());
	            String[] columns = {"Station", "Parameter", "Statistics", "Value", "Reporting Stations", "Date"};
	            JTable dataTable = new JTable(data, columns);
	            JScrollPane scrollPane = new JScrollPane(dataTable);
	            add(scrollPane);
	        }
	    }
}