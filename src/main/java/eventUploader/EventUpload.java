package eventUploader;

import java.io.IOException;

import com.google.api.services.calendar.model.Event;

import serverConnection.APIConnection;

public class EventUpload {

	// is this thread safe
	public static void upload( Event event ) {

		String calendarId = "primary";
		try {
			event = APIConnection.getCalendarService().events().insert(calendarId, event).execute();
			System.out.println( "event uploaded" );
			System.out.println( event );

			fileIO.EventToFile.write( event );

			// TODO write updated event back to source file
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
