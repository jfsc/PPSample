package trabalho3_exe1;

import java.util.concurrent.atomic.LongAdder;



public class Dijkstra {
	//Parallel Counter  (Java 8)
	public static LongAdder pcsticks;
	public static long CHOPSTICKTS = 5;
	public static int PHILOSOPHERS = 20;
	public static void main(String[] args) {

		pcsticks = new LongAdder();
		pcsticks.add(CHOPSTICKTS);
		for (int i = 0; i< PHILOSOPHERS; i++){
			Thread tphil = new Thread(new Philosopher("Phil_"+i, pcsticks));
			tphil.start();	
		}	
	}
	
	public static synchronized Boolean IWantToEaT(){
		if(pcsticks.longValue()>1L){
			pcsticks.add(-2L);
			return true;
		}
		return false;
		
	}
	
}
