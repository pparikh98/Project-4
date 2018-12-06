import java.io.IOException;
import javax.swing.JFrame;

/**
 * 
 * @author Pooja Parikh, my partner was Jacob Courtney
 * @version December 2nd, 2018
 * 
 * Driver for project 4. This Determines which file to pass in and creates a MapData object,
 * parses the file and uses the toString method on the MapData object
 * 
 */

public class Driver 
{
    /**
     * @param args
     * @throws IOException
     */
    
    public static void main(String[] args) throws IOException
    {   
    	
        final int YEAR = 2018;
        final int MONTH = 8;
        final int DAY = 30;
        final int HOUR = 17;
        final int MINUTE = 45;
        final String directory = "data";
        MapData mapData1 = new MapData(YEAR, MONTH, DAY, HOUR, MINUTE, directory);
        mapData1.parseFile();
        mapData1.calculateAllStatistics();
        System.out.println(mapData1.getStatistics(StatsType.MAXIMUM,"TAIR").toString());
        
        
        MesonetFrame test = new MesonetFrame();
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        test.setSize(900,600);
        test.setVisible(true);
    }

}
