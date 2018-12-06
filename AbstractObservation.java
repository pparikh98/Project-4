/**
 * 
 * The AbstractObservation class is the abstarct version of the observations and is supposed to only be used to hold vailidity
 * of these observations all together. 
 * 
 * @author Pooja Parikh
 * @version December 2nd
 * 
 */
abstract class AbstractObservation {
	
	//Validity of the observations
	protected boolean valid = false;
	
	/**
	 * 
	 */
	public AbstractObservation()
	{
		//Empty constructor, does not do anything.
	}
	
	/**
	 * gets the value of validity
	 * @return
	 */
	public boolean isValid() 
	{
		return valid;
	}
}
