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
public class PlaceMines {
    /*
	Phương thức init_array dùng để tạo mảng x và vùng đối tượng trong mọi mảng
	*/

	public static void init_array(Zone[][] x, ImageIcon[] images)							
	{
		for (int y_pos = 0; y_pos < x[0].length; y_pos++)	//Nhập vị trí y trong mảng
		{
			for (int x_pos = 0; x_pos < x.length; x_pos++)	//Nhập vị trí x trong mảng
			{
				x[x_pos][y_pos] = new Zone(images); //Tạo vị trí mảng (x , y)
			}
		}
	}

	/*
	Phương thức mineplacer dùng để đặt ngẫu nhiên mìn trong mảng 
	*/

	public static void mineplacer(int number_mines, Zone[][] x)
	{
		int x_position;
		int y_position;
		boolean work_done = false;	 
		
		for (int i = 0; i < number_mines; i++)
		{
			work_done = false;			
			while (!work_done)			
			{
				x_position = (int) (x.length * Math.random());
				y_position = (int) (x[0].length * Math.random());
				if (!x[x_position][y_position].is_mined())
				{
					x[x_position][y_position].mine();
					work_done = true;
				}
			}
		}
	}

	/*
	Phương thức check_neighbours dùng để kiểm tra các mìn liền kề
	*/

	public static void check_neighbours(Zone[][] x)
	{
		for (int y_pos = 0; y_pos < x[0].length; y_pos++)		
		{
			for (int x_pos = 0; x_pos < x.length; x_pos++)		
			{
				if (x[x_pos][y_pos].is_mined())
				{
					for (int i = -1; i <= 1; i++)
					{
						for (int j = -1; j <= 1; j++)
						{
							if ((x_pos + j >= 0) && (x_pos + j < x.length) && (y_pos + i >= 0) && (y_pos + i < x[0].length))
							{
								x[x_pos + j][y_pos + i].add_nearby_mine();
							}
						}
					}
				}
			}
		}
	}
}
