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
	private final int scale;														//�ɿ�ʼ��frame���������������Ѷ�
	private JPanel mapArea = new JPanel();											//������ʾ������ͼƬ������
	private JButton[][] map = new JButton[20][20];									//���������������ð�ťʵ��
	private Box opArea = Box.createVerticalBox();
	private JButton hintButton = new JButton("          Hint        ");				//���Hint��ť������ʾ��ʾ�Ľ�
	private JButton exitButton = new JButton("          Exit        ");				//���Exit��ť���˳���Ϸ
	private JButton counter = 	 new JButton();										//��ʱ��
	private JLabel succLabel;														//������ʾ��Ϸ�ɹ�ʱ��ͼƬ����
	private JLabel failLabel;														//������ʾ��Ϸʧ�ܣ���time outʱ��ͼƬ����
	private Point base = new Point(100, 15);										//��¼���Ͻǵ�һ��ͼƬ��λ�ã��Ժ���ÿ��ͼƬ�������ʱ����
	private final int width, height;												//width��height����������ÿ��ͼƬ�Ĺ����scale�Ĳ�ͬ����ͬ
	private Match match;															//ƥ����
	private final String dir = ".\\Image\\target";
	private Solution hintSolution = null;											//���ڼ�¼�û��Ƿ�֮ǰ�����hint��ť�����ǣ�hintSolution��Ϊ��ʾ�Ľ⣬������Ϊnull
	
	public void Display(int sec){													//���ĵ���ʱ��ʾ��ʱ��
		counter.setText("    Seconds: "+sec+"   ");
	}
	
	public PlayPanel(int n){
		this.setBackground(null);
		setOpaque(false);  															//��ɫ͸��
		this.setLayout(new BorderLayout());
		mapArea.setLayout(null);
		mapArea.setOpaque(false);
		
		scale = n;
		width = height = 120 - 5 * scale;
		match = new Match(scale);
		
		ImageIcon tmp1 = new ImageIcon(".\\Image\\succPicture\\succ.jpg");			//��ʼ���ɹ�ʱ��ʾ��panel
		tmp1.setImage(tmp1.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT));
		succLabel = new JLabel(tmp1);
		succLabel.setBounds(100, 200, 800, 500);
		succLabel.setVisible(false);
		this.add(succLabel);
		
		ImageIcon tmp2 = new ImageIcon(".\\Image\\failPicture\\failed.jpg");		//��ʼ��ʧ��ʱ��ʾ��panel
		tmp2.setImage(tmp2.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT));
		failLabel = new JLabel(tmp2);
		failLabel.setBounds(100, 200, 800, 500);
		failLabel.setVisible(false);
		this.add(failLabel);
		
		counter.setBackground(null);												//���õ���ʱ�Ĺ��
		counter.setContentAreaFilled(false);
		counter.setFont(new Font("Buxton Sketch", 40, 40));
		counter.setSize(150, 75);
		
		hintButton.setBackground(null);												//����Hint��ť�Ĺ��
		hintButton.setContentAreaFilled(false);
		hintButton.setSize(150, 75);
	    hintButton.setFont(new Font("Buxton Sketch", 40, 40));
	    
		exitButton.setBackground(null);												//����Exit��ť�Ĺ��
		exitButton.setContentAreaFilled(false);
		exitButton.setSize(150, 75);
		exitButton.setFont(new Font("Buxton Sketch", 40, 40));
		
		String[] files = new File(dir).list();										//filesΪ������ͼƬ���ļ��б�
		for(int i = 1; i <= scale; ++i)												//��ʼ��ͼƬ���򣬲���ʾ
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
		exitButton.addActionListener(new ActionListener(){							//����Exit��ť�ļ��������ʱ�˳���Ϸ
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		hintButton.addActionListener(new ActionListener(){							//����Hint��ť�ļ��������ʱ��ʾ���н�
			@Override
			public void actionPerformed(ActionEvent e){
				hintSolution = new Solution(match.GiveHint());						//����match��GiveHint�����ѵõ����н�
				Spot start = hintSolution.start, end = hintSolution.end;			//start��end�ֱ�Ϊ���н������λ��
				map[start.x][start.y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 10));		//��start��end�ı߽�Ӵ���ʾ������ʾ�û���Ϊ���н�
				map[end.x][end.y].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 10));
				//System.out.println("hintjjjjjjj" + start.x + " " + start.y + " " + end.x + " " + end.y); 
			}
		});
		for(int i = 1; i <= scale; ++i)												//��������ͼƬ����ͼƬ��ť�ļ���
			for(int j = 1; j <= scale; ++j)
			{
				JButton cur = map[i][j];
				cur.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						if(hintSolution != null){									//���û�֮ǰ�����Hint��ť��������֮ǰΪ����ʾ�û��ļӴ�ͼƬ�߽����ʾ������hintSolution��Ϊ��
							Spot start = hintSolution.start, end = hintSolution.end;
							map[start.x][start.y].setBorder(BorderFactory.createLineBorder(null));
							map[end.x][end.y].setBorder(BorderFactory.createLineBorder(null));
							hintSolution = null;
						}
						Point tar = cur.getLocation();
						Spot cur_spot = new Spot((tar.x - base.x) / height , (tar.y - base.y) / width);			//��õ�ǰ���ͼƬ������(�������ص�����꣬�����ڵڼ��еڼ���)
						Spot marked = match.getMarked();							//marked���ڼ�¼�û�֮ǰ���������ͼƬ
						if(marked == null)											//���ڴ�֮ǰ�û���δ���������ͼƬ���򽫵�ǰ�����ͼƬ����Ϊ���ܵ������ʾΪ��ɫ
							cur.setEnabled(false);
						Solution tmp = match.Clickon(cur_spot);						//��match���ݵ�ǰ�����ͼƬ��λ�ò�����������һ��ֵ
						if(tmp == null){											
							if(marked != null) map[marked.x][marked.y].setEnabled(true);	//������ֵΪ����֮ǰ���������ͼƬ����˵��������ͼƬ��ƥ����޷��������򽫻ָ�֮ǰ�������ͼƬ�Ŀɵ�����ܣ��ָ�ԭɫ
							cur.setEnabled(false);									//����ǰ�����ͼƬ����Ϊ���ܵ������ʾΪ��ɫ
						}
						else{														//������ֵ��Ϊ�գ���˵���û����������ͼƬƥ��
							map[marked.x][marked.y].setVisible(false);				//��������ͼƬ�İ�ť����
							cur.setVisible(false);
							if(match.isEmpty()){									//�ж��Ƿ�ʣ��ͼƬδ����������ʣ�£�����ʾ�ɹ�ʱ����ʾ��ͼƬ
								succLabel.setVisible(true);
							}
							else if(match.getReshuffle()){							//�ж�ʣ�µ�ͼƬ���Ƿ���Ҫ���ã�������������
								for(int i = 1; i <= scale; ++i)
									for(int j = 1; j <= scale; ++j){
										if(map[i][j].isEnabled()){					//���Ļ�δ���������ͼƬ
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
		java.util.Timer timer = new java.util.Timer();								//���õ���ʱ
		timer.schedule(new TimerTask(){												//����ʱ��ʱ��scale�仯���ֱ�Ϊ120s��240s��360s
			int x = (scale - 8) * 60;
			public void run(){														//ÿ����1s������¼�ʱ����ʾ
				Display(x);
				x--;
				if(x == -1){														//ʱ��ľ�����ֹͣ��ʱ���Ĺ�������������Ϊ������ͼƬ����ʾʧ�ܵ�ͼƬ
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