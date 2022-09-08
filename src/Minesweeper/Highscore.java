/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minesweeper;

/**
 *
 * @author GF65
 */
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Highscore
{
	Highscore_Input[] highscores = new Highscore_Input[10]; //Ma trận Highscore_Input chứa thông tin Highscore 
	private String dirname = ".";							
	private String filename = "highscore.txt";							
	private File highscore_file = new File(dirname, filename);
	private PrintWriter highscore_stream; 
	private BufferedReader old_highscore_stream; 
	
	private JPanel names;
	private JPanel scores;
	private JDialog highscore_dialog;
	private JLabel[] name_labels;
	private JLabel[] score_labels;
	
	
	public Highscore(JFrame my_frame)
	{
		names = new JPanel();
		scores = new JPanel();
		highscore_dialog = new JDialog(my_frame, "Điểm cao nhất", true);
		name_labels = new JLabel[10];
		score_labels = new JLabel[10];
		highscore_dialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		highscore_dialog.setResizable(false);											
		highscore_dialog.getContentPane().setLayout(new GridLayout(1, 2));		
		highscore_dialog.getContentPane().setBackground(Color.magenta);
		names.setLayout(new BoxLayout(names, BoxLayout.Y_AXIS));				
		scores.setLayout(new BoxLayout(scores, BoxLayout.Y_AXIS));
		for (int i = 0; i < 10; i++)
		{
			name_labels[i] = new JLabel();
			score_labels[i] = new JLabel();
		}
	}
	
	/*
	init_label_arrays dùng để khởi tạo Labelarrays name_labels và score_labels
	*/

	private void init_label_arrays()
	{
		for (int i = 0; i < 10; i++)
		{
			name_labels[i].setText(highscores[i].get_name());		
			name_labels[i].setAlignmentX(Component.LEFT_ALIGNMENT);
			score_labels[i].setText(String.valueOf(highscores[i].get_score())); 
			score_labels[i].setAlignmentX(Component.RIGHT_ALIGNMENT);
		}
	}

	/*
	init_filewrite dùng để có thể viết 1 tập tin
	*/

	private void init_filewrite()
	{
		try
		{
			if (!highscore_file.canWrite())			
				highscore_file.createNewFile();		
		
			highscore_stream = new PrintWriter(new BufferedWriter(new FileWriter(highscore_file)));		
			for(int i = 0; i < 10; i++)		
			{
				highscore_stream.println(highscores[i].get_name());			
				highscore_stream.println(highscores[i].get_score());
			}
			highscore_stream.close(); 
		}
		catch (IOException s)			
		{
			System.out.println("");
		}
	}

	/*
         init_fileread dùng để đọc file
	
	*/

	private void init_fileread()
	{
		try
		{
			if (!highscore_file.canWrite())			
				highscore_file.createNewFile();		

			old_highscore_stream = new BufferedReader(new FileReader(highscore_file)); 
		}
		catch (IOException s)
		{
			System.exit(1);			
		}
	}

	/*
	read_highscore dùng để đọc tập tin Highscore và chèn vào mảng highscore_file
	*/

	private void read_highscore()
	{
		try
		{
			for (int i = 0; i < 10; i++)
			{
				String temp_name = old_highscore_stream.readLine();	
				if (temp_name == null)												
					temp_name = "Empty";
				String p00p = old_highscore_stream.readLine();			
				int temp_score = 0;													
				if (p00p != null)															
					temp_score = Integer.parseInt(p00p);

				highscores[i] = new Highscore_Input(temp_name, temp_score);		
			}
			old_highscore_stream.close();		
		}
		catch(IOException s)
		{
			System.exit(1);						
		}
	}
	
	/*
	organize_highscore dùng để sắp xếp điểm cao nhất theo thứ tụ. Bắt đầu ở vị trí 0, sau đó sắp xếp giá trị cao nhất đến vị trí 0. Sau đó tiếp tục và kiểm tra từ vị trí 1 trở đi, và lưu
giá trị cao nhất của các vị trí còn lại ở vị trí 1, v.v.
	*/

	private void organize_highscore()
	{
		Highscore_Input temp;	 
		for (int i = 0; i < 10; i++)
		{
			int temp_position = i;		
			for (int n = i; n < 10; n++)
			{
				if (highscores[temp_position].get_score() < highscores[n].get_score())		
					temp_position = n;																				
			}
			temp = highscores[i];																					
			highscores[i] = highscores[temp_position];													
			highscores[temp_position] = temp;																
		}
	}
	
	/*
	good_enough kiểm tra người dùng có đủ điểm để vào bảng highscore không
	*/

	public boolean good_enough(int points)
	{
		init_fileread();										
		read_highscore();												
		organize_highscore();							
		if (points > highscores[9].get_score())	
			return true;
		else
			return false;
	}

	public void new_highscore(Highscore_Input new_input)
	{
		init_fileread();
		organize_highscore();					
		highscores[9] = new_input;			
		organize_highscore();					
		init_filewrite();								
	}
	
	public JDialog get_highscores(int x, int y, int owner_width, int owner_height)
	{
		init_fileread();							
		read_highscore();					
		organize_highscore();				
		init_label_arrays();					
		for (int i = 0; i < 10; i++)
		{
			names.add(name_labels[i]);	
			scores.add(score_labels[i]);
		}
		highscore_dialog.getContentPane().add(names);
		highscore_dialog.getContentPane().add(scores);
		highscore_dialog.pack();			
		highscore_dialog.setLocation(x + (owner_width / 2) - (highscore_dialog.getWidth() / 2), y + (owner_height / 2) - (highscore_dialog.getHeight() / 2));
		return highscore_dialog;			
	}
}