#include "types.h"
#include "methods.h"
#include "classes.h"
#include "ostypes.h"
#include "gc.h"
#include "allocation_point.h"
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
extern const ClassInfo *classes;
volatile uint32 z;


int16 n_client_SCJLevel1SocketSDEClient_vif_ini(int32 *sp)
{ char *str= sp[0];
    
  int16 y= vif_initialise("sat0",3);
  printf("%d\n",y);
 
  return -1;

}


int16 n_client_SCJLevel1SocketSDEClient_vif_write(int32 *sp)
{

    // printStr("Good morning");
//int32 x=sp[0];
//int32 buffer2[1200];
 // buffer2=x;
   
 //int32 numverofbytes;
//numverofbytes = vif_write(&buffer2,1200);
  //to keep the application running
//while(x>-3){};
//  printf("%d\n",buffer2[0]
    
//  );

//printf("number of bytes written");
 // printf("%d\n",numverofbytes);
  return -1;
}


int16 n_client_SCJLevel1SocketSDEClient_SocketHVM(){
  
 
  
  int sd, sd2, rc, length = sizeof(int);
int totalcnt = 0, on = 1;
char temp;
unsigned char buffer[BufferLength];
struct sockaddr_in serveraddr;
struct sockaddr_in their_addr;
 
fd_set read_fd;
struct timeval timeout;
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
//perror("Server-socket() error");
/* Just exit */
exit (-1);
}
else
//printf("Server-socket() is OK\n");
 
/* The setsockopt() function is used to allow */
/* the local address to be reused when the server */
/* is restarted before the required wait time */
/* expires. */
/***********************************************/
/* Allow socket descriptor to be reusable */
if((rc = setsockopt(sd, SOL_SOCKET, SO_REUSEADDR, (char *)&on, sizeof(on))) < 0)
{
//perror("Server-setsockopt() error");
close(sd);
exit (-1);
}
else
//printf("Server-setsockopt() is OK\n");
 
/* bind to an address */
memset(&serveraddr, 0x00, sizeof(struct sockaddr_in));
serveraddr.sin_family = AF_INET;
serveraddr.sin_port = htons(SERVPORT2);
serveraddr.sin_addr.s_addr = htonl(INADDR_ANY);
 
//printf("Using %s, listening at %d\n", "192.168.0.18", SERVPORT2);
 
/* After the socket descriptor is created, a bind() */
/* function gets a unique name for the socket. */
/* In this example, the user sets the */
/* s_addr to zero, which allows the system to */
/* connect to any client that used port 3005. */
if((rc = bind(sd, (struct sockaddr *)&serveraddr, sizeof(serveraddr))) < 0)
{
//perror("Server-bind() error");
/* Close the socket descriptor */
close(sd);
/* and just exit */
exit(-1);
}
else
  //  printf("Server-bind() is OK\n");
 //
/* The listen() function allows the server to accept */
/* incoming client connections. In this example, */
/* the backlog is set to 10. This means that the */
/* system can queue up to 10 connection requests before */
/* the system starts rejecting incoming requests.*/
/*************************************************/
/* Up to 10 clients can be queued */
if((rc = listen(sd, 10)) < 0)
{
  //  perror("Server-listen() error");
    close(sd);
    exit (-1);
}
else
 //   printf("Server-Ready for client connection...\n")
  ;
 
/* The server will accept a connection request */
/* with this accept() function, provided the */
/* connection request does the following: */
/* - Is part of the same address family */
/* - Uses streams sockets (TCP) */
/* - Attempts to connect to the specified port */
/***********************************************/
/* accept() the incoming connection request. */
int sin_size = sizeof(struct sockaddr_in);
if((sd2 = accept(sd, (struct sockaddr *)&their_addr, &sin_size)) < 0)
{
  //  perror("Server-accept() error");
    close(sd);
    exit (-1);
}
else
   // printf("Server-accept() is OK\n");
 
/*client IP*/
//printf("Server-new socket, sd2 is OK...\n");
//printf("Got connection from the following client: %s\n", inet_ntoa(their_addr.sin_addr));
 
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
int16 i;

//the last successful run of the applications
// printf("data recived from the client");
//  for(i=0;i<=70;i++){
// printf("  %02x", buffer[i]);};
// printf("\n");





if(rc < 0)
{
 //   perror("Server-read() error");
    close(sd);
    close(sd2);
    exit (-1);
}
else if (rc == 0)
{
  //  printf("Client program has issued a close()\n");
    close(sd);
    close(sd2);
    exit(-1);
}
else
{
    totalcnt += rc;
  //  printf("Server-read() is OK\n");
  //  printf("Here is the buffer read (%d): %d\n\n", sizeof(buffer), buffer);
  //   printf("totalcnt = %d\n",totalcnt);
   //    printf("buffer0 = %d\n",buffer[0]);
   //           printf("buffer1 = %d\n",buffer[1]);

}
 
}
}
else if (rc < 0)
{
  //  perror("Server-select() error");
    close(sd);
    close(sd2);
    exit(-1);
}
/* rc == 0 */
else
{
  //  printf("Server-select() timed out.\n");
    close(sd);
    close(sd2);
    exit(-1);
}
 
