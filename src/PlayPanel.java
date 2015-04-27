import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class PlayPanel extends Panel{
	private Panel mapArea = new Panel();
	private JButton[][] map = new JButton[14][14];
	private Box opArea = Box.createVerticalBox();
	private JTextArea counter=new JTextArea(1,10);
	private JButton hintButton = new JButton("Hint");
	private JButton menuButton = new JButton("Return to Main Menu");
	private JButton exitButton = new JButton("Exit");
	private Point base = new Point(100, 25);
	private final int width = 50, height = 50;
	private Match match=new Match(12);
	private final String dir = "LLK";
	
	public void Display(int sec){
		counter.setText("Seconds: "+sec);
	}
	
	public PlayPanel(){
		this.setLayout(new BorderLayout());
		mapArea.setLayout(null);
		String[] files = new File(dir).list();
		for (int i=1;i<=12;i++) System.out.println(files[i]);
		for(int i = 1; i <= 12; ++i)
			for(int j = 1; j <= 12; ++j){
				map[i][j] = new JButton(new ImageIcon(dir + "\\" + files[match.getMap(i,j)]));
				map[i][j].setBounds(i * height + base.x, j * width + base.y, height, width);
				mapArea.add(map[i][j]);
			}
		counter.setLineWrap(true);
		counter.setEditable(false);
		opArea.add(Box.createVerticalStrut(240));
		opArea.add(counter);
		opArea.add(Box.createVerticalStrut(240));
		opArea.add(hintButton);
		opArea.add(Box.createVerticalStrut(10));
		opArea.add(menuButton);
		opArea.add(Box.createVerticalStrut(10));
		opArea.add(exitButton);
		this.add(mapArea, BorderLayout.CENTER);
		this.add(opArea, BorderLayout.EAST);
		this.setVisible(true);
		for(int i = 1; i <= 12; ++i)
			for(int j = 1; j <= 12; ++j)
			{
				JButton cur = map[i][j];
				cur.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						Point tar = cur.getLocation();
						Spot cur_spot = new Spot((tar.x - base.x) / height , (tar.y - base.y) / width);
						System.out.println("Mouse Clicked on "+cur_spot.x+" "+cur_spot.y);
						Spot marked = match.getMarked();
						if(marked == null)
							cur.setEnabled(false);
						Solution tmp = match.Clickon(cur_spot);
						if(tmp == null){
							if(marked != null) map[marked.x][marked.y].setEnabled(true);
							cur.setEnabled(false);
						}
						else{
							map[marked.x][marked.y].setVisible(false);
							cur.setVisible(false);
							//map[tmp.start.x][tmp.start.y].setVisible(false);
							//map[tmp.end.x][tmp.end.y].setVisible(false);
						}
						/*else if(match.isSame(cur_spot, marked)){}
						else if(match.isConnected(marked,cur_spot) == null){
							map[marked.x][marked.y].setEnabled(true);
							cur.setEnabled(false);
						}
						else{
							map[marked.x][marked.y].setVisible(false);
							cur.setVisible(false);
						}*/
						//match.Clickon(cur_spot);
					}
				});
			}
		Timer timer=new Timer();
		timer.schedule(new TimerTask(){
			int x=10;
			public void run(){
				Display(x);
				x--;
				if(x==0) JOptionPane.showMessageDialog(null,"Time out!! You have failed...","Oops!",JOptionPane.ERROR_MESSAGE);
			}
		},1000,1000);
	}
}