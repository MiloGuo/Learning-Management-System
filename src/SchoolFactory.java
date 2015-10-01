

public class SchoolFactory
{	
	public static SchoolFactory GetInstance()
	{
		if ( sInstance == null )
		{
			sInstance = new SchoolFactory();
		}
		return sInstance;
	}
	
	private SchoolFactory()
	{
		mFactoryMethods = new Relation1M<SchoolCreator>();
		
		mFactoryMethods.Add( new AssignmentCreator() );
		mFactoryMethods.Add( new CourseCreator() );
		mFactoryMethods.Add( new ScoreCreator() );
		mFactoryMethods.Add( new CourseOfferingCreator() );
		mFactoryMethods.Add( new StudentCreator() );
	}
	
	public SchoolProduct CreateNew( ClassId classId )
	{
		for ( int factoryIndex = 0;  factoryIndex < mFactoryMethods.GetChildCount(); ++factoryIndex )
		{
			SchoolCreator factoryMethod = mFactoryMethods.GetChild(factoryIndex);
			if( factoryMethod.RecognizesClue( classId ) )
			{
				return factoryMethod.CreateNew();
			}
		}
		return null;
	}
	
	public SchoolProduct Create( ClassId classId, ObjectId objectId )
	{
		for ( int factoryIndex = 0;  factoryIndex < mFactoryMethods.GetChildCount(); ++factoryIndex )
		{
			SchoolCreator factoryMethod = mFactoryMethods.GetChild(factoryIndex);
			if( factoryMethod.RecognizesClue( classId ) )
			{
				return factoryMethod.Create( objectId);
			}
		}
		return null;
	}
	
	public void Save()
	{
		for ( int factoryIndex = 0;  factoryIndex < mFactoryMethods.GetChildCount(); ++factoryIndex )
		{
			SchoolCreator factoryMethod = mFactoryMethods.GetChild(factoryIndex);
			factoryMethod.Save();
		}
	}
	
	private static Relation1M< SchoolCreator > mFactoryMethods;
	private static SchoolFactory sInstance = null;
	
}
