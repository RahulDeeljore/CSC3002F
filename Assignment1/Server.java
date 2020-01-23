// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.io.*; 
import java.util.*; 
import java.net.*; 

// Server class 
public class Server 
{ 

	// Vector to store active clients 
	static Vector<ClientHandler> ar = new Vector<>(); 
	
	// counter for clients 
	static int i = 0; 

	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 1234 
		ServerSocket ss = new ServerSocket(1234); 
		
		Socket s; 
      String username = "";
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			// Accept the incoming request 
			s = ss.accept(); 

			System.out.println("New client request received : " + s); 
			
			// obtain input and output streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
			
			System.out.println("Creating a new handler for this client...");
         
         //get username of new client
         try {
            username = dis.readUTF();
         } catch(Exception e) {
            System.out.println(e);
         }


			// Create a new handler object for handling this request. 
			ClientHandler mtch = new ClientHandler(s, username, dis, dos, ar); 

			// Create a new Thread with this object. 
			Thread t = new Thread(mtch); 
			
			System.out.println("Adding "+username+" to active client list"); 

			// add this client to active clients list 
			ar.add(mtch); 

			// start the thread. 
			t.start(); 

			// increment i for new client. 
			// i is used for naming only, and can be replaced 
			// by any naming scheme 
			i++; 

		} 
	} 
} 

// ClientHandler class 
class ClientHandler implements Runnable 
{ 
	Scanner scn = new Scanner(System.in); 
	private String name; 
	final DataInputStream dis; 
	final DataOutputStream dos; 
	Socket s; 
   Vector<ClientHandler> clients;
	boolean isloggedin; 
	
	// constructor 
	public ClientHandler(Socket s, String name, 
							DataInputStream dis, DataOutputStream dos, Vector<ClientHandler> clients) { 
		this.dis = dis; 
		this.dos = dos; 
		this.name = name; 
		this.s = s; 
      this.clients = clients;
		this.isloggedin=true; 
	} 
   
   //method to send file to client
   public void sendFile(String filename, Socket rec_socket) {
      try {
         FileInputStream fr = new FileInputStream(filename);
         byte b[]=new byte[(int)filename.length()];
         fr.read(b, 0, b.length);
         OutputStream os = rec_socket.getOutputStream();
         os.write(b, 0, b.length);
      } catch(Exception e) {
         System.out.println(e);
      }
   }
   
   //method to receive file from client
   public void receiveFile(String filename) {
      try {
         byte []b=new byte[20002];
         InputStream is = s.getInputStream();
         FileOutputStream fr = new FileOutputStream(filename);
         is.read(b,0,b.length);
         fr.write(b, 0, b.length);
      } catch(Exception e) {
         System.out.println(e);
      }
   }

	@Override
	public void run() { 
		String received;
      String recipient;
      String recipient_ans; 
		while (true)
      {   
   			try
   			{ 
   				// receive the string 
   				received = dis.readUTF(); 
            } catch(Exception e) {
               System.out.println("Client has disconnected");
               clients.remove(this);
               break;
            }
            
            //if received == null meaning the client has abruptly disconnected
            if (received==null) {
               break;
            }
            
            try {
   				
   				System.out.println(received); 
   				
   				if(received.equals("logout")){ 
   					this.isloggedin=false;
                  
                  try { 
   					   this.s.close();
                  } catch(IOException e) {
                     System.out.println(e); 
                  }
                  
                  clients.remove(this); 
                  //i--;
   					break; 
   				} 
               
               //if received == Y or N, for file transfer
               if (received.equals("Y")) {
                  //System.out.println("Problem x");
                  //System.out.println("Problem y");
                  dos.writeUTF("File has been saved to your directory!");
                  sendFile("temp.txt", s);
                  continue;
               }
               else if (received.equals("N")) {
                  dos.writeUTF("You have declined the file.");
                  continue;
               }
   				
   				// break the string into message and recipient part 
   				StringTokenizer st = new StringTokenizer(received, "#"); 
   				String MsgToSend = st.nextToken(); 
   				recipient = st.nextToken(); 
               
               if(MsgToSend.equals("send file")) {
               
                  System.out.println(MsgToSend);
                  
                  //first receive the file and store it
                  receiveFile("temp.txt");
                  
                  //search for the recipient's socket
                  for (ClientHandler mc : Server.ar) 
      				{ 
      					// if the recipient is found, send the file
      					if (mc.name.equals(recipient) && mc.isloggedin==true) 
      					{ 
                        mc.dos.writeUTF("File coming. Do you want to receive it? Y-yes, N-no");
                        break;
                        
                        //wait for recipient input
                        //recipient_ans = mc.dis.readUTF();
                        //System.out.println(recipient_ans);
                        //mc.dos.writeUTF("Okay cool!!");
                        
                        // if(recipient_ans.equals("Y")) {
//                            sendFile("temp.txt", mc.s);
//                            mc.dos.writeUTF("File has been saved to your directory!");
//                            break;
//                         }
//                         else {
//                            mc.dos.writeUTF("You have declined the file.");
//                            break;
//                         }
      					   // sendFile("temp.txt", mc.s); 
//       						break; 
      					} 
      				} 
                  continue;
               }
                
   				// search for the recipient in the connected devices list. 
   				// ar is the vector storing client of active users 
   				for (ClientHandler mc : Server.ar) 
   				{ 
   					// if the recipient is found, write on its 
   					// output stream 
   					if (mc.name.equals(recipient) && mc.isloggedin==true) 
   					{ 
   						mc.dos.writeUTF(this.name+" : "+MsgToSend); 
   						break; 
   					} 
   				}
             } catch(IOException e) {
                  System.out.println(e);
             }
 
        } 
   			
   		try
   		{ 
   			// closing resources 
   			this.dis.close(); 
   			this.dos.close(); 
   			
   		}catch(IOException e){ 
            
            System.out.println("Problem here 2");
   			//e.printStackTrace(); 
   		} 
   	} 
}
   
 
