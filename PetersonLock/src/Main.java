import java.util.ArrayList;
import java.util.List;

class Thread1 implements Runnable   {
    private Counter c;
    private int id;
    private List<Integer> values;
    private Peterson lock;

    public Thread1(Counter c, int id, List<Integer> values, Peterson l) {
        this.c = c;
        this.id = id;
        this.values = values;
        this.lock = l;
    }

    public void run() {

        while (true)
        {
            lock.lock(id);
            try {
                try {

                    if (c.get() > 20000)
                        return;

                    int n = c.getAndIncrement();
                    values.add(n);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            finally {
                lock.unlock(id);
            }
        }
    }
}

public class Main {

	public static void main(String[] args) {
		Counter  c = new Counter(1);
        Thread[] t = new Thread[2];
        List<Integer> values = new ArrayList<Integer>();
        Peterson l =  new Peterson();

        for (int i = 0; i < t.length; ++i)  {
            t[i] = new Thread(new Thread1(c, i, values, l));
            t[i].start();
        }

        for (int i = 0; i < t.length; ++i)  {
            try{
                t[i].join();
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();//don't expect it but good practice to handle anyway
            }
        }

        System.out.println(values.size());

	}

}
