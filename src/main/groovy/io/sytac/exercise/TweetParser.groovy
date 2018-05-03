package io.sytac.exercise

import com.google.api.client.json.JsonFactory
import com.google.api.client.json.JsonObjectParser
import com.google.api.client.json.jackson2.JacksonFactory
import rx.observables.StringObservable

import java.util.concurrent.TimeUnit

class TweetParser {

    static JsonFactory JSON_FACTORY = new JacksonFactory()
    static int DEFAULT_TIMEOUT = 30
    static int DEFAULT_MAX_TWEETS = 100
    def timeout
    def maxTweets

    //TODO refactor to an Observable that parses first 100 of max 30 seconds
    List<Tweet> parse(InputStream tweetStream) {
        def tweets = []
        def observable = StringObservable.decode(StringObservable.from(tweetStream), "UTF-8")
//        StringObservable.byLine(observable)
        StringObservable.split(observable,"\\r\\n")
                .take(timeout ?: DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .take(maxTweets ?: DEFAULT_MAX_TWEETS)
                .subscribe(
                { tweet ->
                    if (tweet?.trim()) {
                        tweets.add(new JsonObjectParser(JSON_FACTORY).parseAndClose(new StringReader(tweet), Tweet))
                    }
                },
                {error -> println "ERROR: $error"},
                { tweetStream.close() })

//        def tweetStreamReader = new InputStreamReader(tweetStream)
//        def tweet
//        while ((tweet = tweetStreamReader.readLine()) != null) {
//            tweets.add(new JsonObjectParser(JSON_FACTORY).parseAndClose(new StringReader(tweet), Tweet))
//        }
        tweets
    }
}
