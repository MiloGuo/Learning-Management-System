
public class Student extends SchoolProduct
{

	private String mName;
	private String mId;
	private Relation1M<Score> mScores;
	private Relation1M<CourseOffering> mCourseOfferings;
	
	private static ClassId sClassId = new ClassId( "Student");

    public Student( ObjectId objectId)
    {
        super( sClassId, objectId);
    	mName ="";
    	mId = "";
    	mScores = new Relation1M<Score>();
    	mCourseOfferings  = new Relation1M<CourseOffering>();
    }

    public void SetName( String name)
    {
    	mName = new String(name);
    }
    public void SetId( String Id)
    {
    	mId = new String(Id);
    }
    
    public String GetName()
    {
    	return mName;
    }
    
    public String GetId()
    {
    	return mId;
    }
    
    public void AddRelation( Score score)
    {
    	mScores.Add( score );
    }
    
    public void AddRelation( CourseOffering courseOffering)
    {
    	mCourseOfferings.Add( courseOffering );
    }
    
    public boolean HasTaken( Course.Department department, int number, CourseOffering [] courseOffering )
    {
    	for( int courseOfferingIndex = 0; courseOfferingIndex < mCourseOfferings.GetChildCount(); ++courseOfferingIndex )
    	{
    		Course course = mCourseOfferings.GetChild( courseOfferingIndex).GetCourse();
    		if( ( course.GetDepartment() == department  ) && ( course.GetNumber() == number  ) )
    		{
    			courseOffering[0] = mCourseOfferings.GetChild (courseOfferingIndex );//为什么要这样
    			return true;
    		}
    	}
    	courseOffering[0] = null;
    	return false;
    }
      
    public Relation1M<Score>  GetScores()
    {
    	return mScores;
    }
   
    
    public Relation1M<CourseOffering> GetCourseOfferings()
    {
    	return mCourseOfferings;
    }
    
	
}
