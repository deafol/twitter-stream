import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestFactory
import com.google.api.client.http.HttpResponse
import io.sytac.exercise.Tweet
import io.sytac.exercise.TweetParser
import io.sytac.exercise.TweetSorter
import org.interview.oauth.twitter.TwitterAuthenticator

//def url = new TwitterUrl("https://stream.twitter.com/1.1/statuses/filter.json")
//url.track = "bieber"
HttpRequestFactory factory = new TwitterAuthenticator(System.out, "RLSrphihyR4G2UxvA0XBkLAdl", "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4")
        .authorizedHttpRequestFactory
HttpRequest request = factory.buildGetRequest(new GenericUrl("https://stream.twitter.com/1.1/statuses/filter.json?track=bieber"))
HttpResponse response = request.execute()

def tweets = new TweetParser().parse(response.content)

def bieberTweets = new TweetSorter().sort(tweets)

new File("output.json").withWriter {writer ->
    bieberTweets.each {println it}
//    bieberTweets.each {writer.writeLine(it.toString())}
}


