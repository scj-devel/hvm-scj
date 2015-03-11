package icecaptools.conversion;

import icecaptools.AnalysisObserver;
import icecaptools.AnnotationsAttributeReader;
import icecaptools.BNode;
import icecaptools.BasicBNode;
import icecaptools.BranchBNode;
import icecaptools.ByteCodeStream;
import icecaptools.CFuncInfo;
import icecaptools.CheckcastBNode;
import icecaptools.ClassManager;
import icecaptools.ClassfileUtils;
import icecaptools.DefaultObserver;
import icecaptools.DependencyLeakException;
import icecaptools.DependencyWalker;
import icecaptools.DynamicMethodCallVirtualMethodBNode;
import icecaptools.GotoBNode;
import icecaptools.IcecapClassPath;
import icecaptools.IcecapIterator;
import icecaptools.IcecapRepository;
import icecaptools.InterfaceMethodCallBNode;
import icecaptools.JavaArrayClass;
import icecaptools.LDCBNode;
import icecaptools.MethodEntryPoints;
import icecaptools.MethodOrFieldDesc;
import icecaptools.NewArrayBNode;
import icecaptools.NewArrayMultiBNode;
import icecaptools.NewBNode;
import icecaptools.NewList;
import icecaptools.ObjectFieldAccessBNode;
import icecaptools.RawByteCodes;
import icecaptools.RawByteCodes.RawCpIndexOperation;
import icecaptools.RawByteCodes.RawReturnOperation;
import icecaptools.RawByteCodes.RawShortBranchOperation;
import icecaptools.RawByteCodes.Raw_anewarray;
import icecaptools.RawByteCodes.Raw_athrow;
import icecaptools.RawByteCodes.Raw_checkcast;
import icecaptools.RawByteCodes.Raw_getfield;
import icecaptools.RawByteCodes.Raw_getstatic;
import icecaptools.RawByteCodes.Raw_goto;
import icecaptools.RawByteCodes.Raw_instanceof;
import icecaptools.RawByteCodes.Raw_invokedynamic;
import icecaptools.RawByteCodes.Raw_invokeinterface;
import icecaptools.RawByteCodes.Raw_invokespecial;
import icecaptools.RawByteCodes.Raw_invokestatic;
import icecaptools.RawByteCodes.Raw_invokevirtual;
import icecaptools.RawByteCodes.Raw_ldc;
import icecaptools.RawByteCodes.Raw_ldc2_w;
import icecaptools.RawByteCodes.Raw_ldc_w;
import icecaptools.RawByteCodes.Raw_lookupswitch;
import icecaptools.RawByteCodes.Raw_lookupswitch.Pair;
import icecaptools.RawByteCodes.Raw_multianewarray;
import icecaptools.RawByteCodes.Raw_new;
import icecaptools.RawByteCodes.Raw_newArray;
import icecaptools.RawByteCodes.Raw_putfield;
import icecaptools.RawByteCodes.Raw_putstatic;
import icecaptools.RawByteCodes.Raw_ret;
import icecaptools.RawByteCodes.Raw_tableswitch;
import icecaptools.RestartableMethodObserver;
import icecaptools.ReturnBNode;
import icecaptools.SpecialMethodCallBNode;
import icecaptools.StaticFieldAccessBNode;
import icecaptools.StaticMethodCallBNode;
import icecaptools.SwitchBNode;
import icecaptools.VirtualMethodCallBNode;
import icecaptools.compiler.ICompilationRegistry;
import icecaptools.compiler.LDCConstant;
import icecaptools.compiler.utils.CallGraph;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.BootstrapMethod;
import org.apache.bcel.classfile.BootstrapMethods;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.CodeException;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantInterfaceMethodref;
import org.apache.bcel.classfile.ConstantInvokeDynamic;
import org.apache.bcel.classfile.ConstantMethodHandle;
import org.apache.bcel.classfile.ConstantMethodType;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.util.ClassPath;
import org.apache.bcel.util.SyntheticRepository;

public class Converter implements ClassManager {

    private DependencyExtent dependencyExtent;

    private AnalysisObserver observer;

    private RestartableMethodObserver methodObserver;

    private PrintStream out;

    DependencyRestrictor dependencyRestrictor;

    private boolean initialized;

    private CallGraph cg;

	private boolean supportLoading;

    public Converter(PrintStream out, RestartableMethodObserver methodObserver, ICompilationRegistry cregistry, boolean supportLoading) throws ClassNotFoundException {
        this.out = out;
        dependencyExtent = new DependencyExtent();
        dependencyRestrictor = new DependencyRestrictor(cregistry);
        this.methodObserver = methodObserver;
        initialized = false;
        this.supportLoading = supportLoading;
        
        cg = new CallGraph();
    }

    public NewList startConversion(ConversionConfiguration config) {
        NewList newList = new NewList();
        try {
            methodObserver.restart();
            if (!initialized) {
                Attribute.addAttributeReader("RuntimeInvisibleAnnotations", new AnnotationsAttributeReader());
                initialized = true;
            }
            ClassPath classPath = new IcecapClassPath(config.getInputFolder());

            org.apache.bcel.util.Repository repository = new IcecapRepository(SyntheticRepository.getInstance(classPath));

            Repository.setRepository(repository);

            Repository.clearCache();

            if (observer == null) {
                observer = new DefaultObserver();
            }

            MethodEntryPoints entryPoint;
            DependencyWalker dependencyWalker = new DependencyWalker(this, observer, out, config);

            JavaClass clazz = dependencyWalker.lookupClass(config.getInputPackage() + "." + config.getInputClass());

            config.setEntryPointClassName(clazz.getClassName());

            if (ClassfileUtils.hasClassInitializer(clazz)) {
                entryPoint = convertByteCode(null, clazz, "<clinit>", "()V", true);
                newList = dependencyWalker.analyseMethod(entryPoint, new NewList());
            }
            
            if (supportLoading)
            {
            	JavaClass systemClass = dependencyWalker.lookupClass("devices.System");
            	entryPoint = convertByteCode(null, systemClass, "includeHWObjectSupport", "()V", true);
            	newList = dependencyWalker.analyseMethod(entryPoint, newList);
            }

            if (config.getEntryPointMethodName() == null) {
                config.setEntryPointMethodName("main");
            }

            if (config.getEntryPointMethodSignature() == null) {
                config.setEntryPointMethodSignature("([Ljava/lang/String;)V");
            }

            entryPoint = convertByteCode(null, clazz, config.getEntryPointMethodName(), config.getEntryPointMethodSignature(), config.isEntryPointModyfierIsStatic());

            if (entryPoint != null) {
                newList = dependencyWalker.analyseMethod(entryPoint, newList);
            }

            IcecapIterator<CFuncInfo> cfunctions = observer.getCFunctions();

            while (cfunctions.hasNext()) {
                CFuncInfo next = cfunctions.next();
                MethodOrFieldDesc methodDesc = next.getMethodOrFieldDesc();

                clazz = dependencyWalker.lookupClass(methodDesc.getClassName());

                entryPoint = convertByteCode(null, clazz, methodDesc.getName(), methodDesc.getSignature(), true);

                if (entryPoint != null) {
                    newList = dependencyWalker.analyseMethod(entryPoint, newList);
                }
            }

            IcecapIterator<String> leafMethods = dependencyWalker.getLeafs();
            boolean first = true;
            while (leafMethods.hasNext()) {
                if (first) {
                    out.println("Empty leaf methods:");
                    first = false;
                }
                out.println(leafMethods.next());
            }

            String[] elements = newList.getElementsAsArray();

            if (elements.length > 0) {
                out.println("Inferred creation of the following instance types:");
                for (int i = 0; i < elements.length; i++) {

                    out.println("  " + elements[i]);
                }
            } else {
                out.println("No instance types created.");
            }
        } catch (ClassNotFoundException e) {
            out.println(e.getMessage());
            e.printStackTrace(out);
            newList = new NewList("Error");
        } catch (DependencyLeakException dleak) {
            out.println("Dependency leak, failed to compile " + config.getInputClass());
            out.flush();
            return null;
        } catch (Throwable e) {
            out.println(e.getMessage());
            e.printStackTrace(out);
            newList = new NewList("Error");
        } finally {
            methodObserver.refresh();
        }
        return newList;
    }

