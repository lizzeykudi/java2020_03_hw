package TestFramework.runner;

import TestFramework.exampleTestClass.TestTest;

public class TestFrameworkCore {

    public static void main(String... args) {
        Result result = new TestFrameworkCore().run(TestTest.class);
        System.out.println(result);
    }

    public static Result run(Class clazz) {
        return new TestFrameworkCore().run(new Runner(clazz));
    }

    public Result run(Runner runner) {
        return runner.run();
    }
}
