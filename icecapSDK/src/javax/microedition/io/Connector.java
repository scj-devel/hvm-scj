package javax.microedition.io;

import java.io.IOException;

import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.io.ConnectionFactory;
import javax.safetycritical.io.ConsoleConnection;

public class Connector {

	@SCJAllowed
	public static final int READ = 1;
	
	@SCJAllowed
	public static final int WRITE = 2;
	
	@SCJAllowed
	public static final int READ_WRITE = 3;
	
	private static final String SerialPort_URI = "HVMSerialPortAnd:PortNumber"; // to be implemented
	private static ConsoleConnection consoleCon;  // The default connection
    
	static {
		try {
			consoleCon = 
				new ConsoleConnection(SerialPort_URI);  // The default connection
		}
		catch (ConnectionNotFoundException e) {
			e.toString();
		}
	}
	
	// No instantiation of class Connector
	private Connector() {
	}
	
	public static javax.microedition.io.Connection open(String name)
			throws java.io.IOException {
		return open(name, READ_WRITE);
	}
	
	public static javax.microedition.io.Connection open(String name, int mode)
			throws java.io.IOException {
		
	  /* Test for correct mode value */
      if (mode != READ &&  mode != WRITE && mode != READ_WRITE) {
      	
          throw new IllegalArgumentException("Wrong mode value");            	
      }
      /* Test for null argument */
      if (name == null || name.length() == 0) {
        throw new IllegalArgumentException("URL is null or empty");
      }
            
      /* Look for : as in "http:", "file:", or whatever */
      int separatorIndex = name.indexOf(':');

      /* Test for null argument */
      if (separatorIndex < 1) {
        throw new IllegalArgumentException( "no ':' in URL");
      }
      
//      /* Get the protocol name */
//      String scheme = name.substring(0, separatorIndex);
//      /* Get the rest of the string */
//      String rest = name.substring(separatorIndex+1);
      
      ConnectionFactory factory = getFactory(name);
      Connection retval = null;
       
      if (factory != null) {
    	  
          retval = factory.create(name);
      } 
      else {

    	  // fall back to console connection.
    	  try {
    		  retval = consoleCon;
    	  } 
    	  catch (Exception e) {
    		  throw new ConnectionNotFoundException();
          }
      }

      if (retval == null)
          throw new ConnectionNotFoundException();
      else
          return retval;
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
	
	private static ConnectionFactory getFactory(String scheme) {
		
		/* If scheme is the name of a ConnectionFactory, it should be 
		 * possible to get a connection factory with this name;
		 * see ConnectionFactory with a connectionFactorySet of all
		 * registered ConnectionFactory'es.
		 * 
		 * The create(String url) in ConnectionFactory creates and returns 
		 * the correct Connection.		
		 */
		
		// ToDo: implement it.
		return null;
	}
	

}


// From knopflerfish:

/*
class ConnectorServiceImpl implements ConnectorService {

  private static BundleContext bc = null;

  public ConnectorServiceImpl(BundleContext bc) {
    this.bc = bc;
  }

  public Connection open(String uri) throws IOException {
    return open(uri, ConnectorService.READ_WRITE);
  }

  public Connection open(String uri, int mode) throws IOException {
    return open(uri, mode, false);
  }

  public Connection open(String uri, int mode, boolean timeouts)
    throws IOException
  {
    if (mode != Connector.READ &&
        mode != Connector.WRITE &&
        mode != Connector.READ_WRITE)
      throw new IllegalArgumentException("Variable mode has an invalid value");

    if (null==uri || uri.length()==0)
      throw new IllegalArgumentException("URI must not be null or empty");

    int schemeSeparator = uri.indexOf(':');

    if (schemeSeparator < 0) {
      throw new IllegalArgumentException("Not a valid URI");
    }

    String scheme = uri.substring(0, schemeSeparator);

    ConnectionFactory factory = getFactory(scheme);
    Connection retval = null;

    if (factory != null) {
      retval = factory.createConnection(uri, mode, timeouts);

    } else {

      // fall back to Connector.
      try {
        retval = Connector.open(uri, mode, timeouts);
      } catch (Exception e) {
        throw new ConnectionNotFoundException();
      }

    }

    if (retval == null)
      throw new ConnectionNotFoundException();
    else
      return retval;
  }


  private ConnectionFactory getFactory(String scheme) {
    ServiceReference[] refs = null;

    try {
      refs =
        bc.getServiceReferences(ConnectionFactory.class.getName(), null);
    } catch (InvalidSyntaxException e) {
      // this should not be happening
    }

    if (refs == null) {
      return null;
    }

    ArrayList list = new ArrayList(); // matching services

    for (int i = 0; i < refs.length; i++) {
      String[] tmp = (String[])refs[i].getProperty(ConnectionFactory.IO_SCHEME);

      for (int o = 0; o < tmp.length; o++) {

        if (scheme.equalsIgnoreCase(tmp[o])) {
          list.add(refs[i]);
          break;
        }
      }
    }

    if (list.isEmpty()) {
      return null;
    }

    ServiceReference bestRef = (ServiceReference)list.get(0);

    if (list.size() == 1)
      return (ConnectionFactory)bc.getService(bestRef);

    int bestRanking = getRanking(bestRef);
    int rank;
    ServiceReference ref;

    for (int i = 1; i < list.size(); i++) {
      ref = (ServiceReference)list.get(i);
      rank = getRanking(ref);

      if (rank > bestRanking) { // by highest rank, then lowest service id.
        bestRanking = rank;
        bestRef = ref;

      } else {
        if (rank == bestRanking) {

          Long l1 = (Long)ref.getProperty(Constants.SERVICE_ID);
          Long l2 = (Long)bestRef.getProperty(Constants.SERVICE_ID);

          if (l1.compareTo(l2) < 0) {
            bestRef = ref;
          }
        }
      }
    }
    return (ConnectionFactory)bc.getService(bestRef);
  }
*/
  /* Returns 0 (default value) if there is not rank for
     the specificed service or if it has an invalid rank.
  */
/*
  private int getRanking(ServiceReference ref) {

    Object rank =
      (Object)ref.getProperty(Constants.SERVICE_RANKING);

    if (rank == null)
      return 0;

    if (rank instanceof Integer)
      return ((Integer)rank).intValue();
    else
      return 0;

  }
*/
/*
  public DataInputStream openDataInputStream(String name) throws IOException {
    Connection con = open(name, ConnectorService.READ, false);

    if (con instanceof InputConnection) {
      DataInputStream stream = ((InputConnection)con).openDataInputStream();
      con.close();
      return stream;
    }
    con.close();
    throw new IOException("Given scheme does not support input");
  }


  public DataOutputStream openDataOutputStream(String name) throws IOException {
    Connection con = open(name, ConnectorService.WRITE, false);

    if (con instanceof OutputConnection) {
      DataOutputStream stream = ((OutputConnection)con).openDataOutputStream();
      con.close();
      return stream;
    }

    con.close();
    throw new IOException("Given scheme does not support output");
  }


  public InputStream openInputStream(String name) throws IOException {

    Connection con = open(name, ConnectorService.READ, false);

    if (con instanceof InputConnection) {
      InputStream stream = ((InputConnection)con).openInputStream();
      con.close();
      return stream;
    }

    con.close();
    throw new IOException("Given scheme does not support input");
  }


  public OutputStream openOutputStream(String name) throws IOException {
    Connection con = open(name, ConnectorService.WRITE, false);

    if (con instanceof OutputConnection) {
      OutputStream stream = ((OutputConnection)con).openOutputStream();
      con.close();
      return stream;
    }

    con.close();
    throw new IOException("Given scheme does not support output");
  }
}

*/


///**
// * The platform name.
// */
//private static String platform;
//
///**
// * The root of the classes.
// */
//private static String classRoot;
//
///**
// * The fallback root of the classes.
// */
//private static String classRootFallback;
//
///**
// * Class initializer.
// */
//static {
//    /* Set up the platform name */
//    platform = System.getProperty("microedition.platformimpl");
//    if ((platform == null) || (platform.equals("generic"))) {
//        platform = "j2me";
//    }
//
//    /* Set up the library class root path */
//    /* This may vary from one CLDC implementation to another */
//    classRoot = System.getProperty("javax.microedition.io.Connector.protocolpath");
//    if (classRoot == null) {
//        //classRoot = "com.sun.cldc.io";
//        classRoot = "javax.microedition.io";
//    }
//    classRootFallback = System.getProperty(
//        "javax.microedition.io.Connector.protocolpath.fallback");
//}

///**
//* Create and open a Connection.
//*
//* @param string           The URL for the connection
//* @param mode             The access mode
//* @param timeouts         A flag to indicate that the caller
//*                         wants timeout exceptions
//* @return                 A new Connection object
//*
//* @exception ClassNotFoundException  If the protocol cannot be found.
//* @exception IllegalArgumentException If a parameter is invalid.
//* @exception ConnectionNotFoundException If the target of the
//*   name cannot be found, or if the requested protocol type
//*   is not supported.
//* @exception IOException If some other kind of I/O error occurs.
//* @exception IllegalArgumentException If a parameter is invalid.
//*/
//private static Connection openPrim(String name, int mode,
// boolean timeouts)
// throws IOException, ClassNotFoundException {
//
// /* Test for correct mode value */
// if (mode != READ &&  mode != WRITE && mode != READ_WRITE) {
// 	
//     throw new IllegalArgumentException("Wrong mode value");            	
// }
//
// /* Test for null argument */
// if (name == null) {
//     throw new IllegalArgumentException("Null URL");
// }
//
// /* Look for : as in "http:", "file:", or whatever */
// int colon = name.indexOf(':');
//
// /* Test for null argument */
// if (colon < 1) {
//     throw new IllegalArgumentException( "no ':' in URL");
// }
//
// try {
//     String protocol;
//
//     /* Strip off the protocol name */
//     protocol = name.substring(0, colon);
//
//     /* sanity check the protocol name */
//     char[] chars = protocol.toCharArray();
//     for (int i = 0; i < chars.length; ++i) {
//         char c = chars[i];
//         /* only allow characters that are valid in RFC 2396
//            alpha *( alpha | digit | "+" | "-" | "." )
//         */
//         if ( ('A' <= c && c <= 'Z') ||
//              ('a' <= c && c <= 'z') ||
//              ( (i > 0) && (
//                  ('0' <= c && c <= '9') ||
//                  c == '+' ||
//                  c == '-' ||
//                  c == '.'))) {
//             continue;
//         }
//         throw new IllegalArgumentException("Invalid protocol name");
//     }
//
//     /* Strip off the rest of the string */
//     name = name.substring(colon+1);
//
//     /* Convert all the '-' characters in the protocol */
//     /* name to '_' characters (dashes are not allowed */
//     /* in class names).  This operation creates garbage */
//     /* only if the protocol name actually contains dashes */
//     protocol = protocol.replace('-', '_');
//
//     /* Use the platform and protocol names to look up */
//     /* a class to implement the connection */
//     Class clazz;
//     try {
//         clazz =
//             Class.forName(classRoot +
//                       "." + platform +
//                       "." + protocol + ".Protocol");
//     } catch (ClassNotFoundException exc) {
//         if (classRootFallback != null) {
//             clazz =
//                 Class.forName(classRootFallback +
//                       "." + platform +
//                       "." + protocol + ".Protocol");
//         } else {
//             throw exc;
//         }
//     }
//
//     /* Construct a new instance of the protocol */
//     ConnectionBaseInterface uc =
//         (ConnectionBaseInterface)clazz.newInstance();
//
//     /* Open the connection, and return it */
//     return uc.openPrim(name, mode, timeouts);
//
// } 
// catch (InstantiationException x) {
//     throw new IOException(x.toString());
// } 
// catch (IllegalAccessException x) {
//     throw new IOException(x.toString());
// } 
// catch (ClassCastException x) {
//     throw new IOException(x.toString());
// }
//}




