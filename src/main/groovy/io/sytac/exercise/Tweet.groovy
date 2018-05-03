package io.sytac.exercise

import com.google.api.client.util.Key

class Tweet {
    @Key
    def id
    @Key("created_at")
    def creationDate
    @Key
    def text
    @Key("user")
    TwitterUser author


    @Override
    String toString() {
        return "\nTweet{" +
                "id=" + id +
                ", creationDate='" + creationDate + '\'' +
                ", text='" + text + '\'' +
                '}'
    }
}
