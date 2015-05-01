
public class Peterson {
    private volatile boolean[] flag = new boolean[2];
    private volatile int victim;
    volatile boolean barr;

    public void lock(int id)
    {
        //System.out.println(id);
        int i = id;
        int j = 1 - id; 
        flag[i] = true;
        victim = i;
        while (barr && flag[j] && victim == i) {};
    }

    public void unlock(int id)
    {
        flag[id] = false;
        barr = true;
    }
}
