package thelaborseekers.jobhubapi.model.enums;

public enum Reputation {
    BAJA,
    MEDIA,
    ALTA,
    MUY_ALTA;
    public static Reputation fromValue(Integer reputationValue) {
        if (reputationValue >= 75) {
            return MUY_ALTA;
        } else if (reputationValue >= 60) {
            return ALTA;
        } else if (reputationValue >= 40) {
            return MEDIA;
        } else {
            return BAJA;
        }}
}
