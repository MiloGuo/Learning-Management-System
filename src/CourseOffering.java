



public class CourseOffering extends SchoolProduct {
	 private boolean mOpenForRegistration;
	 private boolean mGradingClosed;
	 private Quarter  mQuarter;
	 private int      mYear;
	 private Course   mCourse;
	 
	 private Relation1M<Assignment> mAssignments;
	 private static ClassId sClassId = new ClassId( "CourseOffering");
	 public enum Quarter{
		 WINTER,
        SPRING,
        FALL,
        SUMMER}
	public CourseOffering( ObjectId objectId ) {
		 super( sClassId, objectId);
		 this.mOpenForRegistration = false;
		 this.mGradingClosed = false;
		 this.mQuarter = Quarter.FALL;
		 this.mYear = 2000;
		 this.mCourse = null;
		 this.mAssignments = new Relation1M <Assignment>();
		
	}

	public void AddRelation( Assignment assignment ){
		if (this.mAssignments == null){
			this.mAssignments = new Relation1M <Assignment>();
		}
		mAssignments.Add(assignment);
	}

	public Relation1M< Assignment > GetAssignments(){
		return mAssignments;
	}
	public void SetQuarter( Quarter quarter){
    	this.mQuarter = quarter;
    }

    public void SetYear( int year ) {
    	this.mYear= year;
    }

    public void AddRelation( Course course ) {
    	this.mCourse = course;
    }

    public void CloseForRegistration(){
    	this.mOpenForRegistration = false;
    	
    }

    public void OpenForRegistration(){
    	this.mOpenForRegistration = true;
    	
    }

    public void MarkGradingClosed(){
    	this.mGradingClosed = true;
    	
    }

    public Quarter GetQuarter(){
    	return this.mQuarter;
    }

    public int GetYear(){
    	return this.mYear;
    }

    public boolean IsOpenToRegister(){
    	return this.mOpenForRegistration;
    }

    public boolean IsGradingClosed(){
    	return this.mGradingClosed;
    }

    public Course GetCourse() {
		return mCourse;
	}

	
}
