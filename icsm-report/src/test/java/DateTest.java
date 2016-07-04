import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;



public class DateTest extends TestCase {

	public void testDate(){
		String releasedate = "2009-02-18T13:26:15-07:00";
		
		DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		String jtdate = "2010-01-01T12:00:00+01:00";
		System.out.println(parser2.parseDateTime(releasedate));
		
		DateTime time = parser2.parseDateTime(releasedate);
		System.out.println(time.toDate().toString());
		System.out.println(time.toGregorianCalendar().getTime().toString());
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");		
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		
		
		
		
		try {
			Date newdate = format.parse(time.toDate().toString());
			System.out.println(newdate.toString());
			System.out.println("releasedate " +sdf.format(newdate));
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("unable to parse release date");
			System.out.println("releasedate " +releasedate);
		}

	
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

		Date today = new Date(); 

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_YEAR, 0 - 4);
		
		System.out.println("this guy" + sdf2.format(cal.getTime()));
		
		String date1 = "2012-07-19T12:00:01+0000";
		

	
	}
}
