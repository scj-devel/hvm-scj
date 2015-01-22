#include "types.h"
#include "methods.h"
#include "classes.h"
#include "ostypes.h"
#include "gc.h"
#include "allocation_point.h"
#include "vif.h"
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>
#include <arpa/inet.h> 
#ifdef PRINTFSUPPORT
#include <stdio.h>
#endif
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <unistd.h>
#include <errno.h>
#include <unistd.h>
/* BufferLength is 100 bytes */
#define BufferLength 1500
/* Default host name of server system. Change it to your default */
/* server hostname or IP.  If the user do not supply the hostname */
/* as an argument, the_server_name_or_IP will be used as default*/
#define SERVER "The_server_name_or_IP"
/* Server's port number */
#define SERVPORT 3111
#define SERVPORT2 3112

volatile uint32 z;

int16 n_client_SCJLevel1SocketClient_vif_read(int32 *sp)
{ 
   vif_initialise("sat0",3);
 
   return -1;
}

int16 n_client_SCJLevel1SocketClient_vif_write(int32 *sp)
{
   return -1;
}


int16 n_client_SCJLevel1SocketClient_SocketHVM(){
   int sd, sd2, rc;
   int totalcnt = 0, on = 1;
   unsigned char buffer[BufferLength];
   struct sockaddr_in serveraddr;
   struct sockaddr_in their_addr;
   int sin_size;
   fd_set read_fd;
   struct timeval timeout;
   int16 i;
   int32 numverofbytes;
   timeout.tv_sec = 15;
   timeout.tv_usec = 0;

/* The socket() function returns a socket descriptor */
/* representing an endpoint. The statement also */
/* identifies that the INET (Internet Protocol) */
/* address family with the TCP transport (SOCK_STREAM) */
/* will be used for this socket. */
/************************************************/
/* Get a socket descriptor */
   if((sd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
   {

/* Just exit */
      exit (-1);
   }
   else
 
/* The setsockopt() function is used to allow */
/* the local address to be reused when the server */
/* is restarted before the required wait time */
/* expires. */
/***********************************************/
/* Allow socket descriptor to be reusable */
      if((rc = setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, (char *)&on, sizeof(on))) < 0)
      {
	 close(sd);
	 exit (-1);
      }
      else
 
/* bind to an address */
	 memset(&serveraddr, 0x00, sizeof(struct sockaddr_in));
   serveraddr.sin_family = AF_INET;
   serveraddr.sin_port = htons(SERVPORT2);
   serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);
 
/* After the socket descriptor is created, a bind() */
/* function gets a unique name for the socket. */
/* In this example, the user sets the */
/* s_addr to zero, which allows the system to */
/* connect to any client that used port 3005. */
   if((rc = bind(sd, (struct sockaddr *)&serveraddr, sizeof(serveraddr))) < 0)
   {
/* Close the socket descriptor */
      close(sd);
/* and just exit */
      exit(-1);
   }
   else
/* The listen() function allows the server to accept */
/* incoming client connections. In this example, */
/* the backlog is set to 10. This means that the */
/* system can queue up to 10 connection requests before */
/* the system starts rejecting incoming requests.*/
/*************************************************/
/* Up to 10 clients can be queued */
      if((rc = listen(sd, 10)) < 0)
      {
	 close(sd);
	 exit (-1);
      }
      else
	 ;
 
/* The server will accept a connection request */
/* with this accept() function, provided the */
/* connection request does the following: */
/* - Is part of the same address family */
/* - Uses streams sockets (TCP) */
/* - Attempts to connect to the specified port */
/***********************************************/
/* accept() the incoming connection request. */
   sin_size = sizeof(struct sockaddr_in);
   if((sd2 = accept(sd, (struct sockaddr *)&their_addr, (socklen_t *)&sin_size)) < 0)
   {
      close(sd);
      exit (-1);
   }
   else
 
/*client IP*/
/* The select() function allows the process to */
/* wait for an event to occur and to wake up */
/* the process when the event occurs. In this */
/* example, the system notifies the process */
/* only when data is available to read. */
/***********************************************/
/* Wait for up to 15 seconds on */
/* select() for data to be read. */
      FD_ZERO(&read_fd);
   FD_SET(sd2, &read_fd);
   rc = select(sd2+1, &read_fd, NULL, NULL, &timeout);
   if((rc == 1) && (FD_ISSET(sd2, &read_fd)))
   {
/* Read data from the client. */
      totalcnt = 0;
 
      while(totalcnt < BufferLength)
      {
/* When select() indicates that there is data */
/* available, use the read() function to read */
/* 100 bytes of the string that the */
/* client sent. */
/***********************************************/
/* read() from client */
	 rc = read(sd2, buffer, 1500);

	 printf("data recived from the client");
	 for(i=0;i<=70;i++){
	    printf("  %02x", buffer[i]);};
	 printf("\n");





	 if(rc < 0)
	 {
	    close(sd);
	    close(sd2);
	    exit (-1);
	 }
	 else if (rc == 0)
	 {
	    close(sd);
	    close(sd2);
	    exit(-1);
	 }
	 else
	 {
	    totalcnt += rc;
	 }
 
      }
   }
   else if (rc < 0)
   {
      close(sd);
      close(sd2);
      exit(-1);
   }
/* rc == 0 */
   else
   {
      close(sd);
      close(sd2);
      exit(-1);
   }
 
/* Shows the data */
   
   numverofbytes = vif_write((char*)&buffer,1500);
   printf("number of bytes written is %d\n",numverofbytes  );
   close(sd2);
   close(sd);
   return -1;
}
 
