package io.sytac.exercise

import com.google.api.client.json.JsonObjectParser
import com.google.api.client.json.jackson2.JacksonFactory
import rx.observables.StringObservable

import java.util.concurrent.TimeUnit

class TweetParser {

    static final int DEFAULT_TIMEOUT = 30
    static final int DEFAULT_MAX_TWEETS = 100
    static final JsonObjectParser jsonObjectParser = new JsonObjectParser(new JacksonFactory())
    def timeout
    def maxTweets

    List<Tweet> parse(InputStream tweetStream) {
        def tweets = []
        def observable = StringObservable.decode(StringObservable.from(tweetStream), "UTF-8")
        StringObservable.split(observable, "\\r\\n")
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
