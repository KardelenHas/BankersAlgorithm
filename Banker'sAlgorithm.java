public class BankersAlg {
	static int ofProcess   = 4;
	static int ofResources = 5;
	static int [] safeSequence = new int[ofProcess];
	
	public static void main(String[] args){
		int [][] available = new int[ofProcess][ofResources];
		int resourceAvailable[] = {0 , 0 , 2 , 1 , 2};
		
		int maxResourceMatrix[][] = 		{{1 , 1 , 2 , 1 , 3} , 	
									 		 {2 , 2 , 2 , 1 , 0} , 
									 		 {2 , 1 , 3 , 1 , 0} , 
									 		 {1 , 1 , 2 , 2 , 1}};
		int resourceAllocationMatrix[][] =  {{1 , 0 , 2 , 1 , 1} , 
											 {2 , 0 , 1 , 1 , 0} , 
											 {1 , 1 , 0 , 1 , 0} , 
											 {1 , 1 , 1 , 1 , 0}};
		
		safetyCheck(resourceAvailable, maxResourceMatrix, resourceAllocationMatrix); // initial state was safe / there is a deadlock
		
		System.out.println("Change in available resource matrix :");
		System.out.println("-------------------------------------");
		
		for (int k = 0 ; k < ofResources ; k++) {
			available [0][k] = resourceAvailable[k] + resourceAllocationMatrix[safeSequence[0]][k] ; //available = allocation + available 
		}
		
		for( int k = 1 ; k < ofProcess ; k++) {
			for (int h = 0 ; h < ofResources ; h++) {
				available [k][h] = available [k-1][h] + resourceAllocationMatrix[safeSequence[k]][h] ; //available = allocation + available 
			}
		}
		
		for(int k = 0 ; k < ofProcess ; k++) {
			for ( int h = 0 ; h < ofResources ; h++) {
				System.out.print(available[k][h] + " ");
			}
			System.out.println();
		}
	}

	static void needMatrix( int needMatrix[][], int maxMatrix[][], int resourceAllocationMatrix[][]) {
		for (int k = 0 ; k < ofProcess ; k++) {
			for (int h = 0 ; h < ofResources ; h++)
				needMatrix[k][h] = maxMatrix[k][h] - resourceAllocationMatrix[k][h]; //need = max-allocation
		}	
	}

	static boolean safetyCheck(int available[], int maxResourceMatrix[][], int resourceAllocationMatrix[][]){
	   	int [][] needMatrix = new int[ofProcess][ofResources];
	   	int [] resource = new int[ofResources];
	    int need = 0 ;
	    boolean [] end = new boolean[ofProcess];
		
	    needMatrix(needMatrix, maxResourceMatrix, resourceAllocationMatrix);
		
	    for (int k = 0 ; k < ofResources ; k++) {
	    	resource[k] = available[k];
	    }
				 
	    while (need < ofProcess){
	    	boolean found = false;
	    	int h;
	    	
	    	for (int l = 0 ; l < ofProcess ; l++){
	    		if (end[l] == false){
	    			for (h = 0 ; h < ofResources ; h++) {
						if (needMatrix[l][h] > resource [h])
							break;
	    			}
	    			
	    			if (h == ofResources){
	    				for (int m = 0 ; m < ofResources ; m++) {
	    					resource[m] += resourceAllocationMatrix[l][m];
	    				}
	    				
	    				found = true;
	    				safeSequence[need++] = l;
	    				end[l] = true;
	    				
	    			}	
	    		}
	    	}
			 
	    	if (found == false){ //need?available
	    		System.out.print("! DEADLOCK !"); //there is a deadlock
	    		for(int k = 0 ; k < ofProcess ; k++ ) {
	    			System.out.println("İnitial state was not safe ! : " + safeSequence[k]);	    			
	    		}
	    		return false;
	    	}
	    }	
	    for(int k = 0 ; k < ofProcess ; k++ ) {
			System.out.println("İnitial state was safe ! : " + safeSequence[k]);	    			
		}
	    return true;
	}
}