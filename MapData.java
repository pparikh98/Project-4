import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * 
 * @author Pooja Parikh
 * @version December 3rd, 2018
 * 
 * Hold various Observation[] that contain all of the weather data for a given time and date,
 * and also includes methods that parse that information in form a mdf file, as well as a few other simple computations.
 * 
 *   NOTE: I got help from my partner Jacob Courtney on this class
 *
 */
public class MapData {
	
	/*postion of stid in the mdf file */
	private int stidPosition = 0;
	
	/* holds the String "Mesonet" for avgs, maxes, mins and totals */
	private static final String MESONET = "Mesonet";
	
	/* holds the full file name */
	private String fileName;
	
	/* the year the data file was recorded */
	private int year;
	
	/* the month the data file was recorded */
	private int month;
	
	/* the day the data file was recorded */
    private int day;

    /* the hour the data file was recorded */
    private int hour;

    /* the minute the data file was recorded */
    private int minute;
    
    /* the directory the data file is in. probably "data" */
    private String directory;
    
    /* the total number of stations including invalid stations */
    private int numberOfStations;
    
    /* the ArrayList<Observation> that holds the Solar Radiation data from each station */
    public ArrayList<Observation> sradData = new ArrayList<Observation>();

    /*
     * the ArrayList<Observation> that holds the temperature 1.5m above the ground from
     * each station
     */
    public ArrayList<Observation> tairData = new ArrayList<Observation>();

    /*
     * the ArrayList<Observation> that holds the temperature 9 meters above the ground from
     * each station
     */
    public ArrayList<Observation> ta9mData = new ArrayList<Observation>();
    
    /*
     * The full data catalog. This is a hashmap of ArrayList<Observation>, meaning it is a two dimensional
     * catalog. The String key is the parameter, and the associated ArrayList is all of the data for
     * that parameter. 
     */
    HashMap<String,ArrayList<Observation>> dataCatalog = new HashMap<String,ArrayList<Observation>>();
    
    /*
     * This is the EnumMap that holds all of the statistics (maxes, mins, totals and averages). The
     * StatsType is the Type (Average, Max, Min or Total) and the associated TreeMap<String,Statistics>
     * holds the parameter (in the String key) and the statistics data as a statistics object
     */
     EnumMap<StatsType,TreeMap<String,Statistics>> statistics = new EnumMap<StatsType,TreeMap<String,Statistics>>(StatsType.class);
    
    /*
     * Holds all of the positions of the parameters in a tree map. Must run parseParamHeaders to fill
     */
     TreeMap<String,Integer> paramPositions = new TreeMap<String,Integer>();

    /* instantiating tairMin */
    Statistics tairMin;

    /* instantiating tairMax */
    Statistics tairMax;

    /* instantiating tairAverage */
    Statistics tairAverage;

     /* instantiating ta9mMin */
    Statistics ta9mMin;

     /* instantiating ta9mMax */
    Statistics ta9mMax;

    /* instantiating ta9mAverage */
    Statistics ta9mAverage;

    /* instantiating sradMin */
    Statistics sradMin;

    /* instantiating sradMax */
    Statistics sradMax;

    /* instantiating sradAverage */
    Statistics sradAverage;

    /* instantiating sradTotal */
    Statistics sradTotal;

    /**
     * Gets the year, month, day, hour, minute, and directory from the files name
     * and sets the appropriate variables
     */
    public MapData(int year, int month, int day, int hour, int minute, String directory)
    {
        // sets the year passed in by the constructor
        this.year = year;

        // sets the month passed in by the constructor
        this.month = month;

        // sets the day passed in by the constructor
        this.day = day;

        // sets the hour passed in by the constructor
        this.hour = hour;

        // sets the minute passed in by the constructor
        this.minute = minute;

        // sets the directory passed in by the constructor
        this.directory = directory;
    }

    public MapData(String fileName2, String filePath) {
		// TODO Auto-generated constructor stub
	}

	public void MapData1(String fileName2, String filePath) {
		// TODO Auto-generated constructor stub
	}

	/**
     * parses the different parts of the fileName into a single String
     * NOTE: I should update and use String.format, but it works fine for now
     * @return
     */
    public String createFileName()
    {
        String fullFileName = directory + "/";

        // adding the year
        fullFileName += year;

        // adding the month and padding with 0's if necessary
        if (month < 10)
        {
            fullFileName += ("0" + month);
        }
        else
        {
            fullFileName += month;
        }

        // adding the day and padding with 0's if necessary
        if (day < 10)
        {
            fullFileName += ("0" + day);
        }
        else
        {
            fullFileName += day;
        }

        // adding the hour and padding with 0's if necessary
        if (hour < 10)
        {
            fullFileName += ("0" + hour);
        }
        else
        {
            fullFileName += hour;
        }

        // adding the minute and padding with 0's if necessary
        if (minute < 10)
        {
            fullFileName += ("0" + minute);
        }
        else
        {
            fullFileName += minute;
        }

        // appending the .mdf extention
        fullFileName += ".mdf";
        
        //setting the fileName variable
        fileName = fullFileName;
        
        // returning the full file name
        return fullFileName;
    }
    /**
     * 
     * @param type
     * @param paramID
     * @return
     */
    public Statistics getStatistics(StatsType type,String paramID)
    {
        return statistics.get(type).get(paramID);
    }

