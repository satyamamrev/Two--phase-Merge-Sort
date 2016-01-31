import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;


public class engine {
	
	static HashMap<String, Integer> mapFile= new HashMap<String,Integer>();


	public static void main(String[] args) throws IOException {
		
		long fileSize,blockSize,mainMemoryParts,number_BlocksInFile,numberOfIntermediateFile;
		
		
		Scanner scanner=new Scanner(System.in);
		String input=scanner.nextLine();
		Parser parseinput=new Parser();
		parseinput.parse(input);
		fileSize=inputFileSize(parseinput.inputFile);
		blockSize=record_size(parseinput.metaFile);
		// mainMemoryParts ----> numberOfBlocksInMemory
		mainMemoryParts=(Integer.parseInt(parseinput.memoryLimit)*100)/blockSize;
		//System.out.println("Number of Blocks : "+mainMemoryParts);
		// mainMemoryPartsInFile ---> mainMemoryPartsin file 
		number_BlocksInFile=fileSize/blockSize+1;
		// numberOfIntermediateFile ------> Numberofintermediatefile
		numberOfIntermediateFile=number_BlocksInFile/mainMemoryParts;
		//System.out.println("Number of Block In file: "+numberOfIntermediateFile);
		if((numberOfIntermediateFile)>mainMemoryParts-1)
		{
			System.out.println("Two way mergesort not possible");
			return ;
		}
		BlockCreator creater=new BlockCreator();
		creater.createBlock(parseinput.inputFile,mainMemoryParts, parseinput.sortColumn);
		
		
	}
	
	
	
	public static int  record_size(String metafile) throws IOException
	{
	
		
			BufferedReader reader= new BufferedReader(new FileReader(metafile));
			String str="";
			int size=0,count=0;
		    while ((str = reader.readLine()) != null)
		    {
		    	
		    	String parts[]=str.split(",");
		    	if(parts[1].contains("date"))
		    	{
		    		mapFile.put(parts[0],count);
		    		size+=10;
		    	}
		    	else if(parts[1].contains("int"))
		    	{
		    		mapFile.put(parts[0],count);
		    		size+=10;
		    	}
		    	else if(parts[1].contains("char("))
		    	{
		    		mapFile.put(parts[0],count);
		    		String char_value=parts[1].substring(parts[1].indexOf("char(")+5,parts[1].indexOf(')'));
		    		size+=(Integer.parseInt(char_value));
		    			    		
		    	}
		    	count++;
		    	
		    }
		       
		    reader.close();
		    
		    return size;
		
	}
	public static long inputFileSize(String inputFile)
	{
		//System.out.println(inputFile);
		File file=new File(inputFile);
			
		return file.length();
	}

}
