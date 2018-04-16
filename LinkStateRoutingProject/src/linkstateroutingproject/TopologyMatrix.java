/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linkstateroutingproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Shri Ram
 */

public class TopologyMatrix 
{
    static int a_len=0;
    static int i=0;
    public static int T_Matrix [][];
	public int [][] read_file(String file)
	{
            BufferedReader br = null;
            BufferedReader br1 = null;
            FileReader fr = null;
            FileReader fr1 = null;

	    try
	    {
	    	fr = new FileReader(file);
                br = new BufferedReader(fr);
                //int line_num=0;
                
                //Compute the size of the Topology Matrix
	    	String CurrentLine;
                while ((CurrentLine = br.readLine()) != null) 
                {
                    //line_num++;
                    a_len=CurrentLine.split(" ").length;	    	
                    T_Matrix=new int[a_len][a_len];  
                }
                
                //Create the array list 
                List<String>line=new ArrayList<>();
                
                fr1 = new FileReader(file);
                br1 = new BufferedReader(fr1);
                while ((CurrentLine = br1.readLine()) != null) 
                {
                    String l=CurrentLine.trim();
                    line.add(l);
                }
                
                //array mat_ctr[] to indicate the split
                String mat_ctr[]=new String[line.size()];
	    	
	    	for(i=0;i<line.size();i++)
                {  
                    int j=0;
	    		
                    String val=line.get(i);
                    mat_ctr=val.split(" ");
	    	
	    	   while(j<line.size())
                    {
                            T_Matrix[i][j] = new Integer(mat_ctr[j++]).intValue();

                    }
	    	}
                
                //Print the topology matrix
                for(int j=0;j<T_Matrix.length;j++){
	    		for(int k=0;k<T_Matrix.length;k++){
	    			System.out.printf(" "+T_Matrix[j][k]);
	    		}
	    	System.out.println();
	    	}
                br1.close();
	    
	        return T_Matrix;
                        
	    }
            
            catch(Exception e)
	    {
	    	
	    }
	    return null;
	    }

    
    
}
