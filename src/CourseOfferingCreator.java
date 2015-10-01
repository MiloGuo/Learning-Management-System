
public class CourseOfferingCreator extends SchoolCreator
{
	public CourseOfferingCreator()
	{
		super( sCourseOfferingClassId );
		mCourseOfferings = new Relation1M<CourseOffering>();
	}
	
	// Create a new student 
	public SchoolProduct CreateNew()
	{
		ObjectId objectId = new ObjectId();
		CourseOffering courseOffering = new CourseOffering( objectId);
		mCourseOfferings.Add( courseOffering );
		return courseOffering;
	}
	
	// Create an existing student 
	public SchoolProduct Create( ObjectId objectId )
	{
		for ( int childIndex = 0; childIndex < mCourseOfferings.GetChildCount(); ++childIndex )
		{
			CourseOffering courseOffering =  mCourseOfferings.GetChild(childIndex);
			if( courseOffering.GetObjectId() == objectId )
			{
				mCourseOfferings.Add( courseOffering );
				return courseOffering;
			}
		}
		
		// Not already loaded ?
		
		VariableLengthRecord record = Read( objectId );
		if( record != null )
		{
			int [] quarterOrdinal = new int[1];
			if( record.GetValue( 0, quarterOrdinal ) )
			{
				CourseOffering.Quarter quarter = CourseOffering.Quarter.values()[quarterOrdinal[0]];
				int [] year = new int[1];
				if( record.GetValue( 1, year ) )
				{
					
					CourseOffering courseOffering = new CourseOffering( objectId );
					courseOffering.SetQuarter(quarter);
					courseOffering.SetYear( year[0] );
					
					int recordIndex = 2;
					
					// 1-m Relation to assignment 
					//  Get the number of assignments, may be 0 or more
					int [] assignmentCount = new int[1];
					if( record.GetValue( recordIndex, assignmentCount ) )
					{
						recordIndex++;
						if( assignmentCount[0] > 0 )
						{
							int [] assignmentObjectIdValue = new int[1];
						
							for( int assignmentIndex = 0; assignmentIndex < assignmentCount[0]; assignmentIndex++ )
							{
								if( record.GetValue( recordIndex, assignmentObjectIdValue ) )
								{
									recordIndex++;
									ObjectId assignmentObjectId = new ObjectId( assignmentObjectIdValue[0] );
									
									// 
									Assignment assignment = (Assignment)SchoolFactory.GetInstance().Create( sAssignmentClassId, assignmentObjectId );
									courseOffering.AddRelation( assignment);
								}
							}
						}
					}
						
					// Get optional 1-M course id
					
					int [] courseObjectIdValue = new int[1];
					if( record.GetValue( recordIndex, courseObjectIdValue ) )
					{
						recordIndex++;
						
						ObjectId courseObjectId = new ObjectId( courseObjectIdValue[0] );
						
						// M-1 relation
						Course course = (Course)SchoolFactory.GetInstance().Create( sCourseClassId, courseObjectId );
						courseOffering.AddRelation( course);
					}
				
					return courseOffering;
				}
			}
		}
		return null;
	}
	
	public void Save()
	{
		// Step through any records and save to file.
		
		for ( int childIndex = 0; childIndex < mCourseOfferings.GetChildCount(); ++childIndex )
		{
			CourseOffering courseOffering =  mCourseOfferings.GetChild(childIndex);
			
			VariableLengthRecord record = new VariableLengthRecord();
			record.AppendValue ( courseOffering.GetQuarter().ordinal() );// enumeration saved as int
			record.AppendValue ( courseOffering.GetYear() );  
			
			
			// 1-m Relation to assignment
			Relation1M<Assignment> assignments = courseOffering.GetAssignments();
			record.AppendValue ( assignments.GetChildCount() );   // Might be 0
			
			if( assignments.GetChildCount() > 0 )
			{
				for( int assigmentIndex = 0; assigmentIndex < assignments.GetChildCount(); assigmentIndex++ )
				{
					Assignment assignment = assignments.GetChild( assigmentIndex );
					record.AppendValue ( assignment.GetObjectId().GetValue() ); 
				}
			}
			
			// Save M-1 relation by appending an 
			Course course = courseOffering.GetCourse();
			if( course != null )
			{
				record.AppendValue ( course.GetObjectId().GetValue() );  // Object id saved as an int
			}
			
			Write( courseOffering.GetObjectId(), record );
		}
	}
	
	static ClassId sCourseClassId         = new ClassId( "Course");
	static ClassId sCourseOfferingClassId = new ClassId( "CourseOffering");
	static ClassId sAssignmentClassId = new ClassId( "Assignment");
	private Relation1M<CourseOffering> mCourseOfferings;
}
