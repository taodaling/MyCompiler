package org.dalingtao.re;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class DfaRETest {

    void assertState(String pattern, String s, boolean val) {
        var re = new REParser().parse(pattern).toDfa(256);
        var matcher = re.matcher();
        for (char c : s.toCharArray()) {
            matcher.consume(c);
        }
        Assert.assertEquals(val, matcher.match());
    }

    @Test
    public void test1() {
        assertState(".", "1", true);
    }

    @Test
    public void test2() {
        assertState("asd|.x?", "1", true);
        assertState("asd|.x?", "1x", true);
        assertState("asd|.x?", "asd", true);
        assertState("asd|.x", "x", false);
    }

    @Test
    public void test3() {
        assertState("(xsa|x)+", "xsaxxsaxx", true);
        assertState("(xsa|x)+", "xsaxxaxx", false);
    }

    @Test
    public void test4() {
        assertState("[abc]*", "abcca", true);
        assertState("[^abc]*", "ddsx", true);
        assertState("[^abc]*", "addsx", false);
    }

    @Test
    public void test5() {
        assertState(".?", "", true);
    }

    @Test
    public void test6() {
        assertState("(a|b)c", "ac", true);
        assertState("(a|b)c", "bc", true);
    }


    @Test
    public void test7() {
        assertState("//[^\\n]*", "//ac", true);
        assertState("//[^\n]*", "//bc", true);
    }

    private boolean fastTest(String a, String b) {
        var re = new REParser().parse(a).toDfa(256);
        var matcher = re.matcher();
        for (char c : b.toCharArray()) {
            matcher.consume(c);
        }
        return matcher.match();
    }

    @Test
    public void _test1() {
        Assert.assertTrue(fastTest("a", "a"));
    }

    @Test
    public void _test3() {
        Assert.assertTrue(fastTest(".", "a"));
    }

    @Test(expected = ParseCompileException.class)
    public void _test4() {
        Assert.assertTrue(fastTest("?", ""));
    }

    @Test
    public void _test5() {
        Assert.assertTrue(fastTest("88?", "88"));
    }

    @Test
    public void _test6() {
        Assert.assertTrue(fastTest("88?", "8"));
    }

    @Test
    public void _test7() {
        Assert.assertTrue(fastTest("ab*\\(", "abbb("));
    }

    @Test
    public void _test8() {
        Assert.assertTrue(fastTest("((ab|c)*1..)", "ccc135"));
    }

    @Test
    public void _test9() {
        Assert.assertTrue(fastTest("((ab|c)*(1.).)", "ab123"));
    }

    @Test
    public void _test10() {
        Assert.assertTrue(fastTest("a|b|c", "b"));
    }

    @Test
    public void _test11() {
        Assert.assertFalse(fastTest("a|b|c", "bc"));
    }

    @Test
    public void _test12() {
        Assert.assertTrue(fastTest("a|bc|c", "bc"));
    }

    @Test
    public void _test13() {
        Assert.assertFalse(fastTest("a|bc|c", "cb"));
    }

    @Test
    public void _test14() {
        Assert.assertFalse(fastTest("a|bc|c", "cb"));
    }

    @Test
    public void _test16() {
        Assert.assertFalse(fastTest("\\.123", "1123"));
    }


    String randomString(int n) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append((char) random.nextInt('z' - 'a' + 1));
        }
        return sb.toString();
    }

    @Test
    public void hugeTest() {
        String s = randomString(1000);
        String t = randomString(10000000);
        fastTest(s, t);
    }
}