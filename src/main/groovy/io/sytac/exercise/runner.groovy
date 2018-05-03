import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestFactory
import com.google.api.client.http.HttpResponse
import com.google.api.client.json.JsonObjectParser
import com.google.api.client.json.jackson2.JacksonFactory
import io.sytac.exercise.TweetParser
import io.sytac.exercise.TwitterUrl
import org.interview.oauth.twitter.TwitterAuthenticator

def url = new TwitterUrl("https://stream.twitter.com/1.1/statuses/filter.json")
url.track = "bieber"
//def url = new GenericUrl("https://github.com")
//Type type = new TypeToken<List<GenericJson>>() {}.getType()
HttpRequestFactory factory = new TwitterAuthenticator(System.out, "RLSrphihyR4G2UxvA0XBkLAdl", "FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4")
        .authorizedHttpRequestFactory
HttpRequest request = factory.buildGetRequest(url)
//request.setReadTimeout(5000)
request.parser = new JsonObjectParser(new JacksonFactory())
HttpResponse response = request.execute()
new TweetParser().parse(response.content).each {println()}

//new File("output.json").withWriter {writer ->
//    def input = new InputStreamReader(response.getContent())
//    String tweet
//    while((tweet = input.readLine()) != null) {
//        writer.writeLine(tweet)
//    }
//}

//def output
//try {
//  output = response.parseAs(GenericJson)
//} catch (SocketTimeoutException e){
//    println("timed out")
//}
//println output

