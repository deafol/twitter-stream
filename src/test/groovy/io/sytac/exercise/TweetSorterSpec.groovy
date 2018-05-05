package io.sytac.exercise

import spock.lang.Specification

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class TweetSorterSpec extends Specification {


    def "testing grouping and sorting"() {
        setup:
            def earlyDate = "Thu Sep 24 19:55:36 +0000 2015"
            def laterDate = "Fri Sep 25 19:55:36 +0000 2015"
            def author1 = new TwitterUser(id: 1, creationDate: laterDate, name: "author1", screenName: "auth1")
            def author2 = new TwitterUser(id: 2, creationDate: earlyDate, name: "author2", screenName: "auth2")
            def tweet1 = new Tweet(id: 1, creationDate: laterDate, text: "text1", author: author1)
            def tweet2 = new Tweet(id: 2, creationDate: earlyDate, text: "text2", author: author1)
            def tweet3 = new Tweet(id: 3, creationDate: earlyDate, text: "text3", author: author2)
            def tweets = [tweet1, tweet2, tweet3]
        when:
            def sortedTweets = TweetSorter.groupAndSort(tweets)
        then: "check the grouping: 2 authors, resp 2 and 1 tweets"
            sortedTweets.size() == 2
            sortedTweets.get(author1).size() == 2
            sortedTweets.get(author2).size() == 1
        and: "check the sorting: author2 before author1, tweet2 before tweet1 "
            sortedTweets.entrySet()[0].key.name == 'author2'
            sortedTweets.entrySet()[1].key.name == 'author1'
            sortedTweets.values()[1][0].id == 2
            sortedTweets.values()[1][1].id == 1

            sortedTweets.each { println it }
    }

    def "Wed May 02 10:26:57 +0000 2018 should parse the corresponding java Date"() {
        setup:
            ZonedDateTime zonedDt = ZonedDateTime.of(2018,5,2,10,26,57,0,ZoneId.of("UTC"))
        expect:
            TweetSorter.parseDate("Wed May 02 10:26:57 +0000 2018") == Date.from(Instant.from(zonedDt))
    }

}
