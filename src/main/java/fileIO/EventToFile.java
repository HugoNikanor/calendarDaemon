package fileIO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import other.TimestampFormater;

public class EventToFile {
	public static void write( Event event ) {
		//Parser file = new Parser(); 
		DateTime startDateTime = event.getStart().getDateTime();

		String startDate = "";
		String startTime = "";
		String timeZone = "";

		// 2016-02-29T17:00:00.000+01:00
		Pattern startPattern = Pattern.compile(
				        "(?<date>\\d{4}-\\d{2}-\\d{2})T"
				.concat("(?<time>\\d{2}:\\d{2}).*")
				.concat("(?<timezone>[+-]\\d{2}:\\d{2})"));
		Matcher startMatcher = startPattern.matcher( startDateTime.toString() );
		if( startMatcher.matches() ) {
			// times should probably not have secounds
			// 2016-02-29
			// 17:00
			// [+-]01:00
			startDate = TimestampFormater.dateNumToEng(startMatcher.group( "date" ));
			startTime = startMatcher.group( "time" );
			timeZone  = startMatcher.group( "timezone" );
		}
		System.out.println( startDate );
		System.out.println( startTime );
		System.out.println( timeZone );

	}
}
