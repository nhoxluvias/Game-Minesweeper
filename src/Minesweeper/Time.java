/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minesweeper;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author GF65
 */
public class Time {
        Timer timer; //Khởi tạo timer
	JPanel clock;					
	JLabel counter;				
	int current_time = 0; //Bắt đầu từ 0

	/*
	Time dùng để tạo thời gian đếm
	*/
	
	public Time()
	{
		timer = new Timer(1000, new count());
		clock = new JPanel();
		counter = new JLabel();
		clock.setBorder(BorderFactory.createTitledBorder("Thời gian"));
		clock.setLayout(new GridLayout(1, 1));
		clock.setBackground(Color.white);
		counter.setText("0");
		counter.setBackground(Color.white);
		clock.add(counter);
	}
	/*
	count dùng để nhận một hành động từ bộ hẹn giờ và thêm 1 vào biến current_time
	*/
	
	private class count implements ActionListener
	{
		public void actionPerformed(ActionEvent s)
		{
			counter.setText(String.valueOf(current_time));
			current_time++;
		}
	}

	/*
	start dùng để bắt đầu đếm  thời gian khi bắt đầu trò chơi
	*/

	public void start()
	{
		counter.setText(String.valueOf(current_time));
		current_time++;
		timer.start();
	}

	/*
	stop dùng để dừng lại khi thua hoặc người chơi dừng chơi
	*/

	public void stop()
	{
		timer.stop();
	}

	/*
	reset dùng để khởi tạo lại thời gian và trả thời gian về 0
	*/

	public void reset()
	{
		if (timer.isRunning())
			timer.stop();

		current_time = 0;
		counter.setText(String.valueOf(current_time));
	}

	public int get_time()
	{
		return current_time;
	}
	
	public JPanel get_clock()
	{
		return clock;
	}
}
