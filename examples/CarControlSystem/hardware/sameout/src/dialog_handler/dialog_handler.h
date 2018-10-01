/*! \file dialog_handler.h
    \ifnot NO_DOCUMENTATION_DIALOG_HANDLER
*/

#ifndef DIALOG_HANDLER_H_
#define DIALOG_HANDLER_H_
#include <stdint.h>


#define TO(x) x
#define LEN(x) x

/**
    \ingroup dialog_handler_return_codes
    \{
    \brief The dialog completed with success. */
#define DIALOG_OK_STOP 255
/** \brief The dialog completed with errors. */
#define DIALOG_ERROR_STOP 254
/** \brief Can be used to specify that no buffer for arguments are specified. */
#define DIALOG_NO_BUFFER 0
/**
    \}
*/

/**
    \ingroup dialog_handler_public
    \{
    \brief This struct defines an argument buffer.
*/
typedef struct {
	uint8_t *arg_buf; /**< Pointer to the array of byte buffers to store received arguments in
							\note Each buffer must be specified to be able to hold at least the number of bytes in the argument */
	uint8_t arg_len;  /**< The actual length of the received argument will be returned here */
} dialog_arg_buf_t;

/**
    \ingroup dialog_handler_public
    \{
    \brief This struct contains one dialog step.
*/
typedef struct {
	uint8_t *command; /**< command string to be send. */
	uint8_t command_length; /**< length of command string. */
	uint8_t *responce_format; /**< expected response string to be received as response to the command. This can contain format specifiers for arguments */
	uint8_t responce_format_length; /**< Length of responce_format. */
	uint8_t max_response_time; /**< max time in seconds to wait for the response. */
	uint8_t ok_state; /**< When expected response is received in time, then go to this dialog step. */
	uint8_t error_state; /**< When response is wrong or NOT received in time, then go to this dialog step. */
	//uint8_t no_of_arguments; /**< No of arguments to be sampled. */
	dialog_arg_buf_t
	*arg_buffers;  /**< Pointer to the array of argument buffers to store received arguments in, null (0) if no arguments are specified in response_format.
							\note the array must be specified with at least no_of_arguments indexes. */
} dialog_seq_t;
/** \} */

void dialog_start( dialog_seq_t *p_seq,
                   void ( *pf_send )( uint8_t *command, uint8_t command_length ),
                   void ( *pf_call_back )( uint8_t result ), uint8_t ticks_to_wait_between_states );

void dialog_tick( void );
void dialog_byte_received( const uint8_t ch );

/**
    \endif
*/
#endif /* DIALOG_HANDLER_H_ */
