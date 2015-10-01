//package Release;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class KeyFile
{
	public KeyFile( String folder, String filename )
	{
		mPathname = new String( folder);
		mPathname += ( "/");
		mPathname += (filename);
		mPathname += ( ".ids" );
		mKeys = new TreeMap< ObjectId, OffsetAndLength >();
		Load();
	}
	
	public void Update( ObjectId objectId, int dataFileOffset,	int dataSizeBytes ) 
	{
		OffsetAndLength offsetAndLength = new OffsetAndLength();
		offsetAndLength.fileOffset = dataFileOffset;
		offsetAndLength.sizeBytes = dataSizeBytes;
		mKeys.put( objectId, offsetAndLength);
	}
	
	public boolean Get( ObjectId objectId, int[] dataFileOffsetAndSize )
	{
		if( mKeys.containsKey( objectId ) )
		{
			OffsetAndLength offsetAndLength = mKeys.get( objectId );
			dataFileOffsetAndSize [0] = offsetAndLength.fileOffset;
			dataFileOffsetAndSize [1] = offsetAndLength.sizeBytes;
			return true;
		}
		return false;
	}
	
	public void Save()
	{
		try
		{
			File folder = new File("C://Persistence");
			if( !folder.exists() )
			{
				folder.mkdir();
			}
			File filename = new File( mPathname);
			FileWriter outputStream = new FileWriter( filename);
			for (Entry<ObjectId, OffsetAndLength> entry : mKeys.entrySet())
			{
				OffsetAndLength offsetAndLength = entry.getValue();
				String outputline = entry.getKey().GetValue() + " " +
				offsetAndLength.fileOffset + " " +
				offsetAndLength.sizeBytes + "\r\n";
				outputStream.write(outputline);
			}
			outputStream.close();
		}
		catch (Exception e) 
		{
			
		}
	}
	
	private void Load() 
	{
		try
		{
			File file = new File(mPathname);
			if (file.exists() )
			{
				FileReader fileReader = new FileReader( file);
				Scanner scanner = new Scanner( fileReader);
				while (scanner.hasNextInt() )
				{
					int objectIdValue = scanner.nextInt();
					ObjectId objectId = new ObjectId( objectIdValue );
					OffsetAndLength offsetAndLength = new OffsetAndLength();
					offsetAndLength.fileOffset = scanner.nextInt();
					offsetAndLength.sizeBytes = scanner.nextInt();
					mKeys.put(objectId, offsetAndLength);
				}
				scanner.close();
			}
		}
		catch (FileNotFoundException e) 
		{
			
		}
	}
	class OffsetAndLength
	{
		public int fileOffset; // Offset into data file
		public int sizeBytes; // Number of bytes in record
	}
	private String mPathname; // File to retrieve and save keys
	private TreeMap< ObjectId, OffsetAndLength > mKeys;
	
}
