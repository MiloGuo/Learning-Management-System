

import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudentCreator 
{
	@Test public void testRecognizesClue() 
	{
		StudentCreator studentCreator = new StudentCreator();
		ScoreCreator scoreCreator = new ScoreCreator();
		AssignmentCreator assignmentCreator = new AssignmentCreator();
		CourseOfferingCreator courseOfferingCreator = new CourseOfferingCreator();
		CourseCreator courseCreator = new CourseCreator();
		
		ClassId goodClassId = new ClassId( "Student");
		assertTrue( studentCreator.RecognizesClue(goodClassId) );
		
		ClassId badClassId = new ClassId( "Donkey");
		assertFalse( studentCreator.RecognizesClue(badClassId) );
				
		Assignment assignment1 = (Assignment)assignmentCreator.CreateNew();
		assignment1.SetDescription( "Assignment #1");
		assignment1.SetWeighting( 0.2F);
	
		Assignment assignment2 = (Assignment)assignmentCreator.CreateNew();
		assignment1.SetDescription( "Assignment #2");
		assignment1.SetWeighting( 0.8F);
	
		Score score1 = (Score)scoreCreator.CreateNew();
		score1.SetPoints( 90.0F);
		score1.AddRelation( assignment1 );
	
		Score score2 = (Score)scoreCreator.CreateNew();
		score2.SetPoints( 80.0F);
		score2.AddRelation( assignment2 );
	
		Score score3 = (Score)scoreCreator.CreateNew();
		score3.SetPoints( 75.0F);
		score3.AddRelation( assignment1 );
	
		ObjectId scoreObjectId4;
		Score score4 = (Score)scoreCreator.CreateNew();
		score3.SetPoints( 65.0F);
		score3.AddRelation( assignment2 );
	
		Course course = (Course)courseCreator.CreateNew();
		course.SetName( "Calculus II" );
		course.SetDepartment( Course.Department.MATH);
		course.SetNumber( 2312 );
		course.SetCreditHours( 3 );
		
		CourseOffering courseOfferingA = (CourseOffering)courseOfferingCreator.CreateNew();
		courseOfferingA.SetQuarter( CourseOffering.Quarter.FALL);
		courseOfferingA.SetYear( 2010 );
		courseOfferingA.AddRelation(course);
		
		CourseOffering courseOfferingB = (CourseOffering)courseOfferingCreator.CreateNew();
		courseOfferingB.SetQuarter( CourseOffering.Quarter.WINTER);
		courseOfferingA.SetYear( 2011 );
		courseOfferingB.AddRelation(course);
	
		CourseOffering courseOfferingC = (CourseOffering)courseOfferingCreator.CreateNew();
		courseOfferingC.SetQuarter( CourseOffering.Quarter.SUMMER );
		courseOfferingA.SetYear( 2012 );
		courseOfferingC.AddRelation(course);
	

		Student studentA = (Student)studentCreator.CreateNew();
		studentA.SetName( "Bevis" );
		studentA.AddRelation( courseOfferingA );
		studentA.AddRelation( courseOfferingB );
		studentA.AddRelation( courseOfferingC );
		studentA.AddRelation( score1 );
		studentA.AddRelation( score2 );

		ObjectId studentObjectIdA = studentA.GetObjectId();

		Student studentB = (Student)studentCreator.CreateNew();
		studentB.SetName( "Butthead");
		studentB.AddRelation( courseOfferingA );
		studentB.AddRelation( courseOfferingB );
		studentB.AddRelation( courseOfferingC );
		studentB.AddRelation( score3 );
		studentB.AddRelation( score4 );

		ObjectId studentObjectIdB = studentB.GetObjectId();

	    Student recreatedStudentA = (Student)studentCreator.Create(studentObjectIdA);
	    assertTrue( recreatedStudentA.GetName().compareTo( "Bevis" ) == 0 );

	    Student recreatedStudentB = (Student)studentCreator.Create(studentObjectIdB);
	    assertTrue( recreatedStudentB.GetName().compareTo( "Butthead" ) == 0 );
	}
	
	@Test public void testMissingStudent() 
	{
		StudentCreator studentCreator = new StudentCreator();
		ObjectId nonExistantObjectId = new ObjectId( 12323 );
		SchoolProduct student = studentCreator.Create( nonExistantObjectId );
		
		assertTrue( student == null );
	}
	
	@Test public void testCreateNewStudent() 
	{
		StudentCreator studentCreator = new StudentCreator();
		Student student = (Student)studentCreator.CreateNew(  );
		assertTrue( student != null );
		student.SetName( "Test Name");
		
		ObjectId objectId = student.GetObjectId();
		Student recreatedStudent = (Student)studentCreator.Create( objectId );
		assertTrue( student.GetName().compareTo( recreatedStudent.GetName() ) == 0 );
	}
	
}