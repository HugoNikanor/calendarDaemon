package eventUploader;

import java.io.File;

public class EventQueObject {
	private File file;
	private int countdown;
	public EventQueObject( File file, int countdown ) {
		this.file = file;
		this.countdown = countdown;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @return if the countdown has hit 0
	 */
	public boolean countdownFinished() {
		return (countdown <= 0);
	}

	/**
	 * Decreases the countdown with one
	 */
	public void tickCountdown() {
		countdown--;
	}
}
