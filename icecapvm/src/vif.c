/******************************************************************************
*                                                                             *
*         N O V A  S A T E L L I T E  T E R M I N A L  F I R M W A R E        *
*                                                                             *
*                 Copyright (c) 2014-2015, e2E Satcom Limited                 *
*                                                                             *
*   This file contains proprietary and confidential information belonging     *
*   to e2E Satcom Limited; it must not be copied, distributed or otherwise    *
*   used, except as allowed by the license agreement under which this file    *
*   has been supplied.                                                        *
*                                                                             *
******************************************************************************/

# include <sys/ioctl.h>
# include <sys/types.h>
# include <sys/stat.h>
# include <fcntl.h>
# include <string.h>
# include <unistd.h>
# include <net/if.h>
# include <linux/if_tun.h>

# include "vif.h"

/*
 *  Make sure that NULL has been defined.
 */
# ifndef NULL
# define NULL ((char *) 0)
# endif

/*
 *  The TUN/TAP clone device.
 */
# define VIF_CLONE_DEV "/dev/net/tun"

/*
 *  File descriptor associated with the virtual interface.
 */
static int vif_fd = -1;

/*
 *  Initialise the virtual interface library. Returns 0 if the virtual interface
 *  was opened correctly, or -1 if an error was detected.
 */
int vif_initialise (char *vif_name, vif_layer_t vif_layer)
{

   int vif_clone_fd;
   struct ifreq ifr;

   /*
    *  Make sure that we have supplied valid arguments.
    */
   if (vif_name == NULL)
   {

      return -1;

   }
   else if ((vif_layer != LAYER_2) && (vif_layer != LAYER_3))
   {

      return -1;

   }

   /*
    *  Make sure that the virtual interface has not already been opened.
    */
   if (vif_fd != -1)
   {

      return -1;

   }

   /*
    *  We must run this as the superuser. Force the process UID and GID to 0.
    */
   if ((setuid (0) != 0) || (setgid (0) != 0))
   {

      return -1;

   }

   /*
    *  Make sure that the "ifr" structure is cleared.
    */
   memset (&ifr, 0, sizeof (ifr));

   /*
    *  Set up the device flags (depends on the network layer we are planning
    *  on dealing with).
    */
   if (vif_layer == LAYER_2)
   {

      ifr.ifr_flags = IFF_TAP | IFF_NO_PI;

   }
   else /* (vif_layer == LAYER_3) */
   {

      ifr.ifr_flags = IFF_TUN | IFF_NO_PI;


   }

   /*
    *  Copy the virtual interface device name.
    */
   strncpy (ifr.ifr_name, vif_name, IFNAMSIZ);

   /*
    *  Open the TUN/TAP clone device.
    */
   vif_clone_fd = open (VIF_CLONE_DEV, O_RDWR);
   if (vif_clone_fd < 0)
   {

      return -1;

   }

   /*
    *  Try to create the virtual interface device.
    */
    if (ioctl (vif_clone_fd, TUNSETIFF, (void *) &ifr) < 0)
    {

       close (vif_clone_fd);
       return -1;

    }

   /*
    *  Store the virtual interface file descriptor.
    */
   vif_fd = vif_clone_fd;

   /*
    *  No errors detected.
    */
   return 0;

}

/*
 *  Read a frame (Layer-2) or packet (Layer-3) from the virtual interface into
 *  a buffer (whose size is specified by the last argument). Returns the size
 *  of the frame/packet, or -1 if an error was detected.
 */
int vif_read (void *vif_buffer, int vif_buffer_size)
{

   int vif_nbytes;

   /*
    *  Make sure that the virtual interface has been opened.
    */
   if (vif_fd == -1)
   {

      return -1;

   }

   /*
    *  Don't do anything if a zero-length buffer has been specified.
    */
   if (vif_buffer_size == 0)
   {

      return 0;

   }

   /*
    *  Attempt to read a packet from the virtual interface.
    */
   vif_nbytes = read (vif_fd, vif_buffer, vif_buffer_size);
   if (vif_nbytes < 0)
   {

      return -1;

   }

   /*
    *  Return the number of bytes read from the buffer.
    */
   return vif_nbytes;

}

/*
 *  Write a frame (Layer-2) or packet (Layer-3) from the virtual interface from
 *  a buffer (whose size is specified by the last argument). Returns 0 if the
 *  frame/packet was successfully written, or -1 if an error was detected.
 */
int vif_write (char *vif_buffer, int vif_buffer_size)
{

   int vif_nbytes;

   /*
    *  Make sure that the virtual interface has been opened.
    */
   if (vif_fd == -1)
   {
  
      return -1;

   }

   /*
    *  Don't do anything if a zero-length buffer has been specified.
    */
   if (vif_buffer_size == 0)
   {
 
      return 0;

   }

   /*
    *  Attempt to write the packet from the virtual interface.
    */
   vif_nbytes = write (vif_fd, vif_buffer, vif_buffer_size);
   if (vif_nbytes < 0)
   {

      return -1;

   }

   /*
    *  Return the number of bytes written to the buffer.
    */
 
   return vif_nbytes;

}
