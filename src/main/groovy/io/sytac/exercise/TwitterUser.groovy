package io.sytac.exercise

import com.google.api.client.util.Key

class TwitterUser {
    @Key
    def id
    @Key("created_at")
    def creationDate
    @Key
    def name
    @Key("screen_name")
    def screenName

    @Override
    String toString() {
        "\nAuthor {id:$id, creationDate: $creationDate, name: $name, screenName: $screenName}"
    }
}
