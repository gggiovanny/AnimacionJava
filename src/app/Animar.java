package app;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Animar extends JPanel implements Runnable
{
	private ImageIcon[] imagen;
	private ImageIcon imagenActual;
	private ImageIcon fondo;
	private int posX;
	private int posY;
	private int limiteIzquierdo;
	private int limiteDerecho;
	private boolean vaDerecho;
	

	/**
	 * Create the panel.
	 */
	public Animar()
	{
		limiteDerecho = 550;
		limiteIzquierdo = 210;
		posX = limiteIzquierdo;
		posY = 380;
		vaDerecho = true;
		fondo = new ImageIcon(getClass().getResource("/hombre-fondo.jpg"));
		String[] listaImagenes = new String[25];
		
		int c;
		for (int i = 0; i<listaImagenes.length; i++)
		{
			c = i + 1;
			if(c >= 10)
			{
				listaImagenes[i] = "00" + c + ".png";
			}
			else
			{
				listaImagenes[i] = "000" + c + ".png";
			}
		}
		
		imagen = new ImageIcon[listaImagenes.length];
		
		for (int i = 0; i<imagen.length; i++)
			imagen[i] = new ImageIcon((getClass().getResource("/" + listaImagenes[i])));
		
		imagenActual = imagen[0];
		
		Thread hilo = new Thread(this);
		hilo.start();
	}
	
	@Override
	protected void paintComponent(Graphics gg)
	{
		super.paintComponent(gg);
		Graphics2D g2d = (Graphics2D)gg;
		
		
		g2d.drawImage(fondo.getImage(), 0, 0, this);
		if(vaDerecho)
			g2d.drawImage(imagenActual.getImage(), posX, posY, imagenActual.getIconWidth(), imagenActual.getIconHeight(), this);
		else
			g2d.drawImage(imagenActual.getImage(), posX + imagenActual.getIconWidth(), posY, -imagenActual.getIconWidth(), imagenActual.getIconHeight(), this);
//		gg.setColor(Color.RED);
//		gg.drawLine(limiteIzquierdo, posY + imagenActual.getIconHeight(), limiteDerecho, posY + imagenActual.getIconHeight());
	}
	
	@Override
	public void run()
	{
		int delta = 4;
		
		while(true)
		{
			for (int i = 0; i<imagen.length; i++)
			{
				imagenActual = imagen[i];
				repaint();
				try
				{
					Thread.sleep(75);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				
				if(posX >= limiteDerecho - imagenActual.getIconWidth())
					vaDerecho = false;
				if(posX <= limiteIzquierdo)
					vaDerecho = true;
				
				if(vaDerecho)
					posX += delta;
				else
					posX -= delta;
			}
		}
	}

}
