/** \file dialog_handler.c
\author Ib Havn
\date 2014-02-14

\defgroup dialog_handler Dialog Handler
\{
\brief Implements a dialog handler that can be used to send and receive commands/response to/from serial streamed device's.

The dialog handler is a utility that makes it easy to establish a dialog with a device that communicates via serial sequences.<br>
An example could be a modem controlled by AT commands.
Communicating with AT-based modems is typically dialog based. Where the first part of the dialog is about setting up the modem parameters,
and establish the connection to the other end. When the connection is established the second part of the dialog will typical be transparent
seen from the devices that communicates.
This Dialog Handler is very useful for the first part of the communication.

\defgroup dialog_handler_public Public
\brief Public, can be used from the application.

\defgroup dialog_handler_return_codes Dialog Handler Return codes
\brief Return values from the dialog handler.

\defgroup dialog_handler_private Private
\brief Private, should not be called or used from the application, these items are internal items only for use inside Dialog Handler.
\}
*/

/* ################################################## Standard includes ################################################# */

/* ################################################### Project includes ################################################# */
#include "dialog_handler.h"

/* ################################################### Global Variables ################################################# */

/* ################################################### Module Variables ################################################# */

/**
\ingroup dialog_handler_private
\{
\brief This struct contains information about the current expected response.
*/
typedef struct {
	uint8_t *response; /**< pointer to the beginning of actual format_response information. */
	uint8_t *last; /**< pointer to the last byte in the format_response. */
	uint8_t *response_p; /**< pointer to the actual byte in format_response. */
	uint8_t arg_cnt; /**< counter that holds the number of bytes in current argument, that still needs to be received (fixed length argument),
	or is allowed to be received (variable length argument). */
	int8_t arg_index; /**< index to current argument buffer array */
	dialog_arg_buf_t
	*arg_buffers; /**< pointer to the array of argument buffer structs to store received arguments in. */
	uint8_t *arg_buf_p; /**< pointer to current argument buffers next free byte position. */
} _dialog_format_t;
/** \} */

// private
static _dialog_format_t
_dialog_format; // current expected response information.
static uint8_t _dialog_second_counter = 0; // counts seconds elapsed
static uint8_t _dialog_current_state =
0; // index to current dialog state in dialog_seq[]
static dialog_seq_t *_dialog_seq; // pointer to current dialog_seq[]

static uint8_t _ticks_to_wait_between_states = 0; // How many tick to wait between state changes
static uint8_t _remaining_states_to_wait_before_next_state = 0;
static uint8_t _dialog_next_state_after_wait;

/**
\ingroup dialog_handler_private
\{
\brief Definition of states used in dialog_char_received() .
*/
enum _dialog_await_state_t {
	NORMAL_STATE = 0, ARG_STATE, ARG_MAX_STATE
};
/** \} */

static uint8_t _dialog_await_state =
NORMAL_STATE; // current dialog_char_received() state

/* ################################################# Function prototypes ################################################# */
static void _dialog_prepare_for_next_byte( void );
static void ( *_dialog_pf_send )( uint8_t *command,
uint8_t command_length );
static void ( *_dialog_pf_call_back )( uint8_t result );

/* ======================================================================================================================= */
/**
\ingroup dialog_handler_private
\brief Prepare the response to wait for.

Setup variables to wait for the next expected response in the new state.
\param[in] new_state to set up variables to its response to wait for.

*/
static void _dialog_await( uint8_t new_state )
{
	_dialog_format.response_p = _dialog_format.response =
	_dialog_seq[new_state].responce_format;
	_dialog_format.arg_buffers = _dialog_seq[new_state].arg_buffers;
	_dialog_format.arg_index = -1;

	// Are any arg_buffers specified?
	if ( _dialog_format.arg_buffers ) {
		_dialog_format.arg_buf_p = _dialog_seq[new_state].arg_buffers[0].arg_buf;
	}

	_dialog_format.last = _dialog_seq[new_state].responce_format
	+ _dialog_seq[new_state].responce_format_length - 1;
	_dialog_prepare_for_next_byte();
}

