package data;

public class CSRBlock {
    private int ptr_base;
    private int[] ptr;
    private int[] idx;

    public CSRBlock(int ptr_base, int[] ptr, int[] idx){
        this.ptr_base = ptr_base;
        this.ptr = ptr;
        this.idx = idx;
    }

    public int getPtr_base(){
        return ptr_base;
    }

    public int[] getPtr(){
        return ptr;
    }

    public int[] getIdx(){
        return idx;
    }
}
