package mmt.connect4;

public class Timer extends Thread {
	private boolean alive;
	private int id, delay;
	private Display callback;

	public Timer(int id, int delay, Display callback) {
		alive = true;
		this.id = id;
		this.delay = delay;
		this.callback = callback;
		start();
	}

	public void Kill() {
		alive = false;
	}

	public void run() {
		while (alive) {
			callback.OnTimerCallback(id);
			try {
				sleep(delay);
			} catch (InterruptedException e) {
			}
		}
	}
}
