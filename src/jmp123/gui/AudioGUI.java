/*
* AudioGUI.java -- 音频输出及频谱显示
* Copyright (C) 2010
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*
* If you would like to negotiate alternate licensing terms, you may do
* so by contacting the author: <http://jmp123.sf.net/>
*/
package jmp123.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import SideBar.Setting;
import jmp123.decoder.Header;
import jmp123.decoder.IAudio;
import jmp123.output.Audio;
import jmp123.output.FFT;

/**
 * 音频输出（播放），频谱显示，唱片集显示。
 */
public class AudioGUI extends JPanel implements IAudio{
	// FFT
	private float realIO[];
	private FFT fft;

	// 音频输出
	private Audio theAudio;
	
	// 频谱显示
	private static final long serialVersionUID = 1L;
	private static final int maxColums = 128;
	private static final int Y0 = 1 << ((FFT.FFT_N_LOG + 3) << 1);
	private static final double logY0 = Math.log10(Y0); //lg((8*FFT_N)^2)
	private int band;
	private final int width;
	private final int height;
	private int maxFs, histogramType=Setting.pattren;
	private int[] xplot;
	private BufferedImage spectrumBufferdImage;
	private Graphics2D spectrumGraphics;
	private Header heder;
	private final double PI=3.1415926535897932;
	private double angle;
	private boolean isEnable;
	Stroke stroke1=new BasicStroke(1.0f);
	Stroke stroke2=new BasicStroke(2.0f);
	Stroke stroke3=new BasicStroke(3.0f);
	Stroke stroke4=new BasicStroke(10.0f);
	private double widthm = 1.3;
	private double heightm = 1.3;
	private Image bgImage;
	private star[] stars= new star[8];
	private Random rand = new Random();
	/**
	 * 构造AudioGUI对象。
	 * @param sampleRate 频谱显示的截止频率。
	 * @param p 实例化本类的对象（GUI播放口器）。
	 */
	public AudioGUI(int sampleRate) {
		
		
		fft = new FFT();
		realIO = new float[FFT.FFT_N];
		width = 500;	// 频谱窗口396x140
		height =500;
		band = 128;		// 64段频谱
		isEnable = false;
		maxFs = sampleRate >> 1;
	
		xplot = new int[maxColums + 1];
		
		setLayout(null);
		this.setSize((int)(width*widthm),(int)(height*heightm));
		this.setOpaque(false);
		setPlot(width);
		
		bgImage=new ImageIcon("img/background.png").getImage();

		for(int n=0;n<stars.length;n++)
		{
			stars[n]=new star();
			stars[n].setX((int)(width*widthm));
			stars[n].setY((int)(height*heightm));
			stars[n].setAngle(rand.nextDouble()*2*PI);
			stars[n].setSpeed(rand.nextInt(10)+1);
			stars[n].setColor(new Color(stars[n].getSpeed()*15,stars[n].getSpeed()*15,stars[n].getSpeed()*15,stars[n].getSpeed()*15));
			stars[n].setSize(5);
		}
	}

	public void setHistogramType(int histogramType) {
		this.histogramType = histogramType;
	}

	/**
	 * 划分频段。
	 */
	private void setPlot(int width) {
		// fsband个频段落重新分划为band个频段，各频段宽度非线性划分。
		int fsband = FFT.FFT_N >> 1;
		if(maxFs > 20000) {
			float deltaFs = (float)maxFs / (FFT.FFT_N >> 1);
			fsband -= (maxFs - 20000) / deltaFs;
		}
		for (int i = 0; i <= band; i++) {
			xplot[i] = 0;
			xplot[i] = (int) (0.5 + Math.pow(fsband, (double) i / band));
			if (i > 0 && xplot[i] <= xplot[i - 1])
				xplot[i] = xplot[i - 1] + 1;
		}
	}

