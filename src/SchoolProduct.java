//package Release;
// Common base class for all the persistent elements of the School 
public class SchoolProduct 
{
	public SchoolProduct ( ClassId classId, ObjectId objectId)
	{
		mClassId  = classId;
		mObjectId = objectId;
	}
	
	public ClassId GetClassId()
	{
		return mClassId;
	}
	
	public ObjectId GetObjectId()
	{
		return mObjectId;
	}
	
	
	private ClassId  mClassId;
	private ObjectId mObjectId;
	
}
