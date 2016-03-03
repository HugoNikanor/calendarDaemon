// this should write to the eventQue xml file
//
// another file in this packet shoul read from that file.
// This class will notifiy that class whene there is sometihng to read

package eventUploader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventQue {

	private static EventThread thread;
	private static List<EventQueObject> events;

	public EventQue() {
		if( events == null ) {
			events = Collections.synchronizedList( new ArrayList<EventQueObject>() );
		}

		if( thread == null ) {
			thread = new EventThread();
			new Thread( thread ).start();
		}
	}

	public void addEvent( File path ) {
		if( events.contains(path) ) {
			events.remove( path );
		}
		events.add( new EventQueObject( path, 10 ) );
	}

	private class EventThread implements Runnable {

		@Override
		public void run() {
			System.out.println( "thread started" );
			synchronized( this ) {
				while( true ) {
					while( events.size() > 0 ) {
						for( EventQueObject ev : events ) {
							ev.tickCountdown();
						}
						if( events.get(0).countdownFinished() ) {
							System.out.println( "creating the event" );
							// this is probably the right place to do the final check
							// if the event is "fine"
							try {
								EventUpload.upload( new EventCreator( events.get(0).getFile() ).getEvent() );
							} catch( Exception e ) {
								// TODO log this error to the user
								e.printStackTrace();
							}
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
}
