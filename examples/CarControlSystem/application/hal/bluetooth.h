#ifndef BLUETOOTH_H
#define BLUETOOTH_H

void bluetooth_start();
void bluetooth_send_byte(int byte);
int bluetooth_read_byte();

#endif /* BLUETOOTH_H */
