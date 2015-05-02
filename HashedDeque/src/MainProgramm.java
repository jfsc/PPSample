import java.util.LinkedList;


public class MainProgramm {
	private static HashedDeque hdeque = new HashedDeque();
	private final static int THREADS = 10;
	private static Thread[] thread = new Thread[THREADS];
	
	public MainProgramm(){
		hdeque._dequeSetup();
		 for (int i = 0; i < THREADS; i++) {
				thread[i] = new MyThread();
				thread[i].start();
			}
	}
	class MyThread extends Thread {
	    public void run() {
	    	for(int i=0; i<=10;i++){
	    		if((i%2==0)) hdeque.addLeft();
	    		else hdeque.addRight();
	    	}
	    		
	        
	    }
	  }
	
	public static void main(String[] args) {
		 MainProgramm mp = new MainProgramm();
	}

}