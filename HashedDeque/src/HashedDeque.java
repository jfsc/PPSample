import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.ReentrantLock;


public class HashedDeque {
	private volatile LinkedList<ElementDeque> deque = new LinkedList<ElementDeque>();
	private LeftDIndexLock lIndex = new LeftDIndexLock();
	private RightDIndexLock rIndex = new RightDIndexLock();
	private ReentrantLock indexLeftLock = new ReentrantLock();
	private ReentrantLock indexRightLock = new ReentrantLock();
	private int sizeOfDeque = 5;
	
	public void _dequeSetup(){
		
		for(int i=0; i<sizeOfDeque; i++){
			ElementDeque bucket = new ElementDeque();
			bucket.setPosition(i);
			bucket.setName("DEQ_"+i);
			deque.add(bucket);
		}
		lIndex.setLbucket(deque.getFirst());
		rIndex.setRbucket(deque.get(1));
	}
	
	public void addLeft(){
		try{
			acquiringSafeLock();
			ElementDeque tempBucket =  new ElementDeque();
			tempBucket.setPosition(lIndex.getLbucket().getPosition());
			tempBucket.setName("LDEQ_"+lIndex.getLbucket().getPosition());
			rIndex.getRbucket().Ladd(tempBucket);
			//deque.get(lIndex.getLbucket().getPosition()).Ladd(tempBucket);
			setNextLeftIndex();
		}finally{
			releaseLocks();
		}
		
		
	}
	
	public void addRight(){
		try{
			
			acquiringSafeLock();
			ElementDeque tempBucket =  new ElementDeque();
			tempBucket.setPosition(rIndex.getRbucket().getPosition());
			tempBucket.setName("RDEQ_"+rIndex.getRbucket().getPosition());
			rIndex.getRbucket().Radd(tempBucket);
			setNextRightIndex();	
		}finally{
			releaseLocks();
		}
		
		
	}
	
	private void acquiringSafeLock(){
		releaseLocks();
		//Firt acquire Left, after Right
		indexRightLock.lock();
		indexLeftLock.lock();
	}
	private void releaseLocks(){
		//Clear
		try{
			if (indexRightLock.isHeldByCurrentThread()) {
				indexRightLock.unlock();
			}
			if(indexLeftLock.isHeldByCurrentThread()){
				indexLeftLock.unlock();
			}
			
		}catch(IllegalMonitorStateException e){
			e.printStackTrace();
		}
		
	}
	
	private void setNextLeftIndex(){
		int i = lIndex.getLbucket().getPosition();
		//wrapround 
		i-=1;
		if(i<0) i = (sizeOfDeque-1);
		lIndex.setLbucket(deque.get(i));
	}
	
	private void setNextRightIndex(){
		int i = rIndex.getRbucket().getPosition();
		ElementDeque tempBucket = new ElementDeque();
		i+=1;
		//wrapround 
		if(i>(sizeOfDeque-1)) i = 0;
		tempBucket = deque.get(i);
		while(!tempBucket.isEmpty()){
			if(tempBucket.isEmpty()) {
				tempBucket.setPosition(i);
				rIndex.setRbucket(tempBucket);
			}
			tempBucket = tempBucket.getLast();
		}
		
	}

}
