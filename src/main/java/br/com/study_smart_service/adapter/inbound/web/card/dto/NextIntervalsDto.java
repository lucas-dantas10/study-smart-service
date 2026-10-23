package br.com.study_smart_service.adapter.inbound.web.card.dto;

/** Em quantos dias o card volta para cada avaliação possível. */
public record NextIntervalsDto(int hard, int medium, int easy) {
}
