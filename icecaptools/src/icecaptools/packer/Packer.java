package icecaptools.packer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.apache.bcel.classfile.JavaClass;

/**
 * This class provides JAR packing functionality using standard Java SDK
 * @todo Add manifest !
 * @see java.util.jar
 *
 */
public class Packer {
    /**
     * Creates a JAR file from Java classes, including manifest
     *
     * @param outputFile
     *            Name of the output file
     * @param javaClasses
     *            Array of Java classes to save
     * @param mainClass
     *            Class to be used as the main class in manifest file
     * @see java.util.jar
     * @see java.util.jar.Manifest
     */

    private static final String MANIFEST_VERSION_VALUE = "1.0";
    public static String createJar(String outputFileName, JavaClass[] javaClasses, String mainClass) {
        if (javaClasses.length > 0) {
            try {
                Manifest manifest = new Manifest();
                Attributes attributes = manifest.getMainAttributes();

                attributes.put(Attributes.Name.MANIFEST_VERSION, MANIFEST_VERSION_VALUE);
                attributes.put(Attributes.Name.MAIN_CLASS, mainClass);
                File outputFile = new File(outputFileName);
                FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
                JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream, manifest);

                JarEntry jarEntry = null;
                for (int i = 0; i < javaClasses.length; i++) {
                    //we need to replace dot characters with slash to get directory structure in ZIP
                    jarEntry = new JarEntry(javaClasses[i].getClassName().replace('.', '/')+".class");
                    jarOutputStream.putNextEntry(jarEntry);
                    javaClasses[i].dump(jarOutputStream);
                }
                jarOutputStream.flush();
                jarOutputStream.close();
                return outputFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("No classes to pack");
        }
        return null;
    }
}