int16 n_client_SCJLevel1SocketClient_SocketHVMClient(int32 *sp, char *argv[]){
   int16  i;
   unsigned char buffer1[1500];
   int sd, rc, length = sizeof(int);
   struct sockaddr_in serveraddr;
   char server[255]="192.168.0.18";
   char temp;
   int argc=5;
   struct hostent *hostp;

   z=vif_read(&buffer1,1500);
   z=buffer1[0];
   printf("data sent from the client");
   for(i=0;i<=70;i++){
      printf("  %02x", buffer1[i]);};
   printf("\n");
 
/* Variable and structure definitions. */
 
/* The socket() function returns a socket */
/* descriptor representing an endpoint. */
/* The statement also identifies that the */
/* INET (Internet Protocol) address family */
/* with the TCP transport (SOCK_STREAM) */
/* will be used for this socket. */
/******************************************/
/* get a socket descriptor */
   if((sd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
   {
      exit(-1);
   }
   else
/*If the server hostname is supplied*/
      if(argc > 1)
      {
/*Use the supplied argument*/
      }
      else
/*Use the default server name or IP*/
	 strcpy(server, SERVER);
 
   memset(&serveraddr, 0x00, sizeof(struct sockaddr_in));
   serveraddr.sin_family = AF_INET;
   serveraddr.sin_port = htons(SERVPORT);
 
   if((serveraddr.sin_addr.s_addr = inet_addr(server)) == (unsigned long)INADDR_NONE)
   {
 
/* When passing the host name of the server as a */
/* parameter to this program, use the gethostbyname() */
/* function to retrieve the address of the host server. */
/***************************************************/
/* get host address */
      hostp = gethostbyname(server);
      if(hostp == (struct hostent *)NULL)
      {
	 close(sd);
	 exit(-1);
      }
      memcpy(&serveraddr.sin_addr, hostp->h_addr, sizeof(serveraddr.sin_addr));
   }
 
/* After the socket descriptor is received, the */
/* connect() function is used to establish a */
/* connection to the server. */
/***********************************************/
/* connect() to server. */
   while((rc = connect(sd, (struct sockaddr *)&serveraddr, sizeof(serveraddr))) < 0)
   {
   }

 
/* Send string to the server using */
/* the write() function. */
/*********************************************/
/* Write() some string to the server. */
   rc = write(sd, buffer1, 1500);
 
   if(rc < 0)
   {
      rc = getsockopt(sd, SOL_SOCKET, SO_ERROR, &temp, (socklen_t *)&length);
      if(rc == 0)
      {
/* Print out the asynchronously received error. */
	 errno = temp;
      }
      close(sd);
      exit(-1);
   }
   else
   {

   }
 
 
/* Wait for the server to echo the */
/* string by using the read() function. */
/***************************************/
/* Read data from the server. */
 
/* When the data has been read, close() */
/* the socket descriptor. */
/****************************************/
/* Close socket descriptor from client side. */
   close(sd);
   return -1;
}


