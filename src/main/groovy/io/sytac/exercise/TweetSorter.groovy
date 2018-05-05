package io.sytac.exercise

import java.text.SimpleDateFormat

class TweetSorter {

    /**
     * First sorts a list of tweets by their creationDate ascending, then
     * groups the list by its author (creating a map wit sublists of tweets) and
     * finally sorts the output by authors creationDate ascending
     */
    static groupAndSort(List<Tweet> tweets) {
        tweets.sort { a, b -> parseDate(a["creationDate"]) <=> parseDate(b["creationDate"]) }
                .groupBy { tweet -> tweet.author }
                .sort { a, b -> parseDate(a.key["creationDate"]) <=> parseDate(b.key["creationDate"]) }
    }

    /**
     * Parses a String with Twitter created_at date-format to a java Date
     */
    static parseDate(String twitterDate) {
        new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy").parse(twitterDate)
    }
}
