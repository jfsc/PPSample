package mutex;

public class PetersonTreeTest{
	  private final static int THREADS = 4;
	  private final static int COUNT = 1024;
	  private final static int PER_THREAD = COUNT / THREADS;
	  Thread[] thread = new Thread[THREADS];
	  int counter = 0;

	  //Peterson instance = new Peterson();
	  PetersonTree instance = new PetersonTree(THREADS);
	  
	  public void testParallel() throws Exception {
	      System.out.println("test parallel");
	     // ThreadID.reset();
	      for (int i = 0; i < THREADS; i++) {
		  thread[i] = new MyThread();
	      }
	      for (int i = 0; i < THREADS; i++) {
		  thread[i].start();
		  System.out.println(thread[i].getId()+": COMEÇANDO MEUS TRABALHOS");
	      }
	      
	      for (int i = 0; i < THREADS; i++) {
		  thread[i].join();
	      }
	      
	      if (counter != COUNT) {
		  System.out.println("Wrong! " + counter + " " + COUNT);
	      }
	      
	  }
	  
	  class MyThread extends Thread {
	    public void run() {
	      for (int i = 0; i < PER_THREAD; i++) {
		  instance.PTlock();
		  try {
		      counter = counter + 1;
		      
		  } finally {
			  
		      instance.PTunlock();
		  }
	      }
	    }
	  }

	  public static void main(String[] args) {
	      PetersonTreeTest mpt = new PetersonTreeTest(); //Would like to be able to pass this # of threads

	      try {
	    	  //while(true){
	    		  mpt.testParallel();
	    		  
	    	  //}
		  
	      }
	      catch (Exception e) {e.printStackTrace();}   
	  }
	}