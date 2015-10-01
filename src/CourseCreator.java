


public class CourseCreator extends SchoolCreator {
	//private static ClassId sCourseId;
	public CourseCreator()
	{
		super( sCourseId );
		mCourses = new Relation1M<Course>();
	}
	@Override
	public SchoolProduct CreateNew() {
		ObjectId objectId = new ObjectId();
		Course course = new Course( objectId );
		
		mCourses.Add( course );
		return course;
	}

	@Override
	public SchoolProduct Create(ObjectId objectId) {
		// Check if it already exists
		
				for ( int childIndex = 0; childIndex < mCourses.GetChildCount(); ++childIndex )
				{
					Course course = mCourses.GetChild(childIndex);
					if( course.GetObjectId() == objectId )
					{
						mCourses.Add( course );
						return course;
					}
				}
				
				// Not already loaded ?
				VariableLengthRecord record = Read( objectId );
				if( record != null )
				{
					String [] name = new String [1];
					if (record.GetValue(0, name));{
						Course course = new Course(objectId);
						String [] description = new String [1];
						if(record.GetValue(1, description)){
							course.SetDescription(description[0]);
							int [] departmentOrdinal = new int[1];
							if (record.GetValue(2, description)){
								Course.Department department = 	Course.Department.values()[departmentOrdinal[0]];
								course.SetDepartment(department);
								int [] number = new int [1];
								if(record.GetValue(3, number)){
									course.SetNumber(number[0]);
									int [] credits = new int [1];
									if(record.GetValue(4, credits)){
										course.SetCreditHours(credits[0]);
									
						}
									return course;
					}
					
						
						
						
						
						}
						
						
					}
					
				}
			}
return null;	
}

	@Override
	public void Save() {
		for (int childIndex =0; childIndex < mCourses.GetChildCount();++childIndex){
			Course course = mCourses.GetChild(childIndex);
			VariableLengthRecord record = new VariableLengthRecord();
			record.AppendValue(course.GetName());
			record.AppendValue(course.GetDescription());
			record.AppendValue(course.GetDepartment().ordinal());
			record.AppendValue(course.GetNumber());
			record.AppendValue(course.GetCredits());
			Write(course.GetObjectId(),record);
		}

	}

	private static ClassId sCourseId = new ClassId( "Course");
	private Relation1M<Course> mCourses;
}


