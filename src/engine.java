import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;


public class engine {
	
	static HashMap<String, Integer> mapFile= new HashMap<String,Integer>();
	static HashMap<Integer, String> mapDataType= new HashMap<Integer,String>();


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
		/*for(Map.Entry<String, Integer> entry : mapFile.entrySet()){
		    System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());
		}
		for(Map.Entry<String, String> entry : mapDataType.entrySet()){
		    System.out.printf("Key : %s and Value: %s %n", entry.getKey(), entry.getValue());
		}
		for(int i=0;i<parseinput.sortColumn.length;i++)
		{
			System.out.println(parseinput.sortColumn[i]);
		}*/


		

		creater.createFile(parseinput.inputFile,mainMemoryParts, parseinput.sortColumn);
		
		
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
		    		mapDataType.put(count,"date");
		    		size+=10;
		    	}
		    	else if(parts[1].contains("int"))
		    	{
		    		
		    		mapFile.put(parts[0],count);
		    		mapDataType.put(count,"int");
		    		size+=10;
		    	}
		    	else if(parts[1].contains("char("))
		    	{
		    		mapDataType.put(count,"char");
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
