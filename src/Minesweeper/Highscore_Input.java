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

public class Highscore_Input
{
	private int score;
	private String name;
	
	public Highscore_Input(String new_name, int new_score)
	{
		score = new_score;
		name = new_name;
	}
/*				
* get_score dùng để trả điểm về kiểu int
*/

	public int get_score()
	{
		return score;
	}

/*				
* Highscore dùng để trả về tên dạng chuỗi
*/

	public String get_name()
	{
		return name;
	}
}