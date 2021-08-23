package com.rick.oss.client;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.rick.oss.conf.OSSConfig;
import com.rick.oss.model.ImageObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rick
 * @createdAt 2021-08-19 14:47:00
 */
@Component
@RequiredArgsConstructor
public class OssStoryPicClient implements InitializingBean {

    private final OSSConfig ossConfig;

    private OSS ossClient;

    /**
     * 上传文件
     * @param storyId
     * @param imageObjectList
     * @return
     */
    public List<String> upload(long storyId, List<ImageObject> imageObjectList) {
        if (CollectionUtils.isEmpty(imageObjectList)) {
            return Collections.emptyList();
        }

        List<String> storyPicList = new ArrayList<>();
        int size = imageObjectList.size();
        for (int i = 0; i < size; i++) {
            ImageObject imageObject = imageObjectList.get(i);

            String name = storyId + "-" + i + "." + imageObject.getExt();
            ossClient.putObject(ossConfig.getBucketName(),
                    name,
                    imageObject.getIs());

            storyPicList.add("https://" + ossConfig.getBucketName() + ".oss-cn-shanghai.aliyuncs.com/" + name);
        }

        return storyPicList;
    }

    /**
     * 根据url删除文件
     * @param urlList
     */
    public void deleteFiles(List<String> urlList) {
        if (CollectionUtils.isEmpty(urlList)) {
            return;
        }

        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(ossConfig.getBucketName());
        deleteObjectsRequest.setKeys(urlList.stream().map(url -> StringUtils.getFilename(url)).collect(Collectors.toList()));
        ossClient.deleteObjects(deleteObjectsRequest);
    }

    @Override
    public void afterPropertiesSet() {
        ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
    }
}
