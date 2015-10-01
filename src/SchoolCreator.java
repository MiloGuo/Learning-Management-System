//package Release;

public abstract class SchoolCreator 
{	
	public SchoolCreator( ClassId classId )
	{
		mClassId = classId;
	}
	
	public abstract SchoolProduct CreateNew( );
	
	public abstract SchoolProduct Create( ObjectId objectId );
	
	public abstract void Save();
	
	public boolean RecognizesClue( ClassId classId )
	{
		return ( classId.compareTo(mClassId) == 0 );
	}
	
	//  This function reads a variable length record by object id.  It returns null
	//    if the record does not exist.

	public VariableLengthRecord Read( ObjectId objectId )
	{
		int [] dataFileOffsetAndSize = new int[2];
		if( sKeyFile.Get( objectId, dataFileOffsetAndSize ) )
		{
			VariableLengthRecord record = new VariableLengthRecord();
			if( sDataFile.Read( dataFileOffsetAndSize[0], dataFileOffsetAndSize[1], record ) )
			{
				return record;
			}
		}
		return null;
	}
	
	//  This function writes a variable length record by object id
	public void Write( ObjectId objectId, VariableLengthRecord record )
	{
		int [] dataFileOffsetAndSize = new int[2];
			
		if( sDataFile.Append( record, dataFileOffsetAndSize ) )
		{
			sKeyFile.Update( objectId, dataFileOffsetAndSize[0], dataFileOffsetAndSize[1] );
		}
	}
	
	
	private ClassId mClassId;
	static KeyFile sKeyFile = new KeyFile( "C:/Persistence",  "Keys"  );
	static VariableLengthRecordFile sDataFile = new VariableLengthRecordFile( "C:/Persistence",  "Data"  );

}
