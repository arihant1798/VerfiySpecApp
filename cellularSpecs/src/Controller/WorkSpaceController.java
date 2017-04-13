package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JFileChooser;

import ui.utils.FileChooser;
import Model.Screen;
import Model.WorkSpace;
import ToolGUI.*;

public class WorkSpaceController implements ActionListener,MouseListener {
	private int CordinateX; 
	private int CordinateY; 
	NewSpecGUI newSpecGui;
	 MainScreenGui mainScreenGui;
	AddScreenGUI addScreen;
	WorkSpace wk=WorkSpace.getInstance();
	
	private  static boolean clicked;
	
	public WorkSpaceController(NewSpecGUI newSpecGui,MainScreenGui mainSpecGui)
	{
		this.newSpecGui=newSpecGui;
		this.mainScreenGui=mainSpecGui;
			 
	}
	public void OpenSpecFromFile(String fileName){
		
		try (ObjectInputStream ois
			= new ObjectInputStream(new FileInputStream(fileName+".ser"))) {

			WorkSpace.setInstance((WorkSpace) ois.readObject());

		} catch (Exception ex) {
			ex.printStackTrace();
		}


	}
	public void SaveSpecToFile(String fileName){
		
		WorkSpace w= WorkSpace.getInstance();
		
		try (ObjectOutputStream oos =
				new ObjectOutputStream(new FileOutputStream(fileName+".ser"))) {

			oos.writeObject(w);
			System.out.println("Done.. write to file "+fileName+".ser");

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		switch(e.getActionCommand())
		{
		case("Create"):
			String st=null;
			st=newSpecGui.getSpecName().getText().toString();
			wk.setWorkSpaceName(st);
			wk.setIsPressed(true);
			synchronized(wk){
				wk.notify();
			}
		break;
		
		case("AddScreen"):

		System.out.println("AddScreen");

					mainScreenGui.addMainScreenMouseListener((MouseListener)this);
					wk.setIsClicked(true);
        break;
		case("New"):
		break;
		case("Open.."):
			   JFileChooser c = new JFileChooser();
	      // Demonstrate "Open" dialog:
	      int rVal = c.showOpenDialog(null);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	    	  WorkSpace.getLog().info(c.getSelectedFile().getName());
	    	  WorkSpace.getLog().info(c.getCurrentDirectory().toString());
	      }
	      if (rVal == JFileChooser.CANCEL_OPTION) {
	    	  WorkSpace.getLog().info("cancel");
	    	  WorkSpace.getLog().info(c.getCurrentDirectory().toString());
	      }
	      
			OpenSpecFromFile("aaa");
		mainScreenGui.dispose();
		mainScreenGui=new MainScreenGui();
		mainScreenGui.setVisible(true);
			Screen s ;
			Iterator it = WorkSpace.getInstance().getScreensMap().entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pair =(Map.Entry) it.next(); 
				s= (Screen)pair.getValue();			
				
			ScreenGUI screenTempGui=new ScreenGUI(s.getScreenName(),s.getCordinateX(),s.getCordinateY());
			mainScreenGui.getContentPane().add(screenTempGui);
			}
			mainScreenGui.addMainScreenListener(this);
			mainScreenGui.setSpecNameLabel(WorkSpace.getInstance().getWorkSpaceName());
			mainScreenGui.getContentPane().repaint();
			mainScreenGui.getContentPane().revalidate();
		break;
		case("Save SPEC"):
		
			SaveSpecToFile(WorkSpace.getInstance().getWorkSpaceName()); 
		break;
		case("Verifiy SPEC"):
		break;
		case("ShowResults"):
		break;
		case("Save"):
			Screen screen = new Screen();
			screen.setScreenName(addScreen.getScreenName().getText().toString());
			screen.setDescription(addScreen.getDescription().getText().toString());
			screen.setCordinateX(this.CordinateX);
			screen.setCordinateY(this.CordinateY);

			ScreenGUI screenGUI=new ScreenGUI(screen.getScreenName(),screen.getCordinateX(),screen.getCordinateY());//there is a problem
			AddScreenController a=new AddScreenController();
			//screenGUI.addScreenListener(a);
			wk.addScreen(screen.getScreenName(), screen);
			mainScreenGui.setSpecNameLabel(WorkSpace.getInstance().getWorkSpaceName());
			mainScreenGui.getContentPane().add(screenGUI);
			mainScreenGui.getContentPane().repaint();
			mainScreenGui.getContentPane().revalidate();
			addScreen.dispose();
			break;
		case("List"):
		break;
		case("defined/undefined"):
			break;
		case("On/Off"):
			break;
		case("button"):
		break;
		
		}

	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(wk.getisIsClicked()==true)
		{
			/*
			 * save temporary CordinateX &CordinateY of new screen 
			 */
			this.CordinateX=arg0.getX()-9;
			this.CordinateX=arg0.getY()-29;	
			
			/*
			 * remove listener 
			 */
			mainScreenGui.removeMouseListener((MouseListener)this);
			 addScreen=new AddScreenGUI();
			AddScreenController addScreenControlle=new AddScreenController(addScreen,mainScreenGui);
			addScreen.addScreenListener(this);
			addScreen.setVisible(true);
			
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
}
