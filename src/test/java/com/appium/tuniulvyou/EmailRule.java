package com.appium.tuniulvyou;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/*获取指定附件并发送邮件*/

public class EmailRule{

	public  boolean Flag=true;                                                                                              //是否发送邮件的开关
	private ArrayList<String> send_to=new ArrayList<String>();                                                              //初始化发件人列表

	public void send_email(String folder_path) throws MessagingException, UnsupportedEncodingException
	{
		String send_from="jenkinsrobot@sohu.com";                                                                           //此处添加发件人
	    send_to.add("shifawu@tuniu.com");                                                                                   //此处添加收件人列表
		
		Properties props=new Properties();
		props.setProperty("mail.smtp.host","smtp.sohu.com");
		props.setProperty("mail.smtp.port","25");
		props.setProperty("mail.smtp.auth", "true");  
		props.setProperty("mail.smtp.timeout", "10000");

		Authenticator auth=new Authenticator()
		{
		   public PasswordAuthentication getPasswordAuthentication()
		   {
		        return new PasswordAuthentication("jenkinsrobot@sohu.com","shi@123456");                                    //发件邮箱账号密码
		   }
		};
		Session session=Session.getInstance(props,auth);                          
		//session.setDebug(true);                                                                                           //打开则可以显示调试信息

		try
		{
			MimeMessage message=new MimeMessage(session);
			message.setFrom(new InternetAddress(send_from));
			
			InternetAddress[] tos=new InternetAddress[send_to.size()+1];                                                    //从列表中获取并配置多个收件人
			for(int i=0;i<send_to.size();i++)
			{
				tos[i]=new InternetAddress(send_to.get(i));
				message.addRecipient(Message.RecipientType.TO,tos[i]);
			}
			
			message.setSubject("途牛旅游APP入口自动化界面测试报告");
			
			//配置图片
			MimeBodyPart attachment=get_pic(folder_path);
			MimeBodyPart body=get_html(folder_path);
			
			MimeMultipart allpart=new MimeMultipart("mixed");
			allpart.addBodyPart(attachment);
			allpart.addBodyPart(body);
			message.setContent(allpart,"text/html;charset=GBK");
			Transport.send(message);
			//System.out.println("Successed!");
			
		}
		catch(MessagingException e)
		{
			e.printStackTrace();
		}
	}
	
	/*从指定文件路径中获得"report_nomal.html文件，并转化为MimeBodyPart类型
	 * 
	 *@Param 文件路径
	 *@Return 附件
	 * 
	 */
	
	public MimeBodyPart get_html(String folder_path) throws UnsupportedEncodingException, MessagingException                 //folder_path是文件夹的路径，不包含文件名称
	{
		File file=new File(folder_path+"/"+"report_nomal.html");
		String encoding = "GBK";   
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        MimeBodyPart textbody=new MimeBodyPart();
        String  content_string=new String(filecontent,encoding);
        textbody.setContent(content_string,"text/html;charset=GBK");
        return textbody;
	} 
	
	/*从指定文件路径中获得所有jpg文件，并转化为MimeBodyPart类型
	 * 
	 *@Param 文件路径
	 *@Return 附件图片
	 * 
	 */
	
	public MimeBodyPart get_pic(String folder_path) throws MessagingException
	{
		String pic_path ="";
		
		File f=new File(folder_path);
		File[] fa=f.listFiles();                                                                                             //获取folder_path文件夹中的所有内容
		for(int i=0;i<fa.length;i++)
		{
			File fs=fa[i];
			String fs_name=fs.toString();
			if(fs_name.contains("jpg"))
			{
				pic_path=fs.getAbsolutePath();
				break;
			}
		}
		
		MimeBodyPart att=new MimeBodyPart();
		FileDataSource fds=new FileDataSource(pic_path);
		att.setDataHandler(new DataHandler(fds));
		att.setFileName("Screenshot_ERROR.jpg");
		return att;
		
	}
}
