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

	private File file;
	private File defFile;
	private Map<String, String> values;
	private Map<String, String> defaultValues;

	/**
	 * TODO update this to allow end of line comments
	 * currently comments are created by not having a colon on the line 
	 */
	public Parser( File file ) {
		this.file = file;
		defFile = new File( System.getProperty( "user.home" )
								.concat("/calendar/.meta/defaultSettings") );
	}

	private Map<String, String> createMap( File path ) throws Exception {
		Map<String, String> valueMap = Collections.synchronizedMap( new HashMap<String, String>() );

		// this fails with FileNotFoundException if a directory is given
		BufferedReader br = new BufferedReader( new FileReader( path ) );

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

				valueMap.put( key, value );
			}
		}

		valueMap.put( "summary", file.getName() );

		// TODO this puts the whole path, it should only put the date part (2016/jan/22)
		// Does it?
		Pattern p = Pattern.compile( ".*(\\d{4}+/.../\\d+).*" );
		Matcher m = p.matcher( file.getPath() );
		if( m.matches() )
			valueMap.put( "startDate", m.group( 1 ) );

		br.close();

		return valueMap;
	}

	//private void setupWriter() {
	//}

	// both of these should maybe throw some sort of error
	public String get( String key ) throws Exception {
		if( values == null ) 
			values = createMap( file );
		if( defaultValues == null ) 
			defaultValues = createMap( defFile );

		if( values == null ) System.out.println( "es ist NULL" );

		return values.containsKey( key ) ?
		   	values.get( key ) : defaultValues.get( key );
	}

	public void write( String key, String value ) {
	}
}