    /**
     * 
     */
    public void calculateAllStatistics() throws IOException
    {
        this.calculateStatistics();
    }
    
    /**
     * calculates all statistics at once. takes in an arraylist and the parameter to be calculated 
     * @param inData
     * @param paramId
     */
    public void calculateStatistics() throws IOException
    {
        
        
        //getting the date and time from the date and time of the MapData object as a GregorianCalendar
        GregorianCalendar dateTimeForStatistics = new GregorianCalendar(this.year,this.month,this.day,this.hour,this.minute);
        
        //Instantiating the TreeMaps so they wont be null
        statistics.put(StatsType.AVERAGE, new TreeMap<String, Statistics>());
        statistics.put(StatsType.MINIMUM, new TreeMap<String, Statistics>());
        statistics.put(StatsType.MAXIMUM, new TreeMap<String, Statistics>());
        statistics.put(StatsType.TOTAL, new TreeMap<String, Statistics>());
        
        //WORKING ON AVERAGES FIRST
        //looping through
        for (int i = 0; i < paramPositions.size(); ++i)
        {
            //keeping a total count to divide at the end
            double total = 0;
            
            //holds the current param as a String
            String currentParam = this.getValueOf(i);
            
            //the number of invalid variables
            int numberOfInvalidVariables = 0;
            
            //looping through to find the numberOfInvalid
            for (int k = 0; k < this.dataCatalog.get(currentParam).size(); ++k)
            {
                if (!this.dataCatalog.get(currentParam).get(k).isValid())
                {
                    numberOfInvalidVariables++;
                }
            }
            
            //looping through and adding them all up
            for (int j = 0; j < this.dataCatalog.get(currentParam).size(); ++j)
            {
                if (this.dataCatalog.get(currentParam).get(j).isValid())
                {
                    total += this.dataCatalog.get(currentParam).get(j).getValue();
                }
            }
            
            total /= (this.numberOfStations - numberOfInvalidVariables);
            
            this.statistics.get(StatsType.AVERAGE).put
            (currentParam, new Statistics(total,
                    MESONET,
                    dateTimeForStatistics,
                    this.numberOfStations - numberOfInvalidVariables,
                    StatsType.AVERAGE));
        }
        
        //WORKING ON TOTALS
        for (int i = 0; i < paramPositions.size(); ++i)
        {
            //keeping a total count
            double total = 0;
            
            //holds the current param as a String
            String currentParam = this.getValueOf(i);
            
            //the number of invalid variables
            int numberOfInvalidVariables = 0;
            
            //looping through to find the numberOfInvalidVariables
            for (int k = 0; k < this.dataCatalog.get(currentParam).size(); ++k)
            {
                if (!this.dataCatalog.get(currentParam).get(k).isValid())
                {
                    numberOfInvalidVariables++;
                }
            }
            
            //looping through and adding them all up
            for (int j = 0; j < this.dataCatalog.get(currentParam).size(); ++j)
            {
                if (this.dataCatalog.get(currentParam).get(j).isValid())
                {
                    total += this.dataCatalog.get(currentParam).get(j).getValue();
                    
                }
            }
            
            this.statistics.get(StatsType.TOTAL).put
            (currentParam, new Statistics(total,
                    MESONET,
                    dateTimeForStatistics,
                    this.numberOfStations - numberOfInvalidVariables,
                    StatsType.TOTAL));
            
        }
        
        //WORKING ON MINIMUMS
        for (int i = 0; i < paramPositions.size(); ++i)
        {
            //index of the maximum value
            int minIndex = 0;
            
            //holds the current param as a String
            String currentParam = this.getValueOf(i);
            
            //the number of invalid variables
            int numberOfInvalidVariables = 0;
            
            //looping through to find the numberOfInvalid
            for (int k = 0; k < this.dataCatalog.get(currentParam).size(); ++k)
            {
                if (!this.dataCatalog.get(currentParam).get(k).isValid())
                {
                    numberOfInvalidVariables++;
                }
            }
            
            //looping through and finding the index
            for (int j = 0; j < this.dataCatalog.get(currentParam).size(); ++j)
            {
                for (int x = 0; x < this.dataCatalog.get(currentParam).size(); ++x)
                {
                    //comparing the current value to the current maximum and checking for validity
                    if (this.dataCatalog.get(currentParam).get(x).getValue() < this.dataCatalog.get(currentParam).get(minIndex).getValue()
                            && this.dataCatalog.get(currentParam).get(x).isValid())
                    {
                        minIndex = x;
                    }
                }
                //adding the Statistics object to statistics.get(StatsType.MAXIMUM)
                this.statistics.get(StatsType.MINIMUM).put
                (currentParam, new Statistics(this.dataCatalog.get(currentParam).get(minIndex).getValue(),
                        this.dataCatalog.get(currentParam).get(minIndex).getStid(),
                        dateTimeForStatistics,
                        this.numberOfStations - numberOfInvalidVariables,
                        StatsType.MINIMUM));
                
            }
            
        }
        
        //WORKING ON MAXIMUMS
        for (int i = 0; i < paramPositions.size(); ++i)
        {
            //index of the maximum value
            int maxIndex = 0;
            
            //holds the current param as a String
            String currentParam = this.getValueOf(i);
            
            //the number of invalid variables
            int numberOfInvalidVariables = 0;
            
            //looping through to find the numberOfInvalid
            for (int k = 0; k < this.dataCatalog.get(currentParam).size(); ++k)
            {
                if (!this.dataCatalog.get(currentParam).get(k).isValid())
                {
                    numberOfInvalidVariables++;
                }
            }
            
            //looping through and finding the index
            for (int j = 0; j < this.dataCatalog.get(currentParam).size(); ++j)
            {
                for (int x = 0; x < this.dataCatalog.get(currentParam).size(); ++x)
                {
                    //comparing the current value to the current maximum and checking for validity
                    if (this.dataCatalog.get(currentParam).get(x).getValue() > this.dataCatalog.get(currentParam).get(maxIndex).getValue()
                            && this.dataCatalog.get(currentParam).get(x).isValid())
                    {
                        maxIndex = x;
                    }
                }
                //adding the Statistics object to statistics.get(StatsType.MAXIMUM)
                this.statistics.get(StatsType.MAXIMUM).put
                (currentParam, new Statistics(this.dataCatalog.get(currentParam).get(maxIndex).getValue(),
                        this.dataCatalog.get(currentParam).get(maxIndex).getStid(),
                        dateTimeForStatistics,
                        this.numberOfStations - numberOfInvalidVariables,
                        StatsType.MAXIMUM));
                
            }
            
        }
        
    }
    
