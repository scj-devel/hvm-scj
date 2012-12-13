/**************************************************************************
 * File name  : Safelet.java
 * 
 * This file is part of our SCJ Level 0 and Level 1 implementation, 
 * based on SCJ Draft, Version 0.79. 16 May 2011.
 *
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as  
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This SCJ Level 0 and Level 1 implementation is distributed in the hope 
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the  
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this SCJ Level 0 and Level 1 implementation.  
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2012 
 * @authors  Anders P. Ravn, Aalborg University, DK
 *           Stephan E. Korsholm and Hans S&oslash;ndergaard, 
 *             VIA University College, DK
 *   
 * Description: 
 * 
 * Revision history:
 *   date   init  comment
 *
 *************************************************************************/
package javax.safetycritical;

import javax.safetycritical.annotate.Level;
import javax.safetycritical.annotate.Phase;
import javax.safetycritical.annotate.SCJAllowed;
import javax.safetycritical.annotate.SCJRestricted;

/**
 * Every safety critical application implements <code>Safelet</code> which
 * identifies a <code>MissionSequencer</code>.
 * 
 * @version 1.0; - May 2012
 * 
 * @author Anders P. Ravn, Aalborg University, 
 * <A HREF="mailto:apr@cs.aau.dk">apr@cs.aau.dk</A>, <br>
 * Hans S&oslash;ndergaard, VIA University College, Denmark, 
 * <A HREF="mailto:hso@viauc.dk">hso@viauc.dk</A>
 * 
 * @scjComment
 *  -SCJ issue: the following two methods specified in the SCJ Draft are
 *    ommitted:
 *    <ul> <code>
 *      <li> public void setUp();
 *      <li> public void tearDown();
 *    </code> </ul>
 *  -SCJ issue: In some sections we have seen 
 *      <code>Safelet&lt;Mission&gt;</code>. What shall it be? 
 * 
 */
@SCJAllowed(Level.SUPPORT) 
@SCJRestricted(Phase.INITIALIZE)
public interface Safelet
{
  /**
   * 
   * @return The <code>MissionSequencer</code> responsible for selecting
   *   the sequence of <code>Mission</code>s that represent this SCJ 
   *   application.
   */
  public MissionSequencer getSequencer();
}
