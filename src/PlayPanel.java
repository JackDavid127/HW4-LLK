import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class PlayPanel extends Panel{
	private Panel mapArea = new Panel();
	private JButton[][] map = new JButton[12][12];
	private Box opArea = Box.createVerticalBox();
	private JButton hintButton = new JButton("Hint");
	private JButton menuButton = new JButton("Return to Main Menu");
	private JButton exitButton = new JButton("Exit");
	private Point base = new Point(100, 25);
	private final int width = 50, height = 50;
	private Match match=new Match();
	private final String dir = "F:\\JAVA\\workspace\\S04_LLK\\LLK";
	
	public PlayPanel(){
		this.setLayout(new BorderLayout());
		mapArea.setLayout(null);
		String[] files = new File(dir).list();
		for(int i = 0, counter = 0; i < 12; ++i)
			for(int j = 0; j < 12; ++j){
				map[i][j] = new JButton(new ImageIcon(dir + "\\" + files[(counter++) % files.length]));
				map[i][j].setBounds(i * height + base.x, j * width + base.y, height, width);
				mapArea.add(map[i][j]);
			}
		opArea.add(Box.createVerticalStrut(500));
		opArea.add(hintButton);
		opArea.add(Box.createVerticalStrut(10));
		opArea.add(menuButton);
		opArea.add(Box.createVerticalStrut(10));
		opArea.add(exitButton);
		this.add(mapArea, BorderLayout.CENTER);
		this.add(opArea, BorderLayout.EAST);
		this.setVisible(true);
		for(int i = 0; i < 12; ++i)
			for(int j = 0; j < 12; ++j)
			{
				JButton cur = map[i][j];
				cur.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseClicked(MouseEvent e) {
						Point tar = cur.getLocation();
						Spot cur_spot = new Spot((tar.x - base.x) / height , (tar.y - base.y) / width);
						Spot marked = match.getMarked();
						if(marked == null)
							cur.setEnabled(false);
						else if(match.isSame(cur_spot, marked)){}
						else if(!match.isConnected(marked,cur_spot)){
							map[marked.x][marked.y].setEnabled(true);
							cur.setEnabled(false);
						}
						else{
							map[marked.x][marked.y].setVisible(false);
							cur.setVisible(false);
						}
						match.Clickon(cur_spot);
					}
				});
			}
	}
}
