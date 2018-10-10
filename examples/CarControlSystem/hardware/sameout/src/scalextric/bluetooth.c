#include "bluetooth.h"

uint8_t bt_initialised = 0;
static serial_p bt_serial = NULL;

// Setup serial channel to be used with BT
static void bt_uart_init ( void )
{
    // set the pins to use the UART peripheral
    pio_configure ( PIOD, PIO_PERIPH_A, ( PIO_PD28A_URXD3 | PIO_PD30A_UTXD3 ),
    PIO_DEFAULT );
    bt_serial = serial_new_instance ( UART3, 115200L, ser_NO_PARITY, 32,
    NULL );
    // Configure UART3 interrupts.
    NVIC_ClearPendingIRQ ( UART3_IRQn );
    NVIC_DisableIRQ ( UART3_IRQn );
    NVIC_SetPriority ( UART3_IRQn, 1 ); // IRQ Priority 1
    NVIC_EnableIRQ ( UART3_IRQn );
}

// This function is called from ISR so be careful!!
static void bluetooth_call_back ( uint8_t status, uint8_t byte )
{
    if ( status == BT_CONNECTED )
    {
        //BT-Module is ready and connected and a new byte is received
        // Use it quickly here or set a flag, and take it out of the BT RX fifo later?
    }
    else if ( status == BT_CONNECTED )
    {
        bt_initialised = 1;  // BT-module initialization is finished
    }
    else if ( status == BT_ERROR_DURING_CONNECTION )
    {
        // BT module is NOT initialized correctly!!
        // What to do??
        bt_initialised = -1;
    }
}


void bluetooth_start()
{
    printf("C: called bluetooth_start\n");

    if ( !bt_initialised && bt_serial == NULL )
    {
        printf("C: Initializing bluetooth\n");
        bt_uart_init();  // Initialize the serial driver to be used of the BT-Module
        bt_module_init ( bluetooth_call_back, bt_serial ); // Initialize the BT-Module.
    }
    else
    {
        printf("C: Bluetooth already initialized\n");
    }
}

void bluetooth_send_byte(int byte)
{
	printf("C: called bluetooth_send_byte with parameter %d (%c)\n", byte, byte);

    if ( bt_initialised == 1 ) {
        bt_send_bytes ( ( uint8_t * )&byte, 1 ); // Send a byte to BT
    }
    else {
        printf("C: Bluetooth not initialized\n");
    }
}

int bluetooth_read_byte()
{
	printf("C: called bluetooth_read_byte\n");

    uint8_t byte;
    if ( bt_initialised == 1 && bt_get_byte ( &byte ) == BT_SUCCESS )
    { // Are there any bytes received from BT?
        return byte;
    }
    return -1;
}

void bluetooth_tick()
{
    bt_tick();
}
