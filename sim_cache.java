import java.io.*;

/* args hold the command line arguments

    Example:-
    sim_cache 32 8192 4 7 262144 8 gcc_trace.txt
    args[0] = "32"
    args[1] = "8192"
    args[2] = "4"
    ... and so on
*/
public class sim_cache
{
    public static void main(String[] args) {

        cache_params params = new cache_params();       // check cache_params.java class for the definition of class cache_params 
		//Cache_Class cacheClass = new Cache_Class();
        String trace_file;                              // Variable that holds trace file name
        char rw;                                        // Variable holds read/write type read from input file
        long addr;                                      // Variable holds the address read from input file

        if (args.length !=7 )       					// Checks if correct number of inputs have been given. Throw error and exit if wrong
        {
            System.out.println("Error: Expected inputs:7 Given inputs:" + args.length);
            System.exit(0);
        }

        // Long.parseLong() converts string to long
        params.block_size = Long.parseLong(args[0]);
        params.l1_size = Long.parseLong(args[1]);
        params.l1_assoc = Long.parseLong(args[2]);
		boolean l1Enable=false;
		if(params.l1_size !=0)
		{
			l1Enable= true;
		}
		//if l1_assoc=1 Direct mapped
		//if l1_assoc=numner of sets ==> fully asssoc
        params.vc_num_blocks = Long.parseLong(args[3]);
		//if vc is 0 dont implement it.
		boolean vcEnable=false;	//flag for victim cache's existance
		if(params.vc_num_blocks != 0){
			vcEnable= true;
		}
		//similarly check for the l2 caches existance using input params.
        params.l2_size = Long.parseLong(args[4]);
        params.l2_assoc = Long.parseLong(args[5]);
		
		boolean l2Enable=false;	//flag for l2 cache's existance
		if(params.l2_size !=0)
		{
			l2Enable= true;
		}
		//l1,l2 vc exist
		if(l1Enable && vcEnable && l2Enable){
					 Cache_Class l1Cache = new Cache_Class(params.block_size,params.l1_size,params.l1_assoc,params.vc_num_blocks,params.l2_size,params.l2_assoc);
					 System.out.println(" All 3 caches l1, vc and l2 are instantiated");
					}
		//l1 & l2 exist without a victim cache
		else if(l1Enable && l2Enable && !vcEnable){
		    		 System.out.println(" caches l1, l2 are instantiated");

		}
		//l1 exists with victim cache
		else if(l1Enable && !l2Enable && vcEnable){
		            System.out.println(" caches l1, vc are instantiated");
		}
		//only l1 exists
		else if(l1Enable && !l2Enable && !vcEnable){
		            System.out.println(" only cache l1 is instantiated");
		}
					
		
        trace_file = args[6];

        // Print params
        System.out.printf("  ===== Simulator configuration =====%n"+
                "  L1_BLOCKSIZE:                     %d%n"+
                "  L1_SIZE:                          %d%n"+
                "  L1_ASSOC:                         %d%n"+
                "  VC_NUM_BLOCKS:                    %d%n"+
                "  L2_SIZE:                          %d%n"+
                "  L2_ASSOC:                         %d%n"+
                "  trace_file:                       %s%n"+
                "  ===================================%n%n", params.block_size, params.l1_size, params.l1_assoc, params.vc_num_blocks, params.l2_size, params.l2_assoc, trace_file);

        // Read file line by line
        try (BufferedReader br = new BufferedReader(new FileReader(trace_file)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                rw = line.charAt(0);                                // gets r/w char from String line
                addr = Long.parseLong(line.substring(2), 16);       // gets address from String line and converts to long. parseLong uses base 16
                if (rw == 'r')
                    System.out.printf("%s %x%n", "read", addr);     // Print and test if file is read correctly 
					//First time cache is empty so initialize the r/c matrix and do a callback to the L2 cache.
					//initialize l1 cache class with l2 and vc present
					
                else if (rw == 'w')
                    System.out.printf("%s %x%n", "write", addr);    // Print and test if file is read correctly 
                /* ************************************
                  Add (calls to) cache code here
                **************************************/
            }
        }
        catch (IOException x)                                       // Throw error if file I/O fails
        {
            System.err.format("IOException: %s%n", x);
        }
		//cacheClass.update(); //test to check if a call to another class fn is working
		/*There are a few constraints on the above parameters: 1) BLOCKSIZE is a power of two and 2)
		the number of sets is a power of two.*/
		/*if(n != 0 && (n & (n-1)) == 0)
		{
        cout<<"Number is power of 2"<<endl;
		}*/
		if(params.block_size!=0 && (params.block_size & (params.block_size-1))==0){
		long sets= (params.l1_size/(params.block_size*params.l1_assoc)); //Rows in the cache matrix ranging from 0 to sets-1 rows
		//n way associative ==> n is number of columns of the cache matrix ranging from 0 to n-1 columns
		System.out.printf("number of sets per L1 is : %d%n",sets);
		}
		else{
			System.out.printf("BLOCKSIZE must be a power of two"); 
		}
}
}