package directoryMonitor;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import eventUploader.EventQue;

import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

public class DirectoryWatcher {

	private WatchService watcher;
	private Map<WatchKey, Path> keys;

	private EventQue que;

	public DirectoryWatcher() throws IOException {
		System.out.println( "direcotryWatcher created" );

		que = new EventQue();

		keys = new HashMap<WatchKey, Path>();
		watcher = FileSystems.getDefault().newWatchService();

		registerEvents( Paths.get( "/home/hugo/calendar" ) );

		System.out.println( "listening for events" );
		while( true ) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch( InterruptedException e ) {
				// if the event wasn't really taken just look for the next event
				return;
			}

			if( key.isValid() ) {
				for( WatchEvent<?> event : key.pollEvents() ) {
					WatchEvent.Kind<?> kind = event.kind();

					if( kind.equals( "OWERFLOW" ) ) continue;

					Path path = ((Path) event.context()).toAbsolutePath();

					System.out.println( "kind: " + kind.name() );
					System.out.println( "path: " + path );

					if( !path.toFile().isDirectory() ) {
						System.out.print( path.toFile().toString() );
						System.out.println( " is directory" );
						continue;
					}
					if( path.toFile().isHidden() ) continue;

					if( kind.name().equals( "ENTRY_CREATE" ) ) {
						que.addEvent( path );
					} else if( kind.name().equals( "ENTRY_MODIFY" ) ) {
						que.addEvent( path );
					} else if( kind.name().equals( "ENTRY_DELETE" ) ) {
						System.out.println( path );
					} else {
						continue;
					}
				}
				key.reset();
			}
		}
	}

	private void registerEvents( Path path ) throws IOException {
		Files.walkFileTree( path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory( Path dir, BasicFileAttributes attrs ) 
			throws IOException {
				if( !dir.toFile().isHidden() ) {
					WatchKey key = dir.register( watcher,
							StandardWatchEventKinds.ENTRY_CREATE,
							StandardWatchEventKinds.ENTRY_DELETE,
							StandardWatchEventKinds.ENTRY_MODIFY);

					keys.put(key, dir);
				}

				return FileVisitResult.CONTINUE;
			}
		} );
	}
}
