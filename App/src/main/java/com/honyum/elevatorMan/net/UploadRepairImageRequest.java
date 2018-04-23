package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.net.base.RequestBean;
import com.honyum.elevatorMan.net.base.RequestBody;

/**
 * Created by Star on 2017/6/17.
 */

public class UploadRepairImageRequest extends RequestBean {



    private UploadRepairImageBody body;

    public UploadRepairImageBody getBody() {
        return body;
    }

    public UploadRepairImageRequest setBody(UploadRepairImageBody body) {
        this.body = body;
        return this;
    }

    public class UploadRepairImageBody extends RequestBody
    {
        private String img;

        public void setImg(String img) {
            this.img = img;
        }

        public String getImg() {
            return img;
        }
    }
}
