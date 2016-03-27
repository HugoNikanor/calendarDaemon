package eventUploader;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import fileIO.Parser;

import other.DateTransformer;
import other.TimestampFormater;

public class EventCreator {
	// optional:
	// colorId
	// description - done
	// location - done
	// repeat
	// remiainders
	// status
	// transparency
	// visibility
	//
	//
	//
	// required:
	// summary — Event name (file name) - done
	// creator (auto set?) - creator email auto set
	// end time and date - done possibly merge 'endDate' and 'endTime' 
	// 			into one variable
	// start time and date - done
	// timezone (have default) - done
	//
	//
	// future:
	// attendees
	// organizer
	// status
	private Event event;

	public EventCreator(File file) throws IOException {
		event = new Event();

		Parser parser = new Parser( file );

		event.setDescription( parser.get( "description" ) );
		event.setSummary( parser.get( "summary" ) );

		// TODO change default to all day
		String startTimeStr = TimestampFormater.formatTime(
				parser.get( "startTime" ).replace( '.', ':' ) );

		String endTimeStr   = parser.get( "endTime" ).replace( '.', ':' );
		if( endTimeStr.charAt(0) == '+' )
			endTimeStr = DateTransformer.
				relativeToAbsoluteTime(startTimeStr, endTimeStr);

		String startDateStr = TimestampFormater.dateEngToNum(
				parser.get( "startDate" ));
		String endDateStr = parser.get( "endDate" );
		if( endDateStr.charAt(0) == '+' )
			endDateStr = DateTransformer.
				relativeToAbsoluteDate(startDateStr, endDateStr);


		try {
			// TODO have case to create all day events
			DateTime startDateTime = new DateTime(
					new SimpleDateFormat("yyyy/MM/ddhh:mm").parse(
						startDateStr + startTimeStr));

			DateTime endDateTime = new DateTime(
					new SimpleDateFormat("yyyy/MM/ddhh:mm").parse(
						endDateStr + endTimeStr));

			EventDateTime startTime = new EventDateTime()
				.setTimeZone( parser.get( "timeZone" ) )
				.setDateTime( startDateTime );
			event.setStart( startTime );

			EventDateTime endTime = new EventDateTime()
				.setTimeZone( parser.get( "timeZone" ) )
				.setDateTime( endDateTime );
			event.setEnd( endTime );
		} catch( ParseException e ) {
			e.printStackTrace();
		}

		// TODO possibly change this to using 'event.set("key", "value")' 
		// and store the supported keys in a file
		event.setLocation( parser.get( "location" ) );
		// TODO support british spelling
		//event.setColorId(  parser.get( "color" ) );
	}

	public Event getEvent() {
		return event;
	}
}
