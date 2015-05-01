/**
 * jDetails It is possible to identify that threads miss counters consistency  
 * @author <a href="mailto:jfsc@cin.ufpe.br">Jose Fernando</a>
 */
public class GoCounter {
	private final static int THREADS = 10;
	Thread[] thread = new Thread[THREADS];
	long counter = 0;
	
	public GoCounter(){
		for (int i = 0; i < THREADS; i++) {
			thread[i] = new MyThread();
			thread[i].start();
		}
	}
	class MyThread extends Thread {
	    public void run() {
	   
	      for (long i = 0; i < 100014000; i++) {
		      counter = counter + 1;
		  }
	      System.out.println(Thread.currentThread().getId()+": "+counter);
	    
	      
	    }
	  }
	public static void main(String[] args) throws InterruptedException {
		GoCounter fc =  new GoCounter();
	}
	
}


