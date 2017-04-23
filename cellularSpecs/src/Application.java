import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import Controller.*;
import Controller.*;
import Model.Screen;
import Model.WorkSpace;
import ToolGUI.MainScreenGui;
import ToolGUI.NewSpecGUI;

public  class Application{
	private static NewSpecGUI newSpecGUI=new NewSpecGUI();
	private static MainScreenGui mainScreenGui;
	private static WorkSpaceController workSpaceController;
	static JFrame mainFram;
	public static void main(String[] args) {
		    WorkSpace workSpace=WorkSpace.getInstance();
			
		mainScreenGui=MainScreenGui.getInstance();
		WorkSpaceController.setInstance(newSpecGUI,mainScreenGui);
		workSpaceController=WorkSpaceController.getInstance();
		
		newSpecGUI.addWorkSpaceListener(workSpaceController);
		newSpecGUI.setVisible(true);
			synchronized(workSpace){
				try {
					workSpace.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	
		newSpecGUI.dispose();
		mainScreenGui.setSpecNameLabel(WorkSpace.getInstance().getWorkSpaceName());
		mainScreenGui.addMainScreenListener(workSpaceController);
		//mainScreenGui.addMainScreenMouseListener(workSpaceController);
		mainScreenGui.setVisible(true);
		
	}

}