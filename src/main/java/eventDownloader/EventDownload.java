package eventDownloader;

import java.io.IOException;

import com.google.api.services.calendar.model.Event;

import serverConnection.APIConnection;

public class EventDownload {

	public Event download( String calendar, String id ) throws IOException {
		Event event = APIConnection.getCalendarService().events()
			.get(calendar, id).execute();

		return event;
	}
}
