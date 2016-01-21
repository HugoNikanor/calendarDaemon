package eventUploader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class EventParser {

	public EventParser( String filePath ) {
		/*
		EventDateTime startTime = new EventDateTime()
			.setDateTime(new DateTime(new Date(System.currentTimeMillis()) ) )
			.setTimeZone( "Europe/Stockholm" );
		EventDateTime endTime = new EventDateTime()
			.setDateTime( new DateTime(new Date(System.currentTimeMillis() + 1200000)) )
			.setTimeZone( "Europe/Stockholm" );

		Event event = new Event()
			.setSummary("Extreme Test Event")
			.setLocation("home")
			.setDescription("This is a test")
			.setStart( startTime )
			.setEnd( endTime);
		*/	

		Event event = new Event();

		try {
			File eventFile = new File( filePath );
			event.setSummary( eventFile.getName() );

			BufferedReader br = new BufferedReader( new FileReader( eventFile ) );

			String rawDate = eventFile.getAbsolutePath();
			String date;
			String regexPattern = ".*calendar/(.*/.*/.*)/.*";
			Pattern r = Pattern.compile( regexPattern );
			Matcher m = r .matcher( rawDate );
			if( m.find() ) {
				date = m.group( 1 );
			} else {
				date = "1970/jan/01";
			}

			while( true ) {
				String line = br.readLine();
				if( line == null ) break;

				int colonPos = line.indexOf(':');
				String key = line.substring(0, colonPos);
				String value = line.substring( ++colonPos );

				switch( key ) {
					case "desc":
						event.setDescription( value );
						break;
					case "start-time":
						
						event.setStart( new EventDateTime()
								.setDateTime( new DateTime( 
										new SimpleDateFormat( "yyyy/MMM/dd HH:mm" ).parse( date.concat( value ) ) ) ) 
								.setTimeZone( "Europe/Stockholm" ));
						break;
					case "end-time":
						event.setEnd( new EventDateTime()
								.setDateTime( new DateTime( 
										new SimpleDateFormat( "yyyy/MMM/dd HH:mm" ).parse( date.concat( value ) ) ) ) 
								.setTimeZone( "Europe/Stockholm" ));
						break;
					case "location":
						event.setLocation( value );
						break;
					default:
						System.out.println( "Bad data" );
						break;
				}
				System.out.println( key.concat(":").concat(value) );


			}
			br.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}


		new EventUpload().upload( event );
	}
}
