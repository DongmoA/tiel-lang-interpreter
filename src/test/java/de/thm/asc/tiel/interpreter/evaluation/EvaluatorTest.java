package de.thm.asc.tiel.interpreter.evaluation;

import de.thm.asc.tiel.interpreter.lexical.Scanner;
import de.thm.asc.tiel.interpreter.semantic.Binder;
import de.thm.asc.tiel.interpreter.syntax.Parser;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvaluatorTest {

    //<< 02-design, unit-test, Klasse EvaluatorTest
    @Test
    void evaluatesArithmeticAssignmentAndWhileLoop() {
        var output = evaluate("""
                var x = 1;
                while x < 5 { x = x + 1; }
                print(x); print(3 + 4);""");
        assertEquals("""
                5
                7
                """, output);
    }
    //>>

    @Test
    void evaluatesClosures() {
        var output = evaluate("""
                fun makeAdder(a) {
                    fun add(b) {
                        return a + b;
                    }
                    return add;
                }

                var addTwo = makeAdder(2);
                print(addTwo(5));
                """);

        assertEquals("""
                7
                """, output);
    }

    @Test
    void shortCircuitsLogicalExpressions() {
        var output = evaluate("""
                var x = 0;
                true or (x = 1);
                false and (x = 2);
                print(x);
                """);

        assertEquals("""
                0
                """, output);
    }

    @Test
    void evaluatesArrayLiteral() {
        var output = evaluate("""
        var a = [1, 2, 3];
        print(a);
    """);

        assertEquals("""
        [1, 2, 3]
        """, output);
    }


    @Test
    void evaluatesArrayInExpressions() {
        var output = evaluate("""
        var a = [1,2,3];
        print(a[0] + a[2]);
    """);

        assertEquals("""
        4
        """, output);
    }


    @Test
    void evaluatesNestedArrayAssignment() {
        var output = evaluate("""
        var m = [[1,2],[3,4]];
        m[0][1] = 42;
        print(m[0][1]);
    """);

        assertEquals("""
        42
        """, output);
    }

    @Test
    void evaluatesClassInstancesIndependently() {
        var output = evaluate("""
            class Counter {
                Counter(start) {
                    this.value = start;
                }
                inc() {
                    this.value = this.value + 1;
                }
            }

            var a = Counter(10);
            var b = Counter(3);
            a.inc();
            print(a.value);
            print(b.value);
            """);

        assertEquals("""
            11
            3
            """, output);
    }


    private static String evaluate(String source) {
        var outputStream = new ByteArrayOutputStream();

        var ast = new Parser(new Scanner(source).scan()).parse();
        var evaluator = new Evaluator(Globals.initEnv(new PrintStream(outputStream)));

        new Binder(evaluator).resolve(ast);
        evaluator.interpret(ast);

        return outputStream.toString().replace("\r\n", "\n");
    }
}
