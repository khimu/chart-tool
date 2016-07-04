import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import junit.framework.TestCase;



public class MapTest extends TestCase {

	public void testDate(){
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		DateTime releasedate = parser2.parseDateTime("2012-07-26T09:26:43-0700");
		
		Calendar rdCal = Calendar.getInstance();
		rdCal.setTimeInMillis(releasedate.getMillis());
		rdCal.add(Calendar.DAY_OF_YEAR, 5);
		
		// was this app released within the last 5 days
		if(rdCal.getTimeInMillis() >= System.currentTimeMillis()){
			System.out.println("true");
		}else{
			System.out.println("false");
		}
	
	}
}
