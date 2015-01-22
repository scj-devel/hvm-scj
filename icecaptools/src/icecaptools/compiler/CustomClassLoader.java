package icecaptools.compiler;

import java.io.FileInputStream;
import java.util.HashSet;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;


public class CustomClassLoader {
    private final boolean debug = true;
    private String path;
    private HashSet<JavaClass> classSet;

    public CustomClassLoader(String path) {
        this.path = path;
        classSet = new HashSet<JavaClass>();
    }

    public void loadJar(){
        try {
            FileInputStream inputStream = new FileInputStream(path);
            JarInputStream jarInputStream = new JarInputStream(inputStream, true);

            ZipEntry currentEntry;

            while ((currentEntry = jarInputStream.getNextEntry()) != null) {
                if (currentEntry.isDirectory()) {
                    continue;
                }

                ClassParser classParser = new ClassParser(jarInputStream, currentEntry.getName());
                JavaClass clazz = classParser.parse();
                classSet.add(clazz);
                if(debug)System.out.println("Parsed class: " + clazz.getClassName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JavaClass[] getAllClasses(){
        return classSet.toArray(new JavaClass[0]);
    }
}