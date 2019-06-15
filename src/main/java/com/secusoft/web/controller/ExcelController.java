package com.secusoft.web.controller;

import com.secusoft.web.core.util.ExcelUtil;
import com.secusoft.web.mapper.PictureMapper;
import com.secusoft.web.model.PictureBean;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huanghao
 * @date 2019-06-15
 */
@RestController
public class ExcelController {
    @Resource
    private PictureMapper pictureMapper;

    @RequestMapping("trackExcel")
    //public void trackExecl(HttpServletResponse response,@RequestBody TrackBean trackBean){
    public void trackExecl(HttpServletResponse response){
        String[] headArray = {"序号","点位位置","设备ID","设备名称","抓拍时间","轨迹图片"};
        List<Object[]> contentList = new ArrayList<>();
        //List<PictureBean> pictureBeans = pictureMapper.selectPictureByTid(trackBean.getId());
        List<PictureBean> pictureBeans = pictureMapper.selectPictureByTid("8");
        if(!CollectionUtils.isEmpty (pictureBeans)){
            Integer i=1;
            for (PictureBean pictureBean : pictureBeans){
                String longitude = pictureBean.getDeviceBean().getLongitude();
                String latitude = pictureBean.getDeviceBean().getLatitude();

                StringBuffer point=new StringBuffer();
                point.append(longitude);
                point.append(",");
                point.append(latitude);
                Long time=pictureBean.getPictureTime();
                Date date = new Date(time);

                byte[] byUrl = getImageFromNetByUrl(pictureBean.getCropImageUrl());
                Object[] o={
                        i,
                        point,
                        pictureBean.getDeviceBean().getDeviceId(),
                        pictureBean.getDeviceBean().getDeviceName(),
                        date,
                        byUrl
                };
                contentList.add(o);
                i++;
            }
        }

        try {
            ExcelUtil.ExportExcel(response, headArray, contentList, "Task.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static byte[] getImageFromNetByUrl(String strUrl){
        try {
            //要放到try/catch里面
            URL url = new URL(strUrl);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 输入流
            InputStream is = con.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len=is.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            is.close();
            byte[] bytes = outStream.toByteArray();
            is.close();
            return  bytes;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
