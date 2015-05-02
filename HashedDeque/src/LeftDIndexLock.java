import java.util.concurrent.locks.ReentrantLock;


public class LeftDIndexLock {

	private ReentrantLock indexLeftLock = new ReentrantLock();
	private ElementDeque lbucket = new ElementDeque();
	
	public ElementDeque getLbucket() {
		try{
			indexLeftLock.lock();
			return lbucket;
		}finally{
			indexLeftLock.unlock();
		}
	}
	public void setLbucket(ElementDeque lbucket) {
		try{
			indexLeftLock.lock();
			this.lbucket = lbucket;
		}finally{
			indexLeftLock.unlock();
		}
		
	}
	
	
}
