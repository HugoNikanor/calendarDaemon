package fileIO;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import other.TimestampFormater;

/**
 * Writes an event to the appropriate file depending on the
 * summary and start date of the event
 */ 
public class EventToFile {
	public static void write(Event event) throws IOException {
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
		//System.out.println( startDate );
		//System.out.println( startTime );
		//System.out.println( timeZone );

		Parser p = new Parser( new File(
					System.getProperty("user.home")
					.concat("/calendar/")
					.concat( startDate )
					.concat( "/" )
					.concat( event.getSummary() ) ));

		p.put("created", event.getCreated().toString() );
		p.put("creatorEmail", event.getCreator().getEmail() );
		p.put("updated", event.getUpdated().toString() );
		// TODO possibly get more values to write

		p.write();
	}
}
