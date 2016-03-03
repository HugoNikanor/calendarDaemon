package other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimestampFormater {

	/**
	 * goes from 2016/jan/13 to 2016/01/13
	 */
	public static String dateEngToNum( String date ) throws ParseException {
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

	public static String dateNumToEng( String date ) {
		String[] monthNames = {
			"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"
		};

		String formatedDate = String.format( "%04d/%s/%02d",
			Integer.parseInt( date.substring(0, 4) ),
			monthNames[ Integer.parseInt( date.substring(5, 7) ) - 1 ],
			Integer.parseInt( date.substring(8, 10) ));

		return formatedDate;
	}

	public static String formatTime( String time ) {
		return time;
	}
}
