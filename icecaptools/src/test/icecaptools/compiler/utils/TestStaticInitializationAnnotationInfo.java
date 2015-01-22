package test.icecaptools.compiler.utils;

import icecaptools.compiler.utils.StaticInitializationAnnotationInfo;
import org.junit.Assert;
import org.junit.Test;

public class TestStaticInitializationAnnotationInfo {

    @Test
    public void testBasic() throws Exception {
        StaticInitializationAnnotationInfo info1 = new StaticInitializationAnnotationInfo();

        Assert.assertFalse(info1.isClassInitialized("aa"));

        info1.classIsDefinatelyInitialized("aa");
        info1.classIsDefinatelyInitialized("bb");
        info1.classIsDefinatelyInitialized("cc");

        Assert.assertTrue(info1.isClassInitialized("aa"));
        Assert.assertTrue(info1.isClassInitialized("bb"));
        Assert.assertTrue(info1.isClassInitialized("cc"));
        Assert.assertFalse(info1.isClassInitialized("cykel"));

        info1 = new StaticInitializationAnnotationInfo();

        StaticInitializationAnnotationInfo info2 = new StaticInitializationAnnotationInfo();

        info1.classIsDefinatelyInitialized("aa");
        Assert.assertTrue(info1.isClassInitialized("aa"));
        if (!info1.merge(info2)) {
            Assert.fail();
        } else {
            Assert.assertFalse(info1.isClassInitialized("aa"));
        }

        info1 = new StaticInitializationAnnotationInfo();
        info2 = new StaticInitializationAnnotationInfo();

        if (info1.merge(info2)) {
            Assert.fail();
        }

        info1 = new StaticInitializationAnnotationInfo();
        info1.classIsDefinatelyInitialized("aa");
        info2 = new StaticInitializationAnnotationInfo();
        info2.classIsDefinatelyInitialized("aa");

        if (info1.merge(info2)) {
            Assert.fail();
        }

        info1 = new StaticInitializationAnnotationInfo();
        info2 = new StaticInitializationAnnotationInfo();
        info2.classIsDefinatelyInitialized("aa");

        if (!info1.merge(info2)) {
            Assert.fail();
        } else {
            Assert.assertFalse(info1.isClassInitialized("aa"));
        }
        
        info1 = new StaticInitializationAnnotationInfo();
        info1.classIsDefinatelyInitialized("aa");
        info2 = new StaticInitializationAnnotationInfo();
        info2.classIsDefinatelyInitialized("aa");
        info1.classIsDefinatelyInitialized("bb");
        
        if (!info1.merge(info2)) {
            Assert.fail();
        } else {
            Assert.assertTrue(info1.isClassInitialized("aa"));
            Assert.assertFalse(info1.isClassInitialized("bb"));
        }
        
        info1 = new StaticInitializationAnnotationInfo();
        info1.classIsDefinatelyInitialized("aa");
        info2 = info1.copy();
        info1.classIsDefinatelyInitialized("bb");
        
        Assert.assertTrue(info1.isClassInitialized("aa"));
        Assert.assertTrue(info1.isClassInitialized("bb"));

        Assert.assertTrue(info2.isClassInitialized("aa"));
        Assert.assertFalse(info2.isClassInitialized("bb"));

    }
}
