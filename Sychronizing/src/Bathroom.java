import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Bathroom {
	Lock lck;
	Condition femalecon, malecon;
	volatile boolean areYouThere;
	
	public Bathroom(){
		lck = new ReentrantLock();
		malecon = lck.newCondition();
		femalecon = lck.newCondition();
		areYouThere = false;
	}
	
	public void enterMale(){
		try{
			lck.lock();
		try {
			while (areYouThere) {malecon.await();}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			areYouThere = true;
		}finally{
			lck.unlock();
		}
	}
	public void enterFemale(){
    try{
      lck.lock();
    try {
      while (areYouThere) {femalecon.await();}
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
      areYouThere = true;
    }finally{
      lck.unlock();
    }
	}
	public void leaveMale(){
		areYouThere = false;
		malecon.signal();
	}
	public void leaveFemale(){
		areYouThere = false;
		femalecon.signal();
	}

}
