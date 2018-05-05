package io.sytac.exercise

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.util.stream.IntStream

class TweetParserSpec extends Specification {

    def "parsing of tweetstream should result in a list with 2 Tweet instances with corresponding id's"() {
        when:
            def tweets = TweetParser.parse(testStream())
        then:
            tweets.size() == 2
            tweets[0].id == 991625081062350848
            tweets[1].id == 991625081062350849

            tweets.each { println it }
    }

    def "when maximum of tweets is set to 4 only 4 tweets should be read from a stream of 8"() {
        when:
            def tweets = TweetParser.parse(testStream(8), 4)
        then:
            tweets.size() == 4

            tweets.each { println it }
    }

    def testStream() {
        new ClassPathResource("tweets.json").inputStream
    }

    def testStream(int numberOfTweets) {
        def mapper = new ObjectMapper()
        String tweets
        IntStream.rangeClosed(1, numberOfTweets).forEach { id ->
            tweets = String.join("\r\n", tweets, mapper.writeValueAsString(new Tweet(id: id)))
        }
        new ByteArrayInputStream(tweets.getBytes("UTF-8"))
    }
}
