
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Platformer extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Runnable {
	private static final long serialVersionUID = 6965003731288522401L;
	/**
	 * A platformer by Runeneo
	 */
	
	static Image[] tiles = new Image[14];
	static Image[] tiles2 = new Image[14];
	static Image[] monsters = new Image[8];
	static Image[] player = new Image[14];
	static int sizeX = 858;
	static int sizeY = 628;
	static JFrame j = new JFrame("Platformer");
	
	static BufferedImage background = new BufferedImage(650, 550, BufferedImage.TYPE_INT_ARGB);
	static Graphics bg = background.getGraphics();
	
	static Image renderImage = new BufferedImage(650, 550, BufferedImage.TYPE_INT_ARGB);
	static Graphics ri = renderImage.getGraphics();
	
	public void init() {
		new Thread(this).start();
	}
	
	public void run() {
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setSize(sizeX, sizeY);
		j.setVisible(true);
		Platformer gui = new Platformer();
		j.add(gui);
		j.addMouseListener((MouseListener) gui);
		j.addMouseMotionListener((MouseMotionListener) gui);
		j.addKeyListener((KeyListener) gui);
		
		for (int i = 1; i < tiles.length-1; i++) {
				tiles[i] = (Image) Toolkit.getDefaultToolkit().getImage(getClass().getResource("Tile Sides/"+i+".png"));
				tiles2[i] = (Image) Toolkit.getDefaultToolkit().getImage(getClass().getResource("Tile Sides/"+i+".png"));
				ri.drawImage(tiles[i], 0, 0,this);
				ri.drawImage(tiles2[i], 0, 0,this);
		}
		
		tiles2[7] = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
		tiles[tiles.length-1] = (Image) Toolkit.getDefaultToolkit().getImage(getClass().getResource("Tile Sides/m1.png"));
		
		for (int i = 0; i < 8; i++){
			monsters[i] = (Image) Toolkit.getDefaultToolkit().getImage(getClass().getResource("Tile Sides/m"+(i + 1)+".png"));
			ri.drawImage(monsters[i], 0, 0,this);
		}
		
		for (int i = 0; i < 12; i++){
			player[i] = (Image) Toolkit.getDefaultToolkit().getImage(getClass().getResource("Tile Sides/p"+(i + 1)+".png"));
			ri.drawImage(player[i], 0, 0,this);
		}
		
		player[12] = (Image) Toolkit.getDefaultToolkit().getImage(getClass().getResource("Tile Sides/s1.png"));
		player[13] = (Image) Toolkit.getDefaultToolkit().getImage(getClass().getResource("Tile Sides/s2.png"));
		ri.drawImage(player[12], 0, 0,this);
		ri.drawImage(player[13], 0, 0,this);
		
		for (int i = 0; i < 550; i++){
			int n = i / 4;
			bg.setColor(new Color(100 + n, 100 + n, 255));
			bg.drawLine(0, i, 650, i);
		}
		bg.setColor(Color.WHITE);
		for (int i = 0; i < 90; i++){
			int x = (int) Math.round((Math.random() * 650) - 100);
			int y = (int) Math.round((1 + Math.sin(i)) * 300);
			int w = (int) Math.round((150 - (y / 2)) + Math.random() * 20);
			int h = w / 4;
			bg.fillOval(x, y, w, h);
		}
	}
	
	BufferedImage db = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
	Graphics g2 = db.getGraphics();
	
	String[] defaultMaps = 	{"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaalllaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaamaaaaaaaaaaaaaaaaaaaaaaaaaaaaallaaaaaaaaaaaaaakkkkaaaaaaaaaaaaaaaaalllllaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaalllaaaaaalaaaaaacdaaaaaalllaaaaaaaaaaaaaaaaaaaakkaaaaaaaaaaaaaaaaaaaaaaaaaallllaaaaaaaaaaaaafaaaaaakkkaaaaaaaaaaaalllaaaaaaacdaaaaaaaaaaaaaacdaceeaaaaaaaanaaaaaaaanaaaaaaallaaaaaaaaaaaaaaaaaaaaaaaaaaaanaaaaaaaacdaaanaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaleeaaaanaaaaaafaaaaaaaaaaaaaacbbkkkkkkkkkkaaaaaaanaaaaahaaaaaallllkkkaaaacbbbbbdaaaaaaaeejcbbdafaaaaaaaaaaaaaanacdaaaaaaaaaaaaaaleejcbbbdaafaaaaaaaaaaaaaaaaaeeeaaaaaaaaaaaaaaacbbdjcbcbbbbdaaaaaaaaaaaaaaeeeeeebbkkaakeejameeaaaaaaaaakkkkkkkkkeedaaaacbdaaaaaaceejeeeeeaaaaaaaaaaaaaaaaaaaceeeaaaaaaaaaaaaaaaaaaajeeeeeeeedaaanaaaaaaaaaaeeeeeeeaaaaaeejaaeeaaaaaaaaaaaaaaaaaaeeeaaaceeeaaaaaaaaajeeeeaaaaaaaaaanacbdaaacbbbbbdaaaaaaaaaaaaaaaaaajaeeeeeeeeacbbdaaaaaaaaaaeeeeeeaaaaaaebbbeaaaaaaaaanaaaaamlacbbbdaaeeeebdanaaanajeeeaaaaaafkkkkbbeeeaaceeeeeeeaalllaaaaaaaallaaajaaeeeeeeeaeeeeaaaaaaaaaaaeeeeeaaaaaaaeeeaaaaaaaacbbdaaaacdaeeeeeaaeeaneekkkkbbbbeeeaaaaaaaaaaaeeeeeaaeeeeeeeedaaanaaaaaaaallaaajaieeeeeeegeeeeggggggggggggeeeegggggggggggggggggceeeeggggeegeeeeeggeebbeeggggeeeeeeggggggggggggeeeeeggeeeeeeeeebbbdggggggggcdggcbbb|2.6.128.10",
							"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaafaaaaaaaaacdaaaaaaaaaaaaaaaaalleeeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaafaaaaaaaaaeeaaaaaaaaaaaaaaaaalmeeeeeaaaaaaaaaaaaaaaaaaaaaaallllaaaaaeaaaaaaaaaaaaaaaaaaaaaaaaaacdaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaafaaaaaaaaceeaaaaaaaaaaaaaaaaacbeeeelaaaaaaaaaaaaaaaaaaaaaaalllmaaaaaeaaaaaaaaaaaaaaaaaaaaaaaaaaeejcbdaaaaaaaaaaaaaaaaaaaaaaaaaaafaaaaaaaaeeeaaanaaaaaaaanaaaaaaeeeeaaaaaaacdaaaaaaaaaaaaanaaaaananaaealcdaaaaaaaaaaaaaaaaaaaaaaaljeeaacbbdaaaaaacbdaaaaaaaaaaaaafaaaaaaaceeeaakkkaaaaakkkkaaaaaeeelacdaaaaeelaaaaaaaaaaakkkkkkkkkbaaeaceeaaaaaaaaaacbbbaahaaaaaaljeaaaaeeedaaaaaeeeaaaaaaaaaaaaaaaaaaaaceeeeaaaaaalllaaaaaaacdaeeeaaeeaaakeelaaaaaaaaaaaaaaaaaaaaeaaeaaeekakkkkkbbbeaaacbbbbdaaaljaaaaaaeeekaakbeeaaaaaaaaaaaaflllfaaaaeeeelaaaaaaanaaaaaaaaeeaeelaceeaaaaeelaaaaaacdacdaaaaaaaaaeaaelaeeaaaaaaceeeeaiaeeeeeebdaljaaaaaaaaaaaaaaaaaaaaaaaaaaaafanafaaaaeeeelaaaakkkkkkkaaaaceeaeaaaeeeaaaaeedaaaaaceeaeeaaaaaaaaaeaaedaeeaaaaaaeeeeebbbeeeeeeeeanjaaaaaaaaaaaaalmlaaaaaaaaaafafkfkfafkbbbbbdaaaaaaaaaaaaaaaeeeaaaaceeeaaaaeeeaanaaeeeaeeaaaaaaaaaeaaaaaeeaaaaaaaaaaaaaaeeeeeeeebbdaaaaaaaaaaaaaanaaaaacbbbdafafafafafaeeeeeekkkaaaaaaaaaaaaeeeaanaeeeeaaaaeeekkkkkeeemeeaaaaaaaaaeanaaleelaaalaanlaaalaeeeeeeeeeeegggggggggggggcbdggcbeeeeggfgfgfgfgfgeeeeeegggggggggggggggeeekkkbeeeeggggeeegggggeeegeegggggggggebbbbbeebbbbbbbbbbbbbb|3.6.127.7",
							"aaaaaaaaaaaaaacbbbbbbbbdaaaaaaaaaaaaaafffffffaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaacbdaaaaaaaaaaallaaaaaaaaaaaaaaaaeeeeeeeeaaaaaaaaaaaaaaaafaaaaafaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaceeeaaaaaaaaaiallaaaahaaaaaaaaaaaeeeeeeeaaaaaaaacdjcdaaaafaaaaafaalllaaaalaaaaaaaaaaaaaamaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaeeelaaaaaaaacbbbdaabbbbbbbdaaaaaaaaaaaaaaaaaaaaaeejeeaaaafaaflafaaaaanaaalaaaaaaaaaaaaaakaaaaaaaaaaaaaaaaaaaaaaaanfafaaaaaaaaaaaeeelaaaaaaaaeeeeeaaeeeeeeeeaaaaaaaaaaaaaacdaaaaaaajeedaaafafffafkkkkkkkaakalaalaaaaaaaaaaakkfkkaafffaacdaaaaaaaaaffaffaaaaaaaaacbbbdaaaaaaaaeeeeeaaeeeeeeeeaaaaaaaaaaaaaceeaaaaaaajeeebdafaaflafaaaaaaaaaaalaalaaaaaaaaaaaaafaaaafmlaaeeaaaaaaanfffafffnaaaaacbeeeeebdaaaaacbbbbbdaeellleeekkaaaaanaaaaceeeaaaaaaajeeeeeafaafmafaaaaaaaaaaakaakaacbdaaaaaaalfaaaafllaaeeaallaaaffffgffffaaaaaaeeeeeeeaaaaaaeeeeeeeaeelmlaaaaaaakkbbggbbeeeeaaaaanajeeeeeafafffafkkaaaaaaaaaaaaaaleeeaaaaaaakfaaaafffaaeedaanaafffffffffffnaaaaaeeeeeaaaaaaaeeeeeeeaeelllanaaaaaaaeeeeeeeeeeaaakkkkjeeeeeaaaaflaaaaaaaaaaaaaaaaaaleeeaaaaaaaafaaaaaafaaaeebbbbfffffffffffffkakaaaaaaaaaallacbbbbbbbdeebbbbbbbdaaaaeeeeeeeeeeaaaaaaaaaeeeeaaaaflanacdaaaaaaaaaaaaaceeeanaanaaafaaaaaafaaaaeeeeffffffffffffffaaaaaaaaaaaaaanaeeeeeeeeeeeeeeeeeeeggggeeeeeeeeeegggggggggeeeebbbbbbbbbeegggggggggggggeeeebkkkkkkkfggggggfgggggggfffffffffffffffggggggggggggkkkkeeeeeeeee|3.3.123.2",
							"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaamaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaalaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaalllaaaaaaaaaaaacbdlaaaaaaaalaaaaaaaaaaaaaaaaaaaaaaaakkkkffaaaaaaaaaaaanaaaaanaaaaaaaaaaaaaajaaaaaaaaaaanaaaaaaaaaaaaaaaaaaaaaaaanaaaaaaaaaaaaaaeeedaaaalaaaaaaaaaaaaaaaaaaaaaajcbbdaaaaaaaaaaaaaaaaakkkkkkkkkkaaaaaaalcdaaajjaaaaaaaacbbdaaaaaalalaaaaaaaaaaaalakkkalaaaaaaaaaaeeeeaaaaanakkkaaaaaaaaaaaaaaaaajaaaedaaaalaaaaaaaaaaaaaaaaaaaaaaaaaaamceeacdjjjjjaaaaaeeeedaaaalfaflaaaaaaaaaaanaaaaaanaaaaaaajcbbbbdakkkkkaaaaaaaaaaaaaaaaaaaajaaaaeaaaaffaaaaaaaaaaaaaaaaaaaakkaaaaleeeaeejjjjjjaaaaeeeeeaaanffaffnaaaaaaalafffaaafffalaaaaajeeeeeaaaaaaaaaaaanaaaaaaaaaaaaaajaiaceaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaalaaaaaeeajjjjjjaacbbbbbdalfffaffflaaaaanaaaaaaaaaaaaaaaaaajaeeeaaaaaaaaaaafafafffaaaaaaaaaabbbbeeaaaaalaaaaaaaanaaaaaallaaaaaaalaaaaaaeeaajjjjjjjaalleeeaffffaffffaaakkfffaaaaaaaaafffaalljaaaaaaaaaaaaaaafgfaafaaaaaaaaahaeeeeeaaaaaffkkkkkakkkkaaaaallanacdanlaaaaaaeaaaaaaajjjaalmeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaalljaaaaaaaaaaaaaaafffaafaaaancbbbbbeeeeaaaaaaaaaaaaaaaaaaaaaaacbbbbeebbbbbbbbbeaaaaaaaajjaacbeeeaaaaaaaaaaaaaaaaaaaaaaaaaaaaaakkkkkaaaaaaaaaaaaaaafafaafaacbbeeeeeeeeeggggggggggggggggggggggggeeeeeeeeeeeeeeeeekkkkkkkkkkbbeeeeeggggggggggggggggggggggggggggggggggggggggggggggggggfgfgfffgeeeeeeeee|127.8.3.7"
							};
	
	int spawnX = -1;
	int spawnY = -1;
	int endX = -1;
	int endY = -1;
	
	int tileID = 1;
	
	int mapMaxX = 128;
	int mapViewX = 0;
	int[] map = new int[11 << 7];
	double[] monstersX;
	int[] monstersY;
	int[] monstersDir;
	int[] monstersAnim;
	boolean[] solid = { false, true, true, true, true, true, true, false, false, false, true, false, false, false };
	
	int score = 0;
	int health = 3;
	int coins = 0;
	
	int fps = 0;
	
	int scoreHudY[] = new int[100];
	int scoreHudText[] = new int[100];
	int scoring = 0;
	
	double mapTimer = 0;
	
	double plyX = 0;
	double plyY = 0;
	double plyVelX = 0;
	double plyVelY = 0;
	
	int plyAnim = 0;
	int plyDir = 13;
	
	long timeStart = 0;
	
	int keyA = 0;
	int keyD = 0;
	int keySpace = 0;
	int keyW = 0;
	
	int mouseX = 0;
	int mouseY = 0;
	int mouseXGrid = 0;
	int mouseYGrid = 0;
	
	int menuID = 0;
	int buttonID = 0;
	
	int mapID = 0;
	
	double scoreMenuStage = 0;
	long scoreMenuTimer = 0;
	
	public void plyMove(double timePassed){
		double startX = plyX;
		mapTimer += timePassed;
		if (keyA != 0){
			plyVelX = -5;
		}
		if (keyD != 0){
			plyVelX = 5;
		}
		
		plyX += plyVelX * timePassed;
		int num = map[(int) ((Math.round(Math.ceil(plyY)) << 7) + Math.round(plyX))];
		if (isSolid(plyX, plyY) && (num < 1 || num > 3)){
			plyX -= plyVelX * timePassed;
			plyVelX = 0;
		}
		plyY += plyVelY * timePassed;
		if (isSolid(plyX, plyY) && plyVelY > 0.0){
			plyY -= plyVelY * timePassed;
			if (isSolid(plyX, plyY) && (num < 1 || num > 3)){
				plyVelY += timePassed * 20;
				plyY += plyVelY * timePassed;
			}
			else if (num > 0 && num < 4){
				plyY = Math.ceil(plyY);
				plyVelY = 0;
			}
			else {
				plyVelY = 0;
			}
			if (keySpace != 0 && plyVelY >= 0.0 && plyVelY < 0.3 && (isSolid(plyX, plyY + 1) || isSolid(plyX, plyY))){
				plyVelY = -12;
			}
		}
		else if (map[(int) ((Math.round(Math.ceil(plyY)) << 7) + Math.round(plyX))] != 9 || map[(int) ((Math.round(Math.ceil(plyY - 1)) << 7) + Math.round(plyX))] != 9 || map[(int) ((Math.round(Math.ceil(plyY - 2)) << 7) + Math.round(plyX))] != 9){
			plyVelY += timePassed * 20;
		}
		if (keyW != 0){
			if (map[(int) ((Math.round(Math.ceil(plyY)) << 7) + Math.round(plyX))] == 9 || map[(int) ((Math.round(Math.ceil(plyY - 1)) << 7) + Math.round(plyX))] == 9 || map[(int) ((Math.round(Math.ceil(plyY - 2)) << 7) + Math.round(plyX))] == 9){
				plyY -= 10 * timePassed;
			}
		}
		if (map[(int) ((Math.round(Math.ceil(plyY - 1)) << 7) + Math.round(plyX))] == 11){
			map[(int) ((Math.round(Math.ceil(plyY - 1)) << 7) + Math.round(plyX))] = 0;
			coins++;
		}
		if (map[(int) ((Math.round(Math.ceil(plyY - 2)) << 7) + Math.round(plyX))] == 11){
			map[(int) ((Math.round(Math.ceil(plyY - 2)) << 7) + Math.round(plyX))] = 0;
			coins++;
		}
		if (map[(int) ((Math.round(Math.ceil(plyY - 1)) << 7) + Math.round(plyX))] == 12){
			map[(int) ((Math.round(Math.ceil(plyY - 1)) << 7) + Math.round(plyX))] = 0;
			health++;
		}
		if (map[(int) ((Math.round(Math.ceil(plyY - 2)) << 7) + Math.round(plyX))] == 12){
			map[(int) ((Math.round(Math.ceil(plyY - 2)) << 7) + Math.round(plyX))] = 0;
			health++;
		}
		if (health > 3){
			health = 3;
		}
		for (int i = 0; i < monstersX.length; i++){
			if (Math.abs((plyX + 0.5) - monstersX[i]) < 0.5 && Math.abs((plyY - 1) - monstersY[i]) < 1){
				if (plyVelY > 0.7){
					monstersX[i] = 0;
					monstersY[i] = 0;
					monstersDir[i] = 9;
					score(200);
				}
				else {
					health--;
					plyX = spawnX;
					plyY = spawnY;
					score(-500);
				}
			}
		}
		if (num == 6 && plyVelY > -0.5){
			health--;
			plyX = spawnX;
			plyY = spawnY;
			score(-500);
		}
		if (plyX < 0){
			plyX = 0;
		}
		else if (plyX > mapMaxX - 1){
			plyX = mapMaxX - 1;
		}
		plyVelX = 0;
		if (plyY < 2){
			plyY = 2;
			plyVelY = 0;
		}
		else if (plyY > 11){
			plyY = 11;
			plyVelY = 0;
		}
		timeStart = System.currentTimeMillis();
		if (map[(int) ((Math.round(Math.ceil(plyY)) << 7) + Math.round(plyX))] == 8){
			menuID = 5;
		}
		else if (map[(int) ((Math.round(Math.ceil(plyY - 1)) << 7) + Math.round(plyX))] == 8){
			menuID = 5;
		}
		if (health < 1){
			menuID = 6;
		}
		double endX = plyX;
		if (isSolid(plyX, plyY + 1) || isSolid(plyX, plyY)){
			if (startX < endX){
				plyAnim += (timePassed * 3000);
				if (plyAnim > 200 && plyDir < 6){
					plyAnim = 0;
					plyDir++;
					plyDir %= 6;
				}
				else if (plyAnim > 200 && plyDir < 12){
					plyAnim = 0;
					plyDir = 0;
				}
				else if (plyAnim > 200) {
					plyAnim = 0;
					plyDir = 0;
				}
			}
			else if (startX > endX){
				plyAnim += (timePassed * 3000);
				if (plyAnim > 200 && plyDir < 6){
					plyAnim = 0;
					plyDir = 6;
				}
				else if (plyAnim > 200 && plyDir < 12){
					plyAnim = 0;
					plyDir++;
					plyDir %= 6;
					plyDir += 6;
				}
				else if (plyAnim > 200) {
					plyAnim = 0;
					plyDir = 6;
				}
			}
			else {
				if (plyDir < 6){
					plyDir = 12;
				}
				else if (plyDir < 12){
					plyDir = 13;
				}
				if (plyAnim > 200){
					plyAnim = 0;
				}
			}
		}
		else {
			if (startX < endX){
				plyDir = 0;
			}
			else if (startX > endX){
				plyDir = 6;
			}
		}
	}
	
	public void monstersMove(double timePassed){
		for (int i = 0; i < monstersX.length; i++){
			if (!(monstersX[i] == 0 && monstersY[i] == 0)){
				monstersAnim[i] += (timePassed * 1000);
				if (monstersAnim[i] > 200){
					monstersAnim[i] -= 200;
					if (monstersDir[i] < 4){
						monstersDir[i]++;
						monstersDir[i] %= 4;
					}
					else if (monstersDir[i] < 8){
						monstersDir[i]++;
						monstersDir[i] %= 4;
						monstersDir[i] += 4;
					}
				}
				if (monstersDir[i] < 4){
					monstersX[i] -= 2.0 * timePassed;
					if (monstersX[i] < 0){
						monstersX[i] = 0;
						monstersDir[i] = 4;
						monstersX[i] += 2.0 * timePassed;
					}
					else if (solid[map[(int) ((monstersY[i] << 7) + (monstersX[i] - 0.4))]] || solid[map[(int) ((monstersY[i] << 7) + (monstersX[i] + 0.4))]] || !solid[map[(int) (((monstersY[i] + 1) << 7) + monstersX[i])]] || map[(int) (((monstersY[i] + 1) << 7) + monstersX[i])] == 6){
						monstersDir[i] = 4;
						monstersX[i] += 2.0 * timePassed;
					}
				}
				else if (monstersDir[i] < 8){
					monstersX[i] += 2.0 * timePassed;
					if (monstersX[i] > mapMaxX){
						monstersX[i] = mapMaxX;
						monstersDir[i] = 4;
						monstersX[i] -= 2.0 * timePassed;
					}
					else if (solid[map[(int) ((monstersY[i] << 7) + (monstersX[i] - 0.4))]] || solid[map[(int) ((monstersY[i] << 7) + (monstersX[i] + 0.4))]] || !solid[map[(int) (((monstersY[i] + 1) << 7) + monstersX[i])]] || map[(int) (((monstersY[i] + 1) << 7) + monstersX[i])] == 6){
						monstersDir[i] = 0;
						monstersX[i] -= 2.0 * timePassed;
					}
				}
			}
		}
	}
	
	public void score(int scores){
		score += scores;
		scoreHudY[scoring] = 0;
		scoreHudText[scoring] = scores;
		scoring++;
	}
	
	public boolean isSolid(double x, double y){
		double yy = Math.ceil(y - 1);
		if (yy < 0){
			yy = 0;
		}
		return solid[map[(int) ((yy * 128) + Math.round(x))]];
	}
	
	public void paint(Graphics g) {
		g2.clearRect(0, 0, sizeX, sizeY);
		g2.drawImage(background, 25, 25, this);
		if (menuID == 0) {
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Neuropol",Font.BOLD,48));
			g2.drawString("Platformer",50,80);
			
			g2.fillRoundRect(50, 150, 300, 50, 20, 20);
			g2.fillRoundRect(50, 250, 300, 50, 20, 20);
			g2.fillRoundRect(50, 350, 300, 50, 20, 20);
			g2.fillRoundRect(50, 450, 300, 50, 20, 20);
			
			g2.setFont(new Font("Neuropol",Font.BOLD,24));
			g2.drawString("by Runeneo",480,550);
			g2.setColor(Color.WHITE);
			g2.drawString("Play Game", 60, 182);
			g2.drawString("Create Level", 60, 282);
			g2.drawString("Help", 60, 382);
			g2.drawString("Exit", 60, 482);
			
			buttonID = 0;
			if (mouseX >= 50 && mouseX <= 350){
				if (mouseY >= 175 && mouseY <= 225){
					buttonID = 1;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(50, 150, 300, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Play Game", 60, 182);
				}
				else if (mouseY >= 275 && mouseY <= 325){
					buttonID = 2;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(50, 250, 300, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Create Level", 60, 282);
				}
				else if (mouseY >= 375 && mouseY <= 425){
					buttonID = 3;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(50, 350, 300, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Help", 60, 382);
				}
				else if (mouseY >= 475 && mouseY <= 525){
					buttonID = 4;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(50, 450, 300, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Exit", 60, 482);
				}
			}
		}
		else if (menuID == 1){
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Neuropol",Font.BOLD,48));
			g2.drawString("Play Game",50,80);
			
			g2.fillRoundRect(50, 150, 300, 50, 20, 20);
			g2.fillRoundRect(50, 250, 300, 50, 20, 20);
			g2.fillRoundRect(50, 450, 300, 50, 20, 20);
			
			g2.setFont(new Font("Neuropol",Font.BOLD,24));
			g2.setColor(Color.WHITE);
			g2.drawString("Play Default Maps", 60, 182);
			g2.drawString("Play Custom Map", 60, 282);
			g2.drawString("Back to menu", 60, 482);
			
			buttonID = 0;
			if (mouseX >= 50 && mouseX <= 350){
				if (mouseY >= 175 && mouseY <= 225){
					buttonID = 1;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(50, 150, 300, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Play Default Maps", 60, 182);
				}
				else if (mouseY >= 275 && mouseY <= 325){
					buttonID = 2;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(50, 250, 300, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Play Custom Map", 60, 282);
				}
				else if (mouseY >= 475 && mouseY <= 525){
					buttonID = 3;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(50, 450, 300, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Back to menu", 60, 482);
				}
			}
		}
		else if (menuID == 2) {
			g2.drawImage(background, 25, 25, this);
			
			g2.setColor(new Color(100,100,100));
			g2.fillRect(700, 0, 150, 600);
			
			for (int i = 0; i < tiles.length; i++) {
				g2.drawImage(tiles[i], 725 + ((i % 2) * 50), 25 + ((int) Math.floor(i / 2) * 50), this);
			}
			
			for (int y = 0; y < 11; y++){
				for (int x = 1; x < 14; x++){
					g2.drawImage(tiles[map[y << 7 + ((x - 1)+ mapViewX)]], x * 50 - 25, (y + 1) * 50 - 25, this);
				}
			}
			
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Arial",Font.BOLD,14));
			g2.drawString("Mouse: (" + mouseXGrid + "," + mouseYGrid + ") | Across: " + mapViewX + "/" + (mapMaxX-13), 30, 40);
			g2.drawString("Spawn: " + "(" + spawnX + "," + spawnY + ")", 30, 60);
			g2.drawString("End: (" + endX + "," + endY + ")", 30, 80);
			g2.setFont(new Font("Neuropol",Font.BOLD,24));
			g2.setColor(Color.WHITE);
			g2.drawString("Load", 737, 458);
			g2.drawString("Save", 737, 508);
			g2.drawString("<", 743, 558);
			g2.drawString(">", 793, 558);
			g2.setFont(new Font("Neuropol",Font.BOLD,20));
			g2.drawString("Click here to exit map creation mode.", 130, 593);
			
			g2.setColor(Color.WHITE);
			mouseXGrid = Math.round((mouseX + 25)/50);
			mouseYGrid = Math.round(mouseY/50);
			if (mouseXGrid > 0 && mouseXGrid < 14 && mouseYGrid > 0 && mouseYGrid < 12){
				g2.drawImage(tiles[tileID], mouseXGrid * 50 - 25, mouseYGrid * 50 - 25, this);
			}
			g2.drawRect(mouseXGrid * 50 - 25, mouseYGrid * 50 - 25, 50, 50);
		}
		else if (menuID == 3){
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Neuropol",Font.BOLD,48));
			g2.drawString("Help",50,80);
			
			g2.setFont(new Font("Neuropol",Font.BOLD,24));
			
			g2.setColor(Color.RED);
			g2.drawString("Left:", 50, 130);
			g2.drawString("Right:", 50, 160);
			g2.drawString("Climb:", 50, 190);
			g2.drawString("Jump:", 50, 220);
			g2.setColor(Color.BLACK);
			g2.drawString("A / Left Arrow", 150, 130);
			g2.drawString("D / Right Arrow", 150, 160);
			g2.drawString("W / Up Arrow", 150, 190);
			g2.drawString("Space", 150, 220);
			
			g2.drawImage(monsters[0], 50, 240, this);
			g2.drawString("Jumping on a monster will kill it.", 110, 290);
			g2.drawImage(tiles[6], 50, 280, this);
			g2.drawString("Spikes are bad. Avoid them.", 110, 330);
			g2.drawImage(tiles[11], 50, 330, this);
			g2.drawString("Collect coins to boost your score.", 110, 370);
			g2.drawImage(tiles[12], 50, 370, this);
			g2.drawString("Collect hearts to restore your health.", 110, 410);
			
			g2.setColor(Color.BLACK);
			g2.fillRoundRect(50, 450, 300, 50, 20, 20);
			g2.setColor(Color.WHITE);
			g2.drawString("Back to menu", 60, 482);
			
			buttonID = 0;
			if (mouseX >= 50 && mouseX <= 350 && mouseY >= 475 && mouseY <= 525){
				buttonID = 1;
				g2.setColor(Color.WHITE);
				g2.fillRoundRect(50, 450, 300, 50, 20, 20);
				g2.setColor(Color.BLACK);
				g2.drawString("Back to menu", 60, 482);
			}
		}
		else if (menuID == 4){
			g2.drawImage(background, 25, 25, this);
			
			int worldX = (int) Math.floor(plyX - 6);
			if (worldX < 0){
				worldX = 0;
			}
			else if (worldX > mapMaxX - 13){
				worldX = mapMaxX - 13;
			}
			
			int xs = (int) Math.round(50 * (Math.floor(plyX) - plyX));
			if (plyX >= 6 && plyX <= mapMaxX - 7){
				for (int y = 0; y < 11; y++){
					for (int x = 1; x < 15; x++){
						g2.drawImage(tiles2[map[y * 128 + ((x - 1) + (int) Math.round(worldX))]], (int) (((x * 50) - 25) + xs), (y + 1) * 50 - 25, this);
					}
				}
			}
			else {
				for (int y = 0; y < 11; y++){
					for (int x = 1; x < 14; x++){
						g2.drawImage(tiles2[map[y * 128 + ((x - 1) + (int) Math.round(worldX))]], (int) (x * 50) - 25, (y + 1) * 50 - 25, this);
					}
				}
			}
			
			for (int i = 0; i < monstersX.length; i++){
				if (!(monstersX[i] == 0 && monstersY[i] == 0)){
					if (monstersDir[i] < 8 && plyX >= 6 && plyX <= mapMaxX - 7){
						g2.drawImage(monsters[monstersDir[i]], (int) Math.round(((1 - (worldX - monstersX[i])) * 50) - 50) + xs, (int) Math.round((monstersY[i] + 0.5) * 50), this);
					}
					else if (monstersDir[i] < 8){
						g2.drawImage(monsters[monstersDir[i]], (int) Math.round(((1 - (worldX - monstersX[i])) * 50) - 50), (int) Math.round((monstersY[i] + 0.5) * 50), this);
					}
				}
			}
			
			int drawX = 300;
			if (plyX < 6){
				drawX = (int) Math.round(plyX * 50);
			}
			else if (plyX > mapMaxX - 7){
				drawX = (int) Math.round((13 - (mapMaxX - plyX)) * 50);
			}
			int drawY = (int) Math.round((plyY - 2) * 50);
			
			g2.drawImage(player[plyDir], 25 + drawX,  25 + drawY, this);
			
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, 25, sizeY);
			g2.fillRect(675, 0, 25, sizeY);
			
			g2.fillRect(700, 0, 150, 600);
			
			double n = (System.currentTimeMillis() - timeStart) / 1000.0;
			if (n < 1000000){
				plyMove(n);
				monstersMove(n);
			}
			else {
				timeStart = System.currentTimeMillis();
			}
			
			for (int i = 0; i < scoring; i++){
				g2.setColor(new Color(255, 0, 0, 200 - (scoreHudY[i] * 2)));
				g2.drawString("" + scoreHudText[i], 25 + drawX, (drawY + 25) - scoreHudY[i]);
				scoreHudY[i]++;
				if (scoreHudY[i] > 100){
					for (int p = i; p < scoring; p++){
						scoreHudY[p] = scoreHudY[p + 1];
						scoreHudText[p] = scoreHudText[p + 1];
					}
					scoring--;
				}
			}
			
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Neuropol",Font.BOLD,18));
			for (int i = 0; i < health; i++){
				g2.drawImage(tiles2[12], 740 + (50 * (i - 1)), 50, this);
			}
			g2.drawString("Score: " + score, 705, 150);
			int mins = (int) Math.round(Math.floor(mapTimer / 60));
			int secs = (int) mapTimer % 60;
			g2.drawString("Time: " + mins + "m " + secs + "s", 705, 200);
			g2.drawString("Coins: " + coins, 705, 250);
			
			g2.setColor(Color.BLACK);
		}
		else if (menuID == 5){
			g2.drawImage(background, 25, 25, this);
			g2.setFont(new Font("Neuropol",Font.BOLD,24));
			if (scoreMenuStage > 255){
				for (int i = 0; i < 255; i++){
					g2.setColor(new Color(0, 0, i));
					g2.drawLine(100, 150 + i, 600, 150 + i);
				}
			}
			else {
				for (int i = 0; i < scoreMenuStage; i++){
					g2.setColor(new Color(0, 0, i));
					g2.drawLine(100, 150 + i, 600, 150 + i);
				}
			}
			if (scoreMenuStage > 50){
				if (mapID != 1337){
					g2.drawString("Scores (Map "+mapID+")", 120, 180);
				}
				else {
					g2.drawString("Scores (Custom Map)", 120, 180);
				}
			}
			if (scoreMenuStage > 100){
				g2.setColor(new Color(g2.getColor().getBlue() - 50, g2.getColor().getBlue() - 50, g2.getColor().getBlue() - 50));
				g2.drawString("Score: " + score, 120, 230);
			}
			if (scoreMenuStage > 150){
				int mins = (int) Math.round(Math.floor(mapTimer / 60));
				int secs = (int) mapTimer % 60;
				g2.drawString("Time: " + mins + "m " + secs + "s", 120, 280);
			}
			if (scoreMenuStage > 200){
				g2.drawString("Coins: " + coins, 120, 330);
			}
			if (scoreMenuStage > 250){
				int timeScore = (int) Math.round(100 - mapTimer);
				if (timeScore < 0){
					timeScore = 0;
				}
				int totScore = score + timeScore + (coins * 25);
				double displayTime = (600 - (600 - scoreMenuStage)) / 600;
				if (displayTime > 1){
					displayTime = 1;
				}
				int displayScore = (int) Math.round(totScore * displayTime);
				g2.setColor(new Color(0, (int) Math.round(255 * displayTime), 0));
				g2.drawString("Total: " + displayScore, 120, 380);
			}
			
			if (scoreMenuTimer != 0 && scoreMenuStage < 599 && scoreMenuStage != 0){
				scoreMenuStage += (System.currentTimeMillis() - scoreMenuTimer) / 3;
			}
			else if (scoreMenuStage > 598){
				g2.setColor(Color.BLACK);
				g2.drawString("Click anywhere to continue...", 145, 450);
			}
			else if (scoreMenuTimer != 0 && scoreMenuStage == 0){
				scoreMenuStage = 1;
			}
			scoreMenuTimer = System.currentTimeMillis();
		}
		else if (menuID == 6){
			g2.drawImage(background, 25, 25, this);
			g2.setFont(new Font("Neuropol", Font.BOLD, 72));
			g2.setColor(Color.RED);
			g2.drawString("You died!", 145, 250);
			g2.setFont(new Font("Neuropol", Font.BOLD, 24));
			g2.setColor(Color.BLACK);
			g2.drawString("Click anywhere to continue...", 145, 450);
		}
		else if (menuID == 7){
			g2.drawImage(background, 25, 25, this);
			g2.setFont(new Font("Neuropol", Font.BOLD, 48));
			g2.setColor(Color.BLACK);
			g2.drawString("Select a map", 50, 80);
			
			g2.fillRoundRect(70, 140, 170, 50, 20, 20);
			g2.fillRoundRect(70, 215, 170, 50, 20, 20);
			g2.fillRoundRect(70, 290, 170, 50, 20, 20);
			g2.fillRoundRect(70, 365, 170, 50, 20, 20);
			g2.fillRoundRect(70, 490, 230, 50, 20, 20);
			
			g2.setFont(new Font("Neuropol", Font.BOLD, 24));
			g2.setColor(Color.WHITE);
			g2.drawString("Map 1", 80, 170);
			g2.drawString("Map 2", 80, 245);
			g2.drawString("Map 3", 80, 320);
			g2.drawString("Map 4", 80, 395);
			g2.drawString("Back to menu", 80, 520);
			
			if (mouseX >= 70 && mouseX <= 240){
				if (mouseY >= 165 && mouseY <= 215){
					buttonID = 1;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(70, 140, 170, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Map 1", 80, 170);
				}
				else if (mouseY >= 240 && mouseY <= 290){
					buttonID = 2;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(70, 215, 170, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Map 2", 80, 245);
				}
				else if (mouseY >= 315 && mouseY <= 365){
					buttonID = 3;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(70, 290, 170, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Map 3", 80, 320);
				}
				else if (mouseY >= 390 && mouseY <= 440){
					buttonID = 4;
					g2.setColor(Color.WHITE);
					g2.fillRoundRect(70, 365, 170, 50, 20, 20);
					g2.setColor(Color.BLACK);
					g2.drawString("Map 4", 80, 395);
				}
			}
			if (mouseX >= 70 && mouseX <= 300 && mouseY >= 515 && mouseY <= 565){
				buttonID = 5;
				g2.setColor(Color.WHITE);
				g2.fillRoundRect(70, 490, 230, 50, 20, 20);
				g2.setColor(Color.BLACK);
				g2.drawString("Back to menu", 80, 520);
			}
		}
		
		g.drawImage(db, 0, 0, this);
		repaint();
	}
	
	public void resetMapVars(){
		spawnX = -1;
		spawnY = -1;
		endX = -1;
		endY = -1;
		
		plyX = 0;
		plyY = 0;
		plyVelX = 0;
		plyVelY = 0;
		
		tileID = 1;
		mapViewX = 0;
		map = new int[11 << 7];
		
		score = 0;
		health = 3;
		coins = 0;
		
		scoreHudY = new int[100];
		scoreHudText = new int[100];
		scoring = 0;
		
		timeStart = 0;
		
		keyA = 0;
		keyD = 0;
		keySpace = 0;
		keyW = 0;
		
		scoreMenuStage = 0;
		mapTimer = 0;
		mapID = 0;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (menuID == 4){
			if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
				keyA = 1;
			}
			else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
				keyD = 1;
			}
			else if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
				keyW = 1;
			}
			else if (key == KeyEvent.VK_SPACE){
				keySpace = 1;
			}
			else if (key == KeyEvent.VK_ESCAPE){
				menuID = 0;
				resetMapVars();
			}
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (menuID == 4){
			if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
				keyA = 0;
			}
			else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
				keyD = 0;
			}
			else if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
				keyW = 0;
			}
			else if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_UP){
				keySpace = 0;
			}
		}
	}
	public void keyTyped(KeyEvent arg0) {}
	
	public void parseClick(){
		if (menuID == 0){
			if (buttonID == 1){
				menuID = 1;
			}
			else if (buttonID == 2){
				menuID = 2;
			}
			else if (buttonID == 3){
				menuID = 3;
			}
			else if (buttonID == 4){
				System.exit(0);
			}
		}
		else if (menuID == 1){
			if (buttonID == 1){
				menuID = 7;
			}
			else if (buttonID == 2){
				if (loadMap(true)) {
					menuID = 4;
				}
			}
			else if (buttonID == 3){
				menuID = 0;
			}
		}
		else if (menuID == 2){
			if (mouseXGrid > 0 && mouseXGrid < 14 && mouseYGrid > 0 && mouseYGrid < 12){
				if (map[(mouseYGrid - 1) << 7 + ((mouseXGrid - 1) + mapViewX)] == 7){
					spawnX = -1;
					spawnY = -1;
				}
				else if (map[(mouseYGrid - 1) << 7 + ((mouseXGrid - 1) + mapViewX)] == 8){
					endX = -1;
					endY = -1;
				}
				if (tileID == 7){
					if (spawnX != -1){
						for (int y = 0; y < 11; y++){
							for (int x = 0; x < mapMaxX; x++){
								if (map[(y << 7) + x] == 7){
									map[(y << 7) + x] = 0;
									break;
								}
							}
						}
					}
					spawnX = mouseXGrid + mapViewX;
					spawnY = mouseYGrid;
				}
				else if (tileID == 8){
					if (endX != -1){
						for (int y = 0; y < 11; y++){
							for (int x = 0; x < mapMaxX; x++){
								if (map[(y << 7) + x] == 8){
									map[(y << 7) + x] = 0;
									break;
								}
							}
						}
					}
					endX = mouseXGrid + mapViewX;
					endY = mouseYGrid;
				}
				map[(mouseYGrid - 1) << 7 + ((mouseXGrid - 1) + mapViewX)] = tileID;
			}
			else if (mouseXGrid == 15 && mouseYGrid == 11){
				if (mapViewX > 0){
					mapViewX--;
				}
			}
			else if (mouseXGrid == 16 && mouseYGrid == 11){
				if (mapViewX < mapMaxX - 13){
					mapViewX++;
				}
			}
			else if (mouseXGrid > 14 && mouseXGrid < 17 && mouseYGrid > 0 && mouseYGrid < 8){
				int x = mouseXGrid - 15;
				int y = mouseYGrid - 1;
				
				tileID = y * 2 + x;
			}
			else if (mouseXGrid > 2 && mouseXGrid < 12 && mouseYGrid == 12){
				int quit = JOptionPane.showConfirmDialog(j, "Quit map creation mode?", "Are you sure?", JOptionPane.YES_NO_OPTION);
				if (quit == 0){
					menuID = 0;
					resetMapVars();
				}
			}
			else if (mouseXGrid > 14 && mouseXGrid < 17 && mouseYGrid > 8 && mouseYGrid < 10){
				loadMap(false);
			}
			else if (mouseXGrid > 14 && mouseXGrid < 17 && mouseYGrid > 9 && mouseYGrid < 11){
				if (spawnX != -1 && endX != -1){
					JFileChooser write = new JFileChooser();
					int fileID = write.showSaveDialog(j);
				
					if (fileID == JFileChooser.APPROVE_OPTION){
						try {
							FileWriter file = new FileWriter(write.getSelectedFile());
							BufferedWriter out = new BufferedWriter(file);
						
							String alpha = "abcdefghijklmnopqrstuvwxyz";
							String toSave = "";
							for (int y = 0; y < 11; y++){
								for (int x = 0; x < mapMaxX; x++){
									toSave += alpha.charAt(map[(y << 7) + x]);
								}
							}
							toSave += "|";
							toSave += spawnX + "." + spawnY + "." + endX + "." + endY;
							out.write(toSave);
							out.close();
						}
						catch (IOException err) {
							System.out.println("IOException error!");
							err.printStackTrace();
						} 
					}
				}
				else {
					if (spawnX == -1 && endY != -1){
						JOptionPane.showMessageDialog(j, "You do not have a spawn!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else if (spawnX == -1 && endY == -1){
						JOptionPane.showMessageDialog(j, "You do not have a spawn or an end!", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(j, "You do not have an end!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		else if (menuID == 3){
			if (buttonID == 1){
				menuID = 0;
			}
		}
		else if (menuID == 5){
			if (scoreMenuStage > 598){
				menuID = 0;
				resetMapVars();
			}
		}
		else if (menuID == 6){
			menuID = 0;
			resetMapVars();
		}
		else if (menuID == 7){
			if (buttonID > 0 && buttonID < 5){
				parseMapData(defaultMaps[buttonID - 1], true);
				mapID = buttonID;
				menuID = 4;
			}
			else if (buttonID == 5){
				menuID = 1;
			}
		}
	}
	
	public boolean loadMap(boolean game){
		JFileChooser load = new JFileChooser();
		int fileID = load.showOpenDialog(j);
		
		if (fileID == JFileChooser.APPROVE_OPTION){
			String record = null;
			try {
				FileReader fr = new FileReader(load.getSelectedFile());
				BufferedReader br = new BufferedReader(fr);
				String str = "";
				while ((record = br.readLine()) != null) {			       		
					str += record;
				}
				parseMapData(str, game);
			}
			catch (IOException e) {
				System.out.println("IOException error!");
				e.printStackTrace();
			}
			return true;
		}
		else {
			return false;
		}
	}
	public void parseMapData(String str, boolean game){
		String s0 = str.substring(0, str.indexOf('|'));
		String s1 = str.substring(str.indexOf('|') + 1);
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < s0.length(); i++){
			map[i] = alpha.indexOf(s0.charAt(i));
		}
		int a = s1.indexOf(".");
		int b = s1.indexOf(".",a + 1);
		int c = s1.indexOf(".",b + 1);
		spawnX = Integer.parseInt(s1.substring(0, a)) - 1;
		spawnY = Integer.parseInt(s1.substring(a + 1, b));
		plyX = spawnX;
		plyY = spawnY;
		endX = Integer.parseInt(s1.substring(b + 1, c));
		endY = Integer.parseInt(s1.substring(c + 1));
		
		if (game){
		int monsterCount = 0;
		for (int i = 0; i < str.length(); i++){
			if (str.charAt(i) == 'n'){
				monsterCount++;
			}
		}
		monstersX = new double[monsterCount];
		monstersY = new int[monsterCount];
		monstersDir = new int[monsterCount];
		monstersAnim = new int[monsterCount];
		
		monsterCount = 0;
		for (int i = 0; i < str.length(); i++){
			if (str.charAt(i) == 'n'){
				monstersX[monsterCount] = (i % mapMaxX) + 0.5;
				monstersY[monsterCount] = (int) Math.round(Math.floor(i / mapMaxX));
				monstersDir[monsterCount] = (int) Math.round(Math.random() * 6);
				monstersAnim[monsterCount] = (int) Math.round(Math.random() * 100);
				monsterCount++;
				map[i] = 0;
			}
		}
		}
		
		mapTimer = 0;
		mapID = 1337;
	}
	
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		parseClick();
	}
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	public void mouseDragged(MouseEvent e) {
		if (menuID == 2){
			mouseX = e.getX();
			mouseY = e.getY();
			parseClick();
		}
	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}

