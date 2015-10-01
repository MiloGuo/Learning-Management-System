//package Release;
// Each 1:M relationship contains
// a.  The ObjectId of the relationship instance.
// b.  The ClassId of the "1" Class
// c.  The ObjectId of the "1" Class instance
// d.  The ClassId of the "M" Class
// e.  The multiplicity of the "M" Class
// f.  The ObjectIds ( 0 - M )of the "M" Class instances

import java.util.*;

public class Relation1M<Type>
{
	// This function is used to create a new relationship with no children
	
	public Relation1M()
	{
		 mChildren = new Vector<Type>();
	}
	
	public void Add( Type child )
	{
		mChildren.add( child );
	}
	
	public int GetChildCount()
	{
		return mChildren.size();
	}
	
	public Type GetChild( int childIndex )
	{
		if( childIndex < mChildren.size() )
		{
			return mChildren.get(childIndex);
		}
		else
		{
			System.out.println("Out of range index!" );
			return null;  
		}
	}
		
	private Vector<Type> mChildren;
}
	
