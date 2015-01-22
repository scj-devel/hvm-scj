package icecaptools.launching;

import java.io.File;

import icecaptools.CompilationSequence;
import icecaptools.ConverterJob;
import icecaptools.MethodOrFieldDesc;
import icecaptools.compiler.ByteCodePatcher;
import icecaptools.debugging.HVMStackFrame;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;

public class HVMSourceLookupParticipant extends AbstractSourceLookupParticipant {

    @Override
    public void init(ISourceLookupDirector director) {
        super.init(director);
    }

    @Override
    public String getSourceName(Object object) throws CoreException {
        if (object instanceof HVMStackFrame) {
            HVMStackFrame frame = (HVMStackFrame) object;
            CompilationSequence compilationSequence = ConverterJob.mostRecentSequence;
            if (compilationSequence != null) {
                ByteCodePatcher patcher = compilationSequence.getPatcher();
                MethodOrFieldDesc desc = patcher.getMethodDescriptor(frame.getMethodNumber());
                if (desc != null) {
                    JavaClass clazz;
                    try {
                        clazz = Repository.lookupClass(desc.getClassName());
                        if (clazz != null) {
                            StringBuffer buffer = new StringBuffer();
                            buffer.append(clazz.getPackageName().replace('.', File.separatorChar));
                            buffer.append(File.separatorChar);
                            buffer.append(clazz.getSourceFileName());
                            return buffer.toString();
                        }
                    } catch (ClassNotFoundException e) {
                        throw new CoreException(new IStatus() {

                            private static final String message = "Could not get source name";

                            @Override
                            public IStatus[] getChildren() {
                                return null;
                            }

                            @Override
                            public int getCode() {
                                return IStatus.ERROR;
                            }

                            @Override
                            public Throwable getException() {
                                return new Exception(message);
                            }

                            @Override
                            public String getMessage() {
                                return message;
                            }

                            @Override
                            public String getPlugin() {
                                return DebugPlugin.getUniqueIdentifier();
                            }

                            @Override
                            public int getSeverity() {
                                return IStatus.ERROR;
                            }

                            @Override
                            public boolean isMultiStatus() {
                                return false;
                            }

                            @Override
                            public boolean isOK() {
                                return false;
                            }

                            @Override
                            public boolean matches(int severityMask) {
                                return false;
                            };
                        });

                    }
                }
            }
        }
        return "???";
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void sourceContainersChanged(ISourceLookupDirector director) {
        super.sourceContainersChanged(director);
    }
}
