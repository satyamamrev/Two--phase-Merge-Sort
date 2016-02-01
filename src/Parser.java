
public class Parser {
	
	String metaFile;
	String inputFile;
	static String outputFile;
	String outputColumn[];
	String sortColumn[];
	String memoryLimit;
	static String order;
	
	
	public void parse(String inputFromCommandLine)
	{
		String arguments[]=inputFromCommandLine.split("--");
				
		for(int i=1;i<arguments.length;i++)
		{
			String parts[]=arguments[i].split(" ");
			/*System.out.print(parts[0]);
			System.out.println(parts[1]);*/
			switch(parts[0].trim())
			{
			
				case "meta_file" : 
					
									metaFile=parts[1].trim();
									break;
									
				case "input_file" :
					
									inputFile=parts[1].trim();
									break;
									
				case "output_file" :
					
									outputFile=parts[1].trim();
									break;
									
				case "output_column" : 
					
									outputColumn=parts[1].replaceAll(" ","").split(",");
									break;
								
				case "sort_column" :
					
									sortColumn=parts[1].replaceAll(" ", "").split(",");
									break;	
									
				case "mm" :        
									memoryLimit=parts[1].trim();
									break;
									
				case "order" :
								
									order=parts[1].trim();
									break;
					
				default : 
					
									System.out.println("Wrong Argument :");
									//return ;
									
			}
		}
		
		System.out.println("Metafile : " + metaFile);
		System.out.println("Inputfile : "+ inputFile);
		System.out.println("Outputfile : "+ outputFile);
		System.out.print("OutputColumn :");
		for(int i=0;i<outputColumn.length;i++)
		{
			System.out.print(outputColumn[i]+ " ");
		}
		System.out.println();
		System.out.print("Sortcolumn :");
		for(int i=0;i<sortColumn.length;i++)
		{
			System.out.print(sortColumn[i]+ " ");
		}
		System.out.println();
		System.out.println("Memory Limit : " +memoryLimit);
		System.out.println("Order : "+order);
	}

}
