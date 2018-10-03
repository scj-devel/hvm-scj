#include "bluetooth.h"

void bluetooth_start()
{
	printf("called bluetooth_start\n");
	//TODO: implement adapter for bluetooth drivers
}

void bluetooth_send_byte(int byte)

{
	printf("called bluetooth_send_byte with parameter %H\n", byte);
	//TODO: implement adapter for bluetooth drivers
}

int bluetooth_read_byte()
{
	printf("called bluetooth_read_byte\n");
	//TODO: implement adapter for bluetooth drivers
	return 42;
}
