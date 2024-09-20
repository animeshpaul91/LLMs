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
import org.javabrains.jax.rs.messenger.model.Profile;
import org.javabrains.jax.rs.messenger.service.ProfileService;

import java.util.List;

@Path("/profiles") // All controller methods in this class will have this prefix
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML}) // This represents the body of the request
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML}) // This is the return type to the HTTP reque
public class ProfileResource {
    private final ProfileService profileService;

    public ProfileResource() {
        this.profileService = new ProfileService();
    }

    @GET // This is a GET request and will resolve to /Profiles
    public List<Profile> getProfiles() {
        return profileService.getAllProfiles();
    }

    @GET
    @Path("/{profileName}")
    public Profile getProfileInstance(@PathParam("profileName") String profileName) {
        return profileService.getProfile(profileName);
    }

    @POST
    public Profile addProfile(Profile profile) {
        return profileService.addProfile(profile);
    }

    @PUT
    @Path("/{ProfileId}")
    public Profile updateProfile(@PathParam("ProfileId") String profileName, Profile profile) {
        profile.setProfileName(profileName);
        return profileService.updateProfile(profile);
    }

    @DELETE
    @Path("/{profileName}")
    public void deleteProfile(@PathParam("profileName") String profileName) {
        profileService.deleteProfile(profileName);
    }

}
