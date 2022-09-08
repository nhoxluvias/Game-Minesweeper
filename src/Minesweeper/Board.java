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
public class Board {
        private Zone[][] minefield;		
	private ImageIcon[] pics;		
	private int mines = 0;			
	private int cleared_zones = 0;	

	public Board(int x_size, int y_size, int number_of_mines)
	{
		minefield = new Zone[x_size][y_size];
		mines = number_of_mines;
		pics = new ImageIcon[12];
	}
	
	public void init_board()
	{
		Images.load_pics(pics);						
		PlaceMines.init_array(minefield, pics);		
		PlaceMines.mineplacer(mines, minefield);		
		PlaceMines.check_neighbours(minefield);		
		cleared_zones = 0;
	}

	/*
	mark_zone dùng để đánh dấu các ô vuông hoặc bỏ đánh dấu nếu ô vuông đã được đánh dấu
	*/
	public void mark_zone(int x_pos, int y_pos)
	{
		if (!minefield[x_pos][y_pos].is_cleared())
		{
			if (minefield[x_pos][y_pos].is_marked())
				minefield[x_pos][y_pos].unmark();
			else
				minefield[x_pos][y_pos].mark();
		}
	}
	
	/*
	clear_zone dùng để xóa tọa độ cho trước và lần lượt xóa các vùng liền kề nếu ô vuông đầu tiên không có mìn gần đó
	*/
	public int clear_zone(int x_pos, int y_pos)
	{
		if (!minefield[x_pos][y_pos].is_marked() && minefield[x_pos][y_pos].is_mined())
		{
			return -1;
		}
		if (!minefield[x_pos][y_pos].is_cleared() && !minefield[x_pos][y_pos].is_marked())
		{
			minefield[x_pos][y_pos].clear();					
			cleared_zones++;									

			if (minefield[x_pos][y_pos].nearby_mines() == 0)
			{
				for (int i = -1; i <= 1; i++)
				{
					for (int j = -1; j <= 1; j++)
					{
						if ((x_pos + j >= 0) && (x_pos + j < minefield.length) && (y_pos + i >= 0) && (y_pos + i < minefield[0].length))
						{
							if (!minefield[x_pos + j][y_pos + i].is_cleared())
								clear_zone(x_pos + j, y_pos + i);					
						}
					}
				}
			}
		}
		if (minefield.length * minefield[0].length - cleared_zones - mines == 0)
		{
			return 1;	
		}
		else
			return 0;	
	}

	/*
	clear_all dùng để xóa toàn bộ bãi mìn
	*/
	public void clear_all()
	{
		for (int i = 0; i < minefield[0].length; i++)
		{
			for (int j = 0; j < minefield.length; j++)
			{
				minefield[j][i].clear();
			}
		}
	}

	public Zone[][] get_minefield()
	{
		return minefield;
	}
		
	public int get_mines()
	{
		return mines;
	}

	public int get_x_size()
	{
		return minefield.length;
	}

	public int get_y_size()
	{
		return minefield[0].length;
	}
}
