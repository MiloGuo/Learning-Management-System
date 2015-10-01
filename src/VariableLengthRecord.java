
import java.io.RandomAccessFile;
import java.util.Vector;

public class VariableLengthRecord 
{
	public VariableLengthRecord()
	{
		mElements = new Vector<RecordElement>();
	}
	
	public int GetCount()
	{
		return mElements.size();
	}
	
	public void AppendValue( boolean value )
	{
		if( value )
		{
			mElements.add(new RecordElement( RecordElementType.BOOLEAN,"Y" ));
		}
		else
		{
			mElements.add( new RecordElement( RecordElementType.BOOLEAN,"N"));
		}
	}
	
	public void AppendValue( char value )
	{
		String text = String.valueOf(value);
		mElements.add( new RecordElement( RecordElementType.BYTE, text ) );
	}
	
	public void AppendValue( int value )
	{
		mElements.add( new RecordElement( RecordElementType.INT,Integer.toString( value )) );
	}
	public void AppendValue( float value )
	{
		mElements.add( new RecordElement( RecordElementType.FLOAT,Float.toString( value )) );
	}
	public void AppendValue( double value )
	{
		mElements.add( new RecordElement( RecordElementType.DOUBLE,	Double.toString( value )) );
	}
	
	public void AppendValue( String value )
	{
		mElements.add(new RecordElement( RecordElementType.STRING, value ));
	}
	
	public boolean GetValue( int elementIndex, boolean[] value )
	{
		if( mElements.size() > elementIndex )
		{
			if( mElements.elementAt( elementIndex ).mType == RecordElementType.BOOLEAN )
			{
				String text = mElements.elementAt( elementIndex ).mText;
				if( text.charAt(0)== 'Y')
				{
					value[0] = true;
					return true;
				}
				else if( text.charAt(0)== 'N')
				{
					value[0] = false;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean GetValue( int elementIndex, char[] value )
	{
		if( mElements.size() > elementIndex )
		{
			if( mElements.elementAt( elementIndex ).mType ==
			RecordElementType.BYTE )
			{
				value [0] = mElements.elementAt( elementIndex ).mText.charAt(0);
				return true;
			}
		}
		return false;
	}
	
	public boolean GetValue( int elementIndex, int[] value )
	{
		if( mElements.size() > elementIndex )
		{
			if( mElements.elementAt( elementIndex ).mType ==
			RecordElementType.INT )
			{
				value[0] = Integer.parseInt( mElements.elementAt( elementIndex	).mText);
				return true;
			}
		}
		return false; 
	}
	
	public boolean GetValue( int elementIndex, float[] value )
	{
		if( mElements.size() > elementIndex )
		{
			if( mElements.elementAt( elementIndex ).mType ==
			RecordElementType.FLOAT )
			{
				value[0] = Float.parseFloat( mElements.elementAt( elementIndex ).mText);
				return true;
			}
		}
		return false;
	}
	
	public boolean GetValue( int elementIndex, double[] value )
	{
		if( mElements.size() > elementIndex )
		{
			if( mElements.elementAt( elementIndex ).mType == RecordElementType.DOUBLE )
			{
				value[0] = Double.parseDouble( mElements.elementAt( elementIndex ).mText);
				return true;
			}
		}
		return false;
	}


	public boolean GetValue( int elementIndex, String[] value )
	{
		if( mElements.size() > elementIndex )
		{
			if( mElements.elementAt( elementIndex ).mType == RecordElementType.STRING )
			{
				value[0] = mElements.elementAt( elementIndex ).mText;
				return true;
			}
		}
		return false;
	}
	
	public int Serialize( RandomAccessFile outputStream )
	{
		try
		{
			int elementCount = GetCount();
			int startingLocation = (int) outputStream.getFilePointer();
			outputStream.writeInt( elementCount);
			int location = (int)outputStream.getFilePointer();
			for( int elementIndex = 0; elementIndex < elementCount; ++elementIndex )
			{
				outputStream.writeInt( mElements.elementAt(	elementIndex).mType.ordinal());
				location = (int)outputStream.getFilePointer();
				outputStream.writeInt( mElements.elementAt( elementIndex ).mSize);
				location = (int)outputStream.getFilePointer();
				outputStream.writeBytes( mElements.elementAt( elementIndex ).mText);
				location = (int)outputStream.getFilePointer();
			}
			int bytesWritten = (int)outputStream.getFilePointer() - startingLocation;
			return bytesWritten;
		}
		catch( Exception ex)
		{
			return 0;
		}
	}
	
	public boolean Deserialize( RandomAccessFile inputStream )
	{
		try
		{
			int elementCount =inputStream.readInt( );
			for( int elementIndex = 0; elementIndex < elementCount; ++elementIndex )
			{
				int elementTypeOrdinal = inputStream.readInt();
				VariableLengthRecord.RecordElementType elementType = RecordElementType.values()[elementTypeOrdinal];
				int size = inputStream.readInt();
				byte [] text = new byte[size];
				if( size == inputStream.read( text, 0, size) )
				{
					String value = new String( text );
					RecordElement recordElement = new RecordElement( elementType, value );
					mElements.add( recordElement);
				}
			}
			return true;
		}
		catch( Exception ex)
		{
			
		}
		return false;
	}

	private enum RecordElementType
	{
		BOOLEAN,
		BYTE,
		INT,
		FLOAT,
		DOUBLE,
		STRING
	}
	
	class RecordElement
	{
		public RecordElementType mType;
		public int mSize;
		public String mText;
		RecordElement( RecordElementType type, String text ) 
		{
			mType = type;
			mText = text;
			mSize = text.length();
		}
	}
	Vector< RecordElement> mElements;
}
