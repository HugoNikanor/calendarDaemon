package eventUploader;

import java.io.IOException;

import com.google.api.services.calendar.model.Event;

import serverConnection.APIConnection;

public class EventUpload {

	// is this thread safe
	/**
	 * uploads the event if it isn't on the server
	 * else it updates it
	 *
	 * If it is on the server is checked by looking in the file if an id is present
	 */
	public static void upload( Event event ) {

		String calendarId = "primary";
		try {
			/*
			if( event.getId() != null )
				event = APIConnection.getCalendarService().events().update(calendarId, event.getId(), event).execute();
			else
			*/
			System.out.println( event );
			event = APIConnection.getCalendarService().events().insert(calendarId, event).execute();

			System.out.println( "————————————————————————————————————————————————————————————————————————————————" );
			//System.out.println( "event uploaded" );
			System.out.println( event );

			//fileIO.EventToFile.write( event );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
