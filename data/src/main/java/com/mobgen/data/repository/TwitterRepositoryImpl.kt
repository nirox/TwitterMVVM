package com.mobgen.data.repository

import TwitterApiConstants
import com.mobgen.data.AuthManager
import com.mobgen.data.mapper.TweetDataMapper
import com.mobgen.data.repository.service.AuthenticationService
import com.mobgen.data.repository.service.TwitterSearchService
import com.mobgen.data.repository.service.TwitterTimeLineService
import com.mobgen.domain.TwitterRepository
import com.mobgen.domain.check
import com.mobgen.domain.model.Tweet
import com.mobgen.domain.subscribe
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class TwitterRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val twitterSearchService: TwitterSearchService,
    private val twitterTimeLineService: TwitterTimeLineService,
    private val authManager: AuthManager,
    private val tweetDataMapper: TweetDataMapper,
    @Named("token") private val token: String
) : TwitterRepository {

    companion object {
        private val tweetsCache = mutableSetOf<Tweet>()
        private const val USER_NAME = "MOBGEN"
    }

    override fun search(search: String): Single<List<Tweet>> =
        twitterSearchService.getTweetList(queries = search)
            .map { tweetDataMapper.map(it.statusesEntityList).also { tweets -> tweetsCache.addAll(tweets) } }

    override fun getNextSearch(search: String, maxId: String): Single<List<Tweet>> =
        twitterSearchService.getTweetList(queries = search, maxId = maxId)
            .map { tweetDataMapper.map(it.statusesEntityList).also { tweets -> tweetsCache.addAll(tweets) } }


    override fun getTimeLine(): Single<List<Tweet>> =
        twitterTimeLineService.getTimeLine(screenName = USER_NAME)
            .map { tweetDataMapper.map(it).also { tweets -> tweetsCache.addAll(tweets) } }

    override fun getNextTimeLine(maxId: String): Single<List<Tweet>> =
        twitterTimeLineService.getTimeLine(screenName = USER_NAME, maxId = maxId)
            .map { tweetDataMapper.map(it).also { tweets -> tweetsCache.addAll(tweets) } }

    override fun getTweetById(id: Long): Single<Tweet> =
        Single.create<Tweet> { emitter ->
            val tweet = tweetsCache.find { it.id == id }
            tweet.check(
                ifNotNull = {
                    emitter.onSuccess(it)
                },
                ifNull = {
                    emitter.onError(Throwable("Tweet not found"))
                })
        }

    override fun authenticateApp(): Completable =
        Completable.create { emitter ->
            authenticationService.getAuthentication(
                String.format(
                    TwitterApiConstants.AUTHENTICATION_HEADER_VALUE,
                    token
                ), TwitterApiConstants.GRANT_TYPE
            ).subscribe(
                executor = Schedulers.newThread(),
                onSuccess = { authenticationEntity ->
                    authManager.setAuthBearer(authenticationEntity.accessToken)
                    emitter.onComplete()
                },
                onError = {
                    emitter.onError(it)
                }
            )

        }

}