//package Release;

import java.io.RandomAccessFile;

public class VariableLengthRecordFile 
{
	public VariableLengthRecordFile( String folder,  String filename )  
	{
		mPathname = new String( folder);
		mPathname += ( "/");
		mPathname += (filename);
		mPathname += ( ".dat" );
		try
		{
			mFileStream = new RandomAccessFile( mPathname, "rwd" );
			if( mFileStream.length() > 0 )
			{
				mFileStream.seek( 0 );
				mBytesUsed =  mFileStream.readInt();
			}
			else
			{
				mBytesUsed = 0;
				mFileStream.writeInt( mBytesUsed );
			}
		}
		catch( Exception ex)
		{
		}
	}

	public int GetBytesUsed() 
	{
		return mBytesUsed;
	}

	public int GetBytesTotal() 
	{
		try
		{
			int bytesLong = (int) mFileStream.length();
			return (int)mFileStream.length();
		}
		catch( Exception ex)
		{
			return 0;
		}
	}

	// The write function adds the record and returns the starting offset and number of bytes written

	public boolean Append( VariableLengthRecord record, int [] offsetAndSizeBytes )
	{
		try
		{
			offsetAndSizeBytes[0] =  (int) mFileStream.length();
			mFileStream.seek(offsetAndSizeBytes[0]);	// Seek to the end of the file
			offsetAndSizeBytes[1] = record.Serialize( mFileStream );            // Convert the record to the stream
			mBytesUsed += offsetAndSizeBytes[1];
		//	int pstLength = (int) mFileStream.length();
			mFileStream.seek( 0 );
			mFileStream.writeInt( mBytesUsed );
			
			return true;
		}
		catch( Exception ex)
		{
			return false;
		}
		
	}

	// The write function adds the record and returns the starting offset and number of bytes written

	public boolean Read( int dataFileOffset,  int dataSizeBytes, VariableLengthRecord record )
	{
		try
		{
			mFileStream.seek(dataFileOffset);	// Seek to the end of the file
			return( record.Deserialize( mFileStream ) );
		}
		catch( Exception ex)
		{
			return false;
		}
	}


	private	String           mPathname;       // File pathname
	private int              mBytesUsed;      // Total number of bytes of data in the file up to ~4 meg
	private	RandomAccessFile mFileStream;     // File stream
}
