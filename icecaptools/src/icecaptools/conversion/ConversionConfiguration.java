package icecaptools.conversion;

import icecaptools.HVMProperties;
import icecaptools.IcecapIterator;
import icecaptools.IcecapTool;
import icecaptools.PropertyManager;
import icecaptools.ResourceManager;
import icecaptools.compiler.CodeDetector;
import icecaptools.compiler.IcecapCodeFormatter;
import icecaptools.compiler.IcecapSourceCodeLinker;
import icecaptools.compiler.NativeMethodDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.eclipse.jdt.core.IJavaProject;

public class ConversionConfiguration implements IcecapTool {

    private String inputFolder;
    private String inputPackage;
    protected String inputClass;

    private String entryPointClassName;
    private String entryPointMethodName;
    private String entryPointMethodSignature;
    private boolean entryPointModyfierIsStatic;

    private IcecapCodeFormatter codeFormatter;

    private ResourceManager rManager;

    public IcecapCodeFormatter getCodeFormatter() {
        return codeFormatter;
    }

    public IcecapSourceCodeLinker getSourceCodeLinker() {
        return sourceCodeLinker;
    }

    public void setCodeFormatter(IcecapCodeFormatter codeFormatter) {
        this.codeFormatter = codeFormatter;
    }

    private String inputSourceFileName;

    private PropertyManager propertyManager;
    private IcecapSourceCodeLinker sourceCodeLinker;
    private IJavaProject projectResource;
    private boolean doReportConversion;
    private CodeDetector codeDetector;
    private NativeMethodDetector nativeMethodDetector;

    public static final String DEFAULT_PACKAGE_NAME = "default";

    public ConversionConfiguration() {
        entryPointMethodName = entryPointMethodSignature = null;
        entryPointModyfierIsStatic = true;
        propertyManager = null;
        rManager = null;
        doReportConversion = false;
    }

    public String getInputFolder() {
        return inputFolder;
    }

    public void setClassPath(String inputFolder) {
        this.inputFolder = inputFolder;
    }

    public void appendToClassPath(String element) {
        this.inputFolder += File.pathSeparator + element;
    }

    public String getInputPackage() {
        return inputPackage;
    }

    public void setInputPackage(String inputPackage) {
        this.inputPackage = inputPackage;
    }

    public String getInputClass() {
        return inputClass;
    }

    public void setInputClass(String className) {
        StringTokenizer tokenizer = new StringTokenizer(className, ".");
        String previous = className;
        String current = className;

        while (tokenizer.hasMoreElements()) {
            previous = current;
            current = tokenizer.nextToken();
        }
        this.inputClass = previous;
    }

    public String getEntryPointMethodName() {
        return entryPointMethodName;
    }

    public void setEntryPointMethodName(String entryPointMethodName) {
        this.entryPointMethodName = entryPointMethodName;
    }

    public String getEntryPointMethodSignature() {
        return entryPointMethodSignature;
    }

    public void setEntryPointMethodSignature(String entryPointMethodSignature) {
        this.entryPointMethodSignature = entryPointMethodSignature;
    }

    public boolean isEntryPointModyfierIsStatic() {
        return entryPointModyfierIsStatic;
    }

    public void setEntryPointModyfierIsStatic(boolean entryPointModyfierIsStatic) {
        this.entryPointModyfierIsStatic = entryPointModyfierIsStatic;
    }

    public void setInputSourceFileName(String inputSourceFileName) throws Exception {
        this.inputSourceFileName = inputSourceFileName;
        getProperties();
    }

    public String getInputSourceFileName() {
        return inputSourceFileName;
    }

    public HVMProperties getProperties() throws Exception {
        if (propertyManager == null) {
            propertyManager = new PropertyManager();
            propertyManager.reloadLoadedProperties(inputSourceFileName);
        }
        return propertyManager;
    }

    public void setSourceCodeLinker(IcecapSourceCodeLinker sourceCodeLinker) {
        this.sourceCodeLinker = sourceCodeLinker;
    }

    public void setEntryPointClassName(String className) {
        this.entryPointClassName = className;
    }

    public String getEntryPointClassName() {
        return this.entryPointClassName;
    }

    public void setProjectResource(IJavaProject projectRessource) {
        this.projectResource = projectRessource;
    }

    public IJavaProject getProjectResource() {
        return projectResource;
    }

    public ResourceManager getResourceManager() {
        return rManager;
    }

    public void setResourceManager(ResourceManager rManager) {
        this.rManager = rManager;
    }

    public boolean reportConversion() {
        return doReportConversion;
    }

    public void setReportConversion(boolean b) {
        this.doReportConversion = b;
    }

    private static class ClassNameIterator implements IcecapIterator<String> {

        private BufferedReader reader;
        private String nextLine;

        public ClassNameIterator(BufferedReader reader) {
            this.reader = reader;
            try {
                nextLine = reader.readLine();
            } catch (IOException e) {
                nextLine = null;
            }
        }

        @Override
        public boolean hasNext() {
            return nextLine != null;
        }

        @Override
        public String next() {
            String result = nextLine;
            try {
                nextLine = reader.readLine();
            } catch (IOException e) {
                nextLine = null;
            }
            return result;
        }
    }

    private static class EmptyIteraor implements IcecapIterator<String> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public String next() {
            return null;
        }
    }

    public IcecapIterator<String> getForcedIncludes() {
        if (inputSourceFileName != null) {
            StringTokenizer tokenizer = new StringTokenizer(inputSourceFileName, File.separatorChar + "");
            StringBuffer includedClasses = new StringBuffer();

            includedClasses.append(File.separatorChar);
            while (tokenizer.hasMoreTokens()) {
                String nextSegment = tokenizer.nextToken();
                if (tokenizer.hasMoreTokens()) {
                    includedClasses.append(nextSegment);
                    includedClasses.append(File.separatorChar);
                }
            }
            includedClasses.append("forcedincludes.txt");

            File file = new File(includedClasses.toString());
            if (file.exists()) {
                if (file.canRead()) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        return new ClassNameIterator(reader);
                    } catch (FileNotFoundException e) {

                    }
                }
            }
        }
        return new EmptyIteraor();
    }

    @Override
    public CodeDetector getCodeDetector() {
        return codeDetector;
    }

    public void setCodeDetector(CodeDetector architectureDependentCodeDetector) {
       this.codeDetector = architectureDependentCodeDetector;
        
    }

    @Override
    public NativeMethodDetector getNativeMethodDetector() {
        return nativeMethodDetector;
    }

    public void setNativeMethodDetector(NativeMethodDetector nativeMethodDetector) {
        this.nativeMethodDetector = nativeMethodDetector;
    }

	public String getPropertiesFileName() {
		return propertyManager.getPropertiesFileName();
	}
}
