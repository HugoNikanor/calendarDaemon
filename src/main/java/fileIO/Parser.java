package fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Parser {

	private Map<String, String> values;

	public Parser( String filePath ) {
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


		try {
			values = Collections.synchronizedMap( new HashMap<String, String>() );

			File file = new File( filePath );

			BufferedReader br = new BufferedReader( new FileReader( file ) );

			String line;
			while( (line = br.readLine()) != null ) {
				String key;
				String value;

				int sepPos = line.indexOf( ':' );
				if( sepPos != -1 ) {
					key = line.substring(0, sepPos);
					value = line.substring( sepPos + 1 );

					// removes surrounding whitespace
					value = value.trim();

					values.put( key, value );
				}
			}

			values.put( "summary", file.getName() );
			// TODO this puts the whole path, it should only put the date part (2016/jan/22)
			values.put( "start-date", file.getPath() );



			/*
			String rawDate = file.getAbsolutePath();
			String date;
			String regexPattern = ".*calendar/(.* /.* /.*)/.*";
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

				/*
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
				*/


			br.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	// both of these should maybe throw some sort of error
	public String get( String key ) {
		return values.get( key );
	}

	public String get( String key, String defaultValue ) {
		return values.getOrDefault( key, defaultValue );
	}
}