    /**
     * gets the key for a certain "index" in the paramPositions
     * @param the 
     */
    public String getValueOf(int inParamIndex)
    {
        String returnParam = "";

        //Putting the paramPositions into set form to make it easier to loop through
        Set<Entry<String,Integer>> paramPositionsSet = paramPositions.entrySet();
        
        //finding the param
        for (Entry<String,Integer> entry : paramPositionsSet)
        {
            if (entry.getValue() == inParamIndex)
            {
                returnParam = new String(entry.getKey());
            }
            
        }
        
        return returnParam;
        
    }
    
    /**
     * Parses the data in the file into the appropriate arrays
     */
    public void parseFile() throws IOException
    {
        // creating the bufferedReader
        BufferedReader br = null;
        this.createFileName();

        // more friendly fileNotFound error code
        try
        {
            br = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException fnfException)
        {
            System.out.println(fnfException.getMessage());
            System.exit(0);
        }

        // the line of data read from the file
        String strg;

        // the current station id
        String stid = "";

        // the current data
        String data;

        // throwing away the headers
        strg = br.readLine();
        strg = br.readLine();
        
        //using this line to find the parameter positions
        strg = br.readLine();
        String parameters = strg;
        parseParamHeader(parameters);
        
        //Putting the paramPositions into set form to make it easier to loop through
        Set<Entry<String,Integer>> paramPositionsSet = paramPositions.entrySet();
        
        //this is the line we will start on 
        strg = br.readLine();
        
        //creating an index to keep track of the total number of stations. starting at 1
        int numStations = 0;

        // looping through the array
        while (strg != null)
        {
            //FINDING THE STATION ID
            
            // instantiating a scanner to go through the tokens
            Scanner sc = new Scanner(strg);
            
            //read first useful token into a temporary variable
            data = sc.next();
            
            //looping through the tokens to find the stid
            int index1 = 0;
            while (sc.hasNext())
            {
                if (index1 == this.stidPosition)
                {
                    stid = data;
                    ++index1;
                    data = sc.next();
                }
                else
                {
                    ++index1;
                    data = sc.next();
                }
            }
            sc.close();
            
            //new scanner for all of the other params
            Scanner sc2 = new Scanner(strg);
            data = sc2.next();
            
            //index variable for this while loop
            int i = 0;
            while (sc2.hasNext())
            {
                String param = "";
                
                //finding which param we are working on
                for (Entry<String,Integer> entry : paramPositionsSet)
                {
                    if (entry.getValue() == i && !entry.getKey().equals("STID"))
                    {
                        param = entry.getKey();
                        dataCatalog.get(param).add(new Observation(Double.parseDouble(data),stid));
                    }
                }
                
                //incrementing the index
                ++i;
                data = sc2.next();
            }
            
            
            // all done parsing this line! closing up the scanner, moving to the next line, and
            // incrementing the total number of stations
            sc2.close();
            strg = br.readLine();
            numStations++;
        }

        // closing the bufferedReader
        br.close();
        
        //setting the class variable numberOfStations
        this.numberOfStations = numStations;
    }
    
