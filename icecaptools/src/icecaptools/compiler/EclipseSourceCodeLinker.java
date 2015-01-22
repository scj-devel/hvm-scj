package icecaptools.compiler;

import icecaptools.ClassfileUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.bcel.classfile.Method;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

public class EclipseSourceCodeLinker implements IcecapSourceCodeLinker {

    private IJavaProject project;

    public EclipseSourceCodeLinker(IJavaProject project) {
        this.project = project;
    }

    @Override
    public void getSourceCode(String className, Method javaMethod, int pc, StringBuffer output) {
        int lineNumber = ClassfileUtils.getLineNumber(javaMethod, pc);
        if (lineNumber != -1) {
            output.append("/*");
            try {
                String sourceLine = getSourceLine(className, lineNumber);
                
                output.append(sourceLine.replace("/*", "'").replace("*/", "'"));
            } catch (IOException e) {
            }
            output.append(" */\n");
        }

    }

    private String getSourceLine(String className, int lineNumber) throws IOException {
        StringBuffer comment = new StringBuffer();
        try {
            IType type = project.findType(className.replace("/", "."));
            if (type != null) {
                ICompilationUnit unit = type.getCompilationUnit();
                if (unit != null) {
                    String unitSource = unit.getSource();
                    if (unitSource != null) {
                        BufferedReader reader = new BufferedReader(new StringReader(unitSource));

                        String nextToken = null;
                        while (((nextToken = reader.readLine()) != null) && (lineNumber > 1)) {
                            lineNumber--;
                        }
                        if (nextToken != null) {
                            comment.append(nextToken);
                        }
                    }
                }
            }
        } catch (JavaModelException e) {
            return " --- ";
        }
        return comment.toString();
    }
}
