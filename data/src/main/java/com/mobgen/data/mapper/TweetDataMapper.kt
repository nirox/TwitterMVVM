package com.mobgen.data.mapper

import TwitterApiConstants
import com.mobgen.data.entity.StatusesEntity
import com.mobgen.domain.Util
import com.mobgen.domain.model.Tweet
import javax.inject.Inject

class TweetDataMapper @Inject constructor() : DataMapper<StatusesEntity, Tweet> {
    override fun map(value: StatusesEntity): Tweet =
        Tweet(
            value.id,
            value.userEntity.profileImage,
            value.userEntity.screenName,
            value.userEntity.name,
            value.text,
            value.entity?.mediaEntityList?.map { it.mediaUrlHttps } ?: listOf(),
            value.extendedEntities?.mediaEntityList?.mapNotNull { it.videoInfo?.variants?.first()?.url } ?: listOf(),
            Util.getDate(value.date, TwitterApiConstants.DATE_FORMAT)
        )
}