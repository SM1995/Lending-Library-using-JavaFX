package media;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    public ArrayList<MediaItem> items;
    public int numberOfItems;

    public Library() {
        items = new ArrayList<>();
        numberOfItems = 0;
    }

    public int displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            showMenu();
            System.out.print("What would you like to do? ");
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                choice = -1;
                scanner.next();
            }
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid option. Try again.");
            }
        } while(choice < 1 || choice > 5);
        return choice;
    }

    public boolean doesTitleExist(String title) {
        for (int i = 0; i < numberOfItems; i++) {
            if (items.get(i).getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public void showMenu() {
        System.out.println("\n1. Add new item");
        System.out.println("2. Mark an item as on loan");
        System.out.println("3. List all items");
        System.out.println("4. Mark an item as returned");
        System.out.println("5. Quit");
    }

    public void addNewItem(String title, String format) {
        MediaItem mediaItem = new MediaItem(title, format);
        items.add(numberOfItems, mediaItem);
        numberOfItems++;
        save();
    }

    public boolean markItemOnLoan(String title, String name, String date) {
        for (int i = 0; i < numberOfItems; i++) {
            if (items.get(i).getTitle().equals(title)) {
                return items.get(i).markOnLoan(name, date);
            }
            //System.out.println("I'm sorry, I couldn't find " + title + " in the library.");
        }
        save();
        return false;
    }

    public String[] listAllItems() {
        String[] itemStrings = new String[numberOfItems];
        for (int i = 0; i < itemStrings.length; i++) {
            String itemStr="";
            itemStr += items.get(i).getTitle() + " (" + items.get(i).getFormat() + ") ";
            if(items.get(i).isOnLoan()) {
                itemStr += "loaned To " + items.get(i).getLoanedTo() + " on "+items.get(i).getDateLoaned();
            }
            itemStrings[i] = itemStr;
        }
        return itemStrings;
    }

    public boolean markItemReturned(String title) {
        for(int i = 0; i < numberOfItems; i++) {
            if(items.get(i).getTitle().equals(title)) {
                return items.get(i).markReturned();

            }
        }
        save();
        return false;
    }

    public void delete(String title){
        for(int i = 0; i < numberOfItems; i++) {
            if(items.get(i).getTitle().equals(title)) {
                items.remove(i);
                break;
            }
        }
        numberOfItems--;
        save();
    }

    public void save(){
        try{
            FileWriter fw = new FileWriter("library.txt");
            BufferedWriter bf = new BufferedWriter(fw);
            for(int i = 0; i < numberOfItems; i++) {
                bf.write(items.get(i).getTitle()+" ");
                bf.write(items.get(i).getFormat()+" ");
                if(items.get(i).isOnLoan()){
                    bf.write(items.get(i).isOnLoan()+" ");
                    bf.write(items.get(i).getDateLoaned()+" ");
                    bf.write(items.get(i).getLoanedTo()+" ");
                }
                bf.write(";");
                bf.newLine();
            }
            bf.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open(){
        try {
            FileReader fw = new FileReader("library.txt");
            BufferedReader bfread = new BufferedReader(fw);
            String line;
            String entry = "";
            while((line = bfread.readLine()) != null){
                entry+=line;
            }
            String[] entryList = entry.split(";");
            for (String s : entryList) {
                MediaItem mediaItem = new MediaItem();
                String[] entries = s.split(" ");
                mediaItem.setTitle(entries[0]);
                mediaItem.setFormat(entries[1]);
                if (entries.length > 2) {
                    mediaItem.setOnLoan(true);
                    mediaItem.setDateLoaned(entries[3]);
                    mediaItem.setLoanedTo(entries[4]);
                }
                items.add(mediaItem);
            }
            numberOfItems=items.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        //int choice;
        //boolean exit = false;
        //media.Library library = new media.Library();
        /*do {
            choice = library.displayMenu();
            switch (choice) {
                case 1:
                    String title, format;
                    System.out.print("What is the title? ");
                    title = scanner.nextLine();
                    System.out.print("What is the format? ");
                    format = scanner.nextLine();
                    library.addNewItem(title, format);
                    break;
                case 2:
                    System.out.print("Which item (enter the title)? ");
                    title = scanner.nextLine();
                    if(!library.doesTitleExist(title)) {
                        System.out.println("I'm sorry, I couldn't find "+title+ " in the library.");
                    } else {
                        System.out.print("Who are you loaning it to? ");
                        String name = scanner.nextLine();
                        String date;
                        System.out.print("When did you loan it to them? ");
                        date = scanner.nextLine();
                        library.markItemOnLoan(title, name, date);
                    }
                    break;
                case 3:
                    String[] movieListStrings = library.listAllItems();
                    for (int i = 0; i < movieListStrings.length; i++) {
                        System.out.println(movieListStrings[i]);
                    }
                    break;
                case 4:
                    System.out.print("Which item? ");
                    title = scanner.nextLine();
                    library.markItemReturned(title);
                    break;
                case 5:
                    System.out.println("Good bye!");
                    exit = true;
            }
        } while (!exit);*/
    //}

}
