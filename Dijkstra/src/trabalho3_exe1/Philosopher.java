package trabalho3_exe1;

import java.util.concurrent.atomic.LongAdder;

public class Philosopher implements Runnable{
	private String name;
	private final LongAdder chopstick;
	private Boolean satisfied = false;
	
	public Philosopher(String name, LongAdder pcstick) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.chopstick = pcstick;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (true){
			if (Thread.interrupted())
			{
				break;
			}
			if(!isISatisfied()){
				if(Dijkstra.IWantToEaT()){
					System.out.println("Philosopher "+this.name+" is eating");
					try {
						Thread.sleep(2000);
						this.satisfied=true;
						//Release CStick
						chopstick.add(2L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else{
					System.out.println(this.name+": Thinking!!");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				System.out.println(this.name+" is satisfied and thinking!!");
				try {
					Thread.sleep(Dijkstra.PHILOSOPHERS*2000);
					this.satisfied=false;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		}
		
	}
	private Boolean isISatisfied(){
		if(satisfied){
			return true;
		}
		return false;
			
	}
	

}
