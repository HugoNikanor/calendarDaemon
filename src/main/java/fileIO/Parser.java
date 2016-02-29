package fileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	public Parser( File file ) throws FileNotFoundException, IOException {
		this.file = file;
		defFile = new File( System.getProperty( "user.home" )
								.concat("/calendar/.meta/defaultSettings") );

		values = createMap( file );

		//System.out.println( this );
	}

	private Map<String, String> createMap( File path )
		throws FileNotFoundException, IOException {
		Map<String, String> valueMap =
		   	Collections.synchronizedMap( new HashMap<String, String>() );

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

		Pattern p = Pattern.compile( ".*(\\d{4}+/.../\\d+).*" );
		Matcher m = p.matcher( file.getPath() );
		if( m.matches() )
			valueMap.put( "startDate", m.group( 1 ) );

		br.close();

		return valueMap;
	}

	// both of these should maybe throw some sort of error
	public String get( String key ) throws Exception {
		if( defaultValues == null ) 
			defaultValues = createMap( defFile );

		if( values == null ) System.out.println( "es ist NULL" );

		return values.containsKey( key ) ?
		   	values.get( key ) : defaultValues.get( key );
	}

	/**
	 * Adds a key value pair to the values
	 */
	public void put( String key, String value ) {
		values.put( key, value );
	}

	/**
	 * writes the values back to the file
	 * TODO This should have some sort of lock check if the file is currently open
	 */
	public void write() throws IOException {
		BufferedWriter bw = new BufferedWriter( new FileWriter(file) );
		for( String key : values.keySet() ) {
			bw.write( String.format( "%s: %s", key, values.get(key) ) );
		}
		bw.close();
	}

	@Override
	public String toString() {
		String ret = "";
		for( String key : values.keySet() ) {
			ret = String.format( "%s: %s\n", key, values.get(key) );
		}
		return ret;
	}
}
