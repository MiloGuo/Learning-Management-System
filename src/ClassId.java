//package Release;

import java.io.*;
import java.util.*;       
import java.util.Map.*;

public class ClassId implements Comparable< ClassId >
{
	public ClassId( String className )
	{
		mClassId = GetClassId(className);
	}

	public ClassId(int classId ) // Used to re-create an existing Object Id
	{
		mClassId = classId;
	}

	public int compareTo(ClassId value )
	{
		if(mClassId < value.mClassId)
		{
			return -1;
		}
		else if (mClassId == value.mClassId)
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
		return Integer.toString(mClassId);
	}
	
	private int GetClassId( String className)
	{
		int classId = 0;
		TreeMap<String, Integer>classNameMap = Load();

		boolean foundExistingClassName = classNameMap.containsKey( className );
		if(foundExistingClassName)
		{
			classId = classNameMap.get(className);
		}
		else
		{
			classId = classNameMap.size() + 1;
			classNameMap.put(className, classId);
			Save(classNameMap);
		}
		return classId;
	}

	private TreeMap<String, Integer> Load()
	{
		TreeMap<String, Integer> classNameMap = new TreeMap<String, Integer>();
		try 
		{
			File file = new File("C://Persistence//ClassId.txt");

			if (file.exists() )
			{
				FileReader fileReader = new FileReader( file);
				Scanner scanner = new Scanner( fileReader);
			    while (scanner.hasNext()) 
			    {
			      String className = scanner.next();
			      int classId = scanner.nextInt();
			      classNameMap.put(className, classId);
			    }
			    scanner.close();
			}
		}
		catch (Exception e) 
		{
			// Just ignore for now
		}
		return classNameMap;
	}

	private void Save( TreeMap<String, Integer> classNameMap)
	{
		try 
		{
			//Now overwrite the original file (create the folder and file if they
			//  do not exist.
			
			File folder = new File("C://Persistence");
			if( !folder.exists() )
			{
				folder.mkdir();
			}
			
			File filename = new File("C://Persistence//ClassId.txt");
			FileWriter outputStream = new FileWriter( filename);
			for (Entry<String, Integer> entry : classNameMap.entrySet())
			{
				String outputline = entry.getKey() + " " + entry.getValue() + "\r\n";
				outputStream.write(outputline);
			}
			outputStream.close();
		}
		catch (Exception e) 
		{
			// Just ignore for now
		}
	}

	private int mClassId;
}
