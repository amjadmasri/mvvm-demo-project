package com.classic.mvvmapplication.utilities.modelsMappers;


import com.classic.mvvmapplication.data.models.api.ReviewRemote;
import com.classic.mvvmapplication.data.models.local.ReviewLocal;

public class ReviewModelMapper {

    public static ReviewLocal mapRemoteVideoToLocal(ReviewRemote reviewRemote, String relType, int relId){
        return new ReviewLocal(reviewRemote.getId(),reviewRemote.getAuthor(),reviewRemote.getContent(),reviewRemote.getUrl(),relId,relType);
    }
}
