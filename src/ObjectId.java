

import java.io.*;

public class ObjectId implements Comparable< ObjectId >
{
	public ObjectId()   // Used to create a new Object Id
	{
		mObjectId = GetNextId();
	}
	
	public ObjectId( int objectId) // Used to re-create an existing Object Id
	{
		mObjectId = objectId;
	}
	
	public int compareTo( ObjectId value )
	{
		if(mObjectId < value. mObjectId )
		{
			return -1;
		}
		else if (mObjectId == value. mObjectId )
		{
			return 0;
		}
		else 
		{
			return 1;
		}
	}

	public String toString() 
	{
		return Integer.toString(mObjectId);
	}
	
	private int GetNextId()
	{
		int objectId = 1;  // Default value if file does not exist.
		try 
		{
			File file = new File("C://Persistence//ObjectId.txt");
			if (file.exists() )
			{
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader); 
			
				String inputLine = bufferedReader.readLine();
				if( inputLine != null )
				{
					objectId = Integer.parseInt(inputLine); 
					objectId++;
				}
			
				bufferedReader.close();
			}
			
			//Now overwrite the original file (create the folder and file if they
			//  do not exist.
			
			File folder = new File("C://Persistence");
			if( !folder.exists() )
			{
				folder.mkdir();
			}

			FileWriter fileWriter = new FileWriter(file);
			String outputLine = Integer.toString(objectId);
			fileWriter.write(outputLine);
			fileWriter.close();
		} 
		catch (Exception e) 
		{
			// Just ignore for now
		}
		return objectId; 
	}
	
	int GetValue()
	{
		return mObjectId;
	}


	private int mObjectId;
}