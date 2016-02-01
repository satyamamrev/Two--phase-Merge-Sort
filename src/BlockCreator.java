import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;


public class BlockCreator {
	
	static ArrayList<String> files;
	static int orderValue;
	// create arraylist that will be used for making sorted file
	public static void createFile(String filename,long blockSize,String sortColumn[],String outputColumn[]) throws IOException
	{
		files=new ArrayList<String>();
		String outputFilenname;
		//Parser obj=new Parser();
		outputFilenname=Parser.outputFile;
		//System.out.println("---->"+Parser.outputFile);
		BufferedReader reader=new BufferedReader(new FileReader(filename));
		String str="";
		long count=1;
		int numbr_files=1,flag=0;
		String interfilename;
		ArrayList<String> data=new ArrayList<String>();
		while ((str = reader.readLine()) != null)
		{
			if(count==blockSize)
			{
				flag=0;
				data.add(str);
				interfilename="file".concat(String.valueOf(numbr_files));
				files.add(interfilename);
				sortArraylist(data, sortColumn,interfilename);
				//interfilename="file".concat(String.valueOf(numbr_files));
				numbr_files++;
				data.clear();
				count=1;
				
			}
			else
			{
				flag=1;
				data.add(str);
				count++;
			}
			
			
			
		}
		if(flag==1)
		{
			interfilename="file".concat(String.valueOf(numbr_files));
			files.add(interfilename);
			sortArraylist(data, sortColumn,interfilename);
			
		}
		
		reader.close();
		
		//for(int i=0;i<files.size();i++)
		//System.out.println(outputFilenname);
		filesMergeSort(files, outputFilenname, sortColumn,outputColumn);
		
		
	}
	
	// Sort arraylist w.r.t any number of column and then create file
	
	public static void  sortArraylist(ArrayList<String> data,final String sortColumn[],String interFilename) throws IOException
	{
		int index=0;
		final engine e=new engine();
		//final int orderValue;
		if(Parser.order.equals("asc"))
		{
			orderValue=1;
		}
		else
		{
			orderValue=-1;
		}
		Collections.sort(data, new Comparator<String>() 
		{
			@Override
			//for()
			
			public int compare(String s1, String s2) 
			{
		
				int j=0,i;
				for(i=0;i<sortColumn.length;i++)
				{
					j=e.mapFile.get(sortColumn[i]);
					if(!s1.split(",")[j].equals(s2.split(",")[j]))
					{
						break;
					}
					
				}
				switch(e.mapDataType.get(j))
				{
					case "int" :
								return orderValue * (Integer.parseInt(s1.split(",")[j]) < Integer.parseInt(s2.split(",")[j]) ? -1 : Integer.parseInt(s1.split(",")[j]) == Integer.parseInt(s2.split(",")[j]) ? 0 : 1);
								
					case "char" :
								return orderValue * (s1.split(",")[j].compareTo(s2.split(",")[j]));
						
					case "date" :
								DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
								try {
									return orderValue * (f.parse(s1.split(",")[j]).compareTo(f.parse(s2.split(",")[j])));
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							
								
				}
				
				
				return 0;
			}
		});
		FileWriter writer=new FileWriter(interFilename);
		for(int i=0;i<data.size();i++)
		{
			writer.write(data.get(i)+"\n");
		}
		writer.close();
		
		
	}
	
	
	public static void filesMergeSort(ArrayList<String> files, String outputfile,final String sortColumn[],String outputColumn[]) throws IOException
	{
		//System.out.println("---"+engine.numberOfIntermediateFile);
		
		final engine e=new engine();
		
		for(int i=0;i<outputColumn.length;i++)
			System.out.println(outputColumn[i]);
		
		PriorityQueue<BinaryFileBuffer> pq = new PriorityQueue<BinaryFileBuffer>(engine.numberOfIntermediateFile, 
	            new Comparator<BinaryFileBuffer>() {
	              public int compare(BinaryFileBuffer ii, BinaryFileBuffer jj) {
	            	  String s1=ii.peek();
	            	 // System.out.println(s1);
	            	 // System.out.println(s1);
	            	  String s2=jj.peek();
	            	  int j=0,i;
	  					for(i=0;i<sortColumn.length;i++)
	  					{
	  						j=e.mapFile.get(sortColumn[i]);
	  						if(!s1.split(",")[j].equals(s2.split(",")[j]))
	  						{
	  							break;
	  						}
	  					
	  					}
	  					switch(e.mapDataType.get(j))
	  					{
	  						case "int" :
	  									return orderValue * (Integer.parseInt(s1.split(",")[j]) < Integer.parseInt(s2.split(",")[j]) ? -1 : Integer.parseInt(s1.split(",")[j]) == Integer.parseInt(s2.split(",")[j]) ? 0 : 1);
	  								
	  						case "char" :
	  									return orderValue * (s1.split(",")[j].compareTo(s2.split(",")[j]));
	  						
	  						case "date" :
	  									DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
	  									try {
	  										return orderValue * (f.parse(s1.split(",")[j]).compareTo(f.parse(s2.split(",")[j])));
	  									} catch (ParseException e1) {
	  										// 	TODO Auto-generated catch block
	  										e1.printStackTrace();
	  									}
	  						
	  								
	  					}
	               // return cmp.compare(i.peek(), j.peek());
						return 0;
	              }
	            }
	        );
	        for (String f : files) {
	            BinaryFileBuffer bfb = new BinaryFileBuffer(f);
	            pq.add(bfb);
	        }
	        BufferedWriter fbw = new BufferedWriter(new FileWriter(outputfile));
	       // int rowcounter = 0;
	        try {
	            while(pq.size()>0) {
	                BinaryFileBuffer bfb = pq.poll();
	                String r[] = bfb.pop().split(",");
	               // add the column in file that appear in command line
	                int len=outputColumn.length;
	                String output="";
	                for(int i=0;i<len;i++)
	                {
	                	if(i<=len-2)
	                		output+=(r[engine.mapFile.get(outputColumn[i])]+",");
	                	else
	                		output+=(r[engine.mapFile.get(outputColumn[i])]);
	                }
	                
	                fbw.write(output);
	                fbw.newLine();
	          //      ++rowcounter;
	                if(bfb.empty()) {
	                    bfb.fbr.close();
	                 //   bfb.originalfile.delete();// we don't need you anymore
	                    
	                } else {
	                    pq.add(bfb); // add it back
	                }
	            }
	        } finally { 
	            fbw.close();
	            for(BinaryFileBuffer bfb : pq ) bfb.close();
	        }
	}

}




class BinaryFileBuffer  {
   // public static int BUFFERSIZE = 2048;
	    public BufferedReader fbr;
	    public String originalfile;
	    private String cache;
	    private boolean empty;
	     
	    public BinaryFileBuffer(String f) throws IOException {
	        originalfile = f;
	        fbr = new BufferedReader(new FileReader(f));
	        reload();
	    }
	     
	    public boolean empty() {
	        return empty;
	    }
	     
	    private void reload() throws IOException {
	        try {
	          if((this.cache = fbr.readLine()) == null){
	            empty = true;
	            cache = null;
	          }
	          else{
	            empty = false;
	          }
	      } catch(EOFException oef) {
	        empty = true;
	        cache = null;
	      }
	    }
	     
	    public void close() throws IOException {
	        fbr.close();
	    }
	     
	     
	    public String peek() {
	        if(empty()) return null;
	        return cache.toString();
	    }
	    public String pop() throws IOException {
	      String answer = peek();
	        reload();
	      return answer;
	    }
}
     
     
 
