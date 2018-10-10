#ifndef FRONT_LIGHT_H_
#define FRONT_LIGHT_H_
#include "ostypes.h"


/*extern int16 n_carcontrol_device_impl_FrontLightImpl_front_light_turn_on(int32 *sp);*/
void front_light_turn_on(void);

void front_light_turn_off(void);
void front_light_low_beam(void);
void front_light_high_beam(void);


#endif /* FRONT_LIGHT_H_ */
