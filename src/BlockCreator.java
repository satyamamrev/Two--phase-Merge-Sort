import java.io.BufferedReader;
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


public class BlockCreator {
	
	// create arraylist that will be used for making sorted file
	public void createFile(String filename,long blockSize,String sortColumn[]) throws IOException
	{
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
				sortArraylist(data, sortColumn,interfilename);
				interfilename="file".concat(String.valueOf(numbr_files));
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
			sortArraylist(data, sortColumn,interfilename);
			
		}
		
		reader.close();
		
	}
	
	// Sort arraylist w.r.t any number of column and then create file
	
	public void  sortArraylist(ArrayList<String> data,final String sortColumn[],String interFilename) throws IOException
	{
		int index=0;
		final engine e=new engine();
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
								return Integer.parseInt(s1.split(",")[j]) < Integer.parseInt(s2.split(",")[j]) ? -1 : Integer.parseInt(s1.split(",")[j]) == Integer.parseInt(s2.split(",")[j]) ? 0 : 1;
								
					case "char" :
								return s1.split(",")[j].compareTo(s2.split(",")[j]);
						
					case "date" :
								DateFormat f = new SimpleDateFormat("dd/MM/yyyy");
								try {
									return f.parse(s1.split(",")[j]).compareTo(f.parse(s2.split(",")[j]));
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

}
