import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.TimerTask;
import java.awt.Font;
import javax.swing.*;

public class PlayPanel extends JPanel{
	private final int scale;														//由开始的frame穿参数，以设置难度
	private JPanel mapArea = new JPanel();											//用于显示连连看图片的区域
	private JButton[][] map = new JButton[20][20];									//连连看消除区域用按钮实现
	private Box opArea = Box.createVerticalBox();
	private JButton hintButton = new JButton("          Hint        ");				//点击Hint按钮可以显示提示的解
	private JButton exitButton = new JButton("          Exit        ");				//点击Exit按钮以退出游戏
	private JButton counter = 	 new JButton();										//计时器
	private JLabel succLabel;														//用于显示游戏成功时的图片区域
	private JLabel failLabel;														//用于显示游戏失败，即time out时的图片区域
	private Point base = new Point(100, 15);										//记录左上角第一张图片的位置，以后获得每张图片的坐标的时候用
	private final int width, height;												//width和height是消除区域每张图片的规格，由scale的不同而不同
	private Match match;															//匹配类
	private final String dir = ".\\Image\\target";
	private Solution hintSolution = null;											//用于记录用户是否之前点击过hint按钮，若是，hintSolution则为提示的解，否则置为null
	
	public void Display(int sec){													//更改倒计时显示的时间
		counter.setText("    Seconds: "+sec+"   ");
	}
	
	public PlayPanel(int n){
		this.setBackground(null);
		setOpaque(false);  															//底色透明
		this.setLayout(new BorderLayout());
		mapArea.setLayout(null);
		mapArea.setOpaque(false);
		
		scale = n;
		width = height = 120 - 5 * scale;
		match = new Match(scale);
		
		ImageIcon tmp1 = new ImageIcon(".\\Image\\succPicture\\succ.jpg");			//初始化成功时显示的panel
		tmp1.setImage(tmp1.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT));
		succLabel = new JLabel(tmp1);
		succLabel.setBounds(100, 200, 800, 500);
		succLabel.setVisible(false);
		this.add(succLabel);
		
