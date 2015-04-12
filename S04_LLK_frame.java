package homework4;
import java.awt.*;
import java.math.*;
import java.awt.event.*;
import javax.swing.*;
class Spot{
	public int x,y;
	public Spot() {}
	public Spot(int a,int b){
		x=a;y=b;
	}
}
class Match{
	private int [][]map=new int[32][32];
	private int []cnt=new int[21];
	private int nrow,ncol,nkind;
	private int nres,nhint;
	private Spot marked;
	public void Print(){//Just for Debugging
		System.out.println("nrow="+nrow+" ncol="+ncol+" nkind="+nkind+" nrestart="+nres+" nhint="+nhint);
		System.out.println("The Count Array:");
		for (int i=1;i<=nkind;i++) System.out.print(cnt[i]+'\t');
		System.out.println("\nThe Map Matrix:");
		for (int i=1;i<=nrow;i++){
			for (int j=1;j<=ncol;j++) System.out.print(map[i][j]+"\t");
			System.out.println("");
		}
	}
	public void Reshuffle(){
		int []tmp=new int[21];
		int i,j,x;
		for (i=1;i<=nkind;i++) tmp[i]=cnt[i];
		for (i=1;i<=nrow;i++)
			for (j=1;j<=ncol;j++){
				do{
					x=(int)(nkind*Math.random())+1;
				}while (tmp[x]==0);
				tmp[x]--;
				map[i][j]=x;
			}
		//Print();
	}
	public Match(){
		nrow=ncol=nkind=20;
		nres=2;nhint=5;
		int i;
		for (i=1;i<=nkind;i++) cnt[i]=nrow*ncol/nkind;
		Reshuffle();
	}
	public void Display(){//Repaint
	}
	public boolean isSame(Spot x,Spot y){
		return (map[x.x][x.y]==map[y.x][y.y]);
	}
	public boolean isConnected(Spot x,Spot y){
		return false;
	}
	public void FindOnePair(Spot x,Spot y){
	}
	public void Clickon(Spot x){//Mouse Clicking on the spot x
		if(marked==null) marked=x;
		else if(!isSame(marked,x)) marked=x;
		else if(!isConnected(marked,x)) marked=x;
		else{
			FindOnePair(marked,x);
			marked=null;
		}
	}
	public void GiveHint(){
	}
}
public class S04_LLK_frame extends JFrame{
	private Match match=new Match();
	private Box admin_v= Box.createVerticalBox();
	private JPanel admin_s0 =new JPanel();
	private JPanel admin_e0 =new JPanel();
	private JButton admin_start =new JButton("START");
	private JButton admin_exit =new JButton("EXIT");
	private JTextField admin_t = new JTextField("连 连 看");
	private JTextField admin_name = new JTextField("name*4");
	public S04_LLK_frame(){
		setSize(1000,700);
		setTitle("Lian Lian Kan");
		setVisible(true);
		admin_start.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		admin_exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		admin_t.setFont(new Font("楷体",50,100)); //title
		admin_t.setBorder(null);
		admin_t.setEditable(false);
		admin_t.setHorizontalAlignment(JTextField.CENTER);

		admin_name.setFont(new Font("楷体",50,20)); //title
		admin_name.setBorder(null);
		admin_name.setEditable(false);
		admin_name.setHorizontalAlignment(JTextField.CENTER);	
		
		admin_s0.add(admin_start);
		admin_e0.add(admin_exit);
		
		admin_v.add(admin_t);
		admin_v.add(admin_s0);
		admin_v.add(admin_e0);
		admin_v.add(admin_name);
		
		getContentPane().setLayout(new BorderLayout(5,5));
		getContentPane().add(admin_v, BorderLayout.CENTER);
	}
	public static void main(String []args){
		S04_LLK_frame gui=new S04_LLK_frame();
		gui.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);//Quit the application
			}
		});
	}
}
