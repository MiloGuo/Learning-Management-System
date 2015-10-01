
public class AssignmentCreator extends SchoolCreator {
	public  AssignmentCreator()
	{
		super( sAssignmentId );
		mAssignments = new Relation1M<Assignment>();
	}
	@Override
	public SchoolProduct CreateNew() {
		ObjectId objectId = new ObjectId();
		Assignment assignment = new Assignment( objectId );
		//student.SetId( GetNextAvailableStudentId() );
		mAssignments.Add( assignment );
		return assignment;
	}

	@Override
	public SchoolProduct Create(ObjectId objectId) {
		// Check if it already exists
		
				for ( int childIndex = 0; childIndex < mAssignments.GetChildCount(); ++childIndex )
				{
					Assignment assignment = mAssignments.GetChild(childIndex);
					if( assignment.GetObjectId() == objectId )
					{
						mAssignments.Add( assignment );
						return assignment;
					}
				}
				
				// Not already loaded ?
				VariableLengthRecord record = Read( objectId );
				if( record != null )
				{
					String [] description = new String [1];
					if (record.GetValue(0, description)){
						float [] weighting = new float [1];
						if(record.GetValue(1, weighting))
						{
							Assignment assignment = new Assignment (objectId);
							assignment.SetDescription(description[0]);
							assignment.SetWeighting(weighting[0]);
							mAssignments.Add(assignment);
							return assignment;
							
						}
							
								
					}
				}
				return null;
					
	}

	@Override
	public void Save() {
		for (int childIndex =0; childIndex < mAssignments.GetChildCount();++childIndex){
			Assignment assignment = mAssignments.GetChild(childIndex);
			VariableLengthRecord record = new VariableLengthRecord();
			record.AppendValue(assignment.GetDescription());
			record.AppendValue(assignment.GetWeighting());
			Write(assignment.GetObjectId(),record);
		}

	}
	private static ClassId sAssignmentId = new ClassId( "Assignment");
	private Relation1M<Assignment> mAssignments;

}
