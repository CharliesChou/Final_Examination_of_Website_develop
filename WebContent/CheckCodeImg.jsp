<%@ page contentType="image/jpeg;charset=UTF-8" language="java" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%! 
	//生成随机颜色
	 public Color getColor()
	 {
		Random ran=new Random();
		int R=ran.nextInt(255);
		int G=ran.nextInt(255);
		int B=ran.nextInt(255);
		return new Color(R,G,B);
	 }
	//生成随4位机数字验证码
	public String codeGenerate()
	{
		Random ran=new Random();
		String code="123456789QWERTYUPLKJHGFDSAXCVBNM";
		String encode="";
		for(int i=0;i<4;i++)
		{
			int index=ran.nextInt(code.length());
			encode+=code.charAt(index);
		}
		//int code=(int)(Math.random()*9000+1000);
		return encode;
	}
%>

<%
	//禁止缓存，防止验证码过期
	response.setHeader("Pragma","no-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Expires","0");
	//实例化一个image长度和宽度分别是80 30
	BufferedImage image=new BufferedImage(80,30,BufferedImage.TYPE_INT_RGB);
	//取画笔，准备画图
	Graphics pen=image.getGraphics();
	//填充该矩形image
	pen.fillRect(0, 0, 88, 30);
	//设置文字样式
	pen.setFont(new Font("seif",Font.BOLD,18));
	//设置画笔颜色
	pen.setColor(Color.BLACK);
	//获取随机数验证码值
    String checkCode = codeGenerate() ;
	//处理该验证码值的每一位数字
    StringBuffer sb = new StringBuffer() ;
     for(int i=0;i<checkCode.length();i++){
         sb.append(checkCode.charAt(i)+" "  )  ;
     }
    //绘制验证码
     pen.drawString( sb.toString(), 8,22 );
    //绘制干扰线条
     for(int i=0;i<15;i++)
    {
        Random ran = new Random() ;
        //产生随机起始位置
        int xBegin = ran.nextInt(80) ;//55
        int yBegin = ran.nextInt(30) ;
		//干扰线条末端位置
        int xEnd = ran.nextInt(xBegin +15 ) ;
        int yEnd = ran.nextInt(yBegin + 15) ;
		//设置随机线条颜色
        pen.setColor( getColor());
        pen.drawLine(xBegin,yBegin,xEnd,yEnd);
    }
    //将验证码真实值 保存在session中，供使用时比较真实性
    session.setAttribute("CKECKCODE"  ,checkCode );
    //真实的产生图片
    ImageIO.write(image,"jpeg",  response.getOutputStream()) ;
    //关闭
    out.clear();
    out = pageContext.pushBody() ;
%>
