package basic.day19_IO;

import java.io.FileReader;
import java.io.Reader;

public class MyBufferReader {
	
	private FileReader fr;
	
	public MyBufferReader(Reader r){
		this.fr=(FileReader) r;
	}
	
	//abkfdhkla\r\n
	public String myReadLine() throws Exception{
		StringBuilder sb = new StringBuilder();
		int ch=0;
		while((ch=fr.read())!=-1){
			if(ch=='\r')
				continue;
			if(ch=='\n')
				return sb.toString();
			else
				sb.append((char)ch);
		}
		if(sb.length()>0){//最后一行没有换行的情况
			return sb.toString();
		}
		return null;
	}
}
