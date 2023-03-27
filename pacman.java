import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Pacman extends JFrame implements ActionListener {

    private int width = 800, height = 600;
    private int block_size = 32;
    private int[][] blocks = new int[20][25];
    private Image pacman, blinky, pinky, inky, clyde;
    private Timer timer;
    private int pacman_x, pacman_y, pacman_direction = 0;
    private int blinky_x, blinky_y;
    private int pinky_x, pinky_y;
    private int inky_x, inky_y;
    private int clyde_x, clyde_y;
    private int score = 0;

    public Pacman() {
        super("Pacman");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if (key == KeyEvent.VK_LEFT) {
                    pacman_direction = 1;
                } else if (key == KeyEvent.VK_RIGHT) {
                    pacman_direction = 2;
                } else if (key == KeyEvent.VK_UP) {
                    pacman_direction = 3;
                } else if (key == KeyEvent.VK_DOWN) {
                    pacman_direction = 4;
                }
            }
        });

        pacman = new ImageIcon("pacman.png").getImage();
        blinky = new ImageIcon("blinky.png").getImage();
        pinky = new ImageIcon("pinky.png").getImage();
        inky = new ImageIcon("inky.png").getImage();
        clyde = new ImageIcon("clyde.png").getImage();

        initGame();
        timer = new Timer(100, this);
        timer.start();
        setVisible(true);
    }

    private void initGame() {
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                if (i == 0 || i == blocks.length - 1 || j == 0 || j == blocks[i].length - 1) {
                    blocks[i][j] = 1; // walls
                } else {
                    blocks[i][j] = 0; // empty spaces
                }
            }
        }

        blocks[9][9] = 2; // Pacman
        pacman_x = 9 * block_size;
        pacman_y = 9 * block_size;

        blocks[10][9] = 3; // Blinky
        blinky_x = 9 * block_size;
        blinky_y = 10 * block_size;

        blocks[10][10] = 4; // Pinky
        pinky_x = 10 * block_size;
        pinky_y = 10 * block_size;

        blocks[10][8] = 5; // Inky
        inky_x = 8 * block_size;
        inky_y = 10 * block_size;

        blocks[10][11] = 6; // Clyde
        clyde_x = 11 * block_size;
        clyde_y = 10 * block_size;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }

    private void update() {
    	int pacman_next_x = pacman_x, pacman_next_y = pacman_y;
    	if (pacman_direction == 1) { // left
		pacman_next_x -= block_size;
   	} else if (pacman_direction == 2) { // right
        	pacman_next_x += block_size;
    	} else if (pacman_direction == 3) { // up
        	pacman_next_y -= block_size;
    	} else if (pacman_direction == 4) { // down
       	pacman_next_y += block_size;
    	}

    	if (blocks[pacman_next_y / block_size][pacman_next_x / block_size] != 1) {
        pacman_x = pacman_next_x;
        pacman_y = pacman_next_y;

        int block_value = blocks[pacman_y / block_size][pacman_x / block_size];
        if (block_value > 1) { // pacman collides with a ghost
            if (block_value == 2) { // Blinky
                score -= 100;
                initGame();
            } else if (block_value == 3) { // Pinky
                score += 200;
                blocks[pinky_y / block_size][pinky_x / block_size] = 0;
                pinky_x = 12 * block_size;
                pinky_y = 14 * block_size;
                blocks[pinky_y / block_size][pinky_x / block_size] = 4;
            } else if (block_value == 4) { // Inky
                score += 400;
                blocks[inky_y / block_size][inky_x / block_size] = 0;
                inky_x = 10 * block_size;
                inky_y = 14 * block_size;
                blocks[inky_y / block_size][inky_x / block_size] = 5;
            } else if (block_value == 5) { // Clyde
                score += 800;
                blocks[clyde_y / block_size][clyde_x / block_size] = 0;
                clyde_x = 8 * block_size;
                clyde_y = 14 * block_size;
                blocks[clyde_y / block_size][clyde_x / block_size] = 6;
            }
        } else {
            score += 10;
            blocks[pacman_y / block_size][pacman_x / block_size] = 0;
        }
    }
}

