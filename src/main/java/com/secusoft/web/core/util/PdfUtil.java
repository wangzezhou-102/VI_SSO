package com.secusoft.web.core.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.secusoft.web.model.PatrolReportBean;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

/**
*  生成PDF格式文档
* */
public class PdfUtil {
   /* *//**
     * 抠模板
     * @throws Exception
     *//*
    public void createAllPdf() throws Exception {
        //填充创建pdf
        PdfReader reader = null;
        PdfStamper stamp = null;
        try {
            reader = new PdfReader("E:/module.pdf");
            SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
            String times = simp.format(new Date()).trim();
            //创建生成报告名称
            String root = ServletActionContext.getRequest().getRealPath("/upload") + File.separator;
            if (!new File(root).exists())
                new File(root).mkdirs();
            File deskFile = new File(root, times + ".pdf");
            stamp = new PdfStamper(reader, new FileOutputStream(deskFile));
            //取出报表模板中的所有字段
            AcroFields form = stamp.getAcroFields();
            // 填充数据
            form.setField("name", "zhangsan");
            form.setField("sex", "男");
            form.setField("age", "15");

            //报告生成日期
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            String generationdate = dateformat.format(new Date());
            form.setField("generationdate", generationdate);
            stamp.setFormFlattening(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stamp != null) {
                stamp.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }*/
    public boolean turnToPdf(HttpServletResponse response, PatrolReportBean patrolReportBean) throws Exception{
        //创建文本对象
        Document document = new Document(PageSize.A4, 40, 40, 60, 60);
        //建立书写器,与文档对象关联
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, ba);
        writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
        writer.setStrictImageSequence(true);//图片精确放置
        /*writer.setEncryption("123".getBytes(),"123".getBytes(), PdfWriter.ALLOW_SCREENREADERS,
                PdfWriter.STANDARD_ENCRYPTION_128);*/
        //设置文档标题
        document.addTitle(patrolReportBean.getReportName()+"报告");
        //设置文档主题
        document.addSubject("巡逻报告");
        //设置关键字
        document.addKeywords("报告");
        //设置作者
        document.addAuthor("视在数科");
        //设置创建者
        document.addCreator("wangzezhou");
        //设置生产者
        document.addProducer();
        //设置创建日期
        document.addCreationDate();
        //add Chinese font
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font boldfont = new Font(bfChinese,14,Font.BOLD);//字体加粗
        //打开文档
        document.open();
        //向文档中添加内容
        //document.add(new Paragraph("巡逻任务内容"));(默认不支持中文字体)
        //设置文本块
        //Chunk chunk = new Chunk("123",FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE));
        //设置短语
        /*Phrase phrase = new Phrase();
        phrase.add(chunk);*/
        //报告标题
        Paragraph reportTitle = new Paragraph(patrolReportBean.getReportName()+"报告", boldfont);
        //1为居中对齐、2为右对齐、3为左对齐,默认为左对齐
        reportTitle.setAlignment(1);
        //PdfContentByte cb = writer.getDirectContent();生成条形码图片时使用
        //创建表格
        PdfPTable pdfTable = createPdfTable(2,30f);
        //巡逻任务名称
        pdfTable.addCell(createPdfCell("巡逻任务名称"));
        pdfTable.addCell(createPdfCell(patrolReportBean.getReportName()));
        //报告时间范围
        pdfTable.addCell(createPdfCell("报告时间范围"));
        pdfTable.addCell(createPdfCell("2019/05/06-2019/06/06"));
        //巡逻循环范围
        PdfPCell pCell = createPdfCell("巡逻循环范围");
        pCell.setRowspan(2);
        pdfTable.addCell(pCell);
        pdfTable.addCell(createPdfCell("日期 19/06/05  19/06/07  19/06/08  19/06/09  19/06/10  19/06/11  19/07/12  19/06/08  19/06/08  19/06/08  19/06/08  19/06/08 "));
        pdfTable.addCell(createPdfCell("时间  00:00-00:30  01:00-02:30  01:00-02:30  01:00-02:30  01:00-02:30  01:00-02:30  01:00-02:30  01:00-02:30  01:00-02:30  01:00-02:30"));
        //巡逻目标库
        pdfTable.addCell(createPdfCell("巡逻目标库"));