/* ======================================================================================================================= */
/**
\ingroup dialog_handler_private
\brief Prepare the new state of the dialog sequence.

IF the new state is either DIALOG_OK_STOP or DIALOG_ERROR_STOP then the call_back function will be called.
ELSE the new State will be setup.

\param[in] new_state the new state to goto.
*/
static void _dialog_goto_state( const uint8_t new_state )
{
	_dialog_second_counter = 0;
	_dialog_current_state = new_state;

	if ( new_state == DIALOG_ERROR_STOP || new_state == DIALOG_OK_STOP )
	{
		( *_dialog_pf_call_back )( new_state );
	}
	else
	{
		// Send command if any
		if ( _dialog_seq[new_state].command_length != 0 )
		{
			( *_dialog_pf_send )( _dialog_seq[new_state].command,
			_dialog_seq[new_state].command_length );
		}

		// Any response to wait for?
		if ( _dialog_seq[new_state].responce_format_length > 0 )
		{
			_dialog_await( new_state );
			_dialog_second_counter = _dialog_seq[new_state].max_response_time
			+ 1; // add one because of 1 sec jitter in second timer
		}
		else
		{
			_dialog_goto_state( _dialog_seq[new_state].ok_state );
		}
	}
}

/* ======================================================================================================================= */
/**
\ingroup dialog_handler_private
\brief Prepare the new state of the dialog sequence.

IF the parameter ticks_to_wait_between_states in dialog_start is set to a value, then the dialog handler will
wait the no of ticks before specified in ticks_to_wait_between_states.

\param[in] new_state the new state to goto after the wait.
*/
static void _dialog_goto_state_with_wait( const uint8_t new_state )
{
	if ( _ticks_to_wait_between_states == 0)
	{
		_dialog_goto_state(new_state);
	}
	else
	{
		_remaining_states_to_wait_before_next_state = _ticks_to_wait_between_states;
		_dialog_next_state_after_wait = new_state;
	}
}

/* ======================================================================================================================= */
/**
\ingroup dialog_handler_public
\brief Start a dialog with the device.

\param[in] *p_seq pointer to an array of dialog sequences to be executed.
\param[in] *pf_send pointer to function that can send a byte buffer to the device.\n
The function must have this signature: <code>void func(uint8_t *command, uint8_t command_length)</code>.\n
Arguments: <code>*command</code>: byte buffer to send to the device. <code>command_length</code>: the number of bytes to send.
\param[in] *pf_call_back pointer to function that will be called when the dialog sequence ends. Either with are OK result, or an ERRROR result.\n
The function must have this signature: <code>func(uint8_t result)</code>.\n
Arguments: <code>result</code>: result of the dialog sequence: DIALOG_OK_STOP dialog ended with expected result. DIALOG_ERROR_STOP dialog ended with an error.
\param[in] ticks_to_wait_between_states a number of ticks to wait between each dialog states. This is useful if the device is not responsive!

Example of array of dialog states:
\code
dialog_seq_t dialog_seq[] = {
{ (uint8_t *)"", 0, (uint8_t *)"READY\x0D\x0A", 7, 15, 1, 0, DIALOG_NO_BUFFER },
{ (uint8_t *)"at+rsi_reset\x0D\x0A", 14, (uint8_t *)"OK\x0D\x0A", 4, 5, 2, 1, DIALOG_NO_BUFFER },
{ (uint8_t *)"at+rsi_opermode=0\x0D\x0A", 19, (uint8_t *)"stuff%%OK\x0D\x0A", 4, 3, 3, 1, DIALOG_NO_BUFFER }, // Example byte stuffing '%' is expected in response: response_format '%%'
{ (uint8_t *)"at+rsi_band=0\x0D\x0A", 15, (uint8_t *)"OK\x0D\x0A", 4, 3, 4, 1, DIALOG_NO_BUFFER },
{ (uint8_t *)"at+rsi_init\x0D\x0A", 13, (uint8_t *)"OK%6B\x0D\x0A", 7, 4, 5, 1, DIALOG_NO_BUFFER },
{ (uint8_t *)"at+rsi_scan=0\x0D\x0A", 15, (uint8_t *)"OK%*255B\x0D\x0A", 4, 4, 6, 1, DIALOG_NO_BUFFER }, // Example of variable length argument, here max 255 bytes in argument.
{ (uint8_t *)"at+rsi_join=dlink,0,2\x0D\x0A", 23, (uint8_t *)"OK%1B\x0D\x0A", 7, 10, DIALOG_OK_STOP, 1, argument_buffer }, // Example of fixed length argument, here 1 byte in argument.
\endcode
*/
void dialog_start( dialog_seq_t *p_seq,
void ( *pf_send )( uint8_t *command, uint8_t command_length ),
void ( *pf_call_back )( uint8_t result ), uint8_t ticks_to_wait_between_states )
{
	_dialog_seq = p_seq;
	_dialog_pf_send = pf_send;
	_dialog_pf_call_back = pf_call_back;
	_dialog_current_state = 0;
	_ticks_to_wait_between_states = ticks_to_wait_between_states;
	_dialog_goto_state( 0 );
}

