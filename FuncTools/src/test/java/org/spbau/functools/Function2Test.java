package org.spbau.functools;

import org.junit.Test;

import static org.junit.Assert.*;

public class Function2Test {

    @Test
    public void apply() throws Exception {
        Base base = new Base();
        Derived derived = new Derived();

        Function2<Base, Base, Base> first = (a, b) -> a;
        Function2<Derived, Derived, Derived> second = (a, b) -> b;

        // should compile
        first.apply(base, base);
        first.apply(derived, derived);
        second.apply(derived, derived);

        /*
        // shouldn't apply
        NotDerived notDerived = new NotDerived();
        first.apply(notDerived, notDerived);
        second.apply(base, base);
        */

        Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;

        assertEquals(new Integer(10), sum.apply(6, 4));
        assertEquals(new Integer(10), sum.apply(4, 6));
    }

    @Test
    public void compose() throws Exception {
        Function1<Base, Base> first = a -> a;
        Function1<Derived, Derived> second = a -> a;

        Function2<Base, Base, Base> third = (a, b) -> a;
        Function2<Derived, Derived, Derived> fourth = (a, b) -> b;

        // should compile
        third.compose(first);
        fourth.compose(first);
        fourth.compose(second);

        /*
        // shouldn't compile
        third.compose(second);
         */

        Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
        Function1<Integer, Integer> sqr = a -> a * a;

        assertEquals(new Integer(25), sum.compose(sqr).apply(3, 2));
    }

    @Test
    public void bind1() throws Exception {
        Base base = new Base();
        Derived derived = new Derived();

        Function2<Base, Base, Base> first = (a, b) -> a;
        Function2<Derived, Derived, Derived> second = (a, b) -> b;

        // should compile
        first.bind1(base);
        first.bind1(derived);
        second.bind1(derived);

        /*
        // shouldn't compile
        NotDerived notDerived = new NotDerived();
        first.bind1(notDerived);
        second.bind1(base);
        */

        Function2<Integer, Integer, Integer> first_arg = (a, b) -> a;
        Function1<Integer, Integer> five = first_arg.bind1(5);

        assertEquals(new Integer(5), five.apply(10));
        assertEquals(new Integer(5), five.apply(5));
        assertEquals(new Integer(5), five.apply(-10));
    }

    @Test
    public void bind2() throws Exception {
        Base base = new Base();
        Derived derived = new Derived();

        Function2<Base, Base, Base> first = (a, b) -> a;
        Function2<Derived, Derived, Derived> second = (a, b) -> b;

        // should compile
        first.bind2(base);
        first.bind2(derived);
        second.bind2(derived);

        /*
        // shouldn't compile
        NotDerived notDerived = new NotDerived();
        first.bind2(notDerived);
        second.bind2(base);
        */


        Function2<Integer, Integer, Integer> second_arg = (a, b) -> b;
        Function1<Integer, Integer> five = second_arg.bind2(5);

        assertEquals(new Integer(5), five.apply(10));
        assertEquals(new Integer(5), five.apply(5));
        assertEquals(new Integer(5), five.apply(-10));
    }

    @Test
    public void curry() throws Exception {
        Base base = new Base();
        Derived derived = new Derived();

        Function2<Base, Base, Base> first = (a, b) -> a;

        first.curry().apply(base).apply(base);
        first.curry().apply(derived).apply(derived);


        Function2<Integer, Integer, Integer> bsum = (a, b) -> a + 2 * b;

        assertEquals(new Integer(10), bsum.curry().apply(4).apply(3));
        assertEquals(new Integer(11), bsum.curry().apply(3).apply(4));
    }

    class Base {}
    class Derived extends Base {}
    class NotDerived {}
}