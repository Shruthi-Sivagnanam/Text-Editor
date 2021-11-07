//Importing awt and file packages
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
//importing swing packages
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
//class for Notepad
class Notepad implements ActionListener
{
    JFrame frame;
    JFrame f;
    JTextArea t;
    JMenuBar mb;
    JMenu mfile,medit;
    JMenuItem mfile1,mfile2,mfile3,mclose;
    JMenuItem medit1,medit2,medit3;
    JPanel panel;
    int flag;
    JButton save,cancel;
    JLabel l1;
    String content,filedir;
    //Constructor
    public Notepad()
    {
        flag=0;
        frame=new JFrame("Text-Editor");

        t=new JTextArea();

        mb=new JMenuBar();

        mfile=new JMenu("File");

        mfile1=new JMenuItem("New");
        mfile2=new JMenuItem("Open");
        mfile3=new JMenuItem("Save");

        mfile1.addActionListener(this);
        mfile2.addActionListener(this);
        mfile3.addActionListener(this);

        mfile.add(mfile1);
        mfile.add(mfile2);
        mfile.add(mfile3);

        medit=new JMenu("Edit");

        medit1=new JMenuItem("Cut");
        medit1.setMnemonic(KeyEvent.VK_X);
        medit2=new JMenuItem("Copy");
        medit3=new JMenuItem("Paste");

        medit1.addActionListener(this);
        medit2.addActionListener(this);
        medit3.addActionListener(this);

        medit.add(medit1);
        medit.add(medit2);
        medit.add(medit3);

        mclose=new JMenuItem("Close");

        mclose.addActionListener(this);

        mb.add(mfile);
        mb.add(medit);
        mb.add(mclose);


        f=new JFrame("File not saved!Do you want to save the file?");
        panel=new JPanel();
        save=new JButton("Save");
        save.setBounds(100,100,30,30);
        save.addActionListener(this);
        cancel=new JButton("Cancel");
        cancel.setBounds(200,100,30,30);
        cancel.addActionListener(this);
        f.setVisible(false);
        f.setSize(450,100);
        panel.add(save);
        panel.add(cancel);
        f.add(panel);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setJMenuBar(mb);
        frame.add(t);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String s=e.getActionCommand();
        if(s.equals("New"))
        {
            frame.setTitle("Text-Editor");
            t.setText("");
        }
        if(s.equals("Cut"))
        {
            t.copy();
            t.cut();
            t.setText("");
        }
        if(s.equals("Copy"))
        {
            t.copy();
        }
        if(s.equals("Paste"))
        {
            t.paste();
        }
        if(s.equals("Save"))
        {
            if(f.isShowing())
                f.dispose();
            if(!frame.getTitle().equals("Text-Editor")&&flag==0)
            {
                File fi=new File(filedir);
                try
                {
                    FileWriter fw=new FileWriter(fi,false);
                    BufferedWriter w=new BufferedWriter(fw);
                    w.write(t.getText());
                    w.flush();
                    w.close();
                    flag=1;
                    content=t.getText();
                }
                catch(Exception evt)
                {
                    JOptionPane.showMessageDialog(frame,evt.getMessage());
                }
            }
            else if(flag==0)
            {
            JFileChooser j=new JFileChooser("f:");
            int r=j.showSaveDialog(null);
            if(r==JFileChooser.APPROVE_OPTION)
            {
                File fi=new File(j.getSelectedFile().getAbsolutePath());
                try
                {
                    frame.setTitle(fi.getName());
                    FileWriter fw=new FileWriter(fi,false);
                    BufferedWriter w=new BufferedWriter(fw);
                    w.write(t.getText());
                    w.flush();
                    w.close();
                    flag=1;
                }
                catch(Exception evt)
                {
                    JOptionPane.showMessageDialog(frame, evt.getMessage());
                }
            } }  
        }
        if (s.equals("Open")) 
        {
			JFileChooser j = new JFileChooser("f:");
			int r = j.showOpenDialog(null);
			if (r == JFileChooser.APPROVE_OPTION) 
            {
				File fi = new File(j.getSelectedFile().getAbsolutePath());
                try 
                {
                    
					String s1 = "", sl = "";
					FileReader fr = new FileReader(fi);
					BufferedReader br = new BufferedReader(fr);
					sl = br.readLine();
					while ((s1 = br.readLine()) != null) 
                    {
						sl = sl + "\n" + s1;
                        br.close();
					}
					t.setText(sl);
                    if(!sl.equals(null))
                    content=new String(sl);
                    filedir=fi.getAbsolutePath();
                    flag=0;
                    frame.setTitle(fi.getName());
				}
				catch (Exception evt) 
                {
					JOptionPane.showMessageDialog(frame, evt.getMessage());
				}
			}
			else
				JOptionPane.showMessageDialog(frame, "You have cancelled the operation");
		}
        if(s.equals("Close"))
        {
            if(!content.equals(t.getText()))
            {    
                flag=0;
                f.setVisible(true);
            }
            if(content.equals(t.getText()))
            {
                flag=1;
            }
	        if(flag==1)
            {
                frame.dispose();
                System.exit(0);
            }
            if(frame.getTitle().equals("Text-Editor"))
                flag=0;

            if(t.getText().equals("")&&frame.getTitle().equals("Text-Editor"))
            {
                frame.dispose();
                System.exit(0);
            }
            
            if(flag==0)
                f.setVisible(true);
        }
        if(s.equals("Cancel"))
        {
            f.dispose();
            frame.dispose();
            System.exit(0);
        }
    }
    //main function
    public static void main(String[] args) {
        new Notepad();
    }

}