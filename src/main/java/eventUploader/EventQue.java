// this should write to the eventQue xml file
//
// another file in this packet shoul read from that file.
// This class will notifiy that class whene there is sometihng to read

package eventUploader;

import java.util.ArrayList;
import java.util.List;

public class EventQue {

	private static List<String> events;

	public EventQue() {
		if( events == null ) {
			events = new ArrayList<String>();
		}

		new Thread( new EventThread() ).start();
	}

	// TODO this should either also take time modified,
	// or it should just take calendar events
	public void addEvent( String path ) {
		events.add( path );
	}

	private class EventThread implements Runnable {

		@Override
		public void run() {
			synchronized( this ) {
				while( true ) {
					while( events.size() > 0 ) {
						new EventParser( events.get(0) );
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
