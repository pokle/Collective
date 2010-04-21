package net.pokle.collective;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import static junit.framework.Assert.assertEquals;


public class CollectiveTest {

    private Person murphy = new Person("Murphy", 5);
    private Person mona = new Person("Mona", 7);
    private Person shona = new Person("Shona", 1);

    private ArrayList<Person> persons = new ArrayList<Person>() {{
        add(murphy);
        add(mona);
        add(shona);
    }};


    @Test
    public void itemsAreExtractable() {

        ArrayList<Person> list = new ArrayList<Person>() {{
                add(new Person("Murphy", 5));
                add(new Person("Shona", 1));
        }};

        Coll<Person> coll = Coll.from(Person.class, list);

        assertEquals(coll.first().getName(), "Murphy");
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowAnExceptionOnAnEmptyCollection() {
        Coll.from(Object.class, Collections.emptyList()).first();
    }


    @Test
    public void shouldCallAllSettersInACollectionOnAll() {
        Coll<Person> coll = Coll.from(Person.class, persons);

        coll.all().setName("Clarence");

        // All items are Clarence
        assertEquals("Clarence", murphy.getName());
        assertEquals("Clarence", shona.getName());

        // Last item is obviously also Clarence
        assertEquals("Clarence", coll.all().getName());

    }

    @Test
    public void shouldReturnTheFirstItemWhenFirstIsCalled() {
        assertEquals("Murphy", Coll.from(Person.class, persons).first().getName());
    }

}
