package org.dalingtao.re;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class NfaRETest {

    void assertState(String pattern, String s, boolean val) {
        NfaRE re = new REParser().parse(pattern);
        NfaMatcher matcher = re.matcher();
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
        assertState("[a-cd]*", "abcca", true);
        assertState("[^abc]*", "ddsx", true);
        assertState("[^abc]*", "addsx", false);
        assertState("[^a-c]*", "addsx", false);
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

    boolean testMerge(String s, String... pattern) {
        List<NfaRE> res = new ArrayList<>();
        for (String p : pattern) {
            res.add(new REParser().parse(p));
        }

        return NfaRE.merge(res.toArray(new NfaRE[0])).matcher().match(s);
    }

    @Test
    public void test7() {
        Assert.assertTrue(testMerge("abc", "a", "b", "cc", "(a|b|c)bc", "cc"));
        Assert.assertTrue(testMerge("abc$__.", ".+\\.", "b", "cc", "(a|b|c)bc", "cc"));
        Assert.assertFalse(testMerge("abc$__.", ".+x", "b", "cc", "(a|b|c)bc", "cc"));
    }

    @Test
    public void test8() {
        String number = "-?(0|[1-9][0-9]*)";
        assertState(number, "-0", true);
        assertState(number, "-01", false);
        assertState(number, "-1505235051", true);
        assertState(number, "1505235051", true);
        assertState(number, "5", true);
    }

    private boolean fastTest(String a, String b) {
        NfaRE re = new REParser().parse(a);
        NfaMatcher matcher = re.matcher();
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
        Random random = new Random();
        String s = randomString(30000);
        String t = s;
        fastTest(s, t);
    }
}