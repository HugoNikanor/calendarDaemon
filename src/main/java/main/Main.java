package main;

import java.io.IOException;

public class Main {
	public static void main( String[] args ) throws IOException {
		// new thread
		new directoryMonitor.DirectoryWatcher();

		// use main thred
		// setup directoryLogger
	}
}
