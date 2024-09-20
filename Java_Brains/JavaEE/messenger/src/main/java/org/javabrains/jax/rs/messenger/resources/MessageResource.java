package org.javabrains.jax.rs.messenger.resources;

import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.javabrains.jax.rs.messenger.model.Message;
import org.javabrains.jax.rs.messenger.resources.beans.MessageFilterBean;
import org.javabrains.jax.rs.messenger.service.MessageService;

import java.net.URI;
import java.util.List;

@Path("/messages") // All controller methods in this class will have this prefix
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML}) // This represents the body of the request
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
// This is the return type to the HTTP request
public class MessageResource {

    private final MessageService messageService;

    public MessageResource() {
        this.messageService = new MessageService();
    }

    @GET // This is a GET request and will resolve to /messages
    public List<Message> getMessages(@BeanParam MessageFilterBean messageFilterBean) {

        // Even with URIs with query parameters, jersey will call the same controller method
        int year = messageFilterBean.getYear(), start = messageFilterBean.getStart(), size = messageFilterBean.getSize();
        if (year > 0) return messageService.getAllMessagesForYear(year);
        if (start >= 0 && size > 0) return messageService.getAllMessagesPaginated(start, size);
        return messageService.getAllMessages();
    }

    @GET
    @Path("/{messageId}")
    public Message getMessageInstance(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
        Message message = messageService.getMessage(messageId);
        message.addLink(getUriForSelf(uriInfo, message), "self");
        message.addLink(getUriForProfile(uriInfo, message), "profile");
        message.addLink(getUriForComments(uriInfo, message), "comments");
        return message;
    }

    private String getUriForSelf(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(MessageResource.class)
                .path(Long.toString(message.getId()))
                .build()
                .toString();
    }

    private String getUriForProfile(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(message.getAuthor())
                .build()
                .toString();
    }

    private String getUriForComments(UriInfo uriInfo, Message message) {
        return uriInfo.getBaseUriBuilder()
                .path(MessageResource.class) // appends "/messages"
                .path(MessageResource.class, "getCommentResource") // appends {messageId}/comments
                // the method refers to the method on line # 109 so that it follows the same uri pattern
                .path(CommentResource.class) // appends "/"
                .resolveTemplate("messageId", message.getId()) // resolves {messageId} with {message.getId()}
                .build()
                .toString();
    }

    @POST
    public Response addMessage(Message message, @Context UriInfo uriInfo) {
        Message resultMessage = messageService.addMessage(message);
        String newId = String.valueOf(resultMessage.getId());

        URI messageCreationUri = uriInfo.getAbsolutePathBuilder()
                .path(newId) // getAbsolutePathBuilder() will return a URI with - http://localhost:8080/messenger/webapi/messages
                .build();

        return Response.created(messageCreationUri) // does two things - sets status as 201 and adds the URI to the response
                .entity(resultMessage)
                .build();
    }

    @PUT
    @Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
        message.setId(messageId);
        return messageService.updateMessage(message);
    }

    @DELETE
    @Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long messageId) {
        messageService.deleteMessage(messageId);
    }

    // works for both GET and POST
    @Path("/{messageId}/comments") // messageId is used in the Subresource
    public CommentResource getCommentResource() {
        return new CommentResource(); // this returns an instance of the subresource
    }
}
