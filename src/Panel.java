import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Panel extends GamePanel
{
    ArrayList<Pos> available = new ArrayList<Pos>();
    char[][] board = {{(char)0,(char)0,(char)0},{(char)0,(char)0,(char)0},{(char)0,(char)0,(char)0}};
    Map<Character, Integer> scores = new HashMap<>();
    int width =200;
    char ai = 'O', player ='X',winner;
    boolean compMove = true, playing = true, printed = false;
    public Panel()
    {
        /*for(int i =0; i<3; i++)
        {
            for(int j =0; j<3; j++)
            {
                available.add(new Pos(i,j));
            }
        }*/

        scores.put('X',-10);
        scores.put('O', 10 );
        scores.put('t',0);
        nextMove();
        this.start();
    }

    @Override
    public void update()
    {





    }

    public char   checkWin()
    {
        // horizontal
        char winner = 'n';
        for(int  i=0; i<3; i++)
        {
            if(equal3(board[i][0],board[i][1],board[i][2]))
            {
                winner = board[i][0];
            }
        }
        for(int  i=0; i<3; i++)
        {
            if(equal3(board[0][i],board[1][i],board[2][i]))
            {
                winner = board[0][i];
            }
        }
        if(equal3(board[0][0],board[1][1],board[2][2]))
        {
            winner = board[0][0];
        }
        if(equal3(board[0][2],board[1][1],board[2][0]))
        {
            winner = board[0][2];
        }
        int open = 0;
        for(int i =0 ; i<3; i++)
        {
            for(int j =0; j<3; j++)
            {
                if(board[i][j]==(char)0)
                {
                    open++;
                }
            }
        }
        if(open==0&&winner=='n')
        {
            return 't';

        }else
        {
            return winner;
        }

    }



    public boolean equal3(char   a, char b , char c)
    {

        if(a==b&&b==c&&a!=(char)0)
        {
            return true;
        }
        return false;
    }

    public String[][] copy(String[][] grid)
    {
        String[][] temp = new String[3][3  ];
        for(int i =0; i<3; i++)
        {
            for(int j =0; j<3; j++)
            {
               temp[i][j] = grid[i][j];
            }
        }
        return temp;
    }




    public void nextMove()
    {

            Pos bestMove = new Pos();
            int bestScore = Integer.MIN_VALUE;
            for(int i=0;i<3; i++)
            {
                for(int j=0; j<3; j++)
                {
                    if(board[i][j]==(char)0) // for each move
                    {

                        board[i][j] = ai;
                       int score = miniMax(board,0, false);
                        board[i][j]=(char)0;
                        if(score>bestScore)
                       {
                           bestScore = score;
                           bestMove = new Pos(i,j);
                       }
                       // might want to revert spot back when done with this time

                    }
                }
            }
            board[bestMove.row][bestMove.col] = ai;
            compMove=false;




    }

    public int miniMax(char[][] grid , int depth , boolean maxizmizingPlayer )
    {
        // check winner
        char result = checkWin();
        if(result!='n')
        {

            return  scores.get(result) ;
        }

        if(maxizmizingPlayer)
        {
            int bestScore = Integer.MIN_VALUE;
            for(int i=0;i<3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (grid[i][j]==(char)0) // for each move
                    {
                        grid[i][j] = ai;
                        int score  = miniMax(grid, depth+1,false  );
                        grid[i][j]=(char)0;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        }else
        {
            int bestScore = Integer.MAX_VALUE;
            for(int i=0;i<3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    if (grid[i][j]==(char)0) // for each move
                    {
                        grid[i][j] = player;
                        int score  = miniMax(grid, depth+1,true  );
                        grid[i][j] = (char)0;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }



    }


    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setFont(new Font("Ink Free", Font.BOLD,80));
        for(int i=0; i<3; i++)
        {
            for(int j =0; j<3; j++)
            {
                g2.drawRect(j*width,i*width, width , width);
                g2.drawString(""+board[i][j],j*width+width/3, i*width+width/2);
            }
        }
        char result =checkWin();
        if(result!='n')
        {
            playing = false;
            if(!printed)
            {
                if(result=='t')
                {
                    System.out.println("TIE");
                }else
                {
                    System.out.println(result+" WON!"); 
                }

                printed = true;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(playing)
        {
            if(!compMove)
            {
                int x = e.getX()/width;
                int y = e.getY()/width;
                if(board[y][x]==(char)0)
                {
                    board[y][x] = player;
                    /*for(int i =0 ; i<available.size(); i++)
                    {
                        if(available.get(i).row==y&&available.get(i).col==x)
                        {
                            available.remove(i);

                        }
                    }*/
                    compMove = true;
                    nextMove();
                    // check if game won
                     winner = checkWin();
                }
            }

        }

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    @Override
    public void mouseDragged(MouseEvent e)
    {

    }

    @Override
    public void mouseMoved(MouseEvent e)
    {

    }
}
