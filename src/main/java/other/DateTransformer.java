package other;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTransformer {

	/**
	 * @param startTime
	 *            What the endTime is relative to, on the from 'hh:mm' or
	 *            'hh.mm'
	 * @param relativeEndTime
	 *            How long time the endtime is after the start time.
	 *            Input on the form +1h2m.
	 *            Notice that the '+' is required, and that the 
	 *            'h' &amp; 'm' are letters. 
	 *            Each number letter block is optional (1h &amp; 2m)
	 */
	public static String relativeToAbsoluteTime( String startTime, String relativeEndtime ) {

		startTime = startTime.replace( ".", ":" );
		int sepIndex = startTime.indexOf( ':' );

		int hours = Integer.parseInt( startTime.substring( 0, sepIndex ) );
		int minutes = Integer.parseInt( startTime.substring( sepIndex + 1 ) );

		// this can be easily be expanded to allow for more time intervals.
		Pattern pattern = Pattern.compile( "^\\+((?<hour>\\d+)h|)((?<minute>\\d+)m|)" );
		Matcher matches = pattern.matcher( relativeEndtime );
		if( matches.matches() ) {
			String h = matches.group( "hour" );
			hours   += h != null ? Integer.parseInt( h ) : 0;
			String m = matches.group( "minute" );
			minutes += m != null ? Integer.parseInt( m ) : 0;
		}
		return (String.format("%02d:%02d", hours, minutes));
	}

	/**
	 * can set years, months and days
	 * +1y2m3d
	 * 1 year, 2 months, 3 days
	 * @throws ParseException
	 */
	public static String relativeToAbsoluteDate(String startDate, String relativeEndDate) throws ParseException {

		Date date = new SimpleDateFormat("MMM")
			.parse( startDate.substring(5, 8) );
		Calendar cal = Calendar.getInstance();
		cal.setTime( date );
		int months = cal.get(Calendar.MONTH);

		// yyyy / MMM / dd
		int years  = Integer.parseInt( startDate.substring(0, 4) );
		//int months = Integer.parseInt( startDate.substring(5, 8) );
		int days   = Integer.parseInt( startDate.substring(9, 11));

		Pattern pattern = Pattern.compile( "^\\+((?<years>\\d+)y|)((?<months>\\d+)m|)((?<days>\\d+)d|)" );
		Matcher matches = pattern.matcher( relativeEndDate );
		if( matches.matches() ) {
			String y = matches.group( "years" );
			years   += y != null ? Integer.parseInt( y ) : 0;
			String m = matches.group( "months" );
			months  += m != null ? Integer.parseInt( m ) : 0;
			String d = matches.group( "days" );
			days    += d != null ? Integer.parseInt( d ) : 0;
		}

		// moths is increased to get it back to human format
		return String.format( "%04d/%02d/%02d", years, ++months, days );
	}
}
