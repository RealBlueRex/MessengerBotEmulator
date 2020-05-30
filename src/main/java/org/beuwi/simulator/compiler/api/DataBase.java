package org.beuwi.simulator.compiler.api;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.io.*;

@SuppressWarnings("serial")
public class DataBase  extends ScriptableObject
{
	final String name;

	public DataBase(ScriptableObject object, String name)
	{
		super(object, object.getPrototype());

		this.name = name;
	}

    @Override
    public String getClassName() 
	{
        return "DataBase";
    }

    @JSFunction
	public String setDataBase(String fileName, String data) 
	{
		try
		{
			File file = new File("System_Root/data/data/com.xfl.msgbot/databases/"+fileName);

			file.getParentFile().mkdirs();
			file.createNewFile();

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, false), "UTF8"));
			writer.write(data);
			writer.close();

			return data;
		}
		catch(Exception e)
		{
			Context.reportError(e.toString());
		}

		return null;
	}

    @JSFunction
	public String getDataBase(String fileName) 
	{
		try
		{
			File file = new File("System_Root/data/data/com.xfl.msgbot/databases/"+fileName);
			if (!file.exists())
			{
				return null;
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = "", text = reader.readLine();
			while ((line = reader.readLine()) != null)
			{
				text += "\n" + line;
			}
			reader.close();
			return text;
		}
		catch (Exception e)
		{
			Context.reportError(e.toString());
		}
		return null;
	}

    @JSFunction
	public String appendDataBase(String fileName, String data) 
	{
		try
		{
			File file = new File("System_Root/data/data/com.xfl.msgbot/databases/"+fileName);

			file.getParentFile().mkdirs();
			file.createNewFile();

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF8"));
			writer.write(data);
			writer.close();

			return data;
		}
		catch(Exception e)
		{
			Context.reportError(e.toString());
		}

		return null;
	}
	
    @JSFunction
	public Boolean removeDataBase(String fileName) 
	{
		try
		{
			File file = new File("System_Root/data/data/com.xfl.msgbot/databases/"+fileName);

			if (!file.exists())
			{
				return false;
			}
			return file.delete();
		}
		catch(Exception e)
		{
			Context.reportError(e.toString());
		}
		return null;
	}
}
