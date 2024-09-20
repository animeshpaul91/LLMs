package org.javabrains.jax.rs.messenger.service;

import org.javabrains.jax.rs.messenger.database.DatabaseClass;
import org.javabrains.jax.rs.messenger.exception.WebApplicationExceptionUtil;
import org.javabrains.jax.rs.messenger.model.Comment;
import org.javabrains.jax.rs.messenger.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommentService {
    private static Map<Long, Message> messages;

    public CommentService() {
        messages = DatabaseClass.getMessages();
    }

    public List<Comment> getAllComments(long messageId) {
        Map<Long, Comment> comments = getCommentsForAMessage(messageId);
        return new ArrayList<>(comments.values());
    }

    private Map<Long, Comment> getCommentsForAMessage(long messageId) {
        Message message = messages.get(messageId);
        if (message == null) {
            // Jersey knows about this exception. so this does not need a Mapper. You can use this in your code at certain places
            WebApplicationExceptionUtil.throwNotFoundException();
        }
        return message.getComments();
    }

    public Comment getComment(long messageId, long commentId) {
        Map<Long, Comment> comments = getCommentsForAMessage(messageId);
        Comment comment = comments.get(commentId);
        if (comment == null) {
            WebApplicationExceptionUtil.throwWebApplicationExceptionWithNotFoundStatus();
        }
        return comment;
    }

    public Comment addComment(long messageId, Comment comment) {
        Map<Long, Comment> comments = getCommentsForAMessage(messageId);
        long commentId = comments.size() + 1;
        comment.setId(commentId);
        comments.put(commentId, comment);
        return comment;
    }

    public Comment updateComment(long messageId, Comment comment) {
        Map<Long, Comment> comments = getCommentsForAMessage(messageId);
        long commentId = comment.getId();
        if (commentId < 1) return null;
        comments.put(commentId, comment);
        return comment;
    }

    public Comment deleteComment(long messageId, long commentId) {
        Map<Long, Comment> comments = getCommentsForAMessage(messageId);
        return comments.remove(commentId);
    }
}
