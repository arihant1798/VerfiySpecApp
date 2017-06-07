package Controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import Model.Element;
import Model.Screen;
import Model.WorkSpace;

public   class formulaTranslate  {
	private static ArrayList<String> ScreenStates=new ArrayList();//fill this araylist in WrokSpace
	private static ArrayList<String> ChangeStates=new ArrayList();//fill this araylist in Screen
	
public static  void translateReq1()
{
	String st="";
	 st="ltl "+" reqid "+"{[]("+getTranslateReq1()+")}";
	 System.out.println(st);
}
private static String getTranslateReq1() {
	String str="";
	for(int i=0;i<ScreenStates.size();i++)
	{
		str=str+"((State=="+ScreenStates.get(i)+")->(";
		if(ChangeStates.size()>0)
		str+="(!([]<>(";
		for(int j=0;j<ChangeStates.size();j++)
		{
		str+="(state=="+ChangeStates.get(j)+ ")||";
		}
		if(ChangeStates.size()>0)
		{
		str=str.substring(0, str.length()-2);
		str+=")))->";
		}
		str=str+"<>((state!="+ScreenStates.get(i)+")";
		for(int j=0;j<ChangeStates.size();j++)
		str+="&&(state!="+ChangeStates.get(j)+")";	
		str+= ")))&&";
	}
	if(ScreenStates.size()>0)
		str=str.substring(0, str.length()-2);
	return str;
}
public static void translateReq2a()
{
	String st="";
	 st="ltl "+" reqid "+"{[](("+getTranslateReq2a()+")}";
	 System.out.println(st);
	 //ltl req2{[]((
	 //((state==screen2)-><>(state==screen3))
	 
	 //&&((state==screen2)-><>(state==screen1)
//	) )}
	
}
private static String getTranslateReq2a() {
	String str="";
	for(int i=0;i<ScreenStates.size();i++)
	{
		for(int j=0;j<ScreenStates.size();j++)
		{
			
			if(j!=i)
			{
				str=str+"((State=="+ScreenStates.get(i)+")->";
				str+="<>(state=="+ScreenStates.get(j)+"))&&";
			}
		}
		if(ScreenStates.size()>1)
		{
			str=str.substring(0, str.length()-2);
			str+="||";
		}
	}
	if(ScreenStates.size()>1)
		str=str.substring(0, str.length()-2);
	str+=")";
	return str;
}
public static  void translateReq2b()
{
	String st="";
	 st="ltl "+" reqid "+"{[]("+getTranslateReq2b()+")}";
	 System.out.println(st);
}
private static String getTranslateReq2b() {
	String str="";
	if(ScreenStates.size()>=2){
	 str="(state="+ScreenStates.get(0)+")->(";// ScreenStates.get(0) is the root 
	for(int i=0;i<ScreenStates.size();i++)
	{
		if(0!=i)
		{
			str=str+"(<>(State=="+ScreenStates.get(i)+"))&&";
		}
	}
	if(ScreenStates.size()>0)
		str=str.substring(0, str.length()-2);
	str+=")";
	}
	return str;
	
}

public static void translateReq7()
{
	String st="";
	 st="ltl "+" reqid "+"{[]("+getTranslateReq7()+")}";
	 System.out.println(st);
}
private static String getTranslateReq7() {
	String st1="";
	String req7="";
	String[] params=ScreenController.getparams();
	for(int i=0;i<params.length;i++)
	{
		String[] values = WorkSpace.getInstance().getParamsMap().get(params[i]).getValues();
		for(int j=0;j<values.length;j++)
		{
			req7+="(("+params[i]+"=="+values[j]+")->(!(X("+getStatesReq7()+"U("+params[i]+"!="+values[j]+")))))&&";
		}
	}
	if(params.length>0)
		req7=req7.substring(0, req7.length()-2);
return req7;
}
private static String getStatesReq7() 
{
	int i=0;
	String st1="";
	String Req7="";
	String[] params=ScreenController.getparams();
	for(int j=0;j<params.length;j++)
	{
		for(i=0;i<ChangeStates.size();i++)
		{
		 if(ChangeStates.get(i).endsWith(params[j]))
			 st1+="("+ChangeStates.get(i)+")||";
		}
	}
	if(params.length>1)
	{
		st1=st1.substring(0, st1.length()-2);
	}
	st1="("+st1+")";
	return st1;
}
public static void addtoChangeStates(String changeStates) {
	ChangeStates.add(changeStates);
}
public static void addtoScreenStates(String screenName) {
	ScreenStates.add(screenName);
}

}
