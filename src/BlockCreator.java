import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class BlockCreator {
	
	
	public void createBlock(String filename,long blockSize,String sortColumn[]) throws IOException
	{
		//System.out.println("getting response");
		//System.out.println(blockSize);
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
				//System.out.println("hey");
				data.add(str);
				interfilename="file".concat(String.valueOf(numbr_files));
				FileWriter writer=new FileWriter(interfilename);
				for(int i=0;i<data.size();i++)
				{
					writer.write(data.get(i)+"\n");
				}
				writer.close();
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
			
			//count++;
			
		}
		if(flag==1)
		{
			interfilename="file".concat(String.valueOf(numbr_files));
			FileWriter writer=new FileWriter(interfilename);
			for(int i=0;i<data.size();i++)
			{
				writer.write(data.get(i)+"\n");
			}
			writer.close();
		}
		
		reader.close();
		
	}

}
