class TwitterApiConstants {
    companion object {
        const val API_TWITTER_AUTHENTICATION = "oauth2/token"
        const val API_TWITTER_TIME_LINE = "/1.1/statuses/user_timeline.json"
        const val API_TWITTER_SEARCH = "/1.1/search/tweets.json"
        const val BASE_URL = "https://api.twitter.com/"
        const val GRANT_TYPE = "client_credentials"
        const val AUTHORIZATION_HEADER_KEY = "Authorization"
        const val AUTHENTICATION_HEADER_VALUE = "Basic %s"
        const val AUTHORIZATION_HEADER_VALUE = "Bearer %s"
        const val AUTHORIZATION_HEADER_CONTENT_TYPE_KEY = "Content-type"
        const val AUTHORIZATION_HEADER_CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded;charset=UTF-8"
    }
}