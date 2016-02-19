package other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimestampFormater {

	public static String formatDate( String date ) throws ParseException {
		Date d = new SimpleDateFormat("MMM")
			.parse( date.substring(5, 8) );
		Calendar cal = Calendar.getInstance();
		cal.setTime( d );

		String formatedDate = String.format( "%04d/%02d/%02d",
			Integer.parseInt( date.substring(0, 4) ),
			cal.get(Calendar.MONTH) + 1,
			Integer.parseInt( date.substring(9, 11) ));

		return formatedDate;
	}

	public static String formatTime( String time ) {
		return time;
	}
}
