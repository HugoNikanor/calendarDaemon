package main;

import java.io.IOException;

import other.DateTransformer;

public class Main {
	public static void main( String[] args ) throws IOException {
		//new directoryMonitor.DirectoryWatcher();
		//new eventDownloader.Test();
		//new eventUploader.Test();

		//new Parser( "/home/hugo/calendar/2016/jan/22/test-event" );

		System.out.println( DateTransformer.relativeToAbsolute( "12.00", "+2h20m" ) );
		System.out.println( DateTransformer.relativeToAbsolute( "12.00", "+20m" ) );
		System.out.println( DateTransformer.relativeToAbsolute( "12.00", "+2h" ) );

		/*
		main creates a live directoryWatcher and a semi-passive directory logger
		When either of them detect an event, they add the event to the event que
		(the event que makes sure that the same event isn't submitted more than once, 
		this may be a concern if both the live and passive directory wather detect the 
		change (which they are suposed to do))

		create clientInterface as interface
		create eventQue

		create directoryLogger( eventQue, interface )
		create directoryMonitor( eventQue )


		alternativly, make the eventQue into a singleton

		*/
	}
}
