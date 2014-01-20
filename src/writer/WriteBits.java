package writer;

import java.io.FileWriter;
import java.io.IOException;

public class WriteBits {

		private FileWriter writer;
		
		public WriteBits(String FileName){
			try {
				writer = new FileWriter(FileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void writeBit(char c){
			try {
				writer.write((int)c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void writeBit(int c){
			try {
				writer.write(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void close(){
			try {
				writer.close();
				writer = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
