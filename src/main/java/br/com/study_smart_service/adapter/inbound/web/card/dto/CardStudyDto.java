package br.com.study_smart_service.adapter.inbound.web.card.dto;

import java.util.List;

public record CardStudyDto(
        String id,
        String frontText,
        String backText,
        List<String> tags,
        NextIntervalsDto nextIntervals
) {

    public CardStudyDto(String id, String frontText, String backText) {
        this(id, frontText, backText, List.of(), null);
    }
}
