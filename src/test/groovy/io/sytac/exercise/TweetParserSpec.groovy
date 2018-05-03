package io.sytac.exercise

import com.google.api.client.http.HttpTransport
import com.google.api.client.http.LowLevelHttpRequest
import com.google.api.client.http.LowLevelHttpResponse
import com.google.api.client.json.Json
import com.google.api.client.json.JsonObjectParser
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.testing.http.HttpTesting
import com.google.api.client.testing.http.MockHttpTransport
import com.google.api.client.testing.http.MockLowLevelHttpRequest
import com.google.api.client.testing.http.MockLowLevelHttpResponse
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

class TweetParserSpec extends Specification {

    def "test it"() {
        when:
            def tweets = new TweetParser().parse(testResponse())
        then:
            tweets != null
            tweets.each {println it.id}
    }

    def testResponse() {
        HttpTransport transport = new MockHttpTransport() {
            @Override
            LowLevelHttpRequest buildRequest(String method, String url) throws IOException {
                return new MockLowLevelHttpRequest() {
                    @Override
                    LowLevelHttpResponse execute() throws IOException {
                        MockLowLevelHttpResponse result = new MockLowLevelHttpResponse()
                        result.contentType = Json.MEDIA_TYPE
                        result.content = new ClassPathResource("tweet.json").inputStream
                        result
                    }
                }
            }
        }
        def request = transport.createRequestFactory().buildGetRequest(HttpTesting.SIMPLE_GENERIC_URL)
        request.parser = new JsonObjectParser(TweetParser.JSON_FACTORY)
        request.execute()
//        LowLevelHttpResponse response = new MockLowLevelHttpResponse()
//        response.content = new ClassPathResource("tweet.json").inputStream
//        new HttpResponse(new MockLowLevelHttpRequest(), response)

    }
}
