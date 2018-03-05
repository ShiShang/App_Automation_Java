package com.appium.tuniulvyou;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.appium.java_client.android.AndroidDriver;


/*实现ItestListener 的 onS tart 方法
 *监听测试用例的执行
 *记录测试用例执行情况
 *完成错误截图
 */

public class PrepareWork implements ITestListener{
	
	static String template_content;                                                                             //简洁模板内容
	static String file_path;                                                                                    //文件路径
	static String folder_path;                                                                                  //文件夹路径
	static ArrayList<String> failed_list=new ArrayList();                                                       //失败测试用例汇总
	static ArrayList<String> passed_list=new ArrayList();                                                       //成功测试用例汇总
	static String email_content="";                                                                             //邮件内容
	
	private StringBuffer buffer=new StringBuffer();
	private util ul=new util();
	
	public void onStart(ITestContext context)
	{
		
		 ul.Create_Result_Forlder();                                                                             //如果没有Results文件夹，则创建结果文件夹
		 ul.Create_Test_Forlder();                                                                               //创建本次测试文件夹
		 folder_path=ul.Get_Test_Path();
		 file_path=folder_path+"/"+"report.html";

		File htmlReport_file=new File(file_path);                                                                //创建report文件
		if(!htmlReport_file.exists())
		{
			try {
				    htmlReport_file.createNewFile();
				    System.out.println("Report folder path:  "+file_path);
			    } 
			catch (IOException e)
			    {
				    e.printStackTrace();
			    }
		}
		
		//读取简洁邮件模板
		
		File template_path=new File("email_template");
		String template_file=template_path.getAbsolutePath()+"/"+"UI_mail_template.html";
		try {
			    InputStream is=new FileInputStream(template_file);
			    String line;
			    BufferedReader reader=new BufferedReader(new InputStreamReader(is));
			    line=reader.readLine();
			    while(line !=null)
			    {
				    buffer.append(line);
			     	buffer.append("\n");
				    line=reader.readLine();
		     	}
			    reader.close();
			    is.close();
			    this.template_content=buffer.toString();
			    this.email_content=buffer.toString();
	     	} 
		catch (FileNotFoundException e)
		    {
			    e.printStackTrace();
		    }
		catch (IOException e) 
		    {
			    e.printStackTrace();
		    }

		try {
		     	Files.write(Paths.get(file_path),template_content.getBytes("GBK"));                  		         //将简洁模板写入生成的report文件中              
		    }catch(IOException e)
		    {
			    e.printStackTrace();
		    }
	}

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult result)
	{
		
		StringBuilder caseRes=new StringBuilder("<tr><td colspan=\"2\"><font face=\"微软雅黑\">");                   //定制模板内容
		caseRes.append(ul.get_testcase_name(result.getMethod().getMethodName()));
		caseRes.append("</td><td colspan=\"2\"><span style=\"color:#66CC00\"><br>Pass<br><br></td></tr>");		
		String res=caseRes.toString();  
		email_content=email_content+res;
		
        try {  
                Files.write((Paths.get(file_path)),res.getBytes("GBK"),StandardOpenOption.APPEND);  
            } 
        catch (IOException e) 
            {  
                e.printStackTrace();  
            }  	
        passed_list.add(result.getMethod().getMethodName());                                                         //执行成功，将用例添加进成功测试用例组
	}

	public void onTestFailure(ITestResult result)
	{
		
		AndroidDriver driver=new TestTuniulvyou_automation().getDriver();                                            //获取句柄
		ScreenshotTestRule.takeScreenshot(driver);                                                                   //失败截图

	    StringBuilder caseRes = new StringBuilder("<tr><td colspan=\"2\"><font face=\"微软雅黑\">");  
	    caseRes.append(ul.get_testcase_name(result.getMethod().getMethodName()));  
	    caseRes.append("</td><td colspan=\"2\"><br><font face=\"微软雅黑\">");   
	    caseRes.append("<br><br>");  
	    Throwable throwable = result.getThrowable();  
	    caseRes.append("测试用例运行失败，原因： "); 
	    
	        /*caseRes.append("<br><br>");                                                                            //打印错误堆栈信息
	        StackTraceElement[] se = throwable.getStackTrace();  
	        caseRes.append("堆栈信息:");  
	        caseRes.append("<br>");  
	        for (StackTraceElement e : se) {  
	            caseRes.append(e.toString());  
	            caseRes.append("<br>");  
	        }  */
	    
	     caseRes.append("<br><br><br><br>");
	     caseRes.append("</td></tr>");  
	     String res = caseRes.toString();  
	     email_content=email_content+res;
	     try {  
	            Files.write((Paths.get(file_path)),res.getBytes("GBK"),StandardOpenOption.APPEND);  
	         } 
	     catch (IOException e) 
	         {  
	            e.printStackTrace();  
	         }  
	        
	     //添加到失败用例组
	     failed_list.add(result.getMethod().getMethodName());
	}

	public void onTestSkipped(ITestResult result) 
	{
		
		StringBuilder caseRes=new StringBuilder("<tr><td><font face=\"微软雅黑\">");
		caseRes.append(ul.get_testcase_name(result.getMethod().getMethodName()));
		caseRes.append("</td><td>Skipped</td><\tr>");
		String res=caseRes.toString();
		email_content=email_content+res;
	    try
	    {
	    	Files.write((Paths.get(file_path)),res.getBytes("GBK"),StandardOpenOption.APPEND);
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext context) 
	{
		String msg="</tbody></table></div></html>";
		email_content=email_content+msg;
		try
		{
			Files.write(Paths.get(file_path),msg.getBytes("GBK"),StandardOpenOption.APPEND);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}