/* Shows the data */
//printf("Received data from the following client: %s\n", buffer);
 //writing to the virtualinterface

   
 int32 numverofbytes;
numverofbytes = vif_write(&buffer,1500);
 //printf("number of bytes written is %d\n",numverofbytes  );
  //to keep the application running
//while(x>-3){};
 // printf("%d\n",buffer[0]  );
/* Echo some bytes of string, back */
/* to the client by using the write() */
/* function. */
/************************************/
/* write() some bytes of string, */
/* back to the client. */
//printf("Server-Echoing back to client...\n");
//rc = write(sd2, buffer, totalcnt);
//if(rc != totalcnt)
//{
//perror("Server-write() error");
/* Get the error number. */
//rc = getsockopt(sd2, SOL_SOCKET, SO_ERROR, &temp, &length);
//if(rc == 0)
//{
    /* Print out the asynchronously */
    /* received error. */
  //  errno = temp;
   // perror("SO_ERROR was: ");
//}
//else
  //  printf("Server-write() is OK\n");
 
//close(sd);
//close(sd2);
//exit(-1);
//}
 
/* When the data has been sent, close() */
/* the socket descriptor that was returned */
/* from the accept() verb and close() the */
/* original socket descriptor. */
/*****************************************/
/* Close the connection to the client and */
/* close the server listening socket. */
/******************************************/
close(sd2);
close(sd);
//exit(0);
return -1;
}
 
int16 n_client_SCJLevel1SocketSDEClient_SocketHVMClient(int32 *sp, char *argv[]){
  unsigned char buffer1[1500];
 z=vif_read(&buffer1,1500);
 z=buffer1[0];
 int16  i;
 //the last successful run of the application
//  printf("data sent from the client");
//  for(i=0;i<=70;i++){
// printf("  %02x", buffer1[i]);};
// printf("\n");
 
 
 

/* Variable and structure definitions. */
int sd, rc, length = sizeof(int);
struct sockaddr_in serveraddr;

char server[255]="10.0.0.3";
char temp;

int totalcnt = 0;
int argc=5;
struct hostent *hostp;
char data[100] = "This is a test string from client lol!!! ";
 
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
//perror("Client-socket() error");
exit(-1);
}
else
//printf("Client-socket() OK\n");
/*If the server hostname is supplied*/
if(argc > 1)
{
/*Use the supplied argument*/
//strcpy(server, argv[1]);
//printf("Connecting to the following %s, port %d ...\n", "192.168.0.18", SERVPORT);
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
//printf("HOST NOT FOUND --> ");
/* h_errno is usually defined */
/* in netdb.h */
//printf("h_errno = %d\n",h_errno);
//printf("---This is a client program---\n");
//printf("Command usage: %s <server name or IP>\n", "192.168.0.18");
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
//perror("Client-connect() error");
//close(sd);
//exit(-1);
}

//printf("Connection established...\n");
 
/* Send string to the server using */
/* the write() function. */
/*********************************************/
/* Write() some string to the server. */
//printf("Sending some string to the following %s...\n", server);
rc = write(sd, buffer1, 1500);
 
if(rc < 0)
{
//perror("Client-write() error");
rc = getsockopt(sd, SOL_SOCKET, SO_ERROR, &temp, &length);
if(rc == 0)
{
/* Print out the asynchronously received error. */
errno = temp;
//perror("SO_ERROR was");
}
close(sd);
exit(-1);
}
else
{
//printf("Client-write() is OK\n");
//printf("String successfully sent lol!\n");

}
 
//totalcnt = 0;
//while(totalcnt < BufferLength)
//{
 
/* Wait for the server to echo the */
/* string by using the read() function. */
/***************************************/
/* Read data from the server. */
//rc = read(sd, &buffer1[totalcnt], BufferLength-totalcnt);
//if(rc < 0)
//{
//perror("Client-read() error");
//close(sd);
//exit(-1);
//}
//else if (rc == 0)
//{
//printf("Server program has issued a close()\n");
//close(sd);
//exit(-1);
//}
//else
//totalcnt += rc;
//}
//printf("Client-read() is OK\n");
//printf("Echoed data from the f***ing server: %s\n", buffer1);
 
/* When the data has been read, close() */
/* the socket descriptor. */
/****************************************/
/* Close socket descriptor from client side. */
close(sd);
//exit(0);
return -1;
}

