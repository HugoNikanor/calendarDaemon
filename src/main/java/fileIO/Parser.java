package fileIO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private Map<String, String> values;
	private static Map<String, String> defaultValues;

	/**
	 * TODO update this to allow end of line comments
	 * currently comments are created by not having a colon on the line 
	 */
	public Parser( File file ) {
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
			if( defaultValues == null ) {
				defaultValues = Collections.synchronizedMap( new HashMap<String, String>() );

				// TODO get better path
				File defaultFile = new File( "/home/hugo/calendar/.meta/defaultSettings" );

				BufferedReader br = new BufferedReader( new FileReader( defaultFile ) );

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

						defaultValues.put( key, value );
					}
				}

				br.close();
			}

			values = Collections.synchronizedMap( new HashMap<String, String>() );

			//File file = filePath;

			// this fails with FileNotFoundException if a directory is given
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
			Pattern p = Pattern.compile( ".*(\\d{4}+/.../\\d+).*" );
			Matcher m = p.matcher( file.getPath() );
			if( m.matches() )
				values.put( "startDate", m.group( 1 ) );

			// TODO end date is not set yet




			br.close();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	// both of these should maybe throw some sort of error
	public String get( String key ) {
		return values.containsKey( key ) ?
		   	values.get( key ) : defaultValues.get( key );
	}

	/*
	public String get( String key, String defaultValue ) {
		return values.getOrDefault( key, defaultValue );
	}
	*/
}
