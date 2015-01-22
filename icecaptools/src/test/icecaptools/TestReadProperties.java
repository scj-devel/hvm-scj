package test.icecaptools;

import icecaptools.HVMProperties;
import icecaptools.conversion.ConversionConfiguration;

import java.io.File;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import org.eclipse.core.runtime.Path;
import org.junit.Assert;

public class TestReadProperties extends TestCase {

    public void testReadBasicProperties() throws Exception {

        String inputFolder = "";
        String inputPackage = "test.icecaptools.minitests.properties";
        String inputClass = "HelloWorld";

        ConversionConfiguration config = new ConversionConfiguration();
        
        File cwd = new File(".");
        
        Path sourceFilePath = new Path(cwd.getAbsolutePath());
        
        StringBuffer root = new StringBuffer(sourceFilePath.toOSString());
        
        root.append(File.separatorChar);
        root.append("src");
        
        StringTokenizer tokenizer = new StringTokenizer(inputPackage, ".");
        while (tokenizer.hasMoreElements())
        {
            root.append(File.separatorChar);
            root.append(tokenizer.nextElement());
        }
        
        root.append(File.separatorChar);
        root.append(inputClass);
        root.append(".java");
        
        config.setInputSourceFileName(root.toString());
        config.setClassPath(inputFolder);
        config.setInputPackage(inputPackage);
        config.setInputClass(inputClass);

        HVMProperties icecapProps = config.getProperties();
        Assert.assertNotNull(icecapProps);
        
        Assert.assertEquals("#pragma memory=dataseg(HVMCONST)", icecapProps.getProgmemStart());
        Assert.assertEquals("#pragma memory=default", icecapProps.getProgmemEnd());
        

    }
}
