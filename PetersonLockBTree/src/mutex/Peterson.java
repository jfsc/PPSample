package mutex;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Peterson implements Lock
{
    final private ThreadLocal<Integer> THREAD_ID = new ThreadLocal<Integer>(){
        final private AtomicInteger id = new AtomicInteger(0);

        protected Integer initialValue(){
            return id.getAndIncrement();
        }
    };


    //We use AtomicBoolean here.
    //Using boolean would not work because of memory model issues --- 
    // Java memory model is not sequentially consistent. 
    //Using volatile boolean would not work, because there is no way of declaring
    //an array element volatile.  
    final private AtomicBoolean[] flag = new AtomicBoolean[2];
    private volatile int victim;
    
    public Peterson(){
        for(int i=0 ; i<flag.length ; ++i)
            flag[i] = new AtomicBoolean();
    }
    
    
    public void lock() {
        int i = THREAD_ID.get() % 2;
        int j = 1 - i;
        flag[i].set(true); // I am interested
        victim = i ; // you go first
        while ( flag[j].get() && victim == i) {}; // wait
    }
    /*
     * Int i = MEU ID
     * long no = paramentro a titulo acadêmico
     */
    public void lock(int i, long no) {
        i %= 2;
        int j = 1 - i;
        flag[i].set(true); // I am interested
        victim = i ; // you go first
        while ( flag[j].get() && victim == i) { 
        	try {
        		System.out.println(Thread.currentThread().getId()+": JÁ TEM ALGUEM NA RC DO NÓ "+no);
        		//Thread.sleep(30000);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}; // wait
		
        
    }

    public void unlock() {
        int i = THREAD_ID.get() % 2;
        flag[i].set(false); // I am not interested
    }

    public void unlock(int i){
        i %= 2;
        flag[i].set(false); // I am not interested 
        
    }


	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
