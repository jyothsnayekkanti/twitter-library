# twitter-library

This project simplifies using Twitter REST APIs useful for user data analysis with an **API tailored to Java programming language**. It uses Twitter REST API endpoint and exposes User High worth network as **webservices**.

Twitter library is a project that enables find high worth friends and followers given screen name of the user.

#### Use cases exposed in this project include
* [Follower Count](#follower-count)
* [Followers with highworth network](#followers-with-highworth-network)
* [User Timeline](#timeline-tweet)

## Pre-requisites:
* Register Twitter App
* Generate Consumer Key and Consumer Secret

## Getting started:
* Create an config file twitter.yaml with Twitter consumer credentials which needs to be provided as the server configuration input. A config file contains consumer key and secret details. Following is an example config file.

```
consumerKey: XXXXXXXXXXXXXXXXXX
consumerSecret: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

## Installing

* Update the yaml file twitter.yaml with all the required properties.

```
consumerKey: <consumerKey>
consumerSecret: <consumerSecret>

logging:

  appenders:
    - type: file
      # The file to which current statements will be logged.
      currentLogFilename: ./logs/example.log

      # When the log file rotates, the archived log will be renamed to this and gzipped. The
      # %d is replaced with the previous day (yyyy-MM-dd). Custom rolling windows can be created
      # by passing a SimpleDateFormat-compatible format as an argument: "%d{yyyy-MM-dd-hh}".
      archivedLogFilenamePattern: ./logs/example-%d.log.gz

      # The number of archived files to keep.
      archivedFileCount: 5

      # The timezone used to format dates. HINT: USE THE DEFAULT, UTC.
      timeZone: UTC
```


## Running the tests

* Run the jar file or deploy it as a service
```
java -jar josh-twitter-library-1.0.jar server twitter.yaml
```


## Built with

* [Dropwizard](http://www.dropwizard.io/1.1.0/docs/) - The web framework used
* [Maven](https://maven.apache.org) - Dependency Management

## API Documentation

### Follower Count

You can use **followers/count** API to get the follower count of a user, given the screen name.

Sample request to get follower count of a user.

```
http://localhost:8080/api/user/<screen_name>/followers/count
```

The following is an example response.

```
<count>
```

### Followers with highworth network

Use this api to get a users high worth followers and friends. High worth refers to the users who have followers within a given range minFollowers - maxFollowers. Currently the api processes the recent 200 followers and 200 friends of the user by default and filters them according to the min-max follwer range.

Sample request to get high worth network of the user

```
http://localhost:8080/api/user/<screen_name>/followersOrFriendsWith?minFollowers=500&maxFollowers=1000
```

The following is an example response.

```
{
"userHandle":"jyothsnaanvrp",
"highWorthFollowers":[
 {
  "id":<id1>,
  "id1_str":"<id_str>",
  "name":"<name1>",
  "screen_name":"<screen name1>",
  "location":"Ireland",
  "followers_count":608,
  "friends_count":764
 }
],
"highWorthFriends":[
 {
  "id":<id2>,
  "id_str":"<id2_str>",
  "name":"<name2>",
  "screen_name":"<screen name2>",
  "location":"Dublin, Ireland",
  "followers_count":754,
  "friends_count":678
 },
 {
  "id":<id3>,
  "id_str":"<id3_str>",
  "name":"<name3>",
  "screen_name":"<screen name3>",
  "location":"Ireland",
  "followers_count":798,
  "friends_count":634
 }
]
}
```

## References

For more information on Twitter REST APIs refer to [Reference Documentation](https://dev.twitter.com/rest/reference)
