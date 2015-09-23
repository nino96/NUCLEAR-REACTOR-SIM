package nukesim1;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PrimaryCoolant extends Observable{
	private int flow_rate;
	private JPanel panel;
	private JLabel label;
	private JSlider slider;
	public PrimaryCoolant(){
		slider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		label = new JLabel("PRIMARY PUMP FLOW RATE");
		panel = new JPanel(new GridLayout(0,1));
		panel.setSize(400, 60);
		
		label.setFont(new Font(label.getName(),Font.PLAIN,20));
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(5);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		
		slider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()){
					flow_rate = source.getValue();
					setChanged();
					notifyObservers(new Integer(flow_rate));
				}
				
			}
			
		});
		panel.add(label);
		panel.add(slider);
	}
	
	public JPanel getPanel(){
		return panel;
	}
}
