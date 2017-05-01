package cn.itcast.elec.util;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.itcast.elec.domain.ElecUser;

public class ChartUtils {

	public String generalBarJpeg(List<Object[]> list) throws IOException {
		//生成dataSet数据
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i=0;list!=null && i<list.size();i++){
			Object[] objects = list.get(i);
			dataset.addValue(Double.parseDouble(objects[2].toString()), objects[0].toString(), objects[1].toString());
		}
		
		JFreeChart chart = ChartFactory.createBarChart3D("用户统计报表(所属单位)", // 主表题
				"所属单位名称", // X轴标题
				"数量", // Y轴的标题
				dataset, // 图表需要的数据
				PlotOrientation.VERTICAL, // 图表的方法,水平还是垂直
				true, // 是否显示图例
				true, // 是否显示工具提示
				true); // 首产生url链接

		// 获取图表区域对象
		System.out.println(chart.getPlot());
		CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot();

		// 获取x轴
		System.out.println(categoryPlot.getDomainAxis());
		CategoryAxis3D categoryAxis3D = (CategoryAxis3D) categoryPlot
				.getDomainAxis();

		// 获取y轴
		System.out.println(categoryPlot.getRangeAxis());
		NumberAxis3D numberAxis3D = (NumberAxis3D) categoryPlot.getRangeAxis();

		// 绘图区域对象 xxxRender
		System.out.println(categoryPlot.getRenderer());
		BarRenderer3D barRenderer3D = (BarRenderer3D) categoryPlot
				.getRenderer();

		/** ************************************************************************************************* */
		// 处理主标题乱码
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
		// 处理子标题乱码
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 18));
		/** ************************************************************************************************* */
		// 处理X轴乱码
		// 处理x轴上的乱码
		categoryAxis3D.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
		// 处理x轴外的乱码
		categoryAxis3D.setLabelFont(new Font("宋体", Font.BOLD, 15));

		// 处理Y轴乱码
		// 处理Y轴上的乱码
		numberAxis3D.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
		// 处理Y轴外的乱码
		numberAxis3D.setLabelFont(new Font("宋体", Font.BOLD, 15));
		/** ************************************************************************************************* */
		// 处理Y轴的刻度
		// 设置y轴不是使用自动刻度
		numberAxis3D.setAutoTickUnitSelection(false);
		// 设置刻度
		NumberTickUnit numberTickUnit = new NumberTickUnit(1);
		numberAxis3D.setTickUnit(numberTickUnit);
		/** ************************************************************************************************* */
		// 处理图形的的宽度
		barRenderer3D.setMaximumBarWidth(0.08);

		// 处理柱体上的数字
		barRenderer3D
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// 设置柱体上的数字可见
		barRenderer3D.setBaseItemLabelsVisible(true);

		// 处理柱体上的数字的字体
		barRenderer3D.setBaseItemLabelFont(new Font("宋体", Font.BOLD, 15));
		
		/** ************************************************************************************************* */
		/**保存生成的图形到某个路径下*/
		//获取图片的保存的路径
		String basepath = ServletActionContext.getServletContext().getRealPath("/char");
		
		//利用时间生成文件名称
		String filename=DateFormatUtils.format(new java.util.Date(),"yyyyMMddHHmmss")+".jpeg";
		
		try{
			File file=new File(basepath+"/"+filename);
			ChartUtilities.saveChartAsJPEG(file, chart, 600, 400);
		}catch(IOException e){
			e.printStackTrace();
		}
		return filename;
	}
}
