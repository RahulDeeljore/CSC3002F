// Java implementation for multithreaded chat client 
// Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

public class Client 
{ 
	final static int ServerPort = 1234; 
   static boolean loggedIn=true;
   public static String username;
   public static String msg;


	public static void main(String args[]) throws UnknownHostException, IOException 
	{ 
		//Scanner scn = new Scanner(System.in); 		
		final Scanner scn = new Scanner(System.in); 
       Scanner scn2 = new Scanner(System.in);
		
		// getting localhost ip 
		InetAddress ip = InetAddress.getByName("localhost"); 
      //InetAddress ip = InetAddress.get196.42.73.93;
		
		// establish the connection 
		Socket s = new Socket(ip, ServerPort); 
      
      //when user logs in, ask for user name
      System.out.println("Hi. Enter your name please: ");
      username = scn.nextLine();
      System.out.println("You have joined the chat. Send messages to others using format 'message#recipient'");
      System.out.println("Type 'send file' to send a file, 'logout' to logout and exit");
      System.out.println("");
		
		// obtaining input and out streams 
		final DataInputStream dis = new DataInputStream(s.getInputStream()); 
		final DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
      
       //write name to server socket 
      try {
         dos.writeUTF(username);
      } catch(IOException e) {
         System.out.println(e);
      }

		// sendMessage thread 
		Thread sendMessage = new Thread(new Runnable() 
		{ 
      
         //send file method
         public void sendFile(String filename, Socket soc) {
            File myFile = new File(filename);
            try {
               //System.out.println("checkpoint A");
               FileInputStream fr = new FileInputStream(myFile);
               byte b[]=new byte[(int)myFile.length()];
               fr.read(b, 0, b.length);
               dos.write(b, 0, b.length);
               dos.flush();
               //System.out.println("checkpoint B");
            } catch(Exception e) {
               System.out.println(e);
            }

         }
         
         
			@Override
			public void run() { 
            
            String recipient;
            String file;
            
            //run an infinite loop waiting for client messages
				while (true) { 
					// read the message to deliver. 
					msg = scn.nextLine(); 
					
					try { 
                  //if the message typed is logout
                  if (msg.equals("logout")) {
                     loggedIn = false;
                     dos.writeUTF(msg);
                     break;
                  }
                  
                  //if the message typed is Y or N for file transfer
                  if (msg.equals("Y")) {
                     dos.writeUTF(msg);
                     //System.out.println("now here");
                     continue;
                     //receiveFile();
                  }
                  else if(msg.equals("N")) {
                        dos.writeUTF(msg);
                        //System.out.println(msg);
                     }

                  
                  //if message is send file
                  if (msg.equals("send file")) {
                     System.out.println("Type name of recepient:");
                     recipient = scn.nextLine();
                     System.out.println("Enter file name:");
                     file = scn.nextLine();
                     dos.writeUTF(msg+"#"+recipient);
                     sendFile(file, s);
                  } else {
   						// write on the output stream 
   						dos.writeUTF(msg); 
                  }
					} catch (IOException e) { 
						System.out.println("There is an error...");
                  break; 
					} 
				} 
			} 
		}); 
		
		// readMessage thread 
		Thread readMessage = new Thread(new Runnable() 
		{ 
      
          //function to receive files
         public void receiveFile() {
            try {
               byte []b=new byte[5002];
               InputStream is = s.getInputStream();
               FileOutputStream fr = new FileOutputStream("received.txt");
               is.read(b,0,b.length);
               fr.write(b, 0, b.length);
               //dos.writeUTF(msg);
               System.out.println("checkpoint d");
            } catch(Exception e) {
               System.out.println(e);
            }

         }
         
			@Override
			public void run() { 

				while (true) {
               if(loggedIn==false) {
                  break;
               } 
					try { 
						// read the message sent to this client 
						String msg2 = dis.readUTF(); 
                  
                  //if the message is 'file coming'
                  if(msg2.equals("File has been saved to your directory!")) {
                     receiveFile();
                     System.out.println(msg2);
                     continue;
                  }
                  else if(msg2.equals("You have declined the file.")) {
                     System.out.println(msg2);
                     continue;
                  }
                  else {
						      System.out.println(msg2);
                    } 
					} catch (IOException e) { 
                  System.out.println("Client has left...");
                  break; 
					} 
				} 
			} 
		}); 

		sendMessage.start(); 
		readMessage.start(); 

	} 
} 
