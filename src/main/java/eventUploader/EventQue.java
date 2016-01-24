// this should write to the eventQue xml file
//
// another file in this packet shoul read from that file.
// This class will notifiy that class whene there is sometihng to read

package eventUploader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventQue {

	private static List<String> events;
	private static EventThread thread;

	public EventQue() {
		if( events == null ) {
			events = Collections.synchronizedList( new ArrayList<String>() );
		}

		if( thread == null ) {
			thread = new EventThread();
			new Thread( thread ).start();
		}
	}

	// TODO this should either also take time modified,
	// or it should just take calendar events
	public void addEvent( String path ) {
		events.add( path );
		System.out.println( path + "added" );
	}

	private class EventThread implements Runnable {

		@Override
		public void run() {
			System.out.println( "thread started" );
			synchronized( this ) {
				while( true ) {
					while( events.size() > 0 ) {
						System.out.println( "creating the event" );
						EventUpload.upload( new EventCreator( events.get(0) ).getEvent() );
						events.remove( 0 );
					}
					try {
						this.wait( 1000 );
					} catch( InterruptedException e ) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
