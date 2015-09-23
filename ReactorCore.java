package nukesim1;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ReactorCore implements Observer {
	static int counter;
	static final int RATE_MIN = 0;
	static final int RATE_MAX = 100;
	static final int RATE_INIT = 0;
	
	private int coolant_flowrate;
	private int temp_without_flow;
	private int temperature = 76;
	private int reaction_rate;
	private JPanel panel;
	private final JTextArea textarea;
	private JSlider slider;
	private JLabel label1,label2;
	Timer timer;
	public ReactorCore()
	{
		label1 = new JLabel("REACTOR CORE TEMP");
		label2 = new JLabel("REACTION RATE");
		textarea = new JTextArea("76");
		slider = new JSlider(JSlider.HORIZONTAL,RATE_MIN,RATE_MAX,RATE_INIT);
		
		textarea.setEditable(false);
		textarea.setSize(20, 20);
		label1.setFont(new Font(label1.getName(),Font.PLAIN,20));
		label2.setFont(new Font(label2.getName(),Font.PLAIN,20));
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		panel = new JPanel(new GridLayout(0,1));
		
		panel.add(label1);
		panel.add(textarea);
		panel.add(label2);
		panel.add(slider);
		
		slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting())
				{
					reaction_rate = source.getValue();
					calculateTemp();
				}
				
			}
			
		});
	}
	private class MyActionListener implements ActionListener{
		boolean sub;
		private int flowrate;
		private int temp;
		public MyActionListener(int temp)
		{
			if(counter<temp)
			{
				sub = false;
			}
			else 
			{
				sub = true;
			}
			this.temp = temp;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(sub==false)
			{
				if(counter<=temp)
				{
					textarea.setText(Integer.toString(counter));
					textarea.updateUI();
					counter++;
				}
				else
					timer.stop();
			}
			else
				if(counter>=temp)
				{
					textarea.setText(Integer.toString(counter));
					textarea.updateUI();
					counter--;					
				}
				else
					timer.stop();
		}
		
	}
	public JPanel getPanel()
	{
		return panel;
	}
	
	public void calculateTemp()
	{
		
		counter = temperature;
		temp_without_flow = (10*reaction_rate)-(5*coolant_flowrate);
		temperature = temp_without_flow;
		timer = new Timer(75,new MyActionListener(temp_without_flow));
		timer.start();
	}
	
	public void calculateTemp(int flowrate)
	{
		
		counter = temperature;
		coolant_flowrate = flowrate;
		temperature = temp_without_flow - (5*flowrate);
		int temp = temperature;
		Counter(temp,flowrate);
	}
	public void Counter(int temp,int flowrate)
	{
		
		timer = new Timer(100, new MyActionListener(temp));	
		timer.start();
	}
	
	public void setReactionRate(int rate)
	{
		this.reaction_rate = rate;
	}
	public int getReactionRate()
	{
		return reaction_rate;
	}

	@Override
	public void update(Observable o, Object arg) {
		int flowrate = ((Integer)arg).intValue();
		calculateTemp(flowrate);
		
	}

}
