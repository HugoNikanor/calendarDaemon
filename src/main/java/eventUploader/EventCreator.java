package eventUploader;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import fileIO.Parser;

import other.DateTransformer;

public class EventCreator {
	// optional:
	// colorId
	// description
	// location
	// requring
	// remiainders
	// status
	// transparency
	// visibility
	//
	//
	//
	// required:
	// summary — Event name (file name)
	// creator (auto set?)
	// end time and date
	// start time and date
	// timezone (have default)
	//
	//
	// futurn:
	// attendees
	// organizer
	// status
	private Event event;

	public EventCreator( String path ) {
		event = new Event();

		Parser parser = new Parser( path );
		// TODO change to user home

		event.setDescription( parser.get( "desc" ) );
		event.setSummary( parser.get( "summary" ) );

		// TODO change default to all day
		String startStr = parser.get( "startTime" ).replace( '.', ':' );
		String endStr   = parser.get(  "endTime" ).replace( '.', ':' );

		endStr = (endStr.charAt(0) == '+' ?
				DateTransformer.relativeToAbsolute(startStr, endStr) : endStr );

		System.out.println( parser.get( "startDate" ).concat(" ").concat( startStr ) );
		
		try {
			// TODO have case to create all day events
			DateTime startDateTime = new DateTime(
					new SimpleDateFormat("yyyy/MMM/dd hh:mm").parse(
						parser.get( "startDate" )
						.concat( " " )
						.concat( startStr )));
			EventDateTime startTime = new EventDateTime()
				.setTimeZone( parser.get( "timeZone" ) )
				.setDateTime( startDateTime );
			event.setStart( startTime );
		} catch( ParseException e ) {
			e.printStackTrace();
		}

		try {
			// TODO relative end dates
			DateTime endDateTime = new DateTime(
					new SimpleDateFormat("yyyy/MMM/ddhh:mm").parse(
						parser.get( "endDate" ).concat(
							endStr )));
			EventDateTime endTime = new EventDateTime()
				.setTimeZone( parser.get( "timeZone" ) )
				.setDateTime( endDateTime );
			event.setEnd( endTime );
		} catch( ParseException e ) {
			e.printStackTrace();
		}
	}

	public Event getEvent() {
		return event;
	}
}
