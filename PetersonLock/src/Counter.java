
class Counter {
    private int value;

    public Counter(int c)   {
        value = c;
    }

    public int get()
    {
        return value;
    }

    public int getAndIncrement()    {       
        return value++;
    }
}
