import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class S04_LLK_frame extends JFrame{
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
			    getContentPane().remove(admin_v);
			    PlayPanel playpanel = new PlayPanel();
			    getContentPane().add(playpanel, BorderLayout.CENTER);
			    setVisible(true);
				//System.out.println("1");
			    repaint();
				//System.out.println("2");
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