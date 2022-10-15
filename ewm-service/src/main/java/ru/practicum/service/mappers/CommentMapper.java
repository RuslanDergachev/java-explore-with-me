package ru.practicum.service.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.service.models.comments.dto.CommentDto;
import ru.practicum.service.models.comments.entity.Comment;

@Component
public class CommentMapper {

    public CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .authorName(comment.getAuthor().getName())
                .text(comment.getText())
                .created(comment.getCreated())
                .build();
    }

    public Comment toComment(CommentDto comment) {
        return Comment.builder()
                .text(comment.getText())
                .build();
    }
}
