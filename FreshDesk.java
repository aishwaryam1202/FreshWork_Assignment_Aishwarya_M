package freshdesk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.json.simple.JSONObject;

public class FreshDesk {

    public static void main(String[] args) throws IOException
    {
         Scanner s= new Scanner(System.in);
         
         System.out.println("Welcome to FreshDesk Queries");
         System.out.println("Lets create a file");
         System.out.println("Press 0 to create file in default location");
         System.out.println("                    OR                    ");
         System.out.println("Press 1 to create file in specified location");
         File newfile = new File("");
         String Filename="MyFile.txt";
         int m=s.nextInt();
         if(m==1)
                {
                    System.out.println("Enter the path to create file:");
                    String path=s.next();
                    newfile=new File(path+Filename);
                    if(newfile.createNewFile())
                        System.out.println("File Created");

                }
         else
                {
                     newfile=new File(Filename);
                    if(newfile.createNewFile())
                        System.out.println("File created in default directory");
                
                }
         System.out.println("Thus the file with name "+newfile.getName()+"is created at "+newfile.getPath());
         HashMap<String, Object> hMap = new HashMap<String, Object>();
         
         
         Boolean loop=true;
         while(loop)
            {
            System.out.println("Press 1 to create a query");
            System.out.println("Press 2 read a query");
            System.out.println("Press 3 delete a query");
            
             int option=s.nextInt();
            if(option==1)
            {
               System.out.println("Please be Ready to enter a query/complaint"); 
               System.out.println("Enter the customer name:");
               String name=s.next();
               System.out.println("Enter the product name:");
               String pro_name=s.next();
               System.out.println("Enter your contact number");
               String mob=s.next();
               System.out.println("Enter your email_id");
               String email=s.next();
               boolean correctlen=true;
               while(correctlen)
               {
                   if(email.length()<=32)
                       correctlen=false;
                   else
                       System.out.println("please enter the key with only 32 chars");
               }
               s.nextLine();
               System.out.println("Enter your queries or complaint");
               String queries=s.nextLine();
               
               
               JSONObject emps= new JSONObject();
                        emps.put("Costomer-Name",name);
                        emps.put("Product-Name",pro_name);
                        emps.put("Mobile",mob);
                        emps.put("email",email);
                        emps.put("queries",queries);
                        
                        
                        
               hMap.put(email,emps);
                
            writehashmaptotextFile(hMap,newfile);
            }
             else if(option ==2)
            {
                  System.out.println("Enter the email to read the specific data");
                  String key=s.next();
                  boolean found=false;
                  
                  Map<String,Object> mapFromFile = getHashMapFromTextFile(newfile);
                   for(Map.Entry<String, Object> entry : mapFromFile.entrySet())
                   {
                       if(key.equals(entry.getKey()))
                       {
                         System.out.println( entry.getKey() + " => " + entry.getValue() );
                         found=true;
                       }
                       if(found==false)
                           System.out.println("Not found in the file");
                           
                   }
            }
            else
            {
                 System.out.println("Enter the email to delete the specific data");
                 String deletekey=s.next();
                 boolean found=false;
                  
                  Map<String,Object> mapFromFile = getHashMapFromTextFile(newfile);
                   for(Map.Entry<String, Object> entry : mapFromFile.entrySet())
                   {
                       if(deletekey.equals(entry.getKey()))
                       {
                         mapFromFile.remove(deletekey);
                         found=true;
                       }
                       if(found==true)
                           System.out.println("the element is deleted");
                       else
                           System.out.println("The element is not present in the file");
                           
                   }
                   hMap=new HashMap(mapFromFile);
                   writehashmaptotextFile(hMap,newfile);
            }
            System.out.println("Press 1 Perform another operation ");
            System.out.println("Press 0 to exit");
            
            int loops=s.nextInt();
            if(loops==0)
                loop=false;
            }
            
                
    }
    public static void writehashmaptotextFile(HashMap<String, Object> hMap,File filename)
    {
        BufferedWriter bf = null;;
        
        try{
            
            bf = new BufferedWriter( new FileWriter(filename) );
 
            for(Map.Entry<String, Object> entry : hMap.entrySet())
            {
                
                bf.write( entry.getKey() + "--" + entry.getValue() );
                bf.newLine();
            }
            
            bf.flush();
 
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            
            try{
                //always close the writer
                bf.close();
            }catch(Exception e){}
        }
    }
     public static Map<String,Object> getHashMapFromTextFile(File newfile){
        
        Map<String, Object> mapFileContents = new HashMap<String, Object>();
        BufferedReader br = null;
        
        try{
            
            br = new BufferedReader( new FileReader(newfile) );
            System.out.println(newfile.getPath());
            String line = null;
            
            //read file line by line
            while ( (line = br.readLine()) != null ){
                
                //split the line by :
                String[] parts = line.split("--");
                
                //first part is name, second is age
                String name = parts[0].trim();
                Object obj =  parts[1].trim();
                
                //put name, age in HashMap if they are not empty
                if( !name.equals("") && !obj.equals("") )
                    mapFileContents.put(name, obj);
            }
                        
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            
            //Always close the BufferedReader
            if(br != null){
                try { 
                    br.close(); 
                }catch(Exception e){};
            }
        }        
        
        return mapFileContents;
        
    }
    
    
    
    
}
