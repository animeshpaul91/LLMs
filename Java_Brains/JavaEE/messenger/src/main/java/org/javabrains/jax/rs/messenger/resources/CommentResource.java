package org.javabrains.jax.rs.messenger.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.javabrains.jax.rs.messenger.model.Comment;
import org.javabrains.jax.rs.messenger.service.CommentService;

import java.util.List;

@Path("/")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML}) // This represents the body of the request
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML}) // This is the return type to the HTTP req
public class CommentResource {

    private final CommentService commentService;

    public CommentResource() {
        this.commentService = new CommentService();
    }

    @GET
    public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
        return commentService.getAllComments(messageId);
    }

    @GET
    @Path("/{commentId}")
    public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
        // notice that the message ID is not a part of this resource. It comes from the parent resource
        return commentService.getComment(messageId, commentId);
    }

    @POST
    public Comment addComment(@PathParam("messageId") long messageId, Comment comment) {
        return commentService.addComment(messageId, comment);
    }

    @PUT
    @Path("/{commentId}")
    public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId, Comment comment) {
        comment.setId(commentId);
        return commentService.updateComment(messageId, comment);
    }

    @DELETE
    @Path("/{commentId}")
    public Comment deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
        return commentService.deleteComment(messageId, commentId);
    }
}
