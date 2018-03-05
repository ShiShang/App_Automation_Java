package com.appium.tuniulvyou;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

/*测试用例失败时的即时截图并保存
 * @Param 图片保存路径
 * @Param driver 句柄
*/

public class ScreenshotTestRule {

	//重载
	/*指定文件路径时保存在相应的路径下*/
	
    public static void takeScreenshot(String screenPath, AndroidDriver<AndroidElement> driver) 
    {
        try {
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File(screenPath));
            }
        catch (IOException e)
            {
                System.out.println("Screenshot path:  "+ screenPath);
                Reporter.log("该错误可以查看截图："+screenPath);
            }
    }

    /*未指定保存路径时，保存在Result路径下*/
    
    public static void takeScreenshot(AndroidDriver<AndroidElement> driver) 
    {
         String screenName=System.currentTimeMillis()+".jpg";
         String screenPath = PrepareWork.folder_path + "/" + screenName;
         takeScreenshot(screenPath, driver);
         System.out.println("Error!!! Screenshot path:  "+ screenPath);
    }
    
    public static void main(String[] args)
    {
         File dir2=new File("results");
    	 System.out.println(dir2.getAbsoluteFile());
    }

}
