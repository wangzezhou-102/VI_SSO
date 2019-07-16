package com.secusoft.web.core.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

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
    public void turnToPdf() throws Exception{
        //创建文本对象
        Document document = new Document();
        //建立书写器,与文档对象关联
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F://巡逻任务报告.PDF"));
        writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
        writer.setStrictImageSequence(true);//图片精确放置
        writer.setEncryption("123".getBytes(),"1234".getBytes(),1,1);
        //设置文档标题
        document.addTitle("黄龙体育馆巡逻任务报告");
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
        //Font headfont=new Font(bfChinese,10,Font.BOLD);
        Font boldfont = new Font(bfChinese,14,Font.BOLD);//字体加粗
        Font noramlfont = new Font(bfChinese,11,Font.NORMAL);//字体正常
        //打开文档
        document.open();
        //向文档中添加内容
        //document.add(new Paragraph("巡逻任务内容"));(默认不支持中文字体)
        //设置文本块
        //Chunk chunk = new Chunk("sakah",FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE));
        //设置短语
        /*Phrase phrase = new Phrase();
        phrase.add(chunk);*/

        //报告标题
        Paragraph reportTitle = new Paragraph("黄龙体育馆巡逻任务报告", boldfont);
        //1为居中对齐、2为右对齐、3为左对齐,默认为左对齐
        reportTitle.setAlignment(1);
        // 添加表格，2列
        PdfPTable table = new PdfPTable(2);
        //// 设置表格宽度比例为%100
        table.setWidthPercentage(100);
        // 设置表格的宽度
        //table.setTotalWidth(500);
        // 也可以每列分别设置宽度
        //table.setTotalWidth(new float[] { 160, 70, 130, 100 });
        // 锁住宽度
        table.setLockedWidth(true);
        // 设置表格上面空白宽度
        table.setSpacingBefore(10f);
        // 设置表格下面空白宽度
        table.setSpacingAfter(10f);
        // 设置表格默认为无边框
        table.getDefaultCell().setBorder(0);
        //PdfContentByte cb = writer.getDirectContent();生成条形码图片时使用

        // 构建每个单元格
        PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
        // 边框颜色
        cell1.setBorderColor(BaseColor.BLUE);
        // 设置背景颜色
        cell1.setBackgroundColor(BaseColor.ORANGE);
        // 设置跨两行
        cell1.setRowspan(2);
        // 设置距左边的距离
        cell1.setPaddingLeft(10);
        // 设置高度
        cell1.setFixedHeight(20);
        // 设置内容水平居中显示
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置垂直居中
        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell1);




        //巡逻任务名称
        Paragraph reportName = new Paragraph("巡逻任务名称        黄龙体育馆巡逻任务", noramlfont);
        reportName.setPaddingTop(40f);
        //报告时间范围
        Paragraph dateRange = new Paragraph("报告时间范围        2019/05/06-2019/06/06", noramlfont);
        //巡逻循环范围
        Paragraph cycleRange = new Paragraph("巡逻循环范围        日期 19/06/05  19/06/07  19/06/08  19/06/09  19/06/10  19/06/11  19/07/12  19/06/08  19/06/08  19/06/08  19/06/08  19/06/08  \n" +
                                                    "                                      时间  00:00-00:30  01:00-02:30", noramlfont);
        //巡逻目标库
        Paragraph repo = new Paragraph("巡逻目标库    重点人员库 涉毒人员库 重点物品库 重点事件库", noramlfont);
        //巡逻任务报警
        Paragraph alarm = new Paragraph("巡逻任务报警", noramlfont);
        //添加标题
        document.add(reportTitle);
        //添加任务名称
        document.add(reportName);
        //添加时间范围
        document.add(dateRange);
        //添加循环范围
        document.add(cycleRange);
        //添加目标库
        document.add(repo);
        //添加任务报警
        document.add(alarm);
        //设置图像
        for(int i=0;i<6;i++){
            Paragraph alarm1 = new Paragraph();
            alarm.setFont(noramlfont);
            Image image = Image.getInstance("F:\\360downloads\\1.jpg");
            image.setAlignment(Image.ALIGN_LEFT);
            image.scaleAbsolute(80f,80f);
            Image image2 = Image.getInstance("F:\\360downloads\\1.jpg");
            image2.setAlignment(Image.ALIGN_LEFT);
            image2.scaleAbsolute(80f,80f);

            Paragraph name = new Paragraph("姓名:张无忌"+i, noramlfont);
            Paragraph datetime = new Paragraph("报警时间:2019/06/25 22:23:46 报警设备:定安路-西湖大道路口", noramlfont);
            Paragraph address = new Paragraph("报警地点:经度：120.15  纬度：30.28", noramlfont);
            Paragraph state = new Paragraph("状态:已处理", noramlfont);

            alarm1.add(image);
            alarm1.add(image2);
            alarm1.add(name);
            alarm1.add(datetime);
            alarm1.add(address);
            alarm1.add(state);

            document.add(alarm1);
        }
        //关闭文档
        document.close();
    }

    public  void printPayment(HttpServletResponse response) throws Exception {
        // 创建文本对象
        Document document = new Document();
        //页面大小
        Rectangle pageSize = new Rectangle(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        //横向
        //pageSize.rotate();
        //页面背景色
        //pageSize.setBackgroundColor(BaseColor.ORANGE);
        document.setPageSize(pageSize);
        //创建书写器（Writer）对象
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, ba);
//	     下面response.getOutputStream()不行，会出现类似乱码
//		PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
        document.open();
        // 设置PDF ,即确定了表格的列数
        //设置宽度
        float[] widths = { 200, 200, 300, 500, 300, 300, 300, 300, 300, 300, 300,300,400,300,500,200,500,400,300};
        //表格处理   创建表格时必须指定表格的列数
        PdfPTable table = new PdfPTable(widths);
        table.setLockedWidth(true);
        table.setTotalWidth(800);//550
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        System.out.println("生成pdf");
        // 设置PDF表格标题
        pdfTitle(table);
       /* //制表单位等信息设置
        pdfTitle1(table,financeBatchId);
        //表头
        pdfTitle2(table);
        // 设置PDF表格内容
        pdfContent(table, financeBatchId);
        // 设置PDF合计
        pdfSubtotal(table, countDetails);
        //底部四个人名
        pdfDown(table,financeBatchId);*/
        // pdf文档中加入table
        document.add(table);
        document.close();
        response.setHeader("content-Type", "application/pdf");
        ServletOutputStream out = response.getOutputStream();
        ba.writeTo(out);//将字节数组输出流中的数据写入指定的OutputStream输出流中.
        out.flush();
    }

    /**
     * 设置标题
     * @param table
     * @throws Exception
     */
    private void pdfTitle(PdfPTable table) throws Exception {
        String titleContent1= "巡逻任务报告";
        // 表格的单元格
        PdfPCell cell = new PdfPCell();
        // 向单元格中插入数据
        // new Paragraph()是段落的处理，可以设置段落的对齐方式，缩进和间距。
        cell.setPhrase(new Paragraph(titleContent1));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);//设置表格行高
        cell.setBorderWidth(0f);//去除表格的边框
        cell.setColspan(19);
        //cell.setRowspan(19);
        table.addCell(cell);
    }

}
