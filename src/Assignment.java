

public class Assignment extends SchoolProduct{
	 private String mDescription;
     private float  mWeight;
     private static ClassId sClassId = new ClassId( "Assignment");
     public Assignment( ObjectId objectId ) {
		super( sClassId, objectId);
		this.mDescription = null;
		this.mWeight = 0;
	
	}
     public void SetWeighting( float weight ){
    	 this.mWeight = weight;
     }

     public void SetDescription( String description ){
    	 this.mDescription = description;
     }

     public float GetWeighting(){
    	 return this.mWeight;
    	 
     }

     public String GetDescription(){
    	 return this.mDescription;
     }

}
