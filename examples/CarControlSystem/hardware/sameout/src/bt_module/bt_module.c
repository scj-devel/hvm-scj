/*
 * bt_module.c
 *
 * Created: 05-06-2016 10:19:36
 *  Author: IHA
 */

#include <asf.h>
#include "bt_module.h"
#include "../dialog_handler/dialog_handler.h"
#include "../serial/iha_serial.h"

// dialog sequences to setup BT module
typedef enum { eENTER_CMD0=0, eENTER_CMD1, eAUTHENTICATION, eNAME, eREBOOT1, eREBOOT2, eERROR_REBOOT1,  eERROR_REBOOT2} en_init_dialog_states;
// BT dialog
dialog_seq_t _dialog_bt_init_seq[] = {
	{ 0, LEN (0), (uint8_t *)"DUMMY", LEN (5), TO (5), eENTER_CMD1, eENTER_CMD1, DIALOG_NO_BUFFER }, // eENTER_CMD0: Enter command mode
	{ (uint8_t *)"$$$", LEN (3), (uint8_t *)"CMD\x0D\x0A",LEN (5), TO (10), eAUTHENTICATION, eERROR_REBOOT1, DIALOG_NO_BUFFER }, // eENTER_CMD1
	{ (uint8_t *)"SA,1\x0D", LEN (5), (uint8_t *)"AOK\x0D\x0A",LEN (5), TO (10), eNAME, eERROR_REBOOT1, DIALOG_NO_BUFFER }, // eAUTHENTICATION: Set to mode 1 
	{ (uint8_t *)"S-,VIA-CAR\x0D", LEN (11), (uint8_t *)"AOK\x0D\x0A",LEN (5), TO (10), eREBOOT1, eERROR_REBOOT1, DIALOG_NO_BUFFER }, // eNAME: Set device name
	{ (uint8_t *)"R,1\x0D", LEN (4), (uint8_t *)"Reboot!\x0D\x0A",LEN (9), TO (10), eREBOOT2, eERROR_REBOOT1, DIALOG_NO_BUFFER }, // eREBOOT1: Send R,1
	{ 0, LEN (0), (uint8_t *)"DUMMY",LEN (5), TO (5), DIALOG_OK_STOP, DIALOG_OK_STOP, DIALOG_NO_BUFFER }, // eREBOOT2: Just a pause to wait for Reboot
	{ (uint8_t *)"R,1\x0D", LEN (4), (uint8_t *)"Reboot!\x0D\x0A",LEN (9), TO (10), eERROR_REBOOT2, DIALOG_ERROR_STOP, DIALOG_NO_BUFFER }, // eERROR_REBOOT1: Send R,1
	{ 0, LEN (0), (uint8_t *)"DUMMY",LEN (5), TO (5), DIALOG_ERROR_STOP, DIALOG_ERROR_STOP, DIALOG_NO_BUFFER }, // eERROR_REBOOT2: Just a pause to wait for Reboot
};

static uint8_t _bt_dialog_active = 0;
// Pointer to Application BT call back functions
static void (*_app_bt_status_call_back) (uint8_t result, uint8_t byte) = NULL;

static serial_p _bt_serial_instance = NULL;
static fifo_desc_t *_bt_rx_fifo_desc = NULL;


// ----------------------------------------------------------------------------------------------------------------------
uint8_t bt_send_bytes (uint8_t *bytes, uint8_t len)
{
	return serial_send_bytes (_bt_serial_instance, bytes, len);
}


// ----------------------------------------------------------------------------------------------------------------------
static void _bt_send_bytes (uint8_t *bytes, uint8_t len)
{
	serial_send_bytes (_bt_serial_instance, bytes, len);
}

// ----------------------------------------------------------------------------------------------------------------------
static void _bt_status_call_back (uint8_t result)
{
	_bt_dialog_active = 0;
	if (result == DIALOG_OK_STOP) {
		fifo_flush(_bt_rx_fifo_desc);
	}
	
	if (_app_bt_status_call_back) {
		_app_bt_status_call_back (result, 0);
	}
}

// ----------------------------------------------------------------------------------------------------------------------
static void _bt_call_back (serial_p bt_serial_instance, uint8_t serial_last_received_byte)
{
	if (_bt_dialog_active) {
		dialog_byte_received (serial_last_received_byte);
	} else {
		if ( _bt_rx_fifo_desc != NULL) {
			_app_bt_status_call_back (BT_CONNECTED, serial_last_received_byte);
		}
	}
}


// ----------------------------------------------------------------------------------------------------------------------
void bt_module_init (void (*bt_status_call_back) (uint8_t result, uint8_t byte), serial_p bt_serial_instance)
{
	_bt_serial_instance = bt_serial_instance;
	serial_set_call_back_handler(_bt_serial_instance, _bt_call_back);
	_app_bt_status_call_back = bt_status_call_back;
	_bt_rx_fifo_desc = serial_get_rx_fifo_desc (bt_serial_instance);
	_bt_dialog_active = 1;
	dialog_start (_dialog_bt_init_seq, _bt_send_bytes, _bt_status_call_back, 2);
}

// ----------------------------------------------------------------------------------------------------------------------
inline void bt_tick (void)
{
	if (_bt_dialog_active ) {
		dialog_tick();
	}
}
