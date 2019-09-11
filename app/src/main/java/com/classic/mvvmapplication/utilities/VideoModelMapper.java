package com.classic.mvvmapplication.utilities;

import com.classic.mvvmapplication.data.models.api.VideoRemote;
import com.classic.mvvmapplication.data.models.local.VideoLocal;

public class VideoModelMapper {

    public static VideoLocal mapRemoteVideoToLocal(VideoRemote videoRemote,String relType,int relId){
        return new VideoLocal(videoRemote.getId(),relId,relType,videoRemote.getIso6391(),
                videoRemote.getIso31661(),videoRemote.getKey(),videoRemote.getName(),
                videoRemote.getSite(),videoRemote.getSize(),videoRemote.getType());
    }
}
