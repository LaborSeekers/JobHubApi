package thelaborseekers.jobhubapi.model.enums;

public enum Reputation {
    BAJA,
    MEDIA,
    ALTA,
    MUY_ALTA;
    public static Reputation fromValue(Integer reputationValue) {
        if (reputationValue >= 61) {
            return ALTA;
        } else if (reputationValue >= 31) {
            return MEDIA;
        } else {
            return BAJA;
        }}
}
