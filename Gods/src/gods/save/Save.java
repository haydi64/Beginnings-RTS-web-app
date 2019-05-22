package gods.save;
import java.io.*;
import gods.Game.Game;

public class Save {
		public static void save(Game current, String filename) {
			try {
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(current);
			out.close();
			file.close();
			}
			
			catch(IOException ex) 
			{
				System.out.println("IOException is caught");
			}
		}
		public static Game restore(Game newgame, String filename) {
			try {
				FileInputStream file = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(file);
				
				newgame = (Game)in.readObject();
				
				in.close();
				file.close();
				return newgame;
			}
			catch(IOException ex) {
				System.out.println("IO error");
				return null;
				
			}
			catch(ClassNotFoundException ex) 
	        { 
	            System.out.println("ClassNotFoundException is caught");
	            return null;
	        } 
		}
		}

