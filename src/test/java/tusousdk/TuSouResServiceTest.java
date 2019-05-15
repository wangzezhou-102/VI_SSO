package tusousdk;

import com.alibaba.fastjson.JSONArray;
import com.secusoft.web.tusouapi.model.BaseRequest;
import com.secusoft.web.tusouapi.model.BaseResponse;
import com.secusoft.web.tusouapi.service.impl.TuSouResServiceImpl;
import org.junit.Test;

public class TuSouResServiceTest {

    @Test
    public void testResList(){
        TuSouResServiceImpl tuSouResService = new TuSouResServiceImpl();

        BaseRequest<Object> requestBody = new BaseRequest<>();
        requestBody.setRequestId(String.valueOf(System.currentTimeMillis()));
        requestBody.setData(new Object());

        BaseResponse<JSONArray> response = tuSouResService.resList(requestBody);

        assert "SUCCESS".equals(response.getErrorCode());
    }
}
