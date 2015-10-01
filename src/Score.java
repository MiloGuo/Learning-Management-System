

public class Score extends SchoolProduct{
	private float mPoints;   // 0 ¨C 100
    private Assignment mAssignment;
    private static ClassId sClassId = new ClassId( "Score");
	public Score( ObjectId objectId ) {
		super( sClassId, objectId);
		this.mPoints = 0;
		this.mAssignment = null;
			 
		 };

	     public void SetPoints( float points ) {
	    	 this.mPoints = points;
	     }

	     public void AddRelation( Assignment assignment) { 
	    	 this.mAssignment = assignment;
	     }

	     public float GetWeightedPoints(){
	    	 return this.mAssignment.GetWeighting()*this.mPoints;
	     }
	     public Assignment GetAssignment(){
	    	 return this.mAssignment;
	     }

	     public float GetPoints() {
	    	 return this.mPoints;
	     };

	 

	     

}
