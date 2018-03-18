
import javax.swing.JOptionPane;

/**
 * 
 */

/**
 * This class will send a string to DataFetcher class and will receive a list of data.
 * That list will be printed
 * @author harmanRandhawa
 *
 */
public class DataFetcherTester implements Protocols{

	
	/**
	 * @param args
	 * TEST CASES 
	 * INPUT	OUTPUT
	 * ===============
	 * COPS		1
	 * DOCTORS	2
	 * FIREMEN	3
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String cmd = askUser();
		DataFetcher df = new DataFetcher();
		System.out.println(df.startProcessing(cmd));
		
	}
	/**
	 * This method asks user for input. 
	 * @return the input of user in string format
	 */
   public static String askUser( ){
		return JOptionPane.showInputDialog(null, "WHATS THE COMMAND " , " HELPP :) ", JOptionPane.QUESTION_MESSAGE);
	}

}
