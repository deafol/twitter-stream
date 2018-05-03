package io.sytac.exercise

class TweetSorter {

    def sort(List<Tweet> tweets) {
        tweets.groupBy { tweet -> tweet.author }
                .sort { a, b -> a.key["creationDate"] <=> b.key["creationDate"]}
    }
}
