package com.wool.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import com.wool.community.exception.CustomizeErrorCode;
import com.wool.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author WOOL
 * UCLOUD处理
 */
@Service
public class UCloudProvider {

    @Value("${ucloud.ufile.public}")
    private String publicKey;
    @Value("${ucloud.ufile.private}")
    private String privateKey;
    @Value("${ucloud.ufile.bucket}")
    private String bucket;
    @Value("${ucloud.ufile.region}")
    private String region;
    @Value("${ucloud.ufile.suffix}")
    private String suffix;
    @Value("${ucloud.ufile.expires}")
    private int expires;




    public String upload(InputStream fileStream, String mimeType, String fileName) {
        String[] split = fileName.split("\\.");
        String generateFileName;
        if (split.length > 1) {
            generateFileName = UUID.randomUUID() + split[split.length - 1];
        } else {
            throw new CustomizeException(CustomizeErrorCode.FAIL_UPLOAD_ERROR);
        }

        // 对象相关API的授权器
        ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(publicKey, privateKey);
        // 对象操作需要ObjectConfig来配置您的地区和域名后缀
        ObjectConfig config = new ObjectConfig(region, suffix);
        try {
            PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucket)
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long l, long l1) {

                        }
                    })
                    .execute();
            if (response != null && response.getRetCode() == 0) {
                String url = UfileClient.object(OBJECT_AUTHORIZER, config)
                        .getDownloadUrlFromPrivateBucket(generateFileName, bucket, expires)
                        .createUrl();
                return url;
            }else {
                return generateFileName;
            }

        } catch (UfileClientException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FAIL_UPLOAD_ERROR);
        } catch (UfileServerException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FAIL_UPLOAD_ERROR);
        }
    }

}
