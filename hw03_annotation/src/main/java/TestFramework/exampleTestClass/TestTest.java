package TestFramework.exampleTestClass;

import TestFramework.annotations.After;
import TestFramework.annotations.Before;
import TestFramework.annotations.Test;

public class TestTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void test1() {

    }

    @Test
    public void test2() {

        throw new RuntimeException();
    }

    @Test
    public void test3() {
    }

    @After
    public void tearDown() throws Exception {
    }
}