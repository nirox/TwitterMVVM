package com.mobgen.data.mapper

import com.mobgen.data.entity.StatusesEntity
import com.mobgen.domain.model.Tweet
import javax.inject.Inject

class TweetDataMapper @Inject constructor() : DataMapper<StatusesEntity, Tweet> {
    override fun map(value: StatusesEntity): Tweet =
        Tweet(
            value.id,
            value.userEntity.profileImage,
            value.userEntity.screenName,
            value.userEntity.name,
            value.text
        )
}