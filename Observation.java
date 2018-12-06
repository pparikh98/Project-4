	/**
	 * @author Pooja Parikh, my partner was Jacob Courtney
	 * @version Dec 2nd, 2018
	 * 
	 * This class holds a single observation from a single station
	 *  and determines whether or not that value is valid or not. 
	 * 
	 */
	public class Observation extends AbstractObservation
	{
	    // holds the value of this observation
	    private double value;

	    // holds the station id for this observation
	    private String stid;
	    
	    /**
	     *  constructor sets the value and the station id, and determines whether it is valid
	     * @param value
	     * @param stid
	     */
	    public Observation(double value, String stid)
	    {
	        // sets the value passed in by the constructor
	        this.setValue(value);

	        // sets the station id passed in by the constructor
	        this.stid = stid;
	    }

	    /**
	     *  default constructor sets the value to -999, and stid to mesonet
	     */
	    public Observation()
	    {
	        // sets the value passed in by the constructor
	        this.setValue(-999);

	        // sets the station id passed in by the constructor
	        stid = "Mesonet";

	    }

	    /**
	     *  gets the value the observation object is holding
	     * @return
	     */
	    public double getValue()
	    {
	        return value;
	    }

	    /**
	     *  sets the value of the observation
	     * @param value
	     */
	    public void setValue(double value)
	    {
	        this.value = value;
	        if (this.value > -70)
	        {
	            this.valid = true;
	        }
	    }

	    /**
	     *  checks if the value is valid
	     */
	    public boolean isValid()
	    {
	        return this.valid;
	    }

	    /**
	     *  gets id of the station
	     * @return
	     */
	    public String getStid()
	    {
	        return stid;
	    }

	    /**
	     *  sets id of the station
	     * @param stid
	     */
	    public void setStid(String stid)
	    {
	        this.stid = stid;
	    }
	}