    public boolean skipMethodHack(String clazz, String targetMethodName, String targetMethodSignature) {
        if (!clazz.contains("InvocationTargetException")) {
            if (dependencyRestrictor.skipMethod(clazz, targetMethodName, targetMethodSignature)) {
                return true;
            }
        }
        return false;
    }

    public MethodEntryPoints convertByteCode(BNode cause, JavaClass targetClass, String targetMethodName, String targetMethodSignature, boolean isStatic) throws Exception {
        return convertByteCode(cause, targetClass, targetMethodName, targetMethodSignature, isStatic, false);
    }

    public MethodEntryPoints convertByteCode(BNode cause, JavaClass targetClass, String targetMethodName, String targetMethodSignature, boolean isStatic, boolean forceInclusion) throws Exception {
        MethodEntryPoints result;

        result = dependencyExtent.getMethod(targetClass.getClassName(), targetMethodName, targetMethodSignature);

        if (cause != null) {
            cause.registerInCallGraph(cg, targetClass.getClassName(), targetMethodName, targetMethodSignature);
        }

        if (result == null) {
            if (!forceInclusion) {
                if (skipMethodHack(targetClass.getClassName(), targetMethodName, targetMethodSignature)) {
                    observer.methodCodeUsed(targetClass.getClassName(), targetMethodName, targetMethodSignature, false);

                    return null;
                }
            }
            observer.methodCodeUsed(targetClass.getClassName(), targetMethodName, targetMethodSignature, true);

            result = convert(targetClass, targetMethodName, targetMethodSignature, isStatic);

            if (result != null) {
                dependencyExtent.insertMethod(result, targetClass.getClassName(), targetMethodName, targetMethodSignature);
            }
        }
        return result;
    }