@Override
public void paint(Graphics g) {
    super.paint(g);

    for (int i = 0; i < blocks.length; i++) {
        for (int j = 0; j < blocks[i].length; j++) {
            if (blocks[i][j] == 1) { // wall
                g.setColor(Color.BLUE);
                g.fillRect(j * block_size, i * block_size, block_size, block_size);
            } else if (blocks[i][j] == 2) { // pacman
                g.drawImage(pacman, j * block_size, i * block_size, this);
            } else if (blocks[i][j] == 3) { // blinky
                g.drawImage(blinky, j * block_size, i * block_size, this);
            } else if (blocks[i][j] == 4) { // pinky
                g.drawImage(pinky, j * block_size, i * block_size, this);
            } else if (blocks[i][j] == 5) { // inky
                g.drawImage(inky, j * block_size, i * block_size, this);
            } else if (blocks[i][j] == 6) { // clyde
                g.drawImage(clyde, j * block_size, i * block_size, this);
            } else { // empty space
                g.setColor(Color.BLACK);
		    g.fillRect(j * block_size, i * block_size, block_size)
		if (pacman_direction == Direction.RIGHT) {
			g.fillArc(pacman_x, pacman_y, block_size, block_size, 45, 270);
		} else if (pacman_direction == Direction.DOWN) {
			g.fillArc(pacman_x, pacman_y, block_size, block_size, 135, 270);
		} else if (pacman_direction == Direction.LEFT) {
			g.fillArc(pacman_x, pacman_y, block_size, block_size, 225, 270);
		} else if (pacman_direction == Direction.UP) {
			g.fillArc(pacman_x, pacman_y, block_size, block_size, 315, 270);
		}
                // Paint the ghosts
                for (Ghost ghost : ghosts) {
                    g.setColor(ghost.getColor());
                    g.fillOval(ghost.getX(), ghost.getY(), block_size, block_size);
                    g.setColor(Color.WHITE);
                    g.fillOval(ghost.getX() + block_size/2 - block_size/8, ghost.getY() + block_size/4, block_size/4, block_size/4);
                    g.fillOval(ghost.getX() + block_size/2 + block_size/8, ghost.getY() + block_size/4, block_size/4, block_size/4);
                    g.setColor(Color.BLACK);
                    g.fillOval(ghost.getX() + block_size/2 - block_size/16, ghost.getY() + block_size/2, block_size/8, block_size/8);
                }
                
                // Paint the pellets
                g.setColor(Color.WHITE);
                for (int i = 0; i < pellets.length; i++) {
                    for (int j = 0; j < pellets[0].length; j++) {
                        if (pellets[i][j]) {
                            g.fillOval(j * block_size + block_size/2 - block_size/8, i * block_size + block_size/2 - block_size/8, block_size/4, block_size/4);
                        }
                    }
                }
            }
        }
        
        // Move Pac-Man and ghosts
        if (System.currentTimeMillis() - last_move_time > move_interval) {
            last_move_time = System.currentTimeMillis();
            
            // Move Pac-Man
            int next_x = pacman_x;
            int next_y = pacman_y;
            if (pacman_direction == Direction.RIGHT) {
                next_x += block_size;
            } else if (pacman_direction == Direction.DOWN) {
                next_y += block_size;
            } else if (pacman_direction == Direction.LEFT) {
                next_x -= block_size;
            } else if (pacman_direction == Direction.UP) {
                next_y -= block_size;
            }
            if (next_x < 0 || next_x >= width || next_y < 0 || next_y >= height || !isMovable(next_x, next_y)) {
                // Pac-Man cannot move in the intended direction, so do nothing
            } else {
                pacman_x = next_x;
                pacman_y = next_y;
                
                // Check for pellet consumption
                int pellet_i = pacman_y / block_size;
                int pellet_j = pacman_x / block_size;
                if (pellets[pellet_i][pellet_j]) {
                    pellets[pellet_i][pellet_j] = false;
                    score += 10;
                }
            }
            
            // Move ghosts
            for (Ghost ghost : ghosts) {
                int ghost_x = ghost.getX();
                int ghost_y = ghost.getY();
                if (ghost_x % block_size == 0 && ghost_y % block_size == 0) {
			int ghost_grid_x = ghost_x / block_size;
  			int ghost_grid_y = ghost_y / block_size;
  			int ghost_direction = ghosts.get(ghost_num).getDirection();
  
  		if (ghost_direction == RIGHT) {
    			if (canMove(ghost_grid_x+1, ghost_grid_y, ghost_direction)) {
      			ghosts.get(ghost_num).setX(ghost_x + ghost_speed);
    			} else if (canMove(ghost_grid_x, ghost_grid_y-1, UP)) {
      			ghosts.get(ghost_num).setDirection(UP);
  			} else if (canMove(ghost_grid_x, ghost_grid_y+1, DOWN)) {
      			ghosts.get(ghost_num).setDirection(DOWN);
    			} else {
      			ghosts.get(ghost_num).setDirection(LEFT);
    			}
  		}

  		// TODO: Implement the remaining directions and movements for the ghosts

		if (ghost_x % block_size == 0 && ghost_y % block_size == 0) {
  			int ghost_grid_x = ghost_x / block_size;
  			int ghost_grid_y = ghost_y / block_size;
 			int ghost_direction = ghosts.get(ghost_num).getDirection();
  
  			if (ghost_direction == RIGHT) {
   				if (canMove(ghost_grid_x+1, ghost_grid_y, ghost_direction)) {
      				ghosts.get(ghost_num).setX(ghost_x + ghost_speed);
    				} else if (canMove(ghost_grid_x, ghost_grid_y-1, UP)) {
      				ghosts.get(ghost_num).setDirection(UP);
    				} else if (canMove(ghost_grid_x, ghost_grid_y+1, DOWN)) {
      				ghosts.get(ghost_num).setDirection(DOWN);
    				} else {
      				ghosts.get(ghost_num).setDirection(LEFT);
    				}
  			} else if (ghost_direction == LEFT) {
    				if (canMove(ghost_grid_x-1, ghost_grid_y, ghost_direction)) {
      				ghosts.get(ghost_num).setX(ghost_x - ghost_speed);
    				} else if (canMove(ghost_grid_x, ghost_grid_y-1, UP)) {
      				ghosts.get(ghost_num).setDirection(UP);
    				} else if (canMove(ghost_grid_x, ghost_grid_y+1, DOWN)) {
      				ghosts.get(ghost_num).setDirection(DOWN);
    				} else {
      				ghosts.get(ghost_num).setDirection(RIGHT);
    				}
  			} else if (ghost_direction == UP) {
    				if (canMove(ghost_grid_x, ghost_grid_y-1, ghost_direction)) {
      				ghosts.get(ghost_num).setY(ghost_y - ghost_speed);
    				} else if (canMove(ghost_grid_x-1, ghost_grid_y, LEFT)) {
      				ghosts.get(ghost_num).setDirection(LEFT);
    				} else if (canMove(ghost_grid_x+1, ghost_grid_y, RIGHT)) {
      				ghosts.get(ghost_num).setDirection(RIGHT);
    				} else {
      				ghosts.get(ghost_num).setDirection(DOWN);
    				}
  			} else if (ghost_direction == DOWN) {
    				if (canMove(ghost_grid_x, ghost_grid_y+1, ghost_direction)) {
      				ghosts.get(ghost_num).setY(ghost_y + ghost_speed);
    				} else if (canMove(ghost_grid_x-1, ghost_grid_y, LEFT)) {
      				ghosts.get(ghost_num).setDirection(LEFT);
    				} else if (canMove(ghost_grid_x+1, ghost_grid_y, RIGHT)) {
      				ghosts.get(ghost_num).setDirection(RIGHT);
    				} else {
      				ghosts.get(ghost_num).setDirection(UP);
    				}
  			}
		}
	}
}
                    


