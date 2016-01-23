package other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTransformer {

	public static String relativeToAbsolute( String startTime, String relativeEndtime ) {

		// h (hour), m (minute)
		startTime = startTime.replace( ".", ":" );
		int sepIndex = startTime.indexOf( ':' );
		int hours;
		int minutes;
		hours = Integer.parseInt( startTime.substring( 0, sepIndex ) );
		minutes = Integer.parseInt( startTime.substring( sepIndex + 1 ) );

		// this can be easily be expanded to allow for more time intervals.
		Pattern pattern = Pattern.compile( "^\\+((?<hour>\\d+)h|)((?<minute>\\d+)m|)" );
		Matcher matches = pattern.matcher( relativeEndtime );
		if( matches.matches() ) {
			String h = matches.group( "hour" );
			hours   += h != null ? Integer.parseInt( h ) : 0;
			String m = matches.group( "minute" );
			minutes += m != null ? Integer.parseInt( m ) : 0;
		}
		return (hours + ":" + minutes);
	}
}