        pdfTable.addCell(createPdfCell("重点人员库 涉毒人员库 重点物品库 重点事件库 重点事件库 重点事件库 重点事件库 重点事件库 重点事件库"));

        //巡逻任务报警
        pdfTable.addCell(createPdfCell("巡逻任务报警"));
        pdfTable.addCell(createPdfCell(""));//
        //创建表格
        PdfPTable pdfTable1 = createPdfTable(3, 0);
        //设置图像
        for(int i=0;i<10;i++){
           //创建图像单元格
            PdfPCell pdfImageCell = createPdfImageCell("");
            PdfPCell pdfImageCell2 = createPdfImageCell("");
            PdfPCell pdfCell = createPdfCell("姓名:  张无忌","报警时间:  2019/06/25  22:23:46     报警设备:  定安路-西湖大道路口","报警地点:  经度:  120.15  纬度:  30.28","状态:  已处理");
            pdfTable1.addCell(pdfImageCell);
            pdfTable1.addCell(pdfImageCell2);
            pdfTable1.addCell(pdfCell);
        }
        document.add(reportTitle);
        document.add(pdfTable);
        document.add(pdfTable1);
        //关闭文档
        document.close();
        response.setHeader("content-Type", "application/pdf");
        ServletOutputStream out = response.getOutputStream();
        ba.writeTo(out);//将字节数组输出流中的数据写入指定的OutputStream输出流中.
        out.flush();
        return true;
    }
    //创建表格
    public PdfPTable createPdfTable(int cols,float spacingBefore) throws  Exception{
        // 添加表格
        PdfPTable table = new PdfPTable(cols);
        // 设置表格宽度比例为%100
        table.setWidthPercentage(100);
        if(cols == 2){
            float[] wid ={0.16f,0.84f}; //两列宽度的比例
            table.setWidths(wid);
        }
        if(cols == 3){
            float[] wid = {0.15f,0.15f,0.7f};
            table.setWidths(wid);
        }
        // 设置表格的宽度
        //table.setTotalWidth(500);
        // 也可以每列分别设置宽度
        /*table.setTotalWidth(new float[] { 130, 100 });
        // 锁住宽度
        table.setLockedWidth(true);*/
        // 设置表格上面空白宽度
         table.setSpacingBefore(spacingBefore);
        // 设置表格下面空白宽度
       // table.setSpacingAfter(10f);
        // 设置表格默认为无边框
        table.getDefaultCell().setBorder(0);
        return table;
    }
    //创建含文本单元格(多段文本)
    public PdfPCell createPdfCell(String... contents) throws Exception{
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font boldfont = new Font(bfChinese,14,Font.BOLD);//字体加粗
        Font noramlfont = new Font(bfChinese,11,Font.NORMAL);//字体正常
        // 构建每个单元格
        PdfPCell cell = new PdfPCell();
        for (String content : contents) {
            Paragraph paragraph = new Paragraph(content,noramlfont);
            paragraph.setPaddingTop(5f);
            cell.addElement(paragraph);
        }
        // 边框颜色
        //cell.setBorderColor(BaseColor.BLUE);
        // 设置背景颜色
        //cell.setBackgroundColor(BaseColor.ORANGE);
        // 设置跨两行
        //cell1.setRowspan(2);
        // 设置距左边的距离
        cell.setPaddingLeft(10f);
        //距离上边的距离
        cell.setPaddingTop(10f);
        // 设置高度
        //cell.setFixedHeight(50);
        // 设置内容水平居中显示
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderWidth(0);
        return cell;
    }
    //创建含图像单元格
    public PdfPCell  createPdfImageCell(String url) throws Exception{
        Image image = Image.getInstance("F:\\360downloads\\test.jpg");
        //image.setAlignment(Image.ALIGN_LEFT);
        image.scaleAbsolute(70f,70f);
        PdfPCell pdfPCell = new PdfPCell(image,false);//
        pdfPCell.setBorderWidth(0);
        // 设置距左边的距离
        pdfPCell.setPaddingLeft(10f);
        //距离上边的距离
        pdfPCell.setPaddingTop(10f);
        return pdfPCell;
    }

}
