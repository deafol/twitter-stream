package io.sytac.exercise

import spock.lang.Specification

class TweetSorterSpec extends Specification {

    def "group and sort"() {
        setup:
            def author1 = new TwitterUser(id: 1, creationDate: "2 jan", name: "author1", screenName: "auth1")
            def author2 = new TwitterUser(id: 2, creationDate: "1 jan", name: "author2", screenName: "auth2")
            def tweet1 = new Tweet(id: 1, creationDate: "1 feb", text: "text1", author: author1)
            def tweet2 = new Tweet(id: 2, creationDate: "2 feb", text: "text2", author: author1)
            def tweet3 = new Tweet(id: 3, creationDate: "3 feb", text: "text3", author: author2)
            def tweets = [tweet1, tweet2, tweet3]
        when:
            def sortedTweets = new TweetSorter().sort(tweets)
        then: "check the grouping"
            sortedTweets.get(author1).size() == 2
            sortedTweets.get(author2).size() == 1
        and: "check the sorting"
            sortedTweets.entrySet()[0].key.name == 'author2'
            sortedTweets.entrySet()[1].key.name == 'author1'
            sortedTweets.each {println it}
    }
}
