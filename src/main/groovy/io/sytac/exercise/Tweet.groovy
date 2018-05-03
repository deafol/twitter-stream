package io.sytac.exercise

import com.google.api.client.util.Key

class Tweet {
    @Key
    long id
    @Key("created_at")
    String creationDate
    @Key
    String text
    @Key("user")
    String author
}