		ImageIcon tmp2 = new ImageIcon(".\\Image\\failPicture\\failed.jpg");		//初始化失败时显示的panel
		tmp2.setImage(tmp2.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT));
		failLabel = new JLabel(tmp2);
		failLabel.setBounds(100, 200, 800, 500);
		failLabel.setVisible(false);
		this.add(failLabel);
		
		counter.setBackground(null);												//设置倒计时的规格
		counter.setContentAreaFilled(false);
		counter.setFont(new Font("Buxton Sketch", 40, 40));
		counter.setSize(150, 75);
		
		hintButton.setBackground(null);												//设置Hint按钮的规格
		hintButton.setContentAreaFilled(false);
		hintButton.setSize(150, 75);
	    hintButton.setFont(new Font("Buxton Sketch", 40, 40));
	    
		exitButton.setBackground(null);												//设置Exit按钮的规格
		exitButton.setContentAreaFilled(false);
		exitButton.setSize(150, 75);
		exitButton.setFont(new Font("Buxton Sketch", 40, 40));
		
		String[] files = new File(dir).list();										//files为连连看图片的文件列表
		for(int i = 1; i <= scale; ++i)												//初始化图片区域，并显示
			for(int j = 1; j <= scale; ++j){
				ImageIcon img=new ImageIcon(dir + "\\" + files[match.getMap(i,j)]);
				img.setImage(img.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
				map[i][j] = new JButton(img);
				map[i][j].setBounds(i * height + base.x, j * width + base.y, height, width);
				mapArea.add(map[i][j]);
			}
		
		opArea.add(Box.createVerticalStrut(400));
		opArea.add(counter);
		opArea.add(Box.createVerticalStrut(30));
		opArea.add(hintButton);
		opArea.add(Box.createVerticalStrut(30));
		opArea.add(exitButton);
		this.add(mapArea, BorderLayout.CENTER);
		this.add(opArea, BorderLayout.EAST);
		this.setVisible(true);
		exitButton.addActionListener(new ActionListener(){							//设置Exit按钮的监听，点击时退出游戏
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		hintButton.addActionListener(new ActionListener(){							//设置Hint按钮的监听，点击时显示可行解
			@Override
			public void actionPerformed(ActionEvent e){
				hintSolution = new Solution(match.GiveHint());						//调用match的GiveHint函数已得到可行解
				Spot start = hintSolution.start, end = hintSolution.end;			//start和end分别为可行解的两个位置
				map[start.x][start.y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 10));		//将start和end的边界加粗显示，以提示用户其为可行解
				map[end.x][end.y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 10));
				//System.out.println("hintjjjjjjj" + start.x + " " + start.y + " " + end.x + " " + end.y); 
			}
		});
		for(int i = 1; i <= scale; ++i)												//设置消除图片区域图片按钮的监听
			for(int j = 1; j <= scale; ++j)
			{
				JButton cur = map[i][j];
				cur.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						if(hintSolution != null){									//若用户之前点击了Hint按钮，则消除之前为了提示用户的加粗图片边界的显示，并把hintSolution置为空
							Spot start = hintSolution.start, end = hintSolution.end;
							map[start.x][start.y].setBorder(BorderFactory.createLineBorder(null));
							map[end.x][end.y].setBorder(BorderFactory.createLineBorder(null));
							hintSolution = null;
						}
						Point tar = cur.getLocation();
						Spot cur_spot = new Spot((tar.x - base.x) / height , (tar.y - base.y) / width);			//获得当前点击图片的坐标(不是像素点的坐标，而是在第几行第几列)
						Spot marked = match.getMarked();							//marked用于记录用户之前点击的其他图片
						if(marked == null)											//若在此之前用户并未点击其他的图片，则将当前点击的图片设置为不能点击，显示为灰色
							cur.setEnabled(false);
						Solution tmp = match.Clickon(cur_spot);						//给match传递当前点击的图片的位置参数，并返回一个值
						if(tmp == null){											
							if(marked != null) map[marked.x][marked.y].setEnabled(true);	//若返回值为空且之前点击过其他图片，则说明这两张图片不匹配或无法相连，则将恢复之前点击过的图片的可点击功能，恢复原色
							cur.setEnabled(false);									//将当前点击的图片设置为不能点击，显示为灰色
						}
						else{														//若返回值不为空，则说明用户点击的两张图片匹配
							map[marked.x][marked.y].setVisible(false);				//将这两张图片的按钮隐藏
							cur.setVisible(false);
							if(match.isEmpty()){									//判断是否还剩下图片未消除，若不剩下，则显示成功时需显示的图片
								succLabel.setVisible(true);
							}
							else if(match.getReshuffle()){							//判断剩下的图片中是否需要重置，若重置则如下
								for(int i = 1; i <= scale; ++i)
									for(int j = 1; j <= scale; ++j){
										if(map[i][j].isEnabled()){					//更改还未消除区域的图片
											ImageIcon img=new ImageIcon(dir + "\\" + files[match.getMap(i,j)]);
											img.setImage(img.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
											map[i][j].setIcon(img);
										}
									}
								match.resetReshuffle();
							}
						}
					}
				});
			}
		java.util.Timer timer = new java.util.Timer();								//设置倒计时
		timer.schedule(new TimerTask(){												//倒计时的时间scale变化，分别为120s、240s、360s
			int x = (scale - 8) * 60;
			public void run(){														//每经过1s，则更新计时器显示
				Display(x);
				x--;
				if(x == -1){														//时间耗尽，则停止计时器的工作，隐藏所有为消除的图片并显示失败的图片
					cancel();
					for(int i = 1; i <= scale; ++i)
						for(int j = 1; j <= scale; ++j)
							map[i][j].setVisible(false);
					failLabel.setVisible(true);
				}
			}
		},0,1000);
	}
}