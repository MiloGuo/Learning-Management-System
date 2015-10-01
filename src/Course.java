




public class Course extends SchoolProduct{
	 private String   mName;
	 private String  mDescription;
	 private Department mDepartment;
	 private int  mNumber;
	 private int  mCreditHours;
	 //private Relation1M<CourseOffering> mCourseOfferings;
	 private static ClassId sClassId = new ClassId( "Course");
	 public enum Department
	{
		  underdefine,
          CIS,
          ENGLISH,
          MATH
     }
	public Course( ObjectId objectId ) {
		super( sClassId, objectId);
		 this.mName = null;
		 this.mDescription = null;
		 this.mCreditHours = 0;
		 this.mDepartment= Department.underdefine;
		 this.mNumber = 0;
		// mCourseOfferings  = new Relation1M<CourseOffering>();
		 
	 }

    public void SetDepartment( Department department ) {
   	 this.mDepartment = department;
    }

    public void SetNumber(int number) {
   	 this.mNumber = number;
    }

    public void SetName( String name ) {
   	 this.mName = name;
    }

    public void SetCreditHours(  int credits ) {
   	 this.mCreditHours = credits;
    }

    public void SetDescription( String description ) {
   	 this.mDescription = description;
    }

    public int GetNumber(){
   	 return this.mNumber;
    }

    public String GetName(){
   	 return this.mName;
    }

    public Department GetDepartment(){
   	 return this.mDepartment;
    }

    public int GetCredits(){
   	 return this.mCreditHours;
    }

    public String GetDescription(){
   	 return this.mDescription;
    }
    
    


}
