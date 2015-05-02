import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;


public class ElementDeque {
	private String name;
	private int position;
	private ReentrantLock qlock = new ReentrantLock();
	private LinkedList<ElementDeque> bucket =new LinkedList<ElementDeque>();

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public void Ladd(ElementDeque Lbucket){
		try{
			qlock.lock();
			bucket.addFirst(Lbucket);
		}finally{
			qlock.unlock();
		}
		
	}
	public ElementDeque Lpop(){
		try{
			qlock.lock();
			return bucket.pollFirst();
		}finally{
			qlock.unlock();
		}
	}
	public void Radd(ElementDeque Rbucket){
		try{
			qlock.lock();
			bucket.add(Rbucket);
		}finally{
			qlock.unlock();
		}
	}
	
	public ElementDeque Rpop(){
		try{
			qlock.lock();
			return bucket.pollLast();
			}finally{
				qlock.unlock();
		}
	}
	
	public ElementDeque getLast(){
		try{
			qlock.lock();
			if(bucket.isEmpty())return new ElementDeque();
			return bucket.getLast();
		}catch(NoSuchElementException e){
			//e.printStackTrace();
			return null;
		}finally{
			qlock.unlock();
		}
	}
	
	public boolean isEmpty(){
		try{
			qlock.lock();
			return bucket.isEmpty();
		}finally{
			qlock.unlock();
		}
		
		
	}
}