    private MethodEntryPoints convert(JavaClass clazz, String targetMethodName, String targetMethodSignature, boolean isStatic) throws Exception {

        Method method = ClassfileUtils.findMethodInClass(clazz, targetMethodName, targetMethodSignature);
        if (method != null) {
            Code code = method.getCode();

            if (code != null) {
                ArrayList<BNode> bnodes = new ArrayList<BNode>();

                ByteCodeStream stream = new ByteCodeStream();
                stream.init(code.getCode());

                while (stream.hasMore()) {
                    int currentCode = stream.get();
                    RawByteCodes.RawBytecode current = null;
                    BNode currentBNode = null;

                    switch ((byte) currentCode) {
                    case RawByteCodes.nop_opcode:
                        current = new RawByteCodes.Raw_nop();
                        break;
                    case RawByteCodes.aconst_null_opcode:
                        current = new RawByteCodes.Raw_aconst_null();
                        break;
                    case RawByteCodes.iconst_m1_opcode:
                        current = new RawByteCodes.Raw_iconst_m1();
                        break;
                    case RawByteCodes.iconst_0_opcode:
                        current = new RawByteCodes.Raw_iconst_0();
                        break;
                    case RawByteCodes.iconst_1_opcode:
                        current = new RawByteCodes.Raw_iconst_1();
                        break;
                    case RawByteCodes.iconst_2_opcode:
                        current = new RawByteCodes.Raw_iconst_2();
                        break;
                    case RawByteCodes.iconst_3_opcode:
                        current = new RawByteCodes.Raw_iconst_3();
                        break;
                    case RawByteCodes.iconst_4_opcode:
                        current = new RawByteCodes.Raw_iconst_4();
                        break;
                    case RawByteCodes.iconst_5_opcode:
                        current = new RawByteCodes.Raw_iconst_5();
                        break;
                    case RawByteCodes.lconst_0_opcode:
                        current = new RawByteCodes.Raw_lconst_0();
                        break;
                    case RawByteCodes.lconst_1_opcode:
                        current = new RawByteCodes.Raw_lconst_1();
                        break;
                    case RawByteCodes.fconst_0_opcode:
                        current = new RawByteCodes.Raw_fconst_0();
                        break;
                    case RawByteCodes.fconst_1_opcode:
                        current = new RawByteCodes.Raw_fconst_1();
                        break;
                    case RawByteCodes.fconst_2_opcode:
                        current = new RawByteCodes.Raw_fconst_2();
                        break;
                    case RawByteCodes.dconst_0_opcode:
                        current = new RawByteCodes.Raw_dconst_0();
                        break;
                    case RawByteCodes.dconst_1_opcode:
                        current = new RawByteCodes.Raw_dconst_1();
                        break;
                    case RawByteCodes.bipush_opcode:
                        current = new RawByteCodes.Raw_bipush();
                        break;
                    case RawByteCodes.sipush_opcode:
                        current = new RawByteCodes.Raw_sipush();
                        break;
                    case RawByteCodes.ldc_opcode:
                        current = new RawByteCodes.Raw_ldc();
                        break;
                    case RawByteCodes.ldc_w_opcode:
                        current = new RawByteCodes.Raw_ldc_w();
                        break;
                    case RawByteCodes.ldc2_w_opcode:
                        current = new RawByteCodes.Raw_ldc2_w();
                        break;
                    case RawByteCodes.iload_opcode:
                        current = new RawByteCodes.Raw_iload();
                        break;
                    case RawByteCodes.lload_opcode:
                        current = new RawByteCodes.Raw_lload();
                        break;
                    case RawByteCodes.fload_opcode:
                        current = new RawByteCodes.Raw_fload();
                        break;
                    case RawByteCodes.dload_opcode:
                        current = new RawByteCodes.Raw_dload();
                        break;
                    case RawByteCodes.aload_opcode:
                        current = new RawByteCodes.Raw_aload();
                        break;
                    case RawByteCodes.iload_0_opcode:
                        current = new RawByteCodes.Raw_iload_0();
                        break;
                    case RawByteCodes.iload_1_opcode:
                        current = new RawByteCodes.Raw_iload_1();
                        break;
                    case RawByteCodes.iload_2_opcode:
                        current = new RawByteCodes.Raw_iload_2();
                        break;
                    case RawByteCodes.iload_3_opcode:
                        current = new RawByteCodes.Raw_iload_3();
                        break;
                    case RawByteCodes.lload_0_opcode:
                        current = new RawByteCodes.Raw_lload_0();
                        break;
                    case RawByteCodes.lload_1_opcode:
                        current = new RawByteCodes.Raw_lload_1();
                        break;
                    case RawByteCodes.lload_2_opcode:
                        current = new RawByteCodes.Raw_lload_2();
                        break;
                    case RawByteCodes.lload_3_opcode:
                        current = new RawByteCodes.Raw_lload_3();
                        break;
                    case RawByteCodes.fload_0_opcode:
                        current = new RawByteCodes.Raw_fload_0();
                        break;
                    case RawByteCodes.fload_1_opcode:
                        current = new RawByteCodes.Raw_fload_1();
                        break;
                    case RawByteCodes.fload_2_opcode:
                        current = new RawByteCodes.Raw_fload_2();
                        break;
                    case RawByteCodes.fload_3_opcode:
                        current = new RawByteCodes.Raw_fload_3();
                        break;
                    case RawByteCodes.dload_0_opcode:
                        current = new RawByteCodes.Raw_dload_0();
                        break;
                    case RawByteCodes.dload_1_opcode:
                        current = new RawByteCodes.Raw_dload_1();
                        break;
                    case RawByteCodes.dload_2_opcode:
                        current = new RawByteCodes.Raw_dload_2();
                        break;
                    case RawByteCodes.dload_3_opcode:
                        current = new RawByteCodes.Raw_dload_3();
                        break;
                    case RawByteCodes.aload_0_opcode:
                        current = new RawByteCodes.Raw_aload_0();
                        break;
                    case RawByteCodes.aload_1_opcode:
                        current = new RawByteCodes.Raw_aload_1();
                        break;
                    case RawByteCodes.aload_2_opcode:
                        current = new RawByteCodes.Raw_aload_2();
                        break;
                    case RawByteCodes.aload_3_opcode:
                        current = new RawByteCodes.Raw_aload_3();
                        break;
                    case RawByteCodes.iaload_opcode:
                        current = new RawByteCodes.Raw_iaload();
                        break;
                    case RawByteCodes.laload_opcode:
                        current = new RawByteCodes.Raw_laload();
                        break;
                    case RawByteCodes.faload_opcode:
                        current = new RawByteCodes.Raw_faload();
                        break;
                    case RawByteCodes.daload_opcode:
                        current = new RawByteCodes.Raw_daload();
                        break;
                    case RawByteCodes.aaload_opcode:
                        current = new RawByteCodes.Raw_aaload();
                        break;
                    case RawByteCodes.baload_opcode:
                        current = new RawByteCodes.Raw_baload();
                        break;
                    case RawByteCodes.caload_opcode:
                        current = new RawByteCodes.Raw_caload();
                        break;
                    case RawByteCodes.saload_opcode:
                        current = new RawByteCodes.Raw_saload();
                        break;
                    case RawByteCodes.istore_opcode:
                        current = new RawByteCodes.Raw_istore();
                        break;
                    case RawByteCodes.lstore_opcode:
                        current = new RawByteCodes.Raw_lstore();
                        break;
                    case RawByteCodes.fstore_opcode:
                        current = new RawByteCodes.Raw_fstore();
                        break;
                    case RawByteCodes.dstore_opcode:
                        current = new RawByteCodes.Raw_dstore();
                        break;
                    case RawByteCodes.astore_opcode:
                        current = new RawByteCodes.Raw_astore();
                        break;
                    case RawByteCodes.istore_0_opcode:
                        current = new RawByteCodes.Raw_istore_0();
                        break;
                    case RawByteCodes.istore_1_opcode:
                        current = new RawByteCodes.Raw_istore_1();
                        break;
                    case RawByteCodes.istore_2_opcode:
                        current = new RawByteCodes.Raw_istore_2();
                        break;
                    case RawByteCodes.istore_3_opcode:
                        current = new RawByteCodes.Raw_istore_3();
                        break;
                    case RawByteCodes.lstore_0_opcode:
                        current = new RawByteCodes.Raw_lstore_0();
                        break;
                    case RawByteCodes.lstore_1_opcode:
                        current = new RawByteCodes.Raw_lstore_1();
                        break;
                    case RawByteCodes.lstore_2_opcode:
                        current = new RawByteCodes.Raw_lstore_2();
                        break;
                    case RawByteCodes.lstore_3_opcode:
                        current = new RawByteCodes.Raw_lstore_3();
                        break;
                    case RawByteCodes.fstore_0_opcode:
                        current = new RawByteCodes.Raw_fstore_0();
                        break;
                    case RawByteCodes.fstore_1_opcode:
                        current = new RawByteCodes.Raw_fstore_1();
                        break;
                    case RawByteCodes.fstore_2_opcode:
                        current = new RawByteCodes.Raw_fstore_2();
                        break;
                    case RawByteCodes.fstore_3_opcode:
                        current = new RawByteCodes.Raw_fstore_3();
                        break;
                    case RawByteCodes.dstore_0_opcode:
                        current = new RawByteCodes.Raw_dstore_0();
                        break;
                    case RawByteCodes.dstore_1_opcode:
                        current = new RawByteCodes.Raw_dstore_1();
                        break;
                    case RawByteCodes.dstore_2_opcode:
                        current = new RawByteCodes.Raw_dstore_2();
                        break;
                    case RawByteCodes.dstore_3_opcode:
                        current = new RawByteCodes.Raw_dstore_3();
                        break;
                    case RawByteCodes.astore_0_opcode:
                        current = new RawByteCodes.Raw_astore_0();
                        break;
                    case RawByteCodes.astore_1_opcode:
                        current = new RawByteCodes.Raw_astore_1();
                        break;
                    case RawByteCodes.astore_2_opcode:
                        current = new RawByteCodes.Raw_astore_2();
                        break;
                    case RawByteCodes.astore_3_opcode:
                        current = new RawByteCodes.Raw_astore_3();
                        break;
                    case RawByteCodes.iastore_opcode:
                        current = new RawByteCodes.Raw_iastore();
                        break;
                    case RawByteCodes.lastore_opcode:
                        current = new RawByteCodes.Raw_lastore();
                        break;
                    case RawByteCodes.fastore_opcode:
                        current = new RawByteCodes.Raw_fastore();
                        break;
                    case RawByteCodes.dastore_opcode:
                        current = new RawByteCodes.Raw_dastore();
                        break;
                    case RawByteCodes.aastore_opcode:
                        current = new RawByteCodes.Raw_aastore();
                        break;
                    case RawByteCodes.bastore_opcode:
                        current = new RawByteCodes.Raw_bastore();
                        break;
                    case RawByteCodes.castore_opcode:
                        current = new RawByteCodes.Raw_castore();
                        break;
                    case RawByteCodes.sastore_opcode:
                        current = new RawByteCodes.Raw_sastore();
                        break;
                    case RawByteCodes.pop_opcode:
                        current = new RawByteCodes.Raw_pop();
                        break;
                    case RawByteCodes.pop2_opcode:
                        current = new RawByteCodes.Raw_pop2();
                        break;
                    case RawByteCodes.dup_opcode:
                        current = new RawByteCodes.Raw_dup();
                        break;
                    case RawByteCodes.dup_x1_opcode:
                        current = new RawByteCodes.Raw_dup_x1();
                        break;
                    case RawByteCodes.dup_x2_opcode:
                        current = new RawByteCodes.Raw_dup_x2();
                        break;
                    case RawByteCodes.dup2_opcode:
                        current = new RawByteCodes.Raw_dup2();
                        break;
                    case RawByteCodes.dup2_x1_opcode:
                        current = new RawByteCodes.Raw_dup2_x1();
                        break;
                    case RawByteCodes.dup2_x2_opcode:
                        current = new RawByteCodes.Raw_dup2_x2();
                        break;
                    case RawByteCodes.swap_opcode:
                        current = new RawByteCodes.Raw_swap();
                        break;
                    case RawByteCodes.iadd_opcode:
                        current = new RawByteCodes.Raw_iadd();
                        break;
                    case RawByteCodes.ladd_opcode:
                        current = new RawByteCodes.Raw_ladd();
                        break;
                    case RawByteCodes.fadd_opcode:
                        current = new RawByteCodes.Raw_fadd();
                        break;
                    case RawByteCodes.dadd_opcode:
                        current = new RawByteCodes.Raw_dadd();
                        break;
                    case RawByteCodes.isub_opcode:
                        current = new RawByteCodes.Raw_isub();
                        break;
                    case RawByteCodes.lsub_opcode:
                        current = new RawByteCodes.Raw_lsub();
                        break;
                    case RawByteCodes.fsub_opcode:
                        current = new RawByteCodes.Raw_fsub();
                        break;
                    case RawByteCodes.dsub_opcode:
                        current = new RawByteCodes.Raw_dsub();
                        break;
                    case RawByteCodes.imul_opcode:
                        current = new RawByteCodes.Raw_imul();
                        break;
                    case RawByteCodes.lmul_opcode:
                        current = new RawByteCodes.Raw_lmul();
                        break;
                    case RawByteCodes.fmul_opcode:
                        current = new RawByteCodes.Raw_fmul();
                        break;
                    case RawByteCodes.dmul_opcode:
                        current = new RawByteCodes.Raw_dmul();
                        break;
                    case RawByteCodes.idiv_opcode:
                        current = new RawByteCodes.Raw_idiv();
                        break;
                    case RawByteCodes.ldiv_opcode:
                        current = new RawByteCodes.Raw_ldiv();
                        break;
                    case RawByteCodes.fdiv_opcode:
                        current = new RawByteCodes.Raw_fdiv();
                        break;
                    case RawByteCodes.ddiv_opcode:
                        current = new RawByteCodes.Raw_ddiv();
                        break;
                    case RawByteCodes.irem_opcode:
                        current = new RawByteCodes.Raw_irem();
                        break;
                    case RawByteCodes.lrem_opcode:
                        current = new RawByteCodes.Raw_lrem();
                        break;
                    case RawByteCodes.frem_opcode:
                        current = new RawByteCodes.Raw_frem();
                        break;
                    case RawByteCodes.drem_opcode:
                        current = new RawByteCodes.Raw_drem();
                        break;
                    case RawByteCodes.ineg_opcode:
                        current = new RawByteCodes.Raw_ineg();
                        break;
                    case RawByteCodes.lneg_opcode:
                        current = new RawByteCodes.Raw_lneg();
                        break;
                    case RawByteCodes.fneg_opcode:
                        current = new RawByteCodes.Raw_fneg();
                        break;
                    case RawByteCodes.dneg_opcode:
                        current = new RawByteCodes.Raw_dneg();
                        break;
                    case RawByteCodes.ishl_opcode:
                        current = new RawByteCodes.Raw_ishl();
                        break;
                    case RawByteCodes.lshl_opcode:
                        current = new RawByteCodes.Raw_lshl();
                        break;
                    case RawByteCodes.ishr_opcode:
                        current = new RawByteCodes.Raw_ishr();
                        break;
                    case RawByteCodes.lshr_opcode:
                        current = new RawByteCodes.Raw_lshr();
                        break;
                    case RawByteCodes.iushr_opcode:
                        current = new RawByteCodes.Raw_iushr();
                        break;
                    case RawByteCodes.lushr_opcode:
                        current = new RawByteCodes.Raw_lushr();
                        break;
                    case RawByteCodes.iand_opcode:
                        current = new RawByteCodes.Raw_iand();
                        break;
                    case RawByteCodes.land_opcode:
                        current = new RawByteCodes.Raw_land();
                        break;
                    case RawByteCodes.ior_opcode:
                        current = new RawByteCodes.Raw_ior();
                        break;
                    case RawByteCodes.ixor_opcode:
                        current = new RawByteCodes.Raw_ixor();
                        break;
                    case RawByteCodes.lor_opcode:
                        current = new RawByteCodes.Raw_lor();
                        break;
                    case RawByteCodes.lxor_opcode:
                        current = new RawByteCodes.Raw_lxor();
                        break;
                    case RawByteCodes.iinc_opcode:
                        current = new RawByteCodes.Raw_iinc();
                        break;
                    case RawByteCodes.i2l_opcode:
                        current = new RawByteCodes.Raw_i2l();
                        break;
                    case RawByteCodes.i2f_opcode:
                        current = new RawByteCodes.Raw_i2f();
                        break;
                    case RawByteCodes.i2d_opcode:
                        current = new RawByteCodes.Raw_i2d();
                        break;
                    case RawByteCodes.l2i_opcode:
                        current = new RawByteCodes.Raw_l2i();
                        break;
                    case RawByteCodes.l2f_opcode:
                        current = new RawByteCodes.Raw_l2f();
                        break;
                    case RawByteCodes.l2d_opcode:
                        current = new RawByteCodes.Raw_l2d();
                        break;
                    case RawByteCodes.f2i_opcode:
                        current = new RawByteCodes.Raw_f2i();
                        break;
                    case RawByteCodes.f2l_opcode:
                        current = new RawByteCodes.Raw_f2l();
                        break;
                    case RawByteCodes.f2d_opcode:
                        current = new RawByteCodes.Raw_f2d();
                        break;
                    case RawByteCodes.d2i_opcode:
                        current = new RawByteCodes.Raw_d2i();
                        break;
                    case RawByteCodes.d2l_opcode:
                        current = new RawByteCodes.Raw_d2l();
                        break;
                    case RawByteCodes.d2f_opcode:
                        current = new RawByteCodes.Raw_d2f();
                        break;
                    case RawByteCodes.i2b_opcode:
                        current = new RawByteCodes.Raw_i2b();
                        break;
                    case RawByteCodes.i2c_opcode:
                        current = new RawByteCodes.Raw_i2c();
                        break;
                    case RawByteCodes.i2s_opcode:
                        current = new RawByteCodes.Raw_i2s();
                        break;
                    case RawByteCodes.lcmp_opcode:
                        current = new RawByteCodes.Raw_lcmp();
                        break;
                    case RawByteCodes.fcmpl_opcode:
                        current = new RawByteCodes.Raw_fcmpl();
                        break;
                    case RawByteCodes.fcmpg_opcode:
                        current = new RawByteCodes.Raw_fcmpg();
                        break;
                    case RawByteCodes.dcmpl_opcode:
                        current = new RawByteCodes.Raw_dcmpl();
                        break;
                    case RawByteCodes.dcmpg_opcode:
                        current = new RawByteCodes.Raw_dcmpg();
                        break;
                    case RawByteCodes.ifeq_opcode:
                        current = new RawByteCodes.Raw_ifeq();
                        break;
                    case RawByteCodes.ifne_opcode:
                        current = new RawByteCodes.Raw_ifne();
                        break;
                    case RawByteCodes.iflt_opcode:
                        current = new RawByteCodes.Raw_iflt();
                        break;
                    case RawByteCodes.ifge_opcode:
                        current = new RawByteCodes.Raw_ifge();
                        break;
                    case RawByteCodes.ifgt_opcode:
                        current = new RawByteCodes.Raw_ifgt();
                        break;
                    case RawByteCodes.ifle_opcode:
                        current = new RawByteCodes.Raw_ifle();
                        break;
                    case RawByteCodes.if_icmpeq_opcode:
                        current = new RawByteCodes.Raw_if_icmpeq();
                        break;
                    case RawByteCodes.if_icmpne_opcode:
                        current = new RawByteCodes.Raw_if_icmpne();
                        break;
                    case RawByteCodes.if_icmplt_opcode:
                        current = new RawByteCodes.Raw_if_icmplt();
                        break;
                    case RawByteCodes.if_icmpge_opcode:
                        current = new RawByteCodes.Raw_if_icmpge();
                        break;
                    case RawByteCodes.if_icmpgt_opcode:
                        current = new RawByteCodes.Raw_if_icmpgt();
                        break;
                    case RawByteCodes.if_icmple_opcode:
                        current = new RawByteCodes.Raw_if_icmple();
                        break;
                    case RawByteCodes.if_acmpeq_opcode:
                        current = new RawByteCodes.Raw_if_acmpeq();
                        break;
                    case RawByteCodes.if_acmpne_opcode:
                        current = new RawByteCodes.Raw_if_acmpne();
                        break;
                    case RawByteCodes.goto_opcode:
                        current = new RawByteCodes.Raw_goto();
                        break;
                    case RawByteCodes.jsr_opcode:
                        current = new RawByteCodes.Raw_jsr();
                        break;
                    case RawByteCodes.ret_opcode:
                        current = new RawByteCodes.Raw_ret();
                        break;
                    case RawByteCodes.tableswitch_opcode:
                        current = new RawByteCodes.Raw_tableswitch();
                        break;
                    case RawByteCodes.lookupswitch_opcode:
                        current = new RawByteCodes.Raw_lookupswitch();
                        break;
                    case RawByteCodes.ireturn_opcode:
                        current = new RawByteCodes.Raw_ireturn();
                        break;
                    case RawByteCodes.lreturn_opcode:
                        current = new RawByteCodes.Raw_lreturn();
                        break;
                    case RawByteCodes.freturn_opcode:
                        current = new RawByteCodes.Raw_freturn();
                        break;
                    case RawByteCodes.dreturn_opcode:
                        current = new RawByteCodes.Raw_dreturn();
                        break;
                    case RawByteCodes.areturn_opcode:
                        current = new RawByteCodes.Raw_areturn();
                        break;
                    case RawByteCodes.return_opcode:
                        current = new RawByteCodes.Raw_return();
                        break;
                    case RawByteCodes.getstatic_opcode:
                        current = new RawByteCodes.Raw_getstatic();
                        break;
                    case RawByteCodes.putstatic_opcode:
                        current = new RawByteCodes.Raw_putstatic();
                        break;
                    case RawByteCodes.getfield_opcode:
                        current = new RawByteCodes.Raw_getfield();
                        break;
                    case RawByteCodes.putfield_opcode:
                        current = new RawByteCodes.Raw_putfield();
                        break;
                    case RawByteCodes.invokevirtual_opcode:
                        current = new RawByteCodes.Raw_invokevirtual();
                        break;
                    case RawByteCodes.invokedynamic_opcode:
                        current = new RawByteCodes.Raw_invokedynamic();
                        break;
                    case RawByteCodes.invokespecial_opcode:
                        current = new RawByteCodes.Raw_invokespecial();
                        break;
                    case RawByteCodes.invokestatic_opcode:
                        current = new RawByteCodes.Raw_invokestatic();
                        break;
                    case RawByteCodes.invokeinterface_opcode:
                        current = new RawByteCodes.Raw_invokeinterface();
                        break;
                    case RawByteCodes.new_opcode:
                        current = new RawByteCodes.Raw_new();
                        break;
                    case RawByteCodes.anewarray_opcode:
                        current = new RawByteCodes.Raw_anewarray();
                        break;
                    case RawByteCodes.newarray_opcode:
                        current = new RawByteCodes.Raw_newArray();
                        break;
                    case RawByteCodes.arraylength_opcode:
                        current = new RawByteCodes.Raw_arraylength();
                        break;
                    case RawByteCodes.athrow_opcode:
                        current = new RawByteCodes.Raw_athrow();
                        break;
                    case RawByteCodes.checkcast_opcode:
                        current = new RawByteCodes.Raw_checkcast();
                        break;
                    case RawByteCodes.instanceof_opcode:
                        current = new RawByteCodes.Raw_instanceof();
                        break;
                    case RawByteCodes.monitorenter_opcode:
                        current = new RawByteCodes.Raw_monitorenter();
                        break;
                    case RawByteCodes.monitorexit_opcode:
                        current = new RawByteCodes.Raw_monitorexit();
                        break;
                    case RawByteCodes.wide_opcode:
                        current = new RawByteCodes.Raw_wide();
                        break;
                    case RawByteCodes.multianewarray_opcode:
                        current = new RawByteCodes.Raw_multianewarray();
                        break;
                    case RawByteCodes.ifnull_opcode:
                        current = new RawByteCodes.Raw_ifnull();
                        break;
                    case RawByteCodes.ifnonnull_opcode:
                        current = new RawByteCodes.Raw_ifnonnull();
                        break;
                    case RawByteCodes.goto_w_opcode:
                        current = new RawByteCodes.Raw_goto_w();
                        break;
                    case RawByteCodes.jsr_w_opcode:
                        current = new RawByteCodes.Raw_jsr_w();
                        break;
                    default:
                        throw new Exception("Unknown byte code [" + currentCode + "]");
                    }

                    current.read(currentCode, stream);

                    if (current instanceof Raw_goto) {
                        Raw_goto rawGoto = (Raw_goto) current;
                        currentBNode = new GotoBNode(rawGoto.getAddress(), (int) rawGoto.branchoffset, clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof RawShortBranchOperation) {
                        RawShortBranchOperation shortBranchOperation = (RawShortBranchOperation) current;
                        currentBNode = new BranchBNode(shortBranchOperation.getAddress(), (int) shortBranchOperation.branchoffset, clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_new) {
                        Raw_new rawNew = (Raw_new) current;
                        JavaClass newType = ClassfileUtils.getType(clazz, rawNew.cpIndex);
                        currentBNode = new NewBNode(rawNew.getAddress(), newType, clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_checkcast) {
                        JavaClass type = ClassfileUtils.getType(clazz, ((Raw_checkcast) current).cpIndex);
                        if (type != null) {
                            currentBNode = new CheckcastBNode(current.getAddress(), type, clazz.getClassName(), targetMethodName, targetMethodSignature);
                        } else {
                            currentBNode = new BasicBNode(current.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                        }
                    } else if (current instanceof Raw_instanceof) {
                        JavaClass type = ClassfileUtils.getType(clazz, ((Raw_instanceof) current).cpIndex);
                        if (type != null) {
                            currentBNode = new CheckcastBNode(current.getAddress(), type, clazz.getClassName(), targetMethodName, targetMethodSignature);
                        } else {
                            currentBNode = new BasicBNode(current.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                        }
                    } else if (current instanceof Raw_invokevirtual) {
                        Raw_invokevirtual rawInvokeVirtual = (Raw_invokevirtual) current;
                        int index = rawInvokeVirtual.cpIndex;
                        ConstantMethodref methodRef = (ConstantMethodref) clazz.getConstantPool().getConstant(index);
                        ConstantClass classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodRef.getClassIndex());
                        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(methodRef.getNameAndTypeIndex());
                        ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(classRef.getNameIndex());
                        ConstantUtf8 methodName = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getNameIndex());
                        ConstantUtf8 methodSig = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getSignatureIndex());
                        currentBNode = new VirtualMethodCallBNode(rawInvokeVirtual.getAddress(), className.getBytes(), methodName.getBytes(), methodSig.getBytes(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_invokedynamic) {
                        Raw_invokedynamic rawInvokeDynamic = (Raw_invokedynamic) current;
                        int index = rawInvokeDynamic.cpIndex;
                        ConstantInvokeDynamic constantInvokeDynamic = (ConstantInvokeDynamic) clazz.getConstantPool().getConstant(index);
                        int bootstrapIndex = constantInvokeDynamic.getBootstrapMethodAttrIndex();
                        int nameAndTypeIndex = constantInvokeDynamic.getNameAndTypeIndex();

                        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(nameAndTypeIndex);

                        ConstantUtf8 methodName = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getNameIndex());
                        ConstantUtf8 methodSig = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getSignatureIndex());

                        Attribute[] attributes = clazz.getAttributes();
                        for (Attribute attribute : attributes) {
                            if (attribute instanceof BootstrapMethods) {
                                BootstrapMethods bmethods = (BootstrapMethods) attribute;
                                BootstrapMethod[] bmethodsAsArray = bmethods.getBootstrapMethods();
                                if (bmethodsAsArray != null) {
                                    if (bootstrapIndex < bmethodsAsArray.length) {
                                        BootstrapMethod bm = bmethodsAsArray[bootstrapIndex];
                                        int methodRefIndex = bm.getBootstrapMethodRef();
                                        ConstantMethodHandle methodRef = (ConstantMethodHandle) clazz.getConstantPool().getConstant(methodRefIndex);
                                        switch (methodRef.getReferenceKind()) {
                                        case RawByteCodes.REF_getField:
                                            System.out.println("REF_getField");
                                            break;
                                        case RawByteCodes.REF_getStatic:
                                            System.out.println("REF_getStatic");
                                            break;
                                        case RawByteCodes.REF_putField:
                                            System.out.println("REF_putField");
                                            break;
                                        case RawByteCodes.REF_putStatic:
                                            System.out.println("REF_putStatic");
                                            break;
                                        case RawByteCodes.REF_invokeVirtual:
                                        case RawByteCodes.REF_invokeStatic:
                                        case RawByteCodes.REF_invokeSpecial:
                                        case RawByteCodes.REF_newInvokeSpecial: {
                                            ConstantMethodref methodInfo = (ConstantMethodref) clazz.getConstantPool().getConstant(methodRef.getReferenceIndex());
                                            ConstantClass classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodInfo.getClassIndex());
                                            ConstantNameAndType nameAndTypeHandle = (ConstantNameAndType) clazz.getConstantPool().getConstant(methodInfo.getNameAndTypeIndex());
                                            ConstantUtf8 classNameHandle = (ConstantUtf8) clazz.getConstantPool().getConstant(classRef.getNameIndex());
                                            ConstantUtf8 methodNameHandle = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndTypeHandle.getNameIndex());
                                            ConstantUtf8 methodSigHandle = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndTypeHandle.getSignatureIndex());

                                            // System.out.println(classNameHandle
                                            // + ":" + methodNameHandle + ":" +
                                            // methodSigHandle);
                                            currentBNode = new DynamicMethodCallVirtualMethodBNode(rawInvokeDynamic.getAddress(), bootstrapIndex, methodName.getBytes(), methodSig.getBytes(), classNameHandle.getBytes(), methodNameHandle.getBytes(), methodSigHandle.getBytes(),
                                                    clazz.getClassName(), targetMethodName, targetMethodSignature);

                                            break;
                                        }
                                        case RawByteCodes.REF_invokeInterface:
                                            System.out.println("REF_invokeInterface");
                                            break;
                                        default:
                                            System.out.println("default");
                                        }
                                        int[] args = bm.getBootstrapArguments();
                                        for (int i : args) {
                                            Constant c = clazz.getConstantPool().getConstant(i);
                                            if (c instanceof ConstantMethodType) {
                                                // ConstantMethodType cmt =
                                                // (ConstantMethodType) c;
                                                // ConstantUtf8 desc =
                                                // (ConstantUtf8)
                                                // clazz.getConstantPool().getConstant(cmt.getDescriptorIndex());
                                                // System.out.println(desc);
                                            } else if (c instanceof ConstantMethodHandle) {
                                                methodRef = (ConstantMethodHandle) c;
                                                switch (methodRef.getReferenceKind()) {
                                                case RawByteCodes.REF_getField:
                                                    System.out.println("REF_getField");
                                                    break;
                                                case RawByteCodes.REF_getStatic:
                                                    System.out.println("REF_getStatic");
                                                    break;
                                                case RawByteCodes.REF_putField:
                                                    System.out.println("REF_putField");
                                                    break;
                                                case RawByteCodes.REF_putStatic:
                                                    System.out.println("REF_putStatic");
                                                    break;
                                                case RawByteCodes.REF_invokeVirtual:
                                                case RawByteCodes.REF_invokeStatic:
                                                case RawByteCodes.REF_invokeSpecial:
                                                case RawByteCodes.REF_newInvokeSpecial: {
                                                    ConstantMethodref methodInfo = (ConstantMethodref) clazz.getConstantPool().getConstant(methodRef.getReferenceIndex());
                                                    ConstantClass classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodInfo.getClassIndex());
                                                    ConstantNameAndType nameAndTypeHandle = (ConstantNameAndType) clazz.getConstantPool().getConstant(methodInfo.getNameAndTypeIndex());
                                                    ConstantUtf8 classNameHandle = (ConstantUtf8) clazz.getConstantPool().getConstant(classRef.getNameIndex());
                                                    ConstantUtf8 methodNameHandle = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndTypeHandle.getNameIndex());
                                                    ConstantUtf8 methodSigHandle = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndTypeHandle.getSignatureIndex());

                                                    // System.out.println(classNameHandle
                                                    // + ":" + methodNameHandle
                                                    // + ":" + methodSigHandle);

                                                    if (currentBNode instanceof DynamicMethodCallVirtualMethodBNode) {
                                                        ((DynamicMethodCallVirtualMethodBNode) currentBNode).setLambdaMethod(classNameHandle.getBytes(), methodNameHandle.getBytes(), methodSigHandle.getBytes());
                                                    }
                                                    break;
                                                }
                                                case RawByteCodes.REF_invokeInterface:
                                                    System.out.println("REF_invokeInterface");
                                                    break;
                                                default:
                                                    System.out.println("default");
                                                }
                                            }
                                            c = null;
                                        }

                                    }
                                }
                            }
                        }
                    } else if (current instanceof Raw_invokespecial) {
                        Raw_invokespecial rawInvokeSpecial = (Raw_invokespecial) current;
                        int index = rawInvokeSpecial.cpIndex;
                        ConstantMethodref methodRef = (ConstantMethodref) clazz.getConstantPool().getConstant(index);
                        ConstantClass classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodRef.getClassIndex());
                        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(methodRef.getNameAndTypeIndex());
                        ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(classRef.getNameIndex());
                        ConstantUtf8 methodName = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getNameIndex());
                        ConstantUtf8 methodSig = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getSignatureIndex());
                        currentBNode = new SpecialMethodCallBNode(rawInvokeSpecial.getAddress(), className.getBytes(), methodName.getBytes(), methodSig.getBytes(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_invokestatic) {
                        Raw_invokestatic rawInvokeStatic = (Raw_invokestatic) current;
                        int index = rawInvokeStatic.cpIndex;
                        ConstantMethodref methodRef = (ConstantMethodref) clazz.getConstantPool().getConstant(index);
                        ConstantClass classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodRef.getClassIndex());
                        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(methodRef.getNameAndTypeIndex());
                        ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(classRef.getNameIndex());
                        ConstantUtf8 methodName = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getNameIndex());
                        ConstantUtf8 methodSig = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getSignatureIndex());
                        currentBNode = new StaticMethodCallBNode(rawInvokeStatic.getAddress(), className.getBytes(), methodName.getBytes(), methodSig.getBytes(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_invokeinterface) {
                        Raw_invokeinterface rawInvokeInterface = (Raw_invokeinterface) current;
                        int index = rawInvokeInterface.cpIndex;
                        ConstantInterfaceMethodref methodRef = (ConstantInterfaceMethodref) clazz.getConstantPool().getConstant(index);
                        ConstantClass classRef = (ConstantClass) clazz.getConstantPool().getConstant(methodRef.getClassIndex());
                        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(methodRef.getNameAndTypeIndex());
                        ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(classRef.getNameIndex());
                        ConstantUtf8 methodName = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getNameIndex());
                        ConstantUtf8 methodSig = (ConstantUtf8) clazz.getConstantPool().getConstant(nameAndType.getSignatureIndex());
                        currentBNode = new InterfaceMethodCallBNode(rawInvokeInterface.getAddress(), className.getBytes(), methodName.getBytes(), methodSig.getBytes(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if ((current instanceof RawReturnOperation) || (current instanceof Raw_ret) || (current instanceof Raw_athrow)) {
                        currentBNode = new ReturnBNode(current.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if ((current instanceof Raw_putstatic) || (current instanceof Raw_getstatic)) {
                        RawCpIndexOperation putgetstatic = (RawCpIndexOperation) current;
                        int index = putgetstatic.cpIndex;
                        ConstantFieldref fieldRef = (ConstantFieldref) clazz.getConstantPool().getConstant(index);
                        ConstantClass classIndex = (ConstantClass) clazz.getConstantPool().getConstant(fieldRef.getClassIndex());
                        ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(classIndex.getNameIndex());
                        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(fieldRef.getNameAndTypeIndex());
                        String fieldName = nameAndType.getName(clazz.getConstantPool());
                        String signature = nameAndType.getSignature(clazz.getConstantPool());
                        currentBNode = new StaticFieldAccessBNode(putgetstatic.getAddress(), className.getBytes().replace('/', '.'), fieldName, signature, current instanceof Raw_getstatic, clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_tableswitch) {
                        Raw_tableswitch tableSwitch = (Raw_tableswitch) current;
                        currentBNode = new SwitchBNode(tableSwitch, tableSwitch.getAddress(), tableSwitch.defaultVal, clazz.getClassName(), targetMethodName, targetMethodSignature);
                        long[] targets = tableSwitch.offsets;
                        int count = 0;
                        while (count < targets.length) {
                            ((SwitchBNode) currentBNode).addTargetAddress(targets[count]);
                            count++;
                        }
                    } else if (current instanceof Raw_lookupswitch) {
                        Raw_lookupswitch lookupSwitch = (Raw_lookupswitch) current;
                        currentBNode = new SwitchBNode(lookupSwitch, lookupSwitch.getAddress(), lookupSwitch.defaultVal, clazz.getClassName(), targetMethodName, targetMethodSignature);
                        Pair[] pairs = lookupSwitch.pairs;
                        int count = 0;
                        while (count < pairs.length) {
                            ((SwitchBNode) currentBNode).addTargetAddress(pairs[count].offset);
                            count++;
                        }
                    } else if ((current instanceof Raw_putfield) || (current instanceof Raw_getfield)) {
                        RawCpIndexOperation putgetfield = (RawCpIndexOperation) current;
                        int index = putgetfield.cpIndex;
                        ConstantFieldref fieldRef = (ConstantFieldref) clazz.getConstantPool().getConstant(index);
                        ConstantClass classIndex = (ConstantClass) clazz.getConstantPool().getConstant(fieldRef.getClassIndex());
                        ConstantUtf8 className = (ConstantUtf8) clazz.getConstantPool().getConstant(classIndex.getNameIndex());
                        ConstantNameAndType nameAndType = (ConstantNameAndType) clazz.getConstantPool().getConstant(fieldRef.getNameAndTypeIndex());
                        String fieldName = nameAndType.getName(clazz.getConstantPool());
                        String signature = nameAndType.getSignature(clazz.getConstantPool());
                        currentBNode = new ObjectFieldAccessBNode(current.getAddress(), className.getBytes(), fieldName, signature, current instanceof Raw_getfield, clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if ((current instanceof Raw_ldc) || (current instanceof Raw_ldc_w) || (current instanceof RawByteCodes.Raw_ldc2_w)) {
                        int cpIndex;
                        if (current instanceof Raw_ldc) {
                            cpIndex = ((Raw_ldc) current).cpIndex;
                        } else if (current instanceof Raw_ldc2_w) {
                            cpIndex = ((Raw_ldc2_w) current).cpIndex;
                        } else {
                            cpIndex = ((Raw_ldc_w) current).cpIndex;
                        }
                        LDCConstant constant = ClassfileUtils.getLDCConstant(clazz.getClassName(), cpIndex);
                        currentBNode = new LDCBNode(constant, current.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_anewarray) {
                        Raw_anewarray newArray = (Raw_anewarray) current;
                        JavaClass newType = ClassfileUtils.getType(clazz, newArray.cpIndex);
                        currentBNode = new NewArrayBNode(newType, newArray.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_newArray) {
                        Raw_newArray newArray = (Raw_newArray) current;
                        currentBNode = new NewArrayBNode(newArray.type, newArray.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    } else if (current instanceof Raw_multianewarray) {
                        Raw_multianewarray newArray = (Raw_multianewarray) current;
                        JavaArrayClass newType = (JavaArrayClass) ClassfileUtils.getType(clazz, newArray.cpIndex);

                        if (newType.isBasicType()) {
                            int type = newType.getBasicType();
                            currentBNode = new NewArrayMultiBNode(type, newArray.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature, newType.getDimension());
                        } else {
                            currentBNode = new NewArrayMultiBNode(newType, newArray.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature, newType.getDimension());
                        }
                    } else {
                        currentBNode = new BasicBNode(current.getAddress(), clazz.getClassName(), targetMethodName, targetMethodSignature);
                    }

                    currentBNode.setExceptionsThrown(current.exceptionsThrown());
                    currentBNode.setRawBytes(current.getRawBytes());
                    currentBNode.setRawBytecode(current);
                    bnodes.add(currentBNode);
                }

                TargetAddressMap tmap = new TargetAddressMap();

                Converter.linkBNodes(bnodes, tmap);

                return new MethodEntryPoints(bnodes, method, tmap, clazz);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void extendBNodes(ArrayList<BNode> bnodes, CodeException[] codeExceptions) throws Exception {
        for (int i = 0; i < bnodes.size(); i++) {
            BNode current = bnodes.get(i);
            if (current.requiresExtension()) {
                int extension = current.extend();
                int address = current.getAddress();
                relocateExceptions(address, extension, codeExceptions);
                relocateForward(bnodes, i + 1, extension, address);
                relocateBackward(bnodes, i, extension, address);
            }
        }
    }

    private static void relocateExceptions(int address, int extension, CodeException[] codeExceptions) {
        if ((codeExceptions != null) && (codeExceptions.length > 0)) {
            for (int i = 0; i < codeExceptions.length; i++) {
                CodeException exceptionHandler = codeExceptions[i];
                int startPC, endPC, handlerPC;
                startPC = exceptionHandler.getStartPC();
                endPC = exceptionHandler.getEndPC();
                handlerPC = exceptionHandler.getHandlerPC();

                if (startPC > address) {
                    startPC += extension;
                }
                if (endPC > address) {
                    endPC += extension;
                }
                if (handlerPC > address) {
                    handlerPC += extension;
                }
                exceptionHandler.setStartPC(startPC);
                exceptionHandler.setEndPC(endPC);
                exceptionHandler.setHandlerPC(handlerPC);
            }
        }
    }

    private static void relocateBackward(ArrayList<BNode> bnodes, int end, int extension, int address) {
        for (int i = 0; i <= end; i++) {
            BNode current = bnodes.get(i);
            current.relocateBackward(address, extension);
        }
    }

    private static void relocateForward(ArrayList<BNode> bnodes, int start, int extension, int address) {
        for (int i = start; i < bnodes.size(); i++) {
            BNode current = bnodes.get(i);
            current.relocateForward(address, extension);
            current.setAddress(current.getAddress() + extension);
        }
    }

    private static void linkBNodes(ArrayList<BNode> bnodes, TargetAddressMap tmap) throws Exception {
        BNode[] nodes = new BNode[bnodes.size()];

        Iterator<BNode> iterator = bnodes.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            nodes[i] = iterator.next();
            i++;
        }

        for (i = 0; i < nodes.length; i++) {
            BNode bNode = nodes[i];
            bNode.link(nodes, tmap);
        }
    }

    public void setObserver(AnalysisObserver analysisObserver) {
        this.observer = analysisObserver;

    }

    public AnalysisObserver getObserver() {
        return observer;
    }

    public DependencyExtent getDependencyExtent() {
        return this.dependencyExtent;
    }

    public CallGraph getCallGraph() {
        return cg;
    }
}
