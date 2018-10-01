/*! \file bt_module.h
\brief Driver and handler for Bluetooth module.

\author iha

\defgroup  board_drivers Drivers for the ScalexTric Main Board.
\{
\brief A driver selection for the MCU board.

\defgroup board_init Board driver initialization.
\brief How to initialize the Board drivers.

\defgroup board_public_function Public Board Functions
\brief Public Board functions.

Here you you will find the functions you will need.

\defgroup board_public Public Declarations, variables etc.
\brief Declarations, variables etc. that are private for the Board Driver implementation.

\defgroup board_return Board Driver Return codes
\brief Codes returned from Board Driver functions.
\}
*/

#ifndef BT_MODULE_H_
#define BT_MODULE_H_
#include <asf.h>
#include "../dialog_handler/dialog_handler.h"
#include "../serial/iha_serial.h"

#define BT_CONNECTED  100

/* ======================================================================= */
/**
 * \ingroup board_public_function
 * \brief Initialize the Bluetooth module.
 
 \note Should be called after RESET is deactivated on the Bluetooth module!
 
 The result of the initialization can be: DIALOG_OK_STOP when every thing is OK, or DIALOG_ERROR_STOP if the Bluetooth module is not initialized correctly.
 * \param[in] bt_status_call_back pointer to a function that will be called when the initialization is done, or when a byte is received - the result of the initialization, and byte received is given as parameter to the function.
 * \param[in] result of initialization DIALOG_OK_STOP/DIALOG_ERROR_STOP.
 * \param[in] byte last received byte, will be 0 when dialog is active.
 * \param bt_serial_instance pointer to serial instance to be used for BT module.
 
 The  call back function must have this signature:
 \code
 void foo(uint8_t status, uint8_t byte)
 \endcode
 
 \note The call back function is called from an ISR - so keep is very short!!!
 */
void bt_module_init(void (*bt_status_call_back)(uint8_t result, uint8_t byte), serial_p bt_serial_instance);

//-------------------------------------------------
/**
\ingroup board_public_function
\brief Send a byte array to Bluetooth.

\note The Bluetooth module must be initialised before sending.

\param[in] bytes pointer to byte array.
\param[in] len number of bytes to send.

\return Buffer status [BUFFER_OK, BUFFER_EMPTY, BUFFER_FULL].
*/
uint8_t bt_send_bytes(uint8_t *bytes, uint8_t len);

/* ======================================================================= */
/**
 * \ingroup board_public_function
 * \brief Must be called when the BT-Module is in the dialog phase.

	Must be called every 100 ms when the BT-Module is in the dialog/initialization phase.
	After initialization is done it is not necessary to call it.
 */
void bt_tick(void);

#endif /* BT_MODULE_H_ */