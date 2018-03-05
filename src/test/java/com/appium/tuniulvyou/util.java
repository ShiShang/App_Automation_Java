package com.appium.tuniulvyou;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/*工具类*/

public class util {

	public File result_folder;;
	public File test_folder;
	static String folder_path;
	
	//获取当前时间命名
	
	public static String getNowDate()
	{ 
		Date currentTime=new Date(System.currentTimeMillis() );
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String dataString=formatter.format(currentTime.getTime());
		return dataString;
		
	}
	
	//生成结果文件夹
	
	public void Create_Result_Forlder()
	{
		File results_dir=new File("results");
		
		if(!results_dir.exists())
		{
			results_dir.mkdirs();
			System.out.println("Results folder path:  "+results_dir.toString());
		}
		this.result_folder=results_dir;
		
	}
	
	//获取结果文件夹路径
	
	public String Get_Result_Path()
	{
		String result_path=result_folder.getAbsolutePath();
		return result_path;
	}
	
	//生成文件文件夹
	
	public void Create_Test_Forlder()
	{
		File test_dir=new File(Get_Result_Path()+"/"+getNowDate());
		if(!test_dir.exists())
		{
			test_dir.mkdirs();
		}
		folder_path=test_dir.toString();
		System.out.println("File folder path:  "+test_dir.toString());
	}
	
	//获取文件的路径
	
	public String Get_Test_Path()
	{
		return folder_path;
	}
	
	//定义一个处理用例中文名称匹配
	
	public String get_testcase_name(String key)
	{
		Map mp=new HashMap();
		mp.put("LogIn","登录");
		mp.put("ShouYe_Link_Switch","首页-链接跳转");
		mp.put("ZhangHu_Page_Number","账户-数字显示");
		mp.put("Zhanghu_Page_Link_Switch","账户-链接跳转");
		mp.put("jijinlicai_order","基金理财—下单");
		mp.put("niubianxian_order","牛变现-下单");
		mp.put("tuniubao_order","途牛宝-下单");
		
		if(mp.containsKey(key))
		{
			return (String) mp.get(key);
		}
		else
		{
			return key;
		}
	}
}
