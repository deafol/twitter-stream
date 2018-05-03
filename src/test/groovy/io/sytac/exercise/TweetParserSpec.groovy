package io.sytac.exercise

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.util.stream.IntStream

class TweetParserSpec extends Specification {

    def "parsing of tweetstream should result in a list with 2 Tweet instances with corresponding id's"() {
        when:
            def tweets = new TweetParser().parse(testStream())
        then:
            2 == tweets.size()
            991625081062350848 == tweets[0].id
            991625081062350849 == tweets[1].id
            tweets.each { println it }
    }

    def "when maximum of tweets is set to 4 only 4 tweets should be read from a stream of 8"() {
        setup:
            def parser = new TweetParser()
            parser.maxTweets = 4
        when:
            def tweets = parser.parse(testStream(8))
        then:
            4 == tweets.size()
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
