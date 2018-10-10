#ifndef BLUETOOTH_H
#define BLUETOOTH_H

#include <asf.h>
#include <stdio.h>
#include "../bt_module/bt_module.h"

void bluetooth_start(void);
void bluetooth_send_byte(int byte);
int bluetooth_read_byte(void);
void bluetooth_tick(void);

#endif /* BLUETOOTH_H */
