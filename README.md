Goals
====

To make it easier to manipulate java Collections containing Beans. Do this by reducing the lines of code required to extract search and manipulate beans in collections.

I want to be able to write Java code that looks like:

	// Calls getName() on the first item in the persons collection
	Coll.from(persons).first().getName()  
	
	// Find the first Person called Jasmine, and get their age
	Coll.from(persons).findFirst("name", equalTo("Jasmine")).getAge();
	
	// Set all names
	Coll.from(persons).all().setName("Shona");
	
Inspirations are jQuery and Underscore.js