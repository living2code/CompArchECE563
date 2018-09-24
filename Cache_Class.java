import java.io.*;

public class Cache_Class{
/*
	Example:-
    sim_cache 32 8192 4 7 262144 8 gcc_trace.txt
    argc = 8
    argv[0] = "sim_cache"
    argv[1] = "32"
    argv[2] = "8192"
    ... and so on*/
	/*size
	assoc
	public readCache()
	public writeCache()
	readCount
	writeCount
	hitCount
	missCount*/
	long tag;
	int index;
	int assoc;
	int	validit;
	int dirtybit;
	int lru;
	
	long block_size;
	long l1_size;
	long l1_assoc;
	long vc_num_blocks;
	long l2_size;
	long l2_assoc;
	
	char rw;	//read or write methods call 
	long addr;	// used in read or write methods
	Cache_Class(long block_size,long l1_size,long l1_assoc,long vc_num_blocks,long l2_size,long l2_assoc){
	    this.block_size=block_size;
	    this.l1_size=l1_size;
	    this.l1_assoc=l1_assoc;
	    this.vc_num_blocks=vc_num_blocks;
	    this.l2_size=l2_size;
	    this.l2_assoc=l2_assoc;
	}
	public void initializeCache(){
		// loop fro the rows and colums assign everthing to zero
		
	}
	
	public void update(){
		System.out.print("Cache_Class's update Function is called");
		
		
	}
	
	public void readCache(char rw, long addr){
		System.out.print("Read invoked");
	}
	
	public void writeCache(char rw, long addr){
		System.out.print("Write invoked");
	}
	
	public long splitAddr(long addr,long assoc,long block_size){
		offset=log2(block_size);
		tagplusindex= addr>>offset;
		
		return tag;
	}
}