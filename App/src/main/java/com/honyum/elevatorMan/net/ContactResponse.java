package com.honyum.elevatorMan.net;

import com.honyum.elevatorMan.data.ContactDataGrideInfo;
import com.honyum.elevatorMan.net.base.Response;
import com.honyum.elevatorMan.net.base.ResponseBody;

import java.util.List;

/**
 * Created by Star on 2017/12/21.
 */

public class ContactResponse  extends Response {


    private ContactResponse.ContactResponseBody body;

    public void setBody(ContactResponse.ContactResponseBody body) {
        this.body = body;
    }

    public ContactResponse.ContactResponseBody getBody() {
        return body;
    }

    public static class ContactResponseBody extends ResponseBody {
       private String count;

        private List<ContactDataGrideInfo> dataGrid;
        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<ContactDataGrideInfo> getDataGrid() {
            return dataGrid;
        }

        public void setDataGrid(List<ContactDataGrideInfo> dataGrid) {
            this.dataGrid = dataGrid;
        }


    }


    /**
     * 根据json生成对象
     * @param json
     * @return
     */
    public static ContactResponse getContactResponse(String json) {
        return (ContactResponse) parseFromJson(ContactResponse.class, json);
    }
}
