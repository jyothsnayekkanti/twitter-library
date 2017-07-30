package com.josh.twitter.resources;

import com.google.gson.Gson;
import com.josh.twitter.api.HighWorthFollowerResponse;
import com.josh.twitter.service.UserNetworkService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static com.josh.twitter.constants.DefaulValues.*;

@Path("api/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TwitterUserNetworkResource {

    private UserNetworkService userNetworkService;

    public TwitterUserNetworkResource(UserNetworkService userNetworkService){
        this.userNetworkService = userNetworkService;
    }

/*    /api/user/<screen_name>/followers?offset=0&followers_between=100,200
            /api/user/<screen_name>/followers/count */


    @Path("/{screen_name}/followers/count")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public int getFollowerCount(@PathParam("screen_name") String userHandle) throws IOException {
        return userNetworkService.fetchFollowersCount(userHandle);

    }

    @Path("/{screen_name}/followersOrFriendsWith")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GET
    public String getFollowers(@PathParam("screen_name") String userHandle,
                               //@QueryParam("followersToProcess") int pageCount,
                               @QueryParam("minFollowers") int minFollowerCount ,
                               @QueryParam("maxFollowers") int maxFollowerCount) throws IOException {
        String followers = "";
        if(userHandle != null){
            Gson gson = new Gson();
            followers = gson.toJson(
                    userNetworkService.getHighWorthFollowers(
                        userHandle,
                        /*pageCount == 0 ? */DEFAULT_PAGE_COUNT/* : pageCount*/,
                        minFollowerCount == 0 ? DEFAULT_MIN_FOLLOWER_COUNT : minFollowerCount,
                        maxFollowerCount == 0 ? DEFAULT_MAX_FOLLOWER_COUNT : maxFollowerCount),
                    HighWorthFollowerResponse.class);
            return followers;
        }
        return new String("");
    }




}
