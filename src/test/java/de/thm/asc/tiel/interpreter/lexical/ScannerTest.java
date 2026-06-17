package de.thm.asc.tiel.interpreter.lexical;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScannerTest {

    @Test
    void scansVariableDeclarationAndFunctionCall() {
        var tokens = new Scanner("""
                var answer = 12.5;
                print(answer);
                """).scan();

        assertTokenTypes(tokens, List.of(
                TokenType.VAR,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.NUMBER,
                TokenType.SEMICOLON,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.IDENTIFIER,
                TokenType.RIGHT_PAREN,
                TokenType.SEMICOLON,
                TokenType.EOF
        ));

        assertEquals("answer", tokens.get(1).value());
        assertEquals(12.5, tokens.get(3).value());
    }

    @Test
    void skipsLineComments() {
        var tokens = new Scanner("""
                var x = 1; // ignore this
                return x;
                """).scan();

        assertTokenTypes(tokens, List.of(
                TokenType.VAR,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.NUMBER,
                TokenType.SEMICOLON,
                TokenType.RETURN,
                TokenType.IDENTIFIER,
                TokenType.SEMICOLON,
                TokenType.EOF
        ));
    }

    @Test
    void scansMultilineString() {
        var tokens = new Scanner("""
                var message = "hello
                world";
                print(message);
                """).scan();

        assertTokenTypes(tokens, List.of(
                TokenType.VAR,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.STRING,
                TokenType.SEMICOLON,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.IDENTIFIER,
                TokenType.RIGHT_PAREN,
                TokenType.SEMICOLON,
                TokenType.EOF
        ));

        assertEquals("hello\nworld", tokens.get(3).value());
    }

    @Test
    void scansSimpleFunction() {
        var tokens = new Scanner("""
                fun test(flag) {
                    if not flag and true or false {
                        return 1 != 2;
                    }
                }
                """).scan();

        assertTokenTypes(tokens, List.of(
                TokenType.FUN,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.IDENTIFIER,
                TokenType.RIGHT_PAREN,
                TokenType.LEFT_BRACE,
                TokenType.IF,
                TokenType.NOT,
                TokenType.IDENTIFIER,
                TokenType.AND,
                TokenType.TRUE,
                TokenType.OR,
                TokenType.FALSE,
                TokenType.LEFT_BRACE,
                TokenType.RETURN,
                TokenType.NUMBER,
                TokenType.NOT_EQUAL,
                TokenType.NUMBER,
                TokenType.SEMICOLON,
                TokenType.RIGHT_BRACE,
                TokenType.RIGHT_BRACE,
                TokenType.EOF
        ));
    }


    //-------------- Tests for Array feature -----------------


    @Test
    void scansbrackets() {
        var tokens = new Scanner("""
            var numbers = [10, 20, 30];
            print(numbers[0]);
            """).scan();

        assertTokenTypes(tokens, List.of(
                TokenType.VAR,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.LEFT_BRACKET,
                TokenType.NUMBER,
                TokenType.COMMA,
                TokenType.NUMBER,
                TokenType.COMMA,
                TokenType.NUMBER,
                TokenType.RIGHT_BRACKET,
                TokenType.SEMICOLON,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.IDENTIFIER,
                TokenType.LEFT_BRACKET,
                TokenType.NUMBER,
                TokenType.RIGHT_BRACKET,
                TokenType.RIGHT_PAREN,
                TokenType.SEMICOLON,
                TokenType.EOF
        ));
    }



    @Test
    void scansArrayAssignmentWithStringValues() {
        var tokens = new Scanner("""
                var names = ["alice", "bob"];
                names[1] = "carol";
                """).scan();

        assertTokenTypes(tokens, List.of(
                TokenType.VAR,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.LEFT_BRACKET,
                TokenType.STRING,
                TokenType.COMMA,
                TokenType.STRING,
                TokenType.RIGHT_BRACKET,
                TokenType.SEMICOLON,
                TokenType.IDENTIFIER,
                TokenType.LEFT_BRACKET,
                TokenType.NUMBER,
                TokenType.RIGHT_BRACKET,
                TokenType.EQUAL,
                TokenType.STRING,
                TokenType.SEMICOLON,
                TokenType.EOF
        ));

        assertEquals("names", tokens.get(1).value());
        assertEquals("alice", tokens.get(4).value());
        assertEquals("bob", tokens.get(6).value());
    }


    //-------------- Tests for Class feature -----------------

    @Test
    void scansClassMemberAccess() {
        var tokens = new Scanner("""
                var a = Counter("A");
                a.printState();
                """).scan();

        assertTokenTypes(tokens, List.of(
                TokenType.VAR,
                TokenType.IDENTIFIER,   // a
                TokenType.EQUAL,
                TokenType.IDENTIFIER,   // Counter
                TokenType.LEFT_PAREN,
                TokenType.STRING,       // "A"
                TokenType.RIGHT_PAREN,
                TokenType.SEMICOLON,
                TokenType.IDENTIFIER,   // a
                TokenType.DOT,
                TokenType.IDENTIFIER,   // printState
                TokenType.LEFT_PAREN,
                TokenType.RIGHT_PAREN,
                TokenType.SEMICOLON,
                TokenType.EOF
        ));

        assertEquals("a", tokens.get(1).lexeme());
        assertEquals("printState", tokens.get(10).lexeme());
    }

    @Test
    void scansClassDeclaration() {
        var tokens = new Scanner("""
               class Counter {
             \s
                    Counter(name, start) {
                        this.name = name;
                        this.value = start;
                        this.history = [start];
                    }
                   \s
                     printState() {
                         print(this.name);
                         print(this.value);
                         print(this.history[0]);
                     }
               }
              \s""").scan();

        assertTokenTypes(tokens, List.of(
                TokenType.CLASS,
                TokenType.IDENTIFIER,
                TokenType.LEFT_BRACE,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.IDENTIFIER,
                TokenType.COMMA,
                TokenType.IDENTIFIER,
                TokenType.RIGHT_PAREN,
                TokenType.LEFT_BRACE,
                TokenType.THIS,
                TokenType.DOT,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.IDENTIFIER,
                TokenType.SEMICOLON,
                TokenType.THIS,
                TokenType.DOT,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.IDENTIFIER,
                TokenType.SEMICOLON,
                TokenType.THIS,
                TokenType.DOT,
                TokenType.IDENTIFIER,
                TokenType.EQUAL,
                TokenType.LEFT_BRACKET,
                TokenType.IDENTIFIER,
                TokenType.RIGHT_BRACKET,
                TokenType.SEMICOLON,
                TokenType.RIGHT_BRACE,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.RIGHT_PAREN,
                TokenType.LEFT_BRACE,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.THIS,
                TokenType.DOT,
                TokenType.IDENTIFIER,
                TokenType.RIGHT_PAREN,
                TokenType.SEMICOLON,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.THIS,
                TokenType.DOT,
                TokenType.IDENTIFIER,
                TokenType.RIGHT_PAREN,
                TokenType.SEMICOLON,
                TokenType.IDENTIFIER,
                TokenType.LEFT_PAREN,
                TokenType.THIS,
                TokenType.DOT,
                TokenType.IDENTIFIER,
                TokenType.LEFT_BRACKET, // [
                TokenType.NUMBER, // 0
                TokenType.RIGHT_BRACKET, // ]
                TokenType.RIGHT_PAREN, // )
                TokenType.SEMICOLON,

                TokenType.RIGHT_BRACE,
                TokenType.RIGHT_BRACE,
                TokenType.EOF
                ));

    }


    private static void assertTokenTypes(List<Token> tokens, List<TokenType> expectedTypes) {
        assertEquals(expectedTypes.size(), tokens.size());

        for (var i = 0; i < expectedTypes.size(); i++) {
            assertEquals(expectedTypes.get(i), tokens.get(i).type(), "token index " + i);
        }
    }
}
