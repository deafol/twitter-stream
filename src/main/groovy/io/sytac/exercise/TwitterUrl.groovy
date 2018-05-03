package io.sytac.exercise

import com.google.api.client.http.GenericUrl
import com.google.api.client.util.Key

class TwitterUrl extends GenericUrl {

    TwitterUrl(String encodedUrl) {
        super(encodedUrl)
    }

    @Key
    String track
}
