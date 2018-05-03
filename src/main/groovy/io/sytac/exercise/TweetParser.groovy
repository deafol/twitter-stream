package io.sytac.exercise

import com.google.api.client.http.HttpResponse
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.JsonObjectParser
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type

class TweetParser {

    static JsonFactory JSON_FACTORY = new JacksonFactory()

    //TODO make the parm the inputstream
    //TODO refactor to an Observable that parses first 100 of max 30 seconds
    List<Tweet> parse(HttpResponse tweetStream) {
//        Type type = new TypeToken<Tweet>() {}.getType()
//        tweetStream.parseAs(type) as Tweet
        def tweetStreamReader = new InputStreamReader(tweetStream.content)
        def tweet
        def tweets = []
        while ((tweet = tweetStreamReader.readLine()) != null) {
            tweets.add(new JsonObjectParser(JSON_FACTORY).parseAndClose(new StringReader(tweet), Tweet))
        }
        tweets
    }
}
