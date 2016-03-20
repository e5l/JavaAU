package org.spbau.functools;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function1Test {

    @Test
    public void apply() throws Exception {

        Base base = new Base();
        Derived derived = new Derived();
        NotDerived notDerived = new NotDerived();

        Function1<Base, Boolean> first = a -> true;
        Function1<Derived, Boolean> second = a -> true;

        // should compile
        first.apply(base);
        first.apply(derived);

        second.apply(derived);

        /*
        // shouldn't compile
        first.apply(notDerived);
        second.apply(base);
        */

        Function1<Integer, Integer> inc = a -> a + 1;

        assertEquals(new Integer(1), inc.apply(0));
        assertNotEquals(new Integer(10), inc.apply(0));
    }

    @Test
    public void compose() throws Exception {

        Function1<Base, Base> first = a -> a;
        Function1<Derived, Derived> second = a -> a;
        Function1<Base, Derived> third = a -> new Derived();
        Function1<Derived, Base> fourth = a -> a;

        // should compile
        first.compose(first); // Base -> Base -> Base
        first.compose(third); // Base -> Base -> Derived

        second.compose(first); // Derived -> Derived => Base -> Base
        second.compose(second); // Derived -> Derived -> Derived
        second.compose(third); // Derived -> Derived => Base -> Derived
        second.compose(fourth); //Derived -> Derived -> Base

        third.compose(first); // Base -> Derived => Base -> Base
        third.compose(second);
        third.compose(fourth);
        third.compose(third);

        fourth.compose(first);
        fourth.compose(third);

        /*
        // shouldn't compile
        first.compose(second); // Base -> Base !=> Derived -> Derived
        first.compose(fourth); // Base -> Base !=> Derived -> Base
        fourth.compose(second);
        fourth.compose(fourth);
        */

        Function1<Integer, Integer> inc = a -> a + 1;
        Function1<Integer, Integer> sqr = a -> a * a;

        assertEquals(new Integer(10), sqr.compose(inc).apply(3));
    }

    class Base {}
    class Derived extends Base {}
    class NotDerived {}
}