/* ======================================================================================================================= */
/**
\ingroup dialog_handler_public
\brief Housekeeping function that must be called every second when a dialog is active.

If the maximum wait time is exceeded the the dialog will change to error state.
*/
void dialog_tick()
{
	if ( _remaining_states_to_wait_before_next_state )
	{
		if ( --_remaining_states_to_wait_before_next_state == 0 )
		{
			_dialog_goto_state( _dialog_next_state_after_wait);
		}
	}
	else if ( _dialog_second_counter ) {
		if ( --_dialog_second_counter == 0 ) {
			_dialog_goto_state_with_wait( _dialog_seq[_dialog_current_state].error_state );
		}
	}
}

/* ======================================================================================================================= */
/**
\ingroup dialog_handler_public
\brief Every time a byte is received, this function must be called.

The function can be in three different internal states:<br />
<strong>normal_state</strong>: We are not receiving any arguments right now.<br />
<strong>arg_state</strong>: We are receiving a fixed length argument (here 5 bytes - format:  %5B).<br />
<strong>arg_max_state</strong>: We are receiving an variable length argument (here max 6 bytes - format: %*6B),
when a byte is received that match the next ordinary byte in the format,
the argument have been received, and we switch to NORMAL_STATE.
This will also happens if the specified maximum number byte in the argument is received.

If there are specified a pointer to an argument buffer in the current state of the dialog_seq, the received argument bytes will be stored in this buffer, else the argument values will be thrown away.

When/If the expected response string is received the dialog state is changed to the current dialog states OK state.
\param[in] byte received from device we are communication with.
*/
void dialog_byte_received( const uint8_t byte )
{
	uint8_t _retry;

	switch ( _dialog_await_state ) {
		case NORMAL_STATE:
		_retry = 0;

		do {
			if ( *_dialog_format.response_p++ != byte ) {
				// Problem: not the expected byte
				// Lets try from the beginning of the format
				_dialog_format.response_p = _dialog_format.response;
				// Reset all things about argument capturing
				_dialog_format.arg_index = 0;

				if ( _dialog_format.arg_buffers != 0 ) {
					_dialog_format.arg_buf_p = _dialog_format.arg_buffers[0].arg_buf;
				}

				if ( !_retry ) {
					_retry = 1;
					} else {
					_retry = 0;
				}
			}

			_dialog_prepare_for_next_byte();
		} while ( _retry );

		break;

		case ARG_STATE:

		// Test if buffer is specified
		if ( _dialog_format.arg_buffers != 0 ) {
			*_dialog_format.arg_buf_p++ = byte;
			// Update length of received argument in arg_buffers
			_dialog_format.arg_buffers[_dialog_format.arg_index].arg_len++;

			// have we got all the bytes in the this argument
			if ( --( _dialog_format.arg_cnt ) == 0 ) {
				_dialog_prepare_for_next_byte();
			}
			} else if ( --( _dialog_format.arg_cnt ) == 0 ) {
			_dialog_prepare_for_next_byte();
		}

		break;

		case ARG_MAX_STATE:

		// Test if we still are receiving argument bytes
		if ( byte != *_dialog_format.response_p ) {
			// Test if buffer is specified
			if ( _dialog_format.arg_buffers != 0 ) {
				*_dialog_format.arg_buf_p++ = byte;
				// Update length of received argument in arg_buffers
				_dialog_format.arg_buffers[_dialog_format.arg_index].arg_len++;

				// Have we received the maximum number of bytes in argument?
				if ( --( _dialog_format.arg_cnt ) == 0 ) {
					_dialog_prepare_for_next_byte();
				}
			}
		} else if ( --( _dialog_format.arg_cnt ) ==
		0 ) { // or have we received the maximum allowed no of bytes in the argument?
			_dialog_prepare_for_next_byte();
			} else {
			// Next byte in normal response is received
			_dialog_format.response_p++;
			_dialog_prepare_for_next_byte();
		}

		break;

		default:
		break;
	}

	// Test if we are done - have received all bytes in this state of the dialog_seq
	if ( ( _dialog_await_state == NORMAL_STATE ) &&
	( _dialog_format.response_p > _dialog_format.last ) ) {
		// OK - goto OK state
		_dialog_goto_state_with_wait( _dialog_seq[_dialog_current_state].ok_state );
	}
}

