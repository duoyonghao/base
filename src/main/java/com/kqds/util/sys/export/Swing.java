package com.kqds.util.sys.export;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Swing
  extends JPanel
{
  private static final String LABEL_KAIPIAO = "转开票系统";
  private static final String LABEL_KQDS = "转真实系统";
  private static final String LABEL_CQ = "重启系统";
  private Action actionKaipiao;
  private Action actionKqds;
  private Action actionCq;
  
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        try
        {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        JFrame test = new JFrame("系统设置");
        test.setContentPane(new Swing());
        test.setSize(600, 300);
        
        test.setResizable(false);
        test.setLocationRelativeTo(null);
        test.setDefaultCloseOperation(2);
        test.setVisible(true);
      }
    });
  }
  
  Swing()
  {
    super(new BorderLayout(5, 5));
    assert (SwingUtilities.isEventDispatchThread());
    
    this.actionKaipiao = new AbstractAction("转开票系统")
    {
      public void actionPerformed(ActionEvent e)
      {
        String strcmd = "cmd /c start  C:\\tkp.bat";
        Swing.this.run_cmd(strcmd);
      }
    };
    this.actionKqds = new AbstractAction("转真实系统")
    {
      public void actionPerformed(ActionEvent e)
      {
        String strcmd = "cmd /c start  C:\\zreal.bat";
        Swing.this.run_cmd(strcmd);
      }
    };
    this.actionCq = new AbstractAction("重启系统")
    {
      public void actionPerformed(ActionEvent e)
      {
        String strcmd = "cmd /c start  C:\\cq.bat";
        Swing.this.run_cmd(strcmd);
      }
    };
    add(layoutControl());
    setAlignmentY(0.5F);
    setAlignmentX(0.5F);
    setBorder(BorderFactory.createEmptyBorder(100, 5, 5, 5));
  }
  
  private JComponent layoutControl()
  {
    JComponent result = new JPanel();
    JButton jb1 = new JButton(this.actionKaipiao);
    jb1.setPreferredSize(new Dimension(150, 50));
    result.add(jb1);
    
    JButton jb2 = new JButton(this.actionKqds);
    jb2.setPreferredSize(new Dimension(150, 50));
    result.add(jb2);
    
    JButton jb3 = new JButton(this.actionCq);
    jb3.setPreferredSize(new Dimension(150, 50));
    result.add(jb3);
    result.setAlignmentY(0.5F);
    result.setAlignmentX(0.5F);
    return result;
  }
  
  public void run_cmd(String strcmd)
  {
    Runtime rt = Runtime.getRuntime();
    Process ps = null;
    try
    {
      ps = rt.exec(strcmd);
      ps.waitFor();
    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    int i = ps.exitValue();
    









    ps.destroy();
    ps = null;
  }
}
