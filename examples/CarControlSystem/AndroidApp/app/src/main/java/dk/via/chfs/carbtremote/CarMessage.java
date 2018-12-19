package dk.via.chfs.carbtremote;

public enum CarMessage {
//    STOP            (-1),
//    START           (0),
//    GEAR_PARK       (1),
//    GEAR_REVERSE    (2),
//    GEAR_DRIVE      (3),
//    ACCELERATE      (4),
//    BRAKE           (5),
//    GEAR_NEUTRAL    (6);
    STOP            (47),
    START           (48),
    GEAR_PARK       (49),
    GEAR_REVERSE    (50),
    GEAR_DRIVE      (51),
    ACCELERATE      (52),
    BRAKE           (53),
    GEAR_NEUTRAL    (54);

    private final int value;

    CarMessage(int value) {
        this.value = value;
    }

    public byte getValue() {
        return (byte)this.value;
    }

    public byte[] getValueAsArray() {
        return new byte[]{(byte)this.value};
    }
}
