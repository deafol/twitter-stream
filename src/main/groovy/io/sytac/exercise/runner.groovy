import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestFactory
import io.sytac.exercise.TweetParser
import io.sytac.exercise.TweetSorter
import org.interview.oauth.twitter.TwitterAuthenticator

def logger = System.out
//def logger = new PrintStream("output.txt")

HttpRequestFactory factory = new TwitterAuthenticator(logger, "RLSrphihyR4G2UxvA0XBkLAdl", "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4")
        .authorizedHttpRequestFactory
HttpRequest request = factory.buildGetRequest(new GenericUrl("https://stream.twitter.com/1.1/statuses/filter.json?track=bieber"))

def tweets = new TweetParser().parse(request.execute().content)

logger.write("Bieber tweets (${tweets.size()}):\n")
logger.withWriter {writer ->
    new TweetSorter().sort(tweets).each {writer.write(it.toString())}
}


