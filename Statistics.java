	import java.util.GregorianCalendar;
	import java.time.ZoneId;
	import java.time.ZonedDateTime;
	import java.util.Calendar;

	public class Statistics extends Observation implements DateTimeComparable
	{
	    // holds the format for Date and Time
	    protected static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

	    // holds the date and time in a GregorianCalendar object
	    GregorianCalendar utcDateTime = new GregorianCalendar();

	    // holds the date and time in a GregorianCalendar object
	    ZonedDateTime zdtDateTime;

	    // stores the number of reporting stations
	    int numberOfReportingStations;

	    // stores the type of stat
	    StatsType statType;

	    // holds the year value in an int
	    int year;

	    // holds the month in an int
	    int month;

	    // holds the day in an int
	    int day;

	    // holds the hour in an int
	    int hour;

	    // holds the minute in an int
	    int minute;

	    // holds the second in an int
	    int second;

	    /**
	     * constructor that imports with a GregorianCalendar object
	     * 
	     * @param value
	     * @param stid
	     * @param dateTime
	     * @param numberOfValidStations
	     * @param inStatType
	     */
	    public Statistics(double value, String stid, GregorianCalendar dateTime, int numberOfValidStations,
	            StatsType inStatType)
	    {
	        this.setValue(value);
	        this.setStid(stid);
	        this.utcDateTime = dateTime;
	        this.numberOfReportingStations = numberOfValidStations;
	        this.statType = inStatType;
	        this.year = this.utcDateTime.get(Calendar.YEAR);
	        this.month = this.utcDateTime.get(Calendar.MONTH);
	        this.day = this.utcDateTime.get(Calendar.DAY_OF_MONTH);
	        if (this.utcDateTime.get(Calendar.AM_PM) == Calendar.PM)
	        {
	            this.hour = this.utcDateTime.get(Calendar.HOUR);
	            this.hour += 12;
	        }
	        else
	        {
	            this.hour = this.utcDateTime.get(Calendar.HOUR);
	        }
	        this.minute = this.utcDateTime.get(Calendar.MINUTE);
	        this.second = this.utcDateTime.get(Calendar.SECOND);
	        this.zdtDateTime = ZonedDateTime.of(this.year, this.month, this.day, this.hour, this.minute, this.second, 0,
	                ZoneId.of("America/Chicago"));
	    }

	    /**
	     * constructor that imports with a ZonedDateTime object
	     * 
	     * @param value
	     * @param stid
	     * @param zdtDateTime
	     * @param numberOfValidStations
	     * @param inStatType
	     */
	    public Statistics(double value, String stid, ZonedDateTime zdtDateTime, int numberOfValidStations,
	            StatsType inStatType)
	    {
	        this.setValue(value);
	        this.setStid(stid);
	        this.zdtDateTime = zdtDateTime;
	        this.numberOfReportingStations = numberOfValidStations;
	        this.statType = inStatType;
	        this.year = this.zdtDateTime.getYear();
	        this.month = this.zdtDateTime.getMonthValue();
	        this.day = this.zdtDateTime.getDayOfMonth();
	        this.hour = this.zdtDateTime.getHour();
	        this.minute = this.zdtDateTime.getMinute();
	        this.second = this.zdtDateTime.getSecond();
	        this.utcDateTime = new GregorianCalendar(this.year, this.month, this.day, this.hour, this.minute, this.second);
	    }

	    /**
	     * returns a GregorianCalendar from a String
	     * 
	     * @param dateTimeStr
	     * @return
	     */
	    public GregorianCalendar createDateFromString(String dateTimeStr)
	    {
	        String[] splitString = dateTimeStr.split("'");
	        String[] date = splitString[0].split("-");
	        String[] time = splitString[2].split(":");
	        return new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),
	                Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
	    }

	    /**
	     * returns a String from a GregorianCalendar
	     * 
	     * @param calendar
	     * @return
	     */
	    public String createStringFromDate(GregorianCalendar calendar)
	    {
	        return String.format("%d-%02d-%02d'T'%02d:%02d:%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
	                calendar.get(Calendar.DAY_OF_MONTH), this.hour, calendar.get(Calendar.MINUTE),
	                calendar.get(Calendar.SECOND));
	    }

	    /**
	     * returns the number of reporting stations as an int
	     * 
	     * @return
	     */
	    public int getNumberOfReportingStation()
	    {
	        return this.numberOfReportingStations;
	    }

	    /**
	     * returns the utcDateTime of this object as a string
	     */
	    public String getUtcDateTimeString()
	    {
	        return String.format("%d-%02d-%02d'T'%02d:%02d:%02d", year, month, day, hour, minute, second);
	    }

	    /**
	     * overrides the toString method
	     */
	    @Override
	    public String toString()
	    {
	        return String.format("%s\nValue held by this statistic: %.1f\nStation ID: %s\nNumber of Valid Stations: %d\nStatType: %s"
	                , this.getUtcDateTimeString(), this.getValue(),this.getStid(),this.getNumberOfReportingStation(),this.statType.name().toLowerCase());
	    }

	    @Override
	    /**
	     * Checks to see if the passed in GregorianCalendar is newer than this
	     * Statistics object
	     */
	    public boolean newerThan(GregorianCalendar inDateTimeUTC)
	    {
	        return inDateTimeUTC.after(utcDateTime);
	    }

	    @Override
	    /**
	     * Checks to see if the passed in GregorianCalendar is older than this
	     * Statistics object
	     */
	    public boolean olderThan(GregorianCalendar inDateTimeUTC)
	    {
	        return inDateTimeUTC.before(utcDateTime);
	    }

	    @Override
	    /**
	     * Checks to see if the passed in GregorianCalendar is the same age as this
	     * Statistics object
	     */
	    public boolean sameAs(GregorianCalendar inDateTimeUTC)
	    {
	        return inDateTimeUTC.equals(utcDateTime);
	    }

	    /**
	     * returns true if this is newer than passed in date
	     * 
	     * @return boolean for if it is newer
	     */
	    @Override
	    public boolean newerThan(ZonedDateTime inDateTimeUTC)
	    {
	        return this.zdtDateTime.isAfter(inDateTimeUTC);
	    }

	    /**
	     * returns true if this is older than passed in date
	     * 
	     * @return boolean for if it is older
	     */
	    @Override
	    public boolean olderThan(ZonedDateTime inDateTimeUTC)
	    {
	        return this.zdtDateTime.isBefore(inDateTimeUTC);
	    }

	    /**
	     * returns true if this is the same age as the passed in date
	     * 
	     * @return true if they are the same
	     */
	    @Override
	    public boolean sameAs(ZonedDateTime inDateTimeUTC)
	    {
	        return this.zdtDateTime.isEqual(inDateTimeUTC);
	    }

	    /**
	     * creates a ZonedDateTime object from the passed in String
	     * 
	     * @param dateTimeStr
	     *            the date to be parsed
	     * @return a ZonedDateTime object with the specified values
	     */
	    public ZonedDateTime createZDateFromString(String dateTimeStr)
	    {
	        String[] splitString = dateTimeStr.split("'");
	        String[] date = splitString[0].split("-");
	        String[] time = splitString[2].split(":");
	        ZonedDateTime zdt = ZonedDateTime.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]),
	                Integer.parseInt(date[2]), Integer.parseInt(time[0]), Integer.parseInt(time[1]),
	                Integer.parseInt(time[2]), 0, ZoneId.of("Europe/Paris"));
	        return zdt;
	    }

	    /**
	     * creates a String from a ZonedDateTime object
	     * 
	     * @param calendar the calendar to be converted
	     * @return String using the format of DATE_TIME_FORMAT
	     */
	    public String createStringFromDate(ZonedDateTime calendar)
	    {
	        return String.format("%f-%2f-%2f'T'%2f:%2f:%2f", calendar.getYear(), calendar.getMonth(),
	                calendar.getDayOfMonth(), this.hour, calendar.getMinute(), calendar.getSecond());
	    }

}
