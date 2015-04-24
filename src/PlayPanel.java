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
	private JButton[][] map = new JButton[14][14];
	private Box opArea = Box.createVerticalBox();
	private JButton hintButton = new JButton("Hint");
	private JButton menuButton = new JButton("Return to Main Menu");
	private JButton exitButton = new JButton("Exit");
	private Point base = new Point(100, 25);
	private final int width = 50, height = 50;
	private Match match=new Match(12);
	private final String dir = "F:\\Workspace for Java, 2015\\LLK\\LLK";
	
	public PlayPanel(){
		this.setLayout(new BorderLayout());
		mapArea.setLayout(null);
		String[] files = new File(dir).list();
		for(int i = 1; i <= 12; ++i)
			for(int j = 1; j <= 12; ++j){
				map[i][j] = new JButton(new ImageIcon(dir + "\\" + files[match.getMap(i,j)]));
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
		for(int i = 1; i <= 12; ++i)
			for(int j = 1; j <= 12; ++j)
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
	}
}