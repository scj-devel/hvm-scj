#include <fcntl.h>
#include <unistd.h>
#include <sys/mman.h>
#include "types.h"

#define opOUTPUT_POWER 0xA4
#define opOUTPUT_START 0xA6
#define opOUTPUT_STOP  0xA3

typedef struct
{
  signed char Name[11 + 1];
  signed char Type;
  signed char Connection;
  signed char Mode;
  signed char DataSets;
  signed char Format;
  signed char Figures;
  signed char Decimals;
  signed char Views;
  float RawMin;
  float RawMax;
  float PctMin;
  float PctMax;
  float SiMin;
  float SiMax;
  unsigned short InvalidTime;
  unsigned short IdValue;
  signed char Pins;
  signed char Symbol[4 + 1];
  unsigned short Align;
}
TYPES;

typedef struct
{
  TYPES TypeData[4][8];

  unsigned short Repeat[4][300];
  signed char Raw[4][300][32];
  unsigned short Actual[4];
  unsigned short LogIn[4];

  signed char Status[4];
  signed char Output[4][32];
  signed char OutputLength[4];
}
UART;

typedef struct
{
  signed char Pressed[6];
}
UI;

int16 n_devices_ev3_Motor_setMotor(int32 *sp) {
   char motor_command[3];
   int motor_file;

   if ((motor_file = open("/dev/lms_pwm", O_WRONLY)) == -1) {
      return -1;
   }

   motor_command[0] = (char) sp[0];
   motor_command[1] = (char) sp[1];
   motor_command[2] = (char) sp[2];

   switch (motor_command[0]) {
      case opOUTPUT_POWER:
	 write(motor_file, motor_command, 3);
	 break;
      case opOUTPUT_START:
      case opOUTPUT_STOP:
	 write(motor_file, motor_command, 2);
	 break;
   }
   close(motor_file);
   return -1;
}

int16 n_devices_ev3_EV3_sleep(int32 *sp) {
    usleep(sp[0] * 1000);

    return -1;
}

int16 n_devices_ev3_UltraSonicSensor_getSensor(int32 *sp) {
   int file;
   UART *pUart; 
   unsigned char port = (unsigned char)sp[0];
   int value;

   if((file = open("/dev/lms_uart", O_RDWR | O_SYNC)) == -1)
   {
      return -1; 
   }
   
   pUart  =  (UART*)mmap(0, sizeof(UART), PROT_READ | PROT_WRITE, MAP_FILE | MAP_SHARED, file, 0);
   if (pUart == MAP_FAILED)
   {
      return -1;
   }
   
   value = (unsigned char)pUart->Raw[port][pUart->Actual[port]][1];

   sp[0] = (value << 8) | (unsigned char)pUart->Raw[port][pUart->Actual[port]][0];

   close(file);
   return -1;
}

int16 n_devices_ev3_Button_getButton(int32 *sp) {
   int file;
   UI *pButtons;

   if((file = open("/dev/lms_ui", O_RDWR | O_SYNC)) == -1)
   {
      return -1; 
   }

   pButtons  =  (UI*)mmap(0, sizeof(UI), PROT_READ | PROT_WRITE, MAP_FILE | MAP_SHARED, file, 0);

   if (pButtons == MAP_FAILED)
   {
      return -1;
   }

   sp[0] = pButtons->Pressed[sp[0]];
   close(file);
   return -1;
}
