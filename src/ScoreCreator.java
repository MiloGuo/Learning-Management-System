
public class ScoreCreator extends SchoolCreator {
	public ScoreCreator()
	{
		super( sScoreId );
		mScores = new Relation1M<Score>();
	}
	@Override
	public SchoolProduct CreateNew() {
		ObjectId objectId = new ObjectId();
		Score score = new Score( objectId );
		//student.SetId( GetNextAvailableStudentId() );
		mScores.Add( score );
		return score;
	}

	@Override
	public SchoolProduct Create(ObjectId objectId) {
		// Check if it already exists
		
				for ( int childIndex = 0; childIndex < mScores.GetChildCount(); ++childIndex )
				{
					Score score = mScores.GetChild(childIndex);
					if( score.GetObjectId() == objectId )
					{
						mScores.Add( score );
						return score;
					}
				}
				
				// Not already loaded ?
				VariableLengthRecord record = Read( objectId );
				if( record != null )
				{  
					float [] points = new float[1];
					if (record.GetValue(0, points)){
						Score score = new Score(objectId);
						score.SetPoints(points[0]);
						int [] assignmentObjectIdValue = new int [1];
						if (record.GetValue(1, assignmentObjectIdValue)){
							
							ObjectId assignmentObjectId = new ObjectId (assignmentObjectIdValue[0]);
							
							Assignment assignment = (Assignment) SchoolFactory.GetInstance().Create(sAssignmentClassId, assignmentObjectId);
							score.AddRelation(assignment);
							
						}
						return score;
					}
					
					
					
	}
	return null;
}

	@Override
	public void Save() {
		for (int childIndex =0; childIndex < mScores.GetChildCount();++childIndex){
			Score score = mScores.GetChild(childIndex);
			VariableLengthRecord record = new VariableLengthRecord();
			record.AppendValue(score.GetPoints());
			Assignment assignment = score.GetAssignment();
			
			if(assignment != null){
				record.AppendValue(assignment.GetObjectId().GetValue());
			}
			record.AppendValue(assignment.GetWeighting());
			Write(score.GetObjectId(),record);
		}

	}

	private static ClassId sScoreId = new ClassId( "Score");
	static ClassId sAssignmentClassId = new ClassId( "Assignment");
	private Relation1M<Score> mScores;
	
	
	

}
