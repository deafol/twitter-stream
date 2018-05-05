package io.sytac.exercise

import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestFactory
import org.interview.oauth.twitter.TwitterAuthenticator

def logger = System.out

HttpRequestFactory factory = new TwitterAuthenticator(logger, "RLSrphihyR4G2UxvA0XBkLAdl", "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4")
        .authorizedHttpRequestFactory
logger.println "Building Twitter request"
HttpRequest request = factory.buildGetRequest(new GenericUrl("https://stream.twitter.com/1.1/statuses/filter.json?track=bieber"))

logger.println "Parsing Twitter stream"
def tweets = TweetParser.parse(request.execute().content)

logger.println "Creating report in bieber-tweets.txt"
new File("bieber-tweets.txt").withWriter { writer ->
    writer.write("Bieber tweets (${tweets.size()}):\n")
    new TweetSorter().groupAndSort(tweets).each {writer.write(it.toString())}
}
logger.println("Done.")


