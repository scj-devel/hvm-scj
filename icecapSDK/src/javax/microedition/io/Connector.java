package javax.microedition.io;

import java.io.IOException;

import javax.safetycritical.annotate.SCJAllowed;

public class Connector {

	/**
     * Access mode READ.
     */
	@SCJAllowed
	public static final int READ = 1;
	
	/**
     * Access mode WRITE.
     */
	@SCJAllowed
	public static final int WRITE = 2;
	
	/**
     * Access mode READ_WRITE.
     */
	@SCJAllowed
	public static final int READ_WRITE = 3;
	
	
//	/**
//     * The platform name.
//     */
//    private static String platform;
//
//    /**
//     * The root of the classes.
//     */
//    private static String classRoot;
//
//    /**
//     * The fallback root of the classes.
//     */
//    private static String classRootFallback;
//
//    /**
//     * Class initializer.
//     */
//    static {
//        /* Set up the platform name */
//        platform = System.getProperty("microedition.platformimpl");
//        if ((platform == null) || (platform.equals("generic"))) {
//            platform = "j2me";
//        }
//
//        /* Set up the library class root path */
//        /* This may vary from one CLDC implementation to another */
//        classRoot = System.getProperty("javax.microedition.io.Connector.protocolpath");
//        if (classRoot == null) {
//            //classRoot = "com.sun.cldc.io";
//            classRoot = "javax.microedition.io";
//        }
//        classRootFallback = System.getProperty(
//            "javax.microedition.io.Connector.protocolpath.fallback");
//    }

	
	//private static ConsoleConnection consoleCon;
    
	// No instantiation of class Connector
	private Connector() {
	}
	
	public static javax.microedition.io.Connection open(String name)
			throws java.io.IOException {
		return open(name, READ_WRITE);
	}
	
	public static javax.microedition.io.Connection open(String name, int mode)
			throws java.io.IOException {
		return null;
	}
	
	public static java.io.DataInputStream openDataInputStream(String name)
			throws java.io.IOException {
		InputConnection con = null;
        try {
            con = (InputConnection)Connector.open(name, Connector.READ);
        } catch (ClassCastException e) {
            throw new IOException(e.toString());
        }

        try {
            return con.openDataInputStream();
        } finally {
            con.close();
        }
	}
	
	public static java.io.DataOutputStream openDataOutputStream(String name)
			throws java.io.IOException {
		
		OutputConnection con = null;
        try {
            con = (OutputConnection)Connector.open(name, Connector.WRITE);
        } catch (ClassCastException e) {
            throw new IOException(e.toString());
        }

        try {
            return con.openDataOutputStream();
        } finally {
            con.close();
        }
	}
	
	public static java.io.InputStream openInputStream(String name)
			throws java.io.IOException {
		
		return openDataInputStream(name);
	}
	
	public static java.io.OutputStream openOutputStream(String name)
			throws java.io.IOException {
		
		return openDataOutputStream(name);
	}
	
	
//	/**
//     * Create and open a Connection.
//     *
//     * @param string           The URL for the connection
//     * @param mode             The access mode
//     * @param timeouts         A flag to indicate that the caller
//     *                         wants timeout exceptions
//     * @return                 A new Connection object
//     *
//     * @exception ClassNotFoundException  If the protocol cannot be found.
//     * @exception IllegalArgumentException If a parameter is invalid.
//     * @exception ConnectionNotFoundException If the target of the
//     *   name cannot be found, or if the requested protocol type
//     *   is not supported.
//     * @exception IOException If some other kind of I/O error occurs.
//     * @exception IllegalArgumentException If a parameter is invalid.
//     */
//    private static Connection openPrim(String name, int mode,
//        boolean timeouts)
//        throws IOException, ClassNotFoundException {
//
//        /* Test for correct mode value */
//        if (mode != READ &&  mode != WRITE && mode != READ_WRITE) {
//        	
//            throw new IllegalArgumentException("Wrong mode value");            	
//        }
//
//        /* Test for null argument */
//        if (name == null) {
//            throw new IllegalArgumentException("Null URL");
//        }
//
//        /* Look for : as in "http:", "file:", or whatever */
//        int colon = name.indexOf(':');
//
//        /* Test for null argument */
//        if (colon < 1) {
//            throw new IllegalArgumentException( "no ':' in URL");
//        }
//
//        try {
//            String protocol;
//
//            /* Strip off the protocol name */
//            protocol = name.substring(0, colon);
//
//            /* sanity check the protocol name */
//            char[] chars = protocol.toCharArray();
//            for (int i = 0; i < chars.length; ++i) {
//                char c = chars[i];
//                /* only allow characters that are valid in RFC 2396
//                   alpha *( alpha | digit | "+" | "-" | "." )
//                */
//                if ( ('A' <= c && c <= 'Z') ||
//                     ('a' <= c && c <= 'z') ||
//                     ( (i > 0) && (
//                         ('0' <= c && c <= '9') ||
//                         c == '+' ||
//                         c == '-' ||
//                         c == '.'))) {
//                    continue;
//                }
//                throw new IllegalArgumentException("Invalid protocol name");
//            }
//
//            /* Strip off the rest of the string */
//            name = name.substring(colon+1);
//
//            /* Convert all the '-' characters in the protocol */
//            /* name to '_' characters (dashes are not allowed */
//            /* in class names).  This operation creates garbage */
//            /* only if the protocol name actually contains dashes */
//            protocol = protocol.replace('-', '_');
//
//            /* Use the platform and protocol names to look up */
//            /* a class to implement the connection */
//            Class clazz;
//            try {
//                clazz =
//                    Class.forName(classRoot +
//                              "." + platform +
//                              "." + protocol + ".Protocol");
//            } catch (ClassNotFoundException exc) {
//                if (classRootFallback != null) {
//                    clazz =
//                        Class.forName(classRootFallback +
//                              "." + platform +
//                              "." + protocol + ".Protocol");
//                } else {
//                    throw exc;
//                }
//            }
//
//            /* Construct a new instance of the protocol */
//            ConnectionBaseInterface uc =
//                (ConnectionBaseInterface)clazz.newInstance();
//
//            /* Open the connection, and return it */
//            return uc.openPrim(name, mode, timeouts);
//
//        } 
//        catch (InstantiationException x) {
//            throw new IOException(x.toString());
//        } 
//        catch (IllegalAccessException x) {
//            throw new IOException(x.toString());
//        } 
//        catch (ClassCastException x) {
//            throw new IOException(x.toString());
//        }
//    }
}



