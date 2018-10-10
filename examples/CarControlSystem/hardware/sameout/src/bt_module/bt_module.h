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

typedef enum {BT_CONNECTED, BT_BUFFER_FULL, BT_BUFFER_EMPTY, BT_SUCCESS, BT_CONNECTING, BT_ERROR_DURING_CONNECTION} bt_return_code_t;
#define BT_CONNECTED  100

/* ======================================================================= */
/**
 * \ingroup board_public_function
 * \brief Initialize the Bluetooth module.
 
 \note Should be called after RESET is deactivated on the Bluetooth module!
 
 The result of the initialization can be: BT_CONNECTED when every thing is OK, or BT_ERROR_DURING_CONNECTION if the Bluetooth module is not initialized correctly.
 * \param[in] bt_status_call_back pointer to a function that will be called when the initialization is done, or when a byte is received - the result of the initialization, and byte received is given as parameter to the function.
 * \param[in] result of initialization BT_CONNECTED/BT_ERROR_DURING_CONNECTION.
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
\brief Send a byte to Bluetooth.

\note The Bluetooth module must be initialised before sending.

\param[in] byte to be send.

\return status [BT_SUCCESS, BT_BUFFER_FULL].
*/
bt_return_code_t bt_send_byte(uint8_t byte);

//-------------------------------------------------
/**
\ingroup board_public_function
\brief Send a byte array to Bluetooth.

\note The Bluetooth module must be initialised before sending.

\param[in] bytes pointer to byte array to be send.
\param[in] len number of bytes to send.

\return status [BT_SUCCESS, BT_BUFFER_FULL].
*/
bt_return_code_t bt_send_bytes(uint8_t *bytes, uint8_t len);

//-------------------------------------------------
/**
\ingroup board_public_function
\brief get a byte from Bluetooth.

\note The Bluetooth module must be initialised before it is possible to receive anything.

\param[in] bytes pointer to byte.

\return status [BT_SUCCESS, BT_BUFFER_EMPTY].
*/
bt_return_code_t bt_get_byte(uint8_t * byte);

/* ======================================================================= */
/**
 * \ingroup board_public_function
 * \brief Must be called when the BT-Module is in the dialog phase.

	Must be called every 100 ms when the BT-Module is in the dialog/initialization phase.
	After initialization is done it is not necessary to call it.
 */
void bt_tick(void);

#endif /* BT_MODULE_H_ */