/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minesweeper;

import javax.swing.ImageIcon;

/**
 *
 * @author GF65
 */
public class Images {
    /*
	Phương thức load_pics dùng để tải các hình ảnh được sử dụng bởi trò chơi vào một mảng nhất định
	*/
	public static void load_pics(ImageIcon[] pics)
	{
		pics[0] = new ImageIcon("images//cleared.gif");
		pics[1] = new ImageIcon("images//1.gif");
		pics[2] = new ImageIcon("images//2.gif");
		pics[3] = new ImageIcon("images//3.gif");
		pics[4] = new ImageIcon("images//4.gif");
		pics[5] = new ImageIcon("images//5.gif");
		pics[6] = new ImageIcon("images//6.gif");
		pics[7] = new ImageIcon("images//7.gif");
		pics[8] = new ImageIcon("images//8.gif");
		pics[9] = new ImageIcon("images//not_cleared.gif");
		pics[10] = new ImageIcon("images//marked.gif");
		pics[11] = new ImageIcon("images//mine.gif");
	}

	/*
	Phương thức calc_x_zone dùng để lấy vị trí x của chuột và tính toán hình vuông tương ứng của bãi mìn
	*/
	public static int calc_x_zone(int mouse_x_pos)
	{
		final int ZONE_SIDE_LENGTH = 20;

		return(mouse_x_pos / ZONE_SIDE_LENGTH);
	}

	/*
	Phương thức calc_y_zone dùng để lấy vị trí y của chuột và tính toán hình vuông tương ứng của bãi mìn
	*/
	public static int calc_y_zone(int mouse_y_pos)
	{
		final int ZONE_SIDE_LENGTH = 20;

		return(mouse_y_pos / ZONE_SIDE_LENGTH);
	}

	/*
	Phương thức calculate_points dùng để tính điểm số dựa trên số lượng mìn và ô vuông và thời gian trôi qua
	*/
	public static int calculate_points(int number_mines, int number_fields, int time_passed)
	{
		final int CONSTANT  = 500;
		int points = (CONSTANT * number_mines * number_fields)/ time_passed;
		return points;
	}
}
