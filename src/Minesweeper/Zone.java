/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minesweeper;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author GF65
 */
public class Zone {
    private boolean mined = false;		
	private boolean cleared = false;	
	private boolean marked = false;		
	private int nearby_mines = 0;		
	private JLabel label;				
	private ImageIcon[] pics;			

	/*
	Phương thức zone dùng để khỏi tạo và trả về 1 đối tượng kiểu vùng
	*/
	public Zone(ImageIcon[] p)
	{
		label = new JLabel();
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.TOP);
		pics = p;
		label.setIcon(pics[9]);
	}

	/*
	Phương thức clear dùng để xóa các ô vuông và đặt các hình ảnh chính xác
	*/
	public void clear()
	{
		cleared = true;

		if (is_mined())
				label.setIcon(pics[11]);			//Đặt hình ảnh mìn
		else
		{
			if (nearby_mines == 0)
				label.setIcon(pics[0]);				//Đặt hình ảnh trống, rỗng
			else
				label.setIcon(pics[nearby_mines]);	
		}
	}

	/*
	Phương thức add_neary_mine dùng để thêm mìn vào các ô gần đó
	*/
	public void add_nearby_mine()
	{
		nearby_mines++;
	}

	/*
	Phương thức mark dùng để đánh dấu ô vuông, đặt nhãn của nó để hiển thị ảnh chính xác
	*/
	public void mark()
	{
		marked = true;
		label.setIcon(pics[10]);	//Đặt các hình vào vùng được đánh dấu, không rõ ràng
	}

	/*
	Phương thức unmark dùng để bỏ dấu hình vuông, đặt nhãn của nó để hiển thị ảnh chính xác
	*/
	public void unmark()
	{
		marked = false;
		label.setIcon(pics[9]);		//Đặt hình vào vùng trống, không rõ ràng
	}

	public boolean is_mined()
	{
		return mined;
	}

	public boolean is_cleared()
	{
		return cleared;
	}

	public boolean is_marked()
	{
		return marked;
	}

	public int nearby_mines()
	{
		return nearby_mines;
	}

	public void mine()
	{
		mined = true;
	}

	public JLabel get_label()
	{
		return label;
	}	
}
