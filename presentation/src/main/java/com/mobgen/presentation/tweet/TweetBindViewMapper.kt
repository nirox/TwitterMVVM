package com.mobgen.presentation.tweet

import com.mobgen.domain.model.Tweet
import com.mobgen.presentation.Util
import com.mobgen.presentation.ViewMapper
import javax.inject.Inject

class TweetBindViewMapper @Inject constructor() : ViewMapper<Tweet, TweetBindView> {
    override fun map(value: Tweet): TweetBindView {
        return TweetBindView(
            value.image.trim(),
            "@${value.userId.trim()}",
            value.name.trim(),
            value.content.trim(),
            value.medias,
            value.videos,
            Util.getTwetDateSimple(value.date, Util.LONG_FORMAT_DATE)
        )
    }

}