import java.awt.*;
import java.awt.event.*;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.Button;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;






import javax.swing.JTextArea;
import javax.swing.JTextField;


public class LLK extends JFrame{
	private Box admin_v= Box.createVerticalBox();
	//private JPanel admin_s0 =new JPanel();
	private JPanel admin_easy0 =new JPanel();
	private JPanel admin_medium0 =new JPanel();
	private JPanel admin_hard0 =new JPanel();
	private JPanel admin_e0 =new JPanel();
	private JButton admin_easy =new JButton("EASY");
	private JButton admin_medium =new JButton("MEDIUM");
	private JButton admin_hard =new JButton("HARD");
	//private JButton admin_start =new JButton("START");
	private JButton admin_exit =new JButton("EXIT");
	private JTextField admin_t = new JTextField("Picture Matching~!");
	private JTextField admin_name = new JTextField("by   JACK5   ^w^");

	public LLK(){
		setSize(1250,900);
		setTitle("Picture Matching~!");
		setVisible(true);
		admin_easy.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			    getContentPane().remove(admin_v);
			    ((JPanel) getContentPane()).setOpaque(false); 
			    ImageIcon backi = new ImageIcon(".//Image//background//background3.jpg");
			    backi.setImage(backi.getImage().getScaledInstance(1250,900,Image.SCALE_DEFAULT));
				JLabel backlabel = new JLabel(backi);  //背景图片
				getLayeredPane().add(backlabel,  new Integer(Integer.MIN_VALUE));
				
				backlabel.setBounds(0,0,backi.getIconWidth(), backi.getIconHeight());
			    PlayPanel playpanel = new PlayPanel(10);
			    getContentPane().add(playpanel, BorderLayout.CENTER);
			    
			    setVisible(true);
			    repaint();
			}
		});
		admin_medium.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			    getContentPane().remove(admin_v);
			    ((JPanel) getContentPane()).setOpaque(false); 
			    
			    ImageIcon backi = new ImageIcon(".//Image//background//background3.jpg");
			    backi.setImage(backi.getImage().getScaledInstance(1250,900,Image.SCALE_DEFAULT));
				JLabel backlabel = new JLabel(backi);  //背景图片
				getLayeredPane().add(backlabel,  new Integer(Integer.MIN_VALUE));
				
				backlabel.setBounds(0,0,backi.getIconWidth(), backi.getIconHeight());
			    PlayPanel playpanel = new PlayPanel(12);
			    getContentPane().add(playpanel, BorderLayout.CENTER);
			    setVisible(true);
			    repaint();
			}
		});
		admin_hard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			    getContentPane().remove(admin_v);
			    ((JPanel) getContentPane()).setOpaque(false); 
			    
			    ImageIcon backi = new ImageIcon(".//Image//background//background3.jpg");
			    backi.setImage(backi.getImage().getScaledInstance(1250,900,Image.SCALE_DEFAULT));
				JLabel backlabel = new JLabel(backi);  //背景图片
				getLayeredPane().add(backlabel,  new Integer(Integer.MIN_VALUE));
				
				backlabel.setBounds(0,0,backi.getIconWidth(), backi.getIconHeight());
			    PlayPanel playpanel = new PlayPanel(14);
			    getContentPane().add(playpanel, BorderLayout.CENTER);
			    setVisible(true);
			    repaint();
			}
		});
		admin_exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		
		
		admin_t.setFont(new Font("Buxton Sketch",50,100)); //title
		admin_t.setBorder(null);
		admin_t.setEditable(false);
		admin_t.setHorizontalAlignment(JTextField.CENTER);

		admin_name.setFont(new Font("Buxton Sketch",50,20)); //title
		admin_name.setBorder(null);
		admin_name.setEditable(false);
		admin_name.setHorizontalAlignment(JTextField.CENTER);	
		/*admin_start.setBackground(null);
		admin_start.setContentAreaFilled(false);
		admin_start.setSize(150, 75);
	    admin_start.setFont(new Font("Buxton Sketch", 50, 50));
		admin_s0.add(admin_start);*/
		
		admin_easy.setBackground(null);
		admin_easy.setContentAreaFilled(false);
		admin_easy.setSize(150, 75);
		admin_easy.setFont(new Font("Buxton Sketch", 50, 50));
		admin_easy0.add(admin_easy);
		
		admin_medium.setBackground(null);
		admin_medium.setContentAreaFilled(false);
		admin_medium.setSize(150, 75);
		admin_medium.setFont(new Font("Buxton Sketch", 50, 50));
		admin_medium0.add(admin_medium);
		
		admin_hard.setBackground(null);
		admin_hard.setContentAreaFilled(false);
		admin_hard.setSize(150, 75);
		admin_hard.setFont(new Font("Buxton Sketch", 50, 50));
		admin_hard0.add(admin_hard);
		
		admin_exit.setBackground(null);
		admin_exit.setContentAreaFilled(false);
		admin_exit.setSize(150, 75);
		admin_exit.setFont(new Font("Buxton Sketch", 50, 50));
		admin_e0.add(admin_exit);
	
		admin_v.add(admin_t);
		admin_v.setBackground(null);
		admin_v.setOpaque(false);	
		admin_easy0.setOpaque(false);		
		admin_hard0.setOpaque(false);	
		admin_medium0.setOpaque(false);
		admin_e0.setOpaque(false);
		admin_t.setLayout(null);
		admin_t.setOpaque(false);
		admin_name.setLayout(null);
		admin_name.setOpaque(false);	
		
		admin_v.add(admin_easy0);
		admin_v.add(admin_medium0);
		admin_v.add(admin_hard0);
		admin_v.add(admin_e0);
		admin_v.add(admin_name);
				
		((JPanel) getContentPane()).setOpaque(false); 	    
	    ImageIcon backi = new ImageIcon(".//Image//background//background3.jpg");
	    backi.setImage(backi.getImage().getScaledInstance(1250,900,Image.SCALE_DEFAULT));
		JLabel backlabel = new JLabel(backi);  //背景图片
		backlabel.setBounds(0,0,backi.getIconWidth(), backi.getIconHeight());
		getLayeredPane().add(backlabel, new Integer(Integer.MIN_VALUE));//将背景标签添加到jfram的LayeredPane面板里。      
		getContentPane().setLayout(new BorderLayout(5,5));
		getContentPane().add(admin_v, BorderLayout.CENTER);
		setVisible(true);
	    
	}
	
	public static void main(String []args){
		LLK gui=new LLK();
		gui.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);//Quit the application
			}
		});
	}
}