	/**
	 * 绘制"频率-幅值"直方图并显示到屏幕。
	 * @param amp amp[0..FFT_N/2-1]为频谱"幅值"(复数模的平方)。
	 */
	private void drawHistogram(float[] amp) {
		float maxAmp;
		int i = 0,y, xi, lastx=0,lasty=0,fx=0,fy=0,nowx=0,nowy=0,nowx2=0,nowy2=0,lastx2=0,lasty2=0,fx2=0,fy2=0;
		angle = 0;
		double size=0;
		
		spectrumBufferdImage = new BufferedImage((int)(width*widthm),(int)(height*heightm), BufferedImage.TYPE_INT_ARGB);
		spectrumGraphics = (Graphics2D)spectrumBufferdImage.createGraphics();
		spectrumBufferdImage = spectrumGraphics.getDeviceConfiguration().createCompatibleImage((int)(width*widthm),(int)(height*heightm), Transparency.TRANSLUCENT);
		spectrumGraphics.dispose();
		spectrumGraphics = spectrumBufferdImage.createGraphics();
		
		spectrumGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                (RenderingHints.VALUE_ANTIALIAS_ON));
		
		switch (histogramType) {
		case 0:
			spectrumGraphics.setColor(new Color(0,0,0,(int)(255*0.35)));
//			spectrumGraphics.fillOval((int)(width*widthm/2-height/1.25/2), (int)(height*heightm/2-height/1.25/2), (int)(height/1.25),(int)( height/1.25));
			spectrumGraphics.drawImage(bgImage,(int)((widthm-1)/2*width),(int)((heightm-1)/2*height), this);
			break;
		case 1:
			for(int n=0;n<stars.length;n++)
			{
				int random=0;
				if(rand.nextInt(100)>50)
					random=-1;
				else
					random=1;
				double localangle = rand.nextDouble()*2*PI;
				if(stars[n].getY()<80 || stars[n].getY()>(height*heightm-80)||
					stars[n].getX()<80 || stars[n].getX()>(width*widthm-80))
				{
					
					stars[n].setSpeed(rand.nextInt(8)+5);
					stars[n].setY((int)(height*heightm/2+50*Math.sin(localangle) ));
					stars[n].setX((int)(width*widthm/2+50*Math.cos(localangle) ));
					stars[n].setSize(5);
					stars[n].setAngle(localangle);
					
					
				}
				stars[n].setX(stars[n].getX()+(int)(Math.cos(stars[n].getAngle())*stars[n].getSpeed()));
				stars[n].setY(stars[n].getY()+(int)(Math.sin(stars[n].getAngle())*stars[n].getSpeed()));
				stars[n].setColor(new Color(255,255,255,
						255-(int)(Math.sqrt(
								Math.abs((stars[n].getX() - width*widthm/2)* (stars[n].getX() - width*widthm/2)
										+(stars[n].getY() - height*heightm/2)* (stars[n].getY() - height*heightm/2))
								)*255/width*widthm)
						
						));
				stars[n].setSize(5+(int)(Math.sqrt(
						Math.abs((stars[n].getX() - width*widthm/2)* (stars[n].getX() - width*widthm/2)
								+(stars[n].getY() - height*heightm/2)* (stars[n].getY() - height*heightm/2))
						))/25);
				stars[n].setAngle(stars[n].getAngle()+random*PI/24);
						
				spectrumGraphics.setStroke(stroke1);
				spectrumGraphics.setColor(stars[n].getColor());
				spectrumGraphics.fillOval(stars[n].getX(), stars[n].getY(),stars[n].getSize(),stars[n].getSize());
				spectrumGraphics.setColor(new Color(255,255,255,125));
				spectrumGraphics.drawOval(stars[n].getX(), stars[n].getY(),stars[n].getSize(),stars[n].getSize());
				spectrumGraphics.setColor(new Color(255,255,255,
						255-(int)(Math.sqrt(
						Math.abs((stars[n].getX() - width*widthm/2)* (stars[n].getX() - width*widthm/2)
								+(stars[n].getY() - height*heightm/2)* (stars[n].getY() - height*heightm/2))
						)*255/width*widthm)));
				spectrumGraphics.fillOval(stars[n].getX()+stars[n].getSize()/8, stars[n].getY()+stars[n].getSize()/8,stars[n].getSize()/2,stars[n].getSize()/2);
			}
		}
		for (; i != band; i++) {
			// 查找当前频段的最大"幅值"
			maxAmp = 0; xi = xplot[i]; y = xplot[i + 1];
			for (; xi < y; xi++) {
				if (amp[xi] > maxAmp)
					maxAmp = amp[xi];
			}
			
			y = (maxAmp > Y0) ? (int) ((Math.log10(maxAmp) - logY0) * 15) : 0;

			switch (histogramType) {
			
			case 0:
				spectrumGraphics.setColor(new Color(255,255,255,255));
				spectrumGraphics.setStroke(stroke1);
				
				//绘制最底层圆,频谱指向圆心
				size = 3.2;
				nowx=(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size-y/2));
				nowy=(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size-y/2));
				spectrumGraphics.drawOval(nowx, nowy, 4, 4);
				//绘制中间层
				size = 2.9;
				nowx=(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size+y/2));
				nowy=(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size+y/2));
				spectrumGraphics.setStroke(stroke2);

				//连接底层中间层的线
				spectrumGraphics.drawLine(nowx, nowy, (int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size-y/2)), 
						(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size-y/2)));
				spectrumGraphics.setStroke(stroke3);
				if(lastx!=0 && lasty !=0)
				{
					spectrumGraphics.drawLine(nowx,nowy,lastx,lasty);
				}
				lastx=nowx;
				lasty=nowy;
				if(i==0)
				{
					fx=nowx;
					fy=nowy;
				}
				
				//连接最后跟第一
				if(i==band-1)
				{
					spectrumGraphics.drawLine(nowx,nowy,fx, fy);
					
				}
				
				
				//绘制最外层
				spectrumGraphics.setColor(new Color(155,155,255));
				spectrumGraphics.setStroke(stroke3);
				size=2.5;
				nowx=(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size));
				nowy=(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size));
				
				spectrumGraphics.drawLine(nowx, nowy, 
						(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size+y/2)), 
						(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size+y/2)));
				
				
				//绘制圆层
				spectrumGraphics.setStroke(stroke1);
				size=7;
				nowx=(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size));
				nowy=(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size));
				
				spectrumGraphics.drawLine(nowx, nowy, 
						(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size+y/3)), 
						(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size+y/3)));
				
				
				if(y>0 && y<80)
				{
					if(i%5==0)
					{
						
						if(y>255)
						{
							spectrumGraphics.setColor(new Color(125,125,255,155));
						}
						else {
							spectrumGraphics.setColor(new Color(125,125,255,y));
						}
						
						spectrumGraphics.setStroke(stroke4);
						spectrumGraphics.drawOval((int)(width*widthm-   (y*3+height/8)  )/2,
								(int)(height*heightm-   (y*3+height/8)  )/2,
								(int) (y*3+height/8), 
								(int)(y*3+height/8)  );
					}
					
				}
				
				
				
				//角度增加
				angle+=PI*2/band;

				break;
				
				case 1:
					
					spectrumGraphics.setStroke(stroke1);
					size=7;
					nowx=(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size));
					nowy=(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size));
					

				if (y > 0 && y<80)
					{
						if(i%5==0)
						{
							
							spectrumGraphics.setColor(new Color(255,255,255,(int)(y*1.5)));
							
							spectrumGraphics.setStroke(stroke4);
							spectrumGraphics.drawOval((int)(width*widthm-   (y*4+height/8)  )/2,
									(int)(height*heightm-   (y*4+height/8)  )/2,
									(int) (y*4+height/8), 
									(int)(y*4+height/8)  );
						}
						
					}
					

					break;
				case 2:
					
						spectrumGraphics.setStroke(stroke3);
						
						size = 2.9;
						nowx=(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size+y/2));
						nowy=(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size+y/2));	
						spectrumGraphics.setColor(new Color(0,0,0,255));
						spectrumGraphics.drawLine(nowx, nowy, (int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size-y/2)), 
								(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size-y/2)));
						spectrumGraphics.setColor(new Color(255,255,255,255));
						if(lastx!=0 && lasty !=0)
						{
							spectrumGraphics.drawLine(nowx,nowy,lastx,lasty);
						}
						lastx=nowx;
						lasty=nowy;
						if(i==0)
						{
							fx=nowx;
							fy=nowy;
						}
						
						//连接最后跟第一
						if(i==band-1)
						{
							spectrumGraphics.drawLine(nowx,nowy,fx, fy);
							
						}
						
						size=2.9;
						
						nowx2=(int)(width*widthm)/2+(int)(Math.sin(angle)*(height/size-y/2));
						nowy2=(int)(height*heightm)/2-(int)(Math.cos(angle)*(height/size-y/2));
						if(lastx2!=0 && lasty2 !=0)
						{
							spectrumGraphics.drawLine(nowx2,nowy2,lastx2,lasty2);
						}
						lastx2=nowx2;
						lasty2=nowy2;
						if(i==0)
						{
							fx2=nowx2;
							fy2=nowy2;
						}
						
						//连接最后跟第一
						if(i==band-1)
						{
							spectrumGraphics.drawLine(nowx2,nowy2,fx2, fy2);
							
						}
						
						//角度增加
						angle+=PI*2/band;
						
					break;
			}

		}
		
		switch (histogramType) {
		case 0:
			spectrumGraphics.setColor(new Color(255,255,255,155));
			spectrumGraphics.drawOval((int)(width*widthm-height/5)/2, (int)(height*heightm-height/5)/2, height/5, height/5);
			break;
		}

		repaint();
	}

	public int getWindowwh(int t) {
		if(t==1)
			return (int)(width*widthm);
		else
			return (int)(height*heightm);
	}
	//-------------------------------------------------------------------------
	// 实现IAudio接口方法

	@Override
	public boolean open(Header h, String artist) {
		this.heder = h;
		theAudio = new Audio();
		return theAudio.open(h, null);
	}
	
	@Override
	
	public int write(byte[] b, int size) {
		
		int len;
		//1. 音频输出
		if((len = theAudio.write(b, size)) == 0)
			return 0;
		if (isEnable) {
			int i, j;

			//2. 获取PCM数据。如果是双声道，转换为单声道
			if (heder.getChannels() == 2) {
				for (i = 0; i < FFT.FFT_N; i++) {
					j = i << 2;
					// (左声道 + 右声道) / 2
					realIO[i] = (((b[j + 1] << 8) | (b[j] & 0xff))
							+ (b[j + 3] << 8) | (b[j + 2] & 0xff)) >> 1;
				}
			} else {
				for (i = 0; i < 512; i++) {
					j = i << 1;
					realIO[i] = ((b[j + 1] << 8) | (b[j] & 0xff));
				}
			}

			//3. PCM变换到频域并取回模
			fft.getModulus(realIO);

			//4. 绘制
			drawHistogram(realIO);
		}

		return len;
	}
	
	public void start(boolean b) {
		theAudio.start(b);
	}

	@Override
	public void drain() {
		theAudio.drain();
	}

	@Override
	public void close() {
		if(theAudio != null)
			theAudio.close();
		
		setPlot(this.width);

	}
	
	//-------------------------------------------------------------------------
	// 重载父类的2个方法
	
	/**
	 * 根据参数 b 的值显示或隐藏频谱组件。隐藏频谱组件后自动停止对PCM数据的FFT运算并停止获取音频PCM数据。
	 * @param b 如果为 true，则显示频谱组件；否则隐藏频谱组件。
	 */
	public void setVisible(boolean b) {
		
		super.setVisible(b);
		this.isEnable = b;
	}
	
	/**
	 * 绘制频谱组件。
	 * @param g 用于绘制的图像上下文。
	 */

	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.drawImage(spectrumBufferdImage,0,0, null);
		

	}
	
	@Override
	public void refreshMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

}
