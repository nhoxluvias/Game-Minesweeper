/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author GF65
 */
public class Main extends JFrame {
        private JMenuBar menu;
	private JMenu game, difficulty, help;
	private JMenuItem new_game, game_highscores, exit, diff_easy, diff_normal, diff_hard, help_about;
	private JPanel panel;				
	private Container contentPane;		
	private JButton reset;				
	private JPanel top;					
	private JPanel score_display;		
	private JPanel mines_display;		
	private JLabel final_score;			
	private JLabel num_mines;			
	private Highscore highscores;	
	private Time game_time;		
	private Menu_Handler menu_actions;	
	private Board field;				
	private int x_size = 15;			
	private int y_size = 15;			
	private int mines = 45;				
	private boolean game_running = false;

	public Main()
	{
		contentPane = getContentPane();
		menu_actions = new Menu_Handler();
		panel = new JPanel();
		reset = new JButton("Chơi lại: (F2)");
		
		field = new Board(x_size, y_size, mines);			
		field.init_board();									
		game_time = new Time();
		highscores = new Highscore(this);

		panel.addMouseListener(new Mouse_Handler());		
		reset.addActionListener(new Button_Handler());		
											
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setLocation(200,200);								
		setResizable(false);								
		setTitle("Minesweeper");						
		
		menu = new JMenuBar();
		game = new JMenu("Game");
		difficulty = new JMenu("Độ khó");
		help = new JMenu("Hướng dẫn");
		new_game = new JMenuItem("Game mới");
		game_highscores = new JMenuItem("Điểm cao nhất");
		exit = new JMenuItem("Thoát");
		diff_easy = new JMenuItem("Dễ");
		diff_normal = new JMenuItem("Bình thường");
		diff_hard = new JMenuItem("Khó");
		help_about = new JMenuItem("Cách chơi");

	
		game.setMnemonic('F');
		difficulty.setMnemonic('D');
		help.setMnemonic('H');
		new_game.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		exit.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
		diff_easy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
		diff_normal.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
		diff_hard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_MASK));

		game.add(new_game);
		game.add(game_highscores);
		game.addSeparator();
		game.add(exit);		
		difficulty.add(diff_easy);
		difficulty.add(diff_normal);
		difficulty.add(diff_hard);
		help.add(help_about);
		menu.add(game);
		menu.add(difficulty);
		menu.add(help);

		//Add actionlisteners for the menu buttons
		new_game.addActionListener(menu_actions);
		game_highscores.addActionListener(menu_actions);
		exit.addActionListener(menu_actions);
		diff_easy.addActionListener(menu_actions);
		diff_normal.addActionListener(menu_actions);
		diff_hard.addActionListener(menu_actions);
		help_about.addActionListener(menu_actions);

		setJMenuBar(menu);

		top = new JPanel();
		score_display = new JPanel();		
		mines_display = new JPanel();
		final_score = new JLabel();
		num_mines = new JLabel();
		score_display.setBackground(Color.white);
		mines_display.setBackground(Color.white);
		score_display.setBorder(BorderFactory.createTitledBorder("Điểm"));		
		mines_display.setBorder(BorderFactory.createTitledBorder("Bom"));
		top.setLayout(new GridLayout(1, 3));
		score_display.setLayout(new BoxLayout(score_display, BoxLayout.X_AXIS));
		mines_display.setLayout(new BoxLayout(mines_display, BoxLayout.X_AXIS));
		final_score.setText("");
		final_score.setAlignmentX(Container.RIGHT_ALIGNMENT);
		num_mines.setText(String.valueOf(field.get_mines()));
		score_display.add(final_score);
		mines_display.add(num_mines);
		top.add(game_time.get_clock());
		top.add(mines_display);
		top.add(score_display);		

		contentPane.setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(field.get_y_size(), field.get_x_size()));
		init_graphic_minefield();								
		
		contentPane.add(top, BorderLayout.NORTH);
		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(reset, BorderLayout.SOUTH);
		
		pack();				
		setVisible(true);
	}

	public static void main(String[] args)
	{
		new Main();
	}
	
	private void init_graphic_minefield()
	{
		for (int n = 0; n < field.get_y_size(); n++)
		{
			for (int m = 0; m < field.get_x_size(); m++)
			{
				panel.add(field.get_minefield()[m][n].get_label());
			}
		}
	}
	
	private void reset_game(int new_x_size, int new_y_size, int new_num_mines)
	{	
		game_running = false;

		if (panel.getMouseListeners().length == 0)
			panel.addMouseListener(new Mouse_Handler());		
		
		contentPane.remove(panel);	
		panel.removeAll();			
		
		field = new Board(new_x_size, new_y_size, new_num_mines);	
		field.init_board();		

		panel.setLayout(new GridLayout(field.get_y_size(), field.get_x_size()));		
		num_mines.setText(String.valueOf(field.get_mines()));
		
		init_graphic_minefield();
		contentPane.add(panel, BorderLayout.CENTER);
		pack();

		game_time.reset();			
		final_score.setText("");	
	}
	
	private void game_completed()
	{
		game_time.stop();
		panel.removeMouseListener(panel.getMouseListeners()[0]);	
		int score = Images.calculate_points(field.get_mines(), (field.get_x_size() + 1) * (field.get_y_size() + 1), game_time.get_time());
		final_score.setText(String.valueOf(score));			
		if (highscores.good_enough(score))	
		{
			String name = JOptionPane.showInputDialog("Xin chúc mừng!\nBạn đạt được top điểm cao nhất!\n\nMời bạn nhập tên.");
			if (name == null)
				name = "Unknown";
                        
			highscores.new_highscore(new Highscore_Input(name, score));
		}
		display_highscores();
	}
	
	private void game_over()
	{
		game_time.stop();	
		panel.removeMouseListener(panel.getMouseListeners()[0]);
		field.clear_all();
	}

	private void display_highscores()
	{
		highscores.get_highscores(this.getX(), this.getY(), this.getWidth(), this.getHeight()).setVisible(true);
	}

	private class Mouse_Handler extends MouseAdapter
	{
		public void mouseReleased(MouseEvent e)
		{
			if (e.getButton() == e.BUTTON1)		
			{
				if (game_running == false)	
				{
					game_running = true;
					game_time.start();
				}
				switch(field.clear_zone(Images.calc_x_zone(e.getX()), Images.calc_y_zone(e.getY())))
				{
					case 0:				
						break;
					case -1:											
						game_over();
						break;
					case 1:
						game_completed();
						break;
				}
			}
			if (e.getButton() == e.BUTTON2)		
				;
			if (e.getButton() == e.BUTTON3)		
			{
				if (game_running == false)		
				{
					game_running = true;
					game_time.start();
				}
				field.mark_zone(Images.calc_x_zone(e.getX()), Images.calc_y_zone(e.getY()));
			}
		}
	}

	private class Button_Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == reset)
			{
				reset_game(field.get_x_size(), field.get_y_size(), field.get_mines());
			}
		}
	}

	private class Menu_Handler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == new_game)
				reset_game(field.get_x_size(), field.get_y_size(), field.get_mines());
			if (e.getSource() == game_highscores)				
				display_highscores();
			if (e.getSource() == exit)
				System.exit(0);
			if (e.getSource() == diff_easy)
				reset_game(10, 10, 5);	
			if (e.getSource() == diff_normal)
				reset_game(15, 15, 45);	
			if (e.getSource() == diff_hard)
				reset_game(20, 20, 80);
			if (e.getSource() == help_about)
				JOptionPane.showMessageDialog(panel, "Minesweeper có mục đích của riêng nó như là một cách tập cho người sử dụng máy tính sử dụng con trỏ chuột: Người chơi có nhiệm vụ mở hết các ô vuông mà không được bấm vào ô có chứa mìn, bấm chuột trái là mở ô, bấm chuột phải là cắm cờ, khi bạn bấm phải một ô có chứa bom thì bạn sẽ thua.", "Minesweeper", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