    /**
     * prepares the dataCatalog to avoid nullPointers
     */
    public void prepareDataCatalog()
    {
        //I have not found a use for this yet
    }
    
    /**
     * finds the positions of stid, tair, srad and ta9m
     * @param inParamStr
     */
    private void parseParamHeader(String inParamStr)
    {
        //creating a scanner and a temporary String value
        Scanner sc = new Scanner(inParamStr);
        String token;
        
        //initializing the first token and creating an index to keep track of where it is
        token = sc.next();
        int index = 0;
        
        //looping through and comparing. sets when found
        while (sc.hasNext())
        {
            paramPositions.put(token, index);
            dataCatalog.put(token, new ArrayList<Observation>());
            //incrementing the index and moving to the next token
            token = sc.next();
            if (token == "STID")
            {
                this.stidPosition = index;
            }
            index++;
        }
        
        //closing up the scanner
        sc.close();
    }

    /**
     * gets the year this mapData objects info came from
     * @return
     */
    public int getYear()
    {
        return year;
    }

    /**
     *  gets the month this mapData objects info came from
     * @return
     */
    public int getMonth()
    {
        return month;
    }


    /**
     *  gets the day this mapData objects info came from
     * @return
     */
    public int getDay()
    {
        return day;
    }


    /**
     *  gets the hour this mapData objects info came from
     * @return
     */
    public int getHour()
    {
        return hour;
    }

    /**
     *  gets the minute this mapData objects info came from
     * @return
     */
    public int getMinute()
    {
        return minute;
    }

    /**
     *  gets the directory this mapData objects info came from
     * @return
     */
    public String getDirectory()
    {
        return directory;
    }
    
    /**
     * Overrides the toString method
     * I should rewrite and use String.format for padding with 0's, but it works fine for now
     */
    @Override
    public String toString()
    {
        // creating a string that we can concatenate everything on to
        String returnString = "";

        // beginning adding data to returnString
        returnString += "========================================================\n";
        returnString += "=== " + year + "-";

        // padding with zeros if needed
        if (month < 10)
        {
            returnString += "0" + month + "-";
        }
        else
        {
            returnString += month + "-";
        }

        // padding with zeros if needed
        if (day < 10)
        {
            returnString += "0" + day + " ";
        }
        else
        {
            returnString += day + " ";
        }

        // padding with zeros if needed
        if (hour < 10)
        {
            returnString += "0" + hour + ":";
        }
        else
        {
            returnString += hour + ":";
        }

        // padding with zeros if needed
        if (minute < 10)
        {
            returnString += "0" + minute + " ===\n";
        }
        else
        {
            returnString += minute + " ===\n";
        }

        // adding the rest of the data on to returnString
        returnString += String.format(
        "========================================================\n" +
        "Maximum Air Temperature[1.5m] = %.1f C at %s\n" +
        "Minimum Air Temperature[1.5m] = %.1f C at %s\n" +
        "Average Air Temperature[1.5m] = %.1f C at %s\n" +
        "========================================================\n" +
        "========================================================\n" +
        "Maximum Air Temperature[9m] = %.1f C at %s\n" +
        "Minimum Air Temperature[9m] = %.1f C at %s\n" +
        "Average Air Temperature[9m] = %.1f C at %s\n" +
        "========================================================\n" +
        "========================================================\n" +
        "Maximum Solar Radiation[1.5m] = %.1f W/M^2 at %s\n" +
        "Minimum Solar Radiation[1.5m] = %.1f W/M^2 at %s\n" +
        "Average Solar Radiation[1.5m] = %.1f W/M^2 at %s\n" +
        "========================================================\n"
        ,tairMax.getValue(),tairMax.getStid(),tairMin.getValue(),tairMin.getStid(),tairAverage.getValue(),tairAverage.getStid()
        ,ta9mMax.getValue(),ta9mMax.getStid(),ta9mMin.getValue(),ta9mMin.getStid(),ta9mAverage.getValue(),ta9mAverage.getStid()
        ,sradMax.getValue(),sradMax.getStid(),sradMin.getValue(),sradMin.getStid(),sradAverage.getValue(),sradAverage.getStid()
        );
        return returnString;
    }
 
}
