package br.com.study_smart_service.utils.mapper.review;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.study_smart_service.adapter.outbound.review.entity.ReviewEntity;
import br.com.study_smart_service.domain.review.model.Review;
import br.com.study_smart_service.utils.mapper.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ReviewMapper {

    @Mappings({
        @Mapping(source = "reviewEntity.id", target = "id"),
        @Mapping(source = "reviewEntity.user", target = "user", ignore = true),
        @Mapping(source = "reviewEntity.card", target = "card", ignore = true),
        @Mapping(source = "reviewEntity.nextReviewAt", target = "nextReviewAt"),
        @Mapping(source = "reviewEntity.interval", target = "interval"),
        @Mapping(source = "reviewEntity.repetition", target = "repetition"),
        @Mapping(source = "reviewEntity.easiness", target = "easiness"),
        @Mapping(source = "reviewEntity.lastQuality", target = "lastQuality"),
        @Mapping(source = "reviewEntity.createdAt", target = "createdAt"),
        @Mapping(source = "reviewEntity.updatedAt", target = "updatedAt")
    })
    Review jpaToDomain(ReviewEntity reviewEntity);

    @Mappings({
        @Mapping(source = "review.id", target = "id"),
        @Mapping(source = "review.user", target = "user"),
        @Mapping(source = "review.card", target = "card"),
        @Mapping(source = "review.nextReviewAt", target = "nextReviewAt"),
        @Mapping(source = "review.interval", target = "interval"),
        @Mapping(source = "review.repetition", target = "repetition"),
        @Mapping(source = "review.easiness", target = "easiness"),
        @Mapping(source = "review.lastQuality", target = "lastQuality"),
        @Mapping(source = "review.createdAt", target = "createdAt"),
        @Mapping(source = "review.updatedAt", target = "updatedAt")
    })
    ReviewEntity domainToJpa(Review review);

    default String tagsToString(List<String> tags) {
        return tags == null || tags.isEmpty() ? null : String.join(",", tags);
    }

    default List<String> tagsToList(String tags) {
        return tags == null || tags.isBlank() ? new ArrayList<>() : new ArrayList<>(Arrays.asList(tags.split(",")));
    }
}
