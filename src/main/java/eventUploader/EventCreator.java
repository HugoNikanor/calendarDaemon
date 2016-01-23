package eventUploader;

import java.text.SimpleDateFormat;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import fileIO.Parser;

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
		Parser defParse = new Parser( "/home/hugo/calendar/.meta/defaultSettings" );

		event.setDescription( parser.get( "desc", "" ) );
		event.setSummary( parser.get( "summary", "ERROR" ) );
		
		// TODO have case to create all day events
		DateTime startDateTime = new DateTime(
				new SimpleDateFormat("yyyy/MMM/ddhh:mm").format(
					parser.get( "start-date" ).concat(
					parser.get( "start-time", "00:00" ))));
		EventDateTime startTime = new EventDateTime()
			.setTimeZone( parser.get( "timeZone", defParse.get( "timeZone" ) ) )
			.setDateTime( startDateTime );
		event.setStart( startTime );


	}

	public Event getEvent() {
		return event;
	}
}
