package io.sytac.exercise

import java.text.SimpleDateFormat

class TweetSorter {

    def groupAndSort(List<Tweet> tweets) {
        tweets.groupBy { tweet -> tweet.author }
                .sort { a, b -> parseDate(a.key["creationDate"]) <=> parseDate(b.key["creationDate"])}
    }

    def parseDate(String twitterDate) {
        new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy").parse(twitterDate)
    }
}
