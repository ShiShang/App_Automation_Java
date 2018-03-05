package com.appium.tuniulvyou;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.MessagingException;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterTest;

/*
 * 
 * 这是一个JAVA 测试途牛旅游App-途牛金服模块脚本，
 * 主要用到Appium, TestNG框架。
 * 包含功能验证，错误截图，日志记录，测试报告的生成，邮件定制与发送,结果记录等。
 * 也可以将其中的各个Testcases进行分离，
 * 生成更便于维护的测试套件。
 * 此脚本使用Jenkins-Maven定时执行，1次/小时。
 * 
 * @author Shifawu
 * @Time   2017-11-20 14:49:01
 * 
 */

@Listeners({PrepareWork.class})                                                                              //监听测试用例执行情况
public class TestTuniulvyou_automation {
	
  private static AndroidDriver<AndroidElement> driver;                                                       //初始化
  static String file_path;                                                                                   //文件位置

  /* @return 返回当前句柄，错误截图时使用*/
  
  public static AndroidDriver getDriver()
  {
	  return driver;
  }
  
  /* @param 当前句柄*/
  
  public void setDriver(AndroidDriver driver)
  {
	  this.driver=driver;
  }
  
  @BeforeTest
  public void setUp() throws Exception 
  {
	 
	  //启动appium  
	  
      DesiredCapabilities capabilities = new DesiredCapabilities();  
      capabilities.setCapability("deviceName","3db08f2c6889-android");  
      capabilities.setCapability("automationName","Appium");  
      capabilities.setCapability("platformName","Android");  
      capabilities.setCapability("platformVersion","4.4.2");  
        
     //配置测试apk  
      
      capabilities.setCapability("appPackage", "com.tuniu.app.ui"); 
      capabilities.setCapability("appActivity", "com.tuniu.app.ui.homepage.LaunchActivity");  
      capabilities.setCapability("sessionOverride", true);                                                   //每次启动时覆盖session，否则第二次后运行会报错不能新建session  
      capabilities.setCapability("unicodeKeyboard", true);                                                   //设置键盘  
      capabilities.setCapability("resetKeyboard", false);                                                    //设置默认键盘为appium的键盘
      capabilities.setCapability("noReset",true);                                                            //不需要每次执行时重新打开一个App， 所以不需要重复登录 
      driver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); 
      setDriver(driver);   
  }  
  
  //登录
  
  @Test
  public void LogIn()
  {

	 appWait(5000);
	 TouchAction action=new TouchAction(driver);                                                       	     //登陆金融-理财      
	 action.press(800,1240).release().perform();
	 appWait(8000);                                                                                          //等待页面加载，加载速度比较慢
	                                                                                                         //如果有提示更新框则点击取消，没有则不做处理
	 if(! IfElementNotExist("新版本更新"))
	 {
		 driver.findElementByAccessibilityId("android:id/button2").click();
		 appWait(8000);
	 }
	 
	 try{
	         new WebDriverWait(driver,10).until(  ExpectedConditions.presenceOfElementLocated(By.name("您的总财富(元)")));
	         print("进入途牛金服页面成功！");
	    }
	 catch(Exception e)
	    {
		     print("进入途牛金服首页失败！");
	    }
  }
  
  //首页链接跳转
  
  @Test
  public void ShouYe_Link_Switch()
  {
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'途牛宝')]").click();
	  try{
	          new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.id(" 账单")));
	          print("途牛宝页面跳转成功！");
	          Goback();
	          appWait(5000);
	     }
	  catch(Exception e)
	     {
		      print("途牛宝页面跳转失败！");
	     }
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'定期')]").click();
	  try{
	          new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("途牛金服")));
	          print("定期理财页面跳转成功！");
	    	  Goback();
		      appWait(8000);
	     }
	  catch(Exception e)
	     {
		      print("定期理财页面跳转失败！");
	     }
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'基金')]").click();
	  try{
	          new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("热门基金")));
	          print("基金理财页面跳转成功！");
	    	  Goback();
		      appWait(10000);
	     }
	  catch(Exception e)
	     {
		      print("基金理财页面跳转失败！");

	     }
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'牛变现')]").click();
	  try{
	          new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("途牛金服")));
	          print("牛变现页面跳转成功！");
	    	  Goback();
		      appWait(6000);
	     }
	  catch(Exception e)
	     {
	 	      print("牛变现页面跳转失败！");
	     }
	  MoveUp();	                                                                                                       //手势滑动
	  appWait(2000);
	  List<AndroidElement> el=driver.findElementsByName("查看更多");
	  
	  //检查是否有定期理财产品显示在首页
	  
	  if(IfElementNotExist("定期理财 Heading"))
	     {
		      print("定期理财不存在！");
	     }
	  else
	     {
		      print("定期理财存在！");
		      el.get(0).click();
		      try{
	                   new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("途牛金服")));
	                   print("定期理财页面跳转成功（from 更多Link）！");
	    	           Goback();
		               appWait(2000);
			           el.remove(0);
	             }
		      catch(Exception e)
	             {
		               print("定期理财页面跳转失败（from 更多Link）！");
	             }
	      }
	  el.get(0).click();
	  try{
	          new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("热门基金")));
	          print("基金理财页面跳转成功（from 更多Link）！");
	    	  Goback();
		      appWait(8000);
	     }
	  catch(Exception e)
	     {
		      print("基金理财页面跳转失败（from 更多Link）！");
	     }
      driver.findElementsByAccessibilityId("近一年涨跌幅").get(0).click();
      try{
              new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("基金公司  Heading")));
              print("基金理财第一个产品跳转成功！");
    	      Goback();
	          appWait(6000);
         }
      catch(Exception e)
         {
	          print("基金理财第一个产品跳转失败！");
         }
  }
  
  @Test
  public void ZhangHu_Page_Number()
  {    
	  appWait(2000);
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'账户')]").click();
	  appWait(10000);
	  AndroidElement zongzichan=driver.findElementByXPath("//android.view.View[contains(@content-desc,'总资产')]");
	  Float zongzichan_YuE=Get_Number_From_String(zongzichan.getAttribute("name"));
	  try{
     	     assert(zongzichan_YuE>0);
	         print("总资产余额： "+zongzichan_YuE+"显示正确！");
	     }
	  catch(Exception e)
	     {
		     print("总资产余额： "+zongzichan_YuE+"显示不正确！");
	     }
	  Float tuniubao_YuE=Get_Number_From_String(driver.findElementByXPath("//android.view.View[contains(@content-desc,'途牛宝')]").getAttribute("name"));
	  try{
	         assert(tuniubao_YuE>0);
	         print("途牛宝余额： "+tuniubao_YuE+"显示正确！");
	     }
	  catch(Exception e){
		     print("途牛宝余额： "+tuniubao_YuE+"显示不正确！");
	     }
	  Float Licai_YuE=Get_Number_From_String(driver.findElementByXPath("//android.view.View[contains(@content-desc,'定期理财')]").getAttribute("name"));
	  try{
	         assert(Licai_YuE>=0);
	         print("理财余额： "+Licai_YuE+"显示正确！");
	     }
	  catch(Exception e)
	     {
		     print("理财余额： "+Licai_YuE+"显示不正确！");
	     }
	  Float Wodejijin_YuE=Get_Number_From_String(driver.findElementByXPath("//android.view.View[contains(@content-desc,'我的基金')]").getAttribute("name"));
	  try{
	         assert(Wodejijin_YuE>=0);
	         print("我的基金余额： "+Wodejijin_YuE+"显示正确！");
	     }
	  catch(Exception e)
	     {
		     print("我的基金余额： "+Wodejijin_YuE+"显示不正确！");
	     }
	  Float Wodeniubianxian_YuE=Get_Number_From_String(driver.findElementByXPath("//android.view.View[contains(@content-desc,'牛变现')]").getAttribute("name"));
	  try{
	         assert(Wodeniubianxian_YuE>=0);
         	 print("我的牛变现余额： "+Wodeniubianxian_YuE+"显示正确！");
	     }
	  catch(Exception e)
	     {
	   	     print("我的牛变现余额： "+Wodeniubianxian_YuE+"显示不正确！");
	     }
	  appWait(2000);
	  Goback();
	  appWait(10000);
  }
  
  @Test
  public void Zhanghu_Page_Link_Switch()
  {
	  appWait(2000);
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'账户')]").click();
	  appWait(10000);
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'石发伍')]").click();
	  try{
             new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("石发伍")));
             print("个人信息页面跳转成功！");
    	     Goback();
	         appWait(8000);
         }
	  catch(Exception e)
         {
	         print("个人信息页面跳转失败！");
         }
	  driver.findElementByAccessibilityId("我的理财券 Link").click();
	  appWait(2000);
	  try{
             new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("我的理财券")));
             print("我的理财券页面跳转成功！");
	         Goback();
	         appWait(8000);

         }
	  catch(Exception e)
         {
	         print("我的理财券页面跳转失败！");
         }
	  driver.findElementByAccessibilityId("我的红包 Link").click();
      try{
             new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("途牛金服")));
             print("我的红包页面跳转成功！");
    	     Goback();
	         appWait(8000);
         }
      catch(Exception e)
         {
	         print("我的红包页面跳转失败！");
         }
      driver.findElementByAccessibilityId("我的银行卡 Link").click();
	  try{
             new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("工商银行储蓄卡")));
             print("银行卡页面跳转成功！");
	         appWait(2000);
	         driver.findElementByAccessibilityId("工商银行储蓄卡").click();
	      try{
	                new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("解除绑定 Link")));
	                print("第一张银行卡页面跳转成功！");
	    	        Goback();
		            appWait(2000);
		            Goback();
		            appWait(8000);
	         }
	      catch(Exception e)
	         {
		            print("第一张银行卡页面跳转失败！");
	         }
         }
	  catch(Exception e)
         {
	         print("银行卡页面跳转失败！");
         }
      driver.findElementByXPath("//android.view.View[contains(@content-desc,'途牛宝')]").click();
      try{
             new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("转出 Link")));
             print("途牛宝页面跳转成功！");
    	     Goback();
	         appWait(8000);
         }
      catch(Exception e)
         {
	         print("途牛宝页面跳转失败！");
         }
      driver.findElementByXPath("//android.view.View[contains(@content-desc,'理财')]").click();
      try{
             new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("途牛金服")));
             print("理财页面跳转成功！");
	         appWait(2000);
	         try{
	                new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("持有中")));
	                print("我的定期理财产品页面跳转成功！");
	    	        Goback();
		            appWait(8000);
	            }
	         catch(Exception e)
	            {
		            print("我的定期理财产品跳转失败！");
	            }
	          driver.findElementByXPath("//android.view.View[contains(@content-desc,'我的基金')]").click();
	          appWait(5000);
	          try{
	                new WebDriverWait(driver,30).until(ExpectedConditions.presenceOfElementLocated(By.name("近期申购")));
	                print("我的基金理财产品页面跳转成功！");
	                appWait(2000);
	                List <AndroidElement> el=driver.findElementsByXPath("//android.view.View[contains(@index,'0')]");
	                Float dangqianchiyou=Get_Number_From_String(el.get(1).getAttribute("name"));
	                try{
	                     assert(dangqianchiyou>0);
	                     print("我的基金当前持金额为"+dangqianchiyou+"显示正确！");
	                   }
	                catch(Exception e)
	                   {
	        	         print("我的基金当前持金额为"+dangqianchiyou+"显示不正确！");
	                   }
	                List <AndroidElement> ell=driver.findElementsByXPath("//android.view.View[contains(@index,'2')]");
	                Float leijishouyi=Get_Number_From_String(ell.get(0).getAttribute("name"));
	                try{
	                     assert(leijishouyi>=0);
	                     print("我的基金累计收益为"+leijishouyi+"显示正确！");
	                   }
	                catch(Exception e)
	                   {
	        	         print("我的基金累计收益为"+leijishouyi+"显示不正确！");
	                   }
	              }
	          catch(Exception e)
	              {
		            print("我的基金理财产品跳转失败！");
	               }
	           Goback();
	           appWait(8000);
         }
      catch(Exception e)
         {
	           print("理财页面跳转失败！");
         }
      driver.findElementByXPath("//android.view.View[contains(@content-desc,'牛变现')]").click();
      try{
               new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("持有中")));
               print("我的牛变现页面跳转成功！");
	           appWait(2000);
	           driver.findElementByXPath("//android.view.View[contains(@content-desc,'牛变现')]").click();
	           try{
	                new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("《收益权转让协议》 Link")));
	                print("我的牛变现产品页面跳转成功！");
	                Goback();
	                appWait(2000);
	              }
	           catch(Exception e)
	              {
		            print("我的牛变现产品页面跳转失败！");
		            Goback();
	                appWait(2000);
	              }
	           Goback();
	           appWait(8000);
          }
      catch(Exception e)
          {
	            print("我的牛变现页面跳转失败！");
          }
	  Goback();
	  appWait(10000);
  }
  
  @Test
  public void tuniubao_order()
  {
	  appWait(5000);
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'途牛宝')]").click();
	  try{
                new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("昨日收益(元)")));
                print("途牛宝页面跳转成功！");
                String qirinianhua=driver.findElementByXPath("//android.view.View[contains(@content-desc,'最近七日年化收益率(%)')]/parent::*/android.view.View[contains(@index,'1')]").getAttribute("name");
                Float qiribianhua_num=Get_Number_From_String(qirinianhua);
                try{
        	        assert(qiribianhua_num>0);
                    print("七日年化收益率为 "+qiribianhua_num+"显示正确！");
                   }
                catch(Exception e)
                   {
        	        print("七日年化收益率为 "+qiribianhua_num+"显示不正确！");
                   }
                String wanfenshouyi=driver.findElementByXPath("//android.view.View[contains(@content-desc,'万份收益(元)')]/parent::*/android.view.View[contains(@index,'1')]").getAttribute("name");
                Float wanfenshouyi_num=Get_Number_From_String(wanfenshouyi);
                try{
        	        assert(wanfenshouyi_num>0);
                    print("万份收益为 "+wanfenshouyi_num+"显示正确！");
                   }
                catch(Exception e)
                   {
        	         print("万份收益为 "+wanfenshouyi_num+"显示不正确！");
                   }
 	      }
	  catch(Exception e)
	      {
	      	   print("途牛宝页面跳转失败！");
	      }
	  driver.findElementByAccessibilityId("转出 Link").click();
	  try{
               new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("普通转出")));
               print("途牛宝转出页面跳转成功！");
               Goback();
               appWait(2000);
	     }
	  catch(Exception e)
	     {
	 	       print("途牛宝转出页面跳转失败！");
	     }
	  driver.findElementByAccessibilityId("转入 Link").click();
	  try{
               new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("确认转入")));
               print("途牛宝转入页面跳转成功！");
               Goback();
               appWait(2000);
	     }
	  catch(Exception e)
	     {
	 	       print("途牛宝转入页面跳转失败！");
	     }
	  Goback();
      appWait(8000);  
  }

  @Test
  public void jijinlicai_order()
  {
	  MoveDown();
	  appWait(5000);
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'基金')]").click();
	  appWait(5000);
	  driver.findElementByAccessibilityId("定期宝期限灵活 低风险 Link").click();
	  appWait(5000);
	  try{
              new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("定期宝")));
              print("热门基金页面-定期宝板块跳转成功！");
	          appWait(2000);
	          Goback();
	     }
	  catch(Exception e)
	     {
		      e.printStackTrace();
		      print("热门基金页面-定期宝板块跳转失败！");
		      appWait(2000);
	          Goback();
	     }
	  appWait(8000);
	  driver.findElementsByXPath("//android.view.View[contains(@content-desc,'近一年涨跌幅')]").get(0).click();
	  try{
	          appWait(4000);
		      if(IfElementNotExist("买入 Link")){
			       print("第一个基金产品无法购买！");
			       Goback();
		      }else if (! IfElementNotExist("买入 Link"))
		      {
		    	   driver.findElementByAccessibilityId("买入 Link").click();
	               try{
	        	            new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("购买 Link")));
	                        print("基金产品下单页面跳转成功！");
	                        Goback();
	                        appWait(2000);
	                  }
	               catch(Exception e)
	                  {
	        	             print("基金产品下单页面跳转失败！");
	                  }
		       }
             Goback();
             appWait(2000);
	      }
	  catch(Exception e)
	      {
	  	     print("基金产品详情页面跳转失败！");
	      }
      Goback();
      appWait(8000);
  }
  
  @Test
  public void niubianxian_order()
  {
	  appWait(2000);
	  driver.findElementByXPath("//android.view.View[contains(@content-desc,'牛变现')]").click();
	  appWait(5000);
	  driver.findElementsByAccessibilityId("理财期限").get(0).click();
	  try{
             new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("预期年化")));
             print("牛变现产品详情页面跳转成功！");
             appWait(2000);
	     }
	  catch(Exception e)
	     {
	 	     print("牛变现产品详情页面跳转失败！");
	     }
	  if(IfElementNotExist("马上购买 Link"))
	     {
		     print("所有牛变现产品均已募集成功！");
		     Goback();
	         appWait(2000);
	     }else
	     {
		     driver.findElementByAccessibilityId("马上购买 Link").click();
		     try{
	               new WebDriverWait(driver,10).until(ExpectedConditions.presenceOfElementLocated(By.name("确认购买 Link")));
	               print("牛变现下单页面跳转成功！");
	               Goback();
		           appWait(2000);
		        }
		     catch(Exception e)
		     {
		 	       print("牛变现下单页面跳转失败！");
		     }
		     Goback();
	         appWait(2000);
	  }
	  Goback();
      appWait(8000);
  }
  
  @AfterTest
  public void afterTest() 
  {
	  driver.quit();	  
	  
	  //获取Total,Pass,Failed,PassRate
	  
	  java.text.DecimalFormat  df  =new  java.text.DecimalFormat("0.00"); 
	  
	  int pass=PrepareWork.passed_list.size(); 
	  String pass_string=pass+"";
	  int fail=PrepareWork.failed_list.size(); 
	  String fail_string=fail+"";
	  int total=pass+fail;
	  String total_string=total+"";
	  String passrate=df.format(((float)pass/total)*100);
	  
	  String email_content=PrepareWork.email_content;
	  String str="";
	  String report_name="途牛旅游APP入口自动化界面测试报告";                                                                    //简洁模板标题
	  String testcase_name="用例名称";                                                     
	  String status="执行状态";
	  
	  //替换模板内容
	  
	  str=String.format(email_content,report_name,total_string,pass_string,fail_string,passrate,testcase_name,status);
	   
	  //生成另一个邮件模板
	  
	  String another_file=PrepareWork.folder_path+"/"+"report_nomal.html";
	  File report=new File(another_file);
	  if(!report.exists())
	    {
		    try {
			       report.createNewFile();
		        } 
		    catch (IOException e) 
		        {
	  	           e.printStackTrace();
		        }
	    }
	  try{  
            Files.write((Paths.get(another_file)),str.getBytes("GBK"),StandardOpenOption.APPEND);                             //将简洁内容写入简洁模板
         } 
	  catch (IOException e)
	     {  
            e.printStackTrace();  
         }
	  
	  print("App Quit!");
  }
  
  //发送邮件
  @AfterTest
  public void email_send() throws UnsupportedEncodingException, MessagingException
  {
	  EmailRule ER=new EmailRule();
	  if (ER.Flag && PrepareWork.failed_list.size() !=0)
	  {
	       ER.send_email(PrepareWork.folder_path);
	       print("邮件发送成功！Sent Success");
	  }
	  else if (! ER.Flag)
	  {
		  print("邮件开关关闭，本次不发送邮件！Flag Turnoff");
	  }
	  else if (PrepareWork.failed_list.size() ==0)
	  {
		  ER.send_email(PrepareWork.folder_path);
		  print("所有用例执行成功，本次不发送邮件！All Success");
	  }
  }
  
  
  //**********************************************************************************************/
  /*  自定义工具方法*/
  
  //定义线程等待的方法
  
  public void appWait(long time)
  {
	  try{
		      Thread.sleep(time);
	     }
	  catch(InterruptedException e)
	     {
		      e.printStackTrace();
	     }
  }
  
  //关闭App的方法
  
  public void closeApp()
  {
	  for (int h=0;h<3;h++)
	     {
		      driver.pressKeyCode(4);
	     }
  } 
  
  //定义 一个print方法
  
  public void print(String content)
  {
	  System.out.println(content);
  }
  
  //定义一个返回页面的方法
  
  public void Goback()
  {
	  TouchAction action=new TouchAction(driver);
      action.press(10,120).release().perform();
  }
  
  //定义一个元素不存在的方法
  
  public boolean IfElementNotExist(String name)
  {
	  try
	  {
		  driver.findElementByAccessibilityId(name);
		  return false;
	  }catch(Exception e)
	  {
		  return true;
	  }
  }
  
  //定义一个手势滑动（上滑）
  
  public void MoveUp()
  {
	  TouchAction action=new TouchAction(driver);
	  action.press(400,500).moveTo(0,-300).release().perform();
	  appWait(5000);
  }
  
  //定义一个手势滑动（下滑）
  
  public void MoveDown()
  {
	  TouchAction action=new TouchAction(driver);
	  action.press(400,500).moveTo(0,300).release().perform();
      appWait(5000);
  }
  
  //定义一个从字符串中提取数字的方法（正则表达式）
  
  public float Get_Number_From_String(String content)
  {
	  String regEx="[^0-9.0-9]";
	  Pattern p=Pattern.compile(regEx);
	  Matcher m=p.matcher(content);
	  Float Num_out=Float.parseFloat(m.replaceAll("").trim());
	  return Num_out;
  }
}
