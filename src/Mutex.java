

public class Mutex extends Thread{
	private static final Object lock = new Object();
	private static final Object Donelock = new Object();
	int count;
	public enum State {
        Acquired(100),
        Wait(100),
        Done(100);

        private static final int finalIndex = State.values().length - 1;

        final int timeToComplete;

        State(int timeToComplete) {
            this.timeToComplete = timeToComplete;
        }

        State getNext() {
            int currIndex = this.ordinal();
            if (currIndex >= finalIndex) {
                throw new IllegalStateException("Already at final state");
            }
            return State.values()[currIndex + 1];
        }
    }

    private State state;

	public boolean acquire(){
		try {
            Thread.sleep(1);
        } catch (InterruptedException ignored) {}
    

		synchronized (lock) {
			state=State.Acquired;
			while(state==State.Acquired) {
				if(count==1) {
					state=State.Wait;
				}
				count++;
				continue;
			}
			
			
			synchronized (lock) {
				state=State.Wait;
				
				return true;
				}
			
		}
	}

	public void release() {
		synchronized (Donelock) {
		state=State.Done;
			return;
		}
	}
}