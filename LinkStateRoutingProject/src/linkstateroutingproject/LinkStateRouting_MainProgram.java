/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkstateroutingproject;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
/**
 *
 * @author Shri Ram
 */
public class LinkStateRouting_MainProgram {

  
    public static TopologyMatrix tm;
    public static int[] path;
    public static int[]path_arr;
    public static int[][] T_Matrix;
    public static int sr;
    public static int dest;
    public static int dist_arr[];
    public static int a_len;
    public static int []a;
    public static int ctr_next=0;
    public static Boolean ctr;
    static int pre_updtd;
    public static int del_dup;
        
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
         
        LinkStateRouting_MainProgram ma = new LinkStateRouting_MainProgram();
        while(true)
            LinkStateRouting_MainProgram.display();
    }
    
    /**
	 * Function display() presents the Menu & makes calls to the respective inner functions mentioned
	 * 
	 */
    public static void display() throws IOException
    {
        int select=0;
        tm = new TopologyMatrix();
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        do
        {
        String current_line;   
        
        System.out.println("\n|----------Link State Routing----------|");
        System.out.println("1) Create a Network Topology");
        System.out.println("2) Build a Forward Table");
        System.out.println("3) Shortest Path to Destination Router");
        System.out.println("4) Modify a Topology");
        System.out.println("5) Best Router for Broadcast");
        System.out.println("6) Exit the LinkStateRouting Program\n");
        System.out.println("Please choose your input");
        
        current_line = br.readLine();
        select=Integer.parseInt(current_line);
                    
        switch(select)
        {
            case 1:
                LinkStateRouting_MainProgram.net_topology();
                break;
            case 2:
                LinkStateRouting_MainProgram.gen_fwd_table();
                break;
            case 3:
                LinkStateRouting_MainProgram.shortest_path_to_Dest();
                break;
            case 4:
                LinkStateRouting_MainProgram.Mod_Topology();
                break;
            case 5:
                LinkStateRouting_MainProgram.BestRoutr_Broadcast();
                break;
            case 6:
                LinkStateRouting_MainProgram.exit();
                break;
            default:
                System.out.println("Not a valid selection!Please try again\n");
                continue;
        }
     
        }
        while(select<6);
        //br.close();  
    }
    /**
	 * Function net_topology() reads & displays the topology Matrix from the input file
	 * 
	 */
    public static void net_topology() throws IOException
    {
        sr=Integer.MAX_VALUE;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        
        System.out.println("Enter path/location of file for creation of network topology");
        String file = br.readLine();
       
        ctr = new File(file).exists();

        if (ctr!=false) 
        {
                ctr_next=1;
                System.out.println("\nTopology matrix read from the file");
                T_Matrix = tm.read_file(file);
                a_len = T_Matrix.length;
        } 
        else 
        System.out.println("File doesnt exist in the system");
    }
    /**
	 * Function gen_fwd_table() generates the forward/connection table for a given source router
	 * 
	 */
    public static void gen_fwd_table() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        
        if(ctr_next==1) //generate the forward table for topology matrix(Choice 1)
        {
            System.out.println("Select the source router");
            sr=Integer.parseInt(br.readLine());
            
            path_arr=dijkstra(sr-1, T_Matrix ,a_len);
            interfaceconnection(sr-1, path_arr);
        }
        else
        System.out.println("Choose your input as 1 for Topology Matrix creation & then proceed for forward table creation");
        
    }
    /**
	 * Function shortest_path_to_Dest() takes the source & destination router as the input 
         * & makes calls to dijkstra & Display_Path function to compute & print the shortest path & optimal cost 
         * 
	 */
    public static void shortest_path_to_Dest() throws IOException
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        
        System.out.println("Select the source router");
        sr = Integer.parseInt(br.readLine());

        System.out.println("Enter Destination Router");
        dest = Integer.parseInt(br.readLine());

        a_len=T_Matrix.length;
        path_arr=dijkstra(sr-1, T_Matrix ,a_len);
		
        if(dist_arr[dest-1]>0&&dist_arr[dest-1]!=Integer.MAX_VALUE)
        Display_Path((sr-1),(dest-1),a_len,path_arr,T_Matrix,dist_arr);
        else
        System.out.println("Path non-existant!");
        
    }
    /**
	 * Function Display_Path() prints the shortest path & optimal cost from source to destination
	 * 
	 */
    public static void Display_Path(int sr, int dest,int a_len,int path_arr[],int T_Matrix[][],int dist_arr[] ) 
    {
        System.out.print("\nThe optimal total cost from Router" + " " + (sr+1)+ " " + " " + "To" + " " + " " + "Router" + " " + (dest+1)+" = " + dist_arr[dest]);

        System.out.println("\n&\n"+"The Shortest Path From Router" + " " + (sr+1)+ " " + " " + "To" + " " + " " + "Router" + " " + (dest+1)+" is as follows");
        
	int i,j;
	int a[]=new int[a_len+1];
	for(i=0;i<a_len;i++)
	{
		a[i]=-1;
	}
	a[0]=dest;
	i=1;
	while(dest!=sr)
	{   
		a[i]=path_arr[dest];
		dest=path_arr[dest];
		
		i++;
	}

	for(j=i;j>=0;j--)
	{	if(a[j]!=-1)
	System.out.print(""+"--->"+((a[j])+1));
	}
	System.out.println();
    }
    /**
	 * Function Mod_Topology() takes input as to which router to be deleted &
         * updates & prints the topology matrix & forward table by 
         * making calls to dijkstra & interfaceconnection function
	 * 
	 */
    public static void Mod_Topology() throws IOException
    {
        
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        
        int router;
        int del_dup=0;
        System.out.println("Specify the router to be deleted from the Network Topology ");
        router=Integer.parseInt(br.readLine());
        System.out.println("Following is the Updated Topology Matrix & Interface Table after the deletion of router  "+router);
        if(del_dup!=router)
        {
            del_dup=router;
            LinkStateRouting_MainProgram.delete(T_Matrix,(router-1),a_len);          
            if(sr!=Integer.MAX_VALUE)
            {              	
                path_arr=dijkstra(sr-1, T_Matrix,  a_len);
                interfaceconnection(sr-1,path_arr);
            }
        }
        else
        System.out.println("Router"+"  "+router+"  "+"has been already deleted");
        
    }
    /**
	 * Function BestRoutr_Broadcast() computes & displays the best router among all in the network topology
	 * 
	 */
    public static void BestRoutr_Broadcast()
    {
       int min=Integer.MAX_VALUE;
        int index=0;
      
            for(int i=0;i<T_Matrix.length;i++)
		{
			dijkstra(i,T_Matrix,T_Matrix.length);
			int a=0;
			
			for(int j=0;j<T_Matrix.length;j++)
			{
				a=a+dist_arr[j];
			}
			if(a<=min)
			{
				min=a;
				index=i;
			}
                }
		System.out.println("Best router for the broadcast among the Network Topology is "+(index+1));
	
    }
    /**
	 * Function exit() to come out of the main program
         * 
	 */
    public static void exit()
    {
        System.out.println("Exiting the LinkStateRouting Program..... ");
        System.exit(0);    //To exit from the 1st program we use exit(1)
    }
    /**
	 * Function interfaceconnection() computes & displays the interface/connection table of the source router
	 * 
	 */
    public static void interfaceconnection(int sr , int path_arr[])                
	{   
	int i,z,pre;
	int a[]=new int[T_Matrix.length];
	System.out.println("Router"+" || "+"Link/Interface");
        System.out.println("====================================");
	for(z=0;z<T_Matrix.length;z++)
	{
	
            pre = path_arr[z];
            if(pre==-1)
            System.out.println((z+1)+"      ||   "+"None");
            else
            {
                i=0;
                while(pre!=sr)
                {
                        pre_updtd=pre;
                        pre=path_arr[pre];

                        i++;
                }
                if(z==sr)
                {
                        System.out.println((z+1)+"      ||   "+"None");
                }
                else
                        if(i==0)
                        {
                                System.out.println((z+1)+"      ||   "+(z+1));
                        }
                        else
                        System.out.println((z+1)+"      ||   "+(pre_updtd+1));
            }
        }
        }
    /**
	 * Function dijkstra() computes the shortest distance between source router & other routers
	 * 
	 */
    public static int[] dijkstra(int sr, int T_Matrix[][], int a_len) 
	{ 
		
		dist_arr = new int[a_len + 1]; 
		
		int incorporatednode[] = new int[a_len + 1];
		
		path = new int[a_len + 3];
		for (int i = 0; i < a_len; i++)
		{	
			path[i] = -1;
			dist_arr[i] = Integer.MAX_VALUE;
			incorporatednode[i] = 0;			
		}
                //for self loop dist_arr[]=0 
                dist_arr[sr] = 0;

		// Compute the shortest path for all nodes/routers
                int tmat_count = 0;
                    while(tmat_count<a_len)
                    { 
                        int min = Integer.MAX_VALUE; // Integer.MAX_VALUE returns 2,147,483,647
                        int node_indicator = -1;
                        int b = 0;
			while(b<a_len)
                        {
                                if (incorporatednode[b] == 0 && dist_arr[b] < min) 
                                {
					min = dist_arr[b];
					node_indicator = b;
				}
                                b++;
                        }
                        //It gives us the pointer for node incorportaed
			int h = node_indicator; 
                        
                        if(h>=0)
                        {
                                incorporatednode[h] = 1;               //Set visited node array to 1

                                b = 0;
                                while (b < a_len) 
                                {
                                                if (incorporatednode[b] == 0 && T_Matrix[h][b] > 0 && dist_arr[h] != Integer.MAX_VALUE && dist_arr[h] + T_Matrix[h][b] <= dist_arr[b]) 
                                                {
                                                    dist_arr[b] = dist_arr[h] + T_Matrix[h][b];
                                                    path[b]=h;	

                                                }

                                        b++;

                                }

                        }
                        tmat_count++;
                    }
		return path;
	}
    /**
	 * Function delete() turns down/deletes the router requested
	 * 
	 */
    public static void delete(int[][] T_Matrix, int router, int a_len) {
		// TODO Auto-generated method stub
        int i,j;
        
        for(i=0;i<a_len;i++)
        {
            if(i==router)	
            for(j=0;j<a_len;j++)
            {
                T_Matrix[i][j]=-1;
            }
        }
        for(i=0;i<a_len;i++)
        {
            for(j=0;j<a_len;j++)
            {   if(j==router)
                {
                    T_Matrix[i][j]=-1;
                }
            }
        }
        for(i=0;i<a_len;i++)
        {
                for(j=0;j<a_len;j++)
                {
                        System.out.print(T_Matrix[i][j]+" ");
                }
                System.out.println();
        }

    }
    
}
    

    

