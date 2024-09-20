package org.javabrains.jax.rs.messenger.service;

import org.javabrains.jax.rs.messenger.database.DatabaseClass;
import org.javabrains.jax.rs.messenger.model.Profile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProfileService {
    private static Map<String, Profile> profiles;

    public ProfileService() {
        profiles = DatabaseClass.getProfiles();

        // Adding dummy profiles to get started
        Profile profile = new Profile(1L, "Animesh_Profile", "Animesh", "Paul");
        profiles.put(profile.getProfileName(), profile);
    }

    public List<Profile> getAllProfiles() {
        return new ArrayList<>(profiles.values());
    }

    public Profile getProfile(String profileName) {
        return profiles.get(profileName);
    }

    public Profile addProfile(Profile profile) {
        long profileId = profiles.size() + 1;
        profile.setId(profileId);
        setProfileTimestamp(profile);
        profiles.put(profile.getProfileName(), profile);
        return profile;
    }

    private void setProfileTimestamp(Profile profile) {
        profile.setCreated(new Date());
    }

    public Profile updateProfile(Profile profile) {
        String profileName = profile.getProfileName();
        if (profileName.isEmpty()) return null;
        long profileId = profiles.get(profileName).getId();
        profile.setId(profileId); // set the already existing ID
        profiles.put(profileName, profile);
        return profile;
    }

    public void deleteProfile(String profileName) {
        profiles.remove(profileName);
    }
}