/* ======================================================================================================================= */
/**
\ingroup dialog_handler_private
\brief Evaluates the next char/chars in the response_format, and prepare the dialog handler for it.

Evaluates the responce_format to see if we are going to receive and argument, either a fixed length argument (here 5 bytes - format:  %5B),<br />
or a variable length argument (here max 6 bytes - format: %*6B), or an ordinary byte. It also checks for byte stuffing (format: %%).<br />

When the function returns it has setup the state variable _dialog_await_state that will be used in dialog_char_received(), and the needed argument counters etc.
*/
static void _dialog_prepare_for_next_byte()
{
	// esc char?
	if ( *( _dialog_format.response_p ) == '%' ) {
		_dialog_format.response_p++;

		if ( *( _dialog_format.response_p ) == '%' ) {
			//Byte stuffing
			_dialog_await_state = NORMAL_STATE;
			} else if ( *_dialog_format.response_p == '*' ) {
			// Max args: %*nnB
			_dialog_format.response_p++;
			// Find the arg. cnt
			_dialog_format.arg_cnt = 0;

			do {
				_dialog_format.arg_cnt *= 10;
				_dialog_format.arg_cnt += ( *_dialog_format.response_p ) - '0';
			} while ( *( ++_dialog_format.response_p ) != 'B' );

			_dialog_format.response_p++;

			// Are argument buffers allocated?
			if ( _dialog_format.arg_buffers != 0 ) {
				_dialog_format.arg_index++;
				_dialog_format.arg_buffers[_dialog_format.arg_index].arg_len = 0;
				_dialog_format.arg_buf_p =
				_dialog_format.arg_buffers[_dialog_format.arg_index].arg_buf;
			}

			_dialog_await_state = ARG_MAX_STATE;
			} else {
			// args: %nnB
			// Find the arg. cnt
			_dialog_format.arg_cnt = 0;

			do {
				_dialog_format.arg_cnt *= 10;
				_dialog_format.arg_cnt += ( *_dialog_format.response_p ) - '0';
			} while ( *( ++_dialog_format.response_p ) != 'B' );

			_dialog_format.response_p++;

			// Are argument buffers allocated?
			if ( _dialog_format.arg_buffers != 0 ) {
				_dialog_format.arg_index++;
				_dialog_format.arg_buffers[_dialog_format.arg_index].arg_len = 0;
				_dialog_format.arg_buf_p =
				_dialog_format.arg_buffers[_dialog_format.arg_index].arg_buf;
			}

			_dialog_await_state = ARG_STATE;
		}
		} else {
		_dialog_await_state = NORMAL_STATE;
	}
}

