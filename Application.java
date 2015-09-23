package nukesim1;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Application {

	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, fall back to cross-platform
		    try {
		        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch (Exception ex) {
		        
		    }
		 }
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				ReactorCore core = new ReactorCore();
				PrimaryCoolant coolant = new PrimaryCoolant();
				coolant.addObserver(core);
				JFrame frame = new JFrame("Nuclear Test");
				JPanel finalpanel = new JPanel(new GridLayout(0,1));
				frame.setLayout(new GridLayout(0,1));
				frame.setSize(400, 400);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				finalpanel.add(core.getPanel());
				finalpanel.add(coolant.getPanel());
				frame.add(finalpanel);
				frame.setVisible(true);
			}
		});

	}

}
