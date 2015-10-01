

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class StudentCreator  extends SchoolCreator
{
	public StudentCreator()
	{
		super( sStudentClassId );
		mStudents = new Relation1M<Student>();
	}
	
	// Create a new student 
	public SchoolProduct CreateNew()
	{
		ObjectId objectId = new ObjectId();
		Student student = new Student( objectId );
		student.SetId( GetNextAvailableStudentId() );
		mStudents.Add( student );
		return student;
	}
	
	// Create an existing student 
	public SchoolProduct Create( ObjectId objectId )
	{
		
		// Check if it already exists
		
		for ( int childIndex = 0; childIndex < mStudents.GetChildCount(); ++childIndex )
		{
			Student student = mStudents.GetChild(childIndex);
			if( student.GetObjectId() == objectId )
			{
				mStudents.Add( student );
				return student;
			}
		}
		
		// Not already loaded ?
		VariableLengthRecord record = Read( objectId );
		if( record != null )
		{
			Student student = new Student( objectId );
			
			String [] name = new String[1];
			if( record.GetValue( 0, name ) )
			{
				student.SetName( name[0] );
				
				String [] id = new String[1];
				if( record.GetValue( 1, id ) )
				{
					student.SetId( id[0] );
				}
				
				int recordIndex =  2;
				
				// 1-M relation to scores;  Get the number of scores, may be 0 or more
				int [] scoreCount = new int[1];
				if( record.GetValue( recordIndex, scoreCount ) )
				{
					recordIndex++;
					if( scoreCount[0] > 0 )
					{
						int [] scoreObjectIdValue = new int[1];
					
						for( int scoreIndex = 0; scoreIndex < scoreCount[0]; scoreIndex++ )
						{
							if( record.GetValue( recordIndex, scoreObjectIdValue ) )
							{
								recordIndex++;
								ObjectId scoreObjectId = new ObjectId( scoreObjectIdValue[0] );
								
								Score score = (Score)SchoolFactory.GetInstance().Create( sScoreClassId, scoreObjectId );
								student.AddRelation( score);
							}
						}
					}
				}
				
				
				// 1-M relation to courseOfferings
				
				int [] courseOfferingCount = new int[1];
				if( record.GetValue( recordIndex, courseOfferingCount ) )
				{
					recordIndex++;
					if( courseOfferingCount[0] > 0 )
					{
						int [] courseOfferingObjectIdValue = new int[1];
					
						for( int scoreIndex = 0; scoreIndex < courseOfferingCount[0]; scoreIndex++ )
						{
							if( record.GetValue( recordIndex, courseOfferingObjectIdValue ) )
							{
								recordIndex++;
								ObjectId courseOfferingObjectId = new ObjectId( courseOfferingObjectIdValue[0] );
								
								CourseOffering courseOffering = (CourseOffering)SchoolFactory.GetInstance().Create( sCourseOfferingClassId, courseOfferingObjectId );
								student.AddRelation( courseOffering);
							}
						}
					}
				}
				
				mStudents.Add( student );
				return student;
			}
		}
		
		return null;  // No such student
	}
	
	public void Save()
	{
		// Step through any previously loaded records and save to file.
		for ( int childIndex = 0; childIndex < mStudents.GetChildCount(); ++childIndex )
		{
			Student student =  mStudents.GetChild(childIndex);
			
			VariableLengthRecord record = new VariableLengthRecord();
			record.AppendValue ( student.GetName() ); 
			record.AppendValue ( student.GetId() ); 

			// 1-M relation to scores;  Get the number of scores, may be 0 or more
		
			Relation1M<Score> scores = student.GetScores();
			record.AppendValue ( scores.GetChildCount() );   // Might be 0
			
			if( scores.GetChildCount() > 0 )
			{
				for( int scoreIndex = 0; scoreIndex < scores.GetChildCount(); scoreIndex++ )
				{
					Score score = scores.GetChild( scoreIndex );
					record.AppendValue ( score.GetObjectId().GetValue() ); 
				}
			}
			
			// 1-M relation to CourseOfferings Get the number of courseOfferings, may be 0 or more
		
			Relation1M<CourseOffering> courseOfferings = student.GetCourseOfferings();
			record.AppendValue ( courseOfferings.GetChildCount() );   // Might be 0
			
			if( courseOfferings.GetChildCount() > 0 )
			{
				for( int courseOfferingIndex = 0; courseOfferingIndex < courseOfferings.GetChildCount(); courseOfferingIndex++ )
				{
					CourseOffering courseOffering = courseOfferings.GetChild( courseOfferingIndex );
					record.AppendValue ( courseOffering.GetObjectId().GetValue() ); 
				}
			}
			Write( student.GetObjectId(), record );
		}
	}
		
	private String GetNextAvailableStudentId()
	{
		int nextAvailableStudentId = 101;  // Default value
		try 
		{
			File file = new File("C://Persistence//StudentIds.txt");
			if (file.exists() )
			{
				FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader); 
				String inputLine = bufferedReader.readLine();
				if( inputLine != null )
				{
					nextAvailableStudentId = Integer.parseInt(inputLine); 
					nextAvailableStudentId++;
				}
				bufferedReader.close();
			}
				
			File folder = new File("C://Persistence");
			if( !folder.exists() )
			{
				folder.mkdir();
			}

			FileWriter fileWriter = new FileWriter(file);
			String outputLine = Integer.toString(nextAvailableStudentId);
			fileWriter.write(outputLine);
			fileWriter.close();
		} 
		catch (Exception e) 
		{
			// Just ignore for now
		}
		String studentId = "2010-" + Integer.toString(nextAvailableStudentId);
		return studentId; 
	}


	private static ClassId sStudentClassId        = new ClassId( "Student");
	private static ClassId sCourseOfferingClassId = new ClassId( "CourseOffering");
	private static ClassId sScoreClassId          = new ClassId( "Score");

	
	private Relation1M<Student> mStudents;
}
