package io.sytac.exercise

import com.google.api.client.json.JsonObjectParser
import com.google.api.client.json.jackson2.JacksonFactory
import rx.observables.StringObservable

import java.util.concurrent.TimeUnit

class TweetParser {

    static final int DEFAULT_TIMEOUT = 30
    static final int DEFAULT_MAX_TWEETS = 100
    static final JsonObjectParser jsonObjectParser = new JsonObjectParser(new JacksonFactory())

    /**
     * Parses a stream of Tweets from json to Tweet instances. The stream is split on \r\n symbols denoting the end of a
     * single tweet
     * Closing the stream after either the timeout has been reached or a maximum number of tweets has been parsed
     * timeout and maxTweets can be provided an default to the numbers provide in this class
     */
    static List<Tweet> parse(InputStream tweetStream, maxTweets = DEFAULT_MAX_TWEETS, int timeout = DEFAULT_TIMEOUT) {
        def tweets = []
        StringObservable.split(StringObservable.decode(StringObservable.from(tweetStream), "UTF-8"), "\\r\\n")
                .take(timeout ?: DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .take(maxTweets ?: DEFAULT_MAX_TWEETS)
                .subscribe(
                { tweet ->
                    if (tweet?.trim()) {
                        tweets.add(jsonObjectParser.parseAndClose(new StringReader(tweet), Tweet))
                    }
                },
                { error -> println "ERROR: $error" },
                { tweetStream.close() })
        tweets
    }
}
