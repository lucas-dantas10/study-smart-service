package br.com.study_smart_service.domain.review.enums;

public enum EQuality {
    HARD(1),
    MEDIUM(2),
    EASY(3);

    private final int quality;

    EQuality(int quality) {
        this.quality = quality;
    }

    public int getQuality() {
        return quality;
    }

    public static EQuality fromQuality(int quality) {
        for (EQuality qualityEnum : EQuality.values()) {
            if (qualityEnum.getQuality() == quality) {
                return qualityEnum;
            }
        }

        throw new IllegalArgumentException("Tipo de qualidade inválida: " + quality);
    }

    public boolean isDifficult() {
        return quality == HARD.quality;
    }
}
