import java.util.concurrent.locks.ReentrantLock;


public class RightDIndexLock {

	private ReentrantLock indexRightLock = new ReentrantLock();
	private ElementDeque rbucket = new ElementDeque();
	
	public ElementDeque getRbucket() {
		try{
			indexRightLock.lock();
			return rbucket;
		}finally{
			indexRightLock.unlock();
		}
	}
	public void setRbucket(ElementDeque rbucket) {
		try{
			indexRightLock.lock();
			this.rbucket = rbucket;
		}finally{
			indexRightLock.unlock();
		}
		
	}
	
	
}